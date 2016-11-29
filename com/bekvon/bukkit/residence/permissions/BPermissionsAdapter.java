/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  de.bananaco.bpermissions.api.ApiLayer
 *  de.bananaco.bpermissions.api.util.CalculableType
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.permissions;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionManager;
import com.bekvon.bukkit.residence.permissions.PermissionsInterface;
import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BPermissionsAdapter
implements PermissionsInterface {
    @Override
    public String getPlayerGroup(Player player) {
        return this.getPlayerGroup(player.getName(), player.getWorld().getName());
    }

    @Override
    public String getPlayerGroup(String player, String world) {
        String[] groups = ApiLayer.getGroups((String)world, (CalculableType)CalculableType.USER, (String)player);
        PermissionManager pmanager = Residence.getPermissionManager();
        String[] arrstring = groups;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String group = arrstring[n2];
            if (pmanager.hasGroup(group)) {
                return group.toLowerCase();
            }
            ++n2;
        }
        if (groups.length > 0) {
            return groups[0].toLowerCase();
        }
        return null;
    }
}

