package me.vaan.clickTpa.common.commands;

import me.vaan.clickTpa.common.ClickTpaPlugin;
import me.vaan.clickTpa.common.TpaUser;
import me.vaan.clickTpa.common.enums.TeleportMode;
import me.vaan.clickTpa.common.file.Message;

public interface CommonCommandTpToggle {
    default void runCommand(TpaUser user) {
        String name = user.name();
        TeleportMode mode = ClickTpaPlugin.registry.modes.get(name);

        switch (mode) {
            case TP_ON:
                ClickTpaPlugin.registry.modes.put(name, TeleportMode.TP_OFF);
                Message.sendMessage(user.audience(), "command.tptoggle.toggled_off");
                break;
            case TP_OFF:
                ClickTpaPlugin.registry.modes.put(name, TeleportMode.TP_ON);
                Message.sendMessage(user.audience(), "command.tptoggle.toggled_on");
                break;
            case TELEPORTING:
                Message.sendMessage(user.audience(), "command.tptoggle.toggled_error_teleporting");
                break;
        }
    }
}
