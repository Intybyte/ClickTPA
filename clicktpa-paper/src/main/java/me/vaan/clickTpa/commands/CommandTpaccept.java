package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.adapter.PaperAdapter;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.CommonCommandTpaccept;
import me.vaan.clickTpa.TpCountdown;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("tpaccept")
@CommandPermission("clicktpa.tpaccept")
public class CommandTpaccept extends BaseCommand implements CommonCommandTpaccept {

    @Default
    @Syntax("<playerName>")
    @CommandCompletion("@players")
    public void onExecute(Player p, String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        TpaUser pUser = PaperAdapter.adapt(p);
        TpaUser targetUser = PaperAdapter.adapt(target);
        runCommand(pUser, targetUser);
    }

    @Override
    public void success(TeleportType tpType, TpaUser p, TpaUser target) {
        TpCountdown cdListener = new TpCountdown();
        Player pPlayer = (Player) p.audience();
        Player targetPlayer = (Player) target.audience();

        switch (tpType) {
            case TPA -> {
                ClickTpa.registry.modes.put(target.name(), TeleportMode.TELEPORTING);
                ClickTpa.registry.listenMovement.add(target.name());
                Message.sendTitle(target.audience(), "titles.teleport_started");
                cdListener.startCountdown(TeleportType.TPA, pPlayer, targetPlayer); // target = person who sent the reques
            }
            case TPAHERE -> {
                ClickTpa.registry.modes.put(p.name(), TeleportMode.TELEPORTING);
                ClickTpa.registry.listenMovement.add(p.name());
                Message.sendTitle(p.audience(), "titles.teleport_started");
                cdListener.startCountdown(TeleportType.TPAHERE, pPlayer, targetPlayer);
            }
        }

        ClickTpa.registry.removeTpRequest(p.name(), target.name());
    }
}
