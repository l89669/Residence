/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 *  org.bukkit.inventory.ItemStack
 */
package com.bekvon.bukkit.residence.gui;

import com.bekvon.bukkit.residence.CommentedYamlConfiguration;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.gui.FlagData;
import java.io.File;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.inventory.ItemStack;

public class FlagUtil {
    private FlagData flagData = new FlagData();
    private Residence plugin;

    public FlagUtil(Residence plugin) {
        this.plugin = plugin;
    }

    public void load() {
        File f = new File(this.plugin.getDataFolder(), "flags.yml");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration((File)f);
        CommentedYamlConfiguration writer = new CommentedYamlConfiguration();
        conf.options().copyDefaults(true);
        ConfigReader c = new ConfigReader(conf, writer);
        if (!conf.isConfigurationSection("Global.FlagPermission")) {
            return;
        }
        Set allFlags = conf.getConfigurationSection("Global.FlagPermission").getKeys(false);
        for (String oneFlag : allFlags) {
            if (!c.getC().contains("Global.FlagGui." + oneFlag) || !c.getC().contains("Global.FlagGui." + oneFlag + ".Id") || !c.getC().contains("Global.FlagGui." + oneFlag + ".Data")) continue;
            int id = c.get("Global.FlagGui." + oneFlag + ".Id", 35);
            int data = c.get("Global.FlagGui." + oneFlag + ".Data", 0);
            Material Mat = Material.getMaterial((int)id);
            if (Mat == null) {
                Mat = Material.STONE;
            }
            ItemStack item = new ItemStack(Mat, 1, (short)data);
            this.flagData.addFlagButton(oneFlag.toLowerCase(), item);
        }
    }

    public FlagData getFlagData() {
        return this.flagData;
    }
}

