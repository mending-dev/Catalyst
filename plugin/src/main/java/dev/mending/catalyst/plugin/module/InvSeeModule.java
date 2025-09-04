package dev.mending.catalyst.plugin.module;

import com.mojang.brigadier.Command;
import dev.mending.catalyst.api.Module;
import dev.mending.catalyst.plugin.gui.InvSeeGui;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Locale;

public class InvSeeModule extends Module {

    public InvSeeModule() {
        registerCommands();
    }

    private void registerCommands() {
        registerCommand(Commands.literal("invsee")
            .requires(sender -> sender.getSender().hasPermission("catalyst.command.invsee"))
            .then(Commands.argument("player", ArgumentTypes.player())
                .suggests((ctx, builder) -> {
                    Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(builder.getRemainingLowerCase()))
                        .forEach(builder::suggest);
                    return builder.buildFuture();
                })
                .executes(ctx -> {
                    if (ctx.getSource().getSender() instanceof Player player) {
                        final Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                        new InvSeeGui(target).open(player);
                    }
                    return Command.SINGLE_SUCCESS;
                })
            )
            .build()
        );
    }

}
