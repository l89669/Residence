/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Player
 *  org.bukkit.permissions.Permission
 *  org.bukkit.permissions.PermissionDefault
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class FlagPermissions {
    protected static ArrayList<String> validFlags = new ArrayList();
    protected static ArrayList<String> validPlayerFlags = new ArrayList();
    protected static ArrayList<String> validAreaFlags = new ArrayList();
    static final Map<Material, String> matUseFlagList = new EnumMap<Material, String>(Material.class);
    protected Map<UUID, String> cachedPlayerNameUUIDs = Collections.synchronizedMap(new HashMap());
    protected Map<String, Map<String, Boolean>> playerFlags = Collections.synchronizedMap(new HashMap());
    protected Map<String, Map<String, Boolean>> groupFlags = Collections.synchronizedMap(new HashMap());
    protected Map<String, Boolean> cuboidFlags = Collections.synchronizedMap(new HashMap());
    protected FlagPermissions parent;
    protected static HashMap<String, ArrayList<String>> validFlagGroups = new HashMap();
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo;

    public static void addMaterialToUseFlag(Material mat, Flags flag) {
        FlagPermissions.addMaterialToUseFlag(mat, flag.name());
    }

    public static void addMaterialToUseFlag(Material mat, String flag) {
        matUseFlagList.put(mat, flag);
    }

    public static void removeMaterialFromUseFlag(Material mat) {
        matUseFlagList.remove((Object)mat);
    }

    public static EnumMap<Material, String> getMaterialUseFlagList() {
        return (EnumMap)matUseFlagList;
    }

    public static void addFlag(Flags flag) {
        FlagPermissions.addFlag(flag.name());
    }

    public static void addFlag(String flag) {
        if (!validFlags.contains(flag = flag.toLowerCase())) {
            validFlags.add(flag);
        }
        if (validFlagGroups.containsKey(flag)) {
            validFlagGroups.remove(flag);
        }
    }

    public static void addPlayerOrGroupOnlyFlag(Flags flag) {
        FlagPermissions.addPlayerOrGroupOnlyFlag(flag.name());
    }

    public static void addPlayerOrGroupOnlyFlag(String flag) {
        if (!validPlayerFlags.contains(flag = flag.toLowerCase())) {
            validPlayerFlags.add(flag);
        }
        if (validFlagGroups.containsKey(flag)) {
            validFlagGroups.remove(flag);
        }
    }

    public static void addResidenceOnlyFlag(Flags flag) {
        FlagPermissions.addResidenceOnlyFlag(flag.name());
    }

    public static void addResidenceOnlyFlag(String flag) {
        if (!validAreaFlags.contains(flag = flag.toLowerCase())) {
            validAreaFlags.add(flag);
        }
        if (validFlagGroups.containsKey(flag)) {
            validFlagGroups.remove(flag);
        }
    }

    public static void addFlagToFlagGroup(String group, String flag) {
        if (!(validFlags.contains(group) || validAreaFlags.contains(group) || validPlayerFlags.contains(group))) {
            if (!validFlagGroups.containsKey(group)) {
                validFlagGroups.put(group, new ArrayList());
            }
            ArrayList<String> flags2 = validFlagGroups.get(group);
            flags2.add(flag);
        }
    }

    public static void removeFlagFromFlagGroup(String group, String flag) {
        if (validFlagGroups.containsKey(group)) {
            ArrayList<String> flags2 = validFlagGroups.get(group);
            flags2.remove(flag);
            if (flags2.isEmpty()) {
                validFlagGroups.remove(group);
            }
        }
    }

    public static boolean flagGroupExists(String group) {
        return validFlagGroups.containsKey(group);
    }

    public static void initValidFlags() {
        validAreaFlags.clear();
        validPlayerFlags.clear();
        validFlags.clear();
        validFlagGroups.clear();
        Flags[] arrflags = Flags.values();
        int n = arrflags.length;
        int n2 = 0;
        while (n2 < n) {
            Flags flag = arrflags[n2];
            switch (FlagPermissions.$SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode()[flag.getFlagMode().ordinal()]) {
                case 3: {
                    FlagPermissions.addFlag(flag);
                    break;
                }
                case 1: {
                    FlagPermissions.addPlayerOrGroupOnlyFlag(flag);
                    break;
                }
                case 2: {
                    FlagPermissions.addResidenceOnlyFlag(flag);
                    break;
                }
            }
            ++n2;
        }
        Residence.getConfigManager().UpdateGroupedFlagsFile();
        FlagPermissions.addMaterialToUseFlag(Material.DIODE, Flags.diode);
        FlagPermissions.addMaterialToUseFlag(Material.DIODE_BLOCK_OFF, Flags.diode);
        FlagPermissions.addMaterialToUseFlag(Material.DIODE_BLOCK_ON, Flags.diode);
        FlagPermissions.addMaterialToUseFlag(Material.REDSTONE_COMPARATOR, Flags.diode);
        FlagPermissions.addMaterialToUseFlag(Material.REDSTONE_COMPARATOR_OFF, Flags.diode);
        FlagPermissions.addMaterialToUseFlag(Material.REDSTONE_COMPARATOR_ON, Flags.diode);
        FlagPermissions.addMaterialToUseFlag(Material.DAYLIGHT_DETECTOR, Flags.diode);
        FlagPermissions.addMaterialToUseFlag(Material.WORKBENCH, Flags.table);
        FlagPermissions.addMaterialToUseFlag(Material.WOODEN_DOOR, Flags.door);
        Residence.getNms().addDefaultFlags(matUseFlagList);
        FlagPermissions.addMaterialToUseFlag(Material.FENCE_GATE, Flags.door);
        FlagPermissions.addMaterialToUseFlag(Material.NETHER_FENCE, Flags.door);
        FlagPermissions.addMaterialToUseFlag(Material.TRAP_DOOR, Flags.door);
        FlagPermissions.addMaterialToUseFlag(Material.ENCHANTMENT_TABLE, Flags.enchant);
        FlagPermissions.addMaterialToUseFlag(Material.STONE_BUTTON, Flags.button);
        FlagPermissions.addMaterialToUseFlag(Material.LEVER, Flags.lever);
        FlagPermissions.addMaterialToUseFlag(Material.BED_BLOCK, Flags.bed);
        FlagPermissions.addMaterialToUseFlag(Material.BREWING_STAND, Flags.brew);
        FlagPermissions.addMaterialToUseFlag(Material.CAKE, Flags.cake);
        FlagPermissions.addMaterialToUseFlag(Material.NOTE_BLOCK, Flags.note);
        FlagPermissions.addMaterialToUseFlag(Material.DRAGON_EGG, Flags.egg);
        FlagPermissions.addMaterialToUseFlag(Material.COMMAND, Flags.commandblock);
        FlagPermissions.addMaterialToUseFlag(Material.WOOD_BUTTON, Flags.button);
        FlagPermissions.addMaterialToUseFlag(Material.ANVIL, Flags.anvil);
        FlagPermissions.addMaterialToUseFlag(Material.FLOWER_POT, Flags.flowerpot);
        FlagPermissions.addMaterialToUseFlag(Material.BEACON, Flags.beacon);
        FlagPermissions.addMaterialToUseFlag(Material.JUKEBOX, Flags.container);
        FlagPermissions.addMaterialToUseFlag(Material.CHEST, Flags.container);
        FlagPermissions.addMaterialToUseFlag(Material.TRAPPED_CHEST, Flags.container);
        FlagPermissions.addMaterialToUseFlag(Material.HOPPER, Flags.container);
        FlagPermissions.addMaterialToUseFlag(Material.DROPPER, Flags.container);
        FlagPermissions.addMaterialToUseFlag(Material.FURNACE, Flags.container);
        FlagPermissions.addMaterialToUseFlag(Material.BURNING_FURNACE, Flags.container);
        FlagPermissions.addMaterialToUseFlag(Material.DISPENSER, Flags.container);
        FlagPermissions.addMaterialToUseFlag(Material.CAKE_BLOCK, Flags.cake);
    }

    public static FlagPermissions parseFromConfigNode(String name, ConfigurationSection node) {
        FlagPermissions list2 = new FlagPermissions();
        if (!node.isConfigurationSection(name)) {
            return list2;
        }
        Set keys = node.getConfigurationSection(name).getKeys(false);
        if (keys == null) {
            return list2;
        }
        for (String key : keys) {
            boolean state = node.getBoolean(String.valueOf(name) + "." + key, false);
            Flags f = Flags.getFlag(key = key.toLowerCase());
            if (f != null) {
                f.setEnabled(state);
            }
            if (state) {
                list2.setFlag(key, FlagState.TRUE);
                continue;
            }
            list2.setFlag(key, FlagState.FALSE);
        }
        return list2;
    }

    public static FlagPermissions parseFromConfigNodeAsList(String node, String stage) {
        FlagPermissions list2 = new FlagPermissions();
        if (node.equalsIgnoreCase("true")) {
            list2.setFlag(node, FlagState.valueOf(stage));
        } else {
            list2.setFlag(node, FlagState.FALSE);
        }
        return list2;
    }

    protected Map<String, Boolean> getPlayerFlags(String player, boolean allowCreate) {
        Map flags2 = null;
        if (!Residence.getConfigManager().isOfflineMode()) {
            UUID uuid = null;
            if (player.length() == 36) {
                try {
                    uuid = UUID.fromString(player);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                String resolvedName = Residence.getPlayerName(uuid);
                if (resolvedName != null) {
                    player = resolvedName;
                } else if (this.cachedPlayerNameUUIDs.containsKey(uuid)) {
                    player = this.cachedPlayerNameUUIDs.get(uuid);
                }
            } else {
                uuid = Residence.getPlayerUUID(player);
            }
            if (uuid == null) {
                Set<Map.Entry<UUID, String>> values = this.cachedPlayerNameUUIDs.entrySet();
                for (Map.Entry<UUID, String> value : values) {
                    if (!value.getValue().equals(player)) continue;
                    uuid = value.getKey();
                    break;
                }
            }
            if (uuid != null) {
                flags2 = this.playerFlags.get(uuid.toString());
            }
            if (flags2 == null) {
                flags2 = this.playerFlags.get(player);
                if (uuid != null && flags2 != null) {
                    flags2 = this.playerFlags.remove(player);
                    this.playerFlags.put(uuid.toString(), flags2);
                    this.cachedPlayerNameUUIDs.put(uuid, player);
                }
            } else {
                this.cachedPlayerNameUUIDs.put(uuid, player);
            }
            if (flags2 == null && allowCreate) {
                if (uuid != null) {
                    flags2 = Collections.synchronizedMap(new HashMap());
                    this.playerFlags.put(uuid.toString(), flags2);
                    this.cachedPlayerNameUUIDs.put(uuid, player);
                } else {
                    flags2 = Collections.synchronizedMap(new HashMap());
                    this.playerFlags.put(player, flags2);
                }
            }
        } else {
            for (Map.Entry<String, Map<String, Boolean>> one : this.playerFlags.entrySet()) {
                if (!one.getKey().equalsIgnoreCase(player)) continue;
                if (!one.getKey().equals(player)) {
                    Map<String, Boolean> r = this.playerFlags.remove(one.getKey());
                    this.playerFlags.put(player, r);
                }
                flags2 = one.getValue();
                break;
            }
            if (flags2 == null && allowCreate) {
                flags2 = Collections.synchronizedMap(new HashMap());
                this.playerFlags.put(player, flags2);
            }
        }
        return flags2;
    }

    public boolean setPlayerFlag(String player, String flag, FlagState state) {
        Map<String, Boolean> map = this.getPlayerFlags(player, state != FlagState.NEITHER);
        if (map == null) {
            return true;
        }
        if (state == FlagState.FALSE) {
            map.put(flag, false);
        } else if (state == FlagState.TRUE) {
            map.put(flag, true);
        } else if (state == FlagState.NEITHER && map.containsKey(flag)) {
            map.remove(flag);
        }
        if (map.isEmpty()) {
            this.removeAllPlayerFlags(player);
        }
        return true;
    }

    public void removeAllPlayerFlags(String player) {
        if (!Residence.getConfigManager().isOfflineMode()) {
            UUID uuid = Residence.getPlayerUUID(player);
            if (uuid == null) {
                for (Map.Entry<UUID, String> entry : this.cachedPlayerNameUUIDs.entrySet()) {
                    if (!entry.getValue().equals(player)) continue;
                    uuid = entry.getKey();
                    break;
                }
            }
            if (uuid != null) {
                this.playerFlags.remove(uuid.toString());
                this.cachedPlayerNameUUIDs.remove(uuid);
            }
            return;
        }
        this.playerFlags.remove(player);
        this.cachedPlayerNameUUIDs.remove(player);
    }

    public void removeAllGroupFlags(String group) {
        this.groupFlags.remove(group);
    }

    public boolean setGroupFlag(String group, String flag, FlagState state) {
        if (!this.groupFlags.containsKey(group = group.toLowerCase())) {
            this.groupFlags.put(group, Collections.synchronizedMap(new HashMap()));
        }
        Map<String, Boolean> map = this.groupFlags.get(group);
        if (state == FlagState.FALSE) {
            map.put(flag, false);
        } else if (state == FlagState.TRUE) {
            map.put(flag, true);
        } else if (state == FlagState.NEITHER && map.containsKey(flag)) {
            map.remove(flag);
        }
        if (map.isEmpty()) {
            this.groupFlags.remove(group);
        }
        return true;
    }

    public boolean setFlag(String flag, FlagState state) {
        if (state == FlagState.FALSE) {
            this.cuboidFlags.put(flag, false);
        } else if (state == FlagState.TRUE) {
            this.cuboidFlags.put(flag, true);
        } else if (state == FlagState.NEITHER && this.cuboidFlags.containsKey(flag)) {
            this.cuboidFlags.remove(flag);
        }
        return true;
    }

    public static FlagState stringToFlagState(String flagstate) {
        if (flagstate.equalsIgnoreCase("true") || flagstate.equalsIgnoreCase("t")) {
            return FlagState.TRUE;
        }
        if (flagstate.equalsIgnoreCase("false") || flagstate.equalsIgnoreCase("f")) {
            return FlagState.FALSE;
        }
        if (flagstate.equalsIgnoreCase("remove") || flagstate.equalsIgnoreCase("r")) {
            return FlagState.NEITHER;
        }
        return FlagState.INVALID;
    }

    public boolean playerHas(Player player, Flags flag, boolean def) {
        if (player == null) {
            return false;
        }
        return this.playerHas(player.getName(), player.getWorld().getName(), flag.getName(), def);
    }

    public boolean playerHas(String player, String world, Flags flag, boolean def) {
        return this.playerHas(player, world, flag.getName(), def);
    }

    public boolean playerHas(String player, String world, String flag, boolean def) {
        String group = Residence.getPermissionManager().getGroupNameByPlayer(player, world);
        return this.playerCheck(player, flag, this.groupCheck(group, flag, this.has(flag, def)));
    }

    public boolean groupHas(String group, String flag, boolean def) {
        return this.groupCheck(group, flag, this.has(flag, def));
    }

    private boolean playerCheck(String player, String flag, boolean def) {
        Map<String, Boolean> pmap = this.getPlayerFlags(player, false);
        if (pmap != null && pmap.containsKey(flag)) {
            return pmap.get(flag);
        }
        if (this.parent != null) {
            return this.parent.playerCheck(player, flag, def);
        }
        return def;
    }

    private boolean groupCheck(String group, String flag, boolean def) {
        Map<String, Boolean> gmap;
        if (this.groupFlags.containsKey(group) && (gmap = this.groupFlags.get(group)).containsKey(flag)) {
            return gmap.get(flag);
        }
        if (this.parent != null) {
            return this.parent.groupCheck(group, flag, def);
        }
        return def;
    }

    public boolean has(Flags flag, FlagCombo f) {
        switch (FlagPermissions.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo()[f.ordinal()]) {
            case 4: {
                return !this.has(flag, false);
            }
            case 2: {
                return !this.has(flag, true);
            }
            case 1: {
                return this.has(flag, false);
            }
            case 3: {
                return this.has(flag, true);
            }
        }
        return false;
    }

    public boolean has(Flags flag, boolean def) {
        return this.has(flag.getName(), def);
    }

    public boolean has(String flag, boolean def) {
        return this.has(flag, def, true);
    }

    public boolean has(String flag, boolean def, boolean checkParent) {
        if (this.cuboidFlags.containsKey(flag)) {
            return this.cuboidFlags.get(flag);
        }
        if (checkParent && this.parent != null) {
            return this.parent.has(flag, def);
        }
        return def;
    }

    public boolean isPlayerSet(String player, String flag) {
        Map<String, Boolean> flags2 = this.getPlayerFlags(player, false);
        if (flags2 == null) {
            return false;
        }
        return flags2.containsKey(flag);
    }

    public boolean inheritanceIsPlayerSet(String player, String flag) {
        Map<String, Boolean> flags2 = this.getPlayerFlags(player, false);
        if (flags2 == null) {
            return this.parent == null ? false : this.parent.inheritanceIsPlayerSet(player, flag);
        }
        return flags2.containsKey(flag) ? true : (this.parent == null ? false : this.parent.inheritanceIsPlayerSet(player, flag));
    }

    public boolean isGroupSet(String group, String flag) {
        Map<String, Boolean> flags2 = this.groupFlags.get(group = group.toLowerCase());
        if (flags2 == null) {
            return false;
        }
        return flags2.containsKey(flag);
    }

    public boolean inheritanceIsGroupSet(String group, String flag) {
        Map<String, Boolean> flags2 = this.groupFlags.get(group = group.toLowerCase());
        if (flags2 == null) {
            return this.parent == null ? false : this.parent.inheritanceIsGroupSet(group, flag);
        }
        return flags2.containsKey(flag) ? true : (this.parent == null ? false : this.parent.inheritanceIsGroupSet(group, flag));
    }

    public boolean isSet(String flag) {
        return this.cuboidFlags.containsKey(flag);
    }

    public boolean inheritanceIsSet(String flag) {
        return this.cuboidFlags.containsKey(flag) ? true : (this.parent == null ? false : this.parent.inheritanceIsSet(flag));
    }

    public boolean checkValidFlag(String flag, boolean globalflag) {
        if (validFlags.contains(flag)) {
            return true;
        }
        if (globalflag ? validAreaFlags.contains(flag) : validPlayerFlags.contains(flag)) {
            return true;
        }
        return false;
    }

    public Map<String, Object> save() {
        LinkedHashMap<String, Object> root = new LinkedHashMap<String, Object>();
        Residence.addCachedPlayerNameUUIDs(this.cachedPlayerNameUUIDs);
        root.put("PlayerFlags", this.playerFlags);
        if (!this.groupFlags.isEmpty()) {
            root.put("GroupFlags", this.groupFlags);
        }
        if (!FlagPermissions.isDefaultFlags(this.cuboidFlags)) {
            root.put("AreaFlags", this.cuboidFlags);
        }
        return root;
    }

    private static boolean isDefaultFlags(Map<String, Boolean> flags2) {
        FlagPermissions gf = Residence.getConfigManager().getGlobalResidenceDefaultFlags();
        if (gf != null) {
            Map<String, Boolean> dFlags = gf.getFlags();
            if (dFlags.isEmpty()) {
                return false;
            }
            if (dFlags.size() != flags2.size()) {
                return false;
            }
            for (Map.Entry<String, Boolean> one : dFlags.entrySet()) {
                if (flags2.containsKey(one.getKey()) && flags2.get(one.getKey()) == one.getValue()) continue;
                return false;
            }
        }
        return true;
    }

    public static FlagPermissions load(Map<String, Object> root) throws Exception {
        FlagPermissions newperms = new FlagPermissions();
        return FlagPermissions.load(root, newperms);
    }

    protected static FlagPermissions load(Map<String, Object> root, FlagPermissions newperms) throws Exception {
        if (root.containsKey("LastKnownPlayerNames")) {
            newperms.cachedPlayerNameUUIDs = (Map)root.get("LastKnownPlayerNames");
        }
        if (root.containsKey("PlayerFlags")) {
            newperms.playerFlags = (Map)root.get("PlayerFlags");
        }
        for (Map.Entry<String, Map<String, Boolean>> one : newperms.playerFlags.entrySet()) {
            if (one.getKey().length() != 32) continue;
            try {
                UUID uuid = UUID.fromString(one.getKey());
                String name = Residence.getCachedPlayerNameUUIDs().get(uuid);
                newperms.cachedPlayerNameUUIDs.put(uuid, name);
                continue;
            }
            catch (Exception uuid) {
                // empty catch block
            }
        }
        if (root.containsKey("GroupFlags")) {
            newperms.groupFlags = (Map)root.get("GroupFlags");
        }
        newperms.cuboidFlags = root.containsKey("AreaFlags") ? (Map)root.get("AreaFlags") : Residence.getConfigManager().getGlobalResidenceDefaultFlags().getFlags();
        String ownerName = null;
        String uuid = null;
        if (root.containsKey("OwnerLastKnownName")) {
            ownerName = (String)root.get("OwnerLastKnownName");
            uuid = (String)root.get("OwnerUUID");
        }
        if (Residence.getConfigManager().isOfflineMode()) {
            newperms.convertFlagsUUIDsToPlayerNames();
        } else {
            newperms.convertPlayerNamesToUUIDs(ownerName, uuid);
        }
        return newperms;
    }

    private void convertFlagsUUIDsToPlayerNames() {
        HashMap<String, String> converts = new HashMap<String, String>();
        for (String keyset : this.playerFlags.keySet()) {
            if (keyset.length() != 36) continue;
            String uuid = keyset;
            if (uuid.equalsIgnoreCase(Residence.getServerLandUUID())) {
                converts.put(uuid, Residence.getServerLandname());
                continue;
            }
            String name = Residence.getPlayerName(uuid);
            if (name == null) continue;
            converts.put(uuid, name);
        }
        for (Map.Entry one : converts.entrySet()) {
            if (!this.playerFlags.containsKey(one.getKey())) continue;
            Map<String, Boolean> replace = this.playerFlags.get(one.getKey());
            this.playerFlags.remove(one.getKey());
            this.playerFlags.put((String)one.getValue(), replace);
        }
    }

    private void convertPlayerNamesToUUIDs(String OwnerName, String owneruuid) {
        HashMap<String, String> converts = new HashMap<String, String>();
        ArrayList<String> Toremove = new ArrayList<String>();
        for (String keyset : this.playerFlags.keySet()) {
            if (keyset.length() != 36) {
                String uuid = null;
                uuid = OwnerName != null && OwnerName.equals(keyset) && !owneruuid.equals(Residence.getTempUserUUID()) ? owneruuid : Residence.getPlayerUUIDString(keyset);
                if (uuid != null) {
                    converts.put(keyset, uuid);
                    continue;
                }
                if (OwnerName == null || OwnerName.equals(keyset)) continue;
                Toremove.add(keyset);
                continue;
            }
            String pname = Residence.getPlayerName(keyset);
            if (pname == null) continue;
            try {
                UUID uuid2 = UUID.fromString(keyset);
                this.cachedPlayerNameUUIDs.put(uuid2, pname);
                continue;
            }
            catch (Exception uuid2) {
                // empty catch block
            }
        }
        for (String one : Toremove) {
            this.playerFlags.remove(one);
        }
        for (Map.Entry convert : converts.entrySet()) {
            this.playerFlags.put((String)convert.getValue(), this.playerFlags.remove(convert.getKey()));
            try {
                UUID uuid3 = UUID.fromString((String)convert.getValue());
                this.cachedPlayerNameUUIDs.put(uuid3, (String)convert.getKey());
                continue;
            }
            catch (Exception uuid3) {
                // empty catch block
            }
        }
    }

    public String listFlags() {
        return this.listFlags(0, 0);
    }

    public String listFlags(Integer split) {
        return this.listFlags(split, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String listFlags(Integer split, Integer totalShow) {
        Set<Map.Entry<String, Boolean>> set2;
        StringBuilder sbuild = new StringBuilder();
        Set<Map.Entry<String, Boolean>> set3 = set2 = this.cuboidFlags.entrySet();
        synchronized (set3) {
            Iterator<Map.Entry<String, Boolean>> it = set2.iterator();
            int i = -1;
            int t = 0;
            while (it.hasNext()) {
                ++i;
                Map.Entry<String, Boolean> next = it.next();
                if (totalShow > 0 && ++t > totalShow) break;
                if (split > 0 && i >= split) {
                    i = 0;
                    sbuild.append("\n");
                }
                if (next.getValue().booleanValue()) {
                    sbuild.append("&2").append("+").append(next.getKey());
                    if (!it.hasNext()) continue;
                    sbuild.append(" ");
                    continue;
                }
                sbuild.append("&3").append("-").append(next.getKey());
                if (!it.hasNext()) continue;
                sbuild.append(" ");
            }
        }
        if (sbuild.length() == 0) {
            sbuild.append("none");
        }
        return ChatColor.translateAlternateColorCodes((char)'&', (String)sbuild.toString());
    }

    public Map<String, Boolean> getFlags() {
        return this.cuboidFlags;
    }

    public Map<String, Boolean> getPlayerFlags(String player) {
        return this.getPlayerFlags(player, false);
    }

    public Set<String> getposibleFlags() {
        HashSet<String> t = new HashSet<String>();
        t.addAll(validFlags);
        t.addAll(validPlayerFlags);
        return t;
    }

    public ArrayList<String> getposibleAreaFlags() {
        return validAreaFlags;
    }

    public List<String> getPosibleFlags(Player player, boolean residence, boolean resadmin2) {
        ArrayList<String> flags2 = new ArrayList<String>();
        for (Map.Entry<String, Boolean> one : Residence.getPermissionManager().getAllFlags().getFlags().entrySet()) {
            if (!one.getValue().booleanValue() && !resadmin2 && !player.hasPermission(new Permission("residence.flag." + one.getKey().toLowerCase(), PermissionDefault.FALSE)) || !residence && !this.getposibleFlags().contains(one.getKey())) continue;
            flags2.add(one.getKey());
        }
        return flags2;
    }

    public String listPlayerFlags(String player) {
        Map<String, Boolean> flags2 = this.getPlayerFlags(player, false);
        if (flags2 != null) {
            return this.printPlayerFlags(flags2);
        }
        return "none";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String printPlayerFlags(Map<String, Boolean> flags2) {
        StringBuilder sbuild = new StringBuilder();
        if (flags2 == null) {
            return "none";
        }
        Set<Map.Entry<String, Boolean>> set2 = flags2.entrySet();
        Map<String, Boolean> map = flags2;
        synchronized (map) {
            Iterator<Map.Entry<String, Boolean>> it = set2.iterator();
            while (it.hasNext()) {
                Map.Entry<String, Boolean> next = it.next();
                if (next.getValue().booleanValue()) {
                    sbuild.append("&2").append("+").append(next.getKey());
                    if (!it.hasNext()) continue;
                    sbuild.append(" ");
                    continue;
                }
                sbuild.append("&3").append("-").append(next.getKey());
                if (!it.hasNext()) continue;
                sbuild.append(" ");
            }
        }
        if (sbuild.length() == 0) {
            sbuild.append("none");
        }
        return ChatColor.translateAlternateColorCodes((char)'&', (String)sbuild.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String listOtherPlayersFlags(String player) {
        Set<Map.Entry<String, Map<String, Boolean>>> set2;
        String uuids = Residence.getPlayerUUIDString(player);
        StringBuilder sbuild = new StringBuilder();
        Set<Map.Entry<String, Map<String, Boolean>>> set3 = set2 = this.playerFlags.entrySet();
        synchronized (set3) {
            for (Map.Entry<String, Map<String, Boolean>> nextEnt : set2) {
                String next = nextEnt.getKey();
                if ((Residence.getConfigManager().isOfflineMode() || next.equals(player) || next.equals(uuids)) && (!Residence.getConfigManager().isOfflineMode() || next.equals(player))) continue;
                String perms = this.printPlayerFlags(nextEnt.getValue());
                if (next.length() == 36) {
                    String resolvedName = Residence.getPlayerName(next);
                    if (resolvedName != null) {
                        try {
                            UUID uuid = UUID.fromString(next);
                            this.cachedPlayerNameUUIDs.put(uuid, resolvedName);
                        }
                        catch (Exception uuid) {
                            // empty catch block
                        }
                        next = resolvedName;
                    } else if (this.cachedPlayerNameUUIDs.containsKey(next)) {
                        next = this.cachedPlayerNameUUIDs.get(next);
                    }
                }
                if (perms.equals("none")) continue;
                sbuild.append(next).append((Object)ChatColor.WHITE).append("[").append(perms).append((Object)ChatColor.WHITE).append("] ");
            }
        }
        return sbuild.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String listPlayersFlags() {
        Set<Map.Entry<String, Map<String, Boolean>>> set2;
        StringBuilder sbuild = new StringBuilder();
        Set<Map.Entry<String, Map<String, Boolean>>> set3 = set2 = this.playerFlags.entrySet();
        synchronized (set3) {
            for (Map.Entry<String, Map<String, Boolean>> nextEnt : set2) {
                String next = nextEnt.getKey();
                String perms = this.printPlayerFlags(nextEnt.getValue());
                if (next.length() == 36) {
                    String resolvedName = Residence.getPlayerName(next);
                    if (resolvedName != null) {
                        try {
                            UUID uuid = UUID.fromString(next);
                            this.cachedPlayerNameUUIDs.put(uuid, resolvedName);
                        }
                        catch (Exception uuid) {
                            // empty catch block
                        }
                        next = resolvedName;
                    } else if (this.cachedPlayerNameUUIDs.containsKey(next)) {
                        next = this.cachedPlayerNameUUIDs.get(next);
                    }
                }
                if (next.equalsIgnoreCase(Residence.getServerLandname()) || perms.equals("none")) continue;
                sbuild.append(next).append((Object)ChatColor.WHITE).append("[").append(perms).append((Object)ChatColor.WHITE).append("] ");
            }
        }
        return sbuild.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String listPlayersFlagsRaw(String player, String text) {
        Set<Map.Entry<String, Map<String, Boolean>>> set2;
        StringBuilder sbuild = new StringBuilder();
        sbuild.append("[\"\",");
        sbuild.append("{\"text\":\"" + text + "\"}");
        Set<Map.Entry<String, Map<String, Boolean>>> set3 = set2 = this.playerFlags.entrySet();
        synchronized (set3) {
            Iterator<Map.Entry<String, Map<String, Boolean>>> it = set2.iterator();
            boolean random = true;
            while (it.hasNext()) {
                Map.Entry<String, Map<String, Boolean>> nextEnt = it.next();
                String next = nextEnt.getKey();
                String perms = this.printPlayerFlags(nextEnt.getValue());
                if (next.length() == 36) {
                    String resolvedName = Residence.getPlayerName(next);
                    if (resolvedName != null) {
                        try {
                            UUID uuid = UUID.fromString(next);
                            this.cachedPlayerNameUUIDs.put(uuid, resolvedName);
                        }
                        catch (Exception uuid) {
                            // empty catch block
                        }
                        next = resolvedName;
                    } else if (this.cachedPlayerNameUUIDs.containsKey(next)) {
                        next = this.cachedPlayerNameUUIDs.get(next);
                    }
                }
                if (next.equalsIgnoreCase(Residence.getServerLandname()) || perms.equals("none")) continue;
                sbuild.append(",");
                if (random) {
                    random = false;
                    next = player.equals(next) ? "&4" + next + "&r" : "&2" + next + "&r";
                } else {
                    random = true;
                    next = player.equals(next) ? "&4" + next + "&r" : "&3" + next + "&r";
                }
                sbuild.append(this.ConvertToRaw(next, perms));
            }
        }
        sbuild.append("]");
        return ChatColor.translateAlternateColorCodes((char)'&', (String)sbuild.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String listOtherPlayersFlagsRaw(String text, String player) {
        Set<Map.Entry<String, Map<String, Boolean>>> set2;
        String uuids = Residence.getPlayerUUIDString(player);
        StringBuilder sbuild = new StringBuilder();
        sbuild.append("[\"\",");
        sbuild.append("{\"text\":\"" + text + "\"}");
        Set<Map.Entry<String, Map<String, Boolean>>> set3 = set2 = this.playerFlags.entrySet();
        synchronized (set3) {
            Iterator<Map.Entry<String, Map<String, Boolean>>> it = set2.iterator();
            boolean random = true;
            while (it.hasNext()) {
                Map.Entry<String, Map<String, Boolean>> nextEnt = it.next();
                String next = nextEnt.getKey();
                if ((Residence.getConfigManager().isOfflineMode() || next.equals(player) || next.equals(uuids)) && (!Residence.getConfigManager().isOfflineMode() || next.equals(player))) continue;
                String perms = this.printPlayerFlags(nextEnt.getValue());
                if (next.length() == 36) {
                    String resolvedName = Residence.getPlayerName(next);
                    if (resolvedName != null) {
                        try {
                            UUID uuid = UUID.fromString(next);
                            this.cachedPlayerNameUUIDs.put(uuid, resolvedName);
                        }
                        catch (Exception uuid) {
                            // empty catch block
                        }
                        next = resolvedName;
                    } else if (this.cachedPlayerNameUUIDs.containsKey(next)) {
                        next = this.cachedPlayerNameUUIDs.get(next);
                    }
                }
                if (perms.equals("none")) continue;
                sbuild.append(",");
                if (random) {
                    random = false;
                    next = "&2" + next + "&r";
                } else {
                    random = true;
                    next = "&3" + next + "&r";
                }
                sbuild.append(this.ConvertToRaw(next, perms));
            }
        }
        sbuild.append("]");
        return ChatColor.translateAlternateColorCodes((char)'&', (String)sbuild.toString());
    }

    protected String ConvertToRaw(String playerName, String perms) {
        if (perms.contains(" ")) {
            String[] splited = perms.split(" ");
            int i = 0;
            perms = "";
            String[] arrstring = splited;
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String one = arrstring[n2];
                perms = String.valueOf(perms) + one + " ";
                if (++i >= 5) {
                    i = 0;
                    perms = String.valueOf(perms) + "\n";
                }
                ++n2;
            }
        }
        return "{\"text\":\"[" + playerName + "]\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + perms + "\"}]}}}";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String listGroupFlags() {
        Set<String> set2;
        StringBuilder sbuild = new StringBuilder();
        Set<String> set3 = set2 = this.groupFlags.keySet();
        synchronized (set3) {
            for (String next : set2) {
                String perms = this.listGroupFlags(next);
                if (perms.equals("none")) continue;
                sbuild.append(next).append("[").append((Object)ChatColor.DARK_AQUA).append(perms).append((Object)ChatColor.RED).append("] ");
            }
        }
        return sbuild.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String listGroupFlags(String group) {
        if (this.groupFlags.containsKey(group = group.toLowerCase())) {
            StringBuilder sbuild = new StringBuilder();
            Map<String, Boolean> get = this.groupFlags.get(group);
            Set<Map.Entry<String, Boolean>> set2 = get.entrySet();
            Map<String, Boolean> map = get;
            synchronized (map) {
                Iterator<Map.Entry<String, Boolean>> it = set2.iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Boolean> next = it.next();
                    if (next.getValue().booleanValue()) {
                        sbuild.append("&2").append("+").append(next.getKey());
                        if (!it.hasNext()) continue;
                        sbuild.append(" ");
                        continue;
                    }
                    sbuild.append("&3").append("-").append(next.getKey());
                    if (!it.hasNext()) continue;
                    sbuild.append(" ");
                }
            }
            if (sbuild.length() == 0) {
                this.groupFlags.remove(group);
                sbuild.append("none");
            }
            return ChatColor.translateAlternateColorCodes((char)'&', (String)sbuild.toString());
        }
        return "none";
    }

    public void clearFlags() {
        this.groupFlags.clear();
        this.playerFlags.clear();
        this.cuboidFlags.clear();
    }

    public void printFlags(Player player) {
        Residence.msg((CommandSender)player, lm.General_ResidenceFlags, this.listFlags());
        Residence.msg((CommandSender)player, lm.General_PlayersFlags, this.listPlayerFlags(player.getName()));
        Residence.msg((CommandSender)player, lm.General_GroupFlags, this.listGroupFlags());
        Residence.msg((CommandSender)player, lm.General_OthersFlags, this.listOtherPlayersFlags(player.getName()));
    }

    public void copyUserPermissions(String fromUser, String toUser) {
        Map<String, Boolean> get = this.getPlayerFlags(fromUser, false);
        if (get != null) {
            Map<String, Boolean> targ = this.getPlayerFlags(toUser, true);
            for (Map.Entry<String, Boolean> entry : get.entrySet()) {
                targ.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Deprecated
    public void clearPlayersFlags(String user) {
        this.removeAllPlayerFlags(user);
    }

    public void setParent(FlagPermissions p) {
        this.parent = p;
    }

    public FlagPermissions getParent() {
        return this.parent;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Flags.FlagMode.values().length];
        try {
            arrn[Flags.FlagMode.Both.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Flags.FlagMode.Group.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Flags.FlagMode.Player.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Flags.FlagMode.Residence.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode = arrn;
        return $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[FlagCombo.values().length];
        try {
            arrn[FlagCombo.FalseOrNone.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagCombo.OnlyFalse.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagCombo.OnlyTrue.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[FlagCombo.TrueOrNone.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo = arrn;
        return $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagCombo;
    }

    public static enum FlagCombo {
        OnlyTrue,
        OnlyFalse,
        TrueOrNone,
        FalseOrNone;
        

        private FlagCombo(String string2, int n2) {
        }
    }

    public static enum FlagState {
        TRUE,
        FALSE,
        NEITHER,
        INVALID;
        

        private FlagState(String string2, int n2) {
        }
    }

}

