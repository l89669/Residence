/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.nijiko.permissions.PermissionHandler
 *  com.nijikokun.bukkit.Permissions.Permissions
 *  com.platymuus.bukkit.permissions.PermissionsPlugin
 *  org.bukkit.Bukkit
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package com.bekvon.bukkit.residence.permissions;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.PlayerGroup;
import com.bekvon.bukkit.residence.permissions.BPermissionsAdapter;
import com.bekvon.bukkit.residence.permissions.LegacyPermissions;
import com.bekvon.bukkit.residence.permissions.OriginalPermissions;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.permissions.PermissionsBukkitAdapter;
import com.bekvon.bukkit.residence.permissions.PermissionsInterface;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.vaultinterface.ResidenceVaultAdapter;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PermissionManager {
    protected static PermissionsInterface perms;
    protected LinkedHashMap<String, PermissionGroup> groups;
    protected Map<String, String> playersGroup;
    protected FlagPermissions globalFlagPerms;
    protected HashMap<String, PlayerGroup> groupsMap = new HashMap();

    public PermissionManager() {
        try {
            this.groups = new LinkedHashMap();
            this.playersGroup = Collections.synchronizedMap(new HashMap());
            this.globalFlagPerms = new FlagPermissions();
            this.readConfig();
            PermissionManager.checkPermissions();
        }
        catch (Exception ex) {
            Logger.getLogger(PermissionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FlagPermissions getAllFlags() {
        return this.globalFlagPerms;
    }

    public Map<String, String> getPlayersGroups() {
        return this.playersGroup;
    }

    public Map<String, PermissionGroup> getGroups() {
        return this.groups;
    }

    public PermissionGroup getGroupByName(String group) {
        if (!this.groups.containsKey(group = group.toLowerCase())) {
            return this.groups.get(Residence.getConfigManager().getDefaultGroup());
        }
        return this.groups.get(group);
    }

    public String getGroupNameByPlayer(Player player) {
        String group;
        PlayerGroup PGroup;
        if (!this.groupsMap.containsKey(player.getName())) {
            this.updateGroupNameForPlayer(player);
        }
        if ((PGroup = this.groupsMap.get(player.getName())) != null && (group = PGroup.getGroup(player.getWorld().getName())) != null) {
            return group;
        }
        return Residence.getConfigManager().getDefaultGroup().toLowerCase();
    }

    public String getGroupNameByPlayer(String playerName, String world) {
        String group;
        PlayerGroup PGroup;
        if (!this.groupsMap.containsKey(playerName)) {
            Player player = Bukkit.getPlayer((String)playerName);
            if (player != null) {
                this.updateGroupNameForPlayer(player);
            } else {
                this.updateGroupNameForPlayer(playerName, world, true);
            }
        }
        if ((PGroup = this.groupsMap.get(playerName)) != null && (group = PGroup.getGroup(world)) != null) {
            return group;
        }
        return Residence.getConfigManager().getDefaultGroup().toLowerCase();
    }

    public String getPermissionsGroup(Player player) {
        return this.getPermissionsGroup(player.getName(), player.getWorld().getName()).toLowerCase();
    }

    public String getPermissionsGroup(String player, String world) {
        if (perms == null) {
            return Residence.getConfigManager().getDefaultGroup().toLowerCase();
        }
        try {
            return perms.getPlayerGroup(player, world).toLowerCase();
        }
        catch (Exception e) {
            return Residence.getConfigManager().getDefaultGroup().toLowerCase();
        }
    }

    public void updateGroupNameForPlayer(Player player) {
        this.updateGroupNameForPlayer(player, false);
    }

    public void updateGroupNameForPlayer(Player player, boolean force) {
        if (player == null) {
            return;
        }
        this.updateGroupNameForPlayer(player.getName(), player.getWorld().getName(), force);
    }

    public void updateGroupNameForPlayer(String playerName, String world, boolean force) {
        PlayerGroup GPlayer;
        if (!this.groupsMap.containsKey(playerName)) {
            GPlayer = new PlayerGroup(playerName);
            this.groupsMap.put(playerName, GPlayer);
        } else {
            GPlayer = this.groupsMap.get(playerName);
        }
        GPlayer.updateGroup(world, force);
    }

    public boolean isResidenceAdmin(CommandSender sender) {
        if (!(sender.hasPermission("residence.admin") || sender.isOp() && Residence.getConfigManager().getOpsAreAdmins())) {
            return false;
        }
        return true;
    }

    private static void checkPermissions() {
        Server server2 = Residence.getServ();
        Plugin p = server2.getPluginManager().getPlugin("Vault");
        if (p != null) {
            ResidenceVaultAdapter vault = new ResidenceVaultAdapter(server2);
            if (vault.permissionsOK()) {
                perms = vault;
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Found Vault using permissions plugin:" + vault.getPermissionsName());
                return;
            }
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Found Vault, but Vault reported no usable permissions system...");
        }
        if ((p = server2.getPluginManager().getPlugin("PermissionsBukkit")) != null) {
            perms = new PermissionsBukkitAdapter((PermissionsPlugin)p);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Found PermissionsBukkit Plugin!");
            return;
        }
        p = server2.getPluginManager().getPlugin("bPermissions");
        if (p != null) {
            perms = new BPermissionsAdapter();
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Found bPermissions Plugin!");
            return;
        }
        p = server2.getPluginManager().getPlugin("Permissions");
        if (p != null) {
            if (Residence.getConfigManager().useLegacyPermissions()) {
                perms = new LegacyPermissions(((Permissions)p).getHandler());
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Found Permissions Plugin!");
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + "Permissions running in Legacy mode!");
            } else {
                perms = new OriginalPermissions(((Permissions)p).getHandler());
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Found Permissions Plugin!");
            }
            return;
        }
        Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Permissions plugin NOT FOUND!");
    }

    private void readConfig() {
        Set keys;
        YamlConfiguration groupsFile = YamlConfiguration.loadConfiguration((File)new File(Residence.dataFolder, "groups.yml"));
        YamlConfiguration flags2 = YamlConfiguration.loadConfiguration((File)new File(Residence.dataFolder, "flags.yml"));
        String defaultGroup = Residence.getConfigManager().getDefaultGroup().toLowerCase();
        this.globalFlagPerms = FlagPermissions.parseFromConfigNode("FlagPermission", flags2.getConfigurationSection("Global"));
        ConfigurationSection nodes = groupsFile.getConfigurationSection("Groups");
        if (nodes != null) {
            Set entrys = nodes.getKeys(false);
            int i = 0;
            for (Object key : entrys) {
                try {
                    this.groups.put(key.toLowerCase(), new PermissionGroup(key.toLowerCase(), nodes.getConfigurationSection((String)key), this.globalFlagPerms, ++i));
                    List mirrors = nodes.getConfigurationSection((String)key).getStringList("Mirror");
                    for (String group : mirrors) {
                        this.groups.put(group.toLowerCase(), new PermissionGroup(key.toLowerCase(), nodes.getConfigurationSection((String)key), this.globalFlagPerms));
                    }
                    continue;
                }
                catch (Exception ex) {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Error parsing group from config:" + (String)key + " Exception:" + ex);
                }
            }
        }
        if (!this.groups.containsKey(defaultGroup)) {
            this.groups.put(defaultGroup, new PermissionGroup(defaultGroup));
        }
        if (groupsFile.isConfigurationSection("GroupAssignments") && (keys = groupsFile.getConfigurationSection("GroupAssignments").getKeys(false)) != null) {
            for (String key : keys) {
                this.playersGroup.put(key.toLowerCase(), groupsFile.getString("GroupAssignments." + key, defaultGroup).toLowerCase());
            }
        }
    }

    public boolean hasGroup(String group) {
        group = group.toLowerCase();
        return this.groups.containsKey(group);
    }

    public PermissionsInterface getPermissionsPlugin() {
        return perms;
    }
}

