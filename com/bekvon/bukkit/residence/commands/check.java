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

public class check
implements cmd {
    @CommandAnnotation(simple=1, priority=3500)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        ClaimedResidence res;
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        String pname = player.getName();
        if (args.length != 3 && args.length != 4) {
            return false;
        }
        if (args.length == 4) {
            pname = args[3];
        }
        if ((res = Residence.getResidenceManager().getByName(args[1])) == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return true;
        }
        if (!res.getPermissions().hasApplicableFlag(pname, args[2])) {
            Residence.msg((CommandSender)player, lm.Flag_CheckFalse, args[2], pname, args[1]);
        } else {
            Object[] arrobject = new Object[4];
            arrobject[0] = args[2];
            arrobject[1] = pname;
            arrobject[2] = args[1];
            arrobject[3] = res.getPermissions().playerHas(pname, res.getPermissions().getWorld(), args[2], false) ? Residence.msg(lm.General_True, new Object[0]) : Residence.msg(lm.General_False, new Object[0]);
            Residence.msg((CommandSender)player, lm.Flag_CheckTrue, arrobject);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Check flag state for you");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res check [residence] [flag] (playername)"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]", "[flag]", "[playername]"));
    }
}

