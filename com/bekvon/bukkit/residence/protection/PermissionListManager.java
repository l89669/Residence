/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionListManager {
    private final Map<String, Map<String, FlagPermissions>> lists = Collections.synchronizedMap(new HashMap());

    public FlagPermissions getList(String player, String listname) {
        Map<String, FlagPermissions> get = this.lists.get(player);
        if (get == null) {
            return null;
        }
        return get.get(listname);
    }

    public void makeList(Player player, String listname) {
        FlagPermissions perms;
        Map<String, FlagPermissions> get = this.lists.get(player.getName());
        if (get == null) {
            get = new HashMap<String, FlagPermissions>();
            this.lists.put(player.getName(), get);
        }
        if ((perms = get.get(listname)) == null) {
            perms = new FlagPermissions();
            get.put(listname, perms);
            Residence.msg((CommandSender)player, lm.General_ListCreate, listname);
        } else {
            Residence.msg((CommandSender)player, lm.General_ListExists, new Object[0]);
        }
    }

    public void removeList(Player player, String listname) {
        Map<String, FlagPermissions> get = this.lists.get(player.getName());
        if (get == null) {
            Residence.msg((CommandSender)player, lm.Invalid_List, new Object[0]);
            return;
        }
        FlagPermissions list2 = get.get(listname);
        if (list2 == null) {
            Residence.msg((CommandSender)player, lm.Invalid_List, new Object[0]);
            return;
        }
        get.remove(listname);
        Residence.msg((CommandSender)player, lm.General_ListRemoved, new Object[0]);
    }

    public void applyListToResidence(Player player, String listname, String areaname, boolean resadmin2) {
        FlagPermissions list2 = this.getList(player.getName(), listname);
        if (list2 == null) {
            Residence.msg((CommandSender)player, lm.Invalid_List, new Object[0]);
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        res.getPermissions().applyTemplate(player, list2, resadmin2);
    }

    public void printList(Player player, String listname) {
        FlagPermissions list2 = this.getList(player.getName(), listname);
        if (list2 == null) {
            Residence.msg((CommandSender)player, lm.Invalid_List, new Object[0]);
            return;
        }
        player.sendMessage((Object)ChatColor.LIGHT_PURPLE + "------Permission Template------");
        Residence.msg((CommandSender)player, lm.General_Name, listname);
        list2.printFlags(player);
    }

    public Map<String, Object> save() {
        LinkedHashMap<String, Object> root = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, Map<String, FlagPermissions>> players : this.lists.entrySet()) {
            LinkedHashMap<String, Map<String, Object>> saveMap = new LinkedHashMap<String, Map<String, Object>>();
            Map<String, FlagPermissions> map = players.getValue();
            for (Map.Entry<String, FlagPermissions> list2 : map.entrySet()) {
                saveMap.put(list2.getKey(), list2.getValue().save());
            }
            root.put(players.getKey(), saveMap);
        }
        return root;
    }

    public static PermissionListManager load(Map<String, Object> root) {
        PermissionListManager p = new PermissionListManager();
        if (root != null) {
            for (Map.Entry<String, Object> players : root.entrySet()) {
                try {
                    Map value = (Map)players.getValue();
                    Map<String, FlagPermissions> loadedMap = Collections.synchronizedMap(new HashMap());
                    for (Map.Entry list2 : value.entrySet()) {
                        loadedMap.put((String)list2.getKey(), FlagPermissions.load((Map)list2.getValue()));
                    }
                    p.lists.put(players.getKey(), loadedMap);
                    continue;
                }
                catch (Exception ex) {
                    System.out.println("[Residence] - Failed to load permission lists for player: " + players.getKey());
                }
            }
        }
        return p;
    }

    public void printLists(Player player) {
        StringBuilder sbuild = new StringBuilder();
        Map<String, FlagPermissions> get = this.lists.get(player.getName());
        sbuild.append(Residence.msg(lm.General_Lists, new Object[0]));
        if (get != null) {
            for (Map.Entry<String, FlagPermissions> thislist : get.entrySet()) {
                sbuild.append(thislist.getKey()).append(" ");
            }
        }
        player.sendMessage(sbuild.toString());
    }
}

