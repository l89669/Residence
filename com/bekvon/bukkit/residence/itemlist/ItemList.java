/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.itemlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ItemList {
    protected List<Material> list = new ArrayList<Material>();
    protected ListType type;

    public ItemList(ListType listType) {
        this();
        this.type = listType;
    }

    protected ItemList() {
    }

    public ListType getType() {
        return this.type;
    }

    public boolean contains(Material mat) {
        return this.list.contains((Object)mat);
    }

    public void add(Material mat) {
        if (!this.list.contains((Object)mat)) {
            this.list.add(mat);
        }
    }

    public boolean toggle(Material mat) {
        if (this.list.contains((Object)mat)) {
            this.list.remove((Object)mat);
            return false;
        }
        this.list.add(mat);
        return true;
    }

    public void remove(Material mat) {
        this.list.remove((Object)mat);
    }

    public boolean isAllowed(Material mat) {
        if (this.type == ListType.BLACKLIST) {
            if (this.list.contains((Object)mat)) {
                return false;
            }
            return true;
        }
        if (this.type == ListType.WHITELIST) {
            if (this.list.contains((Object)mat)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean isIgnored(Material mat) {
        if (this.type == ListType.IGNORELIST && this.list.contains((Object)mat)) {
            return true;
        }
        return false;
    }

    public boolean isListed(Material mat) {
        return this.contains(mat);
    }

    public int getListSize() {
        return this.list.size();
    }

    public static ItemList readList(ConfigurationSection node) {
        return ItemList.readList(node, new ItemList());
    }

    protected static ItemList readList(ConfigurationSection node, ItemList list2) {
        ListType type;
        list2.type = type = ListType.valueOf(node.getString("Type", "").toUpperCase());
        List items = node.getStringList("Items");
        if (items != null) {
            for (String item : items) {
                int parse = -1;
                try {
                    parse = Integer.parseInt(item);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (parse == -1) {
                    try {
                        list2.add(Material.valueOf((String)item.toUpperCase()));
                    }
                    catch (Exception exception) {}
                    continue;
                }
                try {
                    list2.add(Material.getMaterial((int)parse));
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return list2;
    }

    public void printList(Player player) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Material mat : this.list) {
            if (!first) {
                builder.append(", ");
            } else {
                builder.append((Object)ChatColor.YELLOW);
            }
            builder.append((Object)mat);
            first = false;
        }
        player.sendMessage(builder.toString());
    }

    public Material[] toArray() {
        Material[] mats = new Material[this.list.size()];
        int i = 0;
        Iterator<Material> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            Material mat;
            mats[i] = mat = iterator.next();
            ++i;
        }
        return mats;
    }

    public Map<String, Object> save() {
        LinkedHashMap<String, Object> saveMap = new LinkedHashMap<String, Object>();
        if (this.list.isEmpty()) {
            return saveMap;
        }
        saveMap.put("Type", this.type.toString());
        ArrayList<String> saveList = new ArrayList<String>();
        for (Material mat : this.list) {
            saveList.add(mat.toString());
        }
        saveMap.put("ItemList", saveList);
        return saveMap;
    }

    public static ItemList load(Map<String, Object> map) {
        ItemList newlist = new ItemList();
        return ItemList.load(map, newlist);
    }

    protected static ItemList load(Map<String, Object> map, ItemList newlist) {
        try {
            newlist.type = ListType.valueOf((String)map.get("Type"));
            List list2 = (List)map.get("ItemList");
            for (String item : list2) {
                newlist.add(Material.valueOf((String)item));
            }
        }
        catch (Exception list2) {
            // empty catch block
        }
        return newlist;
    }

    public static enum ListType {
        BLACKLIST,
        WHITELIST,
        IGNORELIST,
        OTHER;
        

        private ListType(String string2, int n2) {
        }
    }

}

