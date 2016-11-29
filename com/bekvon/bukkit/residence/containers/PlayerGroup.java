/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.containers;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.vaultinterface.ResidenceVaultAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerGroup {
    String playerName = null;
    Player player = null;
    long lastCheck = 0;
    HashMap<String, String> groups = new HashMap();

    public PlayerGroup(String playerName) {
        this.playerName = playerName;
        this.player = Bukkit.getPlayer((String)playerName);
    }

    public PlayerGroup(Player player) {
        this.playerName = player.getName();
        this.player = player;
    }

    public void setLastCkeck(Long time) {
        this.lastCheck = time;
    }

    public void addGroup(String world, String group) {
        this.groups.put(world, group);
    }

    public String getGroup(String world) {
        this.updateGroup(world, false);
        return this.groups.get(world);
    }

    public void updateGroup(String world, boolean force) {
        PermissionGroup g;
        String group;
        if (!force && this.lastCheck != 0 && System.currentTimeMillis() - this.lastCheck < 60000) {
            return;
        }
        this.lastCheck = System.currentTimeMillis();
        ArrayList<PermissionGroup> posibleGroups = new ArrayList<PermissionGroup>();
        if (Residence.getPermissionManager().getPlayersGroups().containsKey(this.playerName.toLowerCase()) && (group = Residence.getPermissionManager().getPlayersGroups().get(this.playerName.toLowerCase())) != null && (group = group.toLowerCase()) != null && Residence.getPermissionManager().getGroups().containsKey(group)) {
            g = Residence.getPermissionManager().getGroups().get(group);
            posibleGroups.add(g);
            this.groups.put(world, group);
        }
        posibleGroups.add(this.getPermissionGroup());
        group = Residence.getPermissionManager().getPermissionsGroup(this.playerName, world);
        g = Residence.getPermissionManager().getGroupByName(group);
        if (g != null) {
            posibleGroups.add(g);
        }
        PermissionGroup finalGroup = null;
        if (posibleGroups.size() == 1) {
            finalGroup = (PermissionGroup)posibleGroups.get(0);
        }
        int i = 0;
        while (i < posibleGroups.size()) {
            if (finalGroup == null) {
                finalGroup = (PermissionGroup)posibleGroups.get(i);
            } else if (finalGroup.getPriority() < ((PermissionGroup)posibleGroups.get(i)).getPriority()) {
                finalGroup = (PermissionGroup)posibleGroups.get(i);
            }
            ++i;
        }
        if (finalGroup == null || !Residence.getPermissionManager().getGroups().containsValue(finalGroup)) {
            this.groups.put(world, Residence.getConfigManager().getDefaultGroup().toLowerCase());
        } else {
            this.groups.put(world, finalGroup.getGroupName());
        }
    }

    private PermissionGroup getPermissionGroup() {
        if (this.player == null) {
            this.player = Bukkit.getPlayer((String)this.playerName);
        }
        PermissionGroup group = Residence.getPermissionManager().getGroupByName(Residence.getConfigManager().getDefaultGroup());
        for (Map.Entry<String, PermissionGroup> one : Residence.getPermissionManager().getGroups().entrySet()) {
            if (this.player != null) {
                if (!this.player.hasPermission("residence.group." + one.getKey())) continue;
                group = one.getValue();
                continue;
            }
            OfflinePlayer offlineP = Residence.getOfflinePlayer(this.playerName);
            if (offlineP == null || !ResidenceVaultAdapter.hasPermission(offlineP, "residence.group." + one.getKey(), Residence.getConfigManager().getDefaultWorld())) continue;
            group = one.getValue();
        }
        return group;
    }
}

