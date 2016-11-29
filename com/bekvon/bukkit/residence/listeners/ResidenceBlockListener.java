/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Snowman
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockBurnEvent
 *  org.bukkit.event.block.BlockDispenseEvent
 *  org.bukkit.event.block.BlockFadeEvent
 *  org.bukkit.event.block.BlockFormEvent
 *  org.bukkit.event.block.BlockFromToEvent
 *  org.bukkit.event.block.BlockGrowEvent
 *  org.bukkit.event.block.BlockIgniteEvent
 *  org.bukkit.event.block.BlockIgniteEvent$IgniteCause
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPistonRetractEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.block.BlockSpreadEvent
 *  org.bukkit.event.block.EntityBlockFormEvent
 *  org.bukkit.event.block.LeavesDecayEvent
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.world.StructureGrowEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package com.bekvon.bukkit.residence.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.itemlist.ResidenceItemList;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class ResidenceBlockListener
implements Listener {
    private List<String> MessageInformed = new ArrayList<String>();
    private List<String> ResCreated = new ArrayList<String>();
    private Residence plugin;
    public static final String SourceResidenceName = "SourceResidenceName";

    public ResidenceBlockListener(Residence residence) {
        this.plugin = residence;
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlantGrow(BlockGrowEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.grow, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onVineGrow(BlockSpreadEvent event) {
        if (event.getSource().getType() != Material.VINE) {
            return;
        }
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.grow, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onleaveDecay(LeavesDecayEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.decay, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onTreeGrowt(StructureGrowEvent event) {
        if (Residence.isDisabledWorldListener(event.getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getLocation());
        if (!perms.has(Flags.grow, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onTreeGrow(StructureGrowEvent event) {
        if (Residence.isDisabledWorldListener(event.getWorld())) {
            return;
        }
        ClaimedResidence startRes = Residence.getResidenceManager().getByLoc(event.getLocation());
        List blocks = event.getBlocks();
        int i = 0;
        for (BlockState one : blocks) {
            ClaimedResidence targetRes = Residence.getResidenceManager().getByLoc(one.getLocation());
            if (startRes == null && targetRes != null || targetRes != null && startRes != null && !startRes.getName().equals(targetRes.getName()) && !startRes.isOwner(targetRes.getOwner())) {
                BlockState matas = (BlockState)blocks.get(i);
                matas.setType(Material.AIR);
                blocks.set(i, matas);
            }
            ++i;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockBreak(BlockBreakEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Block block = event.getBlock();
        Material mat = block.getType();
        String world = block.getWorld().getName();
        String group = Residence.getPermissionManager().getGroupNameByPlayer(player);
        if (Residence.getItemManager().isIgnored(mat, group, world)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(block.getLocation());
        if (Residence.getConfigManager().enabledRentSystem() && res != null) {
            String resname = res.getName();
            if (Residence.getConfigManager().preventRentModify() && Residence.getRentManager().isRented(resname)) {
                Residence.msg((CommandSender)player, lm.Rent_ModifyDeny, new Object[0]);
                event.setCancelled(true);
                return;
            }
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer(block.getLocation(), player);
        String pname = player.getName();
        if (res != null && res.getItemIgnoreList().isListed(mat)) {
            return;
        }
        boolean hasdestroy = perms.playerHas(pname, player.getWorld().getName(), Flags.destroy, perms.playerHas(pname, player.getWorld().getName(), Flags.build, true));
        boolean hasContainer = perms.playerHas(pname, player.getWorld().getName(), Flags.container, true);
        if (!hasdestroy && !player.hasPermission("residence.bypass.destroy")) {
            Residence.msg((CommandSender)player, lm.Flag_Deny, new Object[]{Flags.destroy});
            event.setCancelled(true);
        } else if (!hasContainer && mat == Material.CHEST) {
            Residence.msg((CommandSender)player, lm.Flag_Deny, new Object[]{Flags.container});
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockForm(BlockFormEvent event) {
        FlagPermissions perms;
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        if (!(event instanceof EntityBlockFormEvent)) {
            return;
        }
        if (((EntityBlockFormEvent)event).getEntity() instanceof Snowman && !(perms = Residence.getPermsByLoc(event.getBlock().getLocation())).has(Flags.snowtrail, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onIceForm(BlockFormEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        Material ice = Material.getMaterial((String)"FROSTED_ICE");
        if (event.getNewState().getType() != Material.SNOW && event.getNewState().getType() != Material.ICE && ice != null && ice != event.getNewState().getType()) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.iceform, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onIceMelt(BlockFadeEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        if (event.getNewState().getType() != Material.STATIONARY_WATER && event.getBlock().getState().getType() != Material.SNOW && event.getBlock().getState().getType() != Material.SNOW_BLOCK) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.icemelt, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntityType() != EntityType.FALLING_BLOCK) {
            return;
        }
        Entity ent = event.getEntity();
        if (!ent.hasMetadata("SourceResidenceName")) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
            String resName = res == null ? "NULL" : res.getName();
            ent.setMetadata("SourceResidenceName", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)resName));
        } else {
            String resName;
            String saved = ((MetadataValue)ent.getMetadata("SourceResidenceName").get(0)).asString();
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
            String string = resName = res == null ? "NULL" : res.getName();
            if (!saved.equalsIgnoreCase(resName)) {
                event.setCancelled(true);
                ent.remove();
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockFall(EntityChangeBlockEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        if (!Residence.getConfigManager().isBlockFall()) {
            return;
        }
        if (event.getEntityType() != EntityType.FALLING_BLOCK) {
            return;
        }
        if (event.getTo().hasGravity()) {
            return;
        }
        Block block = event.getBlock();
        if (block == null) {
            return;
        }
        if (!Residence.getConfigManager().getBlockFallWorlds().contains(block.getLocation().getWorld().getName())) {
            return;
        }
        if (block.getY() <= Residence.getConfigManager().getBlockFallLevel()) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(block.getLocation());
        Location loc = new Location(block.getLocation().getWorld(), (double)block.getX(), (double)block.getY(), (double)block.getZ());
        int i = loc.getBlockY() - 1;
        while (i >= Residence.getConfigManager().getBlockFallLevel() - 1) {
            loc.setY((double)i);
            if (loc.getBlock().getType() != Material.AIR) {
                ClaimedResidence targetRes = Residence.getResidenceManager().getByLoc(loc);
                if (res == null && targetRes != null || res != null && targetRes == null || res != null && targetRes != null && !res.getName().equals(targetRes.getName())) {
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                }
                return;
            }
            --i;
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onChestPlace(BlockPlaceEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        if (!Residence.getConfigManager().ShowNoobMessage()) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Block block = event.getBlock();
        if (block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST) {
            return;
        }
        ArrayList<String> list2 = Residence.getPlayerManager().getResidenceList(player.getName());
        if (list2.size() != 0) {
            return;
        }
        if (this.MessageInformed.contains(player.getName())) {
            return;
        }
        Residence.msg((CommandSender)player, lm.General_NewPlayerInfo, new Object[0]);
        this.MessageInformed.add(player.getName());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onChestPlaceCreateRes(BlockPlaceEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        if (!Residence.getConfigManager().isNewPlayerUse()) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Block block = event.getBlock();
        if (block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST) {
            return;
        }
        ArrayList<String> list2 = Residence.getPlayerManager().getResidenceList(player.getName());
        if (list2.size() != 0) {
            return;
        }
        if (this.ResCreated.contains(player.getName())) {
            return;
        }
        Location loc = block.getLocation();
        Residence.getSelectionManager().placeLoc1(player, new Location(loc.getWorld(), (double)(loc.getBlockX() - Residence.getConfigManager().getNewPlayerRangeX()), (double)(loc.getBlockY() - Residence.getConfigManager().getNewPlayerRangeY()), (double)(loc.getBlockZ() - Residence.getConfigManager().getNewPlayerRangeZ())), true);
        Residence.getSelectionManager().placeLoc2(player, new Location(loc.getWorld(), (double)(loc.getBlockX() + Residence.getConfigManager().getNewPlayerRangeX()), (double)(loc.getBlockY() + Residence.getConfigManager().getNewPlayerRangeY()), (double)(loc.getBlockZ() + Residence.getConfigManager().getNewPlayerRangeZ())), true);
        boolean created = Residence.getResidenceManager().addResidence(player, player.getName(), Residence.getSelectionManager().getPlayerLoc1(player.getName()), Residence.getSelectionManager().getPlayerLoc2(player.getName()), Residence.getConfigManager().isNewPlayerFree());
        if (created) {
            this.ResCreated.add(player.getName());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Block block = event.getBlock();
        Material mat = block.getType();
        String world = block.getWorld().getName();
        String group = Residence.getPermissionManager().getGroupNameByPlayer(player);
        if (Residence.getItemManager().isIgnored(mat, group, world)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(block.getLocation());
        if (Residence.getConfigManager().enabledRentSystem() && res != null) {
            String resname = res.getName();
            if (Residence.getConfigManager().preventRentModify() && Residence.getRentManager().isRented(resname)) {
                Residence.msg((CommandSender)player, lm.Rent_ModifyDeny, new Object[0]);
                event.setCancelled(true);
                return;
            }
        }
        String pname = player.getName();
        if (res != null && !res.getItemBlacklist().isAllowed(mat)) {
            Residence.msg((CommandSender)player, lm.General_ItemBlacklisted, new Object[0]);
            event.setCancelled(true);
            return;
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer(block.getLocation(), player);
        boolean hasplace = perms.playerHas(pname, world, Flags.place, perms.playerHas(pname, world, Flags.build, true));
        if (!hasplace && !player.hasPermission("residence.bypass.build")) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.place.getName());
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockSpread(BlockSpreadEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        Location loc = event.getBlock().getLocation();
        FlagPermissions perms = Residence.getPermsByLoc(loc);
        if (!perms.has(Flags.spread, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.piston, true)) {
            event.setCancelled(true);
            return;
        }
        List<Block> blocks = Residence.getNms().getPistonRetractBlocks(event);
        if (!event.isSticky()) {
            return;
        }
        ClaimedResidence pistonRes = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());
        for (Block block : blocks) {
            Location locFrom = block.getLocation();
            ClaimedResidence blockFrom = Residence.getResidenceManager().getByLoc(locFrom);
            if (blockFrom == null || blockFrom == pistonRes || pistonRes != null && blockFrom.isOwner(pistonRes.getOwner()) || !blockFrom.getPermissions().has(Flags.pistonprotection, FlagPermissions.FlagCombo.OnlyTrue)) continue;
            event.setCancelled(true);
            break;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.piston, true)) {
            event.setCancelled(true);
            return;
        }
        ClaimedResidence pistonRes = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());
        BlockFace dir = event.getDirection();
        for (Block block : event.getBlocks()) {
            Location locFrom = block.getLocation();
            Location locTo = new Location(block.getWorld(), (double)(block.getX() + dir.getModX()), (double)(block.getY() + dir.getModY()), (double)(block.getZ() + dir.getModZ()));
            ClaimedResidence blockFrom = Residence.getResidenceManager().getByLoc(locFrom);
            ClaimedResidence blockTo = Residence.getResidenceManager().getByLoc(locTo);
            if (pistonRes == null && blockTo != null && blockTo.getPermissions().has(Flags.pistonprotection, FlagPermissions.FlagCombo.OnlyTrue)) {
                event.setCancelled(true);
                return;
            }
            if (blockTo != null && blockFrom == null && blockTo.getPermissions().has(Flags.pistonprotection, FlagPermissions.FlagCombo.OnlyTrue)) {
                event.setCancelled(true);
                return;
            }
            if (blockTo == null || blockFrom == null || (pistonRes == null || blockTo.isOwner(pistonRes.getOwner())) && blockTo.isOwner(blockFrom.getOwner()) || !blockTo.getPermissions().has(Flags.pistonprotection, FlagPermissions.FlagCombo.OnlyTrue)) continue;
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockFromTo(BlockFromToEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        ClaimedResidence fromRes = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());
        ClaimedResidence toRes = Residence.getResidenceManager().getByLoc(event.getToBlock().getLocation());
        FlagPermissions perms = Residence.getPermsByLoc(event.getToBlock().getLocation());
        boolean hasflow = perms.has(Flags.flow, FlagPermissions.FlagCombo.TrueOrNone);
        Material mat = event.getBlock().getType();
        if (fromRes == null && toRes != null || fromRes != null && toRes != null && !fromRes.equals(toRes) && !fromRes.isOwner(toRes.getOwner())) {
            event.setCancelled(true);
            return;
        }
        if (perms.has(Flags.flow, FlagPermissions.FlagCombo.OnlyFalse)) {
            event.setCancelled(true);
            return;
        }
        if (mat == Material.LAVA || mat == Material.STATIONARY_LAVA) {
            if (!perms.has(Flags.lavaflow, hasflow)) {
                event.setCancelled(true);
            }
            return;
        }
        if (mat == Material.WATER || mat == Material.STATIONARY_WATER) {
            if (!perms.has(Flags.waterflow, hasflow)) {
                event.setCancelled(true);
            }
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onLandDryFade(BlockFadeEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        Material mat = event.getBlock().getType();
        if (mat != Material.SOIL) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getNewState().getLocation());
        if (!perms.has(Flags.dryup, true)) {
            event.getBlock().setData(7);
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onLandDryPhysics(BlockPhysicsEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        Material mat = event.getBlock().getType();
        if (mat != Material.SOIL) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.dryup, true)) {
            event.getBlock().setData(7);
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onDispense(BlockDispenseEvent event) {
        ClaimedResidence sourceres;
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Location location = new Location(event.getBlock().getWorld(), (double)event.getVelocity().getBlockX(), (double)event.getVelocity().getBlockY(), (double)event.getVelocity().getBlockZ());
        ClaimedResidence targetres = Residence.getResidenceManager().getByLoc(location);
        if (targetres == null && location.getBlockY() >= Residence.getConfigManager().getPlaceLevel() && Residence.getConfigManager().getNoPlaceWorlds().contains(location.getWorld().getName())) {
            ItemStack mat = event.getItem();
            if (Residence.getConfigManager().isNoLavaPlace() && mat.getType() == Material.LAVA_BUCKET) {
                event.setCancelled(true);
                return;
            }
            if (Residence.getConfigManager().isNoWaterPlace() && mat.getType() == Material.WATER_BUCKET) {
                event.setCancelled(true);
                return;
            }
        }
        if (((sourceres = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation())) == null && targetres != null || sourceres != null && targetres == null || sourceres != null && targetres != null && !sourceres.getName().equals(targetres.getName())) && (event.getItem().getType() == Material.LAVA_BUCKET || event.getItem().getType() == Material.WATER_BUCKET)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onLavaWaterFlow(BlockFromToEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        Material mat = event.getBlock().getType();
        Location location = event.getToBlock().getLocation();
        if (!Residence.getConfigManager().getNoFlowWorlds().contains(location.getWorld().getName())) {
            return;
        }
        if (location.getBlockY() < Residence.getConfigManager().getFlowLevel()) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(location);
        if (res != null) {
            return;
        }
        if (Residence.getConfigManager().isNoLava() && (mat == Material.LAVA || mat == Material.STATIONARY_LAVA)) {
            event.setCancelled(true);
            return;
        }
        if (Residence.getConfigManager().isNoWater() && (mat == Material.WATER || mat == Material.STATIONARY_WATER)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockBurn(BlockBurnEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.firespread, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        BlockIgniteEvent.IgniteCause cause = event.getCause();
        if (cause == BlockIgniteEvent.IgniteCause.SPREAD) {
            FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
            if (!perms.has(Flags.firespread, true)) {
                event.setCancelled(true);
            }
        } else if (cause == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            Player player = event.getPlayer();
            FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getBlock().getLocation(), player);
            if (player != null && !perms.playerHas(player.getName(), player.getWorld().getName(), Flags.ignite, true) && !Residence.isResAdminOn(player)) {
                event.setCancelled(true);
                Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.ignite.getName());
            }
        } else {
            FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
            if (!perms.has(Flags.ignite, true)) {
                event.setCancelled(true);
            }
        }
    }
}

