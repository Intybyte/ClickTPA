package me.vaan.clickTpa.common.file;

import me.vaan.clickTpa.common.TpaUser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;

public class Chat {
    public static @NotNull Component getNameComponent(TpaUser player, TpaUser viewer, Component name) {
        if (!viewer.name().equals(player.name())) {
            return getTpaComponent(player, name);
        }

        return name.clickEvent(
                ClickEvent.runCommand("/tptoggle")
        ).hoverEvent(
                HoverEvent.showText(Config.getInstance().getComponent("messages.chat.hover_tptoggle"))
        );
    }

    public static @NotNull Component getTpaComponent(TpaUser player, Component name) {
        return name.clickEvent(
                ClickEvent.runCommand("/tpa " + player.name())
        ).hoverEvent(
                HoverEvent.showText(Config.getInstance().getComponent("messages.chat.hover_tpa"))
        );
    }
}
