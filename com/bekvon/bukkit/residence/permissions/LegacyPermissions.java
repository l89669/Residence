/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.nijiko.permissions.PermissionHandler
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.permissions;

import com.bekvon.bukkit.residence.permissions.PermissionsInterface;
import com.nijiko.permissions.PermissionHandler;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LegacyPermissions
implements PermissionsInterface {
    PermissionHandler authority;

    public LegacyPermissions(PermissionHandler perms) {
        this.authority = perms;
    }

    @Override
    public String getPlayerGroup(Player player) {
        return this.getPlayerGroup(player.getName(), player.getWorld().getName());
    }

    @Override
    public String getPlayerGroup(String player, String world) {
        String group = this.authority.getPrimaryGroup(world, player);
        if (group != null) {
            return group.toLowerCase();
        }
        return null;
    }
}

