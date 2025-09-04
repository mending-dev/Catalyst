package dev.mending.catalyst.plugin.module;

import com.mojang.brigadier.Command;
import dev.mending.catalyst.api.Module;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.Arrays;
import java.util.Locale;

public class EnderChestModule extends Module {

    public EnderChestModule() {
        registerCommands();
    }

    private void registerCommands() {
        for (String literal : Arrays.asList("enderchest", "ec")) {
            registerCommand(Commands.literal(literal)
                .requires(sender -> sender.getSender().hasPermission("catalyst.command.enderchest"))
                .executes(ctx -> {
                    if (ctx.getSource().getSender() instanceof Player player) {
                        openToPlayer(player, player);
                    }
                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.argument("player", ArgumentTypes.player())
                    .suggests((ctx, builder) -> {
                        Bukkit.getOnlinePlayers().stream()
                            .map(Player::getName)
                            .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(builder.getRemainingLowerCase()))
                            .forEach(builder::suggest);
                        return builder.buildFuture();
                    })
                    .requires(sender -> sender.getSender().hasPermission("catalyst.command.enderchest.other"))
                    .executes(ctx -> {
                        if (ctx.getSource().getSender() instanceof Player player) {
                            final Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                            openToPlayer(player, target);
                        }
                        return Command.SINGLE_SUCCESS;
                    })
                )
                .build()
            );
        }
    }

    private void openToPlayer(Player player, Player target) {
        player.openInventory(target.getEnderChest());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getInventory().getType().equals(InventoryType.ENDER_CHEST)) {

            Player player = (Player) e.getWhoClicked();

            if (!e.getInventory().equals(player.getEnderChest()) && !player.hasPermission("catalyst.bypass.enderchest")) {
                e.setCancelled(true);
            }
        }
    }

}
