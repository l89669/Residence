/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  cosine.boseconomy.BOSEconomy
 */
package com.bekvon.bukkit.residence.economy;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.economy.EconomyInterface;
import cosine.boseconomy.BOSEconomy;

public class BOSEAdapter
implements EconomyInterface {
    BOSEconomy plugin;

    public BOSEAdapter(BOSEconomy p) {
        this.plugin = p;
        String serverland = Residence.getServerLandname();
        if (!this.plugin.playerRegistered(serverland, false)) {
            this.plugin.registerPlayer(serverland);
        }
    }

    @Override
    public double getBalance(String playerName) {
        return this.plugin.getPlayerMoneyDouble(playerName);
    }

    @Override
    public boolean canAfford(String playerName, double amount) {
        double balance = this.plugin.getPlayerMoneyDouble(playerName);
        if (balance >= amount) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(String playerName, double amount) {
        return this.plugin.setPlayerMoney(playerName, this.plugin.getPlayerMoneyDouble(playerName) + amount, false);
    }

    @Override
    public boolean subtract(String playerName, double amount) {
        if (this.canAfford(playerName, amount)) {
            return this.plugin.setPlayerMoney(playerName, this.plugin.getPlayerMoneyDouble(playerName) - amount, false);
        }
        return false;
    }

    @Override
    public boolean transfer(String playerFrom, String playerTo, double amount) {
        if (this.canAfford(playerFrom, amount)) {
            if (!this.subtract(playerFrom, amount)) {
                return false;
            }
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
        return "BOSEconomy";
    }
}

