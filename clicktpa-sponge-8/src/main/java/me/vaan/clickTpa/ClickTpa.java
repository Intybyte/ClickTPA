package me.vaan.clickTpa;

import com.google.inject.Inject;
import me.vaan.clickTpa.common.ClickTpaPlugin;
import me.vaan.clickTpa.common.file.Config;
import me.vaan.clickTpa.listener.PlayerListener;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Plugin("clicktpasponge8")
public class ClickTpa implements ClickTpaPlugin {

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;
    private Path configFile = Paths.get(configDir + "/config.yml");

    private static PluginContainer container;
    private static Logger logger;

    @Inject
    ClickTpa(final PluginContainer container, Logger logger) {
        ClickTpa.container = container;
        ClickTpa.logger = logger;
    }

    @Listener
    public void loaded(final LoadedGameEvent event) {
        // Perform any one-time setup
        Config.init(this);
        logger.info("Constructing clicktpa-sponge-8");
        Sponge.eventManager().registerListeners(container, new PlayerListener());
    }

    public static Logger logger() {
        return logger;
    }

    @Override
    public File dataFolder() {
        return configDir.toFile();
    }

    @Override
    public void loadDefaultConfig() {
        if (!configFile.toFile().exists()) {
            saveResource("config.yml");
        }
    }

    // all the code under here is yoinked and modified bukkit code
    public void saveResource(@NotNull String resourcePath) {
        if (resourcePath.isEmpty()) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = this.getResourceStream(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");
        }

        File outFile = new File(this.dataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf(47);
        File outDir = new File(this.dataFolder(), resourcePath.substring(0, Math.max(lastIndex, 0)));
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (outFile.exists()) {
                String fileName = outFile.getName();
                logger.error("Could not save {} to {} because {} already exists.", fileName, outFile, outFile.getName());
                return;
            }

            try (OutputStream out = Files.newOutputStream(outFile.toPath())) {
                byte[] buf = new byte[1024];

                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            in.close();
        } catch (IOException ioException) {
            logger.error("Could not save {} to {}", outFile.getName(), String.valueOf(outFile), ioException);
        }
    }

    public @Nullable InputStream getResourceStream(@NotNull String filename) {
        try {
            URL url = this.getClass().getClassLoader().getResource(filename);
            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException var4) {
            return null;
        }
    }
}
