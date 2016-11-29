/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.CancellableResidencePlayerEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceChatEvent
extends CancellableResidencePlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    protected String message;
    ChatColor color = ChatColor.WHITE;
    private String prefix = "";

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceChatEvent(ClaimedResidence resref, Player player, String prefix, String message2, ChatColor color) {
        super("RESIDENCE_CHAT_EVENT", resref, player);
        this.message = message2;
        this.prefix = prefix;
        this.color = color;
    }

    public String getChatMessage() {
        return this.message;
    }

    public String getChatprefix() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)this.prefix);
    }

    public void setChatprefix(String prefix) {
        this.prefix = prefix;
    }

    public void setChatMessage(String newmessage) {
        this.message = newmessage;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public void setColor(ChatColor c) {
        this.color = c;
    }
}

