package me.vaan.clickTpa.common;

import net.kyori.adventure.audience.Audience;

public interface NamedAudience {
    Audience audience();
    String name();
}
