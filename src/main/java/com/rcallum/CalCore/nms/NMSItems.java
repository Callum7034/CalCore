package com.rcallum.CalCore.nms;

import org.bukkit.inventory.ItemStack;

public class NMSItems {
    public static ItemStack addString(ItemStack item, String key, String value) {
        return NBTEditor.set(item, value, key);
    }

    public static boolean hasKey(ItemStack item, String key) {
        return NBTEditor.contains(item, key);
    }

    public static String getString(ItemStack item, String key) {
        return NBTEditor.getString(item, key);
    }

    public static long getLong(ItemStack item, String key) {
        return NBTEditor.getLong(item, key);
    }

    public static ItemStack addLong(ItemStack item, String key, long value) {
        return NBTEditor.set(item, value, key);
    }
}
