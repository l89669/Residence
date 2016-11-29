/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.economy;

public interface EconomyInterface {
    public double getBalance(String var1);

    public boolean canAfford(String var1, double var2);

    public boolean add(String var1, double var2);

    public boolean subtract(String var1, double var2);

    public boolean transfer(String var1, String var2, double var3);

    public String getName();
}

