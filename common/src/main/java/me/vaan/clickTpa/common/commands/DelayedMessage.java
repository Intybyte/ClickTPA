package me.vaan.clickTpa.common.commands;

import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportType;

import java.util.Map;

public interface DelayedMessage {
    void sendDelayMessage(Map<String, TeleportType> requests, TpaUser p, TpaUser target);
}
