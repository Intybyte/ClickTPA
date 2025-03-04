package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.vaan.clickTpa.adapter.SpongeAdapter;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.CommonCommandTpToggle;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

@CommandAlias("tptoggle")
@CommandPermission("clicktpa.tptoggle")
public class CommandTpToggle extends BaseCommand implements CommonCommandTpToggle {

    @Default
    public void onExecute(ServerPlayer p) {
        TpaUser user = SpongeAdapter.adapt(p);
        runCommand(user);
    }
}
