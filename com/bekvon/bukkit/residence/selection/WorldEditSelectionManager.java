/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.bukkit.WorldEditPlugin
 *  com.sk89q.worldedit.bukkit.selections.CuboidSelection
 *  com.sk89q.worldedit.bukkit.selections.Selection
 *  com.sk89q.worldedit.regions.CuboidRegion
 *  com.sk89q.worldedit.regions.Region
 *  com.sk89q.worldedit.regions.RegionSelector
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package com.bekvon.bukkit.residence.selection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class WorldEditSelectionManager
extends SelectionManager {
    public WorldEditSelectionManager(Server serv, Residence plugin) {
        super(serv, plugin);
    }

    @Override
    public boolean worldEdit(Player player) {
        WorldEditPlugin wep = (WorldEditPlugin)this.server.getPluginManager().getPlugin("WorldEdit");
        Selection sel = wep.getSelection(player);
        if (sel != null) {
            Location pos1 = sel.getMinimumPoint();
            Location pos2 = sel.getMaximumPoint();
            try {
                CuboidRegion region = (CuboidRegion)sel.getRegionSelector().getRegion();
                pos1 = new Location(player.getWorld(), region.getPos1().getX(), region.getPos1().getY(), region.getPos1().getZ());
                pos2 = new Location(player.getWorld(), region.getPos2().getX(), region.getPos2().getY(), region.getPos2().getZ());
            }
            catch (Exception region) {
                // empty catch block
            }
            this.playerLoc1.put(player.getName(), pos1);
            this.playerLoc2.put(player.getName(), pos2);
            this.afterSelectionUpdate(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean worldEditUpdate(Player player) {
        if (!this.hasPlacedBoth(player.getName())) {
            return false;
        }
        CuboidSelection selection = new CuboidSelection(player.getWorld(), this.getPlayerLoc1(player.getName()), this.getPlayerLoc2(player.getName()));
        Residence.wep.setSelection(player, (Selection)selection);
        return true;
    }

    @Override
    public void placeLoc1(Player player, Location loc, boolean show2) {
        super.placeLoc1(player, loc, show2);
        this.worldEditUpdate(player);
    }

    @Override
    public void placeLoc2(Player player, Location loc, boolean show2) {
        super.placeLoc2(player, loc, show2);
        this.worldEditUpdate(player);
    }

    @Override
    public void sky(Player player, boolean resadmin2) {
        super.sky(player, resadmin2);
        this.worldEditUpdate(player);
        this.afterSelectionUpdate(player);
    }

    @Override
    public void bedrock(Player player, boolean resadmin2) {
        super.bedrock(player, resadmin2);
        this.worldEditUpdate(player);
        this.afterSelectionUpdate(player);
    }

    @Override
    public void modify(Player player, boolean shift, double amount) {
        super.modify(player, shift, amount);
        this.worldEditUpdate(player);
        this.afterSelectionUpdate(player);
    }

    @Override
    public void selectChunk(Player player) {
        super.selectChunk(player);
        this.worldEditUpdate(player);
        this.afterSelectionUpdate(player);
    }

    @Override
    public void showSelectionInfo(Player player) {
        super.showSelectionInfo(player);
        this.worldEditUpdate(player);
    }
}

