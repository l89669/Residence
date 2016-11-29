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

public class command
implements cmd {
    @CommandAnnotation(simple=1, priority=3000)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        Player player;
        ClaimedResidence res = null;
        String action = null;
        String cmd2 = null;
        if (args.length == 2 && sender instanceof Player) {
            player = (Player)sender;
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
            action = args[1];
        } else if (args.length == 3 && sender instanceof Player) {
            player = (Player)sender;
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
            action = args[1];
            cmd2 = args[2];
        } else if (args.length == 4) {
            res = Residence.getResidenceManager().getByName(args[1]);
            action = args[2];
            cmd2 = args[3];
        } else if (args.length == 3 && !(sender instanceof Player)) {
            res = Residence.getResidenceManager().getByName(args[1]);
            action = args[2];
        }
        if (res == null) {
            Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
            return true;
        }
        if (!res.isOwner(sender) && !resadmin2) {
            Residence.msg(sender, lm.Residence_NotOwner, new Object[0]);
            return true;
        }
        if (action != null && action.equalsIgnoreCase("allow")) {
            if (res.addCmdWhiteList(cmd2)) {
                Residence.msg(sender, lm.command_addedAllow, res.getName());
            } else {
                Residence.msg(sender, lm.command_removedAllow, res.getName());
            }
        } else if (action != null && action.equalsIgnoreCase("block")) {
            if (res.addCmdBlackList(cmd2)) {
                Residence.msg(sender, lm.command_addedBlock, res.getName());
            } else {
                Residence.msg(sender, lm.command_removedBlock, res.getName());
            }
        } else if (action != null && action.equalsIgnoreCase("list")) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < res.getCmdBlackList().size()) {
                sb.append("/" + res.getCmdBlackList().get(i).replace("_", " "));
                if (i + 1 < res.getCmdBlackList().size()) {
                    sb.append(", ");
                }
                ++i;
            }
            Residence.msg(sender, lm.command_Blocked, sb.toString());
            sb = new StringBuilder();
            i = 0;
            while (i < res.getCmdWhiteList().size()) {
                sb.append("/" + res.getCmdWhiteList().get(i).replace("_", " "));
                if (i + 1 < res.getCmdWhiteList().size()) {
                    sb.append(", ");
                }
                ++i;
            }
            Residence.msg(sender, lm.command_Allowed, sb.toString());
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Manages allowed or blocked commands in residence");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res command <residence> <allow/block/list> <command>", "Shows list, adds or removes allowed or disabled commands in residence", "Use _ to include command with multiple variables"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]%%allow%%block%%list", "allow%%block%%list"));
    }
}

