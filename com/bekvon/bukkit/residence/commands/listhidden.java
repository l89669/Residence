/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class listhidden
implements cmd {
    @CommandAnnotation(simple=0, priority=4800)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        int page = 1;
        try {
            if (args.length > 0) {
                page = Integer.parseInt(args[args.length - 1]);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (!resadmin2) {
            Residence.msg(sender, lm.General_NoPermission, new Object[0]);
            return true;
        }
        if (args.length == 1) {
            Residence.getResidenceManager().listResidences(sender, 1, true, true);
            return true;
        }
        if (args.length == 2) {
            try {
                Integer.parseInt(args[1]);
                Residence.getResidenceManager().listResidences(sender, page, true, true);
            }
            catch (Exception ex) {
                Residence.getResidenceManager().listResidences(sender, args[1], 1, true, true, resadmin2);
            }
            return true;
        }
        if (args.length == 3) {
            Residence.getResidenceManager().listResidences(sender, args[1], page, true, true, resadmin2);
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "List Hidden Residences");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res listhidden <player> <page>", "Lists hidden residences for a player."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[playername]"));
    }
}

