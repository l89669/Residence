/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidencePlayerInterface;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerManager
implements ResidencePlayerInterface {
    private ConcurrentHashMap<String, ResidencePlayer> players = new ConcurrentHashMap();

    public void playerJoin(Player player) {
        ResidencePlayer resPlayer = this.players.get(player.getName().toLowerCase());
        if (resPlayer == null) {
            resPlayer = new ResidencePlayer(player);
            resPlayer.recountRes();
            this.players.put(player.getName().toLowerCase(), resPlayer);
        } else {
            resPlayer.RecalculatePermissions();
        }
    }

    public ResidencePlayer playerJoin(String player) {
        if (!this.players.containsKey(player.toLowerCase())) {
            ResidencePlayer resPlayer = new ResidencePlayer(player);
            resPlayer.recountRes();
            this.players.put(player.toLowerCase(), resPlayer);
            return resPlayer;
        }
        return null;
    }

    public void fillList() {
        this.players.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.playerJoin(player);
        }
    }

    @Override
    public ArrayList<String> getResidenceList(String player) {
        ArrayList<String> temp = new ArrayList<String>();
        this.playerJoin(player);
        ResidencePlayer resPlayer = this.players.get(player.toLowerCase());
        if (resPlayer != null) {
            for (ClaimedResidence one : resPlayer.getResList()) {
                temp.add(one.getName());
            }
            return temp;
        }
        return temp;
    }

    @Override
    public ArrayList<String> getResidenceList(String player, boolean showhidden) {
        return this.getResidenceList(player, showhidden, false);
    }

    public ArrayList<String> getResidenceList(String player, boolean showhidden, boolean onlyHidden) {
        ArrayList<String> temp = new ArrayList<String>();
        this.playerJoin(player);
        ResidencePlayer resPlayer = this.players.get(player.toLowerCase());
        if (resPlayer == null) {
            return temp;
        }
        for (ClaimedResidence one : resPlayer.getResList()) {
            boolean hidden = one.getPermissions().has("hidden", false);
            if (!showhidden && hidden || onlyHidden && !hidden) continue;
            temp.add(String.valueOf(Residence.msg(lm.Residence_List, "", one.getName(), one.getWorld())) + (hidden ? Residence.msg(lm.Residence_Hidden, new Object[0]) : ""));
        }
        Collections.sort(temp, String.CASE_INSENSITIVE_ORDER);
        return temp;
    }

    public ArrayList<ClaimedResidence> getResidences(String player, boolean showhidden) {
        return this.getResidences(player, showhidden, false);
    }

    public ArrayList<ClaimedResidence> getResidences(String player, boolean showhidden, boolean onlyHidden) {
        return this.getResidences(player, showhidden, onlyHidden, null);
    }

    public ArrayList<ClaimedResidence> getResidences(String player, boolean showhidden, boolean onlyHidden, World world) {
        ArrayList<ClaimedResidence> temp = new ArrayList<ClaimedResidence>();
        this.playerJoin(player);
        ResidencePlayer resPlayer = this.players.get(player.toLowerCase());
        if (resPlayer == null) {
            return temp;
        }
        for (ClaimedResidence one : resPlayer.getResList()) {
            boolean hidden = one.getPermissions().has("hidden", false);
            if (!showhidden && hidden || onlyHidden && !hidden || world != null && !world.getName().equalsIgnoreCase(one.getWorld())) continue;
            temp.add(one);
        }
        return temp;
    }

    public TreeMap<String, ClaimedResidence> getResidencesMap(String player, boolean showhidden, boolean onlyHidden, World world) {
        TreeMap<String, ClaimedResidence> temp = new TreeMap<String, ClaimedResidence>();
        this.playerJoin(player);
        ResidencePlayer resPlayer = this.players.get(player.toLowerCase());
        if (resPlayer == null) {
            return temp;
        }
        for (Map.Entry<String, ClaimedResidence> one : resPlayer.getResidenceMap().entrySet()) {
            boolean hidden = one.getValue().getPermissions().has(Flags.hidden, false);
            if (!showhidden && hidden || onlyHidden && !hidden || world != null && !world.getName().equalsIgnoreCase(one.getValue().getWorld())) continue;
            temp.put(one.getKey(), one.getValue());
        }
        return temp;
    }

    @Override
    public PermissionGroup getGroup(String player) {
        ResidencePlayer resPlayer = this.getResidencePlayer(player);
        if (resPlayer != null) {
            return resPlayer.getGroup();
        }
        return null;
    }

    @Override
    public int getMaxResidences(String player) {
        ResidencePlayer resPlayer = this.getResidencePlayer(player);
        if (resPlayer != null) {
            return resPlayer.getMaxRes();
        }
        return -1;
    }

    @Override
    public int getMaxSubzones(String player) {
        ResidencePlayer resPlayer = this.getResidencePlayer(player);
        if (resPlayer != null) {
            return resPlayer.getMaxSubzones();
        }
        return -1;
    }

    @Override
    public int getMaxRents(String player) {
        ResidencePlayer resPlayer = this.getResidencePlayer(player);
        if (resPlayer != null) {
            return resPlayer.getMaxRents();
        }
        return -1;
    }

    public ResidencePlayer getResidencePlayer(Player player) {
        return this.getResidencePlayer(player.getName());
    }

    @Override
    public ResidencePlayer getResidencePlayer(String player) {
        ResidencePlayer resPlayer = null;
        resPlayer = this.players.containsKey(player.toLowerCase()) ? this.players.get(player.toLowerCase()) : this.playerJoin(player);
        return resPlayer;
    }

    public void renameResidence(String player, String oldName, String newName) {
        ResidencePlayer resPlayer = this.getResidencePlayer(player);
        if (resPlayer != null) {
            resPlayer.renameResidence(oldName, newName);
        }
    }

    public void addResidence(String player, ClaimedResidence residence) {
        ResidencePlayer resPlayer = this.getResidencePlayer(player);
        if (resPlayer != null) {
            resPlayer.addResidence(residence);
        }
    }

    public void removeResFromPlayer(OfflinePlayer player, String residence) {
        this.removeResFromPlayer(player.getName(), residence);
    }

    public void removeResFromPlayer(Player player, String residence) {
        this.removeResFromPlayer(player.getName(), residence);
    }

    public void removeResFromPlayer(String player, String residence) {
        ResidencePlayer resPlayer = this.players.get(player.toLowerCase());
        if (resPlayer != null) {
            resPlayer.removeResidence(residence);
        }
    }
}

