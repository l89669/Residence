/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.AreaEffectCloud
 *  org.bukkit.entity.LingeringPotion
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.AreaEffectCloudApplyEvent
 *  org.bukkit.event.entity.LingeringPotionSplashEvent
 *  org.bukkit.potion.PotionData
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.potion.PotionType
 */
package com.bekvon.bukkit.residence.allNms;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import java.util.Collection;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class v1_9Events
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onLingeringSplashPotion(LingeringPotionSplashEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        boolean harmfull = false;
        block0 : for (PotionEffect one : event.getEntity().getEffects()) {
            for (String oneHarm : Residence.getConfigManager().getNegativePotionEffects()) {
                if (!oneHarm.equalsIgnoreCase(one.getType().getName())) continue;
                harmfull = true;
                break block0;
            }
        }
        if (!harmfull) {
            return;
        }
        LingeringPotion ent = event.getEntity();
        boolean srcpvp = Residence.getPermsByLoc(ent.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
        if (!srcpvp) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onLingeringEffectApply(AreaEffectCloudApplyEvent event) {
        boolean harmfull;
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        harmfull = false;
        try {
            for (String oneHarm : Residence.getConfigManager().getNegativeLingeringPotionEffects()) {
                if (!event.getEntity().getBasePotionData().getType().name().equalsIgnoreCase(oneHarm)) continue;
                harmfull = true;
                break;
            }
        }
        catch (Exception e) {
            return;
        }
        if (!harmfull) {
            return;
        }
        AreaEffectCloud ent = event.getEntity();
        boolean srcpvp = Residence.getPermsByLoc(ent.getLocation()).has(Flags.pvp, true);
        for (LivingEntity target : event.getAffectedEntities()) {
            if (!(target instanceof Player)) continue;
            Boolean tgtpvp = Residence.getPermsByLoc(target.getLocation()).has(Flags.pvp, true);
            if (srcpvp && tgtpvp.booleanValue()) continue;
            event.getAffectedEntities().remove((Object)target);
            event.getEntity().remove();
            break;
        }
    }
}

