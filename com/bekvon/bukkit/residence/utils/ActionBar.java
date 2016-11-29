/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.json.simple.JSONObject
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.containers.ABInterface;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class ActionBar
implements ABInterface {
    private String version = "";
    private Object packet;
    private Method getHandle;
    private Method sendPacket;
    private Field playerConnection;
    private Class<?> nmsChatSerializer;
    private Class<?> nmsIChatBaseComponent;
    private Class<?> packetType;
    private Constructor<?> constructor;
    private boolean simpleMessages = false;

    public ActionBar() {
        try {
            String[] v = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
            this.version = v[v.length - 1];
            this.packetType = Class.forName(this.getPacketPlayOutChat());
            Class typeCraftPlayer = Class.forName(this.getCraftPlayerClasspath());
            Class typeNMSPlayer = Class.forName(this.getNMSPlayerClasspath());
            Class typePlayerConnection = Class.forName(this.getPlayerConnectionClasspath());
            this.nmsChatSerializer = Class.forName(this.getChatSerializerClasspath());
            this.nmsIChatBaseComponent = Class.forName(this.getIChatBaseComponentClasspath());
            this.getHandle = typeCraftPlayer.getMethod("getHandle", new Class[0]);
            this.playerConnection = typeNMSPlayer.getField("playerConnection");
            this.sendPacket = typePlayerConnection.getMethod("sendPacket", Class.forName(this.getPacketClasspath()));
            this.constructor = !this.version.contains("1_7") ? this.packetType.getConstructor(this.nmsIChatBaseComponent, Byte.TYPE) : this.packetType.getConstructor(this.nmsIChatBaseComponent, Integer.TYPE);
        }
        catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | SecurityException ex) {
            this.simpleMessages = true;
            Bukkit.getLogger().log(Level.SEVERE, "Your server can't fully suport action bar messages. They will be shown in chat instead.");
        }
    }

    @Override
    public void send(CommandSender sender, String msg) {
        if (sender instanceof Player) {
            this.send((Player)sender, msg);
        } else {
            sender.sendMessage(msg);
        }
    }

    @Override
    public void send(Player receivingPacket, String msg) {
        if (this.simpleMessages) {
            receivingPacket.sendMessage(msg);
            return;
        }
        try {
            Object serialized = this.nmsChatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + ChatColor.translateAlternateColorCodes((char)'&', (String)JSONObject.escape((String)msg)) + "\"}");
            this.packet = !this.version.contains("1_7") ? this.constructor.newInstance(serialized, Byte.valueOf(2)) : this.constructor.newInstance(serialized, 2);
            Object player = this.getHandle.invoke((Object)receivingPacket, new Object[0]);
            Object connection = this.playerConnection.get(player);
            this.sendPacket.invoke(connection, this.packet);
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            this.simpleMessages = true;
            Bukkit.getLogger().log(Level.SEVERE, "Your server can't fully suport action bar messages. They will be shown in chat instead.");
        }
    }

    private String getCraftPlayerClasspath() {
        return "org.bukkit.craftbukkit." + this.version + ".entity.CraftPlayer";
    }

    private String getPlayerConnectionClasspath() {
        return "net.minecraft.server." + this.version + ".PlayerConnection";
    }

    private String getNMSPlayerClasspath() {
        return "net.minecraft.server." + this.version + ".EntityPlayer";
    }

    private String getPacketClasspath() {
        return "net.minecraft.server." + this.version + ".Packet";
    }

    private String getIChatBaseComponentClasspath() {
        return "net.minecraft.server." + this.version + ".IChatBaseComponent";
    }

    private String getChatSerializerClasspath() {
        if (this.version.equals("v1_8_R1") || this.version.contains("1_7")) {
            return "net.minecraft.server." + this.version + ".ChatSerializer";
        }
        return "net.minecraft.server." + this.version + ".IChatBaseComponent$ChatSerializer";
    }

    private String getPacketPlayOutChat() {
        return "net.minecraft.server." + this.version + ".PacketPlayOutChat";
    }

    @Override
    public void sendTitle(Player player, Object title, Object subtitle) {
    }
}

