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

public class lease
implements cmd {
    @CommandAnnotation(simple=1, priority=3900)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length == 2 || args.length == 3) {
            if (args[1].equals("expires")) {
                String until;
                ClaimedResidence res = null;
                if (args.length == 2) {
                    res = Residence.getResidenceManager().getByLoc(player.getLocation());
                    if (res == null) {
                        Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                        return true;
                    }
                } else {
                    res = Residence.getResidenceManager().getByName(args[2]);
                    if (res == null) {
                        Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                        return true;
                    }
                }
                if ((until = Residence.getLeaseManager().getExpireTime(res.getName())) != null) {
                    Residence.msg((CommandSender)player, lm.Economy_LeaseRenew, until);
                }
                return true;
            }
            if (args[1].equals("renew")) {
                if (args.length == 3) {
                    Residence.getLeaseManager().renewArea(args[2], player);
                } else {
                    Residence.getLeaseManager().renewArea(Residence.getResidenceManager().getNameByLoc(player.getLocation()), player);
                }
                return true;
            }
            if (args[1].equals("cost")) {
                if (args.length == 3) {
                    ClaimedResidence res = Residence.getResidenceManager().getByName(args[2]);
                    if (res == null || Residence.getLeaseManager().leaseExpires(args[2])) {
                        int cost = Residence.getLeaseManager().getRenewCost(res);
                        Residence.msg((CommandSender)player, lm.Economy_LeaseRenewalCost, args[2], cost);
                    } else {
                        Residence.msg((CommandSender)player, lm.Economy_LeaseNotExpire, new Object[0]);
                    }
                    return true;
                }
                String area2 = Residence.getResidenceManager().getNameByLoc(player.getLocation());
                ClaimedResidence res = Residence.getResidenceManager().getByName(area2);
                if (area2 == null || res == null) {
                    Residence.msg((CommandSender)player, lm.Invalid_Area, new Object[0]);
                    return true;
                }
                if (Residence.getLeaseManager().leaseExpires(area2)) {
                    int cost = Residence.getLeaseManager().getRenewCost(res);
                    Residence.msg((CommandSender)player, lm.Economy_LeaseRenewalCost, area2, cost);
                } else {
                    Residence.msg((CommandSender)player, lm.Economy_LeaseNotExpire, new Object[0]);
                }
                return true;
            }
        } else if (args.length == 4 && args[1].equals("set")) {
            int days;
            if (!resadmin2) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return true;
            }
            if (args[3].equals("infinite")) {
                if (Residence.getLeaseManager().leaseExpires(args[2])) {
                    Residence.getLeaseManager().removeExpireTime(args[2]);
                    Residence.msg((CommandSender)player, lm.Economy_LeaseInfinite, new Object[0]);
                } else {
                    Residence.msg((CommandSender)player, lm.Economy_LeaseNotExpire, new Object[0]);
                }
                return true;
            }
            try {
                days = Integer.parseInt(args[3]);
            }
            catch (Exception ex) {
                Residence.msg((CommandSender)player, lm.Invalid_Days, new Object[0]);
                return true;
            }
            Residence.getLeaseManager().setExpireTime(player, args[2], days);
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Manage residence leases");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res lease [renew/cost] [residence]", "/res lease cost will show the cost of renewing a residence lease.", "/res lease renew will renew the residence provided you have enough money."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("renew%%cost", "[residence]"));
        path = String.valueOf(path) + "SubCommands.";
        c.get(String.valueOf(path) + "set.Description", "Set the lease time");
        c.get(String.valueOf(path) + "set.Info", Arrays.asList("&eUsage: &6/resadmin lease set [residence] [#days/infinite]", "Sets the lease time to a specified number of days, or infinite."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "set"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "renew.Description", "Renew the lease time");
        c.get(String.valueOf(path) + "renew.Info", Arrays.asList("&eUsage: &6/resadmin lease renew <residence>", "Renews the lease time for current or specified residence."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "renew"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "expires.Description", "Lease end date");
        c.get(String.valueOf(path) + "expires.Info", Arrays.asList("&eUsage: &6/resadmin lease expires <residence>", "Shows when expires residence lease time."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "expires"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "cost.Description", "Shows renew cost");
        c.get(String.valueOf(path) + "cost.Info", Arrays.asList("&eUsage: &6/resadmin lease cost <residence>", "Shows how much money you need to renew residence lease."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "cost"), Arrays.asList("[residence]"));
    }
}

