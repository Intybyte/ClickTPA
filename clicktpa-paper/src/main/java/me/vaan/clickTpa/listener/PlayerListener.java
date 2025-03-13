package me.vaan.clickTpa.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.adapter.PaperAdapter;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.file.Chat;
import me.vaan.clickTpa.common.file.Config;
import me.vaan.clickTpa.common.enums.TeleportMode;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        ClickTpa.registry.remove(e.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onChat(AsyncChatEvent e) {
        if (Config.getInstance().getBool("click-user-renderer")) {
            e.renderer(this::render);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!ClickTpa.registry.listenMovement.contains(p.getName()))
            return;

        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            ClickTpa.registry.listenMovement.remove(p.getName());
            ClickTpa.registry.modes.put(p.getName(), TeleportMode.TP_ON);
        }
    }

    public Component render(Player player, Component sourceDisplayName, Component message, Audience viewer) {
        TpaUser playerUser = PaperAdapter.adapt(player);
        Component name;
        if (viewer instanceof Player viewerPlayer) {
            name = Chat.getNameComponent(playerUser, PaperAdapter.adapt(viewerPlayer), sourceDisplayName);
        } else {
            name = Chat.getTpaComponent(playerUser, sourceDisplayName);
        }

        return Component.text("<")
                .append(name)
                .append(Component.text("> "))
                .append(message);
    }
}