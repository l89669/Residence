/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.actionBarNMS;

import com.bekvon.bukkit.residence.containers.ABInterface;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class v1_7_R4
implements ABInterface {
    @Override
    public void send(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)msg));
    }

    @Override
    public void send(Player player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)msg));
    }

    @Override
    public void sendTitle(Player player, Object title, Object subtitle) {
    }
}

