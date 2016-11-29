/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.bekvon.bukkit.residence.listeners;

import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ResidenceFixesListener
implements Listener {
    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=1)
    public void onAnvilPlace(PlayerInteractEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        ItemStack iih = event.getItem();
        if (iih == null || iih.getType() == Material.AIR || iih.getType() != Material.ANVIL) {
            return;
        }
        BlockFace face = event.getBlockFace();
        Block bclicked = event.getClickedBlock();
        if (bclicked == null) {
            return;
        }
        Location loc = new Location(bclicked.getWorld(), (double)(bclicked.getX() + face.getModX()), (double)(bclicked.getY() + face.getModY()), (double)(bclicked.getZ() + face.getModZ()));
        Block block = loc.getBlock();
        if (block == null || block.getType() == Material.AIR || block.getType() != Material.SKULL && block.getType() != Material.FLOWER_POT) {
            return;
        }
        event.setCancelled(true);
    }
}

