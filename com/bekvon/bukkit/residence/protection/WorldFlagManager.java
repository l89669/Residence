/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.World
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class WorldFlagManager {
    protected Map<String, Map<String, FlagPermissions>> groupperms = new HashMap<String, Map<String, FlagPermissions>>();
    protected Map<String, FlagPermissions> worldperms = new HashMap<String, FlagPermissions>();
    protected FlagPermissions globaldefaults = new FlagPermissions();

    public WorldFlagManager() {
        this.parsePerms();
    }

    public FlagPermissions getPerms(Player player) {
        return this.getPerms(player.getWorld().getName(), Residence.getPermissionManager().getGroupNameByPlayer(player));
    }

    public FlagPermissions getPerms(String world, String group) {
        world = world.toLowerCase();
        Map<String, FlagPermissions> groupworldperms = this.groupperms.get(group = group.toLowerCase());
        if (groupworldperms == null) {
            return this.getPerms(world);
        }
        FlagPermissions list2 = groupworldperms.get(world);
        if (list2 == null) {
            list2 = groupworldperms.get("global." + world);
            if (list2 == null) {
                list2 = groupworldperms.get("global");
            }
            if (list2 == null) {
                return this.getPerms(world);
            }
        }
        return list2;
    }

    public FlagPermissions getPerms(String world) {
        FlagPermissions list2 = this.worldperms.get(world = world.toLowerCase());
        if (list2 == null) {
            if (this.globaldefaults == null) {
                return new FlagPermissions();
            }
            return this.globaldefaults;
        }
        return list2;
    }

    public final void parsePerms() {
        block13 : {
            try {
                YamlConfiguration flags2 = YamlConfiguration.loadConfiguration((File)new File(Residence.dataFolder, "flags.yml"));
                YamlConfiguration groups = YamlConfiguration.loadConfiguration((File)new File(Residence.dataFolder, "groups.yml"));
                Set keys = flags2.getConfigurationSection("Global.Flags").getKeys(false);
                if (keys != null) {
                    for (String key : keys) {
                        if (key.equalsIgnoreCase("Global")) {
                            this.globaldefaults = FlagPermissions.parseFromConfigNode(key, flags2.getConfigurationSection("Global.Flags"));
                            continue;
                        }
                        this.worldperms.put(key.toLowerCase(), FlagPermissions.parseFromConfigNode(key, flags2.getConfigurationSection("Global.Flags")));
                    }
                }
                for (Map.Entry entry : this.worldperms.entrySet()) {
                    ((FlagPermissions)entry.getValue()).setParent(this.globaldefaults);
                }
                if (!groups.isConfigurationSection("Groups")) {
                    Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "Your groups.yml file is incorrect!");
                    return;
                }
                keys = groups.getConfigurationSection("Groups").getKeys(false);
                if (keys == null) break block13;
                for (String key : keys) {
                    Set worldkeys;
                    if (!groups.contains("Groups." + key + ".Flags") || !groups.contains("Groups." + key + ".Flags.World") || key == null || (worldkeys = groups.getConfigurationSection("Groups." + key + ".Flags.World").getKeys(false)) == null) continue;
                    HashMap<String, FlagPermissions> perms = new HashMap<String, FlagPermissions>();
                    for (String wkey : worldkeys) {
                        FlagPermissions list2 = FlagPermissions.parseFromConfigNode(wkey, groups.getConfigurationSection("Groups." + key + ".Flags.World"));
                        if (wkey.equalsIgnoreCase("global")) {
                            list2.setParent(this.globaldefaults);
                            perms.put(wkey.toLowerCase(), list2);
                            for (Map.Entry<String, FlagPermissions> worldperm : this.worldperms.entrySet()) {
                                list2 = FlagPermissions.parseFromConfigNode(wkey, groups.getConfigurationSection("Groups." + key + ".Flags.World"));
                                list2.setParent(worldperm.getValue());
                                perms.put("global." + worldperm.getKey().toLowerCase(), list2);
                            }
                            continue;
                        }
                        perms.put(wkey.toLowerCase(), list2);
                    }
                    for (Map.Entry entry : perms.entrySet()) {
                        String wkey = (String)entry.getKey();
                        FlagPermissions list3 = (FlagPermissions)entry.getValue();
                        if (wkey.startsWith("global.")) continue;
                        list3.setParent((FlagPermissions)perms.get("global." + wkey));
                        if (list3.getParent() == null) {
                            list3.setParent(this.worldperms.get(wkey));
                        }
                        if (list3.getParent() != null) continue;
                        list3.setParent(this.globaldefaults);
                    }
                    this.groupperms.put(key.toLowerCase(), perms);
                }
            }
            catch (Exception ex) {
                Logger.getLogger(WorldFlagManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

