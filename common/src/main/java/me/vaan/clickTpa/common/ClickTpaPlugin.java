package me.vaan.clickTpa.common;

import java.io.File;
import java.util.logging.Logger;

public interface ClickTpaPlugin {

    Logger getLogger();
    File getDataFolder();
}
