/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.ResidencePlayerEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceChangedEvent
extends ResidencePlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private ClaimedResidence from = null;
    private ClaimedResidence to = null;

    public ResidenceChangedEvent(ClaimedResidence from, ClaimedResidence to, Player player) {
        super("RESIDENCE_CHANGE", null, player);
        this.from = from;
        this.to = to;
    }

    public ClaimedResidence getFrom() {
        return this.from;
    }

    public ClaimedResidence getTo() {
        return this.to;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

