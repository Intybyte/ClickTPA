package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.vaan.clickTpa.common.file.Config;
import me.vaan.clickTpa.common.file.Message;
import org.spongepowered.api.command.CommandCause;

@CommandAlias("clicktpareload")
@CommandPermission("clicktpa.reload")
public class CommandReload extends BaseCommand {

    @Default
    public static void onExecute(CommandCause sender) {
        Config.getInstance().reloadConfig();
        Message.sendMessage(sender.audience(), "command.reload.reload_success");
    }
}
