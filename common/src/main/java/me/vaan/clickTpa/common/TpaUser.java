package me.vaan.clickTpa.common;

import me.vaan.clickTpa.common.enums.TeleportType;
import net.kyori.adventure.audience.Audience;

public interface TpaUser {
    Audience audience();
    String name();
    boolean isOnline();
    void teleport(TpaUser target, TeleportType type);
}
