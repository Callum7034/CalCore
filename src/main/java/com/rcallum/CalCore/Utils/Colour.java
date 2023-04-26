package com.rcallum.CalCore.Utils;

import org.bukkit.ChatColor;

public class Colour {
    public static String c(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
