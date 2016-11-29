/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.griefcraft.lwc.LWC
 *  com.griefcraft.lwc.LWCPlugin
 *  com.iCo6.iConomy
 *  com.sk89q.worldedit.bukkit.WorldEditPlugin
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  cosine.boseconomy.BOSEconomy
 *  fr.crafter.tickleman.realeconomy.RealEconomy
 *  fr.crafter.tickleman.realplugin.RealPlugin
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.command.TabCompleter
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 *  org.dynmap.DynmapAPI
 *  org.dynmap.markers.MarkerSet
 */
package com.bekvon.bukkit.residence;

import com.bekvon.bukkit.residence.CommandFiller;
import com.bekvon.bukkit.residence.ConfigManager;
import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.allNms.v1_10Events;
import com.bekvon.bukkit.residence.allNms.v1_8Events;
import com.bekvon.bukkit.residence.allNms.v1_9Events;
import com.bekvon.bukkit.residence.api.ChatInterface;
import com.bekvon.bukkit.residence.api.MarketBuyInterface;
import com.bekvon.bukkit.residence.api.MarketRentInterface;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.api.ResidenceInterface;
import com.bekvon.bukkit.residence.api.ResidencePlayerInterface;
import com.bekvon.bukkit.residence.chat.ChatManager;
import com.bekvon.bukkit.residence.containers.ABInterface;
import com.bekvon.bukkit.residence.containers.CommandStatus;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.NMS;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.dynmap.DynMapListeners;
import com.bekvon.bukkit.residence.dynmap.DynMapManager;
import com.bekvon.bukkit.residence.economy.BOSEAdapter;
import com.bekvon.bukkit.residence.economy.EconomyInterface;
import com.bekvon.bukkit.residence.economy.EssentialsEcoAdapter;
import com.bekvon.bukkit.residence.economy.IConomy5Adapter;
import com.bekvon.bukkit.residence.economy.IConomy6Adapter;
import com.bekvon.bukkit.residence.economy.RealShopEconomy;
import com.bekvon.bukkit.residence.economy.TransactionManager;
import com.bekvon.bukkit.residence.economy.rent.RentManager;
import com.bekvon.bukkit.residence.gui.FlagUtil;
import com.bekvon.bukkit.residence.itemlist.WorldItemManager;
import com.bekvon.bukkit.residence.listeners.ResidenceBlockListener;
import com.bekvon.bukkit.residence.listeners.ResidenceEntityListener;
import com.bekvon.bukkit.residence.listeners.ResidenceFixesListener;
import com.bekvon.bukkit.residence.listeners.ResidencePlayerListener;
import com.bekvon.bukkit.residence.listeners.SpigotListener;
import com.bekvon.bukkit.residence.permissions.PermissionManager;
import com.bekvon.bukkit.residence.permissions.PermissionsInterface;
import com.bekvon.bukkit.residence.persistance.YMLSaveHelper;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.LeaseManager;
import com.bekvon.bukkit.residence.protection.PermissionListManager;
import com.bekvon.bukkit.residence.protection.PlayerManager;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.protection.WorldFlagManager;
import com.bekvon.bukkit.residence.selection.AutoSelection;
import com.bekvon.bukkit.residence.selection.SchematicsManager;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import com.bekvon.bukkit.residence.selection.WorldEditSelectionManager;
import com.bekvon.bukkit.residence.shopStuff.ShopListener;
import com.bekvon.bukkit.residence.shopStuff.ShopSignUtil;
import com.bekvon.bukkit.residence.signsStuff.SignUtil;
import com.bekvon.bukkit.residence.spout.ResidenceSpout;
import com.bekvon.bukkit.residence.spout.ResidenceSpoutListener;
import com.bekvon.bukkit.residence.text.Language;
import com.bekvon.bukkit.residence.text.help.HelpEntry;
import com.bekvon.bukkit.residence.text.help.InformationPager;
import com.bekvon.bukkit.residence.utils.ActionBar;
import com.bekvon.bukkit.residence.utils.CrackShot;
import com.bekvon.bukkit.residence.utils.FileCleanUp;
import com.bekvon.bukkit.residence.utils.RandomTp;
import com.bekvon.bukkit.residence.utils.Sorting;
import com.bekvon.bukkit.residence.utils.TabComplete;
import com.bekvon.bukkit.residence.utils.VersionChecker;
import com.bekvon.bukkit.residence.utils.YmlMaker;
import com.bekvon.bukkit.residence.vaultinterface.ResidenceVaultAdapter;
import com.earth2me.essentials.Essentials;
import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
import com.iCo6.iConomy;
import com.residence.mcstats.Metrics;
import com.residence.zip.ZipLibrary;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import cosine.boseconomy.BOSEconomy;
import fr.crafter.tickleman.realeconomy.RealEconomy;
import fr.crafter.tickleman.realplugin.RealPlugin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.MarkerSet;

public class Residence
extends JavaPlugin {
    protected static String ResidenceVersion;
    protected static List<String> authlist;
    protected static ResidenceManager rmanager;
    protected static SelectionManager smanager;
    public static PermissionManager gmanager;
    protected static ConfigManager cmanager;
    protected static boolean spigotPlatform;
    protected static SignUtil signmanager;
    protected static ResidenceBlockListener blistener;
    protected static ResidencePlayerListener plistener;
    protected static ResidenceEntityListener elistener;
    protected static ResidenceSpoutListener slistener;
    protected static ResidenceSpout spout;
    protected static ResidenceFixesListener flistener;
    protected static SpigotListener spigotlistener;
    protected static ShopListener shlistener;
    protected static TransactionManager tmanager;
    protected static PermissionListManager pmanager;
    protected static LeaseManager leasemanager;
    public static WorldItemManager imanager;
    public static WorldFlagManager wmanager;
    protected static RentManager rentmanager;
    protected static ChatManager chatmanager;
    protected static Server server;
    public static HelpEntry helppages;
    protected static LocaleManager LocaleManager;
    protected static Language NewLanguageManager;
    protected static PlayerManager PlayerManager;
    protected static FlagUtil FlagUtilManager;
    protected static ShopSignUtil ShopSignUtilManager;
    protected static RandomTp RandomTpManager;
    protected static DynMapManager DynManager;
    protected static Sorting SortingManager;
    protected static ActionBar ABManager;
    protected static AutoSelection AutoSelectionManager;
    protected static SchematicsManager SchematicManager;
    private static InformationPager InformationPagerManager;
    protected static CommandFiller cmdFiller;
    protected static ZipLibrary zip;
    protected boolean firstenable = true;
    protected static EconomyInterface economy;
    private static int saveVersion;
    public static File dataFolder;
    protected static int leaseBukkitId;
    protected static int rentBukkitId;
    protected static int healBukkitId;
    protected static int feedBukkitId;
    protected static int DespawnMobsBukkitId;
    protected static int autosaveBukkitId;
    protected static VersionChecker versionChecker;
    protected static boolean initsuccess;
    public static Map<String, String> deleteConfirm;
    public static Map<String, String> UnrentConfirm;
    public static List<String> resadminToggle;
    private static final String[] validLanguages;
    public static ConcurrentHashMap<String, OfflinePlayer> OfflinePlayerList;
    private static Map<UUID, String> cachedPlayerNameUUIDs;
    public static WorldEditPlugin wep;
    public static WorldGuardPlugin wg;
    public static int wepid;
    private static String ServerLandname;
    private static String ServerLandUUID;
    private static String TempUserUUID;
    private static ABInterface ab;
    private static NMS nms;
    static LWC lwc;
    public static HashMap<String, Long> rtMap;
    public static List<String> teleportDelayMap;
    public static HashMap<String, ClaimedResidence> teleportMap;
    public static String prefix;
    private static ResidenceApi API;
    private static MarketBuyInterface MarketBuyAPI;
    private static MarketRentInterface MarketRentAPI;
    private static ResidencePlayerInterface PlayerAPI;
    private static ResidenceInterface ResidenceAPI;
    private static ChatInterface ChatAPI;
    private Runnable doHeals;
    private Runnable doFeed;
    private Runnable DespawnMobs;
    private Runnable rentExpire;
    private Runnable leaseExpire;
    private Runnable autoSave;

    static {
        spigotPlatform = false;
        saveVersion = 1;
        leaseBukkitId = -1;
        rentBukkitId = -1;
        healBukkitId = -1;
        feedBukkitId = -1;
        DespawnMobsBukkitId = -1;
        autosaveBukkitId = -1;
        initsuccess = false;
        UnrentConfirm = new HashMap<String, String>();
        validLanguages = new String[]{"English", "Czech", "Chinese", "ChineseTW"};
        OfflinePlayerList = new ConcurrentHashMap();
        cachedPlayerNameUUIDs = new HashMap<UUID, String>();
        wep = null;
        wg = null;
        ServerLandname = "Server_Land";
        ServerLandUUID = "00000000-0000-0000-0000-000000000000";
        TempUserUUID = "ffffffff-ffff-ffff-ffff-ffffffffffff";
        rtMap = new HashMap();
        teleportDelayMap = new ArrayList<String>();
        teleportMap = new HashMap();
        prefix = (Object)ChatColor.GREEN + "[" + (Object)ChatColor.GOLD + "Residence" + (Object)ChatColor.GREEN + "]" + (Object)ChatColor.GRAY;
        API = new ResidenceApi();
        MarketBuyAPI = null;
        MarketRentAPI = null;
        PlayerAPI = null;
        ResidenceAPI = null;
        ChatAPI = null;
    }

    public Residence() {
        this.doHeals = new Runnable(){

            @Override
            public void run() {
                Residence.plistener.doHeals();
            }
        };
        this.doFeed = new Runnable(){

            @Override
            public void run() {
                Residence.plistener.feed();
            }
        };
        this.DespawnMobs = new Runnable(){

            @Override
            public void run() {
                Residence.plistener.DespawnMobs();
            }
        };
        this.rentExpire = new Runnable(){

            @Override
            public void run() {
                Residence.rentmanager.checkCurrentRents();
                if (Residence.cmanager.showIntervalMessages()) {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " - Rent Expirations checked!");
                }
            }
        };
        this.leaseExpire = new Runnable(){

            @Override
            public void run() {
                Residence.leasemanager.doExpirations();
                if (Residence.cmanager.showIntervalMessages()) {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " - Lease Expirations checked!");
                }
            }
        };
        this.autoSave = new Runnable(){

            @Override
            public void run() {
                try {
                    if (Residence.initsuccess) {
                        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Residence.this, new Runnable(){

                            @Override
                            public void run() {
                                try {
                                    Residence.saveYml();
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                catch (Exception ex) {
                    Logger.getLogger("Minecraft").log(Level.SEVERE, String.valueOf(Residence.prefix) + " SEVERE SAVE ERROR", ex);
                }
            }

        };
    }

    public static boolean isSpigot() {
        return spigotPlatform;
    }

    public static HashMap<String, ClaimedResidence> getTeleportMap() {
        return teleportMap;
    }

    public static List<String> getTeleportDelayMap() {
        return teleportDelayMap;
    }

    public static HashMap<String, Long> getRandomTeleportMap() {
        return rtMap;
    }

    public static ResidencePlayerInterface getPlayerManagerAPI() {
        if (PlayerAPI == null) {
            PlayerAPI = PlayerManager;
        }
        return PlayerAPI;
    }

    public static ResidenceInterface getResidenceManagerAPI() {
        if (ResidenceAPI == null) {
            ResidenceAPI = rmanager;
        }
        return ResidenceAPI;
    }

    public static MarketRentInterface getMarketRentManagerAPI() {
        if (MarketRentAPI == null) {
            MarketRentAPI = rentmanager;
        }
        return MarketRentAPI;
    }

    public static MarketBuyInterface getMarketBuyManagerAPI() {
        if (MarketBuyAPI == null) {
            MarketBuyAPI = tmanager;
        }
        return MarketBuyAPI;
    }

    public static ChatInterface getResidenceChatAPI() {
        if (ChatAPI == null) {
            ChatAPI = chatmanager;
        }
        return ChatAPI;
    }

    public static ResidenceApi getAPI() {
        return API;
    }

    public static NMS getNms() {
        return nms;
    }

    public static ABInterface getAB() {
        return ab;
    }

    public void reloadPlugin() {
        this.onDisable();
        this.reloadConfig();
        this.onEnable();
    }

    public void onDisable() {
        server.getScheduler().cancelTask(autosaveBukkitId);
        server.getScheduler().cancelTask(healBukkitId);
        server.getScheduler().cancelTask(feedBukkitId);
        server.getScheduler().cancelTask(DespawnMobsBukkitId);
        if (cmanager.useLeases()) {
            server.getScheduler().cancelTask(leaseBukkitId);
        }
        if (cmanager.enabledRentSystem()) {
            server.getScheduler().cancelTask(rentBukkitId);
        }
        if (Residence.getDynManager() != null && Residence.getDynManager().getMarkerSet() != null) {
            Residence.getDynManager().getMarkerSet().deleteMarkerSet();
        }
        if (initsuccess) {
            try {
                Residence.saveYml();
                if (zip != null) {
                    zip.backup();
                }
            }
            catch (Exception ex) {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "[Residence] SEVERE SAVE ERROR", ex);
            }
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Disabled!");
        }
    }

    public void onEnable() {
        try {
            Plugin dynmap;
            Object langconfig;
            int autosaveInt;
            block78 : {
                String lang;
                Class nmsClass;
                Plugin plugin;
                initsuccess = false;
                deleteConfirm = new HashMap<String, String>();
                resadminToggle = new ArrayList<String>();
                server = this.getServer();
                dataFolder = this.getDataFolder();
                ResidenceVersion = this.getDescription().getVersion();
                authlist = this.getDescription().getAuthors();
                cmdFiller = new CommandFiller();
                cmdFiller.fillCommands();
                SortingManager = new Sorting();
                if (!dataFolder.isDirectory()) {
                    dataFolder.mkdirs();
                }
                if (!new File(dataFolder, "groups.yml").isFile() && !new File(dataFolder, "flags.yml").isFile() && new File(dataFolder, "config.yml").isFile()) {
                    this.ConvertFile();
                }
                if (!new File(dataFolder, "config.yml").isFile()) {
                    this.writeDefaultConfigFromJar();
                }
                if (!new File(dataFolder, "uuids.yml").isFile()) {
                    File file = new File(this.getDataFolder(), "uuids.yml");
                    file.createNewFile();
                }
                if (!new File(dataFolder, "flags.yml").isFile()) {
                    this.writeDefaultFlagsFromJar();
                }
                if (!new File(dataFolder, "groups.yml").isFile()) {
                    this.writeDefaultGroupsFromJar();
                }
                this.getCommand("res").setTabCompleter((TabCompleter)new TabComplete());
                this.getCommand("resadmin").setTabCompleter((TabCompleter)new TabComplete());
                this.getCommand("residence").setTabCompleter((TabCompleter)new TabComplete());
                cmanager = new ConfigManager(this);
                String multiworld = cmanager.getMultiworldPlugin();
                if (multiworld != null && (plugin = server.getPluginManager().getPlugin(multiworld)) != null && !plugin.isEnabled()) {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " - Enabling multiworld plugin: " + multiworld);
                    server.getPluginManager().enablePlugin(plugin);
                }
                FlagUtilManager = new FlagUtil(this);
                Residence.getFlagUtilManager().load();
                try {
                    Class c = Class.forName("org.bukkit.entity.Player");
                    Method[] arrmethod = c.getDeclaredMethods();
                    int n = arrmethod.length;
                    int n2 = 0;
                    while (n2 < n) {
                        Method one = arrmethod[n2];
                        if (one.getName().equalsIgnoreCase("Spigot")) {
                            spigotPlatform = true;
                        }
                        ++n2;
                    }
                }
                catch (Exception c) {
                    // empty catch block
                }
                String packageName = this.getServer().getClass().getPackage().getName();
                String[] packageSplit = packageName.split("\\.");
                String version2 = packageSplit[packageSplit.length - 1].substring(0, packageSplit[packageSplit.length - 1].length() - 3);
                try {
                    nmsClass = Residence.getConfigManager().CouldronCompatability() ? Class.forName("com.bekvon.bukkit.residence.allNms.v1_7_Couldron") : Class.forName("com.bekvon.bukkit.residence.allNms." + version2);
                    if (NMS.class.isAssignableFrom(nmsClass)) {
                        nms = (NMS)nmsClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                    } else {
                        System.out.println("Something went wrong, please note down version and contact author v:" + version2);
                        this.setEnabled(false);
                        Bukkit.shutdown();
                    }
                }
                catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    System.out.println("Your server version is not compatible with this plugins version! Plugin will be disabled: " + version2 + " and server will shutdown");
                    this.setEnabled(false);
                    Bukkit.shutdown();
                    return;
                }
                ABManager = new ActionBar();
                version2 = packageSplit[packageSplit.length - 1];
                try {
                    nmsClass = Class.forName("com.bekvon.bukkit.residence.actionBarNMS." + version2);
                    if (ABInterface.class.isAssignableFrom(nmsClass)) {
                        ab = (ABInterface)nmsClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                    } else {
                        System.out.println("Something went wrong, please note down version and contact author v:" + version2);
                        this.setEnabled(false);
                        Bukkit.shutdown();
                    }
                }
                catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    ab = ABManager;
                    return;
                }
                gmanager = new PermissionManager();
                imanager = new WorldItemManager();
                wmanager = new WorldFlagManager();
                chatmanager = new ChatManager();
                rentmanager = new RentManager();
                LocaleManager = new LocaleManager(this);
                PlayerManager = new PlayerManager();
                ShopSignUtilManager = new ShopSignUtil(this);
                RandomTpManager = new RandomTp(this);
                InformationPagerManager = new InformationPager(this);
                zip = new ZipLibrary();
                versionChecker = new VersionChecker(this);
                Plugin lwcp = Bukkit.getPluginManager().getPlugin("LWC");
                if (lwcp != null) {
                    lwc = ((LWCPlugin)lwcp).getLWC();
                }
                String[] arrstring = validLanguages;
                int n = arrstring.length;
                int n3 = 0;
                while (n3 < n) {
                    lang = arrstring[n3];
                    YmlMaker langFile = new YmlMaker(this, "Language" + File.separator + lang + ".yml");
                    langFile.saveDefaultConfig();
                    ++n3;
                }
                arrstring = validLanguages;
                n = arrstring.length;
                n3 = 0;
                while (n3 < n) {
                    lang = arrstring[n3];
                    Residence.getLocaleManager().LoadLang(lang);
                    ++n3;
                }
                Residence.getConfigManager().UpdateFlagFile();
                try {
                    File langFile = new File(new File(dataFolder, "Language"), String.valueOf(cmanager.getLanguage()) + ".yml");
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
                        YamlConfiguration langconfig2 = new YamlConfiguration();
                        langconfig2.load((Reader)in);
                        helppages = HelpEntry.parseHelp((FileConfiguration)langconfig2, "CommandHelp");
                    } else {
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Language file does not exist...");
                    }
                    if (in != null) {
                        in.close();
                    }
                }
                catch (Exception ex) {
                    File langFile;
                    BufferedReader in;
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Failed to load language file: " + cmanager.getLanguage() + ".yml setting to default - English");
                    langFile = new File(new File(dataFolder, "Language"), "English.yml");
                    in = null;
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
                        langconfig = new OfflinePlayer[]();
                        langconfig.load((Reader)in);
                        helppages = HelpEntry.parseHelp((FileConfiguration)langconfig, "CommandHelp");
                    } else {
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Language file does not exist...");
                    }
                    if (in == null) break block78;
                    in.close();
                }
            }
            economy = null;
            if (this.getConfig().getBoolean("Global.EnableEconomy", false)) {
                ResidenceVaultAdapter vault;
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Scanning for economy systems...");
                if (gmanager.getPermissionsPlugin() instanceof ResidenceVaultAdapter && (vault = (ResidenceVaultAdapter)gmanager.getPermissionsPlugin()).economyOK()) {
                    economy = vault;
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Found Vault using economy system: " + vault.getEconomyName());
                }
                if (economy == null) {
                    this.loadVaultEconomy();
                }
                if (economy == null) {
                    this.loadBOSEconomy();
                }
                if (economy == null) {
                    this.loadEssentialsEconomy();
                }
                if (economy == null) {
                    this.loadRealEconomy();
                }
                if (economy == null) {
                    this.loadIConomy();
                }
                if (economy == null) {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Unable to find an economy system...");
                }
            }
            if (Residence.getConfigManager().isUUIDConvertion()) {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Loading (" + Bukkit.getOfflinePlayers().length + ") player data");
                langconfig = Bukkit.getOfflinePlayers();
                int in = langconfig.length;
                int langFile = 0;
                while (langFile < in) {
                    String name;
                    OfflinePlayer player = langconfig[langFile];
                    if (player != null && (name = player.getName()) != null) {
                        Residence.getOfflinePlayerMap().put(name.toLowerCase(), player);
                        Residence.getCachedPlayerNameUUIDs().put(player.getUniqueId(), player.getName());
                    }
                    ++langFile;
                }
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Player data loaded: " + Residence.getOfflinePlayerMap().size());
            } else {
                Bukkit.getScheduler().runTaskAsynchronously((Plugin)this, new Runnable(){

                    @Override
                    public void run() {
                        OfflinePlayer[] arrofflinePlayer = Bukkit.getOfflinePlayers();
                        int n = arrofflinePlayer.length;
                        int n2 = 0;
                        while (n2 < n) {
                            String name;
                            OfflinePlayer player = arrofflinePlayer[n2];
                            if (player != null && (name = player.getName()) != null) {
                                Residence.getOfflinePlayerMap().put(name.toLowerCase(), player);
                                Residence.getCachedPlayerNameUUIDs().put(player.getUniqueId(), player.getName());
                            }
                            ++n2;
                        }
                    }
                });
            }
            if (rmanager == null) {
                rmanager = new ResidenceManager(this);
            }
            if (leasemanager == null) {
                leasemanager = new LeaseManager(rmanager);
            }
            if (tmanager == null) {
                tmanager = new TransactionManager();
            }
            if (pmanager == null) {
                pmanager = new PermissionListManager();
            }
            try {
                this.loadYml();
            }
            catch (Exception e) {
                this.getLogger().log(Level.SEVERE, "Unable to load save file", e);
                throw e;
            }
            signmanager = new SignUtil(this);
            Residence.getSignUtil().LoadSigns();
            if (Residence.getConfigManager().isUseResidenceFileClean()) {
                FileCleanUp.cleanFiles();
            }
            if (this.firstenable) {
                if (!this.isEnabled()) {
                    return;
                }
                FlagPermissions.initValidFlags();
                this.setWorldEdit();
                Residence.setWorldGuard();
                blistener = new ResidenceBlockListener(this);
                plistener = new ResidencePlayerListener(this);
                elistener = new ResidenceEntityListener(this);
                flistener = new ResidenceFixesListener();
                shlistener = new ShopListener();
                spigotlistener = new SpigotListener();
                PluginManager pm = this.getServer().getPluginManager();
                pm.registerEvents((Listener)blistener, (Plugin)this);
                pm.registerEvents((Listener)plistener, (Plugin)this);
                pm.registerEvents((Listener)elistener, (Plugin)this);
                pm.registerEvents((Listener)flistener, (Plugin)this);
                pm.registerEvents((Listener)shlistener, (Plugin)this);
                if (Residence.getVersionChecker().GetVersion() >= 1800) {
                    pm.registerEvents((Listener)new v1_8Events(), (Plugin)this);
                }
                if (Residence.getVersionChecker().GetVersion() >= 1900) {
                    pm.registerEvents((Listener)new v1_9Events(), (Plugin)this);
                }
                if (Residence.getVersionChecker().GetVersion() >= 11000) {
                    pm.registerEvents((Listener)new v1_10Events(), (Plugin)this);
                }
                if (cmanager.enableSpout()) {
                    slistener = new ResidenceSpoutListener();
                    pm.registerEvents((Listener)slistener, (Plugin)this);
                    spout = new ResidenceSpout(this);
                }
                this.firstenable = false;
            } else {
                plistener.reload();
            }
            NewLanguageManager = new Language(this);
            Residence.getLM().LanguageReload();
            AutoSelectionManager = new AutoSelection();
            if (wep != null) {
                SchematicManager = new SchematicsManager();
            }
            try {
                Class.forName("org.bukkit.event.player.PlayerItemDamageEvent");
                this.getServer().getPluginManager().registerEvents((Listener)spigotlistener, (Plugin)this);
            }
            catch (Exception pm) {
                // empty catch block
            }
            if (this.getServer().getPluginManager().getPlugin("CrackShot") != null) {
                this.getServer().getPluginManager().registerEvents((Listener)new CrackShot(), (Plugin)this);
            }
            if ((dynmap = Bukkit.getPluginManager().getPlugin("dynmap")) != null && Residence.getConfigManager().DynMapUse) {
                DynManager = new DynMapManager(this);
                this.getServer().getPluginManager().registerEvents((Listener)new DynMapListeners(), (Plugin)this);
                Residence.getDynManager().api = (DynmapAPI)dynmap;
                Residence.getDynManager().activate();
            }
            if ((autosaveInt = cmanager.getAutoSaveInterval()) < 1) {
                autosaveInt = 1;
            }
            autosaveInt = autosaveInt * 60 * 20;
            autosaveBukkitId = server.getScheduler().scheduleSyncRepeatingTask((Plugin)this, this.autoSave, (long)autosaveInt, (long)autosaveInt);
            healBukkitId = server.getScheduler().scheduleSyncRepeatingTask((Plugin)this, this.doHeals, 20, (long)(Residence.getConfigManager().getHealInterval() * 20));
            feedBukkitId = server.getScheduler().scheduleSyncRepeatingTask((Plugin)this, this.doFeed, 20, (long)(Residence.getConfigManager().getFeedInterval() * 20));
            if (Residence.getConfigManager().AutoMobRemoval()) {
                DespawnMobsBukkitId = server.getScheduler().scheduleSyncRepeatingTask((Plugin)this, this.DespawnMobs, (long)(20 * Residence.getConfigManager().AutoMobRemovalInterval()), (long)(20 * Residence.getConfigManager().AutoMobRemovalInterval()));
            }
            if (cmanager.useLeases()) {
                int leaseInterval = cmanager.getLeaseCheckInterval();
                if (leaseInterval < 1) {
                    leaseInterval = 1;
                }
                leaseInterval = leaseInterval * 60 * 20;
                leaseBukkitId = server.getScheduler().scheduleSyncRepeatingTask((Plugin)this, this.leaseExpire, (long)leaseInterval, (long)leaseInterval);
            }
            if (cmanager.enabledRentSystem()) {
                int rentint = cmanager.getRentCheckInterval();
                if (rentint < 1) {
                    rentint = 1;
                }
                rentint = rentint * 60 * 20;
                rentBukkitId = server.getScheduler().scheduleSyncRepeatingTask((Plugin)this, this.rentExpire, (long)rentint, (long)rentint);
            }
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (!Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) continue;
                Residence.turnResAdminOn(player);
            }
            try {
                Metrics metrics = new Metrics((Plugin)this);
                metrics.start();
            }
            catch (IOException metrics) {
                // empty catch block
            }
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Enabled! Version " + this.getDescription().getVersion() + " by Zrips");
            initsuccess = true;
            PlayerManager.fillList();
        }
        catch (Exception ex) {
            initsuccess = false;
            this.getServer().getPluginManager().disablePlugin((Plugin)this);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " - FAILED INITIALIZATION! DISABLED! ERROR:");
            Logger.getLogger(Residence.class.getName()).log(Level.SEVERE, null, ex);
            Bukkit.getServer().shutdown();
        }
        Residence.getShopSignUtilManager().LoadShopVotes();
        Residence.getShopSignUtilManager().LoadSigns();
        Residence.getShopSignUtilManager().BoardUpdate();
        Residence.getVersionChecker().VersionCheck(null);
    }

    public static SignUtil getSignUtil() {
        return signmanager;
    }

    public void consoleMessage(String message2) {
        Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " " + message2);
    }

    public static boolean validName(String name) {
        if (name.contains(":") || name.contains(".") || name.contains("|")) {
            return false;
        }
        if (cmanager.getResidenceNameRegex() == null) {
            return true;
        }
        String namecheck = name.replaceAll(cmanager.getResidenceNameRegex(), "");
        if (!name.equals(namecheck)) {
            return false;
        }
        return true;
    }

    private void setWorldEdit() {
        Plugin plugin = server.getPluginManager().getPlugin("WorldEdit");
        if (plugin != null) {
            smanager = new WorldEditSelectionManager(server, this);
            wep = (WorldEditPlugin)plugin;
            wepid = wep.getConfig().getInt("wand-item");
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Found WorldEdit");
        } else {
            smanager = new SelectionManager(server, this);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " WorldEdit NOT found!");
        }
    }

    private static void setWorldGuard() {
        Plugin wgplugin = server.getPluginManager().getPlugin("WorldGuard");
        if (wgplugin != null) {
            try {
                Class.forName("com.sk89q.worldedit.BlockVector");
                Class.forName("com.sk89q.worldguard.bukkit.RegionContainer");
                Class.forName("com.sk89q.worldguard.protection.ApplicableRegionSet");
                Class.forName("com.sk89q.worldguard.protection.managers.RegionManager");
                Class.forName("com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion");
                Class.forName("com.sk89q.worldguard.protection.regions.ProtectedRegion");
            }
            catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + (Object)ChatColor.RED + " Found WorldGuard, but its not supported by Residence plugin. Please update WorldGuard to latest version");
                return;
            }
            wg = (WorldGuardPlugin)wgplugin;
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Found WorldGuard");
        }
    }

    public Residence getPlugin() {
        return this;
    }

    public static VersionChecker getVersionChecker() {
        return versionChecker;
    }

    public static LWC getLwc() {
        return lwc;
    }

    public static File getDataLocation() {
        return dataFolder;
    }

    public static ShopSignUtil getShopSignUtilManager() {
        return ShopSignUtilManager;
    }

    public static ResidenceSpout getSpout() {
        return spout;
    }

    public static ResidenceSpoutListener getSpoutListener() {
        return slistener;
    }

    public static CommandFiller getCommandFiller() {
        if (cmdFiller == null) {
            cmdFiller = new CommandFiller();
            cmdFiller.fillCommands();
        }
        return cmdFiller;
    }

    public static ResidenceManager getResidenceManager() {
        return rmanager;
    }

    public static SelectionManager getSelectionManager() {
        return smanager;
    }

    public static FlagUtil getFlagUtilManager() {
        return FlagUtilManager;
    }

    public static PermissionManager getPermissionManager() {
        return gmanager;
    }

    public static PermissionListManager getPermissionListManager() {
        return pmanager;
    }

    public static DynMapManager getDynManager() {
        return DynManager;
    }

    public static SchematicsManager getSchematicManager() {
        return SchematicManager;
    }

    public static AutoSelection getAutoSelectionManager() {
        return AutoSelectionManager;
    }

    public static Sorting getSortingManager() {
        return SortingManager;
    }

    public static RandomTp getRandomTpManager() {
        return RandomTpManager;
    }

    public static EconomyInterface getEconomyManager() {
        return economy;
    }

    public static Server getServ() {
        return server;
    }

    public static LeaseManager getLeaseManager() {
        return leasemanager;
    }

    public static PlayerManager getPlayerManager() {
        return PlayerManager;
    }

    public static HelpEntry getHelpPages() {
        return helppages;
    }

    public static void setConfigManager(ConfigManager cm) {
        cmanager = cm;
    }

    public static ConfigManager getConfigManager() {
        return cmanager;
    }

    public static TransactionManager getTransactionManager() {
        return tmanager;
    }

    public static WorldItemManager getItemManager() {
        return imanager;
    }

    public static WorldFlagManager getWorldFlags() {
        return wmanager;
    }

    public static RentManager getRentManager() {
        return rentmanager;
    }

    public static LocaleManager getLocaleManager() {
        return LocaleManager;
    }

    public static Language getLM() {
        return NewLanguageManager;
    }

    public static ResidencePlayerListener getPlayerListener() {
        return plistener;
    }

    public static ResidenceBlockListener getBlockListener() {
        return blistener;
    }

    public static ResidenceEntityListener getEntityListener() {
        return elistener;
    }

    public static ChatManager getChatManager() {
        return chatmanager;
    }

    public static WorldEditPlugin getWEplugin() {
        return wep;
    }

    public static String getResidenceVersion() {
        return ResidenceVersion;
    }

    public static List<String> getAuthors() {
        return authlist;
    }

    public static FlagPermissions getPermsByLoc(Location loc) {
        ClaimedResidence res = rmanager.getByLoc(loc);
        if (res != null) {
            return res.getPermissions();
        }
        return wmanager.getPerms(loc.getWorld().getName());
    }

    public static FlagPermissions getPermsByLocForPlayer(Location loc, Player player) {
        ClaimedResidence res = rmanager.getByLoc(loc);
        if (res != null) {
            return res.getPermissions();
        }
        if (player != null) {
            return wmanager.getPerms(player);
        }
        return wmanager.getPerms(loc.getWorld().getName());
    }

    private void loadIConomy() {
        Plugin p = this.getServer().getPluginManager().getPlugin("iConomy");
        if (p != null) {
            if (p.getDescription().getVersion().startsWith("6")) {
                economy = new IConomy6Adapter((iConomy)p);
            } else if (p.getDescription().getVersion().startsWith("5")) {
                economy = new IConomy5Adapter();
            } else {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " UNKNOWN iConomy version!");
                return;
            }
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Successfully linked with iConomy! Version: " + p.getDescription().getVersion());
        } else {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " iConomy NOT found!");
        }
    }

    private void loadBOSEconomy() {
        Plugin p = this.getServer().getPluginManager().getPlugin("BOSEconomy");
        if (p != null) {
            economy = new BOSEAdapter((BOSEconomy)p);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Successfully linked with BOSEconomy!");
        } else {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " BOSEconomy NOT found!");
        }
    }

    private void loadEssentialsEconomy() {
        Plugin p = this.getServer().getPluginManager().getPlugin("Essentials");
        if (p != null) {
            economy = new EssentialsEcoAdapter((Essentials)p);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Successfully linked with Essentials Economy!");
        } else {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Essentials Economy NOT found!");
        }
    }

    private void loadRealEconomy() {
        Plugin p = this.getServer().getPluginManager().getPlugin("RealPlugin");
        if (p != null) {
            economy = new RealShopEconomy(new RealEconomy((RealPlugin)p));
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Successfully linked with RealShop Economy!");
        } else {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " RealShop Economy NOT found!");
        }
    }

    private void loadVaultEconomy() {
        Plugin p = this.getServer().getPluginManager().getPlugin("Vault");
        if (p != null) {
            ResidenceVaultAdapter vault = new ResidenceVaultAdapter(this.getServer());
            if (vault.economyOK()) {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Found Vault using economy: " + vault.getEconomyName());
                economy = vault;
            } else {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Found Vault, but Vault reported no usable economy system...");
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Vault NOT found!");
        }
    }

    public static boolean isResAdminOn(CommandSender sender) {
        if (sender instanceof Player) {
            return Residence.isResAdminOn((Player)sender);
        }
        return true;
    }

    public static boolean isResAdminOn(Player player) {
        if (resadminToggle.contains(player.getName())) {
            return true;
        }
        return false;
    }

    public static void turnResAdminOn(Player player) {
        resadminToggle.add(player.getName());
    }

    public static boolean isResAdminOn(String player) {
        if (resadminToggle.contains(player)) {
            return true;
        }
        return false;
    }

    private static void saveYml() throws IOException {
        YMLSaveHelper yml;
        File backupFile;
        File backupFolder;
        File saveFolder = new File(dataFolder, "Save");
        File worldFolder = new File(saveFolder, "Worlds");
        worldFolder.mkdirs();
        Map<String, Object> save = rmanager.save();
        for (Map.Entry<String, Object> entry : save.entrySet()) {
            File ymlSaveLoc = new File(worldFolder, "res_" + entry.getKey() + ".yml");
            File tmpFile = new File(worldFolder, "tmp_res_" + entry.getKey() + ".yml");
            yml = new YMLSaveHelper(tmpFile);
            yml.getRoot().put("Version", saveVersion);
            World world = server.getWorld(entry.getKey());
            if (world != null) {
                yml.getRoot().put("Seed", world.getSeed());
            }
            yml.getRoot().put("Residences", entry.getValue());
            yml.save();
            if (ymlSaveLoc.isFile()) {
                File backupFolder2 = new File(worldFolder, "Backup");
                backupFolder2.mkdirs();
                File backupFile2 = new File(backupFolder2, "res_" + entry.getKey() + ".yml");
                if (backupFile2.isFile()) {
                    backupFile2.delete();
                }
                ymlSaveLoc.renameTo(backupFile2);
            }
            tmpFile.renameTo(ymlSaveLoc);
        }
        File ymlSaveLoc = new File(saveFolder, "forsale.yml");
        File tmpFile = new File(saveFolder, "tmp_forsale.yml");
        yml = new YMLSaveHelper(tmpFile);
        yml.save();
        yml.getRoot().put("Version", saveVersion);
        yml.getRoot().put("Economy", tmanager.save());
        yml.save();
        if (ymlSaveLoc.isFile()) {
            backupFolder = new File(saveFolder, "Backup");
            backupFolder.mkdirs();
            backupFile = new File(backupFolder, "forsale.yml");
            if (backupFile.isFile()) {
                backupFile.delete();
            }
            ymlSaveLoc.renameTo(backupFile);
        }
        tmpFile.renameTo(ymlSaveLoc);
        ymlSaveLoc = new File(saveFolder, "leases.yml");
        tmpFile = new File(saveFolder, "tmp_leases.yml");
        yml = new YMLSaveHelper(tmpFile);
        yml.getRoot().put("Version", saveVersion);
        yml.getRoot().put("Leases", leasemanager.save());
        yml.save();
        if (ymlSaveLoc.isFile()) {
            backupFolder = new File(saveFolder, "Backup");
            backupFolder.mkdirs();
            backupFile = new File(backupFolder, "leases.yml");
            if (backupFile.isFile()) {
                backupFile.delete();
            }
            ymlSaveLoc.renameTo(backupFile);
        }
        tmpFile.renameTo(ymlSaveLoc);
        ymlSaveLoc = new File(saveFolder, "permlists.yml");
        tmpFile = new File(saveFolder, "tmp_permlists.yml");
        yml = new YMLSaveHelper(tmpFile);
        yml.getRoot().put("Version", saveVersion);
        yml.getRoot().put("PermissionLists", pmanager.save());
        yml.save();
        if (ymlSaveLoc.isFile()) {
            backupFolder = new File(saveFolder, "Backup");
            backupFolder.mkdirs();
            backupFile = new File(backupFolder, "permlists.yml");
            if (backupFile.isFile()) {
                backupFile.delete();
            }
            ymlSaveLoc.renameTo(backupFile);
        }
        tmpFile.renameTo(ymlSaveLoc);
        ymlSaveLoc = new File(saveFolder, "rent.yml");
        tmpFile = new File(saveFolder, "tmp_rent.yml");
        yml = new YMLSaveHelper(tmpFile);
        yml.getRoot().put("Version", saveVersion);
        yml.getRoot().put("RentSystem", rentmanager.save());
        yml.save();
        if (ymlSaveLoc.isFile()) {
            backupFolder = new File(saveFolder, "Backup");
            backupFolder.mkdirs();
            backupFile = new File(backupFolder, "rent.yml");
            if (backupFile.isFile()) {
                backupFile.delete();
            }
            ymlSaveLoc.renameTo(backupFile);
        }
        tmpFile.renameTo(ymlSaveLoc);
        if (cmanager.showIntervalMessages()) {
            System.out.println("[Residence] - Saved Residences...");
        }
    }

    protected boolean loadYml() throws Exception {
        File worldFolder;
        File saveFolder;
        block11 : {
            saveFolder = new File(dataFolder, "Save");
            worldFolder = new File(saveFolder, "Worlds");
            if (saveFolder.isDirectory()) break block11;
            this.getLogger().warning("Save directory does not exist...");
            this.getLogger().warning("Please restart server");
            return true;
        }
        try {
            File loadFile;
            YMLSaveHelper yml;
            HashMap<String, Object> worlds = new HashMap<String, Object>();
            for (World world : Residence.getServ().getWorlds()) {
                loadFile = new File(worldFolder, "res_" + world.getName() + ".yml");
                if (!loadFile.isFile()) continue;
                long time = System.currentTimeMillis();
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Loading save data for world " + world.getName() + "...");
                yml = new YMLSaveHelper(loadFile);
                yml.load();
                worlds.put(world.getName(), yml.getRoot().get("Residences"));
                int pass = (int)(System.currentTimeMillis() - time);
                String PastTime = pass > 1000 ? String.valueOf(String.format("%.2f", Float.valueOf((float)pass / 1000.0f))) + " sec" : String.valueOf(pass) + " ms";
                Bukkit.getConsoleSender().sendMessage(String.valueOf(prefix) + " Loaded " + world.getName() + " data. (" + PastTime + ")");
            }
            rmanager = Residence.getResidenceManager().load(worlds);
            Map<String, ClaimedResidence> resList = rmanager.getResidences();
            for (Map.Entry<String, ClaimedResidence> one : resList.entrySet()) {
                this.addShops(one.getValue());
            }
            if (Residence.getConfigManager().isUUIDConvertion()) {
                Residence.getConfigManager().ChangeConfig("Global.UUIDConvertion", false);
            }
            if ((loadFile = new File(saveFolder, "forsale.yml")).isFile()) {
                yml = new YMLSaveHelper(loadFile);
                yml.load();
                tmanager = new TransactionManager();
                tmanager.load((Map)yml.getRoot().get("Economy"));
            }
            if ((loadFile = new File(saveFolder, "leases.yml")).isFile()) {
                yml = new YMLSaveHelper(loadFile);
                yml.load();
                leasemanager = LeaseManager.load((Map)yml.getRoot().get("Leases"), rmanager);
            }
            if ((loadFile = new File(saveFolder, "permlists.yml")).isFile()) {
                yml = new YMLSaveHelper(loadFile);
                yml.load();
                pmanager = PermissionListManager.load((Map)yml.getRoot().get("PermissionLists"));
            }
            if ((loadFile = new File(saveFolder, "rent.yml")).isFile()) {
                yml = new YMLSaveHelper(loadFile);
                yml.load();
                rentmanager.load((Map)yml.getRoot().get("RentSystem"));
            }
            for (Player one : Bukkit.getOnlinePlayers()) {
                ResidencePlayer rplayer = Residence.getPlayerManager().getResidencePlayer(one);
                if (rplayer == null) continue;
                rplayer.recountRes();
            }
            return true;
        }
        catch (Exception ex) {
            Logger.getLogger(Residence.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    private void addShops(ClaimedResidence res) {
        ResidencePermissions perms = res.getPermissions();
        if (perms.has(Flags.shop, FlagPermissions.FlagCombo.OnlyTrue, false)) {
            rmanager.addShop(res);
        }
        for (ClaimedResidence one : res.getSubzones()) {
            this.addShops(one);
        }
    }

    private void writeDefaultConfigFromJar() {
        if (this.writeDefaultFileFromJar(new File(this.getDataFolder(), "config.yml"), "config.yml", true)) {
            System.out.println("[Residence] Wrote default config...");
        }
    }

    private void writeDefaultGroupsFromJar() {
        if (this.writeDefaultFileFromJar(new File(this.getDataFolder(), "groups.yml"), "groups.yml", true)) {
            System.out.println("[Residence] Wrote default groups...");
        }
    }

    private void writeDefaultFlagsFromJar() {
        if (this.writeDefaultFileFromJar(new File(this.getDataFolder(), "flags.yml"), "flags.yml", true)) {
            System.out.println("[Residence] Wrote default flags...");
        }
    }

    private void ConvertFile() {
        File file = new File(this.getDataFolder(), "config.yml");
        File file_old = new File(this.getDataFolder(), "config_old.yml");
        File newfile = new File(this.getDataFolder(), "groups.yml");
        File newTempFlags = new File(this.getDataFolder(), "flags.yml");
        try {
            Residence.copy(file, file_old);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            Residence.copy(file, newfile);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            Residence.copy(file, newTempFlags);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        File newGroups = new File(this.getDataFolder(), "config.yml");
        List list2 = new ArrayList<String>();
        list2.add((String)"ResidenceVersion");
        list2.add((String)"Global.Flags");
        list2.add((String)"Global.FlagPermission");
        list2.add((String)"Global.ResidenceDefault");
        list2.add((String)"Global.CreatorDefault");
        list2.add((String)"Global.GroupDefault");
        list2.add((String)"Groups");
        list2.add((String)"GroupAssignments");
        list2.add((String)"ItemList");
        try {
            Residence.remove(newGroups, list2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        File newConfig = new File(this.getDataFolder(), "groups.yml");
        list2.clear();
        list2 = new ArrayList();
        list2.add("ResidenceVersion");
        list2.add("Global");
        list2.add("ItemList");
        try {
            Residence.remove(newConfig, list2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        File newFlags = new File(this.getDataFolder(), "flags.yml");
        list2.clear();
        list2 = new ArrayList();
        list2.add("ResidenceVersion");
        list2.add("GroupAssignments");
        list2.add("Groups");
        list2.add("Global.Language");
        list2.add("Global.SelectionToolId");
        list2.add("Global.InfoToolId");
        list2.add("Global.MoveCheckInterval");
        list2.add("Global.SaveInterval");
        list2.add("Global.DefaultGroup");
        list2.add("Global.UseLeaseSystem");
        list2.add("Global.LeaseCheckInterval");
        list2.add("Global.LeaseAutoRenew");
        list2.add("Global.EnablePermissions");
        list2.add("Global.LegacyPermissions");
        list2.add("Global.EnableEconomy");
        list2.add("Global.EnableRentSystem");
        list2.add("Global.RentCheckInterval");
        list2.add("Global.ResidenceChatEnable");
        list2.add("Global.UseActionBar");
        list2.add("Global.ResidenceChatColor");
        list2.add("Global.AdminOnlyCommands");
        list2.add("Global.AdminOPs");
        list2.add("Global.MultiWorldPlugin");
        list2.add("Global.ResidenceFlagsInherit");
        list2.add("Global.PreventRentModify");
        list2.add("Global.StopOnSaveFault");
        list2.add("Global.ResidenceNameRegex");
        list2.add("Global.ShowIntervalMessages");
        list2.add("Global.VersionCheck");
        list2.add("Global.CustomContainers");
        list2.add("Global.CustomBothClick");
        list2.add("Global.CustomRightClick");
        try {
            Residence.remove(newFlags, list2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void remove(File newGroups, List<String> list2) throws IOException {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration((File)newGroups);
        conf.options().copyDefaults(true);
        for (String one : list2) {
            conf.set(one, (Object)null);
        }
        try {
            conf.save(newGroups);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(File source, File target) throws IOException {
        int len;
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    private boolean writeDefaultFileFromJar(File writeName, String jarPath, boolean backupOld) {
        block8 : {
            JarFile jar;
            block9 : {
                try {
                    File fileBackup = new File(this.getDataFolder(), "backup-" + writeName);
                    File jarloc = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getCanonicalFile();
                    if (!jarloc.isFile()) break block8;
                    jar = new JarFile(jarloc);
                    JarEntry entry = jar.getJarEntry(jarPath);
                    if (entry == null || entry.isDirectory()) break block9;
                    InputStream in = jar.getInputStream(entry);
                    InputStreamReader isr = new InputStreamReader(in, "UTF8");
                    if (writeName.isFile()) {
                        if (backupOld) {
                            if (fileBackup.isFile()) {
                                fileBackup.delete();
                            }
                            writeName.renameTo(fileBackup);
                        } else {
                            writeName.delete();
                        }
                    }
                    FileOutputStream out = new FileOutputStream(writeName);
                    OutputStreamWriter osw = new OutputStreamWriter((OutputStream)out, "UTF8");
                    char[] tempbytes = new char[512];
                    int readbytes = isr.read(tempbytes, 0, 512);
                    while (readbytes > -1) {
                        osw.write(tempbytes, 0, readbytes);
                        readbytes = isr.read(tempbytes, 0, 512);
                    }
                    osw.close();
                    isr.close();
                    return true;
                }
                catch (Exception ex) {
                    System.out.println("[Residence] Failed to write file: " + writeName);
                    return false;
                }
            }
            jar.close();
        }
        return false;
    }

    public static boolean isPlayerExist(CommandSender sender, String name, boolean inform) {
        if (Residence.getPlayerUUID(name) != null) {
            return true;
        }
        if (inform) {
            sender.sendMessage(Residence.msg(lm.Invalid_Player, new Object[0]));
        }
        String a = "%%__USER__%%";
        String b = "%%__RESOURCE__%%";
        String c = "%%__NONCE__%%";
        return false;
    }

    public static UUID getPlayerUUID(String playername) {
        Player p = Residence.getServ().getPlayer(playername);
        if (p == null) {
            if (Residence.getOfflinePlayerMap().containsKey(playername.toLowerCase())) {
                return Residence.getOfflinePlayerMap().get(playername.toLowerCase()).getUniqueId();
            }
        } else {
            return p.getUniqueId();
        }
        return null;
    }

    public static ConcurrentHashMap<String, OfflinePlayer> getOfflinePlayerMap() {
        return OfflinePlayerList;
    }

    public static OfflinePlayer getOfflinePlayer(String Name2) {
        if (Residence.getOfflinePlayerMap().containsKey(Name2.toLowerCase())) {
            return Residence.getOfflinePlayerMap().get(Name2.toLowerCase());
        }
        Player player = Bukkit.getPlayer((String)Name2);
        Player offPlayer = null;
        if (player != null) {
            offPlayer = player;
        }
        if (offPlayer == null) {
            offPlayer = Bukkit.getOfflinePlayer((String)Name2);
        }
        if (offPlayer != null) {
            Residence.getOfflinePlayerMap().put(Name2.toLowerCase(), (OfflinePlayer)offPlayer);
        }
        return offPlayer;
    }

    public static String getPlayerUUIDString(String playername) {
        UUID playerUUID = Residence.getPlayerUUID(playername);
        if (playerUUID != null) {
            return playerUUID.toString();
        }
        return null;
    }

    public static String getPlayerName(String uuid) {
        try {
            return Residence.getPlayerName(UUID.fromString(uuid));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public static String getServerLandname() {
        return ServerLandname;
    }

    public static String getServerLandUUID() {
        return ServerLandUUID;
    }

    public static String getTempUserUUID() {
        return TempUserUUID;
    }

    public static String getPlayerName(UUID uuid) {
        Player p = Residence.getServ().getPlayer(uuid);
        if (p == null) {
            p = Residence.getServ().getOfflinePlayer(uuid);
        }
        if (p != null) {
            return p.getName();
        }
        return null;
    }

    public static boolean isDisabledWorldListener(World world) {
        return Residence.isDisabledWorldListener(world.getName());
    }

    public static boolean isDisabledWorldListener(String worldname) {
        if (Residence.getConfigManager().DisabledWorldsList.contains(worldname) && Residence.getConfigManager().DisableListeners) {
            return true;
        }
        return false;
    }

    public static boolean isDisabledWorldCommand(World world) {
        return Residence.isDisabledWorldCommand(world.getName());
    }

    public static boolean isDisabledWorldCommand(String worldname) {
        if (Residence.getConfigManager().DisabledWorldsList.contains(worldname) && Residence.getConfigManager().DisableCommands) {
            return true;
        }
        return false;
    }

    public static String msg(String path) {
        return Residence.getLM().getMessage(path);
    }

    public static void msg(CommandSender sender, String text) {
        if (sender != null && text.length() > 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)text));
        }
    }

    public static void msg(Player player, String text) {
        if (player != null && text.length() > 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)text));
        }
    }

    public static /* varargs */ void msg(CommandSender sender, lm lm2, Object ... variables) {
        if (sender != null) {
            if (Residence.getLM().containsKey(lm2.getPath())) {
                String msg = Residence.getLM().getMessage(lm2, variables);
                if (msg.length() > 0) {
                    sender.sendMessage(msg);
                }
            } else {
                String msg = lm2.getPath();
                if (msg.length() > 0) {
                    sender.sendMessage(lm2.getPath());
                }
            }
        }
    }

    public static List<String> msgL(lm lm2) {
        return Residence.getLM().getMessageList(lm2);
    }

    public static /* varargs */ String msg(lm lm2, Object ... variables) {
        return Residence.getLM().getMessage(lm2, variables);
    }

    public static InformationPager getInfoPageManager() {
        return InformationPagerManager;
    }

    public static Map<UUID, String> getCachedPlayerNameUUIDs() {
        return cachedPlayerNameUUIDs;
    }

    public static void addCachedPlayerNameUUIDs(UUID uuid, String name) {
        cachedPlayerNameUUIDs.put(uuid, name);
    }

    public static void addCachedPlayerNameUUIDs(Map<UUID, String> cachedPlayerNameUUIDs2) {
        cachedPlayerNameUUIDs.putAll(cachedPlayerNameUUIDs2);
    }

}

