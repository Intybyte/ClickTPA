package me.vaan.clickTpa.common;

import me.vaan.clickTpa.common.requests.TeleportRegistry;

import java.io.File;
import java.util.logging.Logger;

public interface ClickTpaPlugin {
    TeleportRegistry registry = new TeleportRegistry();

    Logger getLogger();
    File getDataFolder();
}
