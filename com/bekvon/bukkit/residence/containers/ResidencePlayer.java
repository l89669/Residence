/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.containers;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.vaultinterface.ResidenceVaultAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ResidencePlayer {
    private String userName = null;
    private Player player = null;
    private OfflinePlayer ofPlayer = null;
    private Map<String, ClaimedResidence> ResidenceList = new HashMap<String, ClaimedResidence>();
    private ClaimedResidence mainResidence = null;
    private PermissionGroup group = null;
    private int maxRes = -1;
    private int maxRents = -1;
    private int maxSubzones = -1;

    public ResidencePlayer(Player player) {
        this.player = player;
        this.updateName();
        this.RecalculatePermissions();
    }

    public ResidencePlayer(String userName) {
        this.userName = userName;
        this.player = Bukkit.getPlayer((String)userName);
        if (this.player == null) {
            this.ofPlayer = Residence.getOfflinePlayer(userName);
        }
        if (this.ofPlayer != null) {
            this.userName = this.ofPlayer.getName();
        }
        this.RecalculatePermissions();
    }

    public void setMainResidence(ClaimedResidence res) {
        if (this.mainResidence != null) {
            this.mainResidence.setMainResidence(false);
        }
        this.mainResidence = res;
    }

    public ClaimedResidence getMainResidence() {
        if (this.mainResidence == null) {
            for (Map.Entry<String, ClaimedResidence> one : this.ResidenceList.entrySet()) {
                if (one.getValue() == null || !one.getValue().isMainResidence()) continue;
                this.mainResidence = one.getValue();
                return this.mainResidence;
            }
            for (String one : Residence.getRentManager().getRentedLands(this.player.getName())) {
                ClaimedResidence res = Residence.getResidenceManager().getByName(one);
                if (res == null) continue;
                this.mainResidence = res;
                return this.mainResidence;
            }
            for (Map.Entry one : this.ResidenceList.entrySet()) {
                if (one.getValue() == null) continue;
                this.mainResidence = (ClaimedResidence)one.getValue();
                return this.mainResidence;
            }
        }
        return this.mainResidence;
    }

    public void RecalculatePermissions() {
        if (this.player == null) {
            this.player = Bukkit.getPlayerExact((String)this.userName);
        }
        if (this.player == null && this.ofPlayer == null) {
            this.ofPlayer = Residence.getOfflinePlayer(this.userName);
        }
        this.getGroup();
        this.recountMaxRes();
        this.recountMaxRents();
        this.recountMaxSubzones();
    }

    public void recountMaxRes() {
        this.updateName();
        if (this.group != null) {
            this.maxRes = this.group.getMaxZones();
        }
        int i = 1;
        while (i <= Residence.getConfigManager().getMaxResCount()) {
            if (this.player != null && this.player.isOnline()) {
                if (this.player.hasPermission("residence.max.res." + i)) {
                    this.maxRes = i;
                }
            } else if (this.ofPlayer != null && ResidenceVaultAdapter.hasPermission(this.ofPlayer, "residence.max.res." + i, Residence.getConfigManager().getDefaultWorld())) {
                this.maxRes = i;
            }
            ++i;
        }
    }

    public void recountMaxRents() {
        this.updateName();
        int i = 1;
        while (i <= Residence.getConfigManager().getMaxRentCount()) {
            if (this.player != null) {
                if (this.player.isPermissionSet("residence.max.rents." + i)) {
                    this.maxRents = i;
                }
            } else if (this.ofPlayer != null && ResidenceVaultAdapter.hasPermission(this.ofPlayer, "residence.max.rents." + i, Residence.getConfigManager().getDefaultWorld())) {
                this.maxRents = i;
            }
            ++i;
        }
        int m = this.getGroup().getMaxRents();
        if (this.maxRents < m) {
            this.maxRents = m;
        }
    }

    public void recountMaxSubzones() {
        this.updateName();
        int i = 1;
        while (i <= Residence.getConfigManager().getMaxSubzonesCount()) {
            if (this.player != null) {
                if (this.player.isPermissionSet("residence.max.subzones." + i)) {
                    this.maxSubzones = i;
                }
            } else if (this.ofPlayer != null && ResidenceVaultAdapter.hasPermission(this.ofPlayer, "residence.max.subzones." + i, Residence.getConfigManager().getDefaultWorld())) {
                this.maxSubzones = i;
            }
            ++i;
        }
        int m = this.getGroup().getMaxSubzoneDepth();
        if (this.maxSubzones < m) {
            this.maxSubzones = m;
        }
    }

    public int getMaxRes() {
        this.updateName();
        Residence.getPermissionManager().updateGroupNameForPlayer(this.userName, this.player != null && this.player.isOnline() ? this.player.getPlayer().getLocation().getWorld().getName() : Residence.getConfigManager().getDefaultWorld(), true);
        this.recountMaxRes();
        PermissionGroup g = this.getGroup();
        if (this.maxRes < g.getMaxZones()) {
            return g.getMaxZones();
        }
        return this.maxRes;
    }

    public int getMaxRents() {
        this.recountMaxRents();
        return this.maxRents;
    }

    public int getMaxSubzones() {
        this.recountMaxSubzones();
        return this.maxSubzones;
    }

    public PermissionGroup getGroup() {
        return this.getGroup(this.player != null ? this.player.getWorld().getName() : Residence.getConfigManager().getDefaultWorld());
    }

    public PermissionGroup getGroup(String world) {
        String name = this.userName;
        if (this.player != null) {
            name = this.player.getName();
        }
        String gp = Residence.getPermissionManager().getGroupNameByPlayer(name, world);
        this.group = Residence.getPermissionManager().getGroupByName(gp);
        return this.group;
    }

    public void recountRes() {
        this.updateName();
        if (this.userName != null) {
            ResidenceManager m = Residence.getResidenceManager();
            this.ResidenceList = m.getResidenceMapList(this.userName, true);
        }
    }

    private void updateName() {
        if (this.userName != null) {
            return;
        }
        if (this.player != null) {
            this.userName = this.player.getName();
            return;
        }
        if (this.ofPlayer != null) {
            this.userName = this.ofPlayer.getName();
            return;
        }
    }

    public void addResidence(ClaimedResidence residence) {
        if (residence == null) {
            return;
        }
        this.ResidenceList.put(residence.getName().toLowerCase(), residence);
    }

    public void removeResidence(String residence) {
        if (residence == null) {
            return;
        }
        residence = residence.toLowerCase();
        this.ResidenceList.remove(residence);
    }

    public void renameResidence(String oldResidence, String newResidence) {
        if (oldResidence == null) {
            return;
        }
        if (newResidence == null) {
            return;
        }
        ClaimedResidence res = this.ResidenceList.get(oldResidence = oldResidence.toLowerCase());
        if (res != null) {
            this.removeResidence(oldResidence);
            this.ResidenceList.put(newResidence.toLowerCase(), res);
        }
    }

    public int getResAmount() {
        return this.ResidenceList.size();
    }

    public List<ClaimedResidence> getResList() {
        ArrayList<ClaimedResidence> temp = new ArrayList<ClaimedResidence>();
        for (Map.Entry<String, ClaimedResidence> one : this.ResidenceList.entrySet()) {
            temp.add(one.getValue());
        }
        return temp;
    }

    public Map<String, ClaimedResidence> getResidenceMap() {
        return this.ResidenceList;
    }
}

