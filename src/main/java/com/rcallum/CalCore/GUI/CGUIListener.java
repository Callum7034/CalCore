package com.rcallum.CalCore.GUI;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class CGUIListener implements Listener {

    private GUIRegister register;
    public CGUIListener(GUIRegister register) {
        this.register = register;
    }

    private boolean shouldIgnoreClick(Inventory inventory) {
        return (inventory == null
                || inventory.getHolder() == null
                || !(inventory.getHolder() instanceof CGUI));
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        // Check to see if the clicked inventory is a CGUI
        if (shouldIgnoreClick(event.getClickedInventory())) return;

        CGUI clickedGUI = (CGUI) event.getClickedInventory().getHolder();

        // Return if another plugin has registered this GUI not this plugin
        if (clickedGUI.getOwner() != register.getPlugin()) return;

        // Gets the button that was clicked or null
        CButton clickedButton = clickedGUI.getButton(event.getSlot());

        // If its not a button it could be a filler object so cancel event and return
        if (clickedButton == null) {
            event.setCancelled(true);
            return;
        }

        // Check if the player is allowed to take the item
        event.setCancelled(!clickedButton.canInteract());

        if (!clickedButton.hasListener()) return;
        clickedButton.getListener().onClick(event);
    }
}
