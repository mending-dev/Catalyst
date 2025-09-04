package dev.mending.catalyst.plugin.gui;

import dev.mending.core.paper.api.gui.Gui;
import dev.mending.core.paper.api.gui.GuiIcon;
import dev.mending.core.paper.api.item.ItemBuilder;
import dev.mending.core.paper.api.language.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InvSeeGui extends Gui {

    private final Player target;

    public InvSeeGui(Player target) {
        super(Component.text(target.getName()), 5);
        this.target = target;
    }

    @Override
    public void update() {

        for (int slot = 0; slot < 36; slot++) {
            ItemStack itemStack = target.getInventory().getItem(slot);
            setItem(slot, new GuiIcon(itemStack != null ? itemStack : new ItemStack(Material.AIR)));
        }

        setItem(37, new GuiIcon(
            target.getInventory().getHelmet().getType() != Material.AIR ?
            target.getInventory().getHelmet() : new ItemBuilder(Material.BARRIER).setName(Lang.deserialize("<red>Empty Helmet Slot")).build()
        ));

        setItem(38, new GuiIcon(
            target.getInventory().getChestplate().getType() != Material.AIR ?
            target.getInventory().getChestplate() : new ItemBuilder(Material.BARRIER).setName(Lang.deserialize("<red>Empty Chestplate Slot")).build()
        ));

        setItem(39, new GuiIcon(
            target.getInventory().getLeggings().getType() != Material.AIR ?
            target.getInventory().getLeggings() : new ItemBuilder(Material.BARRIER).setName(Lang.deserialize("<red>Empty Leggings Slot")).build()
        ));

        setItem(40, new GuiIcon(
            target.getInventory().getBoots().getType() != Material.AIR ?
            target.getInventory().getBoots() : new ItemBuilder(Material.BARRIER).setName(Lang.deserialize("<red>Empty Boots Slot")).build()
        ));

        setItem(42, new GuiIcon(
            target.getInventory().getItemInOffHand().getType() != Material.AIR ?
            target.getInventory().getItemInOffHand() : new ItemBuilder(Material.BARRIER).setName(Lang.deserialize("<red>Empty Off-Hand Slot")).build()
        ));
    }
}
