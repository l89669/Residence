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

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unstuck
implements cmd {
    @CommandAnnotation(simple=1, priority=4000)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length != 1) {
            return false;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (!group.hasUnstuckAccess()) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return true;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(player.getLocation());
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
        } else {
            Residence.msg((CommandSender)player, lm.General_Moved, new Object[0]);
            player.teleport(res.getOutsideFreeLoc(player.getLocation(), player));
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Teleports outside of residence");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res unstuck"));
    }
}

