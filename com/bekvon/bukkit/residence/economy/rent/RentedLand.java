/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.economy.rent;

import java.util.HashMap;
import java.util.Map;

public class RentedLand {
    public String player = "";
    public long startTime = 0;
    public long endTime = 0;
    public boolean AutoPay = true;

    public Map<String, Object> save() {
        HashMap<String, Object> rentables = new HashMap<String, Object>();
        rentables.put("Player", this.player);
        rentables.put("StartTime", this.startTime);
        rentables.put("EndTime", this.endTime);
        rentables.put("AutoRefresh", this.AutoPay);
        return rentables;
    }
}

