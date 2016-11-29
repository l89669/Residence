/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.chat.Chat
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  net.milkbowl.vault.permission.Permission
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 */
package com.bekvon.bukkit.residence.vaultinterface;

import com.bekvon.bukkit.residence.economy.EconomyInterface;
import com.bekvon.bukkit.residence.permissions.PermissionsInterface;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class ResidenceVaultAdapter
implements EconomyInterface,
PermissionsInterface {
    public static Permission permissions = null;
    public static Economy economy = null;
    public static Chat chat = null;

    public boolean permissionsOK() {
        if (permissions != null && !permissions.getName().equalsIgnoreCase("SuperPerms")) {
            return true;
        }
        return false;
    }

    public boolean economyOK() {
        if (economy != null) {
            return true;
        }
        return false;
    }

    public boolean chatOK() {
        if (chat != null) {
            return true;
        }
        return false;
    }

    public ResidenceVaultAdapter(Server s) {
        ResidenceVaultAdapter.setupPermissions(s);
        ResidenceVaultAdapter.setupEconomy(s);
        ResidenceVaultAdapter.setupChat(s);
    }

    private static boolean setupPermissions(Server s) {
        RegisteredServiceProvider permissionProvider = s.getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permissions = (Permission)permissionProvider.getProvider();
        }
        if (permissions != null) {
            return true;
        }
        return false;
    }

    private static boolean setupChat(Server s) {
        RegisteredServiceProvider chatProvider = s.getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = (Chat)chatProvider.getProvider();
        }
        if (chat != null) {
            return true;
        }
        return false;
    }

    private static boolean setupEconomy(Server s) {
        RegisteredServiceProvider economyProvider = s.getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy)economyProvider.getProvider();
        }
        if (economy != null) {
            return true;
        }
        return false;
    }

    @Override
    public String getPlayerGroup(Player player) {
        String group = permissions.getPrimaryGroup(player).toLowerCase();
        if (group == null) {
            return group;
        }
        return group.toLowerCase();
    }

    @Override
    public String getPlayerGroup(String player, String world) {
        String group = permissions.getPrimaryGroup(world, player);
        if (group == null) {
            return group;
        }
        return group.toLowerCase();
    }

    public static boolean hasPermission(OfflinePlayer player, String perm, String world) {
        if (permissions == null) {
            return false;
        }
        try {
            return permissions.playerHas(world, player, perm);
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public double getBalance(String playerName) {
        return economy.getBalance(playerName);
    }

    @Override
    public boolean canAfford(String playerName, double amount) {
        return economy.has(playerName, amount);
    }

    @Override
    public boolean add(String playerName, double amount) {
        return economy.depositPlayer(playerName, amount).transactionSuccess();
    }

    @Override
    public boolean subtract(String playerName, double amount) {
        return economy.withdrawPlayer(playerName, amount).transactionSuccess();
    }

    @Override
    public boolean transfer(String playerFrom, String playerTo, double amount) {
        if (economy.withdrawPlayer(playerFrom, amount).transactionSuccess()) {
            if (economy.depositPlayer(playerTo, amount).transactionSuccess()) {
                return true;
            }
            economy.depositPlayer(playerFrom, amount);
            return false;
        }
        return false;
    }

    public String getEconomyName() {
        if (economy != null) {
            return economy.getName();
        }
        return "";
    }

    public String getPermissionsName() {
        if (permissions != null) {
            return permissions.getName();
        }
        return "";
    }

    public String getChatName() {
        if (chat != null) {
            return chat.getName();
        }
        return "";
    }

    @Override
    public String getName() {
        return "Vault";
    }
}

