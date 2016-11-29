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

public class ResidenceAreaAddEvent
extends CancellableResidencePlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    protected String resname;
    CuboidArea area;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceAreaAddEvent(Player player, String newname, ClaimedResidence resref, CuboidArea resarea) {
        super("RESIDENCE_AREA_ADD", resref, player);
        this.resname = newname;
        this.area = resarea;
    }

    public String getResidenceName() {
        return this.resname;
    }

    public CuboidArea getPhysicalArea() {
        return this.area;
    }
}

