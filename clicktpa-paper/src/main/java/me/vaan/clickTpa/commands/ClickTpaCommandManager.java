package me.vaan.clickTpa.commands;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;

public class ClickTpaCommandManager extends PaperCommandManager {
    public ClickTpaCommandManager(Plugin plugin) {
        super(plugin);
    }

    public void registerCommands() {
        this.registerCommand(new CommandTpa());
        this.registerCommand(new CommandTpaHere());
        this.registerCommand(new CommandTpaccept());
        this.registerCommand(new CommandTpDeny());
        this.registerCommand(new CommandTpo());
        this.registerCommand(new CommandTpoHere());
        this.registerCommand(new CommandTpToggle());
        this.registerCommand(new CommandReload());
    }
}
