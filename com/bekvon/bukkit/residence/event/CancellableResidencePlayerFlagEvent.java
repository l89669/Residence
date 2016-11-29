/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.ResidenceFlagEvent;
import com.bekvon.bukkit.residence.event.ResidencePlayerFlagEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class CancellableResidencePlayerFlagEvent
extends ResidencePlayerFlagEvent
implements Cancellable {
    protected boolean cancelled;

    public CancellableResidencePlayerFlagEvent(String eventName, ClaimedResidence resref, Player player, String flag, ResidenceFlagEvent.FlagType type, String target) {
        super(eventName, resref, player, flag, type, target);
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean bln) {
        this.cancelled = bln;
    }
}

