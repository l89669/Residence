/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.api;

import java.util.Map;
import org.bukkit.entity.Player;

public interface MarketBuyInterface {
    public Map<String, Integer> getBuyableResidences();

    public boolean putForSale(String var1, int var2);

    public void buyPlot(String var1, Player var2, boolean var3);

    public void removeFromSale(String var1);

    public boolean isForSale(String var1);

    public int getSaleAmount(String var1);
}

