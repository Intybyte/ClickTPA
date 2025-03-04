package me.vaan.clickTpa.common.teleport;

import me.vaan.clickTpa.common.ClickTpaPlugin;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Config;
import me.vaan.clickTpa.common.file.Message;

public class CountdownTask implements Runnable {
    private final CountdownHandler countdownHandler;
    private final TpaUser sender;
    private final TpaUser target;
    private final TeleportType type;
    private int time;

    public CountdownTask(CountdownHandler countdownHandler, TpaUser sender, TpaUser target, TeleportType type) {
        this.countdownHandler = countdownHandler;
        this.sender = sender;
        this.target = target;
        this.type = type;
        int duration = Config.getInstance().getInt("tp-wait-duration");
        time = duration * 2;
    }

    @Override
    public void run() {
        if (disconnectListener(sender, target) || moveListener(type, sender, target)) {
            ClickTpaPlugin.registry.removeTpRequest(sender.name(), target.name());
            countdownHandler.cancel();
            return;
        }

        if (time == 0) {
            sender.teleport(target, type);
            countdownHandler.cancel();
        }

        time--;
    }

    private boolean moveListener(TeleportType type, TpaUser sender, TpaUser target) {
        if (ClickTpaPlugin.registry.listenMovement.contains(target.name())) {
            return false;
        }

        Message.sendMoved(sender, target, type);
        return true;
    }

    private boolean disconnectListener(TpaUser sender, TpaUser target) {
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
}
