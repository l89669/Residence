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

public class clearflags
implements cmd {
    @CommandAnnotation(simple=0, priority=3600)
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
        ClaimedResidence area2 = Residence.getResidenceManager().getByName(args[1]);
        if (area2 != null) {
            area2.getPermissions().clearFlags();
            Residence.msg((CommandSender)player, lm.Flag_Cleared, new Object[0]);
        } else {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Remove all flags from residence");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res clearflags <residence>"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
    }
}

