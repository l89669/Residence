/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.getspout.spoutapi.SpoutManager
 *  org.getspout.spoutapi.player.SpoutPlayer
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class gui
implements cmd {
    @CommandAnnotation(simple=1, priority=4600)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (Residence.getSpoutListener() == null) {
            return true;
        }
        if (args.length == 1) {
            Residence.getSpout().showResidenceFlagGUI(SpoutManager.getPlayer((Player)player), Residence.getResidenceManager().getNameByLoc(player.getLocation()), resadmin2);
        } else if (args.length == 2) {
            Residence.getSpout().showResidenceFlagGUI(SpoutManager.getPlayer((Player)player), args[1], resadmin2);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Opens gui (Spout only)");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res gui <residence>"));
    }
}

