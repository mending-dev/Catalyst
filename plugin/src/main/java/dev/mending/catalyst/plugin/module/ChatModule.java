package dev.mending.catalyst.plugin.module;

import dev.mending.catalyst.api.Module;
import dev.mending.catalyst.plugin.Catalyst;
import dev.mending.core.paper.api.language.Lang;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.NotNull;

public class ChatModule extends Module {

    private final Catalyst plugin;

    public ChatModule(Catalyst plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {

        e.renderer(new ChatRenderer() {

            @Override
            public @NotNull Component render (
                @NotNull Player source,
                @NotNull Component sourceDisplayName,
                @NotNull Component message,
                @NotNull Audience viewer
            ) {
                return plugin.getLanguage().get("chatFormat")
                    .replaceText(Lang.replace("%player%", source.getName()))
                    .replaceText(Lang.replace("%displayName%", PlainTextComponentSerializer.plainText().serialize(sourceDisplayName)))
                    .replaceText(Lang.replace("%message%", PlainTextComponentSerializer.plainText().serialize(message)))
                ;
            }
        });
    }

}
