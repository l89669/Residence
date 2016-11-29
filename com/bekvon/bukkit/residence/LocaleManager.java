/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.Validate
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 */
package com.bekvon.bukkit.residence;

import com.bekvon.bukkit.residence.CommandFiller;
import com.bekvon.bukkit.residence.CommentedYamlConfiguration;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandStatus;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class LocaleManager {
    public static ArrayList<String> FlagList = new ArrayList();
    public HashMap<List<String>, List<String>> CommandTab = new HashMap();
    private Residence plugin;
    public String path = "CommandHelp.SubCommands.res.SubCommands.";

    public LocaleManager(Residence plugin) {
        this.plugin = plugin;
    }

    private static YamlConfiguration loadConfiguration(BufferedReader in, String language) {
        Validate.notNull((Object)in, (String)"File cannot be null");
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load((Reader)in);
        }
        catch (FileNotFoundException fileNotFoundException) {
        }
        catch (IOException iOException) {
        }
        catch (InvalidConfigurationException ex) {
            Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "[Residence] Your locale file for " + language + " is incorect! Use http://yaml-online-parser.appspot.com/ to find issue.");
            return null;
        }
        return config;
    }

    public void LoadLang(String lang) {
        File f = new File(this.plugin.getDataFolder(), "Language" + File.separator + lang + ".yml");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(f), StandardCharsets.UTF_8));
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        if (in == null) {
            return;
        }
        YamlConfiguration conf = LocaleManager.loadConfiguration(in, lang);
        if (conf == null) {
            try {
                in.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        CommentedYamlConfiguration writer = new CommentedYamlConfiguration();
        ConfigReader c = new ConfigReader(conf, writer);
        c.getC().options().copyDefaults(true);
        StringBuilder header = new StringBuilder();
        header.append(System.getProperty("line.separator"));
        header.append("NOTE If you want to modify this file, it is HIGHLY recommended that you make a copy");
        header.append(System.getProperty("line.separator"));
        header.append("of this file and modify that instead. This file will be updated automatically by Residence");
        header.append(System.getProperty("line.separator"));
        header.append("when a newer version is detected, and your changes will be overwritten.  Once you ");
        header.append(System.getProperty("line.separator"));
        header.append("have a copy of this file, change the Language: option under the Residence config.yml");
        header.append(System.getProperty("line.separator"));
        header.append("to whatever you named your copy.");
        header.append(System.getProperty("line.separator"));
        c.getW().options().header(header.toString());
        lm[] arrlm = lm.values();
        int n = arrlm.length;
        int n2 = 0;
        while (n2 < n) {
            lm lm2 = arrlm[n2];
            if (lm2.getText() instanceof String) {
                c.get(lm2.getPath(), String.valueOf(lm2.getText()));
            } else if (lm2.getText() instanceof ArrayList) {
                ArrayList<String> result = new ArrayList<String>();
                for (Object obj : (ArrayList)lm2.getText()) {
                    if (!(obj instanceof String)) continue;
                    result.add((String)obj);
                }
                c.get(lm2.getPath(), result);
            }
            if (lm2.getComments() != null) {
                writer.addComment(lm2.getPath(), lm2.getComments());
            }
            ++n2;
        }
        writer.addComment("CommandHelp", "");
        c.get("CommandHelp.Description", "Contains Help for Residence");
        c.get("CommandHelp.SubCommands.res.Description", "Main Residence Command");
        c.get("CommandHelp.SubCommands.res.Info", Arrays.asList("&2Use &6/res [command] ? <page> &2to view more help Information."));
        for (Map.Entry<String, CommandStatus> cmo : Residence.getCommandFiller().CommandList.entrySet()) {
            String path = String.valueOf(Residence.getLocaleManager().path) + cmo.getKey() + ".";
            try {
                Residence.getCommandFiller().getClass();
                Class cl = Class.forName(String.valueOf("com.bekvon.bukkit.residence.commands") + "." + cmo.getKey());
                if (!cmd.class.isAssignableFrom(cl)) continue;
                cmd cm = (cmd)cl.getConstructor(new Class[0]).newInstance(new Object[0]);
                cm.getLocale(c, path);
                continue;
            }
            catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException cl) {
                // empty catch block
            }
        }
        c.get("CommandHelp.SubCommands.res.SubCommands.resreload.Description", "Reload residence.");
        c.get("CommandHelp.SubCommands.res.SubCommands.resreload.Info", Arrays.asList("&eUsage: &6/resreload"));
        c.get("CommandHelp.SubCommands.res.SubCommands.resload.Description", "Load residence save file.");
        c.get("CommandHelp.SubCommands.res.SubCommands.resload.Info", Arrays.asList("&eUsage: &6/resload", "UNSAFE command, does not save residences first.", "Loads the residence save file after you have made changes."));
        c.get("CommandHelp.SubCommands.res.SubCommands.removeworld.Description", "Remove all residences from world");
        c.get("CommandHelp.SubCommands.res.SubCommands.removeworld.Info", Arrays.asList("&eUsage: &6/res removeworld [worldname]", "Can only be used from console"));
        try {
            c.getW().save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

