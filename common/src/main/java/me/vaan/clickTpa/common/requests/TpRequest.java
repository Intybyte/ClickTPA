package me.vaan.clickTpa.common.requests;

import me.vaan.clickTpa.common.enums.TeleportType;

import java.util.UUID;

public record TpRequest(UUID target, TeleportType type) {
}
