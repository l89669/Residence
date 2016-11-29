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
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class server
implements cmd {
    @CommandAnnotation(simple=0, priority=5400)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (!resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return true;
        }
        if (args.length == 2) {
            ClaimedResidence res = Residence.getResidenceManager().getByName(args[1]);
            if (res == null) {
                Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                return true;
            }
            res.getPermissions().setOwner(Residence.getServerLandname(), false);
            Residence.msg((CommandSender)player, lm.Residence_OwnerChange, args[1], Residence.getServerLandname());
            return true;
        }
        Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Make land server owned.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/resadmin server [residence]", "Make a residence server owned."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[cresidence]"));
    }
}

