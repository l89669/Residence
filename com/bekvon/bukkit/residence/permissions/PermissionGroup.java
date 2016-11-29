/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 */
package com.bekvon.bukkit.residence.permissions;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class PermissionGroup {
    protected int xmax;
    protected int ymax;
    protected int zmax;
    protected int xmin;
    protected int ymin;
    protected int zmin;
    protected int Subzonexmax;
    protected int Subzoneymax;
    protected int Subzonezmax;
    protected int Subzonexmin;
    protected int Subzoneymin;
    protected int Subzonezmin;
    protected int resmax;
    protected double costperarea;
    protected double sellperarea = 0.0;
    protected boolean tpaccess;
    protected int subzonedepth;
    protected FlagPermissions flagPerms = new FlagPermissions();
    protected Map<String, Boolean> creatorDefaultFlags = new HashMap<String, Boolean>();
    protected Map<String, Map<String, Boolean>> groupDefaultFlags = new HashMap<String, Map<String, Boolean>>();
    protected Map<String, Boolean> residenceDefaultFlags = new HashMap<String, Boolean>();
    protected boolean messageperms;
    protected String defaultEnterMessage;
    protected String defaultLeaveMessage;
    protected int maxLeaseTime;
    protected int leaseGiveTime;
    protected double renewcostperarea;
    protected boolean canBuy;
    protected boolean canSell;
    protected boolean buyIgnoreLimits;
    protected boolean cancreate;
    protected String groupname;
    protected int maxPhysical;
    protected boolean unstuck;
    protected boolean kick;
    protected int minHeight;
    protected int maxHeight;
    protected int maxRents;
    protected int MaxRentDays = -1;
    protected int maxRentables;
    protected boolean selectCommandAccess;
    protected boolean itemListAccess;
    protected int priority = 0;

    public PermissionGroup(String name) {
        this.groupname = name;
    }

    public void setPriority(int number) {
        this.priority = number;
    }

    public int getPriority() {
        return this.priority;
    }

    public PermissionGroup(String name, ConfigurationSection node) {
        this(name);
        this.parseGroup(node);
    }

    public PermissionGroup(String name, ConfigurationSection node, FlagPermissions parentFlagPerms) {
        this(name, node);
        this.flagPerms.setParent(parentFlagPerms);
    }

    public PermissionGroup(String name, ConfigurationSection node, FlagPermissions parentFlagPerms, int priority) {
        this(name, node);
        this.flagPerms.setParent(parentFlagPerms);
        this.priority = priority;
    }

    private void parseGroup(ConfigurationSection limits2) {
        boolean access;
        if (limits2 == null) {
            return;
        }
        this.cancreate = limits2.getBoolean("Residence.CanCreate", false);
        this.resmax = limits2.getInt("Residence.MaxResidences", 0);
        this.maxPhysical = limits2.getInt("Residence.MaxAreasPerResidence", 2);
        this.xmax = limits2.getInt("Residence.MaxEastWest", 0);
        this.xmin = limits2.getInt("Residence.MinEastWest", 0);
        this.xmin = this.xmin > this.xmax ? this.xmax : this.xmin;
        this.ymax = limits2.getInt("Residence.MaxUpDown", 0);
        this.ymin = limits2.getInt("Residence.MinUpDown", 0);
        this.ymin = this.ymin > this.ymax ? this.ymax : this.ymin;
        this.zmax = limits2.getInt("Residence.MaxNorthSouth", 0);
        this.zmin = limits2.getInt("Residence.MinNorthSouth", 0);
        this.zmin = this.zmin > this.zmax ? this.zmax : this.zmin;
        this.minHeight = limits2.getInt("Residence.MinHeight", 0);
        this.maxHeight = limits2.getInt("Residence.MaxHeight", 255);
        this.tpaccess = limits2.getBoolean("Residence.CanTeleport", false);
        this.subzonedepth = limits2.getInt("Residence.SubzoneDepth", 0);
        this.Subzonexmax = limits2.getInt("Residence.SubzoneMaxEastWest", this.xmax);
        this.Subzonexmax = this.xmax < this.Subzonexmax ? this.xmax : this.Subzonexmax;
        this.Subzonexmin = limits2.getInt("Residence.SubzoneMinEastWest", 0);
        this.Subzonexmin = this.Subzonexmin > this.Subzonexmax ? this.Subzonexmax : this.Subzonexmin;
        this.Subzoneymax = limits2.getInt("Residence.SubzoneMaxUpDown", this.ymax);
        this.Subzoneymax = this.ymax < this.Subzoneymax ? this.ymax : this.Subzoneymax;
        this.Subzoneymin = limits2.getInt("Residence.SubzoneMinUpDown", 0);
        this.Subzoneymin = this.Subzoneymin > this.Subzoneymax ? this.Subzoneymax : this.Subzoneymin;
        this.Subzonezmax = limits2.getInt("Residence.SubzoneMaxNorthSouth", this.zmax);
        this.Subzonezmax = this.zmax < this.Subzonezmax ? this.zmax : this.Subzonezmax;
        this.Subzonezmin = limits2.getInt("Residence.SubzoneMinNorthSouth", 0);
        this.Subzonezmin = this.Subzonezmin > this.Subzonezmax ? this.Subzonezmax : this.Subzonezmin;
        this.messageperms = limits2.getBoolean("Messaging.CanChange", false);
        this.defaultEnterMessage = limits2.getString("Messaging.DefaultEnter", null);
        this.defaultLeaveMessage = limits2.getString("Messaging.DefaultLeave", null);
        this.maxLeaseTime = limits2.getInt("Lease.MaxDays", 16);
        this.leaseGiveTime = limits2.getInt("Lease.RenewIncrement", 14);
        this.maxRents = limits2.getInt("Rent.MaxRents", 0);
        if (limits2.contains("Rent.MaxRentDays")) {
            this.MaxRentDays = limits2.getInt("Rent.MaxRentDays", -1);
        }
        this.maxRentables = limits2.getInt("Rent.MaxRentables", 0);
        this.renewcostperarea = limits2.getDouble("Economy.RenewCost", 0.02);
        this.canBuy = limits2.getBoolean("Economy.CanBuy", false);
        this.canSell = limits2.getBoolean("Economy.CanSell", false);
        this.buyIgnoreLimits = limits2.getBoolean("Economy.IgnoreLimits", false);
        this.costperarea = limits2.getDouble("Economy.BuyCost", 0.0);
        if (limits2.contains("Economy.SellCost")) {
            this.sellperarea = limits2.getDouble("Economy.SellCost", 0.0);
        }
        this.unstuck = limits2.getBoolean("Residence.Unstuck", false);
        this.kick = limits2.getBoolean("Residence.Kick", false);
        this.selectCommandAccess = limits2.getBoolean("Residence.SelectCommandAccess", true);
        this.itemListAccess = limits2.getBoolean("Residence.ItemListAccess", true);
        ConfigurationSection node = limits2.getConfigurationSection("Flags.Permission");
        Set flags2 = null;
        if (node != null) {
            flags2 = node.getKeys(false);
        }
        if (flags2 != null) {
            Iterator flagit = flags2.iterator();
            while (flagit.hasNext()) {
                String flagname;
                access = limits2.getBoolean("Flags.Permission." + (flagname = (String)flagit.next()), false);
                this.flagPerms.setFlag(flagname, access ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE);
            }
        }
        if ((node = limits2.getConfigurationSection("Flags.CreatorDefault")) != null) {
            flags2 = node.getKeys(false);
        }
        if (flags2 != null) {
            for (String flagname : flags2) {
                access = limits2.getBoolean("Flags.CreatorDefault." + flagname, false);
                this.creatorDefaultFlags.put(flagname, access);
            }
        }
        if ((node = limits2.getConfigurationSection("Flags.Default")) != null) {
            flags2 = node.getKeys(false);
        }
        if (flags2 != null) {
            for (String flagname : flags2) {
                access = limits2.getBoolean("Flags.Default." + flagname, false);
                this.residenceDefaultFlags.put(flagname, access);
            }
        }
        node = limits2.getConfigurationSection("Flags.GroupDefault");
        Set groupDef = null;
        if (node != null) {
            groupDef = node.getKeys(false);
        }
        if (groupDef != null) {
            for (String name : groupDef) {
                HashMap<String, Boolean> gflags = new HashMap<String, Boolean>();
                flags2 = limits2.getConfigurationSection("Flags.GroupDefault." + name).getKeys(false);
                for (String flagname : flags2) {
                    boolean access2 = limits2.getBoolean("Flags.GroupDefault." + name + "." + flagname, false);
                    gflags.put(flagname, access2);
                }
                this.groupDefaultFlags.put(name, gflags);
            }
        }
    }

    public String getGroupName() {
        return this.groupname;
    }

    public int getMaxX() {
        return this.xmax;
    }

    public int getMaxY() {
        return this.ymax;
    }

    public int getMaxZ() {
        return this.zmax;
    }

    public int getMinX() {
        return this.xmin;
    }

    public int getMinY() {
        return this.ymin;
    }

    public int getMinZ() {
        return this.zmin;
    }

    public int getSubzoneMaxX() {
        return this.Subzonexmax;
    }

    public int getSubzoneMaxY() {
        return this.Subzoneymax;
    }

    public int getSubzoneMaxZ() {
        return this.Subzonezmax;
    }

    public int getSubzoneMinX() {
        return this.Subzonexmin;
    }

    public int getSubzoneMinY() {
        return this.Subzoneymin;
    }

    public int getSubzoneMinZ() {
        return this.Subzonezmin;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int getMaxZones() {
        return this.resmax;
    }

    public double getCostPerBlock() {
        return this.costperarea;
    }

    public double getSellPerBlock() {
        return this.sellperarea;
    }

    public boolean hasTpAccess() {
        return this.tpaccess;
    }

    public int getMaxSubzoneDepth() {
        return this.subzonedepth;
    }

    public boolean canSetEnterLeaveMessages() {
        return this.messageperms;
    }

    public String getDefaultEnterMessage() {
        return this.defaultEnterMessage;
    }

    public String getDefaultLeaveMessage() {
        return this.defaultLeaveMessage;
    }

    public int getMaxLeaseTime() {
        return this.maxLeaseTime;
    }

    public int getLeaseGiveTime() {
        return this.leaseGiveTime;
    }

    public double getLeaseRenewCost() {
        return this.renewcostperarea;
    }

    public boolean canBuyLand() {
        return this.canBuy;
    }

    public boolean canSellLand() {
        return this.canSell;
    }

    public int getMaxRents() {
        return this.maxRents;
    }

    public int getMaxRentDays() {
        return this.MaxRentDays;
    }

    public int getMaxRentables() {
        return this.maxRentables;
    }

    public boolean buyLandIgnoreLimits() {
        return this.buyIgnoreLimits;
    }

    public boolean hasUnstuckAccess() {
        return this.unstuck;
    }

    public boolean hasKickAccess() {
        return this.kick;
    }

    public int getMaxPhysicalPerResidence() {
        return this.maxPhysical;
    }

    public Set<Map.Entry<String, Boolean>> getDefaultResidenceFlags() {
        return this.residenceDefaultFlags.entrySet();
    }

    public Set<Map.Entry<String, Boolean>> getDefaultCreatorFlags() {
        return this.creatorDefaultFlags.entrySet();
    }

    public Set<Map.Entry<String, Map<String, Boolean>>> getDefaultGroupFlags() {
        return this.groupDefaultFlags.entrySet();
    }

    public boolean canCreateResidences() {
        return this.cancreate;
    }

    public boolean hasFlagAccess(String flag) {
        return this.flagPerms.has(flag, false);
    }

    public boolean selectCommandAccess() {
        return this.selectCommandAccess;
    }

    public boolean itemListAccess() {
        return this.itemListAccess;
    }

    public void printLimits(CommandSender player, OfflinePlayer target, boolean resadmin2) {
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(target.getName());
        PermissionGroup group = rPlayer.getGroup();
        Residence.msg(player, lm.General_Separator, new Object[0]);
        Object[] arrobject = new Object[1];
        arrobject[0] = Residence.getPermissionManager().getPermissionsGroup(target.getName(), target.isOnline() ? Bukkit.getPlayer((String)target.getName()).getWorld().getName() : Residence.getConfigManager().getDefaultWorld());
        Residence.msg(player, lm.Limits_PGroup, arrobject);
        Residence.msg(player, lm.Limits_RGroup, group.getGroupName());
        if (target.isOnline() && resadmin2) {
            Residence.msg(player, lm.Limits_Admin, Residence.getPermissionManager().isResidenceAdmin(player));
        }
        Residence.msg(player, lm.Limits_CanCreate, group.canCreateResidences());
        Residence.msg(player, lm.Limits_MaxRes, rPlayer.getMaxRes());
        Residence.msg(player, lm.Limits_NumberOwn, rPlayer.getResAmount());
        Residence.msg(player, lm.Limits_MaxEW, String.valueOf(group.xmin) + "-" + group.xmax);
        Residence.msg(player, lm.Limits_MaxNS, String.valueOf(group.zmin) + "-" + group.zmax);
        Residence.msg(player, lm.Limits_MaxUD, String.valueOf(group.ymin) + "-" + group.ymax);
        Residence.msg(player, lm.Limits_MinMax, group.minHeight, group.maxHeight);
        Residence.msg(player, lm.Limits_MaxSub, rPlayer.getMaxSubzones());
        Object[] arrobject2 = new Object[1];
        arrobject2[0] = String.valueOf(rPlayer.getMaxRents()) + (this.getMaxRentDays() != -1 ? Residence.msg(lm.Limits_MaxRentDays, this.getMaxRentDays()) : "");
        Residence.msg(player, lm.Limits_MaxRents, arrobject2);
        Residence.msg(player, lm.Limits_EnterLeave, group.messageperms);
        if (Residence.getEconomyManager() != null) {
            Residence.msg(player, lm.Limits_Cost, group.costperarea);
            Residence.msg(player, lm.Limits_Sell, group.sellperarea);
        }
        Residence.msg(player, lm.Limits_Flag, group.flagPerms.listFlags());
        if (Residence.getConfigManager().useLeases()) {
            Residence.msg(player, lm.Limits_MaxDays, group.maxLeaseTime);
            Residence.msg(player, lm.Limits_LeaseTime, group.leaseGiveTime);
            Residence.msg(player, lm.Limits_RenewCost, group.renewcostperarea);
        }
        Residence.msg(player, lm.General_Separator, new Object[0]);
    }
}

