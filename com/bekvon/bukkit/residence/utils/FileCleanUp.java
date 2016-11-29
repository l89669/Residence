/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.vaultinterface.ResidenceVaultAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class FileCleanUp {
    public static void cleanFiles() {
        ArrayList<String> resNameList = Residence.getResidenceManager().getResidenceList(false, false);
        int i = 0;
        OfflinePlayer[] offplayer = Bukkit.getOfflinePlayers();
        HashMap<UUID, OfflinePlayer> playermap = new HashMap<UUID, OfflinePlayer>();
        OfflinePlayer[] arrofflinePlayer = offplayer;
        int n = arrofflinePlayer.length;
        int n2 = 0;
        while (n2 < n) {
            OfflinePlayer one = arrofflinePlayer[n2];
            playermap.put(one.getUniqueId(), one);
            ++n2;
        }
        int interval = Residence.getConfigManager().getResidenceFileCleanDays();
        long time = System.currentTimeMillis();
        for (String oneName : resNameList) {
            int dif;
            OfflinePlayer player;
            long lastPlayed;
            ClaimedResidence res = Residence.getResidenceManager().getByName(oneName);
            if (res == null || !playermap.containsKey(res.getOwnerUUID()) || (player = (OfflinePlayer)playermap.get(res.getOwnerUUID())) == null || !Residence.getConfigManager().getCleanWorlds().contains(res.getWorld()) || res.getOwner().equalsIgnoreCase("server land") || res.getOwner().equalsIgnoreCase(Residence.getServerLandname()) || (dif = (int)((time - (lastPlayed = player.getLastPlayed())) / 1000 / 60 / 60 / 24)) < interval || ResidenceVaultAdapter.hasPermission(player, "residence.cleanbypass", res.getWorld())) continue;
            Residence.getResidenceManager().removeResidence(oneName);
            ++i;
        }
        Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Auto CleanUp deleted " + i + " residences!");
    }
}

