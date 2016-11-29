/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockExplodeEvent
 *  org.bukkit.event.player.PlayerInteractAtEntityEvent
 */
package com.bekvon.bukkit.residence.allNms;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class v1_8Events
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerInteractAtArmoStand(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (!Residence.getNms().isArmorStandEntity(ent.getType())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer(ent.getLocation(), player);
        String world = player.getWorld().getName();
        if (!perms.playerHas(player.getName(), world, Flags.container, perms.playerHas(player.getName(), world, Flags.use, true))) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.container.getName());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
        Location loc = event.getBlock().getLocation();
        if (Residence.isDisabledWorldListener(loc.getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        FlagPermissions world = Residence.getWorldFlags().getPerms(loc.getWorld().getName());
        ArrayList<Block> preserve = new ArrayList<Block>();
        for (Block block : event.blockList()) {
            FlagPermissions blockperms = Residence.getPermsByLoc(block.getLocation());
            if (blockperms.has(Flags.explode, world.has(Flags.explode, true))) continue;
            preserve.add(block);
        }
        for (Block block : preserve) {
            event.blockList().remove((Object)block);
        }
    }
}

