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
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class setowner
implements cmd {
    @CommandAnnotation(simple=0, priority=5500)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (args.length < 3) {
            return false;
        }
        if (!resadmin2) {
            Residence.msg(sender, lm.General_NoPermission, new Object[0]);
            return true;
        }
        ClaimedResidence area2 = Residence.getResidenceManager().getByName(args[1]);
        if (area2 != null) {
            area2.getPermissions().setOwner(args[2], true);
            if (Residence.getRentManager().isForRent(area2.getName())) {
                Residence.getRentManager().removeRentable(area2.getName());
            }
            if (Residence.getTransactionManager().isForSale(area2.getName())) {
                Residence.getTransactionManager().removeFromSale(area2.getName());
            }
            area2.getPermissions().applyDefaultFlags();
            if (area2.getParent() == null) {
                Residence.msg(sender, lm.Residence_OwnerChange, args[1], args[2]);
            } else {
                Residence.msg(sender, lm.Subzone_OwnerChange, args[1].split("\\.")[args[1].split("\\.").length - 1], args[2]);
            }
        } else {
            Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Change owner of a residence.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/resadmin setowner [residence] [player]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[cresidence]"));
    }
}

