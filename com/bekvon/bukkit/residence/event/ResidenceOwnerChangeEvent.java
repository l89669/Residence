/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.ResidenceEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.event.HandlerList;

public class ResidenceOwnerChangeEvent
extends ResidenceEvent {
    private static final HandlerList handlers = new HandlerList();
    protected String newowner;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceOwnerChangeEvent(ClaimedResidence resref, String newOwner) {
        super("RESIDENCE_OWNER_CHANGE", resref);
        this.newowner = newOwner;
    }

    public String getNewOwner() {
        return this.newowner;
    }
}

