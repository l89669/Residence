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

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class info
implements cmd {
    @CommandAnnotation(simple=1, priority=600)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (args.length == 1 && sender instanceof Player) {
            Player player = (Player)sender;
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(player.getLocation());
            if (res != null) {
                Residence.getResidenceManager().printAreaInfo(res.getName(), sender, resadmin2);
            } else {
                Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
            }
            return true;
        }
        if (args.length == 2) {
            Residence.getResidenceManager().printAreaInfo(args[1], sender, resadmin2);
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Show info on a residence.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res info <residence>", "Leave off <residence> to display info for the residence your currently in."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
    }
}

