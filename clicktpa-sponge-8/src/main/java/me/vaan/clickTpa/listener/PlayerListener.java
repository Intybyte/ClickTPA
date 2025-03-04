package me.vaan.clickTpa.listener;

import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.adapter.SpongeAdapter;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.file.Chat;
import me.vaan.clickTpa.common.file.Config;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.message.PlayerChatEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.math.vector.Vector3d;

import java.util.Optional;

public class PlayerListener {

    @Listener
    public void onLeave(ServerSideConnectionEvent.Disconnect e) {
        ClickTpa.registry.remove(e.player().name());
    }

    @Listener
    public void onChat(PlayerChatEvent e) {
        if (Config.getInstance().getBool("click-user-renderer")) {
            e.setChatFormatter(this::render);
        }
    }

    @Listener
    public void onMove(MoveEntityEvent e, @Getter("entity") ServerPlayer p) {
        if (!ClickTpa.registry.listenMovement.contains(p.name()))
            return;

        Vector3d d = e.destinationPosition();
        Vector3d o = e.originalPosition();

        if (o.floorX() != d.floorX() || o.floorZ() != d.floorZ()) {
            ClickTpa.registry.listenMovement.remove(p.name());
            ClickTpa.registry.modes.put(p.name(), TeleportMode.TP_ON);
        }
    }

    public Optional<Component> render(ServerPlayer player, Audience viewer, Component message, Component originalMessage) {
        TpaUser playerUser = SpongeAdapter.adapt(player);
        Component name;
        Component playerName = Component.text(playerUser.name());
        if (viewer instanceof ServerPlayer) {
            ServerPlayer viewerPlayer = (ServerPlayer) viewer;
            name = Chat.getNameComponent(playerUser, SpongeAdapter.adapt(viewerPlayer), playerName);
        } else {
            name = Chat.getTpaComponent(playerUser, playerName);
        }

        return Optional.of(Component.text("<")
                .append(name)
                .append(Component.text("> "))
                .append(message));
    }
}
