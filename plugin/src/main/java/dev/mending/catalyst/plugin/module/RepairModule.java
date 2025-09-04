package dev.mending.catalyst.plugin.module;

import com.mojang.brigadier.Command;
import dev.mending.catalyst.api.Module;
import dev.mending.catalyst.plugin.Catalyst;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RepairModule extends Module {

    private final Catalyst plugin;

    public RepairModule(Catalyst plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        registerCommand(Commands.literal("repair")
            .requires(sender -> sender.getSender().hasPermission("catalyst.command.repair"))
            .executes(ctx -> {
                if (ctx.getSource().getSender() instanceof Player player) {
                    if (!repair(player.getInventory().getItemInMainHand())) {
                        player.sendMessage(plugin.getLanguage().get("noItemToRepair"));
                        return Command.SINGLE_SUCCESS;
                    }
                    player.sendMessage(plugin.getLanguage().get("itemRepaired"));
                }
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.literal("all")
                .requires(sender -> sender.getSender().hasPermission("catalyst.command.repair.all"))
                .executes(ctx -> {
                    if (ctx.getSource().getSender() instanceof Player player) {
                        Arrays.stream(player.getInventory().getContents()).toList().forEach(itemStack -> {
                            if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                                repair(itemStack);
                            }
                        });
                        player.sendMessage(plugin.getLanguage().get("itemRepairedAll"));
                    }
                    return Command.SINGLE_SUCCESS;
                })
            )
            .build()
        );
    }

    private boolean repair(ItemStack itemStack) {

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(0);
            itemStack.setItemMeta(itemMeta);
            return true;
        } else {
            return false;
        }
    }
}
