/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 */
package com.bekvon.bukkit.residence.signsStuff;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Location;

public class Signs {
    int Category = 0;
    ClaimedResidence Residence = null;
    Location loc = null;

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public Location GetLocation() {
        return this.loc;
    }

    public void setCategory(int Category2) {
        this.Category = Category2;
    }

    public int GetCategory() {
        return this.Category;
    }

    public void setResidence(ClaimedResidence Residence2) {
        this.Residence = Residence2;
    }

    public ClaimedResidence GetResidence() {
        return this.Residence;
    }
}

