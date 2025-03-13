package me.vaan.clickTpa;

import lombok.Getter;
import me.vaan.clickTpa.commands.ClickTpaCommandManager;
import me.vaan.clickTpa.common.ClickTpaPlugin;
import me.vaan.clickTpa.common.file.Config;
import me.vaan.clickTpa.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ClickTpa extends JavaPlugin implements ClickTpaPlugin {
    @Getter
    private static ClickTpa instance = null;

    @Override
    public void onEnable() {
        instance = this;
        Config.init(this);

        new ClickTpaCommandManager(this).registerCommands();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public File dataFolder() {
        return getDataFolder();
    }

    @Override
    public void loadDefaultConfig() {
        saveDefaultConfig();
    }
}
