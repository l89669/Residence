/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.platymuus.bukkit.permissions.Group
 *  com.platymuus.bukkit.permissions.PermissionsPlugin
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.permissions;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionManager;
import com.bekvon.bukkit.residence.permissions.PermissionsInterface;
import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import java.util.List;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PermissionsBukkitAdapter
implements PermissionsInterface {
    PermissionsPlugin newperms;

    public PermissionsBukkitAdapter(PermissionsPlugin p) {
        this.newperms = p;
    }

    @Override
    public String getPlayerGroup(Player player) {
        return this.getPlayerGroup(player.getName(), player.getWorld().getName());
    }

    @Override
    public String getPlayerGroup(String player, String world) {
        PermissionManager pmanager = Residence.getPermissionManager();
        List groups = this.newperms.getGroups(player);
        for (Group group : groups) {
            String name = group.getName().toLowerCase();
            if (!pmanager.hasGroup(name)) continue;
            return name;
        }
        if (groups.size() > 0) {
            return ((Group)groups.get(0)).getName().toLowerCase();
        }
        return null;
    }
}

