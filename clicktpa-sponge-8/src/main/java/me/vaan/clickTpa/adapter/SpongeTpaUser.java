package me.vaan.clickTpa.adapter;

import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.enums.TeleportType;
import me.vaan.clickTpa.common.file.Message;
import net.kyori.adventure.audience.Audience;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.math.vector.Vector3d;


public class SpongeTpaUser implements TpaUser {

    private final ServerPlayer user;

    SpongeTpaUser(ServerPlayer user) {
        this.user = user;
    }

    public ServerPlayer getUser() {
        return user;
    }

    @Override
    public Audience audience() {
        return user;
    }

    @Override
    public String name() {
        return user.name();
    }

    @Override
    public boolean isOnline() {
        return user.isOnline();
    }

    @Override
    public void teleport(TpaUser target, TeleportType type) {
        SpongeTpaUser spongeTarget = (SpongeTpaUser) target;
        ServerPlayer targetPlayer = spongeTarget.getUser();
        Vector3d rotation;

        switch (type) {
            case TPA:
                Message.sendTitle(target.audience(), "titles.teleport_complete");


                rotation = targetPlayer.rotation();
                targetPlayer.setLocation(user.serverLocation());
                targetPlayer.setRotation(rotation);

                ClickTpa.registry.modes.put(target.name(), TeleportMode.TP_ON);
                break;
            case TPAHERE:
                Message.sendTitle(user, "titles.teleport_complete");

                rotation = user.rotation();
                user.setLocation(targetPlayer.serverLocation());
                user.setRotation(rotation);

                ClickTpa.registry.modes.put(user.name(), TeleportMode.TP_ON);
                break;
        }
    }
}
