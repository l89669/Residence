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

public class removeall
implements cmd {
    @CommandAnnotation(simple=0, priority=5100)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        String target;
        if (args.length != 2 && args.length != 1) {
            return false;
        }
        String string = target = args.length == 2 ? args[1] : sender.getName();
        if (resadmin2) {
            Residence.getResidenceManager().removeAllByOwner(target);
            Residence.msg(sender, lm.Residence_RemovePlayersResidences, target);
        } else {
            Residence.msg(sender, lm.General_NoPermission, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Remove all residences owned by a player.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res removeall [owner]", "Removes all residences owned by a specific player.'", "Requires /resadmin if you use it on anyone besides yourself."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[playername]"));
    }
}

