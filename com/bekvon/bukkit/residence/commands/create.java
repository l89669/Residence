/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.Visualizer;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.selection.WorldGuardUtil;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class create
implements cmd {
    @CommandAnnotation(simple=1, priority=100)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length != 2) {
            return false;
        }
        if (Residence.getWEplugin() != null && Residence.getWEplugin().getConfig().getInt("wand-item") == Residence.getConfigManager().getSelectionTooldID()) {
            Residence.getSelectionManager().worldEdit(player);
        }
        if (Residence.getSelectionManager().hasPlacedBoth(player.getName())) {
            if (Residence.wg != null) {
                if (WorldGuardUtil.isSelectionInRegion(player) == null) {
                    Residence.getResidenceManager().addResidence(player, args[1], Residence.getSelectionManager().getPlayerLoc1(player.getName()), Residence.getSelectionManager().getPlayerLoc2(player.getName()), resadmin2);
                    return true;
                }
                ProtectedRegion Region = WorldGuardUtil.isSelectionInRegion(player);
                Residence.msg((CommandSender)player, lm.Select_WorldGuardOverlap, Region.getId());
                Location lowLoc = new Location(Residence.getSelectionManager().getPlayerLoc1(player.getName()).getWorld(), (double)Region.getMinimumPoint().getBlockX(), (double)Region.getMinimumPoint().getBlockY(), (double)Region.getMinimumPoint().getBlockZ());
                Location highLoc = new Location(Residence.getSelectionManager().getPlayerLoc1(player.getName()).getWorld(), (double)Region.getMaximumPoint().getBlockX(), (double)Region.getMaximumPoint().getBlockY(), (double)Region.getMaximumPoint().getBlockZ());
                Visualizer v = new Visualizer(player);
                v.setAreas(Residence.getSelectionManager().getSelectionCuboid(player));
                v.setErrorAreas(new CuboidArea(lowLoc, highLoc));
                Residence.getSelectionManager().showBounds(player, v);
                return true;
            }
            Residence.getResidenceManager().addResidence(player, args[1], Residence.getSelectionManager().getPlayerLoc1(player.getName()), Residence.getSelectionManager().getPlayerLoc2(player.getName()), resadmin2);
            return true;
        }
        Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Create Residences");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res create <residence name>"));
    }
}

