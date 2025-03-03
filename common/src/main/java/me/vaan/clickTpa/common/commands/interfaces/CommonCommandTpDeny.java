package me.vaan.clickTpa.common.commands.interfaces;

import me.vaan.clickTpa.common.ClickTpaPlugin;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface CommonCommandTpDeny {
    default void runCommand(@NotNull TpaUser p, @Nullable TpaUser target) {
        Map<String, TeleportType> tpList = ClickTpaPlugin.registry.tpaMap.get(p.name());
        if (tpList.isEmpty()) {
            Message.sendMessage(p.audience(), "error.no_request");
            return;
        }

        if (target == null) {
            Message.sendMessage(p.audience(), "error.no_player");
            return;
        }

        if (target.name().equals(p.name())) {
            Message.sendMessage(p.audience(), "error.teleport_self_fail");
            return;
        }


        if (target.isOnline()) {
            Message.sendMessage(target.audience(), "command.tpdeny.denied_by_target", p.name());
            Message.sendMessage(p.audience(), "command.tpdeny.deny_success", target.name());
        } else {
            Message.sendMessage(p.audience(), "error.target_offline", target.name());
        }

        tpList.remove(target.name());
    }
}
