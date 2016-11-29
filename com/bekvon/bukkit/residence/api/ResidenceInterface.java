/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ResidenceInterface {
    public ClaimedResidence getByLoc(Location var1);

    public ClaimedResidence getByName(String var1);

    public String getNameByLoc(Location var1);

    public String getNameByRes(ClaimedResidence var1);

    public String getSubzoneNameByRes(ClaimedResidence var1);

    public void addShop(ClaimedResidence var1);

    public void addShop(String var1);

    public void removeShop(ClaimedResidence var1);

    public void removeShop(String var1);

    public List<ClaimedResidence> getShops();

    public boolean addResidence(String var1, Location var2, Location var3);

    public boolean addResidence(String var1, String var2, Location var3, Location var4);

    public boolean addResidence(Player var1, String var2, Location var3, Location var4, boolean var5);
}

