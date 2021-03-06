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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class padd
implements cmd {
    @CommandAnnotation(simple=1, priority=400)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        String baseCmd = "res";
        if (resadmin2) {
            baseCmd = "resadmin";
        }
        if (args.length == 2) {
            if (!Residence.isPlayerExist((CommandSender)player, args[1], true)) {
                return false;
            }
            player.performCommand(String.valueOf(baseCmd) + " pset " + args[1] + " trusted true");
            return true;
        }
        if (args.length == 3) {
            if (!Residence.isPlayerExist((CommandSender)player, args[2], true)) {
                return false;
            }
            player.performCommand(String.valueOf(baseCmd) + " pset " + args[1] + " " + args[2] + " trusted true");
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Add player to residence.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res padd <residence name> [player]", "Adds essential flags for player"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]%%[playername]", "[playername]"));
    }
}

