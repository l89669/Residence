/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.World
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
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class list
implements cmd {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @CommandAnnotation(simple=1, priority=300)
    @Override
    public boolean perform(String[] args, boolean resadmin, Command command, CommandSender sender) {
        page = 1;
        world = null;
        target = null;
        i = 1;
        while (i < args.length) {
            block6 : {
                try {
                    page = Integer.parseInt(args[i]);
                    if (page < 1) {
                        page = 1;
                    }
                    break block6;
                }
                catch (Exception var9_10) {
                    ** for (w : Bukkit.getWorlds())
                }
lbl-1000: // 1 sources:
                {
                    if (!w.getName().equalsIgnoreCase(args[i])) continue;
                    world = w;
                    break block6;
                }
lbl16: // 1 sources:
                target = args[i];
            }
            ++i;
        }
        if (target != null && !sender.getName().equalsIgnoreCase(target) && !sender.hasPermission("residence.command.list.others")) {
            Residence.msg(sender, lm.General_NoPermission, new Object[0]);
            return true;
        }
        Residence.getResidenceManager().listResidences(sender, target, page, false, false, resadmin, world);
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "List Residences");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res list <player> <page> <worldName>", "Lists all the residences a player owns (except hidden ones).", "If listing your own residences, shows hidden ones as well.", "To list everyones residences, use /res listall."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[playername]", "[worldname]"));
    }
}

