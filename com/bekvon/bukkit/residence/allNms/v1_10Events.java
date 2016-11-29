/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 */
package com.bekvon.bukkit.residence.allNms;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class v1_10Events
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerFireInteract(EntityDamageEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.HOT_FLOOR) {
            return;
        }
        Entity ent = event.getEntity();
        if (!Residence.getPermsByLoc(ent.getLocation()).has(Flags.hotfloor, true)) {
            event.setCancelled(true);
            return;
        }
    }
}

