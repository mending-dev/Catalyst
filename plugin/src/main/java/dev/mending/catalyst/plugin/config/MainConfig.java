package dev.mending.catalyst.plugin.config;

import dev.mending.core.paper.api.config.json.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class MainConfig extends Configuration {

    public MainConfig(@NotNull JavaPlugin plugin, @NotNull String fileName) {
        super(plugin, fileName);
    }

}
