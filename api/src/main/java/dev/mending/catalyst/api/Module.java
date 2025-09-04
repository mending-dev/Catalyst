package dev.mending.catalyst.api;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public abstract class Module implements Listener {

    private final List<LiteralCommandNode<CommandSourceStack>> commandNodes = new ArrayList<>();

    public void registerCommand(LiteralCommandNode<CommandSourceStack> node) {
        this.commandNodes.add(node);
    }

    public List<LiteralCommandNode<CommandSourceStack>> getCommandNodes() {
        return commandNodes;
    }

    public void onEnable() {};
    public void onDisable() {};
}
