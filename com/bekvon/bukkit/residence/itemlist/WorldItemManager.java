/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package com.bekvon.bukkit.residence.itemlist;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.itemlist.WorldItemList;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class WorldItemManager {
    protected List<WorldItemList> lists = new ArrayList<WorldItemList>();

    public WorldItemManager() {
        this.readLists();
    }

    public boolean isAllowed(Material mat, String group, String world) {
        for (WorldItemList list2 : this.lists) {
            if (list2.isAllowed(mat, world, group)) continue;
            return false;
        }
        return true;
    }

    public boolean isIgnored(Material mat, String group, String world) {
        for (WorldItemList list2 : this.lists) {
            if (!list2.isIgnored(mat, world, group)) continue;
            return true;
        }
        return false;
    }

    private void readLists() {
        YamlConfiguration flags2 = YamlConfiguration.loadConfiguration((File)new File(Residence.dataFolder, "flags.yml"));
        Set keys = flags2.getConfigurationSection("ItemList").getKeys(false);
        if (keys != null) {
            for (String key : keys) {
                try {
                    WorldItemList list2 = WorldItemList.readList(flags2.getConfigurationSection("ItemList." + key));
                    this.lists.add(list2);
                    continue;
                }
                catch (Exception ex) {
                    System.out.println("Failed to load item list:" + key);
                }
            }
        }
    }
}

