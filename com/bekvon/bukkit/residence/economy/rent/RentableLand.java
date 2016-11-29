/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.economy.rent;

import com.bekvon.bukkit.residence.Residence;
import java.util.HashMap;
import java.util.Map;

public class RentableLand {
    public int days = 0;
    public int cost = Integer.MAX_VALUE;
    public boolean AllowRenewing = Residence.getConfigManager().isRentAllowRenewing();
    public boolean StayInMarket = Residence.getConfigManager().isRentStayInMarket();
    public boolean AllowAutoPay = Residence.getConfigManager().isRentAllowAutoPay();

    public Map<String, Object> save() {
        HashMap<String, Object> rented = new HashMap<String, Object>();
        rented.put("Days", this.days);
        rented.put("Cost", this.cost);
        rented.put("Repeatable", this.AllowRenewing);
        rented.put("StayInMarket", this.StayInMarket);
        rented.put("AllowAutoPay", this.AllowAutoPay);
        return rented;
    }
}

