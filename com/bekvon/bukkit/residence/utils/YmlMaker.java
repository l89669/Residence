/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.Validate
 *  org.bukkit.configuration.Configuration
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.Residence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class YmlMaker {
    Residence Plugin;
    public String fileName;
    private JavaPlugin plugin;
    public File ConfigFile;
    private FileConfiguration Configuration;

    public YmlMaker(Residence Plugin2) {
        this.Plugin = Plugin2;
    }

    public YmlMaker(JavaPlugin plugin, String fileName) {
        if (plugin == null) {
            throw new IllegalArgumentException("plugin cannot be null");
        }
        this.plugin = plugin;
        this.fileName = fileName;
        File dataFolder = plugin.getDataFolder();
        if (dataFolder == null) {
            throw new IllegalStateException();
        }
        this.ConfigFile = new File(String.valueOf(dataFolder.toString()) + File.separatorChar + this.fileName);
    }

    private static YamlConfiguration loadConfiguration(InputStreamReader inputStreamReader) {
        Validate.notNull((Object)inputStreamReader, (String)"File cannot be null");
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load((Reader)inputStreamReader);
        }
        catch (FileNotFoundException fileNotFoundException) {
        }
        catch (IOException iOException) {
        }
        catch (InvalidConfigurationException ex) {
            return null;
        }
        return config;
    }

    private static YamlConfiguration loadConfiguration(InputStream defConfigStream) {
        Validate.notNull((Object)defConfigStream, (String)"File cannot be null");
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(defConfigStream);
        }
        catch (FileNotFoundException fileNotFoundException) {
        }
        catch (IOException iOException) {
        }
        catch (InvalidConfigurationException ex) {
            return null;
        }
        return config;
    }

    public void reloadConfig() {
        YamlConfiguration defConfig;
        InputStreamReader f = null;
        try {
            f = new InputStreamReader((InputStream)new FileInputStream(this.ConfigFile), "UTF-8");
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        if (f != null) {
            this.Configuration = YmlMaker.loadConfiguration(f);
        }
        if (this.Configuration == null) {
            if (f != null) {
                try {
                    f.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        InputStream defConfigStream = this.plugin.getResource(this.fileName);
        if (defConfigStream != null && (defConfig = YmlMaker.loadConfiguration(defConfigStream)) != null) {
            this.Configuration.setDefaults((Configuration)defConfig);
        }
        if (defConfigStream != null) {
            try {
                defConfigStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (f != null) {
            try {
                f.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration getConfig() {
        if (this.Configuration == null) {
            this.reloadConfig();
        }
        return this.Configuration;
    }

    public void saveConfig() {
        if (this.Configuration == null || this.ConfigFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.ConfigFile);
        }
        catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.ConfigFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (!this.ConfigFile.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }
    }
}

