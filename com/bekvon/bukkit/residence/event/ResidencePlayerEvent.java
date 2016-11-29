/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.event.ResidenceEvent;
import com.bekvon.bukkit.residence.event.ResidencePlayerEventInterface;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResidencePlayerEvent
extends ResidenceEvent
implements ResidencePlayerEventInterface {
    Player p;

    public ResidencePlayerEvent(String eventName, ClaimedResidence resref, Player player) {
        super(eventName, resref);
        this.res = resref;
        this.p = player;
    }

    @Override
    public boolean isPlayer() {
        if (this.p != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAdmin() {
        if (this.isPlayer()) {
            return Residence.getPermissionManager().isResidenceAdmin((CommandSender)this.p);
        }
        return true;
    }

    @Override
    public Player getPlayer() {
        return this.p;
    }
}

