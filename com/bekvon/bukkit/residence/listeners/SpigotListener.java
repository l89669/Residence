/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerItemDamageEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.bekvon.bukkit.residence.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class SpigotListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack held;
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        FlagPermissions perms = Residence.getPermsByLoc(loc);
        if (perms.has(Flags.nodurability, false) && (held = Residence.getNms().itemInMainHand(player)).getType() != Material.AIR) {
            held.setDurability(held.getDurability());
            player.setItemInHand(held);
            event.setDamage(0);
            event.setCancelled(true);
        }
    }
}

