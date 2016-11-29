/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.ResidenceEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.event.HandlerList;

public class ResidenceFlagEvent
extends ResidenceEvent {
    private static final HandlerList handlers = new HandlerList();
    String flagname;
    FlagType flagtype;
    FlagPermissions.FlagState flagstate;
    String flagtarget;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceFlagEvent(String eventName, ClaimedResidence resref, String flag, FlagType type, String target) {
        super(eventName, resref);
        this.flagname = flag;
        this.flagtype = type;
        this.flagtarget = target;
    }

    public String getFlag() {
        return this.flagname;
    }

    public FlagType getFlagType() {
        return this.flagtype;
    }

    public String getFlagTargetPlayerOrGroup() {
        return this.flagtarget;
    }

    public static enum FlagType {
        RESIDENCE,
        GROUP,
        PLAYER;
        

        private FlagType(String string2, int n2) {
        }
    }

}

