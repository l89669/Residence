/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package com.bekvon.bukkit.residence.gui;

import java.util.HashMap;
import org.bukkit.inventory.ItemStack;

public class FlagData {
    private HashMap<String, ItemStack> items = new HashMap();

    public void addFlagButton(String flag, ItemStack item) {
        this.items.put(flag, item);
    }

    public void removeFlagButton(String flag) {
        this.items.remove(flag);
    }

    public boolean contains(String flag) {
        return this.items.containsKey(flag);
    }

    public ItemStack getItem(String flag) {
        return this.items.get(flag);
    }
}

