package me.vaan.clickTpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.vaan.clickTpa.ClickTpa;
import me.vaan.clickTpa.common.file.Message;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.math.vector.Vector3d;

import java.util.concurrent.Executor;

@CommandAlias("tpo")
@CommandPermission("clicktpa.tpo")
public class CommandTpo extends BaseCommand {
    private static final Executor executor = Sponge.server().scheduler().executor(ClickTpa.getInstance().container());

    @Default
    @Syntax("<playerName>")
    @CommandCompletion("@players")
    public void onExecute(ServerPlayer p, String targetName) {
        final Server server = Sponge.server();
        server.userManager().load(targetName).thenAcceptAsync( (optUser) -> {
            if (!optUser.isPresent()) {
                Message.sendMessage(p, "error.no_player");
                return;
            }

            User user = optUser.get();
            if (user.uniqueId().equals(p.uniqueId())) {
                Message.sendMessage(p, "error.teleport_self_fail");
                return;
            }


            server.worldManager().loadWorld(user.worldKey()).thenAcceptAsync((serverWorld) -> {
                Message.sendMessage(p, "command.tpo.teleport_success", user.name());

                ServerLocation loc = ServerLocation.of(serverWorld, user.position());
                Vector3d rotation = user.rotation();
                p.setLocation(loc);
                p.setRotation(rotation);
            }, executor);
        }, executor);
    }
}
