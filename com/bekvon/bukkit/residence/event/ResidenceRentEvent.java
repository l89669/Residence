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

public class ResidenceRentEvent
extends CancellableResidencePlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    RentEventType eventtype;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceRentEvent(ClaimedResidence resref, Player player, RentEventType type) {
        super("RESIDENCE_RENT_EVENT", resref, player);
        this.eventtype = type;
    }

    public RentEventType getCause() {
        return this.eventtype;
    }

    public static enum RentEventType {
        RENT,
        UNRENT,
        RENTABLE,
        UNRENTABLE,
        RENT_EXPIRE;
        

        private RentEventType(String string2, int n2) {
        }
    }

}

