/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class auto
implements cmd {
    @CommandAnnotation(simple=1, priority=150)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length != 2 && args.length != 1 && args.length != 3) {
            return false;
        }
        String resName = null;
        resName = args.length > 1 ? args[1] : player.getName();
        int lenght = -1;
        if (args.length == 3) {
            try {
                lenght = Integer.parseInt(args[2]);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        Location loc = player.getLocation();
        int X = group.getMaxX();
        int Y = group.getMaxY();
        int Z = group.getMaxZ();
        if (lenght > 0) {
            if (lenght < X) {
                X = lenght;
            }
            if (lenght < Z) {
                Z = lenght;
            }
        }
        int rX = (X - 1) / 2;
        int rY = (Y - 1) / 2;
        int rZ = (Z - 1) / 2;
        int minX = loc.getBlockX() - rX;
        int maxX = loc.getBlockX() + rX;
        if (maxX - minX + 1 < X) {
            ++maxX;
        }
        int minY = loc.getBlockY() - rY;
        int maxY = loc.getBlockY() + rY;
        if (maxY - minY + 1 < Y) {
            ++maxY;
        }
        if (minY < 0) {
            maxY += - minY;
            minY = 0;
        }
        if (maxY > loc.getWorld().getMaxHeight()) {
            int dif = maxY - loc.getWorld().getMaxHeight();
            if (minY > 0) {
                minY -= dif;
            }
            if (minY < 0) {
                minY = 0;
            }
            maxY = loc.getWorld().getMaxHeight() - 1;
        }
        int minZ = loc.getBlockZ() - rZ;
        int maxZ = loc.getBlockZ() + rZ;
        if (maxZ - minZ + 1 < Z) {
            ++maxZ;
        }
        Residence.getSelectionManager().placeLoc1(player, new Location(loc.getWorld(), (double)minX, (double)minY, (double)minZ), false);
        Residence.getSelectionManager().placeLoc2(player, new Location(loc.getWorld(), (double)maxX, (double)maxY, (double)maxZ), false);
        player.performCommand("res create " + resName);
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Create maximum allowed residence around you");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res auto (residence name) (radius)"));
    }
}

