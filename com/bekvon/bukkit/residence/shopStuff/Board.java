/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 */
package com.bekvon.bukkit.residence.shopStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.World;

public class Board {
    private Location TopLoc = null;
    private Location BottomLoc = null;
    int StartPlace = 0;
    List<Location> Locations = new ArrayList<Location>();
    HashMap<String, Location> SignLocations = new HashMap();

    public void clearSignLoc() {
        this.SignLocations.clear();
    }

    public void addSignLoc(String resName, Location loc) {
        this.SignLocations.put(resName, loc);
    }

    public HashMap<String, Location> getSignLocations() {
        return this.SignLocations;
    }

    public Location getSignLocByName(String resName) {
        return this.SignLocations.get(resName);
    }

    public String getResNameByLoc(Location location) {
        for (Map.Entry<String, Location> One : this.SignLocations.entrySet()) {
            Location loc = One.getValue();
            if (!loc.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()) || loc.getBlockX() != location.getBlockX() || loc.getBlockY() != location.getBlockY() || loc.getBlockZ() != location.getBlockZ()) continue;
            return One.getKey();
        }
        return null;
    }

    public List<Location> GetLocations() {
        this.Locations.clear();
        if (this.TopLoc == null || this.BottomLoc == null) {
            return null;
        }
        if (this.TopLoc.getWorld() == null) {
            return null;
        }
        int xLength = this.TopLoc.getBlockX() - this.BottomLoc.getBlockX();
        int yLength = this.TopLoc.getBlockY() - this.BottomLoc.getBlockY();
        int zLength = this.TopLoc.getBlockZ() - this.BottomLoc.getBlockZ();
        if (xLength < 0) {
            xLength *= -1;
        }
        if (zLength < 0) {
            zLength *= -1;
        }
        int y = 0;
        while (y <= yLength) {
            int x = 0;
            while (x <= xLength) {
                int z = 0;
                while (z <= zLength) {
                    int tempx = 0;
                    int tempz = 0;
                    tempx = this.TopLoc.getBlockX() > this.BottomLoc.getBlockX() ? this.TopLoc.getBlockX() - x : this.TopLoc.getBlockX() + x;
                    tempz = this.TopLoc.getBlockZ() > this.BottomLoc.getBlockZ() ? this.TopLoc.getBlockZ() - z : this.TopLoc.getBlockZ() + z;
                    this.Locations.add(new Location(this.TopLoc.getWorld(), (double)tempx, (double)(this.TopLoc.getBlockY() - y), (double)tempz));
                    ++z;
                }
                ++x;
            }
            ++y;
        }
        return this.Locations;
    }

    public void setStartPlace(int StartPlace) {
        this.StartPlace = StartPlace;
    }

    public int GetStartPlace() {
        return this.StartPlace == 0 ? 0 : this.StartPlace - 1;
    }

    public String GetWorld() {
        return this.TopLoc.getWorld().getName();
    }

    public Location getTopLoc() {
        return this.TopLoc;
    }

    public void setTopLoc(Location topLoc) {
        this.TopLoc = topLoc;
    }

    public Location getBottomLoc() {
        return this.BottomLoc;
    }

    public void setBottomLoc(Location bottomLoc) {
        this.BottomLoc = bottomLoc;
    }
}

