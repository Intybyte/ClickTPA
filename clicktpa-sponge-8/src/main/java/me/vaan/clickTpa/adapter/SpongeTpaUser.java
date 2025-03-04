package me.vaan.clickTpa.adapter;

import me.vaan.clickTpa.common.TpaUser;
import net.kyori.adventure.audience.Audience;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public class SpongeTpaUser implements TpaUser {

    private final ServerPlayer player;

    SpongeTpaUser(ServerPlayer player) {
        this.player = player;
    }

    @Override
    public Audience audience() {
        return player;
    }

    @Override
    public String name() {
        return player.name();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }
}
