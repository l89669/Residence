/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.gui.SetFlag;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class pset
implements cmd {
    @CommandAnnotation(simple=1, priority=800)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player) && args.length != 5 && args.length == 4 && !args[3].equalsIgnoreCase("removeall")) {
            return false;
        }
        if (args.length == 3 && args[2].equalsIgnoreCase("removeall")) {
            Player player = (Player)sender;
            ClaimedResidence area2 = Residence.getResidenceManager().getByLoc(player.getLocation());
            if (area2 != null) {
                area2.getPermissions().removeAllPlayerFlags(sender, args[1], resadmin2);
            } else {
                Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
            }
            return true;
        }
        if (args.length == 4 && args[3].equalsIgnoreCase("removeall")) {
            ClaimedResidence area3 = Residence.getResidenceManager().getByName(args[1]);
            if (area3 != null) {
                area3.getPermissions().removeAllPlayerFlags(sender, args[2], resadmin2);
            } else {
                Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
            }
            return true;
        }
        if (args.length == 4) {
            Player player = (Player)sender;
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(player.getLocation());
            if (!Residence.isPlayerExist(sender, args[1], true)) {
                return false;
            }
            if (res == null) {
                Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
                return true;
            }
            if (!(res.isOwner(player) || resadmin2 || res.getPermissions().playerHas(player, Flags.admin, false))) {
                Residence.msg(sender, lm.General_NoPermission, new Object[0]);
                return true;
            }
            res.getPermissions().setPlayerFlag(sender, args[1], args[2], args[3], resadmin2, true);
            return true;
        }
        if (args.length == 5) {
            ClaimedResidence res = Residence.getResidenceManager().getByName(args[1]);
            if (!Residence.isPlayerExist(sender, args[2], true)) {
                return false;
            }
            if (res == null) {
                Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
                return true;
            }
            if (!(res.isOwner(sender) || resadmin2 || res.getPermissions().playerHas(sender, Flags.admin, false))) {
                Residence.msg(sender, lm.General_NoPermission, new Object[0]);
                return true;
            }
            res.getPermissions().setPlayerFlag(sender, args[2], args[3], args[4], resadmin2, true);
            return true;
        }
        if ((args.length == 2 || args.length == 3) && Residence.getConfigManager().useFlagGUI()) {
            Player player = (Player)sender;
            ClaimedResidence res = null;
            String targetPlayer = null;
            if (args.length == 2) {
                res = Residence.getResidenceManager().getByLoc(player.getLocation());
                targetPlayer = args[1];
            } else {
                res = Residence.getResidenceManager().getByName(args[1]);
                targetPlayer = args[2];
            }
            if (res == null) {
                Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
                return true;
            }
            if (!Residence.isPlayerExist((CommandSender)player, targetPlayer, true)) {
                return false;
            }
            if (!(res.isOwner(player) || resadmin2 || res.getPermissions().playerHas(player, Flags.admin, false))) {
                Residence.msg(sender, lm.General_NoPermission, new Object[0]);
                return true;
            }
            SetFlag flag = new SetFlag(res.getName(), player, resadmin2);
            flag.setTargetPlayer(targetPlayer);
            flag.recalculatePlayer(res);
            player.closeInventory();
            Residence.getPlayerListener().getGUImap().put(player.getName(), flag);
            player.openInventory(flag.getInventory());
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Set flags on a specific player for a Residence.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res pset <residence> [player] [flag] [true/false/remove]", "&eUsage: &6/res pset <residence> [player] removeall", "To see a list of flags, use /res flags ?"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]%%[playername]", "[playername]%%[flag]", "[flag]%%true%%false%%remove", "true%%false%%remove"));
    }
}

