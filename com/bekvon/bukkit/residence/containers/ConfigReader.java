/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package com.bekvon.bukkit.residence.containers;

import com.bekvon.bukkit.residence.CommentedYamlConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigReader {
    YamlConfiguration config;
    CommentedYamlConfiguration writer;

    public ConfigReader(YamlConfiguration config, CommentedYamlConfiguration writer) {
        this.config = config;
        this.writer = writer;
    }

    public CommentedYamlConfiguration getW() {
        return this.writer;
    }

    public YamlConfiguration getC() {
        return this.config;
    }

    public Boolean get(String path, Boolean boo) {
        this.config.addDefault(path, (Object)boo);
        this.copySetting(path);
        return this.config.getBoolean(path);
    }

    public int get(String path, int boo) {
        this.config.addDefault(path, (Object)boo);
        this.copySetting(path);
        return this.config.getInt(path);
    }

    public List<Integer> getIntList(String path, List<Integer> list2) {
        this.config.addDefault(path, list2);
        this.copySetting(path);
        return this.config.getIntegerList(path);
    }

    public List<String> get(String path, List<String> list2, boolean colorize) {
        this.config.addDefault(path, list2);
        this.copySetting(path);
        if (colorize) {
            return ConfigReader.ColorsArray(this.config.getStringList(path));
        }
        return this.config.getStringList(path);
    }

    public List<String> get(String path, List<String> list2) {
        this.config.addDefault(path, list2);
        this.copySetting(path);
        return this.config.getStringList(path);
    }

    public String get(String path, String boo) {
        this.config.addDefault(path, (Object)boo);
        this.copySetting(path);
        return this.get(path, boo, true);
    }

    public String get(String path, String boo, boolean colorize) {
        this.config.addDefault(path, (Object)boo);
        this.copySetting(path);
        if (colorize) {
            return ChatColor.translateAlternateColorCodes((char)'&', (String)this.config.getString(path));
        }
        return this.config.getString(path);
    }

    public Double get(String path, Double boo) {
        this.config.addDefault(path, (Object)boo);
        this.copySetting(path);
        return this.config.getDouble(path);
    }

    public synchronized void copySetting(String path) {
        this.writer.set(path, this.config.get(path));
    }

    private static List<String> ColorsArray(List<String> text) {
        ArrayList<String> temp = new ArrayList<String>();
        for (String part : text) {
            temp.add(ConfigReader.Colors(part));
        }
        return temp;
    }

    private static String Colors(String text) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)text);
    }
}

