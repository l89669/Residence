/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.griefcraft.cache.ProtectionCache
 *  com.griefcraft.lwc.LWC
 *  com.griefcraft.model.Protection
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitTask
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceInterface;
import com.bekvon.bukkit.residence.containers.AutoSelector;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.Visualizer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.ResidenceBank;
import com.bekvon.bukkit.residence.economy.TransactionManager;
import com.bekvon.bukkit.residence.economy.rent.RentableLand;
import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceRenameEvent;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.utils.GetTime;
import com.griefcraft.cache.ProtectionCache;
import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;

public class ResidenceManager
implements ResidenceInterface {
    protected SortedMap<String, ClaimedResidence> residences = new TreeMap<String, ClaimedResidence>();
    protected Map<String, Map<ChunkRef, List<String>>> chunkResidences = new HashMap<String, Map<ChunkRef, List<String>>>();
    protected List<ClaimedResidence> shops = new ArrayList<ClaimedResidence>();
    private Residence plugin;

    public ResidenceManager(Residence plugin) {
        this.plugin = plugin;
    }

    public boolean isOwnerOfLocation(Player player, Location loc) {
        ClaimedResidence res = this.getByLoc(loc);
        if (res != null && res.isOwner(player)) {
            return true;
        }
        return false;
    }

    @Override
    public ClaimedResidence getByLoc(Location loc) {
        if (loc == null) {
            return null;
        }
        ClaimedResidence res = null;
        String world = loc.getWorld().getName();
        ChunkRef chunk = new ChunkRef(loc);
        if (!this.chunkResidences.containsKey(world)) {
            return null;
        }
        Map<ChunkRef, List<String>> ChunkMap = this.chunkResidences.get(world);
        if (ChunkMap.containsKey(chunk)) {
            for (String key : ChunkMap.get(chunk)) {
                ClaimedResidence entry = this.residences.get(key);
                if (entry == null || !entry.containsLoc(loc)) continue;
                res = entry;
                break;
            }
        }
        if (res == null) {
            return null;
        }
        ClaimedResidence subres = res.getSubzoneByLoc(loc);
        if (subres == null) {
            return res;
        }
        return subres;
    }

    @Override
    public ClaimedResidence getByName(String name) {
        if (name == null) {
            return null;
        }
        String[] split = name.split("\\.");
        if (split.length == 1) {
            return this.residences.get(name.toLowerCase());
        }
        ClaimedResidence res = this.residences.get(split[0].toLowerCase());
        int i = 1;
        while (i < split.length) {
            if (res == null) {
                return null;
            }
            res = res.getSubzone(split[i].toLowerCase());
            ++i;
        }
        return res;
    }

    @Override
    public String getNameByLoc(Location loc) {
        ClaimedResidence res = this.getByLoc(loc);
        if (res == null) {
            return null;
        }
        String name = res.getName();
        if (name == null) {
            return null;
        }
        String szname = res.getSubzoneNameByLoc(loc);
        if (szname != null) {
            return String.valueOf(name) + "." + szname;
        }
        return name;
    }

    @Override
    public String getNameByRes(ClaimedResidence res) {
        Set<Map.Entry<String, ClaimedResidence>> set2 = this.residences.entrySet();
        for (Map.Entry<String, ClaimedResidence> check2 : set2) {
            if (check2.getValue() == res) {
                return check2.getValue().getResidenceName();
            }
            String n = check2.getValue().getSubzoneNameByRes(res);
            if (n == null) continue;
            return String.valueOf(check2.getValue().getResidenceName()) + "." + n;
        }
        return null;
    }

    @Override
    public String getSubzoneNameByRes(ClaimedResidence res) {
        Set<Map.Entry<String, ClaimedResidence>> set2 = this.residences.entrySet();
        for (Map.Entry<String, ClaimedResidence> check2 : set2) {
            if (check2.getValue() == res) {
                return check2.getKey();
            }
            String n = check2.getValue().getSubzoneNameByRes(res);
            if (n == null) continue;
            return n;
        }
        return null;
    }

    @Override
    public void addShop(String resName) {
        ClaimedResidence res = this.getByName(resName);
        if (res != null) {
            this.shops.add(res);
        }
    }

    @Override
    public void addShop(ClaimedResidence res) {
        this.shops.add(res);
    }

    @Override
    public void removeShop(ClaimedResidence res) {
        this.shops.remove(res);
    }

    @Override
    public void removeShop(String resName) {
        for (ClaimedResidence one : this.shops) {
            if (!one.getName().equalsIgnoreCase(resName)) continue;
            this.shops.remove(one);
            break;
        }
    }

    @Override
    public List<ClaimedResidence> getShops() {
        return this.shops;
    }

    @Override
    public boolean addResidence(String name, Location loc1, Location loc2) {
        return this.addResidence(name, Residence.getServerLandname(), loc1, loc2);
    }

    @Override
    public boolean addResidence(String name, String owner, Location loc1, Location loc2) {
        return this.addResidence(null, owner, name, loc1, loc2, true);
    }

    @Override
    public boolean addResidence(Player player, String name, Location loc1, Location loc2, boolean resadmin2) {
        return this.addResidence(player, player.getName(), name, loc1, loc2, resadmin2);
    }

    public boolean addResidence(Player player, String owner, String name, Location loc1, Location loc2, boolean resadmin2) {
        boolean createpermission;
        double chargeamount;
        if (!Residence.validName(name)) {
            Residence.msg((CommandSender)player, lm.Invalid_NameCharacters, new Object[0]);
            return false;
        }
        if (loc1 == null || loc2 == null || !loc1.getWorld().getName().equals(loc2.getWorld().getName())) {
            Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
            return false;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        boolean bl = createpermission = group.canCreateResidences() && (player == null || player.hasPermission("residence.create"));
        if (!createpermission && !resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return false;
        }
        if (rPlayer.getResAmount() >= rPlayer.getMaxRes() && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Residence_TooMany, new Object[0]);
            return false;
        }
        CuboidArea newArea = new CuboidArea(loc1, loc2);
        ClaimedResidence newRes = new ClaimedResidence(owner, loc1.getWorld().getName(), this.plugin);
        newRes.getPermissions().applyDefaultFlags();
        newRes.setEnterMessage(group.getDefaultEnterMessage());
        newRes.setLeaveMessage(group.getDefaultLeaveMessage());
        newRes.setName(name);
        newRes.setCreateTime();
        if (this.residences.containsKey(name.toLowerCase())) {
            Residence.msg((CommandSender)player, lm.Residence_AlreadyExists, this.residences.get(name.toLowerCase()).getResidenceName());
            return false;
        }
        newRes.BlockSellPrice = group.getSellPerBlock();
        if (!newRes.addArea(player, newArea, "main", resadmin2, false)) {
            return false;
        }
        ResidenceCreationEvent resevent = new ResidenceCreationEvent(player, name, newRes, newArea);
        Residence.getServ().getPluginManager().callEvent((Event)resevent);
        if (resevent.isCancelled()) {
            return false;
        }
        if (!newRes.isSubzone() && Residence.getConfigManager().enableEconomy() && !resadmin2 && !TransactionManager.chargeEconomyMoney(player, chargeamount = Math.ceil((double)newArea.getSize() * group.getCostPerBlock()))) {
            return false;
        }
        this.residences.put(name.toLowerCase(), newRes);
        this.calculateChunks(name);
        Residence.getLeaseManager().removeExpireTime(name);
        Residence.getPlayerManager().addResidence(newRes.getOwner(), newRes);
        if (player != null) {
            Visualizer v = new Visualizer(player);
            v.setAreas(newArea);
            Residence.getSelectionManager().showBounds(player, v);
            Residence.getAutoSelectionManager().getList().remove(player.getName().toLowerCase());
            Residence.msg((CommandSender)player, lm.Area_Create, "main");
            Residence.msg((CommandSender)player, lm.Residence_Create, name);
        }
        if (Residence.getConfigManager().useLeases()) {
            if (player != null) {
                Residence.getLeaseManager().setExpireTime(player, name, group.getLeaseGiveTime());
            } else {
                Residence.getLeaseManager().setExpireTime(name, group.getLeaseGiveTime());
            }
        }
        return true;
    }

    public void listResidences(CommandSender sender) {
        this.listResidences(sender, sender.getName(), 1);
    }

    public void listResidences(CommandSender sender, boolean resadmin2) {
        this.listResidences(sender, sender.getName(), 1, false, false, resadmin2);
    }

    public void listResidences(CommandSender sender, String targetplayer, boolean showhidden) {
        this.listResidences(sender, targetplayer, 1, showhidden, false, showhidden);
    }

    public void listResidences(CommandSender sender, String targetplayer, int page) {
        this.listResidences(sender, targetplayer, page, false, false, false);
    }

    public void listResidences(CommandSender sender, int page, boolean showhidden) {
        this.listResidences(sender, sender.getName(), page, showhidden, false, showhidden);
    }

    public void listResidences(CommandSender sender, int page, boolean showhidden, boolean onlyHidden) {
        this.listResidences(sender, sender.getName(), page, showhidden, onlyHidden, showhidden);
    }

    public void listResidences(CommandSender sender, String string, int page, boolean showhidden) {
        this.listResidences(sender, string, page, showhidden, false, showhidden);
    }

    public void listResidences(CommandSender sender, String targetplayer, int page, boolean showhidden, boolean onlyHidden, boolean resadmin2) {
        this.listResidences(sender, targetplayer, page, showhidden, onlyHidden, resadmin2, null);
    }

    public void listResidences(CommandSender sender, String targetplayer, int page, boolean showhidden, boolean onlyHidden, boolean resadmin2, World world) {
        if (targetplayer == null) {
            targetplayer = sender.getName();
        }
        if (showhidden && !Residence.isResAdminOn(sender) && !sender.getName().equalsIgnoreCase(targetplayer)) {
            showhidden = false;
        } else if (sender.getName().equalsIgnoreCase(targetplayer)) {
            showhidden = true;
        }
        boolean hidden = showhidden;
        TreeMap<String, ClaimedResidence> ownedResidences = Residence.getPlayerManager().getResidencesMap(targetplayer, hidden, onlyHidden, world);
        ownedResidences.putAll(Residence.getRentManager().getRentsMap(targetplayer, onlyHidden, world));
        Residence.getInfoPageManager().printListInfo(sender, targetplayer, ownedResidences, page, resadmin2);
    }

    public void listAllResidences(CommandSender sender, int page) {
        this.listAllResidences(sender, page, false);
    }

    public void listAllResidences(CommandSender sender, int page, boolean showhidden, World world) {
        TreeMap<String, ClaimedResidence> list2 = this.getFromAllResidencesMap(showhidden, false, world);
        Residence.getInfoPageManager().printListInfo(sender, null, list2, page, showhidden);
    }

    public void listAllResidences(CommandSender sender, int page, boolean showhidden) {
        this.listAllResidences(sender, page, showhidden, false);
    }

    public void listAllResidences(CommandSender sender, int page, boolean showhidden, boolean onlyHidden) {
        TreeMap<String, ClaimedResidence> list2 = this.getFromAllResidencesMap(showhidden, onlyHidden, null);
        Residence.getInfoPageManager().printListInfo(sender, null, list2, page, showhidden);
    }

    public String[] getResidenceList() {
        return this.getResidenceList(true, true).toArray(new String[0]);
    }

    public Map<String, ClaimedResidence> getResidenceMapList(String targetplayer, boolean showhidden) {
        HashMap<String, ClaimedResidence> temp = new HashMap<String, ClaimedResidence>();
        for (Map.Entry<String, ClaimedResidence> res : this.residences.entrySet()) {
            if (!res.getValue().isOwner(targetplayer)) continue;
            boolean hidden = res.getValue().getPermissions().has("hidden", false);
            if (!showhidden && (showhidden || hidden)) continue;
            temp.put(res.getValue().getName().toLowerCase(), res.getValue());
        }
        return temp;
    }

    public ArrayList<String> getResidenceList(boolean showhidden, boolean showsubzones) {
        return this.getResidenceList(null, showhidden, showsubzones, false);
    }

    public ArrayList<String> getResidenceList(String targetplayer, boolean showhidden, boolean showsubzones) {
        return this.getResidenceList(targetplayer, showhidden, showsubzones, false, false);
    }

    public ArrayList<String> getResidenceList(String targetplayer, boolean showhidden, boolean showsubzones, boolean onlyHidden) {
        return this.getResidenceList(targetplayer, showhidden, showsubzones, false, onlyHidden);
    }

    public ArrayList<String> getResidenceList(String targetplayer, boolean showhidden, boolean showsubzones, boolean formattedOutput, boolean onlyHidden) {
        ArrayList<String> list2 = new ArrayList<String>();
        for (Map.Entry<String, ClaimedResidence> res : this.residences.entrySet()) {
            this.getResidenceList(targetplayer, showhidden, showsubzones, "", res.getKey(), res.getValue(), list2, formattedOutput, onlyHidden);
        }
        return list2;
    }

    public ArrayList<ClaimedResidence> getFromAllResidences(boolean showhidden, boolean onlyHidden, World world) {
        ArrayList<ClaimedResidence> list2 = new ArrayList<ClaimedResidence>();
        for (Map.Entry<String, ClaimedResidence> res : this.residences.entrySet()) {
            boolean hidden = res.getValue().getPermissions().has("hidden", false);
            if (onlyHidden && !hidden || world != null && !world.getName().equalsIgnoreCase(res.getValue().getWorld()) || !showhidden && (showhidden || hidden)) continue;
            list2.add(res.getValue());
        }
        return list2;
    }

    public TreeMap<String, ClaimedResidence> getFromAllResidencesMap(boolean showhidden, boolean onlyHidden, World world) {
        TreeMap<String, ClaimedResidence> list2 = new TreeMap<String, ClaimedResidence>();
        for (Map.Entry<String, ClaimedResidence> res : this.residences.entrySet()) {
            boolean hidden = res.getValue().getPermissions().has("hidden", false);
            if (onlyHidden && !hidden || world != null && !world.getName().equalsIgnoreCase(res.getValue().getWorld()) || !showhidden && (showhidden || hidden)) continue;
            list2.put(res.getKey(), res.getValue());
        }
        return list2;
    }

    private void getResidenceList(String targetplayer, boolean showhidden, boolean showsubzones, String parentzone, String resname, ClaimedResidence res, ArrayList<String> list2, boolean formattedOutput, boolean onlyHidden) {
        boolean hidden = res.getPermissions().has("hidden", false);
        if (onlyHidden && !hidden) {
            return;
        }
        if (showhidden || !showhidden && !hidden) {
            if (targetplayer == null || res.getPermissions().getOwner().equals(targetplayer)) {
                if (formattedOutput) {
                    list2.add(String.valueOf(Residence.msg(lm.Residence_List, parentzone, resname, res.getWorld())) + (hidden ? Residence.msg(lm.Residence_Hidden, new Object[0]) : ""));
                } else {
                    list2.add(String.valueOf(parentzone) + resname);
                }
            }
            if (showsubzones) {
                for (Map.Entry<String, ClaimedResidence> sz : res.subzones.entrySet()) {
                    this.getResidenceList(targetplayer, showhidden, showsubzones, String.valueOf(parentzone) + resname + ".", sz.getKey(), sz.getValue(), list2, formattedOutput, onlyHidden);
                }
            }
        }
    }

    public String checkAreaCollision(CuboidArea newarea, ClaimedResidence parentResidence) {
        Set<Map.Entry<String, ClaimedResidence>> set2 = this.residences.entrySet();
        for (Map.Entry<String, ClaimedResidence> entry : set2) {
            ClaimedResidence check2 = entry.getValue();
            if (check2 == parentResidence || !check2.checkCollision(newarea)) continue;
            return entry.getKey();
        }
        return null;
    }

    public void removeResidence(String name) {
        this.removeResidence((Player)null, name, true);
    }

    public void removeResidence(CommandSender sender, String name, boolean resadmin2) {
        if (sender instanceof Player) {
            this.removeResidence((Player)sender, name, resadmin2);
        } else {
            this.removeResidence((Player)null, name, true);
        }
    }

    public void removeResidence(Player player, String name, boolean resadmin2) {
        ClaimedResidence rented;
        int n;
        int n2;
        String[] arrstring;
        ClaimedResidence res = this.getByName(name);
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        name = res.getName();
        if (Residence.getConfigManager().isRentPreventRemoval() && !resadmin2 && (rented = res.getRentedSubzone()) != null) {
            Residence.msg((CommandSender)player, lm.Residence_CantRemove, res.getName(), rented.getName(), rented.getRentedLand().player);
            return;
        }
        if (!(player == null || resadmin2 || res.getPermissions().hasResidencePermission((CommandSender)player, true) || resadmin2 || res.getParent() == null || res.getParent().isOwner(player))) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return;
        }
        ResidenceDeleteEvent resevent = new ResidenceDeleteEvent(player, res, player == null ? ResidenceDeleteEvent.DeleteCause.OTHER : ResidenceDeleteEvent.DeleteCause.PLAYER_DELETE);
        Residence.getServ().getPluginManager().callEvent((Event)resevent);
        if (resevent.isCancelled()) {
            return;
        }
        ClaimedResidence parent = res.getParent();
        if (parent == null) {
            this.removeChunkList(name);
            this.residences.remove(name.toLowerCase());
            if (Residence.getConfigManager().isUseClean() && Residence.getConfigManager().getCleanWorlds().contains(res.getWorld())) {
                arrstring = res.getAreaArray();
                n = arrstring.length;
                n2 = 0;
                while (n2 < n) {
                    String area2 = arrstring[n2];
                    Location low = area2.getLowLoc();
                    Location high = area2.getHighLoc();
                    if (high.getBlockY() > Residence.getConfigManager().getCleanLevel()) {
                        if (low.getBlockY() < Residence.getConfigManager().getCleanLevel()) {
                            low.setY((double)Residence.getConfigManager().getCleanLevel());
                        }
                        World world = low.getWorld();
                        Location temploc = new Location(world, (double)low.getBlockX(), (double)low.getBlockY(), (double)low.getBlockZ());
                        int x = low.getBlockX();
                        while (x <= high.getBlockX()) {
                            temploc.setX((double)x);
                            int y = low.getBlockY();
                            while (y <= high.getBlockY()) {
                                temploc.setY((double)y);
                                int z = low.getBlockZ();
                                while (z <= high.getBlockZ()) {
                                    temploc.setZ((double)z);
                                    if (Residence.getConfigManager().getCleanBlocks().contains(temploc.getBlock().getTypeId())) {
                                        temploc.getBlock().setTypeId(0);
                                    }
                                    ++z;
                                }
                                ++y;
                            }
                            ++x;
                        }
                    }
                    ++n2;
                }
            }
            if (Residence.getConfigManager().isRemoveLwcOnDelete()) {
                this.removeLwcFromResidence(player, res);
            }
            Residence.msg((CommandSender)player, lm.Residence_Remove, name);
        } else {
            String[] split = name.split("\\.");
            if (player != null) {
                parent.removeSubzone(player, split[split.length - 1], true);
            } else {
                parent.removeSubzone(split[split.length - 1]);
            }
        }
        arrstring = res.getSubzoneList();
        n = arrstring.length;
        n2 = 0;
        while (n2 < n) {
            String oneSub = arrstring[n2];
            Residence.getPlayerManager().removeResFromPlayer(res.getOwner(), String.valueOf(name) + "." + oneSub);
            Residence.getRentManager().removeRentable(String.valueOf(name) + "." + oneSub);
            Residence.getTransactionManager().removeFromSale(String.valueOf(name) + "." + oneSub);
            ++n2;
        }
        Residence.getPlayerManager().removeResFromPlayer(res.getOwner(), name);
        Residence.getRentManager().removeRentable(name);
        Residence.getTransactionManager().removeFromSale(name);
        if (parent == null && Residence.getConfigManager().enableEconomy() && Residence.getConfigManager().useResMoneyBack()) {
            int chargeamount = (int)Math.ceil((double)res.getAreaArray()[0].getSize() * res.getBlockSellPrice());
            TransactionManager.giveEconomyMoney(player, chargeamount);
        }
    }

    public void removeLwcFromResidence(final Player player, final ClaimedResidence res) {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                long time = System.currentTimeMillis();
                LWC lwc = Residence.getLwc();
                if (lwc == null) {
                    return;
                }
                if (res == null) {
                    return;
                }
                int i = 0;
                ProtectionCache cache = lwc.getProtectionCache();
                List<Material> list2 = Residence.getConfigManager().getLwcMatList();
                try {
                    CuboidArea[] arrcuboidArea = res.getAreaArray();
                    int n = arrcuboidArea.length;
                    int n2 = 0;
                    while (n2 < n) {
                        CuboidArea area2 = arrcuboidArea[n2];
                        Location low = area2.getLowLoc();
                        Location high = area2.getHighLoc();
                        World world = low.getWorld();
                        int x = low.getBlockX();
                        while (x <= high.getBlockX()) {
                            int y = low.getBlockY();
                            while (y <= high.getBlockY()) {
                                int z = low.getBlockZ();
                                while (z <= high.getBlockZ()) {
                                    Protection prot;
                                    Block b = world.getBlockAt(x, y, z);
                                    if (list2.contains((Object)b.getType()) && (prot = cache.getProtection(b)) != null) {
                                        prot.remove();
                                        ++i;
                                    }
                                    ++z;
                                }
                                ++y;
                            }
                            ++x;
                        }
                        ++n2;
                    }
                }
                catch (Exception area2) {
                    // empty catch block
                }
                if (i > 0) {
                    Residence.msg((CommandSender)player, lm.Residence_LwcRemoved, i, System.currentTimeMillis() - time);
                }
            }
        });
    }

    public void removeAllByOwner(String owner) {
        ArrayList<String> list2 = Residence.getPlayerManager().getResidenceList(owner);
        for (String oneRes : list2) {
            this.removeResidence((Player)null, oneRes, true);
        }
    }

    public int getOwnedZoneCount(String player) {
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        return rPlayer.getResAmount();
    }

    public boolean hasMaxZones(String player, int target) {
        if (this.getOwnedZoneCount(player) < target) {
            return true;
        }
        return false;
    }

    public void printAreaInfo(String areaname, CommandSender sender) {
        this.printAreaInfo(areaname, sender, false);
    }

    public void printAreaInfo(String areaname, CommandSender sender, boolean resadmin2) {
        String time;
        ClaimedResidence res = this.getByName(areaname);
        if (res == null) {
            Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
            return;
        }
        areaname = res.getName();
        Residence.msg(sender, lm.General_Separator, new Object[0]);
        ResidencePermissions perms = res.getPermissions();
        String resNameOwner = "&e" + Residence.msg(lm.Residence_Line, areaname);
        resNameOwner = String.valueOf(resNameOwner) + Residence.msg(lm.General_Owner, perms.getOwner());
        if (Residence.getConfigManager().enableEconomy() && (res.isOwner(sender) || !(sender instanceof Player) || resadmin2)) {
            resNameOwner = String.valueOf(resNameOwner) + Residence.msg(lm.Bank_Name, res.getBank().getStoredMoney());
        }
        resNameOwner = ChatColor.translateAlternateColorCodes((char)'&', (String)resNameOwner);
        String worldInfo = Residence.msg(lm.General_World, perms.getWorld());
        if (res.getPermissions().has("hidden", FlagPermissions.FlagCombo.FalseOrNone) && res.getPermissions().has("coords", FlagPermissions.FlagCombo.TrueOrNone) || resadmin2) {
            worldInfo = String.valueOf(worldInfo) + "&6 (&3";
            CuboidArea area2 = res.getAreaArray()[0];
            worldInfo = String.valueOf(worldInfo) + Residence.msg(lm.General_CoordsTop, area2.getHighLoc().getBlockX(), area2.getHighLoc().getBlockY(), area2.getHighLoc().getBlockZ());
            worldInfo = String.valueOf(worldInfo) + "&6; &3";
            worldInfo = String.valueOf(worldInfo) + Residence.msg(lm.General_CoordsBottom, area2.getLowLoc().getBlockX(), area2.getLowLoc().getBlockY(), area2.getLowLoc().getBlockZ());
            worldInfo = String.valueOf(worldInfo) + "&6)";
            worldInfo = ChatColor.translateAlternateColorCodes((char)'&', (String)worldInfo);
        }
        worldInfo = String.valueOf(worldInfo) + "\n" + Residence.msg(lm.General_CreatedOn, GetTime.getTime(res.createTime));
        String ResFlagList = perms.listFlags(5);
        if (!(sender instanceof Player)) {
            ResFlagList = perms.listFlags();
        }
        String ResFlagMsg = Residence.msg(lm.General_ResidenceFlags, ResFlagList);
        if (perms.getFlags().size() > 2 && sender instanceof Player) {
            ResFlagMsg = String.valueOf(Residence.msg(lm.General_ResidenceFlags, perms.listFlags(5, 3))) + "...";
        }
        if (sender instanceof Player) {
            String raw = this.convertToRaw(null, resNameOwner, worldInfo);
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + raw));
            raw = this.convertToRaw(null, ResFlagMsg, ResFlagList);
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + raw));
        } else {
            Residence.msg(sender, resNameOwner);
            Residence.msg(sender, worldInfo);
            Residence.msg(sender, ResFlagMsg);
        }
        if (!Residence.getConfigManager().isShortInfoUse() || !(sender instanceof Player)) {
            sender.sendMessage(Residence.msg(lm.General_PlayersFlags, perms.listPlayersFlags()));
        } else if (Residence.getConfigManager().isShortInfoUse() || sender instanceof Player) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + perms.listPlayersFlagsRaw(sender.getName(), Residence.msg(lm.General_PlayersFlags, ""))));
        }
        String groupFlags = perms.listGroupFlags();
        if (groupFlags.length() > 0) {
            Residence.msg(sender, lm.General_GroupFlags, groupFlags);
        }
        String msg = "";
        msg = String.valueOf(msg) + Residence.msg(lm.General_TotalResSize, res.getTotalSize(), res.getXZSize());
        Residence.msg(sender, ChatColor.translateAlternateColorCodes((char)'&', (String)msg));
        if (Residence.getEconomyManager() != null) {
            Residence.msg(sender, lm.General_TotalWorth, (double)((int)((double)res.getTotalSize() * res.getOwnerGroup().getCostPerBlock() * 100.0)) / 100.0, (double)((int)((double)res.getTotalSize() * res.getBlockSellPrice() * 100.0)) / 100.0);
        }
        if (Residence.getConfigManager().useLeases() && Residence.getLeaseManager().leaseExpires(areaname) && (time = Residence.getLeaseManager().getExpireTime(areaname)) != null) {
            Residence.msg(sender, lm.Economy_LeaseExpire, time);
        }
        if (Residence.getConfigManager().enabledRentSystem() && Residence.getRentManager().isForRent(areaname) && !Residence.getRentManager().isRented(areaname)) {
            String forRentMsg = Residence.msg(lm.Rent_isForRent, new Object[0]);
            RentableLand rentable = Residence.getRentManager().getRentableLand(areaname);
            StringBuilder rentableString = new StringBuilder();
            if (rentable != null) {
                rentableString.append(String.valueOf(Residence.msg(lm.General_Cost, rentable.cost, rentable.days)) + "\n");
                rentableString.append(String.valueOf(Residence.msg(lm.Rentable_AllowRenewing, rentable.AllowRenewing)) + "\n");
                rentableString.append(String.valueOf(Residence.msg(lm.Rentable_StayInMarket, rentable.StayInMarket)) + "\n");
                rentableString.append(Residence.msg(lm.Rentable_AllowAutoPay, rentable.AllowAutoPay));
            }
            if (sender instanceof Player) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + this.convertToRaw(null, forRentMsg, rentableString.toString())));
            } else {
                Residence.msg(sender, forRentMsg);
            }
        } else if (Residence.getConfigManager().enabledRentSystem() && Residence.getRentManager().isRented(areaname)) {
            String RentedMsg = Residence.msg(lm.Residence_RentedBy, Residence.getRentManager().getRentingPlayer(areaname));
            RentableLand rentable = Residence.getRentManager().getRentableLand(areaname);
            RentedLand rented = Residence.getRentManager().getRentedLand(areaname);
            StringBuilder rentableString = new StringBuilder();
            if (rented != null) {
                rentableString.append(String.valueOf(Residence.msg(lm.Rent_Expire, GetTime.getTime(rented.endTime))) + "\n");
                if (rented.player.equals(sender.getName()) || resadmin2 || res.isOwner(sender)) {
                    rentableString.append(String.valueOf(rented.AutoPay ? Residence.msg(lm.Rent_AutoPayTurnedOn, new Object[0]) : Residence.msg(lm.Rent_AutoPayTurnedOff, new Object[0])) + "\n");
                }
            }
            if (rentable != null) {
                rentableString.append(String.valueOf(Residence.msg(lm.General_Cost, rentable.cost, rentable.days)) + "\n");
                rentableString.append(String.valueOf(Residence.msg(lm.Rentable_AllowRenewing, rentable.AllowRenewing)) + "\n");
                rentableString.append(String.valueOf(Residence.msg(lm.Rentable_StayInMarket, rentable.StayInMarket)) + "\n");
                rentableString.append(Residence.msg(lm.Rentable_AllowAutoPay, rentable.AllowAutoPay));
            }
            if (sender instanceof Player) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + this.convertToRaw(null, RentedMsg, rentableString.toString())));
            } else {
                Residence.msg(sender, RentedMsg);
            }
        } else if (Residence.getTransactionManager().isForSale(areaname)) {
            int amount = Residence.getTransactionManager().getSaleAmount(areaname);
            String SellMsg = String.valueOf(Residence.msg(lm.Economy_LandForSale, new Object[0])) + " " + amount;
            Residence.msg(sender, SellMsg);
        }
        Residence.msg(sender, lm.General_Separator, new Object[0]);
    }

    public String convertToRaw(String preText, String text, String hover) {
        return this.convertToRaw(preText, text, hover, null);
    }

    public String convertToRaw(String preText, String text, String hover, String command2) {
        StringBuilder msg = new StringBuilder();
        String cmd2 = "";
        if (command2 != null) {
            cmd2 = ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/" + command2 + "\"}";
        }
        msg.append("[\"\",");
        if (preText != null) {
            msg.append("{\"text\":\"" + preText + "\"}");
        }
        msg.append("{\"text\":\"" + text + "\"" + cmd2 + ",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + hover + "\"}]}}}");
        msg.append("]");
        return msg.toString();
    }

    public void mirrorPerms(Player reqPlayer, String targetArea, String sourceArea, boolean resadmin2) {
        ClaimedResidence reciever = this.getByName(targetArea);
        ClaimedResidence source = this.getByName(sourceArea);
        if (source == null || reciever == null) {
            Residence.msg((CommandSender)reqPlayer, lm.Invalid_Residence, new Object[0]);
            return;
        }
        if (!(resadmin2 || reciever.getPermissions().hasResidencePermission((CommandSender)reqPlayer, true) && source.getPermissions().hasResidencePermission((CommandSender)reqPlayer, true))) {
            Residence.msg((CommandSender)reqPlayer, lm.General_NoPermission, new Object[0]);
            return;
        }
        reciever.getPermissions().applyTemplate(reqPlayer, source.getPermissions(), resadmin2);
    }

    public Map<String, Object> save() {
        LinkedHashMap<String, Object> worldmap = new LinkedHashMap<String, Object>();
        for (World world : Residence.getServ().getWorlds()) {
            LinkedHashMap<String, Map<String, Object>> resmap = new LinkedHashMap<String, Map<String, Object>>();
            for (Map.Entry<String, ClaimedResidence> res : this.residences.entrySet()) {
                if (!res.getValue().getWorld().equals(world.getName())) continue;
                try {
                    resmap.put(res.getValue().getResidenceName(), res.getValue().save());
                    continue;
                }
                catch (Exception ex) {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + (Object)ChatColor.RED + " Failed to save residence (" + res.getKey() + ")!");
                    Logger.getLogger(ResidenceManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            worldmap.put(world.getName(), resmap);
        }
        return worldmap;
    }

    public ResidenceManager load(Map<String, Object> root) throws Exception {
        ResidenceManager resm = new ResidenceManager(this.plugin);
        if (root == null) {
            return resm;
        }
        for (World world : Residence.getServ().getWorlds()) {
            long time;
            long pass;
            block5 : {
                time = System.currentTimeMillis();
                Map reslist = (Map)root.get(world.getName());
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Loading " + world.getName() + " data into memory...");
                if (reslist != null) {
                    try {
                        resm.chunkResidences.put(world.getName(), this.loadMap(world.getName(), reslist, resm));
                    }
                    catch (Exception ex) {
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + (Object)ChatColor.RED + "Error in loading save file for world: " + world.getName());
                        if (!Residence.getConfigManager().stopOnSaveError()) break block5;
                        throw ex;
                    }
                }
            }
            String PastTime = (pass = System.currentTimeMillis() - time) > 1000 ? String.valueOf(String.format("%.2f", Float.valueOf((float)pass / 1000.0f))) + " sec" : String.valueOf(pass) + " ms";
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " Loaded " + world.getName() + " data into memory. (" + PastTime + ")");
        }
        return resm;
    }

    public Map<ChunkRef, List<String>> loadMap(String worldName, Map<String, Object> root, ResidenceManager resm) throws Exception {
        HashMap<ChunkRef, List<String>> retRes = new HashMap<ChunkRef, List<String>>();
        if (root == null) {
            return retRes;
        }
        int i = 0;
        int y = 0;
        for (Map.Entry<String, Object> res : root.entrySet()) {
            if (i == 100 & Residence.getConfigManager().isUUIDConvertion()) {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + " " + worldName + " UUID conversion done: " + y + " of " + root.size());
            }
            if (i >= 100) {
                i = 0;
            }
            ++i;
            ++y;
            try {
                ClaimedResidence residence = ClaimedResidence.load(worldName, (Map)res.getValue(), null, this.plugin);
                if (residence == null || residence.getPermissions().getOwnerUUID().toString().equals(Residence.getServerLandUUID()) && !residence.getOwner().equalsIgnoreCase("Server land") && !residence.getOwner().equalsIgnoreCase(Residence.getServerLandname())) continue;
                if (residence.getOwner().equalsIgnoreCase("Server land")) {
                    residence.getPermissions().setOwner(Residence.getServerLandname(), false);
                }
                String resName = res.getKey().toLowerCase();
                int increment = ResidenceManager.getNameIncrement(resName, resm);
                if (residence.getResidenceName() == null) {
                    residence.setName(res.getKey());
                }
                if (increment > 0) {
                    residence.setName(String.valueOf(residence.getResidenceName()) + increment);
                    resName = String.valueOf(resName) + increment;
                }
                for (ChunkRef chunk : ResidenceManager.getChunks(residence)) {
                    ArrayList<String> ress = new ArrayList<String>();
                    if (retRes.containsKey(chunk)) {
                        ress.addAll((Collection)retRes.get(chunk));
                    }
                    ress.add(resName);
                    retRes.put(chunk, ress);
                }
                resm.residences.put(resName, residence);
                continue;
            }
            catch (Exception ex) {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Residence.prefix) + (Object)ChatColor.RED + " Failed to load residence (" + res.getKey() + ")! Reason:" + ex.getMessage() + " Error Log:");
                Logger.getLogger(ResidenceManager.class.getName()).log(Level.SEVERE, null, ex);
                if (!Residence.getConfigManager().stopOnSaveError()) continue;
                throw ex;
            }
        }
        return retRes;
    }

    private static int getNameIncrement(String name, ResidenceManager resm) {
        String orName = name;
        int i = 0;
        while (i < 1000) {
            if (!resm.residences.containsKey(name)) break;
            name = String.valueOf(orName) + ++i;
        }
        return i;
    }

    private static List<ChunkRef> getChunks(ClaimedResidence res) {
        ArrayList<ChunkRef> chunks = new ArrayList<ChunkRef>();
        CuboidArea[] arrcuboidArea = res.getAreaArray();
        int n = arrcuboidArea.length;
        int n2 = 0;
        while (n2 < n) {
            CuboidArea area2 = arrcuboidArea[n2];
            chunks.addAll(area2.getChunks());
            ++n2;
        }
        return chunks;
    }

    public boolean renameResidence(String oldName, String newName) {
        return this.renameResidence(null, oldName, newName, true);
    }

    public boolean renameResidence(Player player, String oldName, String newName, boolean resadmin2) {
        if (!player.hasPermission("residence.rename")) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return false;
        }
        if (!Residence.validName(newName)) {
            Residence.msg((CommandSender)player, lm.Invalid_NameCharacters, new Object[0]);
            return false;
        }
        ClaimedResidence res = this.getByName(oldName);
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return false;
        }
        oldName = res.getName();
        if (res.getPermissions().hasResidencePermission((CommandSender)player, true) || resadmin2) {
            if (res.getParent() == null) {
                if (this.residences.containsKey(newName.toLowerCase())) {
                    Residence.msg((CommandSender)player, lm.Residence_AlreadyExists, newName);
                    return false;
                }
                ResidenceRenameEvent resevent = new ResidenceRenameEvent(res, newName, oldName);
                Residence.getServ().getPluginManager().callEvent((Event)resevent);
                this.removeChunkList(oldName);
                res.setName(newName);
                this.residences.put(newName.toLowerCase(), res);
                this.residences.remove(oldName.toLowerCase());
                Residence.getPlayerManager().renameResidence(player.getName(), oldName, newName);
                this.calculateChunks(newName);
                if (Residence.getConfigManager().useLeases()) {
                    Residence.getLeaseManager().updateLeaseName(oldName, newName);
                }
                Residence.getSignUtil().updateSignResName(res);
                Residence.msg((CommandSender)player, lm.Residence_Rename, oldName, newName);
                return true;
            }
            String[] oldname = oldName.split("\\.");
            ClaimedResidence parent = res.getParent();
            boolean feed = parent.renameSubzone(player, oldname[oldname.length - 1], newName, resadmin2);
            Residence.getSignUtil().updateSignResName(res);
            return feed;
        }
        Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
        return false;
    }

    public void giveResidence(Player reqPlayer, String targPlayer, String residence, boolean resadmin2) {
        ResidencePlayer rPlayer;
        PermissionGroup group;
        ClaimedResidence res = this.getByName(residence);
        if (res == null) {
            Residence.msg((CommandSender)reqPlayer, lm.Invalid_Residence, new Object[0]);
            return;
        }
        residence = res.getName();
        if (!res.getPermissions().hasResidencePermission((CommandSender)reqPlayer, true) && !resadmin2) {
            Residence.msg((CommandSender)reqPlayer, lm.General_NoPermission, new Object[0]);
            return;
        }
        Player giveplayer = Residence.getServ().getPlayer(targPlayer);
        if (giveplayer == null || !giveplayer.isOnline()) {
            Residence.msg((CommandSender)reqPlayer, lm.General_NotOnline, new Object[0]);
            return;
        }
        CuboidArea[] areas = res.getAreaArray();
        if (areas.length > (group = (rPlayer = Residence.getPlayerManager().getResidencePlayer(giveplayer)).getGroup()).getMaxPhysicalPerResidence() && !resadmin2) {
            Residence.msg((CommandSender)reqPlayer, lm.Residence_GiveLimits, new Object[0]);
            return;
        }
        if (!this.hasMaxZones(giveplayer.getName(), rPlayer.getMaxRes()) && !resadmin2) {
            Residence.msg((CommandSender)reqPlayer, lm.Residence_GiveLimits, new Object[0]);
            return;
        }
        if (!resadmin2) {
            CuboidArea[] arrcuboidArea = areas;
            int n = arrcuboidArea.length;
            int n2 = 0;
            while (n2 < n) {
                CuboidArea area2 = arrcuboidArea[n2];
                if (!res.isSubzone() && !res.isSmallerThanMax(giveplayer, area2, resadmin2) || res.isSubzone() && !res.isSmallerThanMaxSubzone(giveplayer, area2, resadmin2)) {
                    Residence.msg((CommandSender)reqPlayer, lm.Residence_GiveLimits, new Object[0]);
                    return;
                }
                ++n2;
            }
        }
        Residence.getPlayerManager().removeResFromPlayer(reqPlayer, residence);
        Residence.getPlayerManager().addResidence(targPlayer, res);
        res.getPermissions().setOwner(giveplayer.getName(), true);
        Residence.msg((CommandSender)reqPlayer, lm.Residence_Give, residence, giveplayer.getName());
        Residence.msg((CommandSender)giveplayer, lm.Residence_Recieve, residence, reqPlayer.getName());
    }

    public void removeAllFromWorld(CommandSender sender, String world) {
        int count = 0;
        Iterator<ClaimedResidence> it = this.residences.values().iterator();
        while (it.hasNext()) {
            ClaimedResidence next = it.next();
            if (!next.getWorld().equals(world)) continue;
            it.remove();
            ++count;
        }
        this.chunkResidences.remove(world);
        this.chunkResidences.put(world, new HashMap());
        if (count == 0) {
            sender.sendMessage((Object)ChatColor.RED + "No residences found in world: " + (Object)ChatColor.YELLOW + world);
        } else {
            sender.sendMessage((Object)ChatColor.RED + "Removed " + (Object)ChatColor.YELLOW + count + (Object)ChatColor.RED + " residences in world: " + (Object)ChatColor.YELLOW + world);
        }
        Residence.getPlayerManager().fillList();
    }

    public int getResidenceCount() {
        return this.residences.size();
    }

    public Map<String, ClaimedResidence> getResidences() {
        return this.residences;
    }

    public void removeChunkList(String name) {
        String world;
        if (name == null) {
            return;
        }
        ClaimedResidence res = this.residences.get(name = name.toLowerCase());
        if (res != null && this.chunkResidences.get(world = res.getWorld()) != null) {
            for (ChunkRef chunk : ResidenceManager.getChunks(res)) {
                ArrayList ress = new ArrayList();
                if (this.chunkResidences.get(world).containsKey(chunk)) {
                    ress.addAll(this.chunkResidences.get(world).get(chunk));
                }
                ress.remove(name);
                this.chunkResidences.get(world).put(chunk, ress);
            }
        }
    }

    public void calculateChunks(String name) {
        ClaimedResidence res = null;
        if (name == null) {
            return;
        }
        res = this.residences.get(name = name.toLowerCase());
        if (res != null) {
            String world = res.getWorld();
            if (this.chunkResidences.get(world) == null) {
                this.chunkResidences.put(world, new HashMap());
            }
            for (ChunkRef chunk : ResidenceManager.getChunks(res)) {
                ArrayList<String> ress = new ArrayList<String>();
                if (this.chunkResidences.get(world).containsKey(chunk)) {
                    ress.addAll((Collection)this.chunkResidences.get(world).get(chunk));
                }
                ress.add(name);
                this.chunkResidences.get(world).put(chunk, ress);
            }
        }
    }

    public static final class ChunkRef {
        private final int z;
        private final int x;

        public static int getChunkCoord(int val) {
            return val >> 4;
        }

        public ChunkRef(Location loc) {
            this.x = ChunkRef.getChunkCoord(loc.getBlockX());
            this.z = ChunkRef.getChunkCoord(loc.getBlockZ());
        }

        public ChunkRef(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            ChunkRef other = (ChunkRef)obj;
            if (this.x == other.x && this.z == other.z) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.x ^ this.z;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{ x: ").append(this.x).append(", z: ").append(this.z).append(" }");
            return sb.toString();
        }
    }

}

