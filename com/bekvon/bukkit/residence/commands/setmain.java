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
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
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

public class setmain
implements cmd {
    @CommandAnnotation(simple=1, priority=2900)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length != 1 && args.length != 2) {
            return false;
        }
        ClaimedResidence res = null;
        res = args.length == 1 ? Residence.getResidenceManager().getByLoc(player.getLocation()) : Residence.getResidenceManager().getByName(args[1]);
        if (res == null) {
            Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
            return false;
        }
        if (res.isOwner(player)) {
            res.setMainResidence(!res.isMainResidence());
        } else if (Residence.getRentManager().isRented(res.getName()) && !Residence.getRentManager().getRentingPlayer(res.getName()).equalsIgnoreCase(player.getName())) {
            Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
            return false;
        }
        Residence.msg((CommandSender)player, lm.Residence_ChangedMain, res.getTopParentName());
        ResidencePlayer rplayer = Residence.getPlayerManager().getResidencePlayer(player);
        if (rplayer != null) {
            rplayer.setMainResidence(res);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Sets defined residence as main to show up in chat as prefix");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res setmain (residence)", "Set defined residence as main."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
    }
}

