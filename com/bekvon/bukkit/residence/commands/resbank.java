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
import com.bekvon.bukkit.residence.economy.ResidenceBank;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class resbank
implements cmd {
    @CommandAnnotation(simple=1, priority=1800)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (args.length != 3 && args.length != 4) {
            return false;
        }
        ClaimedResidence res = null;
        if (args.length == 4) {
            res = Residence.getResidenceManager().getByName(args[2]);
            if (res == null) {
                Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
                return true;
            }
        } else if (sender instanceof Player) {
            res = Residence.getResidenceManager().getByLoc(((Player)sender).getLocation());
        }
        if (res == null) {
            Residence.msg(sender, lm.Residence_NotIn, new Object[0]);
            return true;
        }
        int amount = 0;
        try {
            amount = args.length == 3 ? Integer.parseInt(args[2]) : Integer.parseInt(args[3]);
        }
        catch (Exception ex) {
            Residence.msg(sender, lm.Invalid_Amount, new Object[0]);
            return true;
        }
        if (args[1].equals("deposit")) {
            res.getBank().deposit(sender, amount, resadmin2);
        } else if (args[1].equals("withdraw")) {
            res.getBank().withdraw(sender, amount, resadmin2);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Deposit or widraw money from residence bank");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res resbank [deposit/withdraw] [amount]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("deposit%%withdraw", "1"));
    }
}

