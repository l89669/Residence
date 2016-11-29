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
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class current
implements cmd {
    @CommandAnnotation(simple=1, priority=3100)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length != 1) {
            return false;
        }
        String res = Residence.getResidenceManager().getNameByLoc(player.getLocation());
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
        } else {
            Residence.msg((CommandSender)player, lm.Residence_In, res);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Show residence your currently in.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res current"));
    }
}

