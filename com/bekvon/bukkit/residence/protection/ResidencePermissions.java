/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.PluginManager
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.event.ResidenceFlagChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceFlagCheckEvent;
import com.bekvon.bukkit.residence.event.ResidenceFlagEvent;
import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

public class ResidencePermissions
extends FlagPermissions {
    protected UUID ownerUUID;
    protected String ownerLastKnownName;
    protected String world;
    protected ClaimedResidence residence;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo;

    private ResidencePermissions(ClaimedResidence res) {
        this.residence = res;
    }

    public ResidencePermissions(ClaimedResidence res, String creator, String inworld) {
        this(res);
        this.ownerUUID = Residence.getPlayerUUID(creator);
        if (this.ownerUUID == null) {
            this.ownerUUID = UUID.fromString(Residence.getTempUserUUID());
        }
        this.ownerLastKnownName = creator;
        this.world = inworld;
    }

    public boolean playerHas(CommandSender sender, Flags flag, boolean def) {
        if (sender instanceof Player) {
            return this.playerHas((Player)sender, flag, def);
        }
        return true;
    }

    @Override
    public boolean playerHas(Player player, Flags flag, boolean def) {
        return this.playerHas(player, flag.getName(), def);
    }

    @Deprecated
    public boolean playerHas(Player player, String flag, boolean def) {
        return this.playerHas(player.getName(), this.world, flag, def);
    }

    public boolean playerHas(String player, Flags flag, boolean def) {
        return this.playerHas(player, flag.getName(), def);
    }

    @Deprecated
    public boolean playerHas(String player, String flag, boolean def) {
        return this.playerHas(player, this.world, flag, def);
    }

    public boolean playerHas(Player player, Flags flag, FlagPermissions.FlagCombo f) {
        return this.playerHas(player.getName(), flag, f);
    }

    public boolean playerHas(String player, Flags flag, FlagPermissions.FlagCombo f) {
        switch (ResidencePermissions.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo()[f.ordinal()]) {
            case 4: {
                return !this.playerHas(player, this.world, flag, false);
            }
            case 2: {
                return !this.playerHas(player, this.world, flag, true);
            }
            case 1: {
                return this.playerHas(player, this.world, flag, false);
            }
            case 3: {
                return this.playerHas(player, this.world, flag, true);
            }
        }
        return false;
    }

    @Override
    public boolean playerHas(String player, String world, String flag, boolean def) {
        ResidenceFlagCheckEvent fc = new ResidenceFlagCheckEvent(this.residence, flag, ResidenceFlagEvent.FlagType.PLAYER, player, def);
        Residence.getServ().getPluginManager().callEvent((Event)fc);
        if (fc.isOverriden()) {
            return fc.getOverrideValue();
        }
        return super.playerHas(player, world, flag, def);
    }

    @Override
    public boolean groupHas(String group, String flag, boolean def) {
        ResidenceFlagCheckEvent fc = new ResidenceFlagCheckEvent(this.residence, flag, ResidenceFlagEvent.FlagType.GROUP, group, def);
        Residence.getServ().getPluginManager().callEvent((Event)fc);
        if (fc.isOverriden()) {
            return fc.getOverrideValue();
        }
        return super.groupHas(group, flag, def);
    }

    @Override
    public boolean has(Flags flag, FlagPermissions.FlagCombo f) {
        return this.has(flag, f, true);
    }

    public boolean has(Flags flag, FlagPermissions.FlagCombo f, boolean checkParent) {
        return this.has(flag.getName(), f, checkParent);
    }

    public boolean has(String flag, FlagPermissions.FlagCombo f) {
        return this.has(flag, f, true);
    }

    public boolean has(String flag, FlagPermissions.FlagCombo f, boolean checkParent) {
        switch (ResidencePermissions.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo()[f.ordinal()]) {
            case 4: {
                return !this.has(flag, false, checkParent);
            }
            case 2: {
                return !this.has(flag, true, checkParent);
            }
            case 1: {
                return this.has(flag, false, checkParent);
            }
            case 3: {
                return this.has(flag, true, checkParent);
            }
        }
        return false;
    }

    @Override
    public boolean has(Flags flag, boolean def) {
        return this.has(flag.getName(), def);
    }

    @Override
    public boolean has(String flag, boolean def) {
        return this.has(flag, def, true);
    }

    @Override
    public boolean has(String flag, boolean def, boolean checkParent) {
        ResidenceFlagCheckEvent fc = new ResidenceFlagCheckEvent(this.residence, flag, ResidenceFlagEvent.FlagType.RESIDENCE, null, def);
        Residence.getServ().getPluginManager().callEvent((Event)fc);
        if (fc.isOverriden()) {
            return fc.getOverrideValue();
        }
        return super.has(flag, def, checkParent);
    }

    public boolean hasApplicableFlag(String player, String flag) {
        if (!(super.inheritanceIsPlayerSet(player, flag) || super.inheritanceIsGroupSet(Residence.getPermissionManager().getGroupNameByPlayer(player, this.world), flag) || super.inheritanceIsSet(flag))) {
            return false;
        }
        return true;
    }

    public void applyTemplate(Player player, FlagPermissions list2, boolean resadmin2) {
        if (player != null) {
            if (!resadmin2) {
                if (!Residence.getConfigManager().isOfflineMode() && !player.getUniqueId().toString().equals(this.ownerUUID.toString())) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return;
                }
                if (!player.getName().equals(this.ownerLastKnownName)) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return;
                }
            }
        } else {
            resadmin2 = true;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(this.getOwner());
        PermissionGroup group = rPlayer.getGroup(this.world);
        for (Map.Entry<String, Boolean> flag : list2.cuboidFlags.entrySet()) {
            if (group.hasFlagAccess(flag.getKey()) || resadmin2) {
                this.cuboidFlags.put(flag.getKey(), flag.getValue());
                continue;
            }
            if (player == null) continue;
            Residence.msg((CommandSender)player, lm.Flag_SetDeny, flag.getKey());
        }
        for (Map.Entry plists : list2.playerFlags.entrySet()) {
            Map<String, Boolean> map = this.getPlayerFlags((String)plists.getKey(), true);
            for (Map.Entry flag : ((Map)plists.getValue()).entrySet()) {
                if (group.hasFlagAccess((String)flag.getKey()) || resadmin2) {
                    map.put((String)flag.getKey(), (Boolean)flag.getValue());
                    continue;
                }
                if (player == null) continue;
                Residence.msg((CommandSender)player, lm.Flag_SetDeny, flag.getKey());
            }
        }
        for (Map.Entry glists : list2.groupFlags.entrySet()) {
            for (Map.Entry flag : ((Map)glists.getValue()).entrySet()) {
                if (group.hasFlagAccess((String)flag.getKey()) || resadmin2) {
                    if (!this.groupFlags.containsKey(glists.getKey())) {
                        this.groupFlags.put((String)glists.getKey(), Collections.synchronizedMap(new HashMap()));
                    }
                    ((Map)this.groupFlags.get(glists.getKey())).put((String)flag.getKey(), (Boolean)flag.getValue());
                    continue;
                }
                if (player == null) continue;
                Residence.msg((CommandSender)player, lm.Flag_SetDeny, flag.getKey());
            }
        }
        if (player != null) {
            Residence.msg((CommandSender)player, lm.Residence_PermissionsApply, new Object[0]);
        }
    }

    public boolean hasResidencePermission(CommandSender sender, boolean requireOwner) {
        if (!(sender instanceof Player)) {
            return true;
        }
        ClaimedResidence par = this.residence.getParent();
        if (par != null && par.getPermissions().playerHas(sender.getName(), Flags.admin, FlagPermissions.FlagCombo.OnlyTrue)) {
            return true;
        }
        if (Residence.getConfigManager().enabledRentSystem()) {
            String resname = this.residence.getName();
            if (Residence.getRentManager().isRented(resname)) {
                if (requireOwner) {
                    return false;
                }
                String renter = Residence.getRentManager().getRentingPlayer(resname);
                if (sender.getName().equals(renter)) {
                    return true;
                }
                return this.playerHas(sender.getName(), Flags.admin, FlagPermissions.FlagCombo.OnlyTrue);
            }
        }
        if (requireOwner) {
            return this.getOwner().equals(sender.getName());
        }
        if (!this.playerHas(sender.getName(), Flags.admin, FlagPermissions.FlagCombo.OnlyTrue) && !this.getOwner().equals(sender.getName())) {
            return false;
        }
        return true;
    }

    private boolean checkCanSetFlag(CommandSender sender, String flag, FlagPermissions.FlagState state, boolean globalflag, boolean resadmin2) {
        if (!this.checkValidFlag(flag, globalflag)) {
            Residence.msg(sender, lm.Invalid_Flag, new Object[0]);
            return false;
        }
        if (state == FlagPermissions.FlagState.INVALID) {
            Residence.msg(sender, lm.Invalid_FlagState, new Object[0]);
            return false;
        }
        if (!resadmin2) {
            if (!this.hasResidencePermission(sender, false)) {
                Residence.msg(sender, lm.General_NoPermission, new Object[0]);
                return false;
            }
            if (!this.hasFlagAccess(this.getOwner(), flag) && !sender.hasPermission("residence.flag." + flag.toLowerCase())) {
                Residence.msg(sender, lm.Flag_SetFailed, flag);
                return false;
            }
        }
        return true;
    }

    private boolean hasFlagAccess(String player, String flag) {
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup(this.world);
        return group.hasFlagAccess(flag);
    }

    public boolean setPlayerFlag(CommandSender sender, String targetPlayer, String flag, String flagstate, boolean resadmin2, boolean Show) {
        if (Residence.getPlayerUUID(targetPlayer) == null) {
            sender.sendMessage("no player by this name");
            return false;
        }
        if (validFlagGroups.containsKey(flag)) {
            return this.setFlagGroupOnPlayer(sender, targetPlayer, flag, flagstate, resadmin2);
        }
        FlagPermissions.FlagState state = FlagPermissions.stringToFlagState(flagstate);
        if (this.checkCanSetFlag(sender, flag, state, false, resadmin2)) {
            ResidenceFlagChangeEvent fc = new ResidenceFlagChangeEvent(this.residence, sender instanceof Player ? (Player)sender : null, flag, ResidenceFlagEvent.FlagType.PLAYER, state, targetPlayer);
            Residence.getServ().getPluginManager().callEvent((Event)fc);
            if (fc.isCancelled()) {
                return false;
            }
            if (super.setPlayerFlag(targetPlayer, flag, state)) {
                if (Show) {
                    Residence.msg(sender, lm.Flag_Set, flag, this.residence.getName(), flagstate);
                }
                return true;
            }
        }
        return false;
    }

    public boolean setGroupFlag(Player player, String group, String flag, String flagstate, boolean resadmin2) {
        group = group.toLowerCase();
        if (validFlagGroups.containsKey(flag)) {
            return this.setFlagGroupOnGroup(player, flag, group, flagstate, resadmin2);
        }
        FlagPermissions.FlagState state = FlagPermissions.stringToFlagState(flagstate);
        if (this.checkCanSetFlag((CommandSender)player, flag, state, false, resadmin2)) {
            if (Residence.getPermissionManager().hasGroup(group)) {
                ResidenceFlagChangeEvent fc = new ResidenceFlagChangeEvent(this.residence, player, flag, ResidenceFlagEvent.FlagType.GROUP, state, group);
                Residence.getServ().getPluginManager().callEvent((Event)fc);
                if (fc.isCancelled()) {
                    return false;
                }
                if (super.setGroupFlag(group, flag, state)) {
                    Residence.msg((CommandSender)player, lm.Flag_Set, flag, this.residence.getName(), flagstate);
                    return true;
                }
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Group, new Object[0]);
                return false;
            }
        }
        return false;
    }

    public boolean setFlag(CommandSender sender, String flag, String flagstate, boolean resadmin2) {
        if (validFlagGroups.containsKey(flag)) {
            return this.setFlagGroup(sender, flag, flagstate, resadmin2);
        }
        FlagPermissions.FlagState state = FlagPermissions.stringToFlagState(flagstate);
        if (Residence.getConfigManager().isPvPFlagPrevent()) {
            for (String oneFlag : Residence.getConfigManager().getProtectedFlagsList()) {
                if (!flag.equalsIgnoreCase(oneFlag)) continue;
                ArrayList<Player> players = this.residence.getPlayersInResidence();
                if (resadmin2 || players.size() <= 1 && (players.size() != 1 || players.get(0).getName().equals(this.getOwner()))) continue;
                int size = 0;
                for (Player one : players) {
                    if (one.getName().equals(this.getOwner())) continue;
                    ++size;
                }
                Residence.msg(sender, lm.Flag_ChangeDeny, flag, size);
                return false;
            }
        }
        if (this.checkCanSetFlag(sender, flag, state, true, resadmin2)) {
            ResidenceFlagChangeEvent fc = new ResidenceFlagChangeEvent(this.residence, sender instanceof Player ? (Player)sender : null, flag, ResidenceFlagEvent.FlagType.RESIDENCE, state, null);
            Residence.getServ().getPluginManager().callEvent((Event)fc);
            if (fc.isCancelled()) {
                return false;
            }
            if (super.setFlag(flag, state)) {
                Residence.msg(sender, lm.Flag_Set, flag, this.residence.getName(), flagstate);
                return true;
            }
        }
        return false;
    }

    public boolean removeAllPlayerFlags(CommandSender sender, String targetPlayer, boolean resadmin2) {
        if (this.hasResidencePermission(sender, false) || resadmin2) {
            ResidenceFlagChangeEvent fc = new ResidenceFlagChangeEvent(this.residence, sender instanceof Player ? (Player)sender : null, "ALL", ResidenceFlagEvent.FlagType.RESIDENCE, FlagPermissions.FlagState.NEITHER, null);
            Residence.getServ().getPluginManager().callEvent((Event)fc);
            if (fc.isCancelled()) {
                return false;
            }
            super.removeAllPlayerFlags(targetPlayer);
            Residence.msg(sender, lm.Flag_RemovedAll, targetPlayer, this.residence.getName());
            return true;
        }
        return false;
    }

    public boolean removeAllGroupFlags(Player player, String group, boolean resadmin2) {
        if (this.hasResidencePermission((CommandSender)player, false) || resadmin2) {
            ResidenceFlagChangeEvent fc = new ResidenceFlagChangeEvent(this.residence, player, "ALL", ResidenceFlagEvent.FlagType.GROUP, FlagPermissions.FlagState.NEITHER, null);
            Residence.getServ().getPluginManager().callEvent((Event)fc);
            if (fc.isCancelled()) {
                return false;
            }
            super.removeAllGroupFlags(group);
            Residence.msg((CommandSender)player, lm.Flag_RemovedGroup, group, this.residence.getName());
            return true;
        }
        return false;
    }

    @Override
    public boolean setFlag(String flag, FlagPermissions.FlagState state) {
        ResidenceFlagChangeEvent fc = new ResidenceFlagChangeEvent(this.residence, null, flag, ResidenceFlagEvent.FlagType.RESIDENCE, state, null);
        Residence.getServ().getPluginManager().callEvent((Event)fc);
        if (fc.isCancelled()) {
            return false;
        }
        return super.setFlag(flag, state);
    }

    @Override
    public boolean setGroupFlag(String group, String flag, FlagPermissions.FlagState state) {
        ResidenceFlagChangeEvent fc = new ResidenceFlagChangeEvent(this.residence, null, flag, ResidenceFlagEvent.FlagType.GROUP, state, group);
        Residence.getServ().getPluginManager().callEvent((Event)fc);
        if (fc.isCancelled()) {
            return false;
        }
        return super.setGroupFlag(group, flag, state);
    }

    @Override
    public boolean setPlayerFlag(String player, String flag, FlagPermissions.FlagState state) {
        ResidenceFlagChangeEvent fc = new ResidenceFlagChangeEvent(this.residence, null, flag, ResidenceFlagEvent.FlagType.PLAYER, state, player);
        Residence.getServ().getPluginManager().callEvent((Event)fc);
        if (fc.isCancelled()) {
            return false;
        }
        return super.setPlayerFlag(player, flag, state);
    }

    public void applyDefaultFlags(Player player, boolean resadmin2) {
        if (this.hasResidencePermission((CommandSender)player, true) || resadmin2) {
            this.applyDefaultFlags();
            Residence.msg((CommandSender)player, lm.Flag_Default, new Object[0]);
        } else {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
        }
    }

    public void applyDefaultFlags() {
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(this.getOwner());
        PermissionGroup group = rPlayer.getGroup(this.world);
        Set<Map.Entry<String, Boolean>> dflags = group.getDefaultResidenceFlags();
        Set<Map.Entry<String, Map<String, Boolean>>> dgflags = group.getDefaultGroupFlags();
        this.applyGlobalDefaults();
        for (Map.Entry<String, Boolean> next : dflags) {
            if (!this.checkValidFlag(next.getKey(), true)) continue;
            this.setFlag(next.getKey(), next.getValue() != false ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE);
        }
        for (Map.Entry entry : dgflags) {
            Map value = (Map)entry.getValue();
            for (Map.Entry flag : value.entrySet()) {
                this.setGroupFlag((String)entry.getKey(), (String)flag.getKey(), (Boolean)flag.getValue() != false ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE);
            }
        }
    }

    public void setOwner(String newOwner, boolean resetFlags) {
        UUID playerUUID;
        ResidenceOwnerChangeEvent ownerchange = new ResidenceOwnerChangeEvent(this.residence, newOwner);
        Residence.getServ().getPluginManager().callEvent((Event)ownerchange);
        Residence.getPlayerManager().removeResFromPlayer(this.ownerLastKnownName, this.residence.getName());
        Residence.getPlayerManager().addResidence(newOwner, this.residence);
        this.ownerLastKnownName = newOwner;
        this.ownerUUID = newOwner.equalsIgnoreCase("Server Land") || newOwner.equalsIgnoreCase(Residence.getServerLandname()) ? UUID.fromString(Residence.getServerLandUUID()) : ((playerUUID = Residence.getPlayerUUID(newOwner)) != null ? playerUUID : UUID.fromString(Residence.getTempUserUUID()));
        if (resetFlags) {
            this.applyDefaultFlags();
        }
    }

    public String getOwner() {
        if (Residence.getConfigManager().isOfflineMode()) {
            return this.ownerLastKnownName;
        }
        if (this.ownerUUID.toString().equals(Residence.getServerLandUUID())) {
            return Residence.getServerLandname();
        }
        String name = Residence.getPlayerName(this.ownerUUID);
        if (name == null) {
            return this.ownerLastKnownName;
        }
        this.ownerLastKnownName = name;
        return name;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public String getWorld() {
        return this.world;
    }

    @Override
    public Map<String, Object> save() {
        Map<String, Object> root = super.save();
        root.put("OwnerUUID", this.ownerUUID.toString());
        root.put("OwnerLastKnownName", this.ownerLastKnownName);
        return root;
    }

    public static ResidencePermissions load(String worldName, ClaimedResidence res, Map<String, Object> root) throws Exception {
        ResidencePermissions newperms = new ResidencePermissions(res);
        if (root.containsKey("OwnerUUID")) {
            UUID realUUID;
            newperms.ownerUUID = UUID.fromString((String)root.get("OwnerUUID"));
            newperms.ownerLastKnownName = (String)root.get("OwnerLastKnownName");
            OfflinePlayer p = null;
            if (newperms.ownerLastKnownName == null) {
                p = Bukkit.getOfflinePlayer((UUID)newperms.ownerUUID);
            }
            if (p != null) {
                newperms.ownerLastKnownName = p.getName();
            }
            if (newperms.ownerLastKnownName == null) {
                return newperms;
            }
            if (newperms.ownerLastKnownName.equalsIgnoreCase("Server land") || newperms.ownerLastKnownName.equalsIgnoreCase(Residence.getServerLandname())) {
                newperms.ownerUUID = UUID.fromString(Residence.getServerLandUUID());
                newperms.ownerLastKnownName = Residence.getServerLandname();
            } else if (newperms.ownerUUID.toString().equals(Residence.getTempUserUUID()) && (realUUID = Residence.getPlayerUUID(newperms.ownerLastKnownName)) != null) {
                newperms.ownerUUID = realUUID;
            }
        } else if (root.containsKey("Owner")) {
            String owner;
            newperms.ownerLastKnownName = owner = (String)root.get("Owner");
            newperms.ownerUUID = Residence.getPlayerUUID(owner);
            if (newperms.ownerUUID == null) {
                newperms.ownerUUID = UUID.fromString(Residence.getTempUserUUID());
            }
        } else {
            newperms.ownerUUID = UUID.fromString(Residence.getServerLandUUID());
            newperms.ownerLastKnownName = Residence.getServerLandname();
        }
        newperms.world = worldName;
        FlagPermissions.load(root, newperms);
        if (newperms.getOwner() == null || newperms.world == null || newperms.playerFlags == null || newperms.groupFlags == null || newperms.cuboidFlags == null) {
            throw new Exception("Invalid Residence Permissions...");
        }
        return newperms;
    }

    public void applyGlobalDefaults() {
        this.clearFlags();
        FlagPermissions gRD = Residence.getConfigManager().getGlobalResidenceDefaultFlags();
        FlagPermissions gCD = Residence.getConfigManager().getGlobalCreatorDefaultFlags();
        Map<String, FlagPermissions> gGD = Residence.getConfigManager().getGlobalGroupDefaultFlags();
        for (Map.Entry<String, Boolean> entry : gRD.cuboidFlags.entrySet()) {
            if (entry.getValue().booleanValue()) {
                this.setFlag(entry.getKey(), FlagPermissions.FlagState.TRUE);
                continue;
            }
            this.setFlag(entry.getKey(), FlagPermissions.FlagState.FALSE);
        }
        for (Map.Entry<String, Boolean> entry : gCD.cuboidFlags.entrySet()) {
            if (entry.getValue().booleanValue()) {
                this.setPlayerFlag(this.getOwner(), entry.getKey(), FlagPermissions.FlagState.TRUE);
                continue;
            }
            this.setPlayerFlag(this.getOwner(), entry.getKey(), FlagPermissions.FlagState.FALSE);
        }
        for (Map.Entry entry : gGD.entrySet()) {
            for (Map.Entry<String, Boolean> flag : ((FlagPermissions)entry.getValue()).cuboidFlags.entrySet()) {
                if (flag.getValue().booleanValue()) {
                    this.setGroupFlag((String)entry.getKey(), flag.getKey(), FlagPermissions.FlagState.TRUE);
                    continue;
                }
                this.setGroupFlag((String)entry.getKey(), flag.getKey(), FlagPermissions.FlagState.FALSE);
            }
        }
    }

    public boolean setFlagGroup(CommandSender sender, String flaggroup, String state, boolean resadmin2) {
        if (FlagPermissions.validFlagGroups.containsKey(flaggroup)) {
            ArrayList<String> flags2 = FlagPermissions.validFlagGroups.get(flaggroup);
            boolean changed = false;
            for (String flag : flags2) {
                if (!this.setFlag(sender, flag, state, resadmin2)) continue;
                changed = true;
            }
            return changed;
        }
        return false;
    }

    public boolean setFlagGroupOnGroup(Player player, String flaggroup, String group, String state, boolean resadmin2) {
        if (FlagPermissions.validFlagGroups.containsKey(flaggroup)) {
            ArrayList<String> flags2 = FlagPermissions.validFlagGroups.get(flaggroup);
            boolean changed = false;
            for (String flag : flags2) {
                if (!this.setGroupFlag(player, group, flag, state, resadmin2)) continue;
                changed = true;
            }
            return changed;
        }
        return false;
    }

    public boolean setFlagGroupOnPlayer(CommandSender sender, String target, String flaggroup, String state, boolean resadmin2) {
        if (FlagPermissions.validFlagGroups.containsKey(flaggroup)) {
            ArrayList<String> flags2 = FlagPermissions.validFlagGroups.get(flaggroup);
            boolean changed = false;
            String flagString = "";
            int i = 0;
            for (String flag : flags2) {
                ++i;
                if (!this.setPlayerFlag(sender, target, flag, state, resadmin2, false)) continue;
                changed = true;
                flagString = String.valueOf(flagString) + flag;
                if (i >= flags2.size() - 1) continue;
                flagString = String.valueOf(flagString) + ", ";
            }
            if (flagString.length() > 0) {
                Residence.msg(sender, lm.Flag_Set, flagString, target, state);
            }
            return changed;
        }
        return false;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[FlagPermissions.FlagCombo.values().length];
        try {
            arrn[FlagPermissions.FlagCombo.FalseOrNone.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagPermissions.FlagCombo.OnlyFalse.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagPermissions.FlagCombo.OnlyTrue.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagPermissions.FlagCombo.TrueOrNone.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo = arrn;
        return $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo;
    }
}

