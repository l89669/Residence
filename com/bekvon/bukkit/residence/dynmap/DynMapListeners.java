/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 */
package com.bekvon.bukkit.residence.dynmap;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.event.ResidenceAreaAddEvent;
import com.bekvon.bukkit.residence.event.ResidenceAreaDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceFlagChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceRenameEvent;
import com.bekvon.bukkit.residence.event.ResidenceRentEvent;
import com.bekvon.bukkit.residence.event.ResidenceSizeChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceSubzoneCreationEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class DynMapListeners
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceAreaAdd(ResidenceAreaAddEvent event) {
        Residence.getDynManager().fireUpdateAdd(event.getResidence(), event.getResidence().getSubzoneDeep());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceAreaDelete(ResidenceAreaDeleteEvent event) {
        Residence.getDynManager().fireUpdateRemove(event.getResidence(), event.getResidence().getSubzoneDeep());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceSubZoneCreate(ResidenceSubzoneCreationEvent event) {
        Residence.getDynManager().fireUpdateAdd(event.getResidence(), event.getResidence().getSubzoneDeep());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceFlagChange(ResidenceFlagChangeEvent event) {
        Residence.getDynManager().fireUpdateAdd(event.getResidence(), event.getResidence().getSubzoneDeep());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceDelete(ResidenceDeleteEvent event) {
        Residence.getDynManager().fireUpdateRemove(event.getResidence(), event.getResidence().getSubzoneDeep());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceOwnerChange(ResidenceOwnerChangeEvent event) {
        Residence.getDynManager().fireUpdateAdd(event.getResidence(), event.getResidence().getSubzoneDeep());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceRename(ResidenceRenameEvent event) {
        Residence.getDynManager().handleResidenceRemove(event.getOldResidenceName(), event.getResidence(), event.getResidence().getSubzoneDeep());
        Residence.getDynManager().fireUpdateAdd(event.getResidence(), event.getResidence().getSubzoneDeep());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceRent(ResidenceRentEvent event) {
        Residence.getDynManager().fireUpdateAdd(event.getResidence(), event.getResidence().getSubzoneDeep());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceSizeChange(ResidenceSizeChangeEvent event) {
        Residence.getDynManager().fireUpdateAdd(event.getResidence(), event.getResidence().getSubzoneDeep());
    }
}

