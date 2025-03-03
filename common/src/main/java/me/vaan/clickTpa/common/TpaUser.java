package me.vaan.clickTpa.common;

import net.kyori.adventure.audience.Audience;

public interface TpaUser {
    Audience audience();
    String name();
    boolean isOnline();
}
