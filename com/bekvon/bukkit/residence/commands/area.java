/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
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
import com.bekvon.bukkit.residence.protection.CuboidArea;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class area
implements cmd {
    @CommandAnnotation(simple=1, priority=3300)
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
        if (args.length == 4) {
            if (args[1].equals("remove")) {
                ClaimedResidence res = Residence.getResidenceManager().getByName(args[2]);
                if (res != null) {
                    res.removeArea(player, args[3], resadmin2);
                } else {
                    Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                }
                return true;
            }
            if (args[1].equals("add")) {
                if (Residence.getWEplugin() != null && Residence.getWEplugin().getConfig().getInt("wand-item") == Residence.getConfigManager().getSelectionTooldID()) {
                    Residence.getSelectionManager().worldEdit(player);
                }
                if (Residence.getSelectionManager().hasPlacedBoth(player.getName())) {
                    ClaimedResidence res = Residence.getResidenceManager().getByName(args[2]);
                    if (res != null) {
                        if (res.addArea(player, Residence.getSelectionManager().getSelectionCuboid(player), args[3], resadmin2)) {
                            Residence.msg((CommandSender)player, lm.Area_Create, args[3]);
                        }
                    } else {
                        Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                    }
                } else {
                    Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
                }
                return true;
            }
            if (args[1].equals("replace")) {
                if (Residence.getWEplugin() != null && Residence.getWEplugin().getConfig().getInt("wand-item") == Residence.getConfigManager().getSelectionTooldID()) {
                    Residence.getSelectionManager().worldEdit(player);
                }
                if (Residence.getSelectionManager().hasPlacedBoth(player.getName())) {
                    ClaimedResidence res = Residence.getResidenceManager().getByName(args[2]);
                    if (res != null) {
                        res.replaceArea(player, Residence.getSelectionManager().getSelectionCuboid(player), args[3], resadmin2);
                    } else {
                        Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                    }
                } else {
                    Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
                }
                return true;
            }
        }
        if ((args.length == 3 || args.length == 4) && args[1].equals("list")) {
            ClaimedResidence res = Residence.getResidenceManager().getByName(args[2]);
            if (res != null) {
                res.printAreaList(player, page);
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            }
            return true;
        }
        if ((args.length == 3 || args.length == 4) && args[1].equals("listall")) {
            ClaimedResidence res = Residence.getResidenceManager().getByName(args[2]);
            if (res != null) {
                res.printAdvancedAreaList(player, page);
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            }
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Manage physical areas for a residence.");
        path = String.valueOf(path) + "SubCommands.";
        c.get(String.valueOf(path) + "list.Description", "List physical areas in a residence");
        c.get(String.valueOf(path) + "list.Info", Arrays.asList("&eUsage: &6/res area list [residence] <page>"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "list"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "listall.Description", "List coordinates and other Info for areas");
        c.get(String.valueOf(path) + "listall.Info", Arrays.asList("&eUsage: &6/res area listall [residence] <page>"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "listall"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "add.Description", "Add physical areas to a residence");
        c.get(String.valueOf(path) + "add.Info", Arrays.asList("&eUsage: &6/res area add [residence] [areaID]", "You must first select two points first."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "add"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "remove.Description", "Remove physical areas from a residence");
        c.get(String.valueOf(path) + "remove.Info", Arrays.asList("&eUsage: &6/res area remove [residence] [areaID]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "remove"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "replace.Description", "Replace physical areas in a residence");
        c.get(String.valueOf(path) + "replace.Info", Arrays.asList("&eUsage: &6/res area replace [residence] [areaID]", "You must first select two points first.", "Replacing a area will charge the difference in size if the new area is bigger."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "replace"), Arrays.asList("[residence]"));
    }
}

