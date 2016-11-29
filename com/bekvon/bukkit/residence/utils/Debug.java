/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Debug {
    public static void D(Object message2) {
        Player player = Bukkit.getPlayer((String)"Zrips");
        if (player == null) {
            return;
        }
        player.sendMessage((Object)ChatColor.DARK_GRAY + "[Residence Debug] " + (Object)ChatColor.DARK_AQUA + ChatColor.translateAlternateColorCodes((char)'&', (String)message2.toString()));
    }
}

