package com.rcallum.CalCore.Utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Colour {
    public static String c(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
    public static List<String> c(List<String> in) {
        List<String> out = new ArrayList<>();
        for (String line : in) {
            out.add(c(line));
        }
        return out;
    }
}
