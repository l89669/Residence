/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class version
implements cmd {
    @CommandAnnotation(simple=0, priority=5900)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        sender.sendMessage((Object)ChatColor.GRAY + "------------------------------------");
        sender.sendMessage((Object)ChatColor.RED + "This server running " + (Object)ChatColor.GOLD + "Residence" + (Object)ChatColor.RED + " version: " + (Object)ChatColor.BLUE + Residence.getResidenceVersion());
        sender.sendMessage((Object)ChatColor.GREEN + "Created by: " + (Object)ChatColor.YELLOW + "bekvon");
        sender.sendMessage((Object)ChatColor.GREEN + "Updated to 1.8 by: " + (Object)ChatColor.YELLOW + "DartCZ");
        sender.sendMessage((Object)ChatColor.GREEN + "Currently maintained by: " + (Object)ChatColor.YELLOW + "Zrips");
        String names = null;
        for (String auth : Residence.getAuthors()) {
            names = names == null ? auth : String.valueOf(names) + ", " + auth;
        }
        sender.sendMessage((Object)ChatColor.GREEN + "Authors: " + (Object)ChatColor.YELLOW + names);
        sender.sendMessage((Object)ChatColor.DARK_AQUA + "For a command list, and help, see the wiki:");
        sender.sendMessage((Object)ChatColor.GREEN + "https://github.com/bekvon/Residence/wiki");
        sender.sendMessage((Object)ChatColor.AQUA + "Visit the Spigot Resource page at:");
        sender.sendMessage((Object)ChatColor.BLUE + "https://www.spigotmc.org/resources/residence.11480/");
        sender.sendMessage((Object)ChatColor.GRAY + "------------------------------------");
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "how residence version");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res version"));
    }
}

