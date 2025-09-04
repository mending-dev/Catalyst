package dev.mending.catalyst.api;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final JavaPlugin plugin;
    private final List<Module> modules;

    public ModuleManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.modules = new ArrayList<>();
    }

    public void register(Module module) {
        module.onEnable();
        plugin.getServer().getPluginManager().registerEvents(module, this.plugin);
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            for (LiteralCommandNode<CommandSourceStack> node : module.getCommandNodes()) {
                commands.registrar().register(node);
            }
        });
        this.modules.add(module);
    }

    public void unregister(Module module) {
        module.onDisable();
        this.modules.remove(module);
    }

    public void unregisterAll() {
        this.modules.forEach(Module::onDisable);
        this.modules.clear();
    }

    public Module[] getModules() {
        return this.modules.toArray(new Module[0]);
    }
}
