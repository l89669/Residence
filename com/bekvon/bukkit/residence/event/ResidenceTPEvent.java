/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.CancellableResidencePlayerEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceTPEvent
extends CancellableResidencePlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    Player reqPlayer;
    Location loc;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceTPEvent(ClaimedResidence resref, Location teleloc, Player player, Player reqplayer) {
        super("RESIDENCE_TP", resref, player);
        this.reqPlayer = reqplayer;
        this.loc = teleloc;
    }

    public Player getRequestingPlayer() {
        return this.reqPlayer;
    }

    public Location getTeleportLocation() {
        return this.loc;
    }
}

