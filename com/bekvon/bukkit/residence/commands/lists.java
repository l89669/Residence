/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class lists
implements cmd {
    @CommandAnnotation(simple=1, priority=4900)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length == 2) {
            if (args[1].equals("list")) {
                Residence.getPermissionListManager().printLists(player);
                return true;
            }
        } else if (args.length == 3) {
            if (args[1].equals("view")) {
                Residence.getPermissionListManager().printList(player, args[2]);
                return true;
            }
            if (args[1].equals("remove")) {
                Residence.getPermissionListManager().removeList(player, args[2]);
                return true;
            }
            if (args[1].equals("add")) {
                Residence.getPermissionListManager().makeList(player, args[2]);
                return true;
            }
        } else if (args.length == 4) {
            if (args[1].equals("apply")) {
                Residence.getPermissionListManager().applyListToResidence(player, args[2], args[3], resadmin2);
                return true;
            }
        } else if (args.length == 5) {
            if (args[1].equals("set")) {
                Residence.getPermissionListManager().getList(player.getName(), args[2]).setFlag(args[3], FlagPermissions.stringToFlagState(args[4]));
                Residence.msg((CommandSender)player, lm.Flag_Set, new Object[0]);
                return true;
            }
        } else if (args.length == 6) {
            if (args[1].equals("gset")) {
                Residence.getPermissionListManager().getList(player.getName(), args[2]).setGroupFlag(args[3], args[4], FlagPermissions.stringToFlagState(args[5]));
                Residence.msg((CommandSender)player, lm.Flag_Set, new Object[0]);
                return true;
            }
            if (args[1].equals("pset")) {
                Residence.getPermissionListManager().getList(player.getName(), args[2]).setPlayerFlag(args[3], args[4], FlagPermissions.stringToFlagState(args[5]));
                Residence.msg((CommandSender)player, lm.Flag_Set, new Object[0]);
                return true;
            }
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Predefined permission lists");
        c.get(String.valueOf(path) + "Info", Arrays.asList("Predefined permissions that can be applied to a residence."));
        path = String.valueOf(path) + "SubCommands.";
        c.get(String.valueOf(path) + "add.Description", "Add a list");
        c.get(String.valueOf(path) + "add.Info", Arrays.asList("&eUsage: &6/res lists add <listname>"));
        c.get(String.valueOf(path) + "remove.Description", "Remove a list");
        c.get(String.valueOf(path) + "remove.Info", Arrays.asList("&eUsage: &6/res lists remove <listname>"));
        c.get(String.valueOf(path) + "apply.Description", "Apply a list to a residence");
        c.get(String.valueOf(path) + "apply.Info", Arrays.asList("&eUsage: &6/res lists apply <listname> <residence>"));
        c.get(String.valueOf(path) + "set.Description", "Set a flag");
        c.get(String.valueOf(path) + "set.Info", Arrays.asList("&eUsage: &6/res lists set <listname> <flag> <value>"));
        c.get(String.valueOf(path) + "pset.Description", "Set a player flag");
        c.get(String.valueOf(path) + "pset.Info", Arrays.asList("&eUsage: &6/res lists pset <listname> <player> <flag> <value>"));
        c.get(String.valueOf(path) + "gset.Description", "Set a group flag");
        c.get(String.valueOf(path) + "gset.Info", Arrays.asList("&eUsage: &6/res lists gset <listname> <group> <flag> <value>"));
        c.get(String.valueOf(path) + "view.Description", "View a list.");
        c.get(String.valueOf(path) + "view.Info", Arrays.asList("&eUsage: &6/res lists view <listname>"));
    }
}

