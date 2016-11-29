/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class listallhidden
implements cmd {
    @CommandAnnotation(simple=0, priority=4700)
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
            Residence.getResidenceManager().listAllResidences(sender, 1, true, true);
        } else if (args.length == 2) {
            try {
                Residence.getResidenceManager().listAllResidences(sender, page, true, true);
            }
            catch (Exception exception) {}
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "List All Hidden Residences");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res listhidden <page>", "Lists all hidden residences on the server."));
    }
}

