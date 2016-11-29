/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.api.Economy
 *  com.earth2me.essentials.api.NoLoanPermittedException
 *  com.earth2me.essentials.api.UserDoesNotExistException
 */
package com.bekvon.bukkit.residence.economy;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.economy.EconomyInterface;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class EssentialsEcoAdapter
implements EconomyInterface {
    Essentials plugin;

    public EssentialsEcoAdapter(Essentials p) {
        this.plugin = p;
        String serverland = Residence.getServerLandname();
        if (!Economy.playerExists((String)serverland)) {
            Economy.createNPC((String)serverland);
        }
    }

    @Override
    public double getBalance(String playerName) {
        try {
            if (Economy.playerExists((String)playerName)) {
                return Economy.getMoney((String)playerName);
            }
            return 0.0;
        }
        catch (UserDoesNotExistException ex) {
            return 0.0;
        }
    }

    @Override
    public boolean canAfford(String playerName, double amount) {
        try {
            if (Economy.playerExists((String)playerName)) {
                return Economy.hasEnough((String)playerName, (double)amount);
            }
            return false;
        }
        catch (UserDoesNotExistException ex) {
            return false;
        }
    }

    @Override
    public boolean add(String playerName, double amount) {
        if (Economy.playerExists((String)playerName)) {
            try {
                Economy.add((String)playerName, (double)amount);
                return true;
            }
            catch (UserDoesNotExistException ex) {
                return false;
            }
            catch (NoLoanPermittedException ex) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean subtract(String playerName, double amount) {
        if (Economy.playerExists((String)playerName)) {
            try {
                Economy.subtract((String)playerName, (double)amount);
                return true;
            }
            catch (UserDoesNotExistException ex) {
                return false;
            }
            catch (NoLoanPermittedException ex) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean transfer(String playerFrom, String playerTo, double amount) {
        block4 : {
            block5 : {
                try {
                    if (!Economy.playerExists((String)playerFrom) || !Economy.playerExists((String)playerTo) || !Economy.hasEnough((String)playerFrom, (double)amount)) break block4;
                    if (this.subtract(playerFrom, amount)) break block5;
                    return false;
                }
                catch (UserDoesNotExistException ex) {
                    return false;
                }
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
        return "EssentialsEconomy";
    }
}

