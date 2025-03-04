package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.TpCountdown;
import me.vaan.clickTpa.adapter.SpongeAdapter;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.CommonCommandTpaccept;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

@CommandAlias("tpaccept")
@CommandPermission("clicktpa.tpaccept")
public class CommandTpaccept extends BaseCommand implements CommonCommandTpaccept {

    @Default
    @Syntax("<playerName>")
    @CommandCompletion("@players")
    public void onExecute(ServerPlayer p, String targetName) {
        ServerPlayer target = Sponge.server().player(targetName).orElse(null);
        TpaUser pUser = SpongeAdapter.adapt(p);
        TpaUser targetUser = SpongeAdapter.adapt(target);
        runCommand(pUser, targetUser);
    }

    @Override
    public void success(TeleportType tpType, TpaUser p, TpaUser target) {
        TpCountdown cdListener = new TpCountdown();
        ServerPlayer pPlayer = (ServerPlayer) p.audience();
        ServerPlayer targetPlayer = (ServerPlayer) target.audience();

        switch (tpType) {
            case TPA:
                ClickTpa.registry.modes.put(target.name(), TeleportMode.TELEPORTING);
                ClickTpa.registry.listenMovement.add(target.name());
                Message.sendTitle(targetPlayer, "titles.teleport_started");
                cdListener.startCountdown(TeleportType.TPA, pPlayer, targetPlayer); // target = person who sent the reques
                break;
            case TPAHERE:
                ClickTpa.registry.modes.put(p.name(), TeleportMode.TELEPORTING);
                ClickTpa.registry.listenMovement.add(p.name());
                Message.sendTitle(pPlayer, "titles.teleport_started");
                cdListener.startCountdown(TeleportType.TPAHERE, pPlayer, targetPlayer);
                break;
        }

        ClickTpa.registry.removeTpRequest(p.name(), target.name());
    }
}
