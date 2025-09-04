package dev.mending.catalyst.plugin.module;

import com.mojang.brigadier.Command;
import dev.mending.catalyst.api.Module;
import dev.mending.catalyst.plugin.gui.InvSeeGui;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;

public class InvSeeModule extends Module {

    public InvSeeModule() {
        registerCommands();
    }

    private void registerCommands() {
        registerCommand(Commands.literal("invsee")
            .requires(sender -> sender.getSender().hasPermission("catalyst.command.invsee"))
            .then(Commands.argument("player", ArgumentTypes.player())
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
