/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.ResidencePlayerEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class CancellableResidencePlayerEvent
extends ResidencePlayerEvent
implements Cancellable {
    protected boolean cancelled = false;

    public CancellableResidencePlayerEvent(String eventName, ClaimedResidence resref, Player player) {
        super(eventName, resref, player);
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean bln) {
        this.cancelled = bln;
    }
}

