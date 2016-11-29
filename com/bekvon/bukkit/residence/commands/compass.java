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

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class compass
implements cmd {
    @CommandAnnotation(simple=1, priority=3200)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length != 2) {
            player.setCompassTarget(player.getWorld().getSpawnLocation());
            Residence.msg((CommandSender)player, lm.General_CompassTargetReset, new Object[0]);
            return true;
        }
        if (!player.hasPermission("residence.compass")) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return true;
        }
        if (Residence.getResidenceManager().getByName(args[1]) != null) {
            if (Residence.getResidenceManager().getByName(args[1]).getWorld().equalsIgnoreCase(player.getWorld().getName())) {
                Location low = Residence.getResidenceManager().getByName(args[1]).getArea("main").getLowLoc();
                Location high = Residence.getResidenceManager().getByName(args[1]).getArea("main").getHighLoc();
                Location mid = new Location(low.getWorld(), (double)((low.getBlockX() + high.getBlockX()) / 2), (double)((low.getBlockY() + high.getBlockY()) / 2), (double)((low.getBlockZ() + high.getBlockZ()) / 2));
                player.setCompassTarget(mid);
                Residence.msg((CommandSender)player, lm.General_CompassTargetSet, args[1]);
            }
        } else {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Set compass ponter to residence location");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res compass <residence>"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
    }
}

