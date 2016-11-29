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

public class message
implements cmd {
    @CommandAnnotation(simple=1, priority=1000)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        ClaimedResidence res = null;
        int start = 0;
        boolean enter = false;
        if (args.length < 2) {
            return false;
        }
        if (args[1].equals("enter")) {
            enter = true;
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
            start = 2;
        } else if (args[1].equals("leave")) {
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
            start = 2;
        } else {
            if (args[1].equals("remove")) {
                if (args.length > 2 && args[2].equals("enter")) {
                    res = Residence.getResidenceManager().getByLoc(player.getLocation());
                    if (res != null) {
                        res.setEnterLeaveMessage(player, null, true, resadmin2);
                    } else {
                        Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                    }
                    return true;
                }
                if (args.length > 2 && args[2].equals("leave")) {
                    res = Residence.getResidenceManager().getByLoc(player.getLocation());
                    if (res != null) {
                        res.setEnterLeaveMessage(player, null, false, resadmin2);
                    } else {
                        Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                    }
                    return true;
                }
                Residence.msg((CommandSender)player, lm.Invalid_MessageType, new Object[0]);
                return true;
            }
            if (args.length > 2 && args[2].equals("enter")) {
                enter = true;
                res = Residence.getResidenceManager().getByName(args[1]);
                start = 3;
            } else if (args.length > 2 && args[2].equals("leave")) {
                res = Residence.getResidenceManager().getByName(args[1]);
                start = 3;
            } else {
                if (args.length > 2 && args[2].equals("remove")) {
                    res = Residence.getResidenceManager().getByName(args[1]);
                    if (args.length != 4) {
                        return false;
                    }
                    if (args[3].equals("enter")) {
                        if (res != null) {
                            res.setEnterLeaveMessage(player, null, true, resadmin2);
                        }
                        return true;
                    }
                    if (args[3].equals("leave")) {
                        if (res != null) {
                            res.setEnterLeaveMessage(player, null, false, resadmin2);
                        }
                        return true;
                    }
                    Residence.msg((CommandSender)player, lm.Invalid_MessageType, new Object[0]);
                    return true;
                }
                Residence.msg((CommandSender)player, lm.Invalid_MessageType, new Object[0]);
                return true;
            }
        }
        if (start == 0) {
            return false;
        }
        String message2 = "";
        int i = start;
        while (i < args.length) {
            message2 = String.valueOf(message2) + args[i] + " ";
            ++i;
        }
        if (res != null) {
            res.setEnterLeaveMessage(player, message2, enter, resadmin2);
        } else {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Manage residence enter / leave messages");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res message <residence> [enter/leave] [message]", "Set the enter or leave message of a residence.", "&eUsage: &6/res message <residence> remove [enter/leave]", "Removes a enter or leave message."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]%%enter%%leave", "enter%%leave"));
    }
}

