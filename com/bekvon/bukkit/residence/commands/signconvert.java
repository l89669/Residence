/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class signconvert
implements cmd {
    @CommandAnnotation(simple=0, priority=5600)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (args.length != 0) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
                Residence.getSignUtil().convertSigns(sender);
            } else {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            }
        } else {
            Residence.getSignUtil().convertSigns(sender);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Converts signs from ResidenceSign plugin");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res signconvert", "Will try to convert saved sign data from 3rd party plugin"));
    }
}

