package me.vaan.clickTpa.common.file;

import me.vaan.clickTpa.common.NamedAudience;
import me.vaan.clickTpa.common.enums.TeleportType;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class Message {
    private static final Duration TICK = Duration.ofMillis(50);

    public static void sendMessage(Audience sender, String key) {
        var cs = Config.getInstance();
        var prefix = cs.getComponent("messages.prefix");

        sender.sendMessage(prefix.append(cs.getComponent("messages." + key)));
    }

    public static void sendMessage(Audience player, String key, String target) {
        var cs = Config.getInstance();
        var prefix = cs.getComponent("messages.prefix");

        TagResolver targetResolver = TagResolver.resolver("target", Tag.inserting(Component.text(target)));
        TagResolver total = TagResolver.resolver(TagResolver.standard(), targetResolver);

        MiniMessage mm = MiniMessage.builder().tags(total).build();

        Component componentMessage = mm.deserialize(cs.getString("messages." + key));
        player.sendMessage(prefix.append(componentMessage));
    }

    public static void sendTpRequest(TeleportType type, NamedAudience player, NamedAudience target) {
        var cs = Config.getInstance();

        Component acceptHoverText = cs.getComponent("messages.hover.accept");
        Component accept = cs.getComponent("messages.click.accept")
                .hoverEvent(HoverEvent.showText(acceptHoverText))
                .clickEvent(ClickEvent.runCommand("/tpaccept " + player.name()));

        Component denyHoverText = cs.getComponent("messages.hover.deny");
        Component deny = cs.getComponent("messages.click.deny")
                .hoverEvent(HoverEvent.showText(denyHoverText))
                .clickEvent(ClickEvent.runCommand("/tpdeny " + player.name()));

        List<String> tpa = switch (type) {
            case TPA -> cs.getSList("messages.command.tpa.message");
            case TPAHERE -> cs.getSList("messages.command.tpahere.message");
        };

        int seconds = cs.getInt("expire-request");
        TagResolver senderResolver = TagResolver.resolver("sender", Tag.inserting(Component.text(player.name())));
        TagResolver acceptResolver = TagResolver.resolver("accept", Tag.selfClosingInserting(accept));
        TagResolver denyResolver = TagResolver.resolver("deny", Tag.selfClosingInserting(deny));
        TagResolver secondResolver = TagResolver.resolver("expire", Tag.inserting(Component.text(seconds)));

        TagResolver resolver = TagResolver.resolver(TagResolver.standard(), senderResolver, acceptResolver, denyResolver, secondResolver);
        MiniMessage mm = MiniMessage.builder().tags(resolver).build();

        for (String line : tpa) {
            Component replaced = mm.deserialize(line);
            target.audience().sendMessage(replaced);
        }

        sendMessage(player.audience(), "generic.request_sent", target.name());
    }

    public static void sendMoved(NamedAudience player, NamedAudience target, TeleportType type) {
        switch (type) {
            case TPA -> {
                sendTitle(target.audience(), "titles.cancelled_tp");
                sendMessage(target.audience(), "error.player_moved", target.name());
                sendMessage(player.audience(), "error.player_moved", target.name());
            }
            case TPAHERE -> {
                sendTitle(player.audience(), "titles.cancelled_tp");
                sendMessage(target.audience(), "error.player_moved", player.name());
                sendMessage(player.audience(), "error.player_moved", player.name());
            }
        }
    }

    public static void sendTitle(Audience player, String key) {
        var cs = Config.getInstance();
        var mm = MiniMessage.miniMessage();

        List<Component> cmp = new LinkedList<>();
        for (String line : cs.getSList("messages." + key)) {
            cmp.add(mm.deserialize(line));
        }

        if (cmp.isEmpty()) {
            return;
        }

        int fadeIn = cs.getInt("titles.fade_in");
        int stayDuration = cs.getInt("titles.stay_duration");
        int fadeOut = cs.getInt("titles.fade_out");

        var times = Title.Times.times(TICK.multipliedBy(fadeIn), TICK.multipliedBy(stayDuration), TICK.multipliedBy(fadeOut));
        Title title;
        if (cmp.size() == 1) {
            title = Title.title(cmp.getFirst(), Component.empty(), times);
        } else {
            title = Title.title(cmp.getFirst(), cmp.get(1), times);
        }

        player.showTitle(title);
    }
}
