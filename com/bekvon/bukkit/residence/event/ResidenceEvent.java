/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ResidenceEvent
extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String message;
    ClaimedResidence res;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceEvent(String eventName, ClaimedResidence resref) {
        this.message = eventName;
        this.res = resref;
    }

    public String getMessage() {
        return this.message;
    }

    public ClaimedResidence getResidence() {
        return this.res;
    }
}

