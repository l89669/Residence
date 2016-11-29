/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_9_R2.EntityPlayer
 *  net.minecraft.server.v1_9_R2.IChatBaseComponent
 *  net.minecraft.server.v1_9_R2.IChatBaseComponent$ChatSerializer
 *  net.minecraft.server.v1_9_R2.Packet
 *  net.minecraft.server.v1_9_R2.PacketPlayOutChat
 *  net.minecraft.server.v1_9_R2.PacketPlayOutTitle
 *  net.minecraft.server.v1_9_R2.PacketPlayOutTitle$EnumTitleAction
 *  net.minecraft.server.v1_9_R2.PlayerConnection
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer
 *  org.bukkit.craftbukkit.v1_9_R2.util.CraftChatMessage
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.actionBarNMS;

import com.bekvon.bukkit.residence.containers.ABInterface;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class v1_9_R2
implements ABInterface {
    @Override
    public void send(CommandSender sender, String msg) {
        if (sender instanceof Player) {
            this.send((Player)sender, msg);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)msg));
        }
    }

    @Override
    public void send(Player player, String msg) {
        try {
            CraftPlayer p = (CraftPlayer)player;
            IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + ChatColor.translateAlternateColorCodes((char)'&', (String)msg) + "\"}"));
            PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, 2);
            p.getHandle().playerConnection.sendPacket((Packet)ppoc);
        }
        catch (Exception e) {
            player.sendMessage(msg);
        }
    }

    @Override
    public void sendTitle(Player player, Object title, Object subtitle) {
        CraftPlayer Cplayer = (CraftPlayer)player;
        if (title != null) {
            PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, CraftChatMessage.fromString((String)ChatColor.translateAlternateColorCodes((char)'&', (String)String.valueOf(title)))[0]);
            Cplayer.getHandle().playerConnection.sendPacket((Packet)packetTitle);
        }
        if (subtitle != null) {
            PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, CraftChatMessage.fromString((String)ChatColor.translateAlternateColorCodes((char)'&', (String)String.valueOf(subtitle)))[0]);
            Cplayer.getHandle().playerConnection.sendPacket((Packet)packetSubtitle);
        }
    }
}

