/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Ghast
 *  org.bukkit.entity.Hanging
 *  org.bukkit.entity.ItemFrame
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Monster
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Slime
 *  org.bukkit.entity.Tameable
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.entity.Vehicle
 *  org.bukkit.entity.minecart.HopperMinecart
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityCombustByEntityEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.EntityInteractEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.entity.PlayerLeashEntityEvent
 *  org.bukkit.event.entity.PotionSplashEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.hanging.HangingBreakByEntityEvent
 *  org.bukkit.event.hanging.HangingPlaceEvent
 *  org.bukkit.event.inventory.InventoryMoveItemEvent
 *  org.bukkit.event.vehicle.VehicleDestroyEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.util.Vector
 */
package com.bekvon.bukkit.residence.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class ResidenceEntityListener
implements Listener {
    Residence plugin;
    private static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$Material;
    private static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$event$entity$CreatureSpawnEvent$SpawnReason;
    private static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$entity$EntityType;

    public ResidenceEntityListener(Residence plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onMinecartHopperItemMove(InventoryMoveItemEvent event) {
        if (!(event.getInitiator().getHolder() instanceof HopperMinecart)) {
            return;
        }
        HopperMinecart hopper = (HopperMinecart)event.getInitiator().getHolder();
        if (Residence.isDisabledWorldListener(hopper.getWorld())) {
            return;
        }
        Block block = hopper.getLocation().getBlock();
        switch (ResidenceEntityListener.$SWITCH_TABLE$org$bukkit$Material()[block.getType().ordinal()]) {
            case 28: 
            case 29: 
            case 67: 
            case 158: {
                return;
            }
        }
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onEndermanChangeBlock(EntityChangeBlockEvent event) {
        if (Residence.isDisabledWorldListener(event.getBlock().getWorld())) {
            return;
        }
        if (event.getEntityType() != EntityType.ENDERMAN) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.destroy, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onEntityInteract(EntityInteractEvent event) {
        Block block = event.getBlock();
        if (block == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(block.getWorld())) {
            return;
        }
        Material mat = block.getType();
        Entity entity = event.getEntity();
        FlagPermissions perms = Residence.getPermsByLoc(block.getLocation());
        boolean hastrample = perms.has(Flags.trample, perms.has(Flags.build, true));
        if (!(hastrample || entity.getType() == EntityType.FALLING_BLOCK || mat != Material.SOIL && mat != Material.SOUL_SAND)) {
            event.setCancelled(true);
        }
    }

    public static boolean isMonster(Entity ent) {
        if (!(ent instanceof Monster || ent instanceof Slime || ent instanceof Ghast)) {
            return false;
        }
        return true;
    }

    private static boolean isTamed(Entity ent) {
        return ent instanceof Tameable ? ((Tameable)ent).isTamed() : false;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void AnimalKilling(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(entity.getWorld())) {
            return;
        }
        if (!Residence.getNms().isAnimal(entity)) {
            return;
        }
        Entity damager = event.getDamager();
        if (!(damager instanceof Arrow) && !(damager instanceof Player)) {
            return;
        }
        if (damager instanceof Arrow && !(((Arrow)damager).getShooter() instanceof Player)) {
            return;
        }
        Player cause = null;
        cause = damager instanceof Player ? (Player)damager : (Player)((Arrow)damager).getShooter();
        if (cause == null) {
            return;
        }
        if (Residence.isResAdminOn(cause)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(entity.getLocation());
        if (res == null) {
            return;
        }
        if (res.getPermissions().playerHas(cause, Flags.animalkilling, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)cause, lm.Residence_FlagDeny, Flags.animalkilling.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void AnimalKillingByFlame(EntityCombustByEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity == null) {
            return;
        }
        if (!Residence.getNms().isAnimal(entity)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(entity.getLocation());
        if (res == null) {
            return;
        }
        Entity damager = event.getCombuster();
        if (!(damager instanceof Arrow) && !(damager instanceof Player)) {
            return;
        }
        if (damager instanceof Arrow && !(((Arrow)damager).getShooter() instanceof Player)) {
            return;
        }
        Player cause = null;
        cause = damager instanceof Player ? (Player)damager : (Player)((Arrow)damager).getShooter();
        if (cause == null) {
            return;
        }
        if (Residence.isResAdminOn(cause)) {
            return;
        }
        if (res.getPermissions().playerHas(cause.getName(), Flags.animalkilling, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)cause, lm.Residence_FlagDeny, Flags.animalkilling.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void AnimalDamageByMobs(EntityDamageByEntityEvent event) {
        FlagPermissions world;
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity == null) {
            return;
        }
        if (!Residence.getNms().isAnimal(entity)) {
            return;
        }
        Entity damager = event.getDamager();
        if (damager instanceof Projectile && ((Projectile)damager).getShooter() instanceof Player || damager instanceof Player) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(entity.getLocation());
        if (!perms.has(Flags.animalkilling, (world = Residence.getWorldFlags().getPerms(entity.getWorld().getName())).has(Flags.animalkilling, true))) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void OnEntityDeath(EntityDeathEvent event) {
        LivingEntity ent = event.getEntity();
        if (ent == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(ent.getWorld())) {
            return;
        }
        if (ent instanceof Player) {
            return;
        }
        Location loc = ent.getLocation();
        FlagPermissions perms = Residence.getPermsByLoc(loc);
        if (!perms.has(Flags.mobitemdrop, true)) {
            event.getDrops().clear();
        }
        if (!perms.has(Flags.mobexpdrop, true)) {
            event.setDroppedExp(0);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void VehicleDestroy(VehicleDestroyEvent event) {
        FlagPermissions perms;
        Entity damager = event.getAttacker();
        if (damager == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(damager.getWorld())) {
            return;
        }
        Vehicle vehicle = event.getVehicle();
        if (vehicle == null) {
            return;
        }
        if ((damager instanceof Projectile && !(((Projectile)damager).getShooter() instanceof Player) || !(damager instanceof Player)) && !(perms = Residence.getPermsByLoc(vehicle.getLocation())).has(Flags.vehicledestroy, true)) {
            event.setCancelled(true);
            return;
        }
        Player cause = null;
        if (damager instanceof Player) {
            cause = (Player)damager;
        } else if (damager instanceof Projectile && ((Projectile)damager).getShooter() instanceof Player) {
            cause = (Player)((Projectile)damager).getShooter();
        }
        if (cause == null) {
            return;
        }
        if (Residence.isResAdminOn(cause)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(vehicle.getLocation());
        if (res == null) {
            return;
        }
        if (res.getPermissions().playerHas(cause.getName(), Flags.vehicledestroy, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)cause, lm.Residence_FlagDeny, Flags.vehicledestroy.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void MonsterKilling(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(entity.getWorld())) {
            return;
        }
        if (!ResidenceEntityListener.isMonster(entity)) {
            return;
        }
        Entity damager = event.getDamager();
        if (!(damager instanceof Arrow) && !(damager instanceof Player)) {
            return;
        }
        if (damager instanceof Arrow && !(((Arrow)damager).getShooter() instanceof Player)) {
            return;
        }
        Player cause = null;
        cause = damager instanceof Player ? (Player)damager : (Player)((Arrow)damager).getShooter();
        if (cause == null) {
            return;
        }
        if (Residence.isResAdminOn(cause)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(entity.getLocation());
        if (res == null) {
            return;
        }
        if (res.getPermissions().playerHas(cause.getName(), Flags.mobkilling, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)cause, lm.Residence_FlagDeny, Flags.mobkilling.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void AnimalLeash(PlayerLeashEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        Entity entity = event.getEntity();
        if (!Residence.getNms().isAnimal(entity)) {
            return;
        }
        if (Residence.isResAdminOn(player)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(entity.getLocation());
        if (res == null) {
            return;
        }
        if (res.getPermissions().playerHas(player.getName(), Flags.leash, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, new Object[]{Flags.leash, res.getName()});
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onWitherSpawn(CreatureSpawnEvent event) {
        LivingEntity ent = event.getEntity();
        if (ent == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(ent.getWorld())) {
            return;
        }
        if (ent.getType() != EntityType.WITHER) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getLocation());
        if (perms.has(Flags.witherspawn, FlagPermissions.FlagCombo.OnlyFalse)) {
            event.setCancelled(true);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity ent = event.getEntity();
        if (ent == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(ent.getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getLocation());
        if (Residence.getNms().isAnimal((Entity)ent)) {
            if (!perms.has(Flags.animals, true)) {
                event.setCancelled(true);
                return;
            }
            switch (ResidenceEntityListener.$SWITCH_TABLE$org$bukkit$event$entity$CreatureSpawnEvent$SpawnReason()[event.getSpawnReason().ordinal()]) {
                case 10: {
                    return;
                }
                case 8: 
                case 9: 
                case 25: 
                case 26: {
                    if (perms.has(Flags.canimals, FlagPermissions.FlagCombo.OnlyFalse)) return;
                    event.setCancelled(true);
                    return;
                }
                case 1: 
                case 2: 
                case 3: 
                case 5: 
                case 11: 
                case 12: 
                case 13: 
                case 16: 
                case 17: 
                case 19: 
                case 20: 
                case 22: {
                    if (perms.has(Flags.nanimals, true)) return;
                    event.setCancelled(true);
                    return;
                }
                case 4: 
                case 6: {
                    if (perms.has(Flags.sanimals, true)) return;
                    event.setCancelled(true);
                    return;
                }
                default: {
                    return;
                }
            }
        } else {
            if (!ResidenceEntityListener.isMonster((Entity)ent)) return;
            if (perms.has(Flags.monsters, FlagPermissions.FlagCombo.OnlyFalse)) {
                event.setCancelled(true);
                return;
            }
            switch (ResidenceEntityListener.$SWITCH_TABLE$org$bukkit$event$entity$CreatureSpawnEvent$SpawnReason()[event.getSpawnReason().ordinal()]) {
                case 10: 
                case 25: 
                case 26: {
                    if (perms.has(Flags.cmonsters, true)) return;
                    event.setCancelled(true);
                    return;
                }
                case 1: 
                case 2: 
                case 3: 
                case 7: 
                case 14: 
                case 16: 
                case 17: 
                case 18: 
                case 19: 
                case 21: 
                case 22: {
                    if (perms.has(Flags.nmonsters, true)) return;
                    event.setCancelled(true);
                    return;
                }
                case 4: 
                case 6: {
                    if (perms.has(Flags.smonsters, true)) return;
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onHangingPlace(HangingPlaceEvent event) {
        String world;
        String pname;
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(player.getWorld())) {
            return;
        }
        if (Residence.isResAdminOn(player)) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getEntity().getLocation(), player);
        if (!perms.playerHas(pname = player.getName(), world = player.getWorld().getName(), Flags.place, perms.playerHas(pname, world, Flags.build, true))) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Flag_Deny, new Object[]{Flags.place});
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        String world;
        Hanging ent = event.getEntity();
        if (ent == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(ent.getWorld())) {
            return;
        }
        if (!(event.getRemover() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getRemover();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        if (Residence.getResidenceManager().isOwnerOfLocation(player, ent.getLocation())) {
            return;
        }
        String pname = player.getName();
        FlagPermissions perms = Residence.getPermsByLocForPlayer(ent.getLocation(), player);
        if (!perms.playerHas(pname, world = ent.getWorld().getName(), Flags.destroy, perms.playerHas(pname, world, Flags.build, true))) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Flag_Deny, new Object[]{Flags.destroy});
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        Hanging ent = event.getEntity();
        if (ent == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(ent.getWorld())) {
            return;
        }
        if (event.getRemover() instanceof Player) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(ent.getLocation());
        if (!perms.has(Flags.destroy, perms.has(Flags.build, true))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onEntityCombust(EntityCombustEvent event) {
        Entity ent = event.getEntity();
        if (ent == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(ent.getWorld())) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(ent.getLocation());
        if (!perms.has(Flags.burn, true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        Entity ent = event.getEntity();
        if (ent == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(ent.getWorld())) {
            return;
        }
        EntityType entity = event.getEntityType();
        FlagPermissions perms = Residence.getPermsByLoc(ent.getLocation());
        switch (ResidenceEntityListener.$SWITCH_TABLE$org$bukkit$entity$EntityType()[entity.ordinal()]) {
            case 45: {
                if (perms.has(Flags.creeper, perms.has(Flags.explode, true))) break;
                if (Residence.getConfigManager().isCreeperExplodeBelow()) {
                    if (ent.getLocation().getBlockY() >= Residence.getConfigManager().getCreeperExplodeBelowLevel()) {
                        event.setCancelled(true);
                        ent.remove();
                        break;
                    }
                    ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
                    if (res == null) break;
                    event.setCancelled(true);
                    ent.remove();
                    break;
                }
                event.setCancelled(true);
                ent.remove();
                break;
            }
            case 20: 
            case 42: {
                if (perms.has(Flags.tnt, perms.has(Flags.explode, true))) break;
                if (Residence.getConfigManager().isTNTExplodeBelow()) {
                    if (ent.getLocation().getBlockY() >= Residence.getConfigManager().getTNTExplodeBelowLevel()) {
                        event.setCancelled(true);
                        ent.remove();
                        break;
                    }
                    ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
                    if (res == null) break;
                    event.setCancelled(true);
                    ent.remove();
                    break;
                }
                event.setCancelled(true);
                ent.remove();
                break;
            }
            case 12: 
            case 13: {
                if (!perms.has(Flags.explode, FlagPermissions.FlagCombo.OnlyFalse) && !perms.has(Flags.fireball, FlagPermissions.FlagCombo.OnlyFalse)) break;
                event.setCancelled(true);
                ent.remove();
                break;
            }
            default: {
                if (!perms.has(Flags.destroy, FlagPermissions.FlagCombo.OnlyFalse)) break;
                event.setCancelled(true);
                ent.remove();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onEntityExplode(EntityExplodeEvent event) {
        loc = event.getLocation();
        if (Residence.isDisabledWorldListener(loc.getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        ent = event.getEntity();
        cancel = false;
        perms = Residence.getPermsByLoc(loc);
        world = Residence.getWorldFlags().getPerms(loc.getWorld().getName());
        if (ent == null) ** GOTO lbl50
        switch (ResidenceEntityListener.$SWITCH_TABLE$org$bukkit$entity$EntityType()[event.getEntityType().ordinal()]) {
            case 45: {
                if (perms.has(Flags.creeper, perms.has(Flags.explode, true))) ** GOTO lbl52
                if (!Residence.getConfigManager().isCreeperExplodeBelow()) ** GOTO lbl23
                if (loc.getBlockY() >= Residence.getConfigManager().getCreeperExplodeBelowLevel()) {
                    cancel = true;
                    ** break;
                }
                res = Residence.getResidenceManager().getByLoc(loc);
                if (res != null) {
                    cancel = true;
                    ** break;
                }
                ** GOTO lbl52
lbl23: // 1 sources:
                cancel = true;
                ** break;
            }
            case 20: 
            case 42: {
                if (perms.has(Flags.tnt, perms.has(Flags.explode, true))) ** GOTO lbl52
                if (!Residence.getConfigManager().isTNTExplodeBelow()) ** GOTO lbl36
                if (loc.getBlockY() >= Residence.getConfigManager().getTNTExplodeBelowLevel()) {
                    cancel = true;
                    ** break;
                }
                res = Residence.getResidenceManager().getByLoc(loc);
                if (res != null) {
                    cancel = true;
                    ** break;
                }
                ** GOTO lbl52
lbl36: // 1 sources:
                cancel = true;
                ** break;
            }
            case 12: 
            case 13: {
                if (perms.has(Flags.explode, FlagPermissions.FlagCombo.OnlyFalse) || perms.has(Flags.fireball, FlagPermissions.FlagCombo.OnlyFalse)) {
                    cancel = true;
                    ** break;
                }
                ** GOTO lbl52
            }
            default: {
                if (!perms.has(Flags.destroy, world.has(Flags.destroy, true))) {
                    cancel = true;
                    ** break;
                }
                ** GOTO lbl52
lbl48: // 8 sources:
                break;
            }
        }
        ** GOTO lbl52
lbl50: // 1 sources:
        if (!perms.has(Flags.destroy, world.has(Flags.destroy, true))) {
            cancel = true;
        }
lbl52: // 10 sources:
        if (cancel.booleanValue()) {
            event.setCancelled(true);
            if (ent == null) return;
            ent.remove();
            return;
        }
        preserve = new ArrayList<Block>();
        block12 : for (Block block : event.blockList()) {
            blockperms = Residence.getPermsByLoc(block.getLocation());
            if (ent != null) {
                switch (ResidenceEntityListener.$SWITCH_TABLE$org$bukkit$entity$EntityType()[event.getEntityType().ordinal()]) {
                    case 45: {
                        if (blockperms.has(Flags.creeper, blockperms.has(Flags.explode, true))) continue block12;
                        if (Residence.getConfigManager().isCreeperExplodeBelow()) {
                            if (block.getY() >= Residence.getConfigManager().getCreeperExplodeBelowLevel()) {
                                preserve.add(block);
                                ** break;
                            }
                            res = Residence.getResidenceManager().getByLoc(block.getLocation());
                            if (res == null) continue block12;
                            preserve.add(block);
                            ** break;
                        }
                        preserve.add(block);
                        ** break;
                    }
                    case 20: 
                    case 42: {
                        if (blockperms.has(Flags.tnt, blockperms.has(Flags.explode, true))) continue block12;
                        if (Residence.getConfigManager().isTNTExplodeBelow()) {
                            if (block.getY() >= Residence.getConfigManager().getTNTExplodeBelowLevel()) {
                                preserve.add(block);
                                ** break;
                            }
                            res = Residence.getResidenceManager().getByLoc(block.getLocation());
                            if (res == null) continue block12;
                            preserve.add(block);
                            ** break;
                        }
                        preserve.add(block);
                        ** break;
                    }
                    case 58: {
                        if (blockperms.has(Flags.dragongrief, false)) continue block12;
                        preserve.add(block);
                        ** break;
                    }
                    case 81: {
                        if (!blockperms.has(Flags.explode, FlagPermissions.FlagCombo.OnlyFalse)) continue block12;
                        preserve.add(block);
                        ** break;
                    }
                    case 12: 
                    case 13: {
                        if (!perms.has(Flags.explode, FlagPermissions.FlagCombo.OnlyFalse) && !perms.has(Flags.fireball, FlagPermissions.FlagCombo.OnlyFalse)) continue block12;
                        preserve.add(block);
                        ** break;
                    }
                    default: {
                        if (!blockperms.has(Flags.destroy, FlagPermissions.FlagCombo.OnlyFalse)) continue block12;
                        preserve.add(block);
                        ** break;
lbl102: // 10 sources:
                        break;
                    }
                }
                continue;
            }
            if (blockperms.has(Flags.destroy, world.has(Flags.destroy, true))) continue;
            preserve.add(block);
        }
        for (Block block : preserve) {
            event.blockList().remove((Object)block);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onSplashPotion(EntityChangeBlockEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Entity ent = event.getEntity();
        if (ent.getType() != EntityType.WITHER) {
            return;
        }
        if (!Residence.getPermsByLoc(ent.getLocation()).has(Flags.destroy, FlagPermissions.FlagCombo.OnlyFalse)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onSplashPotion(PotionSplashEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        boolean harmfull = false;
        block0 : for (PotionEffect one : event.getPotion().getEffects()) {
            for (String oneHarm : Residence.getConfigManager().getNegativePotionEffects()) {
                if (!oneHarm.equalsIgnoreCase(one.getType().getName())) continue;
                harmfull = true;
                break block0;
            }
        }
        if (!harmfull) {
            return;
        }
        ThrownPotion ent = event.getEntity();
        boolean srcpvp = Residence.getPermsByLoc(ent.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
        for (LivingEntity target : event.getAffectedEntities()) {
            if (target.getType() != EntityType.PLAYER) continue;
            Boolean tgtpvp = Residence.getPermsByLoc(target.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
            if (srcpvp && tgtpvp.booleanValue()) continue;
            event.setIntensity(target, 0.0);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void PlayerKillingByFlame(EntityCombustByEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity == null) {
            return;
        }
        if (!(entity instanceof Player)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(entity.getLocation());
        if (res == null) {
            return;
        }
        Entity damager = event.getCombuster();
        if (!(damager instanceof Arrow) && !(damager instanceof Player)) {
            return;
        }
        if (damager instanceof Arrow && !(((Arrow)damager).getShooter() instanceof Player)) {
            return;
        }
        Player cause = null;
        cause = damager instanceof Player ? (Player)damager : (Player)((Arrow)damager).getShooter();
        if (cause == null) {
            return;
        }
        if (Residence.isResAdminOn(cause)) {
            return;
        }
        Boolean srcpvp = Residence.getPermsByLoc(cause.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
        Boolean tgtpvp = Residence.getPermsByLoc(entity.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
        if (!srcpvp.booleanValue() || !tgtpvp.booleanValue()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnFallDamage(EntityDamageEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        Entity ent = event.getEntity();
        if (!(ent instanceof Player)) {
            return;
        }
        if (!Residence.getPermsByLoc(ent.getLocation()).has(Flags.falldamage, FlagPermissions.FlagCombo.TrueOrNone)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnArmorStandFlameDamage(EntityDamageEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK) {
            return;
        }
        Entity ent = event.getEntity();
        if (!Residence.getNms().isArmorStandEntity(ent.getType()) && !(ent instanceof Arrow)) {
            return;
        }
        if (!Residence.getPermsByLoc(ent.getLocation()).has(Flags.destroy, true)) {
            event.setCancelled(true);
            ent.setFireTicks(0);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onBlockCatchingFire(ProjectileHitEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (!(event.getEntity() instanceof Arrow)) {
            return;
        }
        Arrow arrow = (Arrow)event.getEntity();
        FlagPermissions perms = Residence.getPermsByLoc(arrow.getLocation());
        if (!perms.has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone)) {
            arrow.setFireTicks(0);
        }
    }

    @EventHandler
    public void OnPlayerDamageByLightning(EntityDamageEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.LIGHTNING) {
            return;
        }
        Entity ent = event.getEntity();
        if (!(ent instanceof Player)) {
            return;
        }
        if (!Residence.getPermsByLoc(ent.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onEntityDamageByFireballEvent(EntityDamageByEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Entity dmgr = event.getDamager();
        if (dmgr.getType() != EntityType.SMALL_FIREBALL && dmgr.getType() != EntityType.FIREBALL) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(event.getEntity().getLocation());
        if (perms.has(Flags.fireball, FlagPermissions.FlagCombo.OnlyFalse)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        ItemFrame it;
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntityType() != EntityType.ENDER_CRYSTAL && event.getEntityType() != EntityType.ITEM_FRAME && !Residence.getNms().isArmorStandEntity(event.getEntityType())) {
            return;
        }
        Entity dmgr = event.getDamager();
        Player player = null;
        if (dmgr instanceof Player) {
            player = (Player)event.getDamager();
        } else if (dmgr instanceof Projectile && ((Projectile)dmgr).getShooter() instanceof Player) {
            player = (Player)((Projectile)dmgr).getShooter();
        } else {
            FlagPermissions perms;
            if (dmgr instanceof Projectile && !(((Projectile)dmgr).getShooter() instanceof Player)) {
                Location loc = event.getEntity().getLocation();
                FlagPermissions perm = Residence.getPermsByLoc(loc);
                if (perm.has(Flags.destroy, FlagPermissions.FlagCombo.OnlyFalse)) {
                    event.setCancelled(true);
                }
                return;
            }
            if ((dmgr.getType() == EntityType.PRIMED_TNT || dmgr.getType() == EntityType.MINECART_TNT || dmgr.getType() == EntityType.WITHER_SKULL || dmgr.getType() == EntityType.WITHER) && (perms = Residence.getPermsByLoc(event.getEntity().getLocation())).has(Flags.explode, FlagPermissions.FlagCombo.OnlyFalse)) {
                event.setCancelled(true);
                return;
            }
        }
        Location loc = event.getEntity().getLocation();
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
        if (res == null) {
            return;
        }
        if (ResidenceEntityListener.isMonster(dmgr) && !res.getPermissions().has(Flags.destroy, false)) {
            event.setCancelled(true);
            return;
        }
        if (player == null) {
            return;
        }
        if (Residence.isResAdminOn(player)) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer(loc, player);
        if (event.getEntityType() == EntityType.ITEM_FRAME && (it = (ItemFrame)event.getEntity()).getItem() != null) {
            if (!perms.playerHas(player, Flags.container, true)) {
                event.setCancelled(true);
                Residence.msg((CommandSender)player, lm.Flag_Deny, new Object[]{Flags.container});
            }
            return;
        }
        if (!perms.playerHas(player, Flags.destroy, perms.playerHas(player, Flags.build, true))) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.destroy.getName());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onEntityDamage(EntityDamageEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        Entity ent = event.getEntity();
        if (ent.hasMetadata("NPC")) {
            return;
        }
        boolean tamedAnimal = ResidenceEntityListener.isTamed(ent);
        ClaimedResidence area2 = Residence.getResidenceManager().getByLoc(ent.getLocation());
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent attackevent = (EntityDamageByEntityEvent)event;
            Entity damager = attackevent.getDamager();
            if (area2 != null && ent instanceof Player && damager instanceof Player && (area2.getPermissions().has(Flags.overridepvp, false) || Residence.getConfigManager().isOverridePvp() && area2.getPermissions().has(Flags.pvp, FlagPermissions.FlagCombo.OnlyFalse, false))) {
                Player player;
                Player damage = player = (Player)event.getEntity();
                damage.damage(event.getDamage());
                damage.setVelocity(damager.getLocation().getDirection());
                event.setCancelled(true);
                return;
            }
            ClaimedResidence srcarea = null;
            if (damager != null) {
                srcarea = Residence.getResidenceManager().getByLoc(damager.getLocation());
            }
            boolean srcpvp = true;
            boolean allowSnowBall = false;
            boolean isSnowBall = false;
            boolean isOnFire = false;
            if (srcarea != null) {
                srcpvp = srcarea.getPermissions().has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone, false);
            }
            if (((ent = attackevent.getEntity()) instanceof Player || tamedAnimal) && (damager instanceof Player || damager instanceof Projectile && ((Projectile)damager).getShooter() instanceof Player) && event.getCause() != EntityDamageEvent.DamageCause.FALL) {
                Player attacker = null;
                if (damager instanceof Player) {
                    attacker = (Player)damager;
                } else if (damager instanceof Projectile) {
                    Projectile project = (Projectile)damager;
                    if (project.getType() == EntityType.SNOWBALL && srcarea != null) {
                        isSnowBall = true;
                        allowSnowBall = srcarea.getPermissions().has(Flags.snowball, FlagPermissions.FlagCombo.TrueOrNone);
                    }
                    if (project.getFireTicks() > 0) {
                        isOnFire = true;
                    }
                    attacker = (Player)((Projectile)damager).getShooter();
                }
                if (!(ent instanceof Player)) {
                    return;
                }
                if (!srcpvp && !isSnowBall || !allowSnowBall && isSnowBall) {
                    if (attacker != null) {
                        Residence.msg((CommandSender)attacker, lm.General_NoPVPZone, new Object[0]);
                    }
                    if (isOnFire) {
                        ent.setFireTicks(0);
                    }
                    event.setCancelled(true);
                    return;
                }
                if (area2 == null) {
                    FlagPermissions aPerm;
                    if (damager != null && !Residence.getWorldFlags().getPerms(damager.getWorld().getName()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone)) {
                        if (attacker != null) {
                            Residence.msg((CommandSender)attacker, lm.General_WorldPVPDisabled, new Object[0]);
                        }
                        if (isOnFire) {
                            ent.setFireTicks(0);
                        }
                        event.setCancelled(true);
                        return;
                    }
                    if (attacker != null && !(aPerm = Residence.getPermsByLoc(attacker.getLocation())).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone)) {
                        Residence.msg((CommandSender)attacker, lm.General_NoPVPZone, new Object[0]);
                        if (isOnFire) {
                            ent.setFireTicks(0);
                        }
                        event.setCancelled(true);
                        return;
                    }
                } else if (!isSnowBall && !area2.getPermissions().has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone, false) || isSnowBall && !allowSnowBall) {
                    if (attacker != null) {
                        Residence.msg((CommandSender)attacker, lm.General_NoPVPZone, new Object[0]);
                    }
                    if (isOnFire) {
                        ent.setFireTicks(0);
                    }
                    event.setCancelled(true);
                    return;
                }
                return;
            }
            if ((ent instanceof Player || tamedAnimal) && damager instanceof Creeper) {
                if (area2 == null && !Residence.getWorldFlags().getPerms(damager.getWorld().getName()).has(Flags.creeper, true)) {
                    event.setCancelled(true);
                } else if (area2 != null && !area2.getPermissions().has(Flags.creeper, true)) {
                    event.setCancelled(true);
                }
            }
        }
        if (area2 == null) {
            if (!Residence.getWorldFlags().getPerms(ent.getWorld().getName()).has(Flags.damage, true) && (ent instanceof Player || tamedAnimal)) {
                event.setCancelled(true);
            }
        } else if (!area2.getPermissions().has(Flags.damage, true) && (ent instanceof Player || tamedAnimal)) {
            event.setCancelled(true);
        }
        if (event.isCancelled() && (ent instanceof Player || tamedAnimal) && (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)) {
            ent.setFireTicks(0);
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$Material() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$org$bukkit$Material;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Material.values().length];
        try {
            arrn[Material.ACACIA_DOOR.ordinal()] = 197;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACACIA_DOOR_ITEM.ordinal()] = 411;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACACIA_FENCE.ordinal()] = 193;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACACIA_FENCE_GATE.ordinal()] = 188;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACACIA_STAIRS.ordinal()] = 164;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACTIVATOR_RAIL.ordinal()] = 158;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.AIR.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ANVIL.ordinal()] = 146;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.APPLE.ordinal()] = 241;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ARMOR_STAND.ordinal()] = 397;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ARROW.ordinal()] = 243;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BAKED_POTATO.ordinal()] = 374;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BANNER.ordinal()] = 406;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BARRIER.ordinal()] = 167;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEACON.ordinal()] = 139;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BED.ordinal()] = 336;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEDROCK.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BED_BLOCK.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEETROOT.ordinal()] = 415;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEETROOT_BLOCK.ordinal()] = 208;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEETROOT_SEEDS.ordinal()] = 416;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEETROOT_SOUP.ordinal()] = 417;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_DOOR.ordinal()] = 195;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_DOOR_ITEM.ordinal()] = 409;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_FENCE.ordinal()] = 190;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_FENCE_GATE.ordinal()] = 185;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_WOOD_STAIRS.ordinal()] = 136;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLACK_SHULKER_BOX.ordinal()] = 235;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLAZE_POWDER.ordinal()] = 358;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLAZE_ROD.ordinal()] = 350;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLUE_SHULKER_BOX.ordinal()] = 231;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT.ordinal()] = 314;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_ACACIA.ordinal()] = 428;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_BIRCH.ordinal()] = 426;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_DARK_OAK.ordinal()] = 429;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_JUNGLE.ordinal()] = 427;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_SPRUCE.ordinal()] = 425;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BONE.ordinal()] = 333;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BONE_BLOCK.ordinal()] = 217;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOOK.ordinal()] = 321;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOOKSHELF.ordinal()] = 48;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOOK_AND_QUILL.ordinal()] = 367;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOW.ordinal()] = 242;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOWL.ordinal()] = 262;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BREAD.ordinal()] = 278;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BREWING_STAND.ordinal()] = 118;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BREWING_STAND_ITEM.ordinal()] = 360;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BRICK.ordinal()] = 46;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BRICK_STAIRS.ordinal()] = 109;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BROWN_MUSHROOM.ordinal()] = 40;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BROWN_SHULKER_BOX.ordinal()] = 232;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BUCKET.ordinal()] = 306;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BURNING_FURNACE.ordinal()] = 63;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CACTUS.ordinal()] = 82;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CAKE.ordinal()] = 335;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CAKE_BLOCK.ordinal()] = 93;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CARPET.ordinal()] = 172;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CARROT.ordinal()] = 142;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CARROT_ITEM.ordinal()] = 372;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CARROT_STICK.ordinal()] = 379;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CAULDRON.ordinal()] = 119;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CAULDRON_ITEM.ordinal()] = 361;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHAINMAIL_BOOTS.ordinal()] = 286;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHAINMAIL_CHESTPLATE.ordinal()] = 284;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHAINMAIL_HELMET.ordinal()] = 283;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHAINMAIL_LEGGINGS.ordinal()] = 285;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHEST.ordinal()] = 55;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHORUS_FLOWER.ordinal()] = 201;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHORUS_FRUIT.ordinal()] = 413;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHORUS_FRUIT_POPPED.ordinal()] = 414;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHORUS_PLANT.ordinal()] = 200;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CLAY.ordinal()] = 83;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CLAY_BALL.ordinal()] = 318;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CLAY_BRICK.ordinal()] = 317;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COAL.ordinal()] = 244;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COAL_BLOCK.ordinal()] = 174;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COAL_ORE.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COBBLESTONE.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COBBLESTONE_STAIRS.ordinal()] = 68;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COBBLE_WALL.ordinal()] = 140;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COCOA.ordinal()] = 128;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMMAND.ordinal()] = 138;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMMAND_CHAIN.ordinal()] = 212;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMMAND_MINECART.ordinal()] = 403;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMMAND_REPEATING.ordinal()] = 211;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMPASS.ordinal()] = 326;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_BEEF.ordinal()] = 345;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_CHICKEN.ordinal()] = 347;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_FISH.ordinal()] = 331;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_MUTTON.ordinal()] = 405;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_RABBIT.ordinal()] = 393;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKIE.ordinal()] = 338;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CROPS.ordinal()] = 60;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CYAN_SHULKER_BOX.ordinal()] = 229;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_DOOR.ordinal()] = 198;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_DOOR_ITEM.ordinal()] = 412;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_FENCE.ordinal()] = 192;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_FENCE_GATE.ordinal()] = 187;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_STAIRS.ordinal()] = 165;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DAYLIGHT_DETECTOR.ordinal()] = 152;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DAYLIGHT_DETECTOR_INVERTED.ordinal()] = 179;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DEAD_BUSH.ordinal()] = 33;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DETECTOR_RAIL.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND.ordinal()] = 245;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_AXE.ordinal()] = 260;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_BARDING.ordinal()] = 400;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_BLOCK.ordinal()] = 58;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_BOOTS.ordinal()] = 294;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_CHESTPLATE.ordinal()] = 292;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_HELMET.ordinal()] = 291;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_HOE.ordinal()] = 274;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_LEGGINGS.ordinal()] = 293;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_ORE.ordinal()] = 57;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_PICKAXE.ordinal()] = 259;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_SPADE.ordinal()] = 258;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_SWORD.ordinal()] = 257;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIODE.ordinal()] = 337;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIODE_BLOCK_OFF.ordinal()] = 94;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIODE_BLOCK_ON.ordinal()] = 95;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIRT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DISPENSER.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DOUBLE_PLANT.ordinal()] = 176;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DOUBLE_STEP.ordinal()] = 44;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DOUBLE_STONE_SLAB2.ordinal()] = 182;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DRAGONS_BREATH.ordinal()] = 418;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DRAGON_EGG.ordinal()] = 123;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DROPPER.ordinal()] = 159;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EGG.ordinal()] = 325;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ELYTRA.ordinal()] = 424;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EMERALD.ordinal()] = 369;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EMERALD_BLOCK.ordinal()] = 134;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EMERALD_ORE.ordinal()] = 130;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EMPTY_MAP.ordinal()] = 376;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENCHANTED_BOOK.ordinal()] = 384;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENCHANTMENT_TABLE.ordinal()] = 117;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_CHEST.ordinal()] = 131;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_PEARL.ordinal()] = 349;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_PORTAL.ordinal()] = 120;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_PORTAL_FRAME.ordinal()] = 121;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_STONE.ordinal()] = 122;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.END_BRICKS.ordinal()] = 207;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.END_CRYSTAL.ordinal()] = 407;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.END_GATEWAY.ordinal()] = 210;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.END_ROD.ordinal()] = 199;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EXPLOSIVE_MINECART.ordinal()] = 388;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EXP_BOTTLE.ordinal()] = 365;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EYE_OF_ENDER.ordinal()] = 362;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FEATHER.ordinal()] = 269;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FENCE.ordinal()] = 86;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FENCE_GATE.ordinal()] = 108;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FERMENTED_SPIDER_EYE.ordinal()] = 357;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FIRE.ordinal()] = 52;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FIREBALL.ordinal()] = 366;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FIREWORK.ordinal()] = 382;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FIREWORK_CHARGE.ordinal()] = 383;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FISHING_ROD.ordinal()] = 327;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FLINT.ordinal()] = 299;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FLINT_AND_STEEL.ordinal()] = 240;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FLOWER_POT.ordinal()] = 141;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FLOWER_POT_ITEM.ordinal()] = 371;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FROSTED_ICE.ordinal()] = 213;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FURNACE.ordinal()] = 62;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GHAST_TEAR.ordinal()] = 351;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLASS.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLASS_BOTTLE.ordinal()] = 355;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLOWING_REDSTONE_ORE.ordinal()] = 75;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLOWSTONE.ordinal()] = 90;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLOWSTONE_DUST.ordinal()] = 329;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLDEN_APPLE.ordinal()] = 303;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLDEN_CARROT.ordinal()] = 377;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_AXE.ordinal()] = 267;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_BARDING.ordinal()] = 399;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_BLOCK.ordinal()] = 42;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_BOOTS.ordinal()] = 298;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_CHESTPLATE.ordinal()] = 296;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_HELMET.ordinal()] = 295;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_HOE.ordinal()] = 275;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_INGOT.ordinal()] = 247;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_LEGGINGS.ordinal()] = 297;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_NUGGET.ordinal()] = 352;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_ORE.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_PICKAXE.ordinal()] = 266;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_PLATE.ordinal()] = 148;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_RECORD.ordinal()] = 432;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_SPADE.ordinal()] = 265;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_SWORD.ordinal()] = 264;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRASS.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRASS_PATH.ordinal()] = 209;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRAVEL.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRAY_SHULKER_BOX.ordinal()] = 227;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GREEN_RECORD.ordinal()] = 433;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GREEN_SHULKER_BOX.ordinal()] = 233;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRILLED_PORK.ordinal()] = 301;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HARD_CLAY.ordinal()] = 173;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HAY_BLOCK.ordinal()] = 171;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HOPPER.ordinal()] = 155;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HOPPER_MINECART.ordinal()] = 389;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HUGE_MUSHROOM_1.ordinal()] = 100;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HUGE_MUSHROOM_2.ordinal()] = 101;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ICE.ordinal()] = 80;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.INK_SACK.ordinal()] = 332;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_AXE.ordinal()] = 239;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_BARDING.ordinal()] = 398;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_BLOCK.ordinal()] = 43;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_BOOTS.ordinal()] = 290;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_CHESTPLATE.ordinal()] = 288;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_DOOR.ordinal()] = 311;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_DOOR_BLOCK.ordinal()] = 72;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_FENCE.ordinal()] = 102;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_HELMET.ordinal()] = 287;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_HOE.ordinal()] = 273;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_INGOT.ordinal()] = 246;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_LEGGINGS.ordinal()] = 289;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_ORE.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_PICKAXE.ordinal()] = 238;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_PLATE.ordinal()] = 149;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_SPADE.ordinal()] = 237;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_SWORD.ordinal()] = 248;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_TRAPDOOR.ordinal()] = 168;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ITEM_FRAME.ordinal()] = 370;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JACK_O_LANTERN.ordinal()] = 92;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUKEBOX.ordinal()] = 85;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_DOOR.ordinal()] = 196;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_DOOR_ITEM.ordinal()] = 410;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_FENCE.ordinal()] = 191;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_FENCE_GATE.ordinal()] = 186;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_WOOD_STAIRS.ordinal()] = 137;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LADDER.ordinal()] = 66;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LAPIS_BLOCK.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LAPIS_ORE.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LAVA.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LAVA_BUCKET.ordinal()] = 308;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEASH.ordinal()] = 401;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER.ordinal()] = 315;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER_BOOTS.ordinal()] = 282;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER_CHESTPLATE.ordinal()] = 280;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER_HELMET.ordinal()] = 279;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER_LEGGINGS.ordinal()] = 281;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEAVES.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEAVES_2.ordinal()] = 162;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEVER.ordinal()] = 70;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LIGHT_BLUE_SHULKER_BOX.ordinal()] = 223;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LIME_SHULKER_BOX.ordinal()] = 225;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LINGERING_POTION.ordinal()] = 422;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LOG.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LOG_2.ordinal()] = 163;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LONG_GRASS.ordinal()] = 32;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAGENTA_SHULKER_BOX.ordinal()] = 222;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAGMA.ordinal()] = 214;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAGMA_CREAM.ordinal()] = 359;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAP.ordinal()] = 339;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MELON.ordinal()] = 341;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MELON_BLOCK.ordinal()] = 104;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MELON_SEEDS.ordinal()] = 343;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MELON_STEM.ordinal()] = 106;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MILK_BUCKET.ordinal()] = 316;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MINECART.ordinal()] = 309;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MOB_SPAWNER.ordinal()] = 53;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MONSTER_EGG.ordinal()] = 364;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MONSTER_EGGS.ordinal()] = 98;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MOSSY_COBBLESTONE.ordinal()] = 49;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MUSHROOM_SOUP.ordinal()] = 263;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MUTTON.ordinal()] = 404;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MYCEL.ordinal()] = 111;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NAME_TAG.ordinal()] = 402;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHERRACK.ordinal()] = 88;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_BRICK.ordinal()] = 113;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_BRICK_ITEM.ordinal()] = 386;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_BRICK_STAIRS.ordinal()] = 115;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_FENCE.ordinal()] = 114;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_STALK.ordinal()] = 353;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_STAR.ordinal()] = 380;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_WARTS.ordinal()] = 116;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_WART_BLOCK.ordinal()] = 215;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NOTE_BLOCK.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.OBSERVER.ordinal()] = 219;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.OBSIDIAN.ordinal()] = 50;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ORANGE_SHULKER_BOX.ordinal()] = 221;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PACKED_ICE.ordinal()] = 175;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PAINTING.ordinal()] = 302;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PAPER.ordinal()] = 320;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PINK_SHULKER_BOX.ordinal()] = 226;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PISTON_BASE.ordinal()] = 34;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PISTON_EXTENSION.ordinal()] = 35;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PISTON_MOVING_PIECE.ordinal()] = 37;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PISTON_STICKY_BASE.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POISONOUS_POTATO.ordinal()] = 375;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PORK.ordinal()] = 300;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PORTAL.ordinal()] = 91;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POTATO.ordinal()] = 143;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POTATO_ITEM.ordinal()] = 373;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POTION.ordinal()] = 354;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POWERED_MINECART.ordinal()] = 324;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POWERED_RAIL.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PRISMARINE.ordinal()] = 169;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PRISMARINE_CRYSTALS.ordinal()] = 391;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PRISMARINE_SHARD.ordinal()] = 390;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PUMPKIN.ordinal()] = 87;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PUMPKIN_PIE.ordinal()] = 381;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PUMPKIN_SEEDS.ordinal()] = 342;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PUMPKIN_STEM.ordinal()] = 105;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPLE_SHULKER_BOX.ordinal()] = 230;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_BLOCK.ordinal()] = 202;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_DOUBLE_SLAB.ordinal()] = 205;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_PILLAR.ordinal()] = 203;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_SLAB.ordinal()] = 206;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_STAIRS.ordinal()] = 204;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.QUARTZ.ordinal()] = 387;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.QUARTZ_BLOCK.ordinal()] = 156;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.QUARTZ_ORE.ordinal()] = 154;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.QUARTZ_STAIRS.ordinal()] = 157;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RABBIT.ordinal()] = 392;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RABBIT_FOOT.ordinal()] = 395;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RABBIT_HIDE.ordinal()] = 396;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RABBIT_STEW.ordinal()] = 394;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RAILS.ordinal()] = 67;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RAW_BEEF.ordinal()] = 344;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RAW_CHICKEN.ordinal()] = 346;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RAW_FISH.ordinal()] = 330;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_10.ordinal()] = 441;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_11.ordinal()] = 442;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_12.ordinal()] = 443;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_3.ordinal()] = 434;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_4.ordinal()] = 435;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_5.ordinal()] = 436;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_6.ordinal()] = 437;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_7.ordinal()] = 438;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_8.ordinal()] = 439;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_9.ordinal()] = 440;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE.ordinal()] = 312;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_BLOCK.ordinal()] = 153;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_COMPARATOR.ordinal()] = 385;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_COMPARATOR_OFF.ordinal()] = 150;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_COMPARATOR_ON.ordinal()] = 151;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_LAMP_OFF.ordinal()] = 124;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_LAMP_ON.ordinal()] = 125;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_ORE.ordinal()] = 74;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_TORCH_OFF.ordinal()] = 76;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_TORCH_ON.ordinal()] = 77;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_WIRE.ordinal()] = 56;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_MUSHROOM.ordinal()] = 41;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_NETHER_BRICK.ordinal()] = 216;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_ROSE.ordinal()] = 39;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_SANDSTONE.ordinal()] = 180;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_SANDSTONE_STAIRS.ordinal()] = 181;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_SHULKER_BOX.ordinal()] = 234;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ROTTEN_FLESH.ordinal()] = 348;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SADDLE.ordinal()] = 310;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SAND.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SANDSTONE.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SANDSTONE_STAIRS.ordinal()] = 129;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SAPLING.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SEA_LANTERN.ordinal()] = 170;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SEEDS.ordinal()] = 276;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SHEARS.ordinal()] = 340;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SHIELD.ordinal()] = 423;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SHULKER_SHELL.ordinal()] = 431;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SIGN.ordinal()] = 304;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SIGN_POST.ordinal()] = 64;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SILVER_SHULKER_BOX.ordinal()] = 228;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SKULL.ordinal()] = 145;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SKULL_ITEM.ordinal()] = 378;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SLIME_BALL.ordinal()] = 322;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SLIME_BLOCK.ordinal()] = 166;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SMOOTH_BRICK.ordinal()] = 99;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SMOOTH_STAIRS.ordinal()] = 110;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SNOW.ordinal()] = 79;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SNOW_BALL.ordinal()] = 313;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SNOW_BLOCK.ordinal()] = 81;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SOIL.ordinal()] = 61;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SOUL_SAND.ordinal()] = 89;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPECKLED_MELON.ordinal()] = 363;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPECTRAL_ARROW.ordinal()] = 420;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPIDER_EYE.ordinal()] = 356;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPLASH_POTION.ordinal()] = 419;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPONGE.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_DOOR.ordinal()] = 194;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_DOOR_ITEM.ordinal()] = 408;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_FENCE.ordinal()] = 189;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_FENCE_GATE.ordinal()] = 184;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_WOOD_STAIRS.ordinal()] = 135;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STAINED_CLAY.ordinal()] = 160;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STAINED_GLASS.ordinal()] = 96;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STAINED_GLASS_PANE.ordinal()] = 161;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STANDING_BANNER.ordinal()] = 177;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STATIONARY_LAVA.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STATIONARY_WATER.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STEP.ordinal()] = 45;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STICK.ordinal()] = 261;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_AXE.ordinal()] = 256;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_BUTTON.ordinal()] = 78;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_HOE.ordinal()] = 272;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_PICKAXE.ordinal()] = 255;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_PLATE.ordinal()] = 71;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_SLAB2.ordinal()] = 183;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_SPADE.ordinal()] = 254;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_SWORD.ordinal()] = 253;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STORAGE_MINECART.ordinal()] = 323;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STRING.ordinal()] = 268;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STRUCTURE_BLOCK.ordinal()] = 236;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STRUCTURE_VOID.ordinal()] = 218;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SUGAR.ordinal()] = 334;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SUGAR_CANE.ordinal()] = 319;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SUGAR_CANE_BLOCK.ordinal()] = 84;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SULPHUR.ordinal()] = 270;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.THIN_GLASS.ordinal()] = 103;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TIPPED_ARROW.ordinal()] = 421;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TNT.ordinal()] = 47;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TORCH.ordinal()] = 51;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TOTEM.ordinal()] = 430;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TRAPPED_CHEST.ordinal()] = 147;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TRAP_DOOR.ordinal()] = 97;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TRIPWIRE.ordinal()] = 133;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TRIPWIRE_HOOK.ordinal()] = 132;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.VINE.ordinal()] = 107;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WALL_BANNER.ordinal()] = 178;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WALL_SIGN.ordinal()] = 69;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WATCH.ordinal()] = 328;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WATER.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WATER_BUCKET.ordinal()] = 307;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WATER_LILY.ordinal()] = 112;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WEB.ordinal()] = 31;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WHEAT.ordinal()] = 277;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WHITE_SHULKER_BOX.ordinal()] = 220;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOODEN_DOOR.ordinal()] = 65;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_AXE.ordinal()] = 252;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_BUTTON.ordinal()] = 144;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_DOOR.ordinal()] = 305;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_DOUBLE_STEP.ordinal()] = 126;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_HOE.ordinal()] = 271;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_PICKAXE.ordinal()] = 251;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_PLATE.ordinal()] = 73;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_SPADE.ordinal()] = 250;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_STAIRS.ordinal()] = 54;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_STEP.ordinal()] = 127;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_SWORD.ordinal()] = 249;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOL.ordinal()] = 36;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WORKBENCH.ordinal()] = 59;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WRITTEN_BOOK.ordinal()] = 368;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.YELLOW_FLOWER.ordinal()] = 38;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.YELLOW_SHULKER_BOX.ordinal()] = 224;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$org$bukkit$Material = arrn;
        return $SWITCH_TABLE$org$bukkit$Material;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$event$entity$CreatureSpawnEvent$SpawnReason() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$org$bukkit$event$entity$CreatureSpawnEvent$SpawnReason;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[CreatureSpawnEvent.SpawnReason.values().length];
        try {
            arrn[CreatureSpawnEvent.SpawnReason.BREEDING.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.BUILD_WITHER.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.CHUNK_GEN.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.CURED.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.CUSTOM.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.DEFAULT.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.DISPENSE_EGG.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.EGG.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.ENDER_PEARL.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.INFECTION.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.JOCKEY.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.LIGHTNING.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.MOUNT.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.NATURAL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.NETHER_PORTAL.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.OCELOT_BABY.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.REINFORCEMENTS.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.SLIME_SPLIT.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.SPAWNER.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.SPAWNER_EGG.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.TRAP.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.VILLAGE_DEFENSE.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$org$bukkit$event$entity$CreatureSpawnEvent$SpawnReason = arrn;
        return $SWITCH_TABLE$org$bukkit$event$entity$CreatureSpawnEvent$SpawnReason;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$entity$EntityType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$org$bukkit$entity$EntityType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[EntityType.values().length];
        try {
            arrn[EntityType.AREA_EFFECT_CLOUD.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ARMOR_STAND.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ARROW.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.BAT.ordinal()] = 60;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.BLAZE.ordinal()] = 56;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.BOAT.ordinal()] = 38;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.CAVE_SPIDER.ordinal()] = 54;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.CHICKEN.ordinal()] = 68;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.COMPLEX_PART.ordinal()] = 87;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.COW.ordinal()] = 67;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.CREEPER.ordinal()] = 45;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.DONKEY.ordinal()] = 31;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.DRAGON_FIREBALL.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.DROPPED_ITEM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.EGG.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ELDER_GUARDIAN.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ENDERMAN.ordinal()] = 53;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ENDERMITE.ordinal()] = 62;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ENDER_CRYSTAL.ordinal()] = 81;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ENDER_DRAGON.ordinal()] = 58;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ENDER_PEARL.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ENDER_SIGNAL.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.EVOKER.ordinal()] = 34;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.EVOKER_FANGS.ordinal()] = 33;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.EXPERIENCE_ORB.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.FALLING_BLOCK.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.FIREBALL.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.FIREWORK.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.FISHING_HOOK.ordinal()] = 83;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.GHAST.ordinal()] = 51;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.GIANT.ordinal()] = 48;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.GUARDIAN.ordinal()] = 63;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.HORSE.ordinal()] = 75;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.HUSK.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.IRON_GOLEM.ordinal()] = 74;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ITEM_FRAME.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.LEASH_HITCH.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.LIGHTNING.ordinal()] = 84;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.LINGERING_POTION.ordinal()] = 82;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.LLAMA.ordinal()] = 78;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.LLAMA_SPIT.ordinal()] = 79;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MAGMA_CUBE.ordinal()] = 57;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MINECART.ordinal()] = 39;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MINECART_CHEST.ordinal()] = 40;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MINECART_COMMAND.ordinal()] = 37;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MINECART_FURNACE.ordinal()] = 41;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MINECART_HOPPER.ordinal()] = 43;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MINECART_MOB_SPAWNER.ordinal()] = 44;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MINECART_TNT.ordinal()] = 42;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MULE.ordinal()] = 32;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.MUSHROOM_COW.ordinal()] = 71;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.OCELOT.ordinal()] = 73;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.PAINTING.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.PIG.ordinal()] = 65;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.PIG_ZOMBIE.ordinal()] = 52;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.PLAYER.ordinal()] = 86;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.POLAR_BEAR.ordinal()] = 77;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.PRIMED_TNT.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.RABBIT.ordinal()] = 76;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SHEEP.ordinal()] = 66;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SHULKER.ordinal()] = 64;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SHULKER_BULLET.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SILVERFISH.ordinal()] = 55;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SKELETON.ordinal()] = 46;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SKELETON_HORSE.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SLIME.ordinal()] = 50;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SMALL_FIREBALL.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SNOWBALL.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SNOWMAN.ordinal()] = 72;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SPECTRAL_ARROW.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SPIDER.ordinal()] = 47;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SPLASH_POTION.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.SQUID.ordinal()] = 69;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.STRAY.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.THROWN_EXP_BOTTLE.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.TIPPED_ARROW.ordinal()] = 88;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.UNKNOWN.ordinal()] = 89;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.VEX.ordinal()] = 35;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.VILLAGER.ordinal()] = 80;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.VINDICATOR.ordinal()] = 36;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.WEATHER.ordinal()] = 85;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.WITCH.ordinal()] = 61;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.WITHER.ordinal()] = 59;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.WITHER_SKELETON.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.WITHER_SKULL.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.WOLF.ordinal()] = 70;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ZOMBIE.ordinal()] = 49;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ZOMBIE_HORSE.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EntityType.ZOMBIE_VILLAGER.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$org$bukkit$entity$EntityType = arrn;
        return $SWITCH_TABLE$org$bukkit$entity$EntityType;
    }
}

