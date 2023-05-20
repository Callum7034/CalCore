package com.rcallum.CalCore.GUI;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface CButtonListener {
    void onClick(InventoryClickEvent event);
}
