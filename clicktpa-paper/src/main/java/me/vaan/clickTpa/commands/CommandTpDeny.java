package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.vaan.clickTpa.adapter.PaperAdapter;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.CommonCommandTpDeny;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("tpdeny")
@CommandPermission("clicktpa.tpdeny")
public class CommandTpDeny extends BaseCommand implements CommonCommandTpDeny {

    @Default
    @Syntax("<playerName>")
    @CommandCompletion("@players")
    public void onExecute(Player p, String playerName) {
        TpaUser playerNamed = PaperAdapter.adapt(p);
        Player target = Bukkit.getPlayer(playerName);
        TpaUser targetNamed = PaperAdapter.adapt(target);
        runCommand(playerNamed, targetNamed);
    }
}
