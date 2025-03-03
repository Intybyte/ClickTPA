package me.vaan.clickTpa.common.commands.interfaces;

import me.vaan.clickTpa.common.ClickTpaPlugin;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CommonCommandTpRequest extends DelayedMessage {
    default void runCommand(@NotNull TpaUser p, @Nullable TpaUser target, TeleportType type) {
        if (target == null) {
            Message.sendMessage(p.audience(), "error.no_player");
            return;
        }

        if (target.name().equals(p.name())) {
            Message.sendMessage(p.audience(), "error.teleport_self_fail");
            return;
        }

        TeleportMode modeExecutor = ClickTpaPlugin.registry.modes.get(p.name());
        if (modeExecutor == TeleportMode.TP_OFF) {
            Message.sendMessage(p.audience(), "error.teleport_off", p.name());
            return;
        }

        TeleportMode modeTarget = ClickTpaPlugin.registry.modes.get(target.name());
        if (modeTarget == TeleportMode.TP_OFF) {
            Message.sendMessage(p.audience(), "error.teleport_off", target.name());
            return;
        }

        if (modeExecutor != TeleportMode.TP_ON) {
            return;
        }

        var requests = ClickTpaPlugin.registry.tpaMap.get(target.name());
        if (requests.containsKey(p.name())) {
            Message.sendMessage(p.audience(), "error.already_requested");
            return;
        }


        Message.sendTpRequest(type, p, target);
        requests.put(p.name(), type);

        sendDelayMessage(requests, p, target);
    }
}
