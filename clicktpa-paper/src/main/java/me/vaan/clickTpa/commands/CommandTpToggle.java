package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.vaan.clickTpa.adapter.PaperAdapter;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.CommonCommandTpToggle;
import org.bukkit.entity.Player;

@CommandAlias("tptoggle")
@CommandPermission("clicktpa.tptoggle")
public class CommandTpToggle extends BaseCommand implements CommonCommandTpToggle {

    @Default
    public void onExecute(Player p) {
        TpaUser user = PaperAdapter.adapt(p);
        runCommand(user);
    }
}
