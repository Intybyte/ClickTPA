package me.vaan.clickTpa;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("clicktpasponge8")
public class ClickTpa {

    private final PluginContainer container;
    private final Logger logger;

    @Inject
    ClickTpa(final PluginContainer container, Logger logger) {
        this.container = container;
        this.logger = logger;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        // Perform any one-time setup
        this.logger.info("Constructing clicktpa-sponge-8");
    }
}
