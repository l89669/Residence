/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.shampaggon.crackshot.events.WeaponDamageEntityEvent
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.projectiles.ProjectileSource
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.projectiles.ProjectileSource;

public class CrackShot
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void AnimalKilling(WeaponDamageEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Entity damager = event.getDamager();
        if (!(damager instanceof Arrow) && !(damager instanceof Player)) {
            return;
        }
        Player cause = null;
        if (damager instanceof Arrow && !(((Arrow)damager).getShooter() instanceof Player)) {
            return;
        }
        cause = damager instanceof Player ? (Player)damager : (Player)((Arrow)damager).getShooter();
        if (cause == null) {
            return;
        }
        if (Residence.isResAdminOn(cause)) {
            return;
        }
        if (!(event.getVictim() instanceof LivingEntity)) {
            return;
        }
        Entity entity = event.getVictim();
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(entity.getLocation());
        if (Residence.getNms().isAnimal(entity) && res != null && res.getPermissions().playerHas(cause, Flags.animalkilling, FlagPermissions.FlagCombo.OnlyFalse)) {
            cause.sendMessage(Residence.msg(lm.Residence_FlagDeny, Flags.animalkilling.getName(), res.getName()));
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onEntityDamageByEntityEvent(WeaponDamageEntityEvent event) {
        Player player;
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getVictim().getType() != EntityType.ITEM_FRAME && !Residence.getNms().isArmorStandEntity(event.getVictim().getType())) {
            return;
        }
        Entity dmgr = event.getDamager();
        if (event.getDamager() instanceof Player) {
            player = (Player)event.getDamager();
        } else if (dmgr instanceof Projectile && ((Projectile)dmgr).getShooter() instanceof Player) {
            player = (Player)((Projectile)dmgr).getShooter();
        } else {
            return;
        }
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Location loc = event.getVictim().getLocation();
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
        if (res != null && res.getPermissions().playerHas(player, Flags.container, FlagPermissions.FlagCombo.OnlyFalse)) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.container.getName(), res.getName());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onEntityDamage(WeaponDamageEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (!(event.getVictim() instanceof Player)) {
            return;
        }
        Player victim = (Player)event.getVictim();
        if (victim.hasMetadata("NPC")) {
            return;
        }
        ClaimedResidence area2 = Residence.getResidenceManager().getByLoc(victim.getLocation());
        Player damager = event.getPlayer();
        ClaimedResidence srcarea = null;
        if (damager == null) {
            return;
        }
        srcarea = Residence.getResidenceManager().getByLoc(damager.getLocation());
        boolean srcpvp = true;
        if (srcarea != null) {
            srcpvp = srcarea.getPermissions().has(Flags.pvp, true);
        }
        if (!srcpvp) {
            damager.sendMessage(Residence.msg(lm.General_NoPVPZone, new Object[0]));
            event.setCancelled(true);
            return;
        }
        if (area2 == null) {
            if (!Residence.getWorldFlags().getPerms(damager.getWorld().getName()).has(Flags.pvp, true)) {
                damager.sendMessage(Residence.msg(lm.General_WorldPVPDisabled, new Object[0]));
                event.setCancelled(true);
            }
        } else if (!area2.getPermissions().has(Flags.pvp, true)) {
            damager.sendMessage(Residence.msg(lm.General_NoPVPZone, new Object[0]));
            event.setCancelled(true);
        }
    }
}

