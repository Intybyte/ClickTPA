package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import io.papermc.lib.PaperLib;
import me.vaan.clickTpa.common.file.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandAlias("tpo")
@CommandPermission("clicktpa.tpo")
public class CommandTpo extends BaseCommand {

    @Default
    @Syntax("<playerName>")
    @CommandCompletion("@players")
    public void onExecute(Player p, String targetName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        if (!target.hasPlayedBefore()) {
            Message.sendMessage(p, "error.no_player");
            return;
        }

        if (target.getUniqueId().equals(p.getUniqueId())) {
            Message.sendMessage(p, "error.teleport_self_fail");
            return;
        }

        Message.sendMessage(p, "command.tpo.teleport_success", target.getName());
        //target can never be null when hasPlayedBefore is true
        PaperLib.teleportAsync(p, target.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
    }
}
