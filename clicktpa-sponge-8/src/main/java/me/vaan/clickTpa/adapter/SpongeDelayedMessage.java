package me.vaan.clickTpa.adapter;

import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.commands.DelayedMessage;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Config;
import me.vaan.clickTpa.common.file.Message;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

import java.time.Duration;
import java.util.Map;

public interface SpongeDelayedMessage extends DelayedMessage {
    default void sendDelayMessage(Map<String, TeleportType> requests, TpaUser p, TpaUser target) {
        int seconds = Config.getInstance().getInt("expire-request") * 20;

        Sponge.server().scheduler().submit(
                Task.builder()
                        .plugin(ClickTpa.getInstance().container())
                        .delay(Duration.ofSeconds(seconds))
                        .execute(() -> {
                                TeleportType result = requests.get(p.name());
                                if (result != null) {
                                    requests.remove(p.name());
                                    Message.sendMessage(target.audience(), "error.request_expired", target.name());
                                }
                            }
                        )
                        .build()
        );
    }
}
