package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.vaan.clickTpa.adapter.SpongeAdapter;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.CommonCommandTpDeny;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

@CommandAlias("tpdeny")
@CommandPermission("clicktpa.tpdeny")
public class CommandTpDeny extends BaseCommand implements CommonCommandTpDeny {

    @Default
    @Syntax("<playerName>")
    @CommandCompletion("@players")
    public void onExecute(ServerPlayer p, String playerName) {
        TpaUser playerNamed = SpongeAdapter.adapt(p);
        ServerPlayer target = Sponge.server().player(playerName).orElse(null);
        TpaUser targetNamed = SpongeAdapter.adapt(target);
        runCommand(playerNamed, targetNamed);
    }
}
