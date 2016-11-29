/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 */
package com.bekvon.bukkit.residence.containers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class RandomTeleport {
    String WorldName;
    int MaxCord;
    int MinCord;
    int centerX;
    int centerZ;
    Location loc;

    public RandomTeleport(String WorldName, int MaxCord, int MinCord, int centerX, int centerZ) {
        this.WorldName = WorldName;
        this.MaxCord = MaxCord;
        this.MinCord = MinCord;
        this.centerX = centerX;
        this.centerZ = centerZ;
    }

    public Location getCenter() {
        if (this.loc == null) {
            World w = Bukkit.getWorld((String)this.WorldName);
            this.loc = new Location(w, (double)this.centerX, 63.0, (double)this.centerZ);
        }
        return this.loc;
    }

    public String getWorld() {
        return this.WorldName;
    }

    public int getMaxCord() {
        return this.MaxCord;
    }

    public int getMinCord() {
        return this.MinCord;
    }

    public int getCenterX() {
        return this.centerX;
    }

    public int getCenterZ() {
        return this.centerZ;
    }
}

