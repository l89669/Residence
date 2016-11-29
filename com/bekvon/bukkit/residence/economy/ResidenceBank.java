/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.economy;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResidenceBank {
    int storedMoney = 0;
    ClaimedResidence res;

    public ResidenceBank(ClaimedResidence parent) {
        this.res = parent;
    }

    public int getStoredMoney() {
        return this.storedMoney;
    }

    public void setStoredMoney(int amount) {
        this.storedMoney = amount;
    }

    public void add(int amount) {
        this.storedMoney += amount;
    }

    public boolean hasEnough(int amount) {
        if (this.storedMoney >= amount) {
            return true;
        }
        return false;
    }

    public void subtract(int amount) {
        this.storedMoney -= amount;
        if (this.storedMoney < 0) {
            this.storedMoney = 0;
        }
    }

    public void withdraw(CommandSender sender, int amount, boolean resadmin2) {
        if (!Residence.getConfigManager().enableEconomy()) {
            Residence.msg(sender, lm.Economy_MarketDisabled, new Object[0]);
        }
        if (!resadmin2 && !this.res.getPermissions().playerHas(sender.getName(), Flags.bank, FlagPermissions.FlagCombo.OnlyTrue)) {
            Residence.msg(sender, lm.Bank_NoAccess, new Object[0]);
            return;
        }
        if (!this.hasEnough(amount)) {
            Residence.msg(sender, lm.Bank_NoMoney, new Object[0]);
            return;
        }
        if (sender instanceof Player && Residence.getEconomyManager().add(sender.getName(), amount) || !(sender instanceof Player)) {
            this.subtract(amount);
            Residence.msg(sender, lm.Bank_Withdraw, String.format("%d", amount));
        }
    }

    public void deposit(CommandSender sender, int amount, boolean resadmin2) {
        if (!Residence.getConfigManager().enableEconomy()) {
            Residence.msg(sender, lm.Economy_MarketDisabled, new Object[0]);
        }
        if (!resadmin2 && !this.res.getPermissions().playerHas(sender.getName(), Flags.bank, FlagPermissions.FlagCombo.OnlyTrue)) {
            Residence.msg(sender, lm.Bank_NoAccess, new Object[0]);
            return;
        }
        if (sender instanceof Player && !Residence.getEconomyManager().canAfford(sender.getName(), amount)) {
            Residence.msg(sender, lm.Economy_NotEnoughMoney, new Object[0]);
            return;
        }
        if (sender instanceof Player && Residence.getEconomyManager().subtract(sender.getName(), amount) || !(sender instanceof Player)) {
            this.add(amount);
            Residence.msg(sender, lm.Bank_Deposit, String.format("%d", amount));
        }
    }
}

