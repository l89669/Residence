/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.BlockPistonRetractEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.ItemStack
 */
package com.bekvon.bukkit.residence.containers;

import java.util.List;
import java.util.Map;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public interface NMS {
    public List<Block> getPistonRetractBlocks(BlockPistonRetractEvent var1);

    public boolean isAnimal(Entity var1);

    public boolean isArmorStandEntity(EntityType var1);

    public boolean isArmorStandMaterial(Material var1);

    public boolean isCanUseEntity_BothClick(Material var1, Block var2);

    public boolean isEmptyBlock(Block var1);

    public boolean isSpectator(GameMode var1);

    public void addDefaultFlags(Map<Material, String> var1);

    public boolean isPlate(Material var1);

    public boolean isMainHand(PlayerInteractEvent var1);

    public Block getTargetBlock(Player var1, int var2);

    public ItemStack itemInMainHand(Player var1);

    public boolean isChorusTeleport(PlayerTeleportEvent.TeleportCause var1);

    public boolean isBoat(Material var1);
}

