/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.selection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.AutoSelector;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoSelection {
    private HashMap<String, AutoSelector> list = new HashMap();

    public void switchAutoSelection(Player player) {
        if (!this.list.containsKey(player.getName().toLowerCase())) {
            ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
            PermissionGroup group = rPlayer.getGroup(player.getWorld().getName());
            this.list.put(player.getName().toLowerCase(), new AutoSelector(group, System.currentTimeMillis()));
            Residence.msg((CommandSender)player, lm.Select_AutoEnabled, new Object[0]);
        } else {
            this.list.remove(player.getName().toLowerCase());
            Residence.msg((CommandSender)player, lm.Select_AutoDisabled, new Object[0]);
        }
    }

    public void UpdateSelection(Player player) {
        if (!this.getList().containsKey(player.getName().toLowerCase())) {
            return;
        }
        AutoSelector AutoSelector2 = this.getList().get(player.getName().toLowerCase());
        int Curenttime = (int)(System.currentTimeMillis() - AutoSelector2.getTime()) / 1000;
        if (Curenttime > 270) {
            this.list.remove(player.getName().toLowerCase());
            Residence.msg((CommandSender)player, lm.Select_AutoDisabled, new Object[0]);
            return;
        }
        String name = player.getName();
        Location cloc = player.getLocation();
        Location loc1 = Residence.getSelectionManager().getPlayerLoc1(name);
        Location loc2 = Residence.getSelectionManager().getPlayerLoc2(name);
        if (loc1 == null) {
            Residence.getSelectionManager().placeLoc1(player, cloc, false);
            loc1 = player.getLocation();
            return;
        }
        if (loc2 == null) {
            Residence.getSelectionManager().placeLoc2(player, cloc, true);
            loc2 = player.getLocation();
            return;
        }
        boolean changed = false;
        CuboidArea area2 = new CuboidArea(loc1, loc2);
        Location hloc = area2.getHighLoc();
        Location lloc = area2.getLowLoc();
        if (cloc.getBlockX() < lloc.getBlockX()) {
            lloc.setX((double)cloc.getBlockX());
            changed = true;
        }
        if (cloc.getBlockY() <= lloc.getBlockY()) {
            lloc.setY((double)(cloc.getBlockY() - 1));
            changed = true;
        }
        if (cloc.getBlockZ() < lloc.getBlockZ()) {
            lloc.setZ((double)cloc.getBlockZ());
            changed = true;
        }
        if (cloc.getBlockX() > hloc.getBlockX()) {
            hloc.setX((double)cloc.getBlockX());
            changed = true;
        }
        if (cloc.getBlockY() >= hloc.getBlockY()) {
            hloc.setY((double)(cloc.getBlockY() + 1));
            changed = true;
        }
        if (cloc.getBlockZ() > hloc.getBlockZ()) {
            hloc.setZ((double)cloc.getBlockZ());
            changed = true;
        }
        PermissionGroup group = AutoSelector2.getGroup();
        if (area2.getXSize() > group.getMaxX()) {
            return;
        }
        if (area2.getYSize() > group.getMaxY() && !Residence.getConfigManager().isSelectionIgnoreY()) {
            return;
        }
        if (area2.getZSize() > group.getMaxZ()) {
            return;
        }
        if (changed) {
            Residence.getSelectionManager().placeLoc1(player, hloc, false);
            Residence.getSelectionManager().placeLoc2(player, lloc, true);
            Residence.getSelectionManager().showSelectionInfoInActionBar(player);
        }
    }

    public HashMap<String, AutoSelector> getList() {
        return this.list;
    }
}

