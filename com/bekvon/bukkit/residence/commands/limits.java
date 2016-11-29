/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
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
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class limits
implements cmd {
    @CommandAnnotation(simple=1, priority=900)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        Player target;
        if (!(sender instanceof Player) && args.length < 2) {
            return false;
        }
        if (args.length != 1 && args.length != 2) {
            return false;
        }
        String[] tempArgs = args;
        boolean rsadm = false;
        if (tempArgs.length == 1) {
            target = (Player)sender;
            rsadm = true;
        } else {
            target = Residence.getOfflinePlayer(tempArgs[1]);
        }
        if (target == null) {
            return false;
        }
        Residence.getPermissionManager().updateGroupNameForPlayer(target.getName(), target.isOnline() ? target.getPlayer().getLocation().getWorld().getName() : Residence.getConfigManager().getDefaultWorld(), true);
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(target.getName());
        rPlayer.getGroup().printLimits(sender, (OfflinePlayer)target, rsadm);
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Show your limits.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res limits (playerName)", "Shows the limitations you have on creating and managing residences."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[playername]"));
    }
}

