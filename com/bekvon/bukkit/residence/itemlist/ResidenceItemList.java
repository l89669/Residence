/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.itemlist;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.itemlist.ItemList;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResidenceItemList
extends ItemList {
    ClaimedResidence res;

    public ResidenceItemList(ClaimedResidence parent, ItemList.ListType type) {
        super(type);
        this.res = parent;
    }

    private ResidenceItemList() {
    }

    public void playerListChange(Player player, Material mat, boolean resadmin2) {
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (resadmin2 || this.res.getPermissions().hasResidencePermission((CommandSender)player, true) && group.itemListAccess()) {
            if (super.toggle(mat)) {
                Residence.msg((CommandSender)player, lm.General_ListMaterialAdd, mat.toString(), this.type.toString().toLowerCase());
            } else {
                Residence.msg((CommandSender)player, lm.General_ListMaterialRemove, mat.toString(), this.type.toString().toLowerCase());
            }
        } else {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
        }
    }

    public static ResidenceItemList load(ClaimedResidence parent, Map<String, Object> map) {
        ResidenceItemList newlist = new ResidenceItemList();
        newlist.res = parent;
        return (ResidenceItemList)ItemList.load(map, newlist);
    }
}

