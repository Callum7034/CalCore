package com.rcallum.CalCore.Utils;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NameUtil {
    public static String getName(ItemStack item) {
        if (item.getItemMeta().hasDisplayName()) return item.getItemMeta().getDisplayName();
        return getName(item.getType());
    }

    public static String getName(Material material) {
        String name = material.toString();
        name = name.replaceAll("_", " ");
        return WordUtils.capitalizeFully(name);
    }

    public static String getName(String name) {
        name = name.replaceAll("_", " ");
        return WordUtils.capitalizeFully(name);
    }

}
