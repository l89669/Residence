/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Cancellable
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.ResidenceEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.event.Cancellable;

public class CancellableResidenceEvent
extends ResidenceEvent
implements Cancellable {
    protected boolean cancelled;

    public CancellableResidenceEvent(String eventName, ClaimedResidence resref) {
        super(eventName, resref);
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean bln) {
        this.cancelled = bln;
    }
}

