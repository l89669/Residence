/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Sign
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package com.bekvon.bukkit.residence.shopStuff;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceFlagChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceRenameEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.shopStuff.Board;
import com.bekvon.bukkit.residence.shopStuff.ShopVote;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopListener
implements Listener {
    public static List<String> Delete = new ArrayList<String>();
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState;

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onSignInteract(PlayerInteractEvent event) {
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
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
        if (Delete.contains(player.getName())) {
            Board Found = null;
            for (Board one : Residence.getShopSignUtilManager().GetAllBoards()) {
                for (Location location : one.GetLocations()) {
                    if (!loc.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()) || loc.getBlockX() != location.getBlockX() || loc.getBlockY() != location.getBlockY() || loc.getBlockZ() != location.getBlockZ()) continue;
                    Found = one;
                    break;
                }
                if (Found != null) break;
            }
            if (Found != null) {
                Residence.getShopSignUtilManager().GetAllBoards().remove(Found);
                Residence.getShopSignUtilManager().saveSigns();
                Residence.msg((CommandSender)player, lm.Shop_DeletedBoard, new Object[0]);
            } else {
                Residence.msg((CommandSender)player, lm.Shop_IncorrectBoard, new Object[0]);
            }
            Delete.remove(player.getName());
            return;
        }
        String resName = null;
        for (Board one : Residence.getShopSignUtilManager().GetAllBoards()) {
            resName = one.getResNameByLoc(loc);
            if (resName != null) break;
        }
        if (resName != null) {
            Bukkit.dispatchCommand((CommandSender)event.getPlayer(), (String)("res tp " + resName));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onFlagChangeShop(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase("shop")) {
            return;
        }
        switch (ShopListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2: 
            case 3: {
                Residence.getResidenceManager().removeShop(event.getResidence());
                Residence.getShopSignUtilManager().BoardUpdate();
                Residence.getShopSignUtilManager().saveSigns();
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                Residence.getResidenceManager().addShop(event.getResidence().getName());
                event.getResidence().getPermissions().setFlag("tp", FlagPermissions.FlagState.TRUE);
                event.getResidence().getPermissions().setFlag("move", FlagPermissions.FlagState.TRUE);
                event.getResidence().getPermissions().setFlag("pvp", FlagPermissions.FlagState.FALSE);
                Residence.getShopSignUtilManager().BoardUpdate();
                Residence.getShopSignUtilManager().saveSigns();
                break;
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceRename(ResidenceRenameEvent event) {
        if (!event.getResidence().GetShopVotes().isEmpty()) {
            Residence.getResidenceManager().addShop(event.getResidence());
            Residence.getResidenceManager().removeShop(event.getOldResidenceName());
            Residence.getShopSignUtilManager().saveShopVotes();
            Residence.getShopSignUtilManager().BoardUpdateDelayed();
            Residence.getShopSignUtilManager().saveSigns();
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=1)
    public void onFlagChange(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getFlag().equalsIgnoreCase("tp") && event.getNewState() == FlagPermissions.FlagState.TRUE) {
            return;
        }
        if (event.getFlag().equalsIgnoreCase("move") && event.getNewState() == FlagPermissions.FlagState.TRUE) {
            return;
        }
        if (event.getFlag().equalsIgnoreCase("pvp") && event.getNewState() == FlagPermissions.FlagState.FALSE) {
            return;
        }
        if (!(event.getFlag().equalsIgnoreCase("move") || event.getFlag().equalsIgnoreCase("tp") || event.getFlag().equalsIgnoreCase("pvp"))) {
            return;
        }
        if (!event.getResidence().getPermissions().has("shop", false)) {
            return;
        }
        event.setCancelled(true);
        Residence.msg(event.getPlayer(), (Object)ChatColor.YELLOW + "Can't change while shop flag is set to true");
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceCreate(ResidenceCreationEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getResidence().getPermissions().has("shop", false)) {
            return;
        }
        Residence.getResidenceManager().addShop(event.getResidence().getName());
        Residence.getShopSignUtilManager().BoardUpdate();
        Residence.getShopSignUtilManager().saveSigns();
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=1)
    public void onResidenceRemove(ResidenceDeleteEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getResidence().getPermissions().has("shop", true)) {
            return;
        }
        Residence.getResidenceManager().removeShop(event.getResidence());
        Residence.getShopSignUtilManager().BoardUpdate();
        Residence.getShopSignUtilManager().saveSigns();
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

