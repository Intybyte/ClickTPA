package me.vaan.clickTpa.common;

import me.vaan.clickTpa.common.requests.TeleportRegistry;

import java.io.File;

public interface ClickTpaPlugin {
    TeleportRegistry registry = new TeleportRegistry();

    File dataFolder();
    void loadDefaultConfig();

    default File configFile() {
        return new File(dataFolder(), "config.yml");
    }
}
