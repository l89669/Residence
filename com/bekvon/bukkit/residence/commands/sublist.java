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

public class sublist
implements cmd {
    @CommandAnnotation(simple=1, priority=4100)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        int page = 1;
        try {
            if (args.length > 0) {
                page = Integer.parseInt(args[args.length - 1]);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (args.length != 1 && args.length != 2 && args.length != 3) {
            return false;
        }
        ClaimedResidence res = args.length == 1 ? Residence.getResidenceManager().getByLoc(player.getLocation()) : Residence.getResidenceManager().getByName(args[1]);
        if (res != null) {
            res.printSubzoneList(player, page);
        } else {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "List Residence Subzones");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res sublist <residence> <page>", "List subzones within a residence."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
    }
}

