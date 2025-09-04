package dev.mending.catalyst.plugin.module;

import dev.mending.catalyst.api.Module;
import dev.mending.catalyst.plugin.Catalyst;
import dev.mending.core.paper.api.language.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NotifierModule extends Module {

    private final Catalyst plugin;

    public NotifierModule(Catalyst plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.joinMessage(plugin.getLanguage().get("playerJoin")
            .replaceText(Lang.replace("%player%", e.getPlayer().getName()))
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.quitMessage(plugin.getLanguage().get("playerQuit")
            .replaceText(Lang.replace("%player%", e.getPlayer().getName()))
        );
    }
}
