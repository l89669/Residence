/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.WeatherType
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Sign
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Boat
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Hanging
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Player$Spigot
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.SignChangeEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerLoginEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerShearEntityEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package com.bekvon.bukkit.residence.listeners;

import com.bekvon.bukkit.residence.ConfigManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.chat.ChatChannel;
import com.bekvon.bukkit.residence.containers.AutoSelector;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.Visualizer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.rent.RentableLand;
import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.event.ResidenceChangedEvent;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceFlagChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceRenameEvent;
import com.bekvon.bukkit.residence.gui.SetFlag;
import com.bekvon.bukkit.residence.listeners.ResidenceEntityListener;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.signsStuff.SignInfo;
import com.bekvon.bukkit.residence.signsStuff.Signs;
import com.bekvon.bukkit.residence.utils.GetTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class ResidencePlayerListener
implements Listener {
    protected Map<String, String> currentRes = new HashMap<String, String>();
    protected Map<String, Long> lastUpdate = new HashMap<String, Long>();
    protected Map<String, Location> lastOutsideLoc = new HashMap<String, Location>();
    protected int minUpdateTime;
    protected boolean chatenabled;
    protected List<String> playerToggleChat = new ArrayList<String>();
    public static Map<String, SetFlag> GUI = new HashMap<String, SetFlag>();
    private Residence plugin;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState;

    public ResidencePlayerListener(Residence plugin) {
        this.playerToggleChat.clear();
        this.minUpdateTime = Residence.getConfigManager().getMinMoveUpdateInterval();
        this.chatenabled = Residence.getConfigManager().chatEnabled();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.lastUpdate.put(player.getName(), System.currentTimeMillis());
        }
        this.plugin = plugin;
    }

    public Map<String, SetFlag> getGUImap() {
        return GUI;
    }

    public void reload() {
        this.currentRes = new HashMap<String, String>();
        this.lastUpdate = new HashMap<String, Long>();
        this.lastOutsideLoc = new HashMap<String, Location>();
        this.playerToggleChat.clear();
        this.minUpdateTime = Residence.getConfigManager().getMinMoveUpdateInterval();
        this.chatenabled = Residence.getConfigManager().chatEnabled();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.lastUpdate.put(player.getName(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.isFlying()) {
            return;
        }
        if (event.getTo().getY() - event.getFrom().getY() != 0.41999998688697815) {
            return;
        }
        FlagPermissions perms = Residence.getPermsByLoc(player.getLocation());
        if (perms.has(Flags.jump2, FlagPermissions.FlagCombo.OnlyTrue)) {
            player.setVelocity(player.getVelocity().add(player.getVelocity().multiply(0.3)));
        } else if (perms.has(Flags.jump3, FlagPermissions.FlagCombo.OnlyTrue)) {
            player.setVelocity(player.getVelocity().add(player.getVelocity().multiply(0.6)));
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=1)
    public void onPlayerGlobalChat(AsyncPlayerChatEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (!Residence.getConfigManager().isGlobalChatEnabled()) {
            return;
        }
        if (!Residence.getConfigManager().isGlobalChatSelfModify()) {
            return;
        }
        Player player = event.getPlayer();
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        if (rPlayer == null) {
            return;
        }
        if (rPlayer.getResList().size() == 0) {
            return;
        }
        ClaimedResidence res = rPlayer.getMainResidence();
        if (res == null) {
            return;
        }
        String honorific = Residence.getConfigManager().getGlobalChatFormat().replace("%1", res.getTopParentName());
        String format = event.getFormat();
        format = format.replace("%1$s", String.valueOf(honorific) + "%1$s");
        event.setFormat(format);
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=1)
    public void onPlayerChatGlobalLow(AsyncPlayerChatEvent event) {
        String format;
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (!Residence.getConfigManager().isGlobalChatEnabled()) {
            return;
        }
        if (Residence.getConfigManager().isGlobalChatSelfModify()) {
            return;
        }
        Player player = event.getPlayer();
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        if (rPlayer == null) {
            return;
        }
        if (rPlayer.getResList().size() == 0) {
            return;
        }
        ClaimedResidence res = rPlayer.getMainResidence();
        if (res == null) {
            return;
        }
        String honorific = Residence.getConfigManager().getGlobalChatFormat().replace("%1", res.getTopParentName());
        if (honorific.equalsIgnoreCase(" ")) {
            honorific = "";
        }
        if (!(format = event.getFormat()).contains("{residence}")) {
            return;
        }
        format = format.replace("{residence}", honorific);
        event.setFormat(format);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceBackup(ResidenceFlagChangeEvent event) {
        if (!event.getFlag().equalsIgnoreCase(Flags.backup.getName())) {
            return;
        }
        Player player = event.getPlayer();
        if (!Residence.getConfigManager().RestoreAfterRentEnds) {
            return;
        }
        if (!Residence.getConfigManager().SchematicsSaveOnFlagChange) {
            return;
        }
        if (Residence.getSchematicManager() == null) {
            return;
        }
        if (player != null && !player.hasPermission("residence.backup")) {
            event.setCancelled(true);
        } else {
            Residence.getSchematicManager().save(event.getResidence());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceBackupRename(ResidenceRenameEvent event) {
        if (Residence.getSchematicManager() == null) {
            return;
        }
        Residence.getSchematicManager().rename(event.getResidence(), event.getNewResidenceName());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceDelete(ResidenceDeleteEvent event) {
        if (Residence.getSchematicManager() == null) {
            return;
        }
        Residence.getSchematicManager().delete(event.getResidence());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!Residence.getConfigManager().isRentInformOnEnding()) {
            return;
        }
        final Player player = event.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (!player.isOnline()) {
                    return;
                }
                List<String> list2 = Residence.getRentManager().getRentedLandsList(player.getName());
                if (list2.isEmpty()) {
                    return;
                }
                for (String one : list2) {
                    RentedLand rentedland = Residence.getRentManager().getRentedLand(one);
                    if (rentedland == null || rentedland.AutoPay || rentedland.endTime - System.currentTimeMillis() >= (long)(Residence.getConfigManager().getRentInformBefore() * 60 * 24 * 7)) continue;
                    Residence.msg((CommandSender)player, lm.Residence_EndingRent, one, GetTime.getTime(rentedland.endTime));
                }
            }
        }, (long)Residence.getConfigManager().getRentInformDelay() * 20);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=1)
    public void onFishingRodUse(PlayerFishEvent event) {
        if (event == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (event.getCaught() == null) {
            return;
        }
        if (Residence.getNms().isArmorStandEntity(event.getCaught().getType()) || event.getCaught() instanceof Boat || event.getCaught() instanceof LivingEntity) {
            FlagPermissions perm = Residence.getPermsByLoc(event.getCaught().getLocation());
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getCaught().getLocation());
            if (perm.has(Flags.hook, FlagPermissions.FlagCombo.OnlyFalse)) {
                event.setCancelled(true);
                if (res != null) {
                    Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.hook.getName(), res.getName());
                }
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onFlagChangeDayNight(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.day.getName()) && !event.getFlag().equalsIgnoreCase(Flags.night.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2: 
            case 3: {
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.resetPlayerTime();
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                if (event.getFlag().equalsIgnoreCase(Flags.day.getName())) {
                    for (Player one : event.getResidence().getPlayersInResidence()) {
                        one.setPlayerTime(6000, false);
                    }
                }
                if (!event.getFlag().equalsIgnoreCase(Flags.night.getName())) break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setPlayerTime(14000, false);
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onFlagChangeGlow(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.glow.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2: 
            case 3: {
                if (Residence.getVersionChecker().GetVersion() <= 1900 || !event.getFlag().equalsIgnoreCase(Flags.glow.getName())) break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setGlowing(false);
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                if (!event.getFlag().equalsIgnoreCase(Flags.glow.getName()) || Residence.getVersionChecker().GetVersion() <= 1900) break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setGlowing(true);
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onFlagChangeWSpeed(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.wspeed1.getName()) && !event.getFlag().equalsIgnoreCase(Flags.wspeed2.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2: 
            case 3: {
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setWalkSpeed(0.2f);
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                if (event.getFlag().equalsIgnoreCase(Flags.wspeed1.getName())) {
                    for (Player one : event.getResidence().getPlayersInResidence()) {
                        one.setWalkSpeed(Residence.getConfigManager().getWalkSpeed1().floatValue());
                    }
                    if (!event.getResidence().getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) break;
                    event.getResidence().getPermissions().setFlag(Flags.wspeed2.getName(), FlagPermissions.FlagState.NEITHER);
                    break;
                }
                if (!event.getFlag().equalsIgnoreCase(Flags.wspeed2.getName())) break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setWalkSpeed(Residence.getConfigManager().getWalkSpeed2().floatValue());
                }
                if (!event.getResidence().getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue)) break;
                event.getResidence().getPermissions().setFlag(Flags.wspeed1.getName(), FlagPermissions.FlagState.NEITHER);
                break;
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onFlagChangeJump(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.jump2.getName()) && !event.getFlag().equalsIgnoreCase(Flags.jump3.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2: 
            case 3: 
            case 4: {
                break;
            }
            case 1: {
                if (event.getFlag().equalsIgnoreCase(Flags.jump2.getName())) {
                    if (!event.getResidence().getPermissions().has(Flags.jump3, FlagPermissions.FlagCombo.OnlyTrue)) break;
                    event.getResidence().getPermissions().setFlag(Flags.jump3.getName(), FlagPermissions.FlagState.NEITHER);
                    break;
                }
                if (!event.getFlag().equalsIgnoreCase(Flags.jump3.getName()) || !event.getResidence().getPermissions().has(Flags.jump2, FlagPermissions.FlagCombo.OnlyTrue)) break;
                event.getResidence().getPermissions().setFlag(Flags.jump2.getName(), FlagPermissions.FlagState.NEITHER);
                break;
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onFlagChangeSunRain(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.sun.getName()) && !event.getFlag().equalsIgnoreCase(Flags.rain.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2: 
            case 3: {
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.resetPlayerWeather();
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                if (event.getFlag().equalsIgnoreCase(Flags.sun.getName())) {
                    for (Player one : event.getResidence().getPlayersInResidence()) {
                        one.setPlayerWeather(WeatherType.CLEAR);
                    }
                }
                if (!event.getFlag().equalsIgnoreCase(Flags.rain.getName())) break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setPlayerWeather(WeatherType.DOWNFALL);
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=1)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        String resname = Residence.getPlayerListener().getCurrentResidenceName(player.getName());
        if (resname == null) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByName(resname);
        if (res == null) {
            return;
        }
        if (!res.getPermissions().playerHas(player, Flags.command, FlagPermissions.FlagCombo.OnlyFalse)) {
            return;
        }
        if (Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
            return;
        }
        if (player.hasPermission("residence.flag.command.bypass")) {
            return;
        }
        String msg = event.getMessage().replace(" ", "_");
        int white = 0;
        int black = 0;
        for (String oneWhite : res.getCmdWhiteList()) {
            if (!msg.startsWith("/" + oneWhite)) continue;
            if (oneWhite.contains("_") && oneWhite.split("_").length > white) {
                white = oneWhite.split("_").length;
                continue;
            }
            if (white != 0) continue;
            white = 1;
        }
        for (String oneBlack : res.getCmdBlackList()) {
            if (!msg.startsWith("/" + oneBlack)) continue;
            if (msg.contains("_")) {
                black = oneBlack.split("_").length;
                break;
            }
            black = 1;
            break;
        }
        if (black == 0) {
            for (String oneBlack : res.getCmdBlackList()) {
                if (!oneBlack.equalsIgnoreCase("*")) continue;
                if (!msg.contains("_")) break;
                black = msg.split("_").length;
                break;
            }
        }
        if (white != 0 && white >= black || black == 0) {
            return;
        }
        event.setCancelled(true);
        Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.command.getName(), res.getName());
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=1)
    public void onFlagGuiClick(InventoryClickEvent event) {
        if (this.getGUImap().size() == 0) {
            return;
        }
        Player player = (Player)event.getWhoClicked();
        if (!this.getGUImap().containsKey(player.getName())) {
            return;
        }
        event.setCancelled(true);
        int slot = event.getRawSlot();
        if (slot > 53 || slot < 0) {
            return;
        }
        SetFlag setFlag = this.getGUImap().get(player.getName());
        ClickType click = event.getClick();
        InventoryAction action = event.getAction();
        setFlag.toggleFlag(slot, click, action);
        setFlag.recalculateInv();
        player.getOpenInventory().getTopInventory().setContents(setFlag.getInventory().getContents());
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=1)
    public void onFlagGuiClose(InventoryCloseEvent event) {
        if (this.getGUImap().isEmpty()) {
            return;
        }
        HumanEntity player = event.getPlayer();
        if (!this.getGUImap().containsKey(player.getName())) {
            return;
        }
        this.getGUImap().remove(player.getName());
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=1)
    public void onSignInteract(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        if (!(block.getState() instanceof Sign)) {
            return;
        }
        Player player = event.getPlayer();
        Location loc = block.getLocation();
        for (Signs one : Residence.getSignUtil().getSigns().GetAllSigns()) {
            if (!one.GetLocation().getWorld().getName().equalsIgnoreCase(loc.getWorld().getName()) || one.GetLocation().getBlockX() != loc.getBlockX() || one.GetLocation().getBlockY() != loc.getBlockY() || one.GetLocation().getBlockZ() != loc.getBlockZ()) continue;
            ClaimedResidence res = one.GetResidence();
            boolean ForSale = res.isForSell();
            boolean ForRent = res.isForRent();
            String landName = res.getName();
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (ForSale) {
                    Bukkit.dispatchCommand((CommandSender)player, (String)("res market buy " + landName));
                    break;
                }
                if (!ForRent) continue;
                if (res.isRented() && player.isSneaking()) {
                    Bukkit.dispatchCommand((CommandSender)player, (String)("res market release " + landName));
                    break;
                }
                boolean stage = true;
                if (player.isSneaking()) {
                    stage = false;
                }
                Bukkit.dispatchCommand((CommandSender)player, (String)("res market rent " + landName + " " + stage));
                break;
            }
            if (event.getAction() != Action.LEFT_CLICK_BLOCK || !ForRent || !res.isRented() || !Residence.getRentManager().getRentingPlayer(res).equals(player.getName())) continue;
            Residence.getRentManager().payRent(player, res, false);
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=1)
    public void onSignCreate(SignChangeEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) {
            return;
        }
        Sign sign = (Sign)block.getState();
        if (!ChatColor.stripColor((String)event.getLine(0)).equalsIgnoreCase(Residence.msg(lm.Sign_TopLine, new Object[0]))) {
            return;
        }
        Signs signInfo = new Signs();
        Location loc = sign.getLocation();
        String landName = null;
        Player player = event.getPlayer();
        ClaimedResidence res = null;
        if (!event.getLine(1).equalsIgnoreCase("")) {
            String resname = event.getLine(1);
            if (!event.getLine(2).equalsIgnoreCase("")) {
                resname = String.valueOf(resname) + "." + event.getLine(2);
            }
            if (!event.getLine(3).equalsIgnoreCase("")) {
                resname = String.valueOf(resname) + "." + event.getLine(3);
            }
            if ((res = Residence.getResidenceManager().getByName(resname)) == null) {
                Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                return;
            }
            landName = res.getName();
        } else {
            res = Residence.getResidenceManager().getByLoc(loc);
            landName = Residence.getResidenceManager().getNameByLoc(loc);
        }
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        final ClaimedResidence residence = res;
        boolean ForSale = Residence.getTransactionManager().isForSale(landName);
        boolean ForRent = Residence.getRentManager().isForRent(landName);
        int category = 1;
        if (Residence.getSignUtil().getSigns().GetAllSigns().size() > 0) {
            category = Residence.getSignUtil().getSigns().GetAllSigns().get(Residence.getSignUtil().getSigns().GetAllSigns().size() - 1).GetCategory() + 1;
        }
        if (ForSale || ForRent) {
            signInfo.setCategory(category);
            signInfo.setResidence(res);
            signInfo.setLocation(loc);
            Residence.getSignUtil().getSigns().addSign(signInfo);
            Residence.getSignUtil().saveSigns();
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                Residence.getSignUtil().CheckSign(residence);
            }
        }, 5);
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=1)
    public void onSignDestroy(BlockBreakEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getBlock();
        if (block == null) {
            return;
        }
        if (!(block.getState() instanceof Sign)) {
            return;
        }
        Location loc = block.getLocation();
        for (Signs one : Residence.getSignUtil().getSigns().GetAllSigns()) {
            if (!one.GetLocation().getWorld().getName().equalsIgnoreCase(loc.getWorld().getName()) || one.GetLocation().getBlockX() != loc.getBlockX() || one.GetLocation().getBlockY() != loc.getBlockY() || one.GetLocation().getBlockZ() != loc.getBlockZ()) continue;
            Residence.getSignUtil().getSigns().removeSign(one);
            Residence.getSignUtil().saveSigns();
            break;
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String pname = event.getPlayer().getName();
        this.currentRes.remove(pname);
        this.lastUpdate.remove(pname);
        this.lastOutsideLoc.remove(pname);
        Residence.getChatManager().removeFromChannel(pname);
        Residence.getPlayerListener().removePlayerResidenceChat(pname);
        Residence.getOfflinePlayerMap().put(pname, (OfflinePlayer)event.getPlayer());
        if (Residence.getAutoSelectionManager().getList().containsKey(pname.toLowerCase())) {
            Residence.getAutoSelectionManager().getList().remove(pname);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onPlayerJoin(PlayerChangedWorldEvent event) {
        ClaimedResidence res;
        Player player = event.getPlayer();
        if (Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
            Residence.turnResAdminOn(player);
        }
        Residence.getPermissionManager().updateGroupNameForPlayer(player, true);
        FlagPermissions perms = Residence.getPermsByLocForPlayer(player.getLocation(), player);
        if (!(!player.getAllowFlight() && !player.isFlying() || !perms.has(Flags.nofly, false) || Residence.isResAdminOn(player) || player.hasPermission("residence.nofly.bypass") || (res = Residence.getResidenceManager().getByLoc(player.getLocation())) != null && res.isOwner(player))) {
            Location lc = player.getLocation();
            Location location = new Location(lc.getWorld(), lc.getX(), (double)lc.getBlockY(), lc.getZ());
            location.setPitch(lc.getPitch());
            location.setYaw(lc.getYaw());
            int from = location.getBlockY();
            int maxH = location.getWorld().getMaxHeight() - 1;
            int i = 0;
            while (i < maxH) {
                location.setY((double)(from - i));
                Block block = location.getBlock();
                if (!Residence.getNms().isEmptyBlock(block)) {
                    location.setY((double)(from - i + 1));
                    break;
                }
                if (location.getBlockY() <= 0) {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.nofly.getName(), location.getWorld().getName());
                    return;
                }
                ++i;
            }
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.nofly.getName(), location.getWorld().getName());
            player.teleport(location);
            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.lastUpdate.put(player.getName(), 0);
        if (Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
            Residence.turnResAdminOn(player);
        }
        this.handleNewLocation(player, player.getLocation(), true);
        Residence.getPlayerManager().playerJoin(player);
        Residence.getPermissionManager().updateGroupNameForPlayer(player, true);
        if (player.hasPermission("residence.versioncheck")) {
            Residence.getVersionChecker().VersionCheck(player);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerSpawn(PlayerRespawnEvent event) {
        if (Residence.isDisabledWorldListener(event.getRespawnLocation().getWorld())) {
            return;
        }
        Location loc = event.getRespawnLocation();
        Boolean bed = event.isBedSpawn();
        Player player = event.getPlayer();
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
        if (res == null) {
            return;
        }
        if (!res.getPermissions().playerHas(player, Flags.move, FlagPermissions.FlagCombo.OnlyFalse)) {
            return;
        }
        if (bed.booleanValue()) {
            loc = player.getWorld().getSpawnLocation();
        }
        if ((res = Residence.getResidenceManager().getByLoc(loc)) != null && res.getPermissions().playerHas(player, Flags.move, FlagPermissions.FlagCombo.OnlyFalse)) {
            loc = res.getOutsideFreeLoc(loc, player);
        }
        Residence.msg((CommandSender)player, lm.General_NoSpawn, new Object[0]);
        event.setRespawnLocation(loc);
    }

    private static boolean isContainer(Material mat, Block block) {
        if (!(FlagPermissions.getMaterialUseFlagList().containsKey((Object)mat) && FlagPermissions.getMaterialUseFlagList().get((Object)mat).equals(Flags.container.getName()) || Residence.getConfigManager().getCustomContainers().contains(block.getTypeId()))) {
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isCanUseEntity_RClickOnly(Material mat, Block block) {
        String string = mat.name();
        switch (string.hashCode()) {
            case -2063836287: {
                if (string.equals("REDSTONE_COMPARATOR")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -1892221527: {
                if (string.equals("DIODE_BLOCK_OFF")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -1629786261: {
                if (string.equals("DAYLIGHT_DETECTOR")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -1331883119: {
                if (string.equals("REDSTONE_COMPARATOR_OFF")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -1289889955: {
                if (string.equals("REDSTONE_COMPARATOR_ON")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -753776059: {
                if (string.equals("DIODE_BLOCK_ON")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -562844383: {
                if (string.equals("ITEM_FRAME")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -415523425: {
                if (string.equals("WORKBENCH")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -343861338: {
                if (string.equals("CAKE_BLOCK")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case -329276657: {
                if (string.equals("BED_BLOCK")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 62437548: {
                if (string.equals("ANVIL")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 65052267: {
                if (string.equals("DIODE")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 760322528: {
                if (string.equals("NOTE_BLOCK")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 867723593: {
                if (string.equals("DAYLIGHT_DETECTOR_INVERTED")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 1401892433: {
                if (string.equals("FLOWER_POT")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 1545025079: {
                if (string.equals("BREWING_STAND")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 1593989766: {
                if (string.equals("ENCHANTMENT_TABLE")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 1668377387: {
                if (string.equals("COMMAND")) return true;
                return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
            }
            case 1955250244: {
                if (!string.equals("BEACON")) return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
                return true;
            }
        }
        return Residence.getConfigManager().getCustomRightClick().contains(block.getTypeId());
    }

    private static boolean isCanUseEntity(Material mat, Block block) {
        if (!Residence.getNms().isCanUseEntity_BothClick(mat, block) && !ResidencePlayerListener.isCanUseEntity_RClickOnly(mat, block)) {
            return false;
        }
        return true;
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerFireInteract(PlayerInteractEvent event) {
        boolean hasplace;
        if (event.getPlayer() == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Block relativeBlock = block.getRelative(event.getBlockFace());
        if (relativeBlock == null) {
            return;
        }
        Player player = event.getPlayer();
        FlagPermissions perms = Residence.getPermsByLocForPlayer(block.getLocation(), player);
        if (relativeBlock.getType() == Material.FIRE && !(hasplace = perms.playerHas(player.getName(), player.getWorld().getName(), Flags.place, perms.playerHas(player.getName(), player.getWorld().getName(), Flags.build, true)))) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.build.getName());
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlatePress(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Material mat = block.getType();
        Player player = event.getPlayer();
        FlagPermissions perms = Residence.getPermsByLocForPlayer(block.getLocation(), player);
        String world = player.getWorld().getName();
        boolean resadmin2 = Residence.isResAdminOn(player);
        if (!resadmin2) {
            boolean hasuse = perms.playerHas(player.getName(), world, Flags.use, true);
            boolean haspressure = perms.playerHas(player.getName(), world, Flags.pressure, hasuse);
            if (!((hasuse || haspressure) && haspressure || mat != Material.STONE_PLATE && mat != Material.WOOD_PLATE && !Residence.getNms().isPlate(mat))) {
                event.setCancelled(true);
                return;
            }
        }
        if (!(perms.playerHas(player.getName(), world, Flags.trample, perms.playerHas(player.getName(), world, Flags.build, true)) || mat != Material.SOIL && mat != Material.SOUL_SAND)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onSelection(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player player = event.getPlayer();
        int heldItemId = player.getItemInHand().getTypeId();
        if (heldItemId != Residence.getConfigManager().getSelectionTooldID()) {
            return;
        }
        if (Residence.wepid == Residence.getConfigManager().getSelectionTooldID()) {
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE) {
            event.setCancelled(true);
        }
        boolean resadmin2 = Residence.isResAdminOn(player);
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (player.hasPermission("residence.select") || player.hasPermission("residence.create") && !player.isPermissionSet("residence.select") || group.canCreateResidences() && !player.isPermissionSet("residence.create") && !player.isPermissionSet("residence.select") || resadmin2) {
            Block block = event.getClickedBlock();
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                Location loc = block.getLocation();
                Residence.getSelectionManager().placeLoc1(player, loc, true);
                Residence.msg((CommandSender)player, lm.Select_PrimaryPoint, Residence.msg(lm.General_CoordsTop, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                event.setCancelled(true);
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && Residence.getNms().isMainHand(event)) {
                Location loc = block.getLocation();
                Residence.getSelectionManager().placeLoc2(player, loc, true);
                Residence.msg((CommandSender)player, lm.Select_SecondaryPoint, Residence.msg(lm.General_CoordsBottom, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                event.setCancelled(true);
            }
            if (Residence.getSelectionManager().hasPlacedBoth(player.getName())) {
                Residence.getSelectionManager().showSelectionInfoInActionBar(player);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onInfoCheck(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        int heldItemId = item.getTypeId();
        if (heldItemId != Residence.getConfigManager().getInfoToolID()) {
            return;
        }
        Location loc = block.getLocation();
        String res = Residence.getResidenceManager().getNameByLoc(loc);
        if (res != null) {
            Residence.getResidenceManager().printAreaInfo(res, (CommandSender)player, false);
        } else {
            Residence.msg((CommandSender)player, lm.Residence_NoResHere, new Object[0]);
        }
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack iih = Residence.getNms().itemInMainHand(player);
        Material heldItem = iih.getType();
        int heldItemId = iih.getTypeId();
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Material mat = block.getType();
        if (!(event.getAction() == Action.PHYSICAL || (ResidencePlayerListener.isContainer(mat, block) || ResidencePlayerListener.isCanUseEntity_RClickOnly(mat, block)) && event.getAction() == Action.RIGHT_CLICK_BLOCK || Residence.getNms().isCanUseEntity_BothClick(mat, block) || heldItemId == Residence.getConfigManager().getSelectionTooldID() || heldItemId == Residence.getConfigManager().getInfoToolID() || heldItem == Material.INK_SACK || Residence.getNms().isArmorStandMaterial(heldItem) || Residence.getNms().isBoat(heldItem))) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        String world = player.getWorld().getName();
        String permgroup = Residence.getPermissionManager().getGroupNameByPlayer(player);
        boolean resadmin2 = Residence.isResAdminOn(player);
        if (!resadmin2 && !Residence.getItemManager().isAllowed(heldItem, permgroup, world)) {
            Residence.msg((CommandSender)player, lm.General_ItemBlacklisted, new Object[0]);
            event.setCancelled(true);
            return;
        }
        if (resadmin2) {
            return;
        }
        int blockId = block.getTypeId();
        FlagPermissions perms = Residence.getPermsByLocForPlayer(block.getLocation(), player);
        if (heldItem != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (heldItem == Material.INK_SACK && (Residence.getNms().itemInMainHand(player).getData().getData() == 15 && block.getType() == Material.GRASS || iih.getData().getData() == 3 && blockId == 17 && (block.getData() == 3 || block.getData() == 7 || block.getData() == 11 || block.getData() == 15)) && !(perms = Residence.getPermsByLocForPlayer(block.getRelative(event.getBlockFace()).getLocation(), player)).playerHas(player.getName(), world, Flags.build, true)) {
                Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.build.getName());
                event.setCancelled(true);
                return;
            }
            if ((Residence.getNms().isArmorStandMaterial(heldItem) || Residence.getNms().isBoat(heldItem)) && !(perms = Residence.getPermsByLocForPlayer(block.getRelative(event.getBlockFace()).getLocation(), player)).playerHas(player.getName(), world, Flags.build, true)) {
                Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.build.getName());
                event.setCancelled(true);
                return;
            }
        }
        if (ResidencePlayerListener.isContainer(mat, block) || ResidencePlayerListener.isCanUseEntity(mat, block)) {
            boolean hasuse = perms.playerHas(player.getName(), world, Flags.use, true);
            for (Map.Entry<Material, String> checkMat : FlagPermissions.getMaterialUseFlagList().entrySet()) {
                if (mat != checkMat.getKey() || perms.playerHas(player.getName(), world, checkMat.getValue(), hasuse)) continue;
                if (hasuse || checkMat.getValue().equals(Flags.container.getName())) {
                    event.setCancelled(true);
                    Residence.msg((CommandSender)player, lm.Flag_Deny, checkMat.getValue());
                    return;
                }
                event.setCancelled(true);
                Residence.msg((CommandSender)player, lm.Flag_Deny, new Object[]{Flags.use});
                return;
            }
            if (Residence.getConfigManager().getCustomContainers().contains(blockId) && !perms.playerHas(player.getName(), world, Flags.container, hasuse)) {
                event.setCancelled(true);
                Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.container.getName());
                return;
            }
            if (Residence.getConfigManager().getCustomBothClick().contains(blockId) && !hasuse) {
                event.setCancelled(true);
                Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.use.getName());
                return;
            }
            if (Residence.getConfigManager().getCustomRightClick().contains(blockId) && event.getAction() == Action.RIGHT_CLICK_BLOCK && !hasuse) {
                event.setCancelled(true);
                Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.use.getName());
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerTradeEntity(PlayerInteractEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.VILLAGER) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
        if (res != null && res.getPermissions().playerHas(player, Flags.trade, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.trade.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerInteractWithHorse(PlayerInteractEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.HORSE) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.container, FlagPermissions.FlagCombo.OnlyFalse) && player.isSneaking()) {
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.container.getName(), res.getName());
            event.setCancelled(true);
        } else if (!res.isOwner(player) && !res.getPermissions().playerHas(player.getName(), Flags.riding, FlagPermissions.FlagCombo.TrueOrNone)) {
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.riding.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerInteractWithMinecartStorage(PlayerInteractEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.MINECART_CHEST && ent.getType() != EntityType.MINECART_HOPPER) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.container, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.container.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerInteractWithMinecart(PlayerInteractEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.MINECART && ent.getType() != EntityType.BOAT) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.riding, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.riding.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerDyeSheep(PlayerInteractEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.SHEEP) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.dye, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.dye.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerShearEntity(PlayerShearEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getEntity();
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.shear, FlagPermissions.FlagCombo.OnlyFalse)) {
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.shear.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerItemFrameInteract(PlayerInteractEntityEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (!(ent instanceof Hanging)) {
            return;
        }
        Hanging hanging = (Hanging)ent;
        if (hanging.getType() != EntityType.ITEM_FRAME) {
            return;
        }
        Material heldItem = Residence.getNms().itemInMainHand(player).getType();
        FlagPermissions perms = Residence.getPermsByLocForPlayer(ent.getLocation(), player);
        String world = player.getWorld().getName();
        String permgroup = Residence.getPermissionManager().getGroupNameByPlayer(player);
        if (!Residence.getItemManager().isAllowed(heldItem, permgroup, world)) {
            Residence.msg((CommandSender)player, lm.General_ItemBlacklisted, new Object[0]);
            event.setCancelled(true);
            return;
        }
        if (!perms.playerHas(player.getName(), world, Flags.container, perms.playerHas(player.getName(), world, Flags.use, true))) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.container.getName());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        FlagPermissions perms;
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        Location loc = event.getBlockClicked().getLocation();
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
        if (res != null) {
            if (Residence.getConfigManager().preventRentModify() && Residence.getConfigManager().enabledRentSystem() && Residence.getRentManager().isRented(res.getName())) {
                Residence.msg((CommandSender)player, lm.Rent_ModifyDeny, new Object[0]);
                event.setCancelled(true);
                return;
            }
            Material mat = event.getBucket();
            if (res.getPermissions().playerHas(player, Flags.build, FlagPermissions.FlagCombo.OnlyFalse) && Residence.getConfigManager().getNoPlaceWorlds().contains(loc.getWorld().getName()) && (mat == Material.LAVA_BUCKET || mat == Material.WATER_BUCKET)) {
                Residence.msg((CommandSender)player, lm.Flag_Deny, new Object[]{Flags.build});
                event.setCancelled(true);
                return;
            }
        }
        if (!(perms = Residence.getPermsByLocForPlayer(loc, player)).playerHas(player, Flags.build, true)) {
            Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.build.getName());
            event.setCancelled(true);
            return;
        }
        Material mat = event.getBucket();
        int level = Residence.getConfigManager().getPlaceLevel();
        if (res == null && Residence.getConfigManager().isNoLavaPlace() && loc.getBlockY() >= level - 1 && Residence.getConfigManager().getNoPlaceWorlds().contains(loc.getWorld().getName()) && mat == Material.LAVA_BUCKET) {
            if (!Residence.msg(lm.General_CantPlaceLava, new Object[0]).equalsIgnoreCase("")) {
                Residence.msg((CommandSender)player, lm.General_CantPlaceLava, level);
            }
            event.setCancelled(true);
            return;
        }
        if (res == null && Residence.getConfigManager().isNoWaterPlace() && loc.getBlockY() >= level - 1 && Residence.getConfigManager().getNoPlaceWorlds().contains(loc.getWorld().getName()) && mat == Material.WATER_BUCKET) {
            if (!Residence.msg(lm.General_CantPlaceWater, new Object[0]).equalsIgnoreCase("")) {
                Residence.msg((CommandSender)player, lm.General_CantPlaceWater, level);
            }
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (Residence.isResAdminOn(player)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getBlockClicked().getLocation());
        if (res != null && Residence.getConfigManager().preventRentModify() && Residence.getConfigManager().enabledRentSystem() && Residence.getRentManager().isRented(res.getName())) {
            Residence.msg((CommandSender)player, lm.Rent_ModifyDeny, new Object[0]);
            event.setCancelled(true);
            return;
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getBlockClicked().getLocation(), player);
        boolean hasdestroy = perms.playerHas(player, Flags.destroy, perms.playerHas(player, Flags.build, true));
        if (!hasdestroy) {
            Residence.msg((CommandSender)player, lm.Flag_Deny, Flags.destroy.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (player.hasMetadata("NPC")) {
            return;
        }
        Location loc = event.getTo();
        if (Residence.isResAdminOn(player)) {
            this.handleNewLocation(player, loc, false);
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
        if (res == null) {
            return;
        }
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND || event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL || event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) {
            if (res.getPermissions().playerHas(player, Flags.move, FlagPermissions.FlagCombo.OnlyFalse) && !res.isOwner(player)) {
                event.setCancelled(true);
                Residence.msg((CommandSender)player, lm.Residence_MoveDeny, res.getName());
                return;
            }
        } else if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && res.getPermissions().playerHas(player, Flags.enderpearl, FlagPermissions.FlagCombo.OnlyFalse)) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.enderpearl.getName(), res.getName());
            return;
        }
        if ((event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN || event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) && Residence.getConfigManager().isBlockAnyTeleportation() && !res.isOwner(player) && res.getPermissions().playerHas(player, Flags.tp, FlagPermissions.FlagCombo.OnlyFalse) && !player.hasPermission("residence.admin.tp")) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.General_TeleportDeny, res.getName());
            return;
        }
        if (Residence.getNms().isChorusTeleport(event.getCause()) && !res.isOwner(player) && res.getPermissions().playerHas(player, Flags.chorustp, FlagPermissions.FlagCombo.OnlyFalse) && !player.hasPermission("residence.admin.tp")) {
            event.setCancelled(true);
            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.chorustp.getName(), res.getName());
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (Residence.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        Player player = event.getEntity();
        if (player == null) {
            return;
        }
        if (player.hasMetadata("NPC")) {
            return;
        }
        Location loc = player.getLocation();
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
        if (res == null) {
            return;
        }
        if (res.getPermissions().has(Flags.keepinv, false)) {
            event.setKeepInventory(true);
        }
        if (res.getPermissions().has(Flags.keepexp, false)) {
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
        if (res.getPermissions().has(Flags.respawn, false) && Bukkit.getVersion().toString().contains("Spigot")) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    try {
                        event.getEntity().spigot().respawn();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }, 1);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceChange(ResidenceChangedEvent event) {
        ClaimedResidence res = event.getTo();
        ClaimedResidence ResOld = event.getFrom();
        Player player = event.getPlayer();
        if (res == null && ResOld != null) {
            if (ResOld.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue) || ResOld.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerTime();
            }
            if (ResOld.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue) || ResOld.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(0.2f);
            }
            if (ResOld.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue) || ResOld.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerWeather();
            }
            if (Residence.getVersionChecker().GetVersion() > 1900 && ResOld.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setGlowing(false);
            }
        }
        if (res != null && ResOld != null && res != ResOld) {
            if (Residence.getVersionChecker().GetVersion() > 1900) {
                if (res.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue)) {
                    player.setGlowing(true);
                } else if (ResOld.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue)) {
                    player.setGlowing(false);
                }
            }
            if (res.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerTime(6000, false);
            } else if (ResOld.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerTime();
            }
            if (res.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerTime(14000, false);
            } else if (ResOld.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerTime();
            }
            if (res.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(Residence.getConfigManager().getWalkSpeed1().floatValue());
            } else if (ResOld.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(0.2f);
            }
            if (res.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(Residence.getConfigManager().getWalkSpeed2().floatValue());
            } else if (ResOld.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(0.2f);
            }
            if (res.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerWeather(WeatherType.CLEAR);
            } else if (ResOld.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerWeather();
            }
            if (res.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            } else if (ResOld.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerWeather();
            }
        }
        if (res != null && ResOld == null) {
            if (Residence.getVersionChecker().GetVersion() > 1900 && res.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setGlowing(true);
            }
            if (res.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerTime(6000, false);
            }
            if (res.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerTime(14000, false);
            }
            if (res.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(Residence.getConfigManager().getWalkSpeed1().floatValue());
            }
            if (res.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(Residence.getConfigManager().getWalkSpeed2().floatValue());
            }
            if (res.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerWeather(WeatherType.CLEAR);
            }
            if (res.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        if (player.hasMetadata("NPC")) {
            return;
        }
        Location locfrom = event.getFrom();
        Location locto = event.getTo();
        if (locfrom.getBlockX() == locto.getBlockX() && locfrom.getBlockY() == locto.getBlockY() && locfrom.getBlockZ() == locto.getBlockZ()) {
            return;
        }
        String name = player.getName();
        if (name == null) {
            return;
        }
        Long last = this.lastUpdate.get(name);
        long now = System.currentTimeMillis();
        if (last != null && now - last < (long)Residence.getConfigManager().getMinMoveUpdateInterval()) {
            return;
        }
        this.lastUpdate.put(name, now);
        this.handleNewLocation(player, locto, true);
        if (!Residence.getTeleportDelayMap().isEmpty() && Residence.getConfigManager().getTeleportDelay() > 0 && Residence.getTeleportDelayMap().contains(player.getName())) {
            Residence.getTeleportDelayMap().remove(player.getName());
            Residence.msg((CommandSender)player, lm.General_TeleportCanceled, new Object[0]);
            if (Residence.getConfigManager().isTeleportTitleMessage()) {
                Residence.getAB().sendTitle(player, "", "");
            }
        }
    }

    public void handleNewLocation(final Player player, Location loc, boolean move) {
        ClaimedResidence res;
        String pname = player.getName();
        ClaimedResidence orres = res = Residence.getResidenceManager().getByLoc(loc);
        String areaname = null;
        String subzone2 = null;
        if (res != null) {
            areaname = res.getName();
            while (res.getSubzoneByLoc(loc) != null) {
                res = res.getSubzoneByLoc(player.getLocation());
                subzone2 = res.getName();
                areaname = String.valueOf(areaname) + "." + subzone2;
            }
        }
        ClaimedResidence ResOld = null;
        if (this.currentRes.containsKey(pname)) {
            ResOld = Residence.getResidenceManager().getByName(this.currentRes.get(pname));
            if (ResOld == null) {
                this.currentRes.remove(pname);
            } else if (res != null && ResOld.getName().equals(res.getName())) {
                if (player.isFlying() && res.getPermissions().playerHas(pname, Flags.nofly, FlagPermissions.FlagCombo.OnlyTrue) && !Residence.isResAdminOn(player) && !player.hasPermission("residence.nofly.bypass") && !res.isOwner(player)) {
                    Location lc = player.getLocation();
                    Location location = new Location(lc.getWorld(), lc.getX(), (double)lc.getBlockY(), lc.getZ());
                    location.setPitch(lc.getPitch());
                    location.setYaw(lc.getYaw());
                    int from = location.getBlockY();
                    int maxH = location.getWorld().getMaxHeight() - 1;
                    int i = 0;
                    while (i < maxH) {
                        location.setY((double)(from - i));
                        Block block = location.getBlock();
                        if (!Residence.getNms().isEmptyBlock(block)) {
                            location.setY((double)(from - i + 1));
                            break;
                        }
                        if (location.getBlockY() <= 0) {
                            Location lastLoc = this.lastOutsideLoc.get(pname);
                            if (lastLoc != null) {
                                player.teleport(lastLoc);
                            } else {
                                player.teleport(res.getOutsideFreeLoc(loc, player));
                            }
                            Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.nofly.getName(), orres.getName());
                            return;
                        }
                        ++i;
                    }
                    Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.nofly.getName(), orres.getName());
                    player.teleport(location);
                    player.setFlying(false);
                    player.setAllowFlight(false);
                }
                this.lastOutsideLoc.put(pname, loc);
                return;
            }
        }
        if (!Residence.getAutoSelectionManager().getList().isEmpty()) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    Residence.getAutoSelectionManager().UpdateSelection(player);
                }
            });
        }
        if (res == null) {
            this.lastOutsideLoc.put(pname, loc);
            if (ResOld != null) {
                String leave = ResOld.getLeaveMessage();
                ResidenceChangedEvent chgEvent = new ResidenceChangedEvent(ResOld, null, player);
                Residence.getServ().getPluginManager().callEvent((Event)chgEvent);
                if (leave != null && !leave.equals("")) {
                    if (Residence.getConfigManager().useActionBar()) {
                        Residence.getAB().send(player, (Object)ChatColor.YELLOW + this.insertMessages(player, ResOld.getName(), ResOld, leave));
                    } else {
                        Residence.msg(player, (Object)ChatColor.YELLOW + this.insertMessages(player, ResOld.getName(), ResOld, leave));
                    }
                }
                this.currentRes.remove(pname);
            }
            return;
        }
        if (move) {
            if (res.getPermissions().playerHas(pname, Flags.move, FlagPermissions.FlagCombo.OnlyFalse) && !Residence.isResAdminOn(player) && !res.isOwner(player) && !player.hasPermission("residence.admin.move")) {
                ClaimedResidence preRes;
                Location lastLoc = this.lastOutsideLoc.get(pname);
                if (Residence.getConfigManager().BounceAnimation()) {
                    Visualizer v = new Visualizer(player);
                    v.setErrorAreas(res);
                    v.setOnce(true);
                    Residence.getSelectionManager().showBounds(player, v);
                }
                if ((preRes = Residence.getResidenceManager().getByLoc(lastLoc)) != null && preRes.getPermissions().playerHas(pname, Flags.tp, FlagPermissions.FlagCombo.OnlyFalse) && !player.hasPermission("residence.admin.tp")) {
                    Location newLoc = res.getOutsideFreeLoc(loc, player);
                    player.teleport(newLoc);
                } else if (lastLoc != null) {
                    player.teleport(lastLoc);
                } else {
                    Location newLoc = res.getOutsideFreeLoc(loc, player);
                    player.teleport(newLoc);
                }
                if (Residence.getConfigManager().useActionBar()) {
                    Residence.getAB().send(player, Residence.msg(lm.Residence_MoveDeny, orres.getName()));
                } else {
                    Residence.msg((CommandSender)player, lm.Residence_MoveDeny, orres.getName());
                }
                return;
            }
            if (player.isFlying() && res.getPermissions().playerHas(pname, Flags.nofly, FlagPermissions.FlagCombo.OnlyTrue) && !Residence.isResAdminOn(player) && !player.hasPermission("residence.nofly.bypass") && !res.isOwner(player)) {
                Location lc = player.getLocation();
                Location location = new Location(lc.getWorld(), lc.getX(), (double)lc.getBlockY(), lc.getZ());
                location.setPitch(lc.getPitch());
                location.setYaw(lc.getYaw());
                int from = location.getBlockY();
                int maxH = location.getWorld().getMaxHeight() - 1;
                int i = 0;
                while (i < maxH) {
                    location.setY((double)(from - i));
                    Block block = location.getBlock();
                    if (!Residence.getNms().isEmptyBlock(block)) {
                        location.setY((double)(from - i + 1));
                        break;
                    }
                    if (location.getBlockY() <= 0) {
                        Location lastLoc = this.lastOutsideLoc.get(pname);
                        if (lastLoc != null) {
                            player.teleport(lastLoc);
                        } else {
                            player.teleport(res.getOutsideFreeLoc(loc, player));
                        }
                        Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.nofly.getName(), orres.getName());
                        return;
                    }
                    ++i;
                }
                Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.nofly.getName(), orres.getName());
                player.teleport(location);
                player.setFlying(false);
                player.setAllowFlight(false);
            }
        }
        this.lastOutsideLoc.put(pname, loc);
        if (!this.currentRes.containsKey(pname) || ResOld != res) {
            this.currentRes.put(pname, areaname);
            ClaimedResidence chgFrom = null;
            if (ResOld != res && ResOld != null) {
                String leave = ResOld.getLeaveMessage();
                chgFrom = ResOld;
                if (leave != null && !leave.equals("") && ResOld != res.getParent()) {
                    if (Residence.getConfigManager().useActionBar()) {
                        Residence.getAB().send(player, (Object)ChatColor.YELLOW + this.insertMessages(player, ResOld.getName(), ResOld, leave));
                    } else {
                        Residence.msg(player, (Object)ChatColor.YELLOW + this.insertMessages(player, ResOld.getName(), ResOld, leave));
                    }
                }
            }
            String enterMessage = res.getEnterMessage();
            ResidenceChangedEvent chgEvent = new ResidenceChangedEvent(chgFrom, res, player);
            Residence.getServ().getPluginManager().callEvent((Event)chgEvent);
            if (ResOld == null || res != ResOld.getParent()) {
                if (Residence.getConfigManager().isExtraEnterMessage() && !res.isOwner(player) && (Residence.getRentManager().isForRent(areaname) || Residence.getTransactionManager().isForSale(areaname))) {
                    if (Residence.getRentManager().isForRent(areaname) && !Residence.getRentManager().isRented(areaname)) {
                        RentableLand rentable = Residence.getRentManager().getRentableLand(areaname);
                        if (rentable != null) {
                            Residence.getAB().send(player, Residence.msg(lm.Residence_CanBeRented, areaname, rentable.cost, rentable.days));
                        }
                    } else if (Residence.getTransactionManager().isForSale(areaname) && !res.isOwner(player)) {
                        int sale = Residence.getTransactionManager().getSaleAmount(areaname);
                        Residence.getAB().send(player, Residence.msg(lm.Residence_CanBeBought, areaname, sale));
                    }
                } else if (enterMessage != null && !enterMessage.equals("")) {
                    if (Residence.getConfigManager().useActionBar()) {
                        Residence.getAB().send(player, (Object)ChatColor.YELLOW + this.insertMessages(player, areaname, res, enterMessage));
                    } else {
                        Residence.msg(player, (Object)ChatColor.YELLOW + this.insertMessages(player, areaname, res, enterMessage));
                    }
                }
            }
        }
    }

    public String insertMessages(Player player, String areaname, ClaimedResidence res, String message2) {
        try {
            message2 = message2.replaceAll("%player", player.getName());
            message2 = message2.replaceAll("%owner", res.getPermissions().getOwner());
            message2 = message2.replaceAll("%residence", areaname);
        }
        catch (Exception ex) {
            return "";
        }
        return message2;
    }

    public void doHeals() {
        try {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                double health;
                Player damage;
                String resname = Residence.getPlayerListener().getCurrentResidenceName(player.getName());
                ClaimedResidence res = null;
                if (resname == null || !(res = Residence.getResidenceManager().getByName(resname)).getPermissions().has(Flags.healing, false) || (health = (damage = player).getHealth()) >= damage.getMaxHealth() || player.isDead()) continue;
                player.setHealth(health + 1.0);
            }
        }
        catch (Exception player) {
            // empty catch block
        }
    }

    public void feed() {
        try {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                int food;
                ClaimedResidence res;
                String resname = Residence.getPlayerListener().getCurrentResidenceName(player.getName());
                if (resname == null || !(res = Residence.getResidenceManager().getByName(resname)).getPermissions().has(Flags.feed, false) || (food = player.getFoodLevel()) >= 20 || player.isDead()) continue;
                player.setFoodLevel(food + 1);
            }
        }
        catch (Exception player) {
            // empty catch block
        }
    }

    public void DespawnMobs() {
        try {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                String resname = Residence.getPlayerListener().getCurrentResidenceName(player.getName());
                if (resname == null) continue;
                ClaimedResidence res = null;
                res = Residence.getResidenceManager().getByName(resname);
                if (!res.getPermissions().has(Flags.nomobs, false)) continue;
                List entities = Bukkit.getServer().getWorld(res.getWorld()).getEntities();
                for (Entity ent : entities) {
                    if (!ResidenceEntityListener.isMonster(ent) || !res.containsLoc(ent.getLocation())) continue;
                    ent.remove();
                }
            }
        }
        catch (Exception player) {
            // empty catch block
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        String pname = event.getPlayer().getName();
        if (!this.chatenabled || !this.playerToggleChat.contains(pname)) {
            return;
        }
        ChatChannel channel = Residence.getChatManager().getPlayerChannel(pname);
        if (channel != null) {
            channel.chat(pname, event.getMessage());
        }
        event.setCancelled(true);
    }

    public void tooglePlayerResidenceChat(Player player, String residence) {
        String pname = player.getName();
        this.playerToggleChat.add(pname);
        Residence.msg((CommandSender)player, lm.Chat_ChatChannelChange, residence);
    }

    public void removePlayerResidenceChat(String pname) {
        this.playerToggleChat.remove(pname);
        Player player = Bukkit.getPlayer((String)pname);
        if (player != null) {
            Residence.msg((CommandSender)player, lm.Chat_ChatChannelLeave, new Object[0]);
        }
    }

    public void removePlayerResidenceChat(Player player) {
        String pname = player.getName();
        this.playerToggleChat.remove(pname);
        Residence.msg((CommandSender)player, lm.Chat_ChatChannelLeave, new Object[0]);
    }

    public String getCurrentResidenceName(String player) {
        return this.currentRes.get(player);
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[FlagPermissions.FlagState.values().length];
        try {
            arrn[FlagPermissions.FlagState.FALSE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagPermissions.FlagState.INVALID.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagPermissions.FlagState.NEITHER.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagPermissions.FlagState.TRUE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState = arrn;
        return $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState;
    }

}

