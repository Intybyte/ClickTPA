package me.vaan.clickTpa.commands;

import co.aikar.commands.SpongeCommandManager;
import org.spongepowered.plugin.PluginContainer;

public class ClickTpaCommandManager extends SpongeCommandManager {
    public ClickTpaCommandManager(PluginContainer plugin) {
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
