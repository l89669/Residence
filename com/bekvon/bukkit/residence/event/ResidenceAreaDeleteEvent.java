/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.CancellableResidencePlayerEvent;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceAreaDeleteEvent
extends CancellableResidencePlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    ResidenceDeleteEvent.DeleteCause cause;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceAreaDeleteEvent(Player player, ClaimedResidence resref, ResidenceDeleteEvent.DeleteCause delcause) {
        super("RESIDENCE_AREA_DELETE", resref, player);
        this.cause = delcause;
    }

    public ResidenceDeleteEvent.DeleteCause getCause() {
        return this.cause;
    }
}

