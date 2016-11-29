/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.CancellableResidencePlayerEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceDeleteEvent
extends CancellableResidencePlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    DeleteCause cause;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceDeleteEvent(Player player, ClaimedResidence resref, DeleteCause delcause) {
        super("RESIDENCE_DELETE", resref, player);
        this.cause = delcause;
    }

    public DeleteCause getCause() {
        return this.cause;
    }

    public static enum DeleteCause {
        LEASE_EXPIRE,
        PLAYER_DELETE,
        OTHER;
        

        private DeleteCause(String string2, int n2) {
        }
    }

}

