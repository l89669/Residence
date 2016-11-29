/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R1.ChatSerializer
 *  net.minecraft.server.v1_8_R1.EntityPlayer
 *  net.minecraft.server.v1_8_R1.EnumTitleAction
 *  net.minecraft.server.v1_8_R1.IChatBaseComponent
 *  net.minecraft.server.v1_8_R1.Packet
 *  net.minecraft.server.v1_8_R1.PacketPlayOutChat
 *  net.minecraft.server.v1_8_R1.PacketPlayOutTitle
 *  net.minecraft.server.v1_8_R1.PlayerConnection
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer
 *  org.bukkit.craftbukkit.v1_8_R1.util.CraftChatMessage
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.actionBarNMS;

import com.bekvon.bukkit.residence.containers.ABInterface;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class v1_8_R1
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
            IChatBaseComponent cbc = ChatSerializer.a((String)("{\"text\": \"" + ChatColor.translateAlternateColorCodes((char)'&', (String)msg) + "\"}"));
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
            PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, CraftChatMessage.fromString((String)ChatColor.translateAlternateColorCodes((char)'&', (String)String.valueOf(title)))[0]);
            Cplayer.getHandle().playerConnection.sendPacket((Packet)packetTitle);
        }
        if (subtitle != null) {
            PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, CraftChatMessage.fromString((String)ChatColor.translateAlternateColorCodes((char)'&', (String)String.valueOf(subtitle)))[0]);
            Cplayer.getHandle().playerConnection.sendPacket((Packet)packetSubtitle);
        }
    }
}

