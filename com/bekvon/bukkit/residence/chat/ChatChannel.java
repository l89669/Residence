/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.PluginManager
 */
package com.bekvon.bukkit.residence.chat;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.event.ResidenceChatEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

public class ChatChannel {
    protected String channelName;
    protected List<String> members;
    protected String ChatPrefix = "";
    protected ChatColor ChannelColor = ChatColor.WHITE;

    public ChatChannel(String channelName, String ChatPrefix, ChatColor chatColor) {
        this.channelName = channelName;
        this.ChatPrefix = ChatPrefix;
        this.ChannelColor = chatColor;
        this.members = new ArrayList<String>();
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChatPrefix(String ChatPrefix) {
        this.ChatPrefix = ChatPrefix;
    }

    public void setChannelColor(ChatColor ChannelColor) {
        this.ChannelColor = ChannelColor;
    }

    public void chat(String sourcePlayer, String message2) {
        Server serv = Residence.getServ();
        ResidenceChatEvent cevent = new ResidenceChatEvent(Residence.getResidenceManager().getByName(this.channelName), serv.getPlayer(sourcePlayer), this.ChatPrefix, message2, this.ChannelColor);
        Residence.getServ().getPluginManager().callEvent((Event)cevent);
        if (cevent.isCancelled()) {
            return;
        }
        for (String member : this.members) {
            Player player = serv.getPlayer(member);
            Residence.msg(player, String.valueOf(cevent.getChatprefix()) + " " + (Object)Residence.getConfigManager().getChatColor() + sourcePlayer + ": " + (Object)cevent.getColor() + cevent.getChatMessage());
        }
        Bukkit.getConsoleSender().sendMessage("ResidentialChat[" + this.channelName + "] - " + sourcePlayer + ": " + ChatColor.stripColor((String)cevent.getChatMessage()));
    }

    public void join(String player) {
        if (!this.members.contains(player)) {
            this.members.add(player);
        }
    }

    public void leave(String player) {
        this.members.remove(player);
    }

    public boolean hasMember(String player) {
        return this.members.contains(player);
    }

    public int memberCount() {
        return this.members.size();
    }
}

