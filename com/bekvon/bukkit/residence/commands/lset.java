/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
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
import com.bekvon.bukkit.residence.itemlist.ResidenceItemList;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class lset
implements cmd {
    @CommandAnnotation(simple=1, priority=5000)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        ClaimedResidence res = null;
        Material mat = null;
        String listtype = null;
        boolean showinfo = false;
        if (args.length == 2 && args[1].equals("info")) {
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
            showinfo = true;
        } else if (args.length == 3 && args[2].equals("info")) {
            res = Residence.getResidenceManager().getByName(args[1]);
            showinfo = true;
        }
        if (showinfo) {
            if (res == null) {
                Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                return true;
            }
            Residence.msg((CommandSender)player, lm.General_Blacklist, new Object[0]);
            res.getItemBlacklist().printList(player);
            Residence.msg((CommandSender)player, lm.General_Ignorelist, new Object[0]);
            res.getItemIgnoreList().printList(player);
            return true;
        }
        if (args.length == 4) {
            res = Residence.getResidenceManager().getByName(args[1]);
            listtype = args[2];
            try {
                mat = Material.valueOf((String)args[3].toUpperCase());
            }
            catch (Exception ex) {
                Residence.msg((CommandSender)player, lm.Invalid_Material, new Object[0]);
                return true;
            }
        }
        if (args.length == 3) {
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
            listtype = args[1];
            try {
                mat = Material.valueOf((String)args[2].toUpperCase());
            }
            catch (Exception ex) {
                Residence.msg((CommandSender)player, lm.Invalid_Material, new Object[0]);
                return true;
            }
        }
        if (res != null) {
            if (listtype != null && listtype.equalsIgnoreCase("blacklist")) {
                res.getItemBlacklist().playerListChange(player, mat, resadmin2);
            } else if (listtype != null && listtype.equalsIgnoreCase("ignorelist")) {
                res.getItemIgnoreList().playerListChange(player, mat, resadmin2);
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_List, new Object[0]);
            }
            return true;
        }
        Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Change blacklist and ignorelist options");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res lset <residence> [blacklist/ignorelist] [material]", "&eUsage: &6/res lset <residence> Info", "Blacklisting a material prevents it from being placed in the residence.", "Ignorelist causes a specific material to not be protected by Residence."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]%%blacklist%%ignorelist", "blacklist%%ignorelist%%[material]", "[material]"));
    }
}

