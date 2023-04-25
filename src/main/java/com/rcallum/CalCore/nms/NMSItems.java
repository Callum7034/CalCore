package com.rcallum.CalCore.nms;

import org.bukkit.inventory.ItemStack;

public class NMSItems {
    public static ItemStack addString(ItemStack item, String key, String value) {
        return NBTEditor.set(item, value, key);
    }

    public static boolean hasKey(ItemStack item, String key) {
        return NBTEditor.contains(item, key);
    }

    public static String getValue(ItemStack item, String key) {
        return NBTEditor.getString(item, key);
    }
}
