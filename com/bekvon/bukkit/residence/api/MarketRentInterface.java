/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.List;
import java.util.Set;
import org.bukkit.entity.Player;

public interface MarketRentInterface {
    public Set<ClaimedResidence> getRentableResidences();

    public Set<ClaimedResidence> getCurrentlyRentedResidences();

    public RentedLand getRentedLand(String var1);

    public List<String> getRentedLands(String var1);

    public void setForRent(Player var1, String var2, int var3, int var4, boolean var5, boolean var6);

    public void setForRent(Player var1, String var2, int var3, int var4, boolean var5, boolean var6, boolean var7);

    public void setForRent(Player var1, String var2, int var3, int var4, boolean var5, boolean var6, boolean var7, boolean var8);

    public void rent(Player var1, String var2, boolean var3, boolean var4);

    public void removeFromForRent(Player var1, String var2, boolean var3);

    public void unrent(Player var1, String var2, boolean var3);

    public void removeFromRent(String var1);

    public void removeRentable(String var1);

    public boolean isForRent(String var1);

    public boolean isRented(String var1);

    public String getRentingPlayer(String var1);

    public int getCostOfRent(String var1);

    public boolean getRentableRepeatable(String var1);

    public boolean getRentedAutoRepeats(String var1);

    public int getRentDays(String var1);

    public void checkCurrentRents();

    public void setRentRepeatable(Player var1, String var2, boolean var3, boolean var4);

    public void setRentedRepeatable(Player var1, String var2, boolean var3, boolean var4);

    public int getRentCount(String var1);

    public int getRentableCount(String var1);
}

