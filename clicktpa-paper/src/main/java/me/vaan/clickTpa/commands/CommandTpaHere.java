package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.vaan.clickTpa.adapter.PaperDelayedMessage;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.CommonCommandTpRequest;
import me.vaan.clickTpa.adapter.PaperAdapter;
import me.vaan.clickTpa.common.enums.TeleportType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("tpahere")
@CommandPermission("clicktpa.tpahere")
public class CommandTpaHere extends BaseCommand implements CommonCommandTpRequest, PaperDelayedMessage {

    @Default
    @Syntax("<playerName>")
    @CommandCompletion("@players")
    public void onExecute(Player p, String targetName) {
        TpaUser playerNamed = PaperAdapter.adapt(p);
        Player target = Bukkit.getPlayer(targetName);
        TpaUser targetNamed = PaperAdapter.adapt(target);
        runCommand(playerNamed, targetNamed, TeleportType.TPAHERE);
    }
}
