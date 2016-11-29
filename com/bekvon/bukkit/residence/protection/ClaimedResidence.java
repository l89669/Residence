/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.World$Environment
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.chat.ChatChannel;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.RandomLoc;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.Visualizer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.ResidenceBank;
import com.bekvon.bukkit.residence.economy.TransactionManager;
import com.bekvon.bukkit.residence.economy.rent.RentableLand;
import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.event.ResidenceAreaAddEvent;
import com.bekvon.bukkit.residence.event.ResidenceAreaDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceSizeChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceSubzoneCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceTPEvent;
import com.bekvon.bukkit.residence.itemlist.ItemList;
import com.bekvon.bukkit.residence.itemlist.ResidenceItemList;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.shopStuff.ShopVote;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class ClaimedResidence {
    private String resName;
    protected ClaimedResidence parent;
    protected Map<String, CuboidArea> areas = new HashMap<String, CuboidArea>();
    protected Map<String, ClaimedResidence> subzones = new HashMap<String, ClaimedResidence>();
    protected ResidencePermissions perms;
    protected ResidenceBank bank;
    protected Double BlockSellPrice = 0.0;
    protected Location tpLoc;
    protected String enterMessage;
    protected String leaveMessage;
    protected String ShopDesc = null;
    protected String ChatPrefix = "";
    protected ChatColor ChannelColor = ChatColor.WHITE;
    protected ResidenceItemList ignorelist;
    protected ResidenceItemList blacklist;
    protected boolean mainRes = false;
    protected long createTime = 0;
    protected List<String> cmdWhiteList = new ArrayList<String>();
    protected List<String> cmdBlackList = new ArrayList<String>();
    List<ShopVote> ShopVoteList = new ArrayList<ShopVote>();
    protected RentableLand rentableland = null;
    protected RentedLand rentedland = null;
    protected Integer sellPrice = -1;
    private Residence plugin;

    public String getResidenceName() {
        return this.resName;
    }

    public void setName(String name) {
        this.resName = name.contains(".") ? name.split("\\.")[name.split("\\.").length - 1] : name;
    }

    public void setCreateTime() {
        this.createTime = System.currentTimeMillis();
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public Integer getSellPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(Integer amount) {
        this.sellPrice = amount;
    }

    public boolean isForSell() {
        return Residence.getTransactionManager().isForSale(this.getName());
    }

    public boolean isForRent() {
        return Residence.getRentManager().isForRent(this);
    }

    public boolean isSubzoneForRent() {
        for (Map.Entry<String, ClaimedResidence> one : this.subzones.entrySet()) {
            if (one.getValue().isForRent()) {
                return true;
            }
            if (!one.getValue().isSubzoneForRent()) continue;
            return true;
        }
        return false;
    }

    public boolean isSubzoneRented() {
        for (Map.Entry<String, ClaimedResidence> one : this.subzones.entrySet()) {
            if (one.getValue().isRented()) {
                return true;
            }
            if (!one.getValue().isSubzoneRented()) continue;
            return true;
        }
        return false;
    }

    public ClaimedResidence getRentedSubzone() {
        for (Map.Entry<String, ClaimedResidence> one : this.subzones.entrySet()) {
            if (one.getValue().isRented()) {
                return one.getValue();
            }
            if (one.getValue().getRentedSubzone() == null) continue;
            return one.getValue().getRentedSubzone();
        }
        return null;
    }

    public boolean isParentForRent() {
        if (this.getParent() != null) {
            return this.getParent().isForRent() ? true : this.getParent().isParentForRent();
        }
        return false;
    }

    public boolean isParentForSell() {
        if (this.getParent() != null) {
            return this.getParent().isForSell() ? true : this.getParent().isParentForSell();
        }
        return false;
    }

    public boolean isRented() {
        return Residence.getRentManager().isRented(this);
    }

    public void setRentable(RentableLand rl) {
        this.rentableland = rl;
    }

    public RentableLand getRentable() {
        return this.rentableland;
    }

    public void setRented(RentedLand rl) {
        this.rentedland = rl;
    }

    public RentedLand getRentedLand() {
        return this.rentedland;
    }

    public ClaimedResidence(String creationWorld, Residence plugin) {
        this(Residence.getServerLandname(), creationWorld, plugin);
    }

    public ClaimedResidence(String creator, String creationWorld, Residence plugin) {
        this(plugin);
        this.perms = new ResidencePermissions(this, creator, creationWorld);
    }

    public ClaimedResidence(String creator, String creationWorld, ClaimedResidence parentResidence, Residence plugin) {
        this(creator, creationWorld, plugin);
        this.parent = parentResidence;
    }

    public ClaimedResidence(Residence plugin) {
        this.bank = new ResidenceBank(this);
        this.blacklist = new ResidenceItemList(this, ItemList.ListType.BLACKLIST);
        this.ignorelist = new ResidenceItemList(this, ItemList.ListType.IGNORELIST);
        this.plugin = plugin;
    }

    public boolean isMainResidence() {
        return this.mainRes;
    }

    public void setMainResidence(boolean state) {
        this.mainRes = state;
    }

    public boolean isSubzone() {
        return this.parent != null;
    }

    public int getSubzoneDeep() {
        return this.getSubzoneDeep(0);
    }

    public int getSubzoneDeep(int deep) {
        ++deep;
        if (this.parent != null) {
            return this.parent.getSubzoneDeep(deep);
        }
        return deep;
    }

    public boolean isBiggerThanMin(Player player, CuboidArea area2, boolean resadmin2) {
        if (resadmin2) {
            return true;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (area2.getXSize() < group.getMinX()) {
            Residence.msg((CommandSender)player, lm.Area_ToSmallX, area2.getXSize(), group.getMinX());
            return false;
        }
        if (area2.getYSize() < group.getMinY()) {
            Residence.msg((CommandSender)player, lm.Area_ToSmallY, area2.getYSize(), group.getMinY());
            return false;
        }
        if (area2.getZSize() < group.getMinZ()) {
            Residence.msg((CommandSender)player, lm.Area_ToSmallZ, area2.getZSize(), group.getMinZ());
            return false;
        }
        return true;
    }

    public boolean isBiggerThanMinSubzone(Player player, CuboidArea area2, boolean resadmin2) {
        if (resadmin2) {
            return true;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (area2.getXSize() < group.getSubzoneMinX()) {
            Residence.msg((CommandSender)player, lm.Area_ToSmallX, area2.getXSize(), group.getSubzoneMinX());
            return false;
        }
        if (area2.getYSize() < group.getSubzoneMinY()) {
            Residence.msg((CommandSender)player, lm.Area_ToSmallY, area2.getYSize(), group.getSubzoneMinY());
            return false;
        }
        if (area2.getZSize() < group.getSubzoneMinZ()) {
            Residence.msg((CommandSender)player, lm.Area_ToSmallZ, area2.getZSize(), group.getSubzoneMinZ());
            return false;
        }
        return true;
    }

    public boolean isSmallerThanMax(Player player, CuboidArea area2, boolean resadmin2) {
        if (resadmin2) {
            return true;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (area2.getXSize() > group.getMaxX()) {
            Residence.msg((CommandSender)player, lm.Area_ToBigX, area2.getXSize(), group.getMaxX());
            return false;
        }
        if (area2.getYSize() > group.getMaxY()) {
            Residence.msg((CommandSender)player, lm.Area_ToBigY, area2.getYSize(), group.getMaxY());
            return false;
        }
        if (area2.getZSize() > group.getMaxZ()) {
            Residence.msg((CommandSender)player, lm.Area_ToBigZ, area2.getZSize(), group.getMaxZ());
            return false;
        }
        return true;
    }

    public boolean isSmallerThanMaxSubzone(Player player, CuboidArea area2, boolean resadmin2) {
        if (resadmin2) {
            return true;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (area2.getXSize() > group.getSubzoneMaxX()) {
            Residence.msg((CommandSender)player, lm.Area_ToBigX, area2.getXSize(), group.getSubzoneMaxX());
            return false;
        }
        if (area2.getYSize() > group.getSubzoneMaxY()) {
            Residence.msg((CommandSender)player, lm.Area_ToBigY, area2.getYSize(), group.getSubzoneMaxY());
            return false;
        }
        if (area2.getZSize() > group.getSubzoneMaxZ()) {
            Residence.msg((CommandSender)player, lm.Area_ToBigZ, area2.getZSize(), group.getSubzoneMaxZ());
            return false;
        }
        return true;
    }

    public boolean addArea(CuboidArea area2, String name) {
        return this.addArea(null, area2, name, true);
    }

    public boolean addArea(Player player, CuboidArea area2, String name, boolean resadmin2) {
        return this.addArea(player, area2, name, resadmin2, true);
    }

    public boolean addArea(Player player, CuboidArea area2, String name, boolean resadmin2, boolean chargeMoney) {
        if (!Residence.validName(name)) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Invalid_NameCharacters, new Object[0]);
            }
            return false;
        }
        String NName = name;
        name = name.toLowerCase();
        if (this.areas.containsKey(NName)) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Area_Exists, new Object[0]);
            }
            return false;
        }
        if (this.isSubzone() && !this.isBiggerThanMinSubzone(player, area2, resadmin2) || !this.isSubzone() && !this.isBiggerThanMin(player, area2, resadmin2)) {
            return false;
        }
        if (!resadmin2 && Residence.getConfigManager().getEnforceAreaInsideArea() && this.getParent() == null) {
            boolean inside = false;
            for (CuboidArea are : this.areas.values()) {
                if (!are.isAreaWithinArea(area2)) continue;
                inside = true;
            }
            if (!inside) {
                return false;
            }
        }
        if (!area2.getWorld().getName().equalsIgnoreCase(this.perms.getWorld())) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Area_DiffWorld, new Object[0]);
            }
            return false;
        }
        if (this.parent == null) {
            String collideResidence = Residence.getResidenceManager().checkAreaCollision(area2, this);
            ClaimedResidence cRes = Residence.getResidenceManager().getByName(collideResidence);
            if (cRes != null) {
                if (player != null) {
                    Residence.msg((CommandSender)player, lm.Area_Collision, cRes.getName());
                    Visualizer v = new Visualizer(player);
                    v.setAreas(area2);
                    v.setErrorAreas(cRes);
                    Residence.getSelectionManager().showBounds(player, v);
                }
                return false;
            }
        } else {
            String[] szs;
            String[] arrstring = szs = this.parent.listSubzones();
            int n = arrstring.length;
            int v = 0;
            while (v < n) {
                String sz = arrstring[v];
                ClaimedResidence res = this.parent.getSubzone(sz);
                if (res != null && res != this && res.checkCollision(area2)) {
                    if (player != null) {
                        Residence.msg((CommandSender)player, lm.Area_SubzoneCollision, sz);
                    }
                    return false;
                }
                ++v;
            }
        }
        if (!resadmin2 && player != null) {
            int chargeamount;
            if (!this.perms.hasResidencePermission((CommandSender)player, true)) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return false;
            }
            if (this.parent != null) {
                if (!this.parent.containsLoc(area2.getHighLoc()) || !this.parent.containsLoc(area2.getLowLoc())) {
                    Residence.msg((CommandSender)player, lm.Area_NotWithinParent, new Object[0]);
                    return false;
                }
                if (!this.parent.getPermissions().hasResidencePermission((CommandSender)player, true) && !this.parent.getPermissions().playerHas(player, Flags.subzone, FlagPermissions.FlagCombo.OnlyTrue)) {
                    Residence.msg((CommandSender)player, lm.Residence_ParentNoPermission, new Object[0]);
                    return false;
                }
            }
            ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
            PermissionGroup group = rPlayer.getGroup();
            if (!this.isSubzone() && !group.canCreateResidences() && !player.hasPermission("residence.create") || this.isSubzone() && !group.canCreateResidences() && !player.hasPermission("residence.create.subzone")) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return false;
            }
            if (this.areas.size() >= group.getMaxPhysicalPerResidence()) {
                Residence.msg((CommandSender)player, lm.Area_MaxPhysical, new Object[0]);
                return false;
            }
            if (!this.isSubzone() && !this.isSmallerThanMax(player, area2, resadmin2) || this.isSubzone() && !this.isSmallerThanMaxSubzone(player, area2, resadmin2)) {
                Residence.msg((CommandSender)player, lm.Area_SizeLimit, new Object[0]);
                return false;
            }
            if (group.getMinHeight() > area2.getLowLoc().getBlockY()) {
                Residence.msg((CommandSender)player, lm.Area_LowLimit, String.format("%d", group.getMinHeight()));
                return false;
            }
            if (group.getMaxHeight() < area2.getHighLoc().getBlockY()) {
                Residence.msg((CommandSender)player, lm.Area_HighLimit, String.format("%d", group.getMaxHeight()));
                return false;
            }
            if (chargeMoney && this.parent == null && Residence.getConfigManager().enableEconomy() && !resadmin2 && !TransactionManager.chargeEconomyMoney(player, chargeamount = (int)Math.ceil((double)area2.getSize() * group.getCostPerBlock()))) {
                return false;
            }
        }
        ResidenceAreaAddEvent resevent = new ResidenceAreaAddEvent(player, NName, this, area2);
        Residence.getServ().getPluginManager().callEvent((Event)resevent);
        if (resevent.isCancelled()) {
            return false;
        }
        Residence.getResidenceManager().removeChunkList(this.getName());
        this.areas.put(name, area2);
        Residence.getResidenceManager().calculateChunks(this.getName());
        return true;
    }

    public boolean replaceArea(CuboidArea neware, String name) {
        return this.replaceArea(null, neware, name, true);
    }

    public boolean replaceArea(Player player, CuboidArea newarea, String name, boolean resadmin2) {
        String[] szs;
        String sz;
        int v2;
        String[] arrstring;
        ClaimedResidence res;
        int n;
        if (!this.areas.containsKey(name)) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Area_NonExist, new Object[0]);
            }
            return false;
        }
        CuboidArea oldarea = this.areas.get(name);
        if (!newarea.getWorld().getName().equalsIgnoreCase(this.perms.getWorld())) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Area_DiffWorld, new Object[0]);
            }
            return false;
        }
        if (this.parent == null) {
            String collideResidence = Residence.getResidenceManager().checkAreaCollision(newarea, this);
            ClaimedResidence cRes = Residence.getResidenceManager().getByName(collideResidence);
            if (cRes != null && player != null) {
                Residence.msg((CommandSender)player, lm.Area_Collision, cRes.getName());
                Visualizer v2 = new Visualizer(player);
                v2.setAreas(this.getAreaArray());
                v2.setErrorAreas(cRes.getAreaArray());
                Residence.getSelectionManager().showBounds(player, v2);
                return false;
            }
        } else {
            arrstring = szs = this.parent.listSubzones();
            n = arrstring.length;
            v2 = 0;
            while (v2 < n) {
                sz = arrstring[v2];
                res = this.parent.getSubzone(sz);
                if (res != null && res != this && res.checkCollision(newarea)) {
                    if (player != null) {
                        Residence.msg((CommandSender)player, lm.Area_SubzoneCollision, sz);
                        Visualizer v3 = new Visualizer(player);
                        v3.setErrorAreas(res.getAreaArray());
                        Residence.getSelectionManager().showBounds(player, v3);
                    }
                    return false;
                }
                ++v2;
            }
        }
        arrstring = szs = this.listSubzones();
        n = arrstring.length;
        v2 = 0;
        while (v2 < n) {
            sz = arrstring[v2];
            res = this.getSubzone(sz);
            if (res != null && res != this) {
                String[] szareas;
                String[] arrstring2 = szareas = res.getAreaList();
                int n2 = arrstring2.length;
                int n3 = 0;
                while (n3 < n2) {
                    String area2 = arrstring2[n3];
                    if (!newarea.isAreaWithinArea(res.getArea(area2))) {
                        boolean good = false;
                        CuboidArea[] arrcuboidArea = this.getAreaArray();
                        int n4 = arrcuboidArea.length;
                        int n5 = 0;
                        while (n5 < n4) {
                            CuboidArea arae = arrcuboidArea[n5];
                            if (arae != oldarea && arae.isAreaWithinArea(res.getArea(area2))) {
                                good = true;
                            }
                            ++n5;
                        }
                        if (!good) {
                            res.removeArea(area2);
                        }
                    }
                    ++n3;
                }
                if (res.getAreaArray().length == 0) {
                    this.removeSubzone(sz);
                }
            }
            ++v2;
        }
        if (!resadmin2 && player != null) {
            PermissionGroup group;
            ResidencePlayer rPlayer;
            int chargeamount;
            if (!this.perms.hasResidencePermission((CommandSender)player, true)) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return false;
            }
            if (this.parent != null) {
                if (!this.parent.containsLoc(newarea.getHighLoc()) || !this.parent.containsLoc(newarea.getLowLoc())) {
                    Residence.msg((CommandSender)player, lm.Area_NotWithinParent, new Object[0]);
                    return false;
                }
                if (!this.parent.getPermissions().hasResidencePermission((CommandSender)player, true) && !this.parent.getPermissions().playerHas(player, Flags.subzone, FlagPermissions.FlagCombo.OnlyTrue)) {
                    Residence.msg((CommandSender)player, lm.Residence_ParentNoPermission, new Object[0]);
                    return false;
                }
            }
            if (!(group = (rPlayer = Residence.getPlayerManager().getResidencePlayer(player)).getGroup()).canCreateResidences() && !player.hasPermission("residence.resize")) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return false;
            }
            if (oldarea.getSize() < newarea.getSize() && (!this.isSubzone() && !this.isSmallerThanMax(player, newarea, resadmin2) || this.isSubzone() && !this.isSmallerThanMaxSubzone(player, newarea, resadmin2))) {
                Residence.msg((CommandSender)player, lm.Area_SizeLimit, new Object[0]);
                return false;
            }
            if (group.getMinHeight() > newarea.getLowLoc().getBlockY()) {
                Residence.msg((CommandSender)player, lm.Area_LowLimit, String.format("%d", group.getMinHeight()));
                return false;
            }
            if (group.getMaxHeight() < newarea.getHighLoc().getBlockY()) {
                Residence.msg((CommandSender)player, lm.Area_HighLimit, String.format("%d", group.getMaxHeight()));
                return false;
            }
            if (this.parent == null && Residence.getConfigManager().enableEconomy() && !resadmin2 && (chargeamount = (int)Math.ceil((double)(newarea.getSize() - oldarea.getSize()) * group.getCostPerBlock())) > 0 && !TransactionManager.chargeEconomyMoney(player, chargeamount)) {
                return false;
            }
        }
        ResidenceSizeChangeEvent resevent = new ResidenceSizeChangeEvent(player, this, oldarea, newarea);
        Residence.getServ().getPluginManager().callEvent((Event)resevent);
        if (resevent.isCancelled()) {
            return false;
        }
        Residence.getResidenceManager().removeChunkList(this.getName());
        this.areas.remove(name);
        this.areas.put(name, newarea);
        Residence.getResidenceManager().calculateChunks(this.getName());
        if (player != null) {
            Residence.msg((CommandSender)player, lm.Area_Update, new Object[0]);
        }
        return true;
    }

    public boolean addSubzone(String name, Location loc1, Location loc2) {
        return this.addSubzone(null, loc1, loc2, name, true);
    }

    public boolean addSubzone(Player player, Location loc1, Location loc2, String name, boolean resadmin2) {
        if (player == null) {
            return this.addSubzone(null, Residence.getServerLandname(), loc1, loc2, name, resadmin2);
        }
        return this.addSubzone(player, player.getName(), loc1, loc2, name, resadmin2);
    }

    public boolean addSubzone(Player player, String owner, Location loc1, Location loc2, String name, boolean resadmin2) {
        ClaimedResidence newres;
        if (!Residence.validName(name)) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Invalid_NameCharacters, new Object[0]);
            }
            return false;
        }
        if (!this.containsLoc(loc1) || !this.containsLoc(loc2)) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Subzone_SelectInside, new Object[0]);
            }
            return false;
        }
        String NName = name;
        if (this.subzones.containsKey(name = name.toLowerCase())) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Subzone_Exists, NName);
            }
            return false;
        }
        if (!resadmin2 && player != null) {
            if (!this.perms.hasResidencePermission((CommandSender)player, true) && !this.perms.playerHas(player.getName(), Flags.subzone, this.perms.playerHas(player, Flags.admin, false))) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return false;
            }
            if (this.getZoneDepth() >= Residence.getPlayerManager().getResidencePlayer(owner).getMaxSubzones()) {
                Residence.msg((CommandSender)player, lm.Subzone_MaxDepth, new Object[0]);
                return false;
            }
        }
        CuboidArea newArea = new CuboidArea(loc1, loc2);
        Set<Map.Entry<String, ClaimedResidence>> set2 = this.subzones.entrySet();
        for (Map.Entry<String, ClaimedResidence> resEntry : set2) {
            ClaimedResidence res = resEntry.getValue();
            if (!res.checkCollision(newArea)) continue;
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Subzone_Collide, resEntry.getKey());
                Visualizer v = new Visualizer(player);
                v.setAreas(newArea);
                v.setErrorAreas(res);
                Residence.getSelectionManager().showBounds(player, v);
            }
            return false;
        }
        if (player != null) {
            newres = new ClaimedResidence(owner, this.perms.getWorld(), this, this.plugin);
            newres.addArea(player, newArea, NName, resadmin2);
        } else {
            newres = new ClaimedResidence(owner, this.perms.getWorld(), this, this.plugin);
            newres.addArea(newArea, NName);
        }
        if (newres.getAreaCount() != 0) {
            newres.getPermissions().applyDefaultFlags();
            if (player != null) {
                ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
                PermissionGroup group = rPlayer.getGroup();
                newres.setEnterMessage(group.getDefaultEnterMessage());
                newres.setLeaveMessage(group.getDefaultLeaveMessage());
            }
            if (Residence.getConfigManager().flagsInherit()) {
                newres.getPermissions().setParent(this.perms);
            }
            newres.resName = name;
            newres.setCreateTime();
            ResidenceSubzoneCreationEvent resevent = new ResidenceSubzoneCreationEvent(player, name, newres, newArea);
            Residence.getServ().getPluginManager().callEvent((Event)resevent);
            if (resevent.isCancelled()) {
                return false;
            }
            this.subzones.put(name, newres);
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Area_Create, name);
                Residence.msg((CommandSender)player, lm.Subzone_Create, name);
            }
            return true;
        }
        if (player != null) {
            Residence.msg((CommandSender)player, lm.Subzone_CreateFail, name);
        }
        return false;
    }

    public String getSubzoneNameByLoc(Location loc) {
        Set<Map.Entry<String, ClaimedResidence>> set2 = this.subzones.entrySet();
        ClaimedResidence res = null;
        String key = null;
        for (Map.Entry<String, ClaimedResidence> entry : set2) {
            if (!entry.getValue().containsLoc(loc)) continue;
            key = entry.getKey();
            res = entry.getValue();
            break;
        }
        if (key == null || res == null) {
            return null;
        }
        String subname = res.getSubzoneNameByLoc(loc);
        if (subname != null) {
            return String.valueOf(key) + "." + subname;
        }
        return key;
    }

    public ClaimedResidence getSubzoneByLoc(Location loc) {
        Set<Map.Entry<String, ClaimedResidence>> set2 = this.subzones.entrySet();
        ClaimedResidence res = null;
        for (Map.Entry<String, ClaimedResidence> entry : set2) {
            if (!entry.getValue().containsLoc(loc)) continue;
            res = entry.getValue();
            break;
        }
        if (res == null) {
            return null;
        }
        ClaimedResidence subrez = res.getSubzoneByLoc(loc);
        if (subrez == null) {
            return res;
        }
        return subrez;
    }

    public ClaimedResidence getSubzone(String subzonename) {
        if (!(subzonename = subzonename.toLowerCase()).contains(".")) {
            return this.subzones.get(subzonename);
        }
        String[] split = subzonename.split("\\.");
        ClaimedResidence get = this.subzones.get(split[0]);
        int i = 1;
        while (i < split.length) {
            if (get == null) {
                return null;
            }
            get = get.getSubzone(split[i]);
            ++i;
        }
        return get;
    }

    public String getSubzoneNameByRes(ClaimedResidence res) {
        Set<Map.Entry<String, ClaimedResidence>> set2 = this.subzones.entrySet();
        for (Map.Entry<String, ClaimedResidence> entry : set2) {
            if (entry.getValue() == res) {
                return entry.getValue().getResidenceName();
            }
            String n = entry.getValue().getSubzoneNameByRes(res);
            if (n == null) continue;
            return String.valueOf(entry.getValue().getResidenceName()) + "." + n;
        }
        return null;
    }

    public String[] getSubzoneList() {
        ArrayList<String> zones = new ArrayList<String>();
        Set<String> set2 = this.subzones.keySet();
        for (String key : set2) {
            if (key == null) continue;
            zones.add(key);
        }
        return zones.toArray(new String[zones.size()]);
    }

    public boolean checkCollision(CuboidArea area2) {
        Set<String> set2 = this.areas.keySet();
        for (String key : set2) {
            CuboidArea checkarea = this.areas.get(key);
            if (checkarea == null || !checkarea.checkCollision(area2)) continue;
            return true;
        }
        return false;
    }

    public boolean containsLoc(Location loc) {
        Collection<CuboidArea> keys = this.areas.values();
        for (CuboidArea key : keys) {
            if (!key.containsLoc(loc)) continue;
            if (this.parent != null) {
                return this.parent.containsLoc(loc);
            }
            return true;
        }
        return false;
    }

    public ClaimedResidence getParent() {
        return this.parent;
    }

    public String getTopParentName() {
        return this.getTopParent().getName();
    }

    public ClaimedResidence getTopParent() {
        if (this.parent == null) {
            return this;
        }
        return this.parent.getTopParent();
    }

    public boolean removeSubzone(String name) {
        return this.removeSubzone(null, name, true);
    }

    public boolean removeSubzone(Player player, String name, boolean resadmin2) {
        if (name == null) {
            return false;
        }
        name = name.toLowerCase();
        ClaimedResidence res = this.subzones.get(name);
        if (player != null && !res.perms.hasResidencePermission((CommandSender)player, true) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return false;
        }
        this.subzones.remove(name);
        if (player != null) {
            Residence.msg((CommandSender)player, lm.Subzone_Remove, name);
        }
        return true;
    }

    public long getTotalSize() {
        Collection<CuboidArea> set2 = this.areas.values();
        long size = 0;
        if (!Residence.getConfigManager().isNoCostForYBlocks()) {
            for (CuboidArea entry : set2) {
                size += entry.getSize();
            }
        } else {
            for (CuboidArea entry : set2) {
                size += (long)(entry.getXSize() * entry.getZSize());
            }
        }
        return size;
    }

    public long getXZSize() {
        Collection<CuboidArea> set2 = this.areas.values();
        long size = 0;
        for (CuboidArea entry : set2) {
            size += (long)(entry.getXSize() * entry.getZSize());
        }
        return size;
    }

    public CuboidArea[] getAreaArray() {
        CuboidArea[] temp = new CuboidArea[this.areas.size()];
        int i = 0;
        Iterator<CuboidArea> iterator = this.areas.values().iterator();
        while (iterator.hasNext()) {
            CuboidArea area2;
            temp[i] = area2 = iterator.next();
            ++i;
        }
        return temp;
    }

    public Map<String, CuboidArea> getAreaMap() {
        return this.areas;
    }

    public ResidencePermissions getPermissions() {
        return this.perms;
    }

    public String getEnterMessage() {
        return this.enterMessage;
    }

    public String getLeaveMessage() {
        return this.leaveMessage;
    }

    public String getShopDesc() {
        return this.ShopDesc;
    }

    public void setEnterMessage(String message2) {
        this.enterMessage = message2;
    }

    public void setLeaveMessage(String message2) {
        this.leaveMessage = message2;
    }

    public void setShopDesc(String message2) {
        this.ShopDesc = message2;
    }

    public void setEnterLeaveMessage(Player player, String message2, boolean enter, boolean resadmin2) {
        PermissionGroup group;
        ResidencePlayer rPlayer;
        if (message2 != null && message2.equals("")) {
            message2 = null;
        }
        if (!(group = (rPlayer = Residence.getPlayerManager().getResidencePlayer(player)).getGroup()).canSetEnterLeaveMessages() && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Residence_OwnerNoPermission, new Object[0]);
            return;
        }
        if (!this.perms.hasResidencePermission((CommandSender)player, false) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return;
        }
        if (enter) {
            this.setEnterMessage(message2);
        } else {
            this.setLeaveMessage(message2);
        }
        Residence.msg((CommandSender)player, lm.Residence_MessageChange, new Object[0]);
    }

    public Location getMiddleFreeLoc(Location insideLoc, Player player) {
        CuboidArea area2 = this.getAreaByLoc(insideLoc);
        if (area2 == null) {
            return insideLoc;
        }
        int y = area2.getHighLoc().getBlockY();
        int x = area2.getLowLoc().getBlockX() + area2.getXSize() / 2;
        int z = area2.getLowLoc().getBlockZ() + area2.getZSize() / 2;
        Location newLoc = new Location(area2.getWorld(), (double)x + 0.5, (double)y, (double)z + 0.5);
        boolean found = false;
        int it = 0;
        int maxIt = area2.getWorld().getMaxHeight() - 63;
        while (it < maxIt) {
            ++it;
            newLoc.setY(newLoc.getY() - 1.0);
            if (newLoc.getBlockY() < 63) break;
            Block block = newLoc.getBlock();
            Block block2 = newLoc.clone().add(0.0, 1.0, 0.0).getBlock();
            Block block3 = newLoc.clone().add(0.0, -1.0, 0.0).getBlock();
            if (!Residence.getNms().isEmptyBlock(block) || !Residence.getNms().isEmptyBlock(block2) || Residence.getNms().isEmptyBlock(block3)) continue;
            found = true;
            break;
        }
        if (found) {
            return newLoc;
        }
        return this.getOutsideFreeLoc(insideLoc, player);
    }

    public Location getOutsideFreeLoc(Location insideLoc, Player player) {
        CuboidArea area2 = this.getAreaByLoc(insideLoc);
        if (area2 == null) {
            return insideLoc;
        }
        ArrayList<RandomLoc> randomLocList = new ArrayList<RandomLoc>();
        int z = -1;
        while (z < area2.getZSize() + 1) {
            randomLocList.add(new RandomLoc(area2.getLowLoc().getX(), 0.0, area2.getLowLoc().getZ() + (double)z));
            randomLocList.add(new RandomLoc(area2.getLowLoc().getX() + (double)area2.getXSize(), 0.0, area2.getLowLoc().getZ() + (double)z));
            ++z;
        }
        int x = -1;
        while (x < area2.getXSize() + 1) {
            randomLocList.add(new RandomLoc(area2.getLowLoc().getX() + (double)x, 0.0, area2.getLowLoc().getZ()));
            randomLocList.add(new RandomLoc(area2.getLowLoc().getX() + (double)x, 0.0, area2.getLowLoc().getZ() + (double)area2.getZSize()));
            ++x;
        }
        Location loc = insideLoc.clone();
        boolean found = false;
        int it = 0;
        int maxIt = 30;
        while (!found && it < maxIt) {
            ClaimedResidence res;
            ++it;
            Random ran = new Random(System.currentTimeMillis());
            if (randomLocList.isEmpty()) break;
            int check2 = ran.nextInt(randomLocList.size());
            RandomLoc place = (RandomLoc)randomLocList.get(check2);
            randomLocList.remove(check2);
            double x2 = place.getX();
            double z2 = place.getZ();
            loc.setX(x2);
            loc.setZ(z2);
            loc.setY((double)area2.getHighLoc().getBlockY());
            int max = area2.getHighLoc().getBlockY();
            int i = max = loc.getWorld().getEnvironment() == World.Environment.NETHER ? 100 : max;
            while ((double)i > area2.getLowLoc().getY()) {
                loc.setY((double)i);
                Block block = loc.getBlock();
                Block block2 = loc.clone().add(0.0, 1.0, 0.0).getBlock();
                Block block3 = loc.clone().add(0.0, -1.0, 0.0).getBlock();
                if (!Residence.getNms().isEmptyBlock(block3) && Residence.getNms().isEmptyBlock(block) && Residence.getNms().isEmptyBlock(block2)) break;
                --i;
            }
            if (!Residence.getNms().isEmptyBlock(loc.getBlock()) || loc.clone().add(0.0, -1.0, 0.0).getBlock().getState().getType() == Material.LAVA || loc.clone().add(0.0, -1.0, 0.0).getBlock().getState().getType() == Material.STATIONARY_LAVA || loc.clone().add(0.0, -1.0, 0.0).getBlock().getState().getType() == Material.WATER || loc.clone().add(0.0, -1.0, 0.0).getBlock().getState().getType() == Material.STATIONARY_WATER || (res = Residence.getResidenceManager().getByLoc(loc)) != null && player != null && !res.getPermissions().playerHas(player, Flags.tp, FlagPermissions.FlagCombo.TrueOrNone) && !player.hasPermission("residence.admin.tp")) continue;
            found = true;
            loc.setY(loc.getY() + 2.0);
            loc.add(0.5, 0.0, 0.5);
            break;
        }
        if (!found && Residence.getConfigManager().getKickLocation() != null) {
            return Residence.getConfigManager().getKickLocation();
        }
        return loc;
    }

    public CuboidArea getAreaByLoc(Location loc) {
        for (CuboidArea thisarea : this.areas.values()) {
            if (!thisarea.containsLoc(loc)) continue;
            return thisarea;
        }
        return null;
    }

    public String[] listSubzones() {
        String[] list2 = new String[this.subzones.size()];
        int i = 0;
        Iterator<String> iterator = this.subzones.keySet().iterator();
        while (iterator.hasNext()) {
            String res;
            list2[i] = res = iterator.next();
            ++i;
        }
        return list2;
    }

    public List<ClaimedResidence> getSubzones() {
        ArrayList<ClaimedResidence> list2 = new ArrayList<ClaimedResidence>();
        for (Map.Entry<String, ClaimedResidence> res : this.subzones.entrySet()) {
            list2.add(res.getValue());
        }
        return list2;
    }

    public void printSubzoneList(Player player, int page) {
        ArrayList<String> temp = new ArrayList<String>();
        for (Map.Entry<String, ClaimedResidence> sz : this.subzones.entrySet()) {
            temp.add((Object)ChatColor.GREEN + sz.getKey() + (Object)ChatColor.YELLOW + " - " + Residence.msg(lm.General_Owner, sz.getValue().getOwner()));
        }
        Residence.getInfoPageManager().printInfo((CommandSender)player, Residence.msg(lm.General_Subzones, new Object[0]), temp, page);
    }

    public void printAreaList(Player player, int page) {
        ArrayList<String> temp = new ArrayList<String>();
        for (String area2 : this.areas.keySet()) {
            temp.add(area2);
        }
        Residence.getInfoPageManager().printInfo((CommandSender)player, Residence.msg(lm.General_PhysicalAreas, new Object[0]), temp, page);
    }

    public void printAdvancedAreaList(Player player, int page) {
        ArrayList<String> temp = new ArrayList<String>();
        for (Map.Entry<String, CuboidArea> entry : this.areas.entrySet()) {
            CuboidArea a = entry.getValue();
            Location h = a.getHighLoc();
            Location l = a.getLowLoc();
            temp.add((Object)ChatColor.GREEN + "{" + (Object)ChatColor.YELLOW + "ID:" + (Object)ChatColor.RED + entry.getKey() + " " + (Object)ChatColor.YELLOW + "P1:" + (Object)ChatColor.RED + "(" + h.getBlockX() + "," + h.getBlockY() + "," + h.getBlockZ() + ") " + (Object)ChatColor.YELLOW + "P2:" + (Object)ChatColor.RED + "(" + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + ") " + (Object)ChatColor.YELLOW + "(Size:" + (Object)ChatColor.RED + a.getSize() + (Object)ChatColor.YELLOW + ")" + (Object)ChatColor.GREEN + "} ");
        }
        Residence.getInfoPageManager().printInfo((CommandSender)player, Residence.msg(lm.General_PhysicalAreas, new Object[0]), temp, page);
    }

    public String[] getAreaList() {
        String[] arealist = new String[this.areas.size()];
        int i = 0;
        for (Map.Entry<String, CuboidArea> entry : this.areas.entrySet()) {
            arealist[i] = entry.getKey();
            ++i;
        }
        return arealist;
    }

    public int getZoneDepth() {
        int count = 0;
        ClaimedResidence res = this.parent;
        while (res != null) {
            ++count;
            res = res.getParent();
        }
        return count;
    }

    public void setTpLoc(Player player, boolean resadmin2) {
        if (!this.perms.hasResidencePermission((CommandSender)player, false) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return;
        }
        if (!this.containsLoc(player.getLocation())) {
            Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
            return;
        }
        this.tpLoc = player.getLocation();
        Residence.msg((CommandSender)player, lm.Residence_SetTeleportLocation, new Object[0]);
    }

    public int isSafeTp(Player player) {
        if (player.getAllowFlight()) {
            return 0;
        }
        if (player.getGameMode() == GameMode.CREATIVE) {
            return 0;
        }
        if (Residence.getNms().isSpectator(player.getGameMode())) {
            return 0;
        }
        if (this.tpLoc == null) {
            return 0;
        }
        Location tempLoc = new Location(this.tpLoc.getWorld(), this.tpLoc.getX(), this.tpLoc.getY(), this.tpLoc.getZ());
        int from = (int)tempLoc.getY();
        int fallDistance = 0;
        int i = 0;
        while (i < 255) {
            tempLoc.setY((double)(from - i));
            Block block = tempLoc.getBlock();
            if (!Residence.getNms().isEmptyBlock(block)) break;
            ++fallDistance;
            ++i;
        }
        return fallDistance;
    }

    public void tpToResidence(Player reqPlayer, Player targetPlayer, boolean resadmin2) {
        int distance;
        boolean isAdmin = Residence.isResAdminOn(reqPlayer);
        if (!(resadmin2 || isAdmin || reqPlayer.hasPermission("residence.tpbypass") || this.isOwner(targetPlayer))) {
            ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(reqPlayer);
            PermissionGroup group = rPlayer.getGroup();
            if (!group.hasTpAccess()) {
                Residence.msg((CommandSender)reqPlayer, lm.General_TeleportDeny, new Object[0]);
                return;
            }
            if (!reqPlayer.equals((Object)targetPlayer)) {
                Residence.msg((CommandSender)reqPlayer, lm.General_NoPermission, new Object[0]);
                return;
            }
            if (!this.perms.playerHas(reqPlayer, Flags.tp, FlagPermissions.FlagCombo.TrueOrNone)) {
                Residence.msg((CommandSender)reqPlayer, lm.Residence_TeleportNoFlag, new Object[0]);
                return;
            }
            if (!this.perms.playerHas(reqPlayer, Flags.move, FlagPermissions.FlagCombo.TrueOrNone)) {
                Residence.msg((CommandSender)reqPlayer, lm.Residence_MoveDeny, this.getName());
                return;
            }
        }
        if (!Residence.getTeleportMap().containsKey(targetPlayer.getName()) && !isAdmin && (distance = this.isSafeTp(reqPlayer)) > 6) {
            Residence.msg((CommandSender)reqPlayer, lm.General_TeleportConfirm, distance);
            Residence.getTeleportMap().put(reqPlayer.getName(), this);
            return;
        }
        if (Residence.getConfigManager().getTeleportDelay() > 0 && !isAdmin && !resadmin2) {
            Residence.msg((CommandSender)reqPlayer, lm.General_TeleportStarted, this.getName(), Residence.getConfigManager().getTeleportDelay());
            if (Residence.getConfigManager().isTeleportTitleMessage()) {
                this.TpTimer(reqPlayer, Residence.getConfigManager().getTeleportDelay());
            }
            Residence.getTeleportDelayMap().add(reqPlayer.getName());
        }
        if (this.tpLoc != null) {
            if (Residence.getConfigManager().getTeleportDelay() > 0 && !isAdmin) {
                this.performDelaydTp(this.tpLoc, targetPlayer, reqPlayer, true);
            } else {
                this.performInstantTp(this.tpLoc, targetPlayer, reqPlayer, true);
            }
        } else {
            CuboidArea area2 = this.areas.values().iterator().next();
            if (area2 == null) {
                reqPlayer.sendMessage((Object)ChatColor.RED + "Could not find area to teleport to...");
                Residence.getTeleportDelayMap().remove(targetPlayer.getName());
                return;
            }
            Location targloc = this.getMiddleFreeLoc(area2.getHighLoc(), targetPlayer);
            if (Residence.getConfigManager().getTeleportDelay() > 0 && !isAdmin) {
                this.performDelaydTp(targloc, targetPlayer, reqPlayer, true);
            } else {
                this.performInstantTp(targloc, targetPlayer, reqPlayer, true);
            }
        }
    }

    public void TpTimer(final Player player, final int t) {
        Residence.getAB().sendTitle(player, Residence.msg(lm.General_TeleportTitle, new Object[0]), Residence.msg(lm.General_TeleportTitleTime, t));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (!Residence.getTeleportDelayMap().contains(player.getName())) {
                    return;
                }
                if (t > 1) {
                    ClaimedResidence.this.TpTimer(player, t - 1);
                }
            }
        }, 20);
    }

    public void performDelaydTp(final Location targloc, final Player targetPlayer, Player reqPlayer, final boolean near) {
        ResidenceTPEvent tpevent = new ResidenceTPEvent(this, targloc, targetPlayer, reqPlayer);
        Residence.getServ().getPluginManager().callEvent((Event)tpevent);
        if (tpevent.isCancelled()) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (!Residence.getTeleportDelayMap().contains(targetPlayer.getName()) && Residence.getConfigManager().getTeleportDelay() > 0) {
                    return;
                }
                if (Residence.getTeleportDelayMap().contains(targetPlayer.getName())) {
                    Residence.getTeleportDelayMap().remove(targetPlayer.getName());
                }
                targetPlayer.teleport(targloc);
                if (near) {
                    Residence.msg((CommandSender)targetPlayer, lm.Residence_TeleportNear, new Object[0]);
                } else {
                    Residence.msg((CommandSender)targetPlayer, lm.General_TeleportSuccess, new Object[0]);
                }
            }
        }, (long)Residence.getConfigManager().getTeleportDelay() * 20);
    }

    private void performInstantTp(Location targloc, Player targetPlayer, Player reqPlayer, boolean near) {
        ResidenceTPEvent tpevent = new ResidenceTPEvent(this, targloc, targetPlayer, reqPlayer);
        Residence.getServ().getPluginManager().callEvent((Event)tpevent);
        if (!tpevent.isCancelled()) {
            targetPlayer.teleport(targloc);
            if (near) {
                Residence.msg((CommandSender)targetPlayer, lm.Residence_TeleportNear, new Object[0]);
            } else {
                Residence.msg((CommandSender)targetPlayer, lm.General_TeleportSuccess, new Object[0]);
            }
        }
    }

    public String getAreaIDbyLoc(Location loc) {
        for (Map.Entry<String, CuboidArea> area2 : this.areas.entrySet()) {
            if (!area2.getValue().containsLoc(loc)) continue;
            return area2.getKey();
        }
        return null;
    }

    public CuboidArea getCuboidAreabyName(String name) {
        for (Map.Entry<String, CuboidArea> area2 : this.areas.entrySet()) {
            if (!area2.getKey().equals(name)) continue;
            return area2.getValue();
        }
        return null;
    }

    public void removeArea(String id) {
        Residence.getResidenceManager().removeChunkList(this.getName());
        this.areas.remove(id);
        Residence.getResidenceManager().calculateChunks(this.getName());
    }

    public void removeArea(Player player, String id, boolean resadmin2) {
        if (this.getPermissions().hasResidencePermission((CommandSender)player, true) || resadmin2) {
            if (!this.areas.containsKey(id)) {
                Residence.msg((CommandSender)player, lm.Area_NonExist, new Object[0]);
                return;
            }
            if (this.areas.size() == 1 && !Residence.getConfigManager().allowEmptyResidences()) {
                Residence.msg((CommandSender)player, lm.Area_RemoveLast, new Object[0]);
                return;
            }
            ResidenceAreaDeleteEvent resevent = new ResidenceAreaDeleteEvent(player, this, player == null ? ResidenceDeleteEvent.DeleteCause.OTHER : ResidenceDeleteEvent.DeleteCause.PLAYER_DELETE);
            Residence.getServ().getPluginManager().callEvent((Event)resevent);
            if (resevent.isCancelled()) {
                return;
            }
            this.removeArea(id);
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Area_Remove, new Object[0]);
            }
        } else if (player != null) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
        }
    }

    public Map<String, Object> save() {
        Map<String, Object> map;
        HashMap<String, Object> root = new HashMap<String, Object>();
        HashMap<String, Map<String, Object>> areamap = new HashMap<String, Map<String, Object>>();
        root.put("CapitalizedName", this.resName);
        if (this.mainRes) {
            root.put("MainResidence", this.mainRes);
        }
        if (this.createTime != 0) {
            root.put("CreatedOn", this.createTime);
        }
        if (this.enterMessage != null) {
            root.put("EnterMessage", this.enterMessage);
        }
        if (this.leaveMessage != null) {
            root.put("LeaveMessage", this.leaveMessage);
        }
        if (this.ShopDesc != null) {
            root.put("ShopDescription", this.ShopDesc);
        }
        if (this.bank.getStoredMoney() != 0) {
            root.put("StoredMoney", this.bank.getStoredMoney());
        }
        if (this.BlockSellPrice != 0.0) {
            root.put("BlockSellPrice", this.BlockSellPrice);
        }
        if (!this.ChatPrefix.equals("")) {
            root.put("ChatPrefix", this.ChatPrefix);
        }
        if (!this.ChannelColor.name().equals(Residence.getConfigManager().getChatColor().name()) && !this.ChannelColor.name().equals("WHITE")) {
            root.put("ChannelColor", this.ChannelColor.name());
        }
        if (!(map = this.blacklist.save()).isEmpty()) {
            root.put("BlackList", map);
        }
        if (!(map = this.ignorelist.save()).isEmpty()) {
            root.put("IgnoreList", map);
        }
        for (Map.Entry<String, CuboidArea> entry : this.areas.entrySet()) {
            areamap.put(entry.getKey(), entry.getValue().save());
        }
        root.put("Areas", areamap);
        HashMap<String, Map<String, Object>> subzonemap = new HashMap<String, Map<String, Object>>();
        for (Map.Entry<String, ClaimedResidence> sz : this.subzones.entrySet()) {
            subzonemap.put(sz.getKey(), sz.getValue().save());
        }
        if (!subzonemap.isEmpty()) {
            root.put("Subzones", subzonemap);
        }
        root.put("Permissions", this.perms.save());
        if (!this.cmdBlackList.isEmpty()) {
            root.put("cmdBlackList", this.cmdBlackList);
        }
        if (!this.cmdWhiteList.isEmpty()) {
            root.put("cmdWhiteList", this.cmdWhiteList);
        }
        if (this.tpLoc != null) {
            HashMap<String, Double> tpmap = new HashMap<String, Double>();
            tpmap.put("X", ClaimedResidence.convertDouble(this.tpLoc.getX()));
            tpmap.put("Y", ClaimedResidence.convertDouble(this.tpLoc.getY()));
            tpmap.put("Z", ClaimedResidence.convertDouble(this.tpLoc.getZ()));
            tpmap.put("Pitch", ClaimedResidence.convertDouble(this.tpLoc.getPitch()));
            tpmap.put("Yaw", ClaimedResidence.convertDouble(this.tpLoc.getYaw()));
            root.put("TPLoc", tpmap);
        }
        return root;
    }

    private static double convertDouble(double d) {
        return ClaimedResidence.convertDouble(String.valueOf(d));
    }

    private static double convertDouble(String dString) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.getDefault()));
        dString = dString.replace(",", ".");
        Double d = 0.0;
        try {
            d = Double.valueOf(dString);
            d = Double.valueOf(formatter.format(d));
        }
        catch (Exception exception) {
            // empty catch block
        }
        return d;
    }

    public static ClaimedResidence load(String worldName, Map<String, Object> root, ClaimedResidence parent, Residence plugin) throws Exception {
        ClaimedResidence res = new ClaimedResidence(plugin);
        if (root == null) {
            throw new Exception("Null residence!");
        }
        if (root.containsKey("CapitalizedName")) {
            res.resName = (String)root.get("CapitalizedName");
        }
        res.createTime = root.containsKey("CreatedOn") ? (Long)root.get("CreatedOn") : System.currentTimeMillis();
        if (root.containsKey("ShopDescription")) {
            res.setShopDesc((String)root.get("ShopDescription"));
        }
        if (root.containsKey("StoredMoney")) {
            res.bank.setStoredMoney((Integer)root.get("StoredMoney"));
        }
        if (root.containsKey("BlackList")) {
            res.blacklist = ResidenceItemList.load(res, (Map)root.get("BlackList"));
        }
        if (root.containsKey("IgnoreList")) {
            res.ignorelist = ResidenceItemList.load(res, (Map)root.get("IgnoreList"));
        }
        Map areamap = (Map)root.get("Areas");
        res.perms = ResidencePermissions.load(worldName, res, (Map)root.get("Permissions"));
        if (res.getPermissions().ownerLastKnownName == null) {
            return null;
        }
        if (root.containsKey("MainResidence")) {
            res.mainRes = (Boolean)root.get("MainResidence");
        }
        res.BlockSellPrice = root.containsKey("BlockSellPrice") ? (Double)root.get("BlockSellPrice") : Double.valueOf(0.0);
        World world = Residence.getServ().getWorld(res.perms.getWorld());
        if (world == null) {
            throw new Exception("Cant Find World: " + res.perms.getWorld());
        }
        for (Map.Entry map : areamap.entrySet()) {
            res.areas.put((String)map.getKey(), CuboidArea.load((Map)map.getValue(), world));
        }
        if (root.containsKey("Subzones")) {
            Map subzonemap = (Map)root.get("Subzones");
            for (Map.Entry map : subzonemap.entrySet()) {
                ClaimedResidence subres = ClaimedResidence.load(worldName, (Map)map.getValue(), res, plugin);
                if (subres == null) continue;
                if (subres.getResidenceName() == null) {
                    subres.setName((String)map.getKey());
                }
                if (Residence.getConfigManager().flagsInherit()) {
                    subres.getPermissions().setParent(res.getPermissions());
                }
                res.subzones.put(((String)map.getKey()).toLowerCase(), subres);
            }
        }
        if (root.containsKey("EnterMessage")) {
            res.enterMessage = (String)root.get("EnterMessage");
        }
        if (root.containsKey("LeaveMessage")) {
            res.leaveMessage = (String)root.get("LeaveMessage");
        }
        res.parent = parent;
        Map tploc = (Map)root.get("TPLoc");
        if (tploc != null) {
            double pitch = 0.0;
            double yaw = 0.0;
            if (tploc.containsKey("Yaw")) {
                yaw = ClaimedResidence.convertDouble(tploc.get("Yaw").toString());
            }
            if (tploc.containsKey("Pitch")) {
                pitch = ClaimedResidence.convertDouble(tploc.get("Pitch").toString());
            }
            res.tpLoc = new Location(world, ClaimedResidence.convertDouble(tploc.get("X").toString()), ClaimedResidence.convertDouble(tploc.get("Y").toString()), ClaimedResidence.convertDouble(tploc.get("Z").toString()));
            res.tpLoc.setPitch((float)pitch);
            res.tpLoc.setYaw((float)yaw);
        }
        if (root.containsKey("cmdBlackList")) {
            res.cmdBlackList = (List)root.get("cmdBlackList");
        }
        if (root.containsKey("cmdWhiteList")) {
            res.cmdWhiteList = (List)root.get("cmdWhiteList");
        }
        if (root.containsKey("ChatPrefix")) {
            res.ChatPrefix = (String)root.get("ChatPrefix");
        }
        res.ChannelColor = root.containsKey("ChannelColor") ? ChatColor.valueOf((String)((String)root.get("ChannelColor"))) : Residence.getConfigManager().getChatColor();
        return res;
    }

    public int getAreaCount() {
        return this.areas.size();
    }

    public boolean renameSubzone(String oldName, String newName) {
        return this.renameSubzone(null, oldName, newName, true);
    }

    public boolean renameSubzone(Player player, String oldName, String newName, boolean resadmin2) {
        if (!Residence.validName(newName)) {
            Residence.msg((CommandSender)player, lm.Invalid_NameCharacters, new Object[0]);
            return false;
        }
        if (oldName == null) {
            return false;
        }
        if (newName == null) {
            return false;
        }
        String newN = newName;
        oldName = oldName.toLowerCase();
        newName = newName.toLowerCase();
        ClaimedResidence res = this.subzones.get(oldName);
        if (res == null) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Invalid_Subzone, new Object[0]);
            }
            return false;
        }
        if (player != null && !res.getPermissions().hasResidencePermission((CommandSender)player, true) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return false;
        }
        if (this.subzones.containsKey(newName)) {
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Subzone_Exists, newName);
            }
            return false;
        }
        res.setName(newN);
        this.subzones.put(newName, res);
        this.subzones.remove(oldName);
        if (player != null) {
            Residence.msg((CommandSender)player, lm.Subzone_Rename, oldName, newName);
        }
        return true;
    }

    public boolean renameArea(String oldName, String newName) {
        return this.renameArea(null, oldName, newName, true);
    }

    public boolean renameArea(Player player, String oldName, String newName, boolean resadmin2) {
        if (!Residence.validName(newName)) {
            Residence.msg((CommandSender)player, lm.Invalid_NameCharacters, new Object[0]);
            return false;
        }
        if (player == null || this.perms.hasResidencePermission((CommandSender)player, true) || resadmin2) {
            if (this.areas.containsKey(newName)) {
                if (player != null) {
                    Residence.msg((CommandSender)player, lm.Area_Exists, new Object[0]);
                }
                return false;
            }
            CuboidArea area2 = this.areas.get(oldName);
            if (area2 == null) {
                if (player != null) {
                    Residence.msg((CommandSender)player, lm.Area_InvalidName, new Object[0]);
                }
                return false;
            }
            this.areas.put(newName, area2);
            this.areas.remove(oldName);
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Area_Rename, oldName, newName);
            }
            return true;
        }
        Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
        return false;
    }

    public CuboidArea getArea(String name) {
        return this.areas.get(name);
    }

    public String getName() {
        return Residence.getResidenceManager().getNameByRes(this);
    }

    public void remove() {
        String name = this.getName();
        if (name != null) {
            Residence.getResidenceManager().removeResidence(name);
            Residence.getResidenceManager().removeChunkList(name);
        }
    }

    public ResidenceBank getBank() {
        return this.bank;
    }

    public String getWorld() {
        return this.perms.getWorld();
    }

    public ResidencePlayer getRPlayer() {
        return Residence.getPlayerManager().getResidencePlayer(this.getPermissions().getOwner());
    }

    public PermissionGroup getOwnerGroup() {
        return this.getRPlayer().getGroup(this.getPermissions().getWorld());
    }

    public String getOwner() {
        return this.perms.getOwner();
    }

    public boolean isOwner(String name) {
        Player player = Bukkit.getPlayer((String)name);
        if (player != null) {
            return this.isOwner(player);
        }
        return this.perms.getOwner().equalsIgnoreCase(name);
    }

    public boolean isOwner(Player p) {
        if (Residence.getConfigManager().isOfflineMode()) {
            return this.perms.getOwner().equals(p.getName());
        }
        return this.perms.getOwnerUUID().equals(p.getUniqueId());
    }

    public boolean isOwner(CommandSender sender) {
        if (Residence.getConfigManager().isOfflineMode()) {
            return this.perms.getOwner().equals(sender.getName());
        }
        if (sender instanceof Player) {
            return this.perms.getOwnerUUID().equals(((Player)sender).getUniqueId());
        }
        return true;
    }

    public void setChatPrefix(String ChatPrefix) {
        this.ChatPrefix = ChatPrefix;
    }

    public String getChatPrefix() {
        return this.ChatPrefix == null ? "" : this.ChatPrefix;
    }

    public void setChannelColor(ChatColor ChannelColor) {
        this.ChannelColor = ChannelColor;
    }

    public ChatChannel getChatChannel() {
        return Residence.getChatManager().getChannel(this.getName());
    }

    public ChatColor getChannelColor() {
        return this.ChannelColor;
    }

    public UUID getOwnerUUID() {
        return this.perms.getOwnerUUID();
    }

    public ResidenceItemList getItemBlacklist() {
        return this.blacklist;
    }

    public ResidenceItemList getItemIgnoreList() {
        return this.ignorelist;
    }

    public List<String> getCmdBlackList() {
        return this.cmdBlackList;
    }

    public List<String> getCmdWhiteList() {
        return this.cmdWhiteList;
    }

    public boolean addCmdBlackList(String cmd2) {
        if (cmd2.contains("/")) {
            cmd2 = cmd2.replace("/", "");
        }
        if (!this.cmdBlackList.contains(cmd2.toLowerCase())) {
            this.cmdBlackList.add(cmd2.toLowerCase());
            return true;
        }
        this.cmdBlackList.remove(cmd2.toLowerCase());
        return false;
    }

    public boolean addCmdWhiteList(String cmd2) {
        if (cmd2.contains("/")) {
            cmd2 = cmd2.replace("/", "");
        }
        if (!this.cmdWhiteList.contains(cmd2.toLowerCase())) {
            this.cmdWhiteList.add(cmd2.toLowerCase());
            return true;
        }
        this.cmdWhiteList.remove(cmd2.toLowerCase());
        return false;
    }

    public Double getBlockSellPrice() {
        return this.BlockSellPrice;
    }

    public ArrayList<Player> getPlayersInResidence() {
        ArrayList<Player> within = new ArrayList<Player>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!this.containsLoc(player.getLocation())) continue;
            within.add(player);
        }
        return within;
    }

    public List<ShopVote> GetShopVotes() {
        return this.ShopVoteList;
    }

    public void clearShopVotes() {
        this.ShopVoteList.clear();
    }

    public void addShopVote(List<ShopVote> ShopVotes) {
        this.ShopVoteList.addAll(ShopVotes);
    }

    public void addShopVote(ShopVote ShopVote2) {
        this.ShopVoteList.add(ShopVote2);
    }

}

