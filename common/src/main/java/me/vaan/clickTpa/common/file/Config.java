package me.vaan.clickTpa.common.file;

import lombok.Getter;
import me.vaan.clickTpa.common.ClickTpaPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    @Getter
    private static Config instance = null;
    private static ClickTpaPlugin plugin;

    private final Map<String, Object> configMap = new HashMap<>();
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static void init(ClickTpaPlugin plugin) {
        Config.plugin = plugin;
        plugin.loadDefaultConfig();
        instance = new Config(plugin.configFile());
    }

    private Config(File config) {
        try (FileInputStream fis = new FileInputStream(config)) {
            Yaml yaml = new Yaml();

            Map<String, Object> map = yaml.load(fis);
            deepLoadKeys("", map);
        } catch (IOException e) {
            throw new RuntimeException("Error loading config file: " + e.getMessage());
        }
    }

    // Method to reload the configuration
    public void reloadConfig() {
        try (FileInputStream fis = new FileInputStream(plugin.configFile())) {
            Yaml yaml = new Yaml();
            configMap.clear();  // Clear current map and reload new data

            Map<String, Object> map = yaml.load(fis);
            deepLoadKeys("", map);

        } catch (IOException e) {
            throw new RuntimeException("Error reloading config file: " + e.getMessage());
        }
    }

    public Object get(String key) {
        Object value = configMap.get(key);
        if (value == null) {
            throw new RuntimeException("Error: " + key + " not found.");
        }

        return value;
    }

    // Utility methods for fetching values from the map
    public String getString(String key) {
        return (String) get(key);
    }

    public List<String> getSList(String key) {
        return (List<String>) get(key);
    }

    public int getInt(String key) {
        return (Integer) get(key);
    }

    public boolean getBool(String key) {
        return (Boolean) get(key);
    }

    public Component getComponent(String key) {
        return mm.deserialize(getString(key));
    }

    // Recursive method to deep load all keys
    private void deepLoadKeys(String prefix, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();  // Create a "dot" notation for keys
            Object value = entry.getValue();

            if (value instanceof Map) {
                deepLoadKeys(key, (Map<String, Object>) value);
            } else {
                configMap.put(key, value);
            }
        }
    }
}