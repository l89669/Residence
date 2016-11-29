/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.bekvon.bukkit.residence.text;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.utils.YmlMaker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Language {
    public FileConfiguration enlocale;
    public FileConfiguration customlocale;
    private Residence plugin;

    public Language(Residence plugin) {
        this.plugin = plugin;
    }

    public void LanguageReload() {
        this.customlocale = new YmlMaker(this.plugin, "Language/" + Residence.getConfigManager().getLanguage() + ".yml").getConfig();
        this.enlocale = new YmlMaker(this.plugin, "Language/English.yml").getConfig();
        if (this.customlocale == null) {
            this.customlocale = this.enlocale;
        }
    }

    public String getMessage(String key) {
        if (!key.contains("Language.") && !key.contains("CommandHelp.")) {
            key = "Language." + key;
        }
        String missing = "Missing locale for " + key;
        String message2 = "";
        if (this.customlocale == null || !this.customlocale.contains(key)) {
            message2 = this.enlocale.contains(key) ? this.enlocale.getString(key) : missing;
        }
        message2 = this.customlocale.contains(key) ? this.customlocale.getString(key) : missing;
        return ChatColor.translateAlternateColorCodes((char)'&', (String)message2);
    }

    public /* varargs */ String getMessage(lm lm2, Object ... variables) {
        String key = lm2.getPath();
        if (!key.contains("Language.") && !key.contains("CommandHelp.")) {
            key = "Language." + key;
        }
        String missing = "Missing locale for " + key;
        String message2 = "";
        if (this.customlocale == null || !this.customlocale.contains(key)) {
            message2 = this.enlocale.contains(key) ? this.enlocale.getString(key) : missing;
        }
        message2 = this.customlocale.contains(key) ? this.customlocale.getString(key) : missing;
        int i = 1;
        while (i <= variables.length) {
            String vr = String.valueOf(variables[i - 1]);
            if (variables[i - 1] instanceof Flags) {
                vr = ((Flags)((Object)variables[i - 1])).getName();
            }
            message2 = message2.replace("%" + i, vr);
            ++i;
        }
        return ChatColor.translateAlternateColorCodes((char)'&', (String)message2);
    }

    public String getDefaultMessage(String key) {
        if (!key.contains("Language.") && !key.contains("CommandHelp.")) {
            key = "Language." + key;
        }
        String missing = "Missing locale for " + key;
        return this.enlocale.contains(key) ? ChatColor.translateAlternateColorCodes((char)'&', (String)this.enlocale.getString(key)) : missing;
    }

    public List<String> getMessageList2(String key) {
        if (!key.contains("Language.") && !key.contains("CommandHelp.")) {
            key = "Language." + key;
        }
        String missing = "Missing locale for " + key;
        if (this.customlocale.isList(key)) {
            return Language.ColorsArray(this.customlocale.getStringList(key));
        }
        return this.enlocale.getStringList(key).size() > 0 ? Language.ColorsArray(this.enlocale.getStringList(key)) : Arrays.asList(missing);
    }

    public List<String> getMessageList(lm lm2) {
        String key = lm2.getPath();
        if (!key.contains("Language.") && !key.contains("CommandHelp.")) {
            key = "Language." + key;
        }
        String missing = "Missing locale for " + key;
        if (this.customlocale.isList(key)) {
            return Language.ColorsArray(this.customlocale.getStringList(key));
        }
        return this.enlocale.getStringList(key).size() > 0 ? Language.ColorsArray(this.enlocale.getStringList(key)) : Arrays.asList(missing);
    }

    public Set<String> getKeyList(String key) {
        if (this.customlocale.isConfigurationSection(key)) {
            return this.customlocale.getConfigurationSection(key).getKeys(false);
        }
        return this.enlocale.getConfigurationSection(key).getKeys(false);
    }

    public boolean containsKey(String key) {
        if (!key.contains("Language.") && !key.contains("CommandHelp.")) {
            key = "Language." + key;
        }
        if (this.customlocale == null || !this.customlocale.contains(key)) {
            return this.enlocale.contains(key);
        }
        return this.customlocale.contains(key);
    }

    private static List<String> ColorsArray(List<String> text) {
        ArrayList<String> temp = new ArrayList<String>();
        for (String part : text) {
            temp.add(Language.Colors(part));
        }
        return temp;
    }

    private static String Colors(String text) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)text);
    }
}

