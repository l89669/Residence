/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
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
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class select
implements cmd {
    @CommandAnnotation(simple=1, priority=1300)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (!group.selectCommandAccess() && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Select_Disabled, new Object[0]);
            return true;
        }
        if (!group.canCreateResidences() && rPlayer.getMaxSubzones() <= 0 && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Select_Disabled, new Object[0]);
            return true;
        }
        if (!player.hasPermission("residence.create") && player.isPermissionSet("residence.create") && !player.hasPermission("residence.select") && player.isPermissionSet("residence.select") && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Select_Disabled, new Object[0]);
            return true;
        }
        if (args.length == 2) {
            if (args[1].equals("size") || args[1].equals("cost")) {
                if (Residence.getSelectionManager().hasPlacedBoth(player.getName())) {
                    try {
                        Residence.getSelectionManager().showSelectionInfo(player);
                        return true;
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Residence.class.getName()).log(Level.SEVERE, null, ex);
                        return true;
                    }
                }
                if (Residence.getSelectionManager().worldEdit(player)) {
                    try {
                        Residence.getSelectionManager().showSelectionInfo(player);
                        return true;
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Residence.class.getName()).log(Level.SEVERE, null, ex);
                        return true;
                    }
                }
            } else {
                if (args[1].equals("vert")) {
                    Residence.getSelectionManager().vert(player, resadmin2);
                    return true;
                }
                if (args[1].equals("sky")) {
                    Residence.getSelectionManager().sky(player, resadmin2);
                    return true;
                }
                if (args[1].equals("bedrock")) {
                    Residence.getSelectionManager().bedrock(player, resadmin2);
                    return true;
                }
                if (args[1].equals("coords")) {
                    Location playerLoc2;
                    Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
                    Location playerLoc1 = Residence.getSelectionManager().getPlayerLoc1(player.getName());
                    if (playerLoc1 != null) {
                        Residence.msg((CommandSender)player, lm.Select_Primary, Residence.msg(lm.General_CoordsTop, playerLoc1.getBlockX(), playerLoc1.getBlockY(), playerLoc1.getBlockZ()));
                    }
                    if ((playerLoc2 = Residence.getSelectionManager().getPlayerLoc2(player.getName())) != null) {
                        Residence.msg((CommandSender)player, lm.Select_Secondary, Residence.msg(lm.General_CoordsBottom, playerLoc2.getBlockX(), playerLoc2.getBlockY(), playerLoc2.getBlockZ()));
                    }
                    Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
                    return true;
                }
                if (args[1].equals("chunk")) {
                    Residence.getSelectionManager().selectChunk(player);
                    return true;
                }
                if (args[1].equals("worldedit")) {
                    if (Residence.getSelectionManager().worldEdit(player)) {
                        Residence.msg((CommandSender)player, lm.Select_Success, new Object[0]);
                    }
                    return true;
                }
            }
        } else if (args.length == 3) {
            if (args[1].equals("expand")) {
                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                }
                catch (Exception ex) {
                    Residence.msg((CommandSender)player, lm.Invalid_Amount, new Object[0]);
                    return true;
                }
                Residence.getSelectionManager().modify(player, false, amount);
                return true;
            }
            if (args[1].equals("shift")) {
                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                }
                catch (Exception ex) {
                    Residence.msg((CommandSender)player, lm.Invalid_Amount, new Object[0]);
                    return true;
                }
                Residence.getSelectionManager().modify(player, true, amount);
                return true;
            }
        }
        if ((args.length == 2 || args.length == 3) && args[1].equals("auto")) {
            Player target = player;
            if (args.length == 3) {
                if (!player.hasPermission("residence.select.auto.others")) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return true;
                }
                target = Bukkit.getPlayer((String)args[2]);
                if (target == null) {
                    Residence.msg((CommandSender)player, lm.General_NotOnline, new Object[0]);
                    return true;
                }
            }
            Residence.getAutoSelectionManager().switchAutoSelection(target);
            return true;
        }
        if (args.length > 1 && args[1].equals("residence")) {
            String areaName;
            ClaimedResidence res = null;
            res = args.length > 2 ? Residence.getResidenceManager().getByName(args[2]) : Residence.getResidenceManager().getByLoc(player.getLocation());
            if (res == null) {
                Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                return true;
            }
            String resName = res.getName();
            CuboidArea area2 = null;
            if (args.length > 3) {
                area2 = res.getArea(args[3]);
                areaName = args[3];
            } else {
                areaName = res.getAreaIDbyLoc(player.getLocation());
                area2 = res.getArea(areaName);
            }
            if (area2 != null) {
                Residence.getSelectionManager().placeLoc1(player, area2.getHighLoc(), false);
                Residence.getSelectionManager().placeLoc2(player, area2.getLowLoc(), true);
                Residence.msg((CommandSender)player, lm.Select_Area, areaName, resName);
            } else {
                Residence.msg((CommandSender)player, lm.Area_NonExist, new Object[0]);
            }
            return true;
        }
        try {
            Residence.getSelectionManager().selectBySize(player, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            return true;
        }
        catch (Exception ex) {
            Residence.msg((CommandSender)player, lm.Select_Fail, new Object[0]);
            return true;
        }
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Selection Commands");
        c.get(String.valueOf(path) + "Info", Arrays.asList("This command selects areas for usage with residence.", "/res select [x] [y] [z] - selects a radius of blocks, with you in the middle."));
        path = String.valueOf(path) + "SubCommands.";
        c.get(String.valueOf(path) + "coords.Description", "Display selected coordinates");
        c.get(String.valueOf(path) + "coords.Info", Arrays.asList("&eUsage: &6/res select coords"));
        c.get(String.valueOf(path) + "size.Description", "Display selected size");
        c.get(String.valueOf(path) + "size.Info", Arrays.asList("&eUsage: &6/res select size"));
        c.get(String.valueOf(path) + "auto.Description", "Turns on auto selection tool");
        c.get(String.valueOf(path) + "auto.Info", Arrays.asList("&eUsage: &6/res select auto [playername]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "auto"), Arrays.asList("[playername]"));
        c.get(String.valueOf(path) + "cost.Description", "Display selection cost");
        c.get(String.valueOf(path) + "cost.Info", Arrays.asList("&eUsage: &6/res select cost"));
        c.get(String.valueOf(path) + "vert.Description", "Expand Selection Vertically");
        c.get(String.valueOf(path) + "vert.Info", Arrays.asList("&eUsage: &6/res select vert", "Will expand selection as high and as low as allowed."));
        c.get(String.valueOf(path) + "sky.Description", "Expand Selection to Sky");
        c.get(String.valueOf(path) + "sky.Info", Arrays.asList("&eUsage: &6/res select sky", "Expands as high as your allowed to go."));
        c.get(String.valueOf(path) + "bedrock.Description", "Expand Selection to Bedrock");
        c.get(String.valueOf(path) + "bedrock.Info", Arrays.asList("&eUsage: &6/res select bedrock", "Expands as low as your allowed to go."));
        c.get(String.valueOf(path) + "expand.Description", "Expand selection in a direction.");
        c.get(String.valueOf(path) + "expand.Info", Arrays.asList("&eUsage: &6/res select expand <amount>", "Expands <amount> in the direction your looking."));
        c.get(String.valueOf(path) + "shift.Description", "Shift selection in a direction");
        c.get(String.valueOf(path) + "shift.Info", Arrays.asList("&eUsage: &6/res select shift <amount>", "Pushes your selection by <amount> in the direction your looking."));
        c.get(String.valueOf(path) + "chunk.Description", "Select the chunk your currently in.");
        c.get(String.valueOf(path) + "chunk.Info", Arrays.asList("&eUsage: &6/res select chunk", "Selects the chunk your currently standing in."));
        c.get(String.valueOf(path) + "residence.Description", "Select a existing area in a residence.");
        c.get(String.valueOf(path) + "residence.Info", Arrays.asList("&eUsage: &6/res select residence <residence>", "Selects a existing area in a residence."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "residence"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "worldedit.Description", "Set selection using the current WorldEdit selection.");
        c.get(String.valueOf(path) + "worldedit.Info", Arrays.asList("&eUsage: &6/res select worldedit", "Sets selection area using the current WorldEdit selection."));
    }
}

