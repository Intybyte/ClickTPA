package me.vaan.clickTpa.common;

import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;

public interface ITpCountdown {
    default boolean moveListener(TeleportType type, TpaUser sender, TpaUser target) {
        if (ClickTpaPlugin.registry.listenMovement.contains(target.name())) {
            return false;
        }

        Message.sendMoved(sender, target, type);
        return true;
    }

    default boolean disconnectListener(TpaUser sender, TpaUser target) {
        if (!sender.isOnline()) {
            Message.sendMessage(target.audience(), "error.interrupted");
            ClickTpaPlugin.registry.modes.put(target.name(), TeleportMode.TP_ON);

            return true;
        }

        if (!target.isOnline()) {
            Message.sendMessage(sender.audience(), "error.interrupted");
            ClickTpaPlugin.registry.modes.put(sender.name(), TeleportMode.TP_ON);

            return true;
        }

        return false;
    }

    void cancel();
}
