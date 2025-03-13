package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.vaan.clickTpa.common.file.Config;
import me.vaan.clickTpa.common.file.Message;
import org.bukkit.command.CommandSender;

@CommandAlias("clicktpareload")
@CommandPermission("clicktpa.reload")
public class CommandReload extends BaseCommand {

    @Default
    public static void onExecute(CommandSender sender) {
        Config.getInstance().reloadConfig();
        Message.sendMessage(sender, "command.reload.reload_success");
    }
}
