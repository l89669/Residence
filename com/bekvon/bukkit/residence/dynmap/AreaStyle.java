/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.dynmap;

import com.bekvon.bukkit.residence.ConfigManager;
import com.bekvon.bukkit.residence.Residence;

class AreaStyle {
    String strokecolor;
    String forrentstrokecolor;
    String rentedstrokecolor;
    String forsalestrokecolor;
    double strokeopacity;
    int strokeweight;
    String fillcolor;
    double fillopacity;
    int y;

    AreaStyle() {
        this.strokecolor = Residence.getConfigManager().DynMapBorderColor;
        this.forrentstrokecolor = Residence.getConfigManager().DynMapFillForRent;
        this.rentedstrokecolor = Residence.getConfigManager().DynMapFillRented;
        this.forsalestrokecolor = Residence.getConfigManager().DynMapFillForSale;
        this.strokeopacity = Residence.getConfigManager().DynMapBorderOpacity;
        this.strokeweight = Residence.getConfigManager().DynMapBorderWeight;
        this.fillcolor = Residence.getConfigManager().DynMapFillColor;
        this.fillopacity = Residence.getConfigManager().DynMapFillOpacity;
        this.y = 64;
    }
}

