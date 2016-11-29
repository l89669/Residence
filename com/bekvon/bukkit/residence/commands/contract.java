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
import com.bekvon.bukkit.residence.protection.CuboidArea;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class contract
implements cmd {
    @CommandAnnotation(simple=1, priority=1900)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        ClaimedResidence res = null;
        if (args.length == 2) {
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
        } else if (args.length == 3) {
            res = Residence.getResidenceManager().getByName(args[1]);
        } else {
            return false;
        }
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return true;
        }
        if (res.isSubzone() && !player.hasPermission("residence.contract.subzone") && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Subzone_CantContract, new Object[0]);
            return false;
        }
        if (!(res.isSubzone() || player.hasPermission("residence.contract") || resadmin2)) {
            Residence.msg((CommandSender)player, lm.Residence_CantContractResidence, new Object[0]);
            return false;
        }
        String resName = res.getName();
        CuboidArea area2 = null;
        String areaName = null;
        if (args.length == 2) {
            areaName = res.getAreaIDbyLoc(player.getLocation());
            area2 = res.getArea(areaName);
        } else if (args.length == 3) {
            areaName = res.isSubzone() ? Residence.getResidenceManager().getSubzoneNameByRes(res) : "main";
            area2 = res.getCuboidAreabyName(areaName);
        }
        if (area2 == null) {
            Residence.msg((CommandSender)player, lm.Area_NonExist, new Object[0]);
            return true;
        }
        Residence.getSelectionManager().placeLoc1(player, area2.getHighLoc(), false);
        Residence.getSelectionManager().placeLoc2(player, area2.getLowLoc(), false);
        Residence.msg((CommandSender)player, lm.Select_Area, areaName, resName);
        int amount = -1;
        try {
            if (args.length == 2) {
                amount = Integer.parseInt(args[1]);
            } else if (args.length == 3) {
                amount = Integer.parseInt(args[2]);
            }
        }
        catch (Exception ex) {
            Residence.msg((CommandSender)player, lm.Invalid_Amount, new Object[0]);
            return true;
        }
        if (amount > 1000) {
            Residence.msg((CommandSender)player, lm.Invalid_Amount, new Object[0]);
            return true;
        }
        if (amount < 0) {
            amount = 1;
        }
        if (!Residence.getSelectionManager().contract(player, amount)) {
            return true;
        }
        if (Residence.getSelectionManager().hasPlacedBoth(player.getName())) {
            if (Residence.getWEplugin() != null && Residence.wepid == Residence.getConfigManager().getSelectionTooldID()) {
                Residence.getSelectionManager().worldEdit(player);
            }
            res.replaceArea(player, Residence.getSelectionManager().getSelectionCuboid(player), areaName, resadmin2);
            return true;
        }
        Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Contracts residence in direction you looking");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res contract (residence) [amount]", "Contracts residence in direction you looking.", "Residence name is optional"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]%%1", "1"));
    }
}

