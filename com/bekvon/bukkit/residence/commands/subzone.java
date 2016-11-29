/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class subzone
implements cmd {
    @CommandAnnotation(simple=1, priority=2100)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        String parent;
        String zname;
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length != 2 && args.length != 3) {
            return false;
        }
        if (args.length == 2) {
            parent = Residence.getResidenceManager().getNameByLoc(player.getLocation());
            zname = args[1];
        } else {
            parent = args[1];
            zname = args[2];
        }
        if (Residence.getWEplugin() != null && Residence.getWEplugin().getConfig().getInt("wand-item") == Residence.getConfigManager().getSelectionTooldID()) {
            Residence.getSelectionManager().worldEdit(player);
        }
        if (Residence.getSelectionManager().hasPlacedBoth(player.getName())) {
            ClaimedResidence res = Residence.getResidenceManager().getByName(parent);
            if (res == null) {
                Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                return true;
            }
            if (!player.hasPermission("residence.create.subzone") && !resadmin2) {
                Residence.msg((CommandSender)player, lm.Subzone_CantCreate, new Object[0]);
                return false;
            }
            res.addSubzone(player, Residence.getSelectionManager().getPlayerLoc1(player.getName()), Residence.getSelectionManager().getPlayerLoc2(player.getName()), zname, resadmin2);
        } else {
            Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Create subzones in residences.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res subzone <residence> [subzone name]", "If residence name is left off, will attempt to use residence your standing in."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
    }
}

