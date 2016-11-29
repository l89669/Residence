/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.bekvon.bukkit.residence.gui;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.gui.FlagData;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetFlag {
    private String residence;
    private Player player;
    private String targetPlayer = null;
    private Inventory inventory;
    private LinkedHashMap<String, Object> permMap = new LinkedHashMap();
    private LinkedHashMap<String, List<String>> description = new LinkedHashMap();
    private boolean admin = false;
    private int page = 1;
    private int pageCount = 1;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState;

    public SetFlag(String residence, Player player, boolean admin) {
        this.residence = residence;
        this.player = player;
        this.admin = admin;
        this.fillFlagDescriptions();
    }

    public void setAdmin(boolean state) {
        this.admin = state;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setTargetPlayer(String player) {
        this.targetPlayer = player;
    }

    public String getResidence() {
        return this.residence;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void toggleFlag(int slot, ClickType click, InventoryAction action) {
        ItemStack item = this.inventory.getItem(slot);
        if (item == null) {
            return;
        }
        String command2 = "true";
        if (click.isLeftClick() && action != InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            command2 = "true";
        } else if (click.isRightClick() && action != InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            command2 = "false";
        } else if (click.isLeftClick() && action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            command2 = "remove";
        } else if (click.isRightClick() && action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            return;
        }
        if (slot == 53) {
            if (this.page < this.pageCount) {
                ++this.page;
            }
            this.recalculateInv();
            return;
        }
        if (slot == 45) {
            if (this.page > 1) {
                --this.page;
            }
            this.recalculateInv();
            return;
        }
        String flag = "";
        int i = 0;
        for (Map.Entry<String, Object> one : this.permMap.entrySet()) {
            flag = one.getKey();
            if (i == slot) break;
            ++i;
        }
        if (this.targetPlayer == null) {
            if (this.admin) {
                Bukkit.dispatchCommand((CommandSender)this.player, (String)("resadmin set " + this.residence + " " + flag + " " + command2));
            } else {
                Bukkit.dispatchCommand((CommandSender)this.player, (String)("res set " + this.residence + " " + flag + " " + command2));
            }
        } else if (this.admin) {
            Bukkit.dispatchCommand((CommandSender)this.player, (String)("resadmin pset " + this.residence + " " + this.targetPlayer + " " + flag + " " + command2));
        } else {
            Bukkit.dispatchCommand((CommandSender)this.player, (String)("res pset " + this.residence + " " + this.targetPlayer + " " + flag + " " + command2));
        }
    }

    public void recalculateInv() {
        if (this.targetPlayer == null) {
            this.recalculateResidence(Residence.getResidenceManager().getByName(this.residence));
        } else {
            this.recalculatePlayer(Residence.getResidenceManager().getByName(this.residence));
        }
    }

    private void fillFlagDescriptions() {
        Set<String> list2 = Residence.getLM().getKeyList("CommandHelp.SubCommands.res.SubCommands.flags.SubCommands");
        for (String onelist : list2) {
            String onelisttemp = Residence.msg("CommandHelp.SubCommands.res.SubCommands.flags.SubCommands." + onelist + ".Description");
            ArrayList<String> lore = new ArrayList<String>();
            int i = 0;
            String sentence = "";
            String[] arrstring = onelisttemp.split(" ");
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String oneWord = arrstring[n2];
                sentence = String.valueOf(sentence) + oneWord + " ";
                if (i > 4) {
                    lore.add((Object)ChatColor.YELLOW + sentence);
                    sentence = "";
                    i = 0;
                }
                ++i;
                ++n2;
            }
            lore.add((Object)ChatColor.YELLOW + sentence);
            this.description.put(onelist, lore);
        }
    }

    public void recalculateResidence(ClaimedResidence res) {
        List<String> flags2 = res.getPermissions().getPosibleFlags(this.player, true, this.admin);
        HashMap<String, Boolean> resFlags = new HashMap<String, Boolean>();
        Map TempPermMap = new LinkedHashMap<String, Object>();
        Map<String, Boolean> globalFlags = Residence.getPermissionManager().getAllFlags().getFlags();
        for (Map.Entry<String, Boolean> one : res.getPermissions().getFlags().entrySet()) {
            if (!flags2.contains(one.getKey())) continue;
            resFlags.put(one.getKey(), one.getValue());
        }
        for (Map.Entry<String, Boolean> one : globalFlags.entrySet()) {
            if (!flags2.contains(one.getKey())) continue;
            if (resFlags.containsKey(one.getKey())) {
                TempPermMap.put((String)one.getKey(), (FlagPermissions.FlagState)((Boolean)resFlags.get(one.getKey()) != false ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE));
                continue;
            }
            TempPermMap.put((String)one.getKey(), (FlagPermissions.FlagState)FlagPermissions.FlagState.NEITHER);
        }
        String title = "";
        title = this.targetPlayer == null ? Residence.msg(lm.Gui_Set_Title, res.getName()) : Residence.msg(lm.Gui_Pset_Title, this.targetPlayer, res.getName());
        if (title.length() > 32) {
            title = title.substring(0, Math.min(title.length(), 32));
        }
        Inventory GuiInv = Bukkit.createInventory((InventoryHolder)null, (int)54, (String)title);
        int i = 0;
        if (this.targetPlayer == null) {
            TempPermMap.remove("admin");
        }
        TempPermMap = Residence.getSortingManager().sortByKeyASC(TempPermMap);
        FlagData flagData = Residence.getFlagUtilManager().getFlagData();
        this.pageCount = (int)Math.ceil((double)TempPermMap.size() / 45.0);
        int start = this.page * 45 - 45;
        int end = this.page * 45;
        int count = -1;
        this.permMap.clear();
        for (Map.Entry one : TempPermMap.entrySet()) {
            if (++count >= end) break;
            if (count < start) continue;
            this.permMap.put((String)one.getKey(), one.getValue());
        }
        for (Map.Entry one : this.permMap.entrySet()) {
            ItemStack MiscInfo = Residence.getConfigManager().getGuiRemove();
            switch (SetFlag.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[((FlagPermissions.FlagState)((Object)one.getValue())).ordinal()]) {
                case 2: {
                    MiscInfo = Residence.getConfigManager().getGuiFalse();
                    break;
                }
                case 1: {
                    MiscInfo = Residence.getConfigManager().getGuiTrue();
                }
            }
            if (flagData.contains((String)one.getKey())) {
                MiscInfo = flagData.getItem((String)one.getKey());
            }
            if ((FlagPermissions.FlagState)((Object)one.getValue()) == FlagPermissions.FlagState.TRUE) {
                ItemMeta im = MiscInfo.getItemMeta();
                im.addEnchant(Enchantment.LUCK, 1, true);
                MiscInfo.setItemMeta(im);
            } else {
                MiscInfo.removeEnchantment(Enchantment.LUCK);
            }
            ItemMeta MiscInfoMeta = MiscInfo.getItemMeta();
            MiscInfoMeta.setDisplayName((Object)ChatColor.GREEN + (String)one.getKey());
            ArrayList<String> lore = new ArrayList<String>();
            String variable = "";
            switch (SetFlag.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[((FlagPermissions.FlagState)((Object)one.getValue())).ordinal()]) {
                case 2: {
                    variable = Residence.msg(lm.General_False, new Object[0]);
                    break;
                }
                case 1: {
                    variable = Residence.msg(lm.General_True, new Object[0]);
                    break;
                }
                case 3: {
                    variable = Residence.msg(lm.General_Removed, new Object[0]);
                }
            }
            lore.add(Residence.msg(lm.General_FlagState, variable));
            if (this.description.containsKey(one.getKey())) {
                lore.addAll((Collection)this.description.get(one.getKey()));
            }
            lore.addAll(Residence.msgL(lm.Gui_Actions));
            MiscInfoMeta.setLore(lore);
            MiscInfo.setItemMeta(MiscInfoMeta);
            GuiInv.setItem(i, MiscInfo);
            if (++i > 53) break;
        }
        ItemStack Item = new ItemStack(Material.ARROW);
        ItemMeta meta = Item.getItemMeta();
        if (this.page > 1) {
            meta.setDisplayName(Residence.msg(lm.General_PrevInfoPage, new Object[0]));
            Item.setItemMeta(meta);
            GuiInv.setItem(45, Item);
        }
        if (this.page < this.pageCount) {
            meta.setDisplayName(Residence.msg(lm.General_NextInfoPage, new Object[0]));
            Item.setItemMeta(meta);
            GuiInv.setItem(53, Item);
        }
        this.inventory = GuiInv;
    }

    public void recalculatePlayer(ClaimedResidence res) {
        HashMap<String, Boolean> globalFlags = new HashMap<String, Boolean>();
        Flags[] arrflags = Flags.values();
        int n = arrflags.length;
        int n2 = 0;
        while (n2 < n) {
            Flags oneFlag = arrflags[n2];
            globalFlags.put(oneFlag.getName(), oneFlag.isEnabled());
            ++n2;
        }
        List<String> flags2 = res.getPermissions().getPosibleFlags(this.player, false, this.admin);
        HashMap<String, Boolean> resFlags = new HashMap<String, Boolean>();
        for (Map.Entry one : res.getPermissions().getFlags().entrySet()) {
            if (!flags2.contains(one.getKey())) continue;
            resFlags.put((String)one.getKey(), (Boolean)one.getValue());
        }
        if (this.targetPlayer != null) {
            Set<String> PosibleResPFlags = res.getPermissions().getposibleFlags();
            HashMap<String, Boolean> temp = new HashMap<String, Boolean>();
            for (String one : PosibleResPFlags) {
                if (!globalFlags.containsKey(one)) continue;
                temp.put(one, (Boolean)globalFlags.get(one));
            }
            globalFlags = temp;
            Map<String, Boolean> pFlags = res.getPermissions().getPlayerFlags(this.targetPlayer);
            if (pFlags != null) {
                for (Map.Entry one : pFlags.entrySet()) {
                    resFlags.put((String)one.getKey(), (Boolean)one.getValue());
                }
            }
        }
        LinkedHashMap<String, Object> TempPermMap22 = new LinkedHashMap<String, Object>();
        for (Map.Entry one : globalFlags.entrySet()) {
            if (!flags2.contains(one.getKey())) continue;
            if (resFlags.containsKey(one.getKey())) {
                TempPermMap22.put((String)one.getKey(), (Object)((Boolean)resFlags.get(one.getKey()) != false ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE));
                continue;
            }
            TempPermMap22.put((String)one.getKey(), (Object)FlagPermissions.FlagState.NEITHER);
        }
        String title = "";
        title = this.targetPlayer == null ? Residence.msg(lm.Gui_Set_Title, res.getName()) : Residence.msg(lm.Gui_Pset_Title, this.targetPlayer, res.getName());
        if (title.length() > 32) {
            title = title.substring(0, Math.min(title.length(), 32));
        }
        Inventory GuiInv = Bukkit.createInventory((InventoryHolder)null, (int)54, (String)title);
        int i = 0;
        LinkedHashMap TempPermMap22 = (LinkedHashMap)Residence.getSortingManager().sortByKeyASC(TempPermMap22);
        FlagData flagData = Residence.getFlagUtilManager().getFlagData();
        this.pageCount = (int)Math.ceil((double)TempPermMap22.size() / 45.0);
        int start = this.page * 45 - 45;
        int end = this.page * 45;
        int count = -1;
        this.permMap.clear();
        for (Map.Entry one : TempPermMap22.entrySet()) {
            if (++count >= end) break;
            if (count < start) continue;
            this.permMap.put((String)one.getKey(), one.getValue());
        }
        for (Map.Entry one : this.permMap.entrySet()) {
            ItemStack MiscInfo = Residence.getConfigManager().getGuiRemove();
            switch (SetFlag.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[((FlagPermissions.FlagState)((Object)one.getValue())).ordinal()]) {
                case 2: {
                    MiscInfo = Residence.getConfigManager().getGuiFalse();
                    break;
                }
                case 1: {
                    MiscInfo = Residence.getConfigManager().getGuiTrue();
                }
            }
            if (flagData.contains((String)one.getKey())) {
                MiscInfo = flagData.getItem((String)one.getKey());
            }
            if ((FlagPermissions.FlagState)((Object)one.getValue()) == FlagPermissions.FlagState.TRUE) {
                ItemMeta im = MiscInfo.getItemMeta();
                im.addEnchant(Enchantment.LUCK, 1, true);
                MiscInfo.setItemMeta(im);
            } else {
                MiscInfo.removeEnchantment(Enchantment.LUCK);
            }
            ItemMeta MiscInfoMeta = MiscInfo.getItemMeta();
            MiscInfoMeta.setDisplayName((Object)ChatColor.GREEN + (String)one.getKey());
            ArrayList<String> lore = new ArrayList<String>();
            String variable = "";
            switch (SetFlag.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[((FlagPermissions.FlagState)((Object)one.getValue())).ordinal()]) {
                case 2: {
                    variable = Residence.msg(lm.General_False, new Object[0]);
                    break;
                }
                case 1: {
                    variable = Residence.msg(lm.General_True, new Object[0]);
                    break;
                }
                case 3: {
                    variable = Residence.msg(lm.General_Removed, new Object[0]);
                }
            }
            lore.add(Residence.msg(lm.General_FlagState, variable));
            if (this.description.containsKey(one.getKey())) {
                lore.addAll((Collection)this.description.get(one.getKey()));
            }
            lore.addAll(Residence.msgL(lm.Gui_Actions));
            MiscInfoMeta.setLore(lore);
            MiscInfo.setItemMeta(MiscInfoMeta);
            GuiInv.setItem(i, MiscInfo);
            if (++i > 53) break;
        }
        ItemStack Item = new ItemStack(Material.ARROW);
        ItemMeta meta = Item.getItemMeta();
        if (this.page > 1) {
            meta.setDisplayName(Residence.msg(lm.General_PrevInfoPage, new Object[0]));
            Item.setItemMeta(meta);
            GuiInv.setItem(45, Item);
        }
        if (this.page < this.pageCount) {
            meta.setDisplayName(Residence.msg(lm.General_NextInfoPage, new Object[0]));
            Item.setItemMeta(meta);
            GuiInv.setItem(53, Item);
        }
        this.inventory = GuiInv;
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

