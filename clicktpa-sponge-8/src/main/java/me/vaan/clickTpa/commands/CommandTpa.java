package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.vaan.clickTpa.adapter.SpongeAdapter;
import me.vaan.clickTpa.adapter.SpongeDelayedMessage;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.CommonCommandTpRequest;
import me.vaan.clickTpa.common.enums.TeleportType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

@CommandAlias("tpa")
@CommandPermission("clicktpa.tpa")
public class CommandTpa extends BaseCommand implements CommonCommandTpRequest, SpongeDelayedMessage {

    @Default
    @Syntax("<playerName>")
    @CommandCompletion("@players")
    public void onExecute(ServerPlayer p, String targetName) {
        TpaUser playerNamed = SpongeAdapter.adapt(p);
        ServerPlayer target = Sponge.server().player(targetName).orElse(null);
        TpaUser targetNamed = SpongeAdapter.adapt(target);
        runCommand(playerNamed, targetNamed, TeleportType.TPA);
    }
}
