package com.rcallum.CalCore.GUI;

import com.rcallum.CalCore.Utils.Colour;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CButton {
    private CButtonListener listener = null;
    private ItemStack item;
    private boolean canInteract = false;

    public CButton(ItemStack item) {
        this.item = item;
        listener = null;
    }

    public CButton(Material material) {
        this.item = new ItemStack(material);
        listener = null;
    }

    public CButton setName(String name) {
        ItemMeta im = getItemMeta();
        im.setDisplayName(Colour.c(name));
        setItemMeta(im);
        return this;
    }

    public CButton setAmount(int amount) {
        if (amount > 64 || amount < 1) amount = 1;
        item.setAmount(amount);
        return this;
    }

    public CButton setCanInteract(boolean canInteract) {
        this.canInteract = canInteract;
        return this;
    }

    public CButton addListener(CButtonListener listener) {
        this.listener = listener;
        return this;
    }

    public CButton setListener(CButtonListener listener) {
        addListener(listener);
        return this;
    }

    public CButton setLore(List<String> newLore) {
        List<String> lore = new ArrayList<>();
        newLore.forEach(s -> lore.add(Colour.c(s)));
        ItemMeta im = getItemMeta();
        im.setLore(lore);
        setItemMeta(im);
        return this;
    }

    public CButton addLore(String loreLine) {
        ItemMeta im = getItemMeta();
        List<String> lore = im.getLore();
        lore.add(Colour.c(loreLine));
        im.setLore(lore);
        setItemMeta(im);
        return this;
    }

    public CButton resetLore() {
        ItemMeta im = getItemMeta();
        im.setLore(new ArrayList<>());
        setItemMeta(im);
        return this;
    }

    private void setItemMeta(ItemMeta itemMeta) {
        item.setItemMeta(itemMeta);
    }

    private ItemMeta getItemMeta() {
        return item.getItemMeta();
    }

    public ItemStack getItem() {
        return item;
    }

    public CButtonListener getListener() {
        return listener;
    }

    public boolean canInteract() {
        return canInteract;
    }

    public boolean hasListener() {
        return (listener != null);
    }
}
