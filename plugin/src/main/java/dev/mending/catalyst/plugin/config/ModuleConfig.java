package dev.mending.catalyst.plugin.config;

import com.google.gson.JsonObject;
import dev.mending.catalyst.api.Module;
import dev.mending.catalyst.plugin.Catalyst;
import dev.mending.catalyst.plugin.gui.InvSeeGui;
import dev.mending.catalyst.plugin.module.*;
import dev.mending.core.paper.api.config.json.Configuration;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public class ModuleConfig extends Configuration {

    private final Catalyst plugin;

    public ModuleConfig(@NotNull Catalyst plugin, @NotNull String fileName) {
        super(plugin, fileName);
        this.plugin = plugin;
    }

    @Override
    public void onLoad(JsonObject json) {

        JsonObject modulesJson = json.getAsJsonObject("modules");

        // Hardcoded class list (simpler than reflection, avoids extra deps)
        Class<?>[] moduleClasses = {
            ChatModule.class,
            NotifierModule.class,
            ItemModule.class,
            SkullModule.class,
            EnderChestModule.class,
            InvSeeModule.class,
            RepairModule.class,
        };

        for (Class<?> clazz : moduleClasses) {
            String name = clazz.getSimpleName();
            boolean enabled = modulesJson.has(name) && modulesJson.get(name).getAsBoolean();

            if (enabled) {
                try {
                    Object instance;

                    try {
                        // Prefer constructor with plugin
                        Constructor<?> ctor = clazz.getConstructor(Catalyst.class);
                        instance = ctor.newInstance(plugin);
                    } catch (NoSuchMethodException e) {
                        // Fallback to no-arg
                        instance = clazz.getDeclaredConstructor().newInstance();
                    }

                    plugin.getModuleManager().register((Module) instance);
                    plugin.getLogger().info("Loaded module: " + name);

                } catch (Exception e) {
                    plugin.getLogger().warning("Failed to load module: " + name + " - " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
