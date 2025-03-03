package me.vaan.clickTpa.common.requests;

import me.vaan.clickTpa.common.ClickTpaPlugin;
import me.vaan.clickTpa.common.DefaultMap;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.enums.TeleportType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TeleportRegistry {
    public final Map<String, TeleportMode> modes = new DefaultMap<>(() -> TeleportMode.TP_ON);
    public final Map<String, Map<String, TeleportType>> tpaMap = new DefaultMap<>(HashMap::new);
    public final Set<String> listenMovement = new HashSet<>();

    public void remove(String p) {
        modes.remove(p);
        tpaMap.remove(p);
        listenMovement.remove(p);
    }

    public void removeTpRequest(String senderName, String targetName) {
        Map<String, TeleportType> tpList = ClickTpaPlugin.registry.tpaMap.get(senderName);
        tpList.remove(targetName);
    }
}
