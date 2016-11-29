/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  fr.crafter.tickleman.realeconomy.RealEconomy
 */
package com.bekvon.bukkit.residence.economy;

import com.bekvon.bukkit.residence.economy.EconomyInterface;
import fr.crafter.tickleman.realeconomy.RealEconomy;

public class RealShopEconomy
implements EconomyInterface {
    RealEconomy plugin;

    public RealShopEconomy(RealEconomy e) {
        this.plugin = e;
    }

    @Override
    public double getBalance(String playerName) {
        return this.plugin.getBalance(playerName);
    }

    @Override
    public boolean canAfford(String playerName, double amount) {
        if (this.plugin.getBalance(playerName) >= amount) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(String playerName, double amount) {
        this.plugin.setBalance(playerName, this.plugin.getBalance(playerName) + amount);
        return true;
    }

    @Override
    public boolean subtract(String playerName, double amount) {
        if (!this.canAfford(playerName, amount)) {
            return false;
        }
        this.plugin.setBalance(playerName, this.plugin.getBalance(playerName) - amount);
        return true;
    }

    @Override
    public boolean transfer(String playerFrom, String playerTo, double amount) {
        if (!this.canAfford(playerFrom, amount)) {
            return false;
        }
        if (this.subtract(playerFrom, amount)) {
            if (!this.add(playerTo, amount)) {
                this.add(playerFrom, amount);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "RealShopEconomy";
    }
}

