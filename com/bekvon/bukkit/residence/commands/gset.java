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
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gset
implements cmd {
    @CommandAnnotation(simple=1, priority=4500)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length == 4) {
            ClaimedResidence area2 = Residence.getResidenceManager().getByLoc(player.getLocation());
            if (area2 != null) {
                area2.getPermissions().setGroupFlag(player, args[1], args[2], args[3], resadmin2);
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Area, new Object[0]);
            }
            return true;
        }
        if (args.length == 5) {
            ClaimedResidence area3 = Residence.getResidenceManager().getByName(args[1]);
            if (area3 != null) {
                area3.getPermissions().setGroupFlag(player, args[2], args[3], args[4], resadmin2);
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            }
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Set flags on a specific group for a Residence.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res gset <residence> [group] [flag] [true/false/remove]", "To see a list of flags, use /res flags ?"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
    }
}

