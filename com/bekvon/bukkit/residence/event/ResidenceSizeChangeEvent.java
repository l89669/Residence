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
import com.bekvon.bukkit.residence.protection.CuboidArea;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceSizeChangeEvent
extends CancellableResidencePlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    protected String resname;
    CuboidArea oldarea;
    CuboidArea newarea;
    ClaimedResidence res;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceSizeChangeEvent(Player player, ClaimedResidence res, CuboidArea oldarea, CuboidArea newarea) {
        super("RESIDENCE_SIZE_CHANGE", res, player);
        this.resname = res.getName();
        this.res = res;
        this.oldarea = oldarea;
        this.newarea = newarea;
    }

    public String getResidenceName() {
        return this.resname;
    }

    @Override
    public ClaimedResidence getResidence() {
        return this.res;
    }

    public CuboidArea getOldArea() {
        return this.oldarea;
    }

    public CuboidArea getNewArea() {
        return this.newarea;
    }
}

