package me.vaan.clickTpa.adapter;

import io.papermc.lib.PaperLib;
import lombok.Getter;
import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@Getter
public class PaperTpaUser implements TpaUser {
    private final Player user;

    PaperTpaUser(Player user) {
        this.user = user;
    }

    @Override
    public Audience audience() {
        return user;
    }

    @Override
    public String name() {
        return user.getName();
    }

    @Override
    public boolean isOnline() {
        return user.isOnline();
    }

    @Override
    public void teleport(TpaUser target, TeleportType type) {
        PaperTpaUser paperTarget = (PaperTpaUser) target;

        switch (type) {
            case TPA -> {
                Message.sendTitle(target.audience(), "titles.teleport_complete");
                PaperLib.teleportAsync(paperTarget.getUser(), user.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
                ClickTpa.registry.modes.put(target.name(), TeleportMode.TP_ON);
            }
            case TPAHERE -> {
                Message.sendTitle(user, "titles.teleport_complete");
                PaperLib.teleportAsync(user, paperTarget.getUser().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
                ClickTpa.registry.modes.put(user.getName(), TeleportMode.TP_ON);
            }
        }
    }
}
