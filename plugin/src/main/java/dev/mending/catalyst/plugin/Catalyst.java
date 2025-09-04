package dev.mending.catalyst.plugin;

import dev.mending.catalyst.api.ModuleManager;
import dev.mending.catalyst.plugin.config.MainConfig;
import dev.mending.catalyst.plugin.config.ModuleConfig;
import dev.mending.core.paper.api.language.json.Language;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Catalyst extends JavaPlugin {

    private final Language language = new Language(this);
    private final MainConfig mainConfig = new MainConfig(this, "config");
    private final ModuleConfig moduleConfig = new ModuleConfig(this, "modules");
    private final ModuleManager moduleManager = new ModuleManager(this);

    @Override
    public void onEnable() {
        this.language.init();
        this.mainConfig.init();
        this.moduleConfig.init();
    }

    @Override
    public void onDisable() {
        moduleManager.unregisterAll();
    }
}
