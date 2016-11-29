/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.itemlist.WorldItemManager;
import com.bekvon.bukkit.residence.permissions.PermissionManager;
import com.bekvon.bukkit.residence.protection.WorldFlagManager;
import com.bekvon.bukkit.residence.text.help.HelpEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class reload
implements cmd {
    @CommandAnnotation(simple=0, priority=5800)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!resadmin2 && !sender.isOp()) {
            Residence.msg(sender, lm.General_NoPermission, new Object[0]);
            return true;
        }
        if (args.length != 2) {
            return false;
        }
        if (args[1].equalsIgnoreCase("lang")) {
            Residence.getLM().LanguageReload();
            File langFile = new File(new File(Residence.dataFolder, "Language"), String.valueOf(Residence.getConfigManager().getLanguage()) + ".yml");
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(langFile), "UTF8"));
            }
            catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            if (langFile.isFile()) {
                YamlConfiguration langconfig = new YamlConfiguration();
                try {
                    langconfig.load((Reader)in);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                Residence.helppages = HelpEntry.parseHelp((FileConfiguration)langconfig, "CommandHelp");
            } else {
                System.out.println(String.valueOf(Residence.prefix) + " Language file does not exist...");
            }
            sender.sendMessage(String.valueOf(Residence.prefix) + " Reloaded language file.");
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        if (args[1].equalsIgnoreCase("config")) {
            Residence.getConfigManager().UpdateConfigFile();
            sender.sendMessage(String.valueOf(Residence.prefix) + " Reloaded config file.");
            return true;
        }
        if (args[1].equalsIgnoreCase("groups")) {
            Residence.getConfigManager().loadGroups();
            Residence.gmanager = new PermissionManager();
            Residence.wmanager = new WorldFlagManager();
            sender.sendMessage(String.valueOf(Residence.prefix) + " Reloaded groups file.");
            return true;
        }
        if (args[1].equalsIgnoreCase("flags")) {
            Residence.getConfigManager().loadFlags();
            Residence.gmanager = new PermissionManager();
            Residence.imanager = new WorldItemManager();
            Residence.wmanager = new WorldFlagManager();
            sender.sendMessage(String.valueOf(Residence.prefix) + " Reloaded flags file.");
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "reload lanf or config files");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res reload [config/lang/groups/flags]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("config%%lang%%groups%%flags"));
    }
}

