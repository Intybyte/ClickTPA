package me.vaan.clickTpa.common.commands;

import me.vaan.clickTpa.common.ClickTpaPlugin;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface CommonCommandTpaccept {
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


        TeleportType tpType = tpList.get(target.name());
        if (tpType == null) {
            Message.sendMessage(p.audience(), "error.no_request");
            return;
        }

        Message.sendMessage(p.audience(), "command.tpaccept.accept_success", target.name());
        Message.sendMessage(target.audience(), "command.tpaccept.accepted_by_target", p.name());

        success(tpType, p, target);
    }

    void success(TeleportType type, TpaUser p, TpaUser target);
}
