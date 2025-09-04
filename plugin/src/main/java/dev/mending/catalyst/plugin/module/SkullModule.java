package dev.mending.catalyst.plugin.module;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.mending.catalyst.api.Module;
import dev.mending.catalyst.plugin.Catalyst;
import dev.mending.core.paper.api.item.SkullBuilder;
import dev.mending.core.paper.api.language.Lang;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class SkullModule extends Module {

    private final Catalyst plugin;

    public SkullModule(Catalyst plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        registerCommand(Commands.literal("skull")
            .requires(sender -> sender.getSender().hasPermission("catalyst.command.skull"))
            .then(Commands.argument("player", StringArgumentType.word())
                .suggests((ctx, builder) -> {
                    Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(builder.getRemainingLowerCase()))
                        .forEach(builder::suggest);
                    return builder.buildFuture();
                })
                .executes(ctx -> {
                    if (ctx.getSource().getSender() instanceof Player player) {
                        final String target = ctx.getArgument("player", String.class);
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                            try {
                                ItemStack skull = new SkullBuilder(target).build();
                                Bukkit.getScheduler().runTask(plugin, () -> {
                                    player.getInventory().addItem(skull);
                                    player.sendMessage(plugin.getLanguage().get("skullReceived")
                                        .replaceText(Lang.replace("%player%", target)
                                    ));
                                });
                            } catch (Exception e) {
                                plugin.getLogger().warning(e.getMessage());
                            }
                        });
                    }
                    return Command.SINGLE_SUCCESS;
                })
            )
            .build()
        );
    }
}
