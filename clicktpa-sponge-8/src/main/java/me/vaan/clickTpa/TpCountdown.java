package me.vaan.clickTpa;

import me.vaan.clickTpa.adapter.SpongeAdapter;
import me.vaan.clickTpa.common.teleport.CountdownHandler;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;
import me.vaan.clickTpa.common.teleport.CountdownTask;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;

public class TpCountdown implements CountdownHandler {
    private ScheduledTask task;

    public void startCountdown(TeleportType type, ServerPlayer sender, ServerPlayer target) {
        switch (type) {
            case TPA:
                Message.sendTitle(target, "titles.teleport_started");
                break;
            case TPAHERE:
                Message.sendTitle(sender, "titles.teleport_started");
                break;
        }

        TpaUser senderUser = SpongeAdapter.adapt(sender);
        TpaUser targetUser = SpongeAdapter.adapt(target);
        task = Sponge.server().scheduler().submit(Task.builder()
                .plugin(ClickTpa.getInstance().container())
                .interval(Ticks.of(10))
                .execute(new CountdownTask(this, senderUser, targetUser, type))
                .build()
        );
    }

    @Override
    public void cancel() {
        if (task != null) {
            task.cancel();
        }
        task = null;
    }
}