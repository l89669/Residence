/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 */
package com.bekvon.bukkit.residence.itemlist;

import com.bekvon.bukkit.residence.itemlist.ItemList;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class WorldItemList
extends ItemList {
    protected String world;
    protected String group;

    public WorldItemList(ItemList.ListType listType) {
        super(listType);
    }

    protected WorldItemList() {
    }

    public String getWorld() {
        return this.world;
    }

    public String getGroup() {
        return this.group;
    }

    public boolean isAllowed(Material mat, String inworld, String ingroup) {
        if (!this.listApplicable(inworld, ingroup)) {
            return true;
        }
        return super.isAllowed(mat);
    }

    public boolean isIgnored(Material mat, String inworld, String ingroup) {
        if (!this.listApplicable(inworld, ingroup)) {
            return false;
        }
        return super.isIgnored(mat);
    }

    public boolean isListed(Material mat, String inworld, String ingroup) {
        if (!this.listApplicable(inworld, ingroup)) {
            return false;
        }
        return super.isListed(mat);
    }

    public boolean listApplicable(String inworld, String ingroup) {
        if (this.world != null && !this.world.equals(inworld)) {
            return false;
        }
        if (this.group != null && !this.group.equals(ingroup)) {
            return false;
        }
        return true;
    }

    public static WorldItemList readList(ConfigurationSection node) {
        WorldItemList list2 = new WorldItemList();
        ItemList.readList(node, list2);
        list2.world = node.getString("World", null);
        list2.group = node.getString("Group", null);
        return list2;
    }
}

