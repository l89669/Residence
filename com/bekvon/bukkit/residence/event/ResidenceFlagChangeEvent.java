/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.CancellableResidencePlayerFlagEvent;
import com.bekvon.bukkit.residence.event.ResidenceFlagEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceFlagChangeEvent
extends CancellableResidencePlayerFlagEvent {
    private static final HandlerList handlers = new HandlerList();
    FlagPermissions.FlagState newstate;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceFlagChangeEvent(ClaimedResidence resref, Player player, String flag, ResidenceFlagEvent.FlagType type, FlagPermissions.FlagState newState, String target) {
        super("RESIDENCE_FLAG_CHANGE", resref, player, flag, type, target);
        this.newstate = newState;
    }

    public FlagPermissions.FlagState getNewState() {
        return this.newstate;
    }
}

