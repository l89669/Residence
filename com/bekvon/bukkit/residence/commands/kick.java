/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
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
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class kick
implements cmd {
    @CommandAnnotation(simple=1, priority=2200)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length != 2) {
            return false;
        }
        Player targetplayer = Bukkit.getPlayer((String)args[1]);
        if (targetplayer == null) {
            Residence.msg((CommandSender)player, lm.General_NotOnline, new Object[0]);
            return true;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (!group.hasKickAccess() && !resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return true;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(targetplayer.getLocation());
        if (res == null || !res.isOwner(player) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Residence_PlayerNotIn, new Object[0]);
            return true;
        }
        if (!res.isOwner(player)) {
            return false;
        }
        if (res.getPlayersInResidence().contains((Object)targetplayer)) {
            Location loc = Residence.getConfigManager().getKickLocation();
            if (loc != null) {
                targetplayer.teleport(loc);
            } else {
                targetplayer.teleport(res.getOutsideFreeLoc(player.getLocation(), player));
            }
            Residence.msg((CommandSender)targetplayer, lm.Residence_Kicked, new Object[0]);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Kicks player from residence.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res kick <player>", "You must be the owner or an admin to do this.", "Player should be online."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[playername]"));
    }
}

