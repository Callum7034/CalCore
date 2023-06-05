package com.rcallum.CalCore.GUI;

import com.rcallum.CalCore.Utils.Colour;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class CGUI implements InventoryHolder {

    private JavaPlugin owner;
    private String guiName;
    private int rowsPerPage;
    private HashMap<Integer, CButton> items;
    private int currentPage;
    private ItemStack fillerItem;

    public CGUI(JavaPlugin owner, String name, int rowsPerPage) {
        this.owner = owner;
        this.guiName = Colour.c(name);
        this.rowsPerPage = rowsPerPage;
        this.items = new HashMap<>();

        this.currentPage = 0;
    }

    public void setItem(int slot, ItemStack item) {
        CButton button = new CButton(item, UUID.randomUUID().toString());
        addButton(slot, button);
    }

    public void setFillerItem(Material material) {
        ItemStack fillerItemStack = new ItemStack(material);
        ItemMeta im = fillerItemStack.getItemMeta();
        im.setDisplayName(" ");
        fillerItemStack.setItemMeta(im);
        fillerItem = fillerItemStack;
    }

    public void setFillerItem(ItemStack item) {
        fillerItem = item;
    }

    public void addButton(int slot, CButton button) {
        items.put(slot, button);
    }

    public int getGUISize() {
        return rowsPerPage * 9;
    }

    public ItemStack getFillerItem() {
        return fillerItem;
    }

    private int getKey(int page) {
        return page * getGUISize();
    }

    private int getKeySlot(int key, int page) {
        return key - getKey(page);
    }

    public JavaPlugin getOwner() {
        return owner;
    }

    public CButton getButton(int slot) {
        int realSlot = slot + (currentPage * getGUISize());
        if (!items.containsKey(realSlot)) return null;
        return items.get(realSlot);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, getGUISize(), guiName);

        // Fill the inventory with filler object
        for (int i = 0; i < getGUISize(); i++) {
            inventory.setItem(i, getFillerItem());
        }


        // Fill the inventory with buttons
        for (int key = getKey(currentPage); key < getKey(currentPage + 1); key ++) {
            if (!items.containsKey(key)) continue;
            inventory.setItem(getKeySlot(key, currentPage), items.get(key).getItem());
        }

        return inventory;
    }
}
