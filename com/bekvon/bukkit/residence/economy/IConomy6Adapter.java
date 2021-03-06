/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.iCo6.iConomy
 *  com.iCo6.system.Account
 *  com.iCo6.system.Accounts
 *  com.iCo6.system.Holdings
 */
package com.bekvon.bukkit.residence.economy;

import com.bekvon.bukkit.residence.economy.EconomyInterface;
import com.iCo6.iConomy;
import com.iCo6.system.Account;
import com.iCo6.system.Accounts;
import com.iCo6.system.Holdings;

public class IConomy6Adapter
implements EconomyInterface {
    iConomy icon;

    public IConomy6Adapter(iConomy iconomy) {
        this.icon = iconomy;
    }

    @Override
    public double getBalance(String playerName) {
        IConomy6Adapter.checkExist(playerName);
        return new Accounts().get(playerName).getHoldings().getBalance();
    }

    @Override
    public boolean canAfford(String playerName, double amount) {
        IConomy6Adapter.checkExist(playerName);
        double holdings = this.getBalance(playerName);
        if (holdings >= amount) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(String playerName, double amount) {
        IConomy6Adapter.checkExist(playerName);
        new Accounts().get(playerName).getHoldings().add(amount);
        return true;
    }

    @Override
    public boolean subtract(String playerName, double amount) {
        IConomy6Adapter.checkExist(playerName);
        if (this.canAfford(playerName, amount)) {
            new Accounts().get(playerName).getHoldings().subtract(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean transfer(String playerFrom, String playerTo, double amount) {
        IConomy6Adapter.checkExist(playerTo);
        IConomy6Adapter.checkExist(playerFrom);
        if (this.canAfford(playerFrom, amount)) {
            Account p1 = new Accounts().get(playerFrom);
            Account p2 = new Accounts().get(playerTo);
            p1.getHoldings().subtract(amount);
            p2.getHoldings().add(amount);
            return true;
        }
        return false;
    }

    private static void checkExist(String playerName) {
        Accounts acc = new Accounts();
        if (!acc.exists(playerName)) {
            acc.create(playerName);
        }
    }

    @Override
    public String getName() {
        return "iConomy";
    }
}

