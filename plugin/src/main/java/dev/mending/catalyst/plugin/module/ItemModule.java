package dev.mending.catalyst.plugin.module;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.mending.catalyst.api.Module;
import dev.mending.catalyst.plugin.Catalyst;
import dev.mending.core.paper.api.language.Lang;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemModule extends Module {

    private final Catalyst plugin;

    public ItemModule(Catalyst plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        registerCommand(Commands.literal("i")
            .requires(sender -> sender.getSender().hasPermission("catalyst.command.item"))
            .then(Commands.argument("itemStack", ArgumentTypes.itemStack())
                .executes(ctx -> {
                    giveItem(ctx, false);
                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                    .executes(ctx -> {
                        giveItem(ctx, true);
                        return Command.SINGLE_SUCCESS;
                    })
                )
            )
            .build()
        );
    }

    private void giveItem(CommandContext<CommandSourceStack> ctx, boolean amountArgument) {
        if (ctx.getSource().getSender() instanceof Player player) {

            ItemStack itemStack = ctx.getArgument("itemStack", ItemStack.class);

            if (amountArgument) {
                itemStack.setAmount(ctx.getArgument("amount", Integer.class));
            }

            player.getInventory().addItem(itemStack);
            player.sendMessage(plugin.getLanguage().get("itemReceived")
                .replaceText(Lang.replace("%amount%", "" + itemStack.getAmount()))
                .replaceText(Lang.replace("%itemStack%", itemStack.getType().name())
            ));
        }
    }
}
