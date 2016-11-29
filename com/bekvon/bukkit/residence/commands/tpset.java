/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpset
implements cmd {
    @CommandAnnotation(simple=1, priority=200)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(player.getLocation());
        if (res != null) {
            res.setTpLoc(player, resadmin2);
        } else {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Set the teleport location of a Residence");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res tpset", "This will set the teleport location for a residence to where your standing.", "You must be standing in the residence to use this command.", "You must also be the owner or have the +admin flag for the residence."));
    }
}

