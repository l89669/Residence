/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class resadmin
implements cmd {
    @CommandAnnotation(simple=0, priority=5300)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (args.length != 2) {
            return true;
        }
        Player player = (Player)sender;
        if (args[1].equals("on")) {
            Residence.resadminToggle.add(player.getName());
            Residence.msg((CommandSender)player, lm.General_AdminToggleTurnOn, new Object[0]);
        } else if (args[1].equals("off")) {
            Residence.resadminToggle.remove(player.getName());
            Residence.msg((CommandSender)player, lm.General_AdminToggleTurnOff, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Enabled or disable residence admin");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res resadmin [on/off]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("on%%off"));
    }
}

