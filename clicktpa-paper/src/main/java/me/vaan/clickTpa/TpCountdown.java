package me.vaan.clickTpa;

import me.vaan.clickTpa.adapter.PaperAdapter;
import me.vaan.clickTpa.common.teleport.CountdownHandler;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.teleport.CountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TpCountdown implements CountdownHandler {
    private BukkitTask task;

    public void startCountdown(TeleportType type, Player sender, Player target) {
        TpaUser senderUser = PaperAdapter.adapt(sender);
        TpaUser targetUser = PaperAdapter.adapt(target);
        task = Bukkit.getScheduler().runTaskTimer(
                ClickTpa.getInstance(),
                new CountdownTask(this, senderUser, targetUser, type),
                0,
                10
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