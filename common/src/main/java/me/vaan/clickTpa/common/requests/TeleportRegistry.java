package me.vaan.clickTpa.common.requests;

import me.vaan.clickTpa.common.DefaultMap;
import me.vaan.clickTpa.common.enums.TeleportMode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TeleportRegistry {
    public final Map<String, TeleportMode> modes = new DefaultMap<>(() -> TeleportMode.TP_ON);
    public final Map<String, List<TpRequest>> tpaListMap = new DefaultMap<>(LinkedList::new);
    public final Set<String> listenMovement = new HashSet<>();

    public void remove(String p) {
        modes.remove(p);
        tpaListMap.remove(p);
        listenMovement.remove(p);
    }
}
