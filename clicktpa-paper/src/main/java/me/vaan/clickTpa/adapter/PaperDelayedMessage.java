package me.vaan.clickTpa.adapter;

import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.DelayedMessage;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Config;
import me.vaan.clickTpa.common.file.Message;
import org.bukkit.Bukkit;

import java.util.Map;

public interface PaperDelayedMessage extends DelayedMessage {
    @Override
    default void sendDelayMessage(Map<String, TeleportType> requests, TpaUser p, TpaUser target) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(ClickTpa.getInstance(), () -> {
            var result = requests.get(p.name());
            if (result != null) {
                requests.remove(p.name());
                Message.sendMessage(target.audience(), "error.request_expired", target.name());
            }

        }, Config.getInstance().getInt("expire-request") * 20L);
    }
}
