package me.vaan.clickTpa.common;

import me.vaan.clickTpa.common.requests.TeleportRegistry;

import java.io.File;

public interface ClickTpaPlugin {
    TeleportRegistry registry = new TeleportRegistry();

    ErrorHandler errorHandler();
    File getDataFolder();
}
