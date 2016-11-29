/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.containers;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Visualizer {
    private Player player;
    private long start;
    private List<CuboidArea> areas = new ArrayList<CuboidArea>();
    private List<CuboidArea> errorAreas = new ArrayList<CuboidArea>();
    private int id = -1;
    private int errorId = -1;
    private boolean once = false;
    private List<Location> locations = new ArrayList<Location>();
    private List<Location> errorLocations = new ArrayList<Location>();
    private List<Location> locations2 = new ArrayList<Location>();
    private List<Location> errorLocations2 = new ArrayList<Location>();
    private Location loc = null;

    public Visualizer(Player player) {
        this.player = player;
        this.start = System.currentTimeMillis();
    }

    public boolean isSameLoc() {
        if (this.loc == null) {
            return false;
        }
        if (this.loc.getWorld() != this.player.getWorld()) {
            return false;
        }
        if (!this.errorAreas.isEmpty() && this.errorLocations.isEmpty()) {
            return false;
        }
        if (this.loc.distance(this.player.getLocation()) > 5.0) {
            return false;
        }
        return true;
    }

    public long getStart() {
        return this.start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<CuboidArea> getAreas() {
        return this.areas;
    }

    public void setAreas(ClaimedResidence res) {
        if (res != null) {
            this.areas = Arrays.asList(res.getAreaArray());
        }
    }

    public void setAreas(CuboidArea[] areas) {
        this.areas = Arrays.asList(areas);
    }

    public void setAreas(ArrayList<CuboidArea> areas) {
        this.areas = areas;
    }

    public void setAreas(CuboidArea area2) {
        this.areas.add(area2);
    }

    public List<CuboidArea> getErrorAreas() {
        return this.errorAreas;
    }

    public void setErrorAreas(ClaimedResidence res) {
        if (res != null) {
            this.errorAreas = Arrays.asList(res.getAreaArray());
        }
    }

    public void setErrorAreas(CuboidArea[] errorAreas) {
        this.errorAreas = Arrays.asList(errorAreas);
    }

    public void setErrorAreas(ArrayList<CuboidArea> errorAreas) {
        this.errorAreas = errorAreas;
    }

    public void setErrorAreas(CuboidArea errorArea) {
        this.errorAreas.add(errorArea);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getErrorId() {
        return this.errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public boolean isOnce() {
        return this.once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations2() {
        return this.locations2;
    }

    public void setLocations2(List<Location> locations) {
        this.locations2 = locations;
    }

    public List<Location> getErrorLocations() {
        return this.errorLocations;
    }

    public void setErrorLocations(List<Location> errorLocations) {
        this.errorLocations = errorLocations;
    }

    public List<Location> getErrorLocations2() {
        return this.errorLocations2;
    }

    public void setErrorLocations2(List<Location> errorLocations) {
        this.errorLocations2 = errorLocations;
    }

    public Location getLoc() {
        return this.loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
}

