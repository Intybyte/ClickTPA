package me.vaan.clickTpa.adapter;

import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public class SpongeAdapter {

    public static SpongeTpaUser adapt(ServerPlayer player) {
        if (player == null) {
            return null;
        }

        return new SpongeTpaUser(player);
    }
}
