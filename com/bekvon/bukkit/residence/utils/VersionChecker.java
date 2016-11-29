/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.scheduler.BukkitTask
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.Residence;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitTask;

public class VersionChecker {
    Residence plugin;
    private int resource = 11480;
    private int cleanVersion = 0;

    public VersionChecker(Residence plugin) {
        this.plugin = plugin;
    }

    public int GetVersion() {
        if (this.cleanVersion == 0) {
            block13 : {
                String[] v = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
                String version2 = v[v.length - 1];
                try {
                    this.cleanVersion = Integer.parseInt(version2.replace("v", "").replace("V", "").replace("_", "").replace("r", "").replace("R", ""));
                    this.cleanVersion *= 10;
                }
                catch (NumberFormatException e) {
                    if (version2.contains("v1_4")) {
                        this.cleanVersion = 1400;
                    }
                    if (version2.contains("v1_5")) {
                        this.cleanVersion = 1500;
                    }
                    if (version2.contains("v1_6")) {
                        this.cleanVersion = 1600;
                    }
                    if (version2.contains("v1_7")) {
                        this.cleanVersion = 1700;
                    }
                    if (version2.contains("v1_8_R1")) {
                        this.cleanVersion = 1810;
                    }
                    if (version2.contains("v1_8_R2")) {
                        this.cleanVersion = 1820;
                    }
                    if (version2.contains("v1_8_R3")) {
                        this.cleanVersion = 1830;
                    }
                    if (version2.contains("v1_9_R1")) {
                        this.cleanVersion = 1910;
                    }
                    if (version2.contains("v1_9_R2")) {
                        this.cleanVersion = 1920;
                    }
                    if (!version2.contains("v1_10_R1")) break block13;
                    this.cleanVersion = 11010;
                }
            }
            if (this.cleanVersion < 1400) {
                this.cleanVersion *= 10;
            }
        }
        return this.cleanVersion;
    }

    public void VersionCheck(final Player player) {
        if (!Residence.getConfigManager().versionCheck()) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                String currentVersion = VersionChecker.this.plugin.getDescription().getVersion();
                String newVersion = VersionChecker.this.getNewVersion();
                if (newVersion == null || newVersion.equalsIgnoreCase(currentVersion)) {
                    return;
                }
                List<String> msg = Arrays.asList((Object)ChatColor.GREEN + "*********************** " + VersionChecker.this.plugin.getDescription().getName() + " **************************", (Object)ChatColor.GREEN + "* " + newVersion + " is now available! Your version: " + currentVersion, (Object)ChatColor.GREEN + "* " + (Object)ChatColor.DARK_GREEN + VersionChecker.this.plugin.getDescription().getWebsite(), (Object)ChatColor.GREEN + "************************************************************");
                for (String one : msg) {
                    if (player != null) {
                        player.sendMessage(one);
                        continue;
                    }
                    VersionChecker.this.plugin.consoleMessage(one);
                }
            }
        });
    }

    public String getNewVersion() {
        try {
            HttpURLConnection con = (HttpURLConnection)new URL("http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + this.resource).getBytes("UTF-8"));
            String version2 = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (version2.length() <= 9) {
                return version2;
            }
        }
        catch (Exception ex) {
            this.plugin.consoleMessage((Object)ChatColor.RED + "Failed to check for " + this.plugin.getDescription().getName() + " update on spigot web page.");
        }
        return null;
    }

}

