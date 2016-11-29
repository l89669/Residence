/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class listall
implements cmd {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @CommandAnnotation(simple=1, priority=4200)
    @Override
    public boolean perform(String[] args, boolean resadmin, Command command, CommandSender sender) {
        page = 1;
        world = null;
        i = 1;
        while (i < args.length) {
            block5 : {
                try {
                    page = Integer.parseInt(args[i]);
                    if (page >= 1) break block5;
                    page = 1;
                    break block5;
                }
                catch (Exception var8_9) {
                    if (args[i].equalsIgnoreCase("-a") && !(sender instanceof Player)) {
                        page = -1;
                        break block5;
                    }
                    ** for (w : Bukkit.getWorlds())
                }
lbl-1000: // 1 sources:
                {
                    if (!w.getName().equalsIgnoreCase(args[i])) continue;
                    world = w;
                    break;
                }
            }
            ++i;
        }
        Residence.getResidenceManager().listAllResidences(sender, page, resadmin, world);
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "List All Residences");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res listall <page> <worldName> <-a>", "Lists all residences"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[worldname]"));
    }
}

