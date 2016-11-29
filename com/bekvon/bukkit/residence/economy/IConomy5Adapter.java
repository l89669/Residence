/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.iConomy.iConomy
 *  com.iConomy.system.Account
 *  com.iConomy.system.Holdings
 */
package com.bekvon.bukkit.residence.economy;

import com.bekvon.bukkit.residence.economy.EconomyInterface;
import com.iConomy.iConomy;
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;

public class IConomy5Adapter
implements EconomyInterface {
    @Override
    public double getBalance(String playerName) {
        Account acc = iConomy.getAccount((String)playerName);
        return acc == null ? 0.0 : acc.getHoldings().balance();
    }

    @Override
    public boolean canAfford(String playerName, double amount) {
        if (amount == 0.0) {
            return true;
        }
        Account acc = iConomy.getAccount((String)playerName);
        return acc == null ? false : acc.getHoldings().hasEnough(amount);
    }

    @Override
    public boolean add(String playerName, double amount) {
        Account acc = iConomy.getAccount((String)playerName);
        if (acc != null) {
            acc.getHoldings().add(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean subtract(String playerName, double amount) {
        Account acc = iConomy.getAccount((String)playerName);
        if (acc != null) {
            acc.getHoldings().subtract(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean transfer(String playerFrom, String playerTo, double amount) {
        Account accFrom = iConomy.getAccount((String)playerFrom);
        Account accTo = iConomy.getAccount((String)playerTo);
        if (accFrom != null && accTo != null) {
            accFrom.getHoldings().subtract(amount);
            accTo.getHoldings().add(amount);
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "iConomy";
    }
}

