package me.vaan.clickTpa.adapter;

import org.bukkit.entity.Player;

public class PaperAdapter {

    public static PaperTpaUser adapt(Player sender) {
        if (sender == null) {
            return null;
        }

        return new PaperTpaUser(sender);
    }
}
