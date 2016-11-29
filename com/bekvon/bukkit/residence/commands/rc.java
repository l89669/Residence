/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.chat.ChatChannel;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rc
implements cmd {
    @CommandAnnotation(simple=1, priority=1100)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        String pname = player.getName();
        if (!Residence.getConfigManager().chatEnabled()) {
            Residence.msg((CommandSender)player, lm.Residence_ChatDisabled, new Object[0]);
            return false;
        }
        if (args.length > 0) {
            args = Arrays.copyOfRange(args, 1, args.length);
        }
        if (args.length == 0) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(player.getLocation());
            if (res == null) {
                ChatChannel chat = Residence.getChatManager().getPlayerChannel(pname);
                if (chat != null) {
                    Residence.getChatManager().removeFromChannel(pname);
                    Residence.getPlayerListener().removePlayerResidenceChat(player);
                    return true;
                }
                Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                return true;
            }
            ChatChannel chat = Residence.getChatManager().getPlayerChannel(pname);
            if (chat != null && chat.getChannelName().equals(res.getName())) {
                Residence.getChatManager().removeFromChannel(pname);
                Residence.getPlayerListener().removePlayerResidenceChat(player);
                return true;
            }
            if (!res.getPermissions().playerHas(player.getName(), Flags.chat, true) && !Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
                Residence.msg((CommandSender)player, lm.Residence_FlagDeny, new Object[]{Flags.chat, res.getName()});
                return false;
            }
            Residence.getPlayerListener().tooglePlayerResidenceChat(player, res.getName());
            Residence.getChatManager().setChannel(pname, res);
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("leave")) {
                Residence.getChatManager().removeFromChannel(pname);
                Residence.getPlayerListener().removePlayerResidenceChat(player);
                return true;
            }
            ClaimedResidence res = Residence.getResidenceManager().getByName(args[0]);
            if (res == null) {
                Residence.msg((CommandSender)player, lm.Chat_InvalidChannel, new Object[0]);
                return true;
            }
            if (!res.getPermissions().playerHas(player.getName(), Flags.chat, true) && !Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
                Residence.msg((CommandSender)player, lm.Residence_FlagDeny, Flags.chat.getName(), res.getName());
                return false;
            }
            Residence.getPlayerListener().tooglePlayerResidenceChat(player, res.getName());
            Residence.getChatManager().setChannel(pname, res);
            return true;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setcolor")) {
                ChatChannel chat = Residence.getChatManager().getPlayerChannel(pname);
                if (chat == null) {
                    Residence.msg((CommandSender)player, lm.Chat_JoinFirst, new Object[0]);
                    return true;
                }
                ClaimedResidence res = Residence.getResidenceManager().getByName(chat.getChannelName());
                if (res == null) {
                    return false;
                }
                if (!res.isOwner(player) && !Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return true;
                }
                if (!player.hasPermission("residence.chatcolor")) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return true;
                }
                String posibleColor = args[1];
                if (!posibleColor.contains("&")) {
                    posibleColor = "&" + posibleColor;
                }
                if (posibleColor.length() != 2 || ChatColor.stripColor((String)ChatColor.translateAlternateColorCodes((char)'&', (String)posibleColor)).length() != 0) {
                    Residence.msg((CommandSender)player, lm.Chat_InvalidColor, new Object[0]);
                    return true;
                }
                ChatColor color = ChatColor.getByChar((String)posibleColor.replace("&", ""));
                res.setChannelColor(color);
                chat.setChannelColor(color);
                Residence.msg((CommandSender)player, lm.Chat_ChangedColor, color.name());
                return true;
            }
            if (args[0].equalsIgnoreCase("setprefix")) {
                ChatChannel chat = Residence.getChatManager().getPlayerChannel(pname);
                if (chat == null) {
                    Residence.msg((CommandSender)player, lm.Chat_JoinFirst, new Object[0]);
                    return true;
                }
                ClaimedResidence res = Residence.getResidenceManager().getByName(chat.getChannelName());
                if (res == null) {
                    return false;
                }
                if (!res.isOwner(player) && !Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return true;
                }
                if (!player.hasPermission("residence.chatprefix")) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return true;
                }
                String prefix = args[1];
                if (prefix.length() > Residence.getConfigManager().getChatPrefixLength()) {
                    Residence.msg((CommandSender)player, lm.Chat_InvalidPrefixLength, Residence.getConfigManager().getChatPrefixLength());
                    return true;
                }
                res.setChatPrefix(prefix);
                chat.setChatPrefix(prefix);
                Residence.msg((CommandSender)player, lm.Chat_ChangedPrefix, ChatColor.translateAlternateColorCodes((char)'&', (String)prefix));
                return true;
            }
            if (args[0].equalsIgnoreCase("kick")) {
                ChatChannel chat = Residence.getChatManager().getPlayerChannel(pname);
                if (chat == null) {
                    Residence.msg((CommandSender)player, lm.Chat_JoinFirst, new Object[0]);
                    return true;
                }
                ClaimedResidence res = Residence.getResidenceManager().getByName(chat.getChannelName());
                if (res == null) {
                    return false;
                }
                if (!res.getOwner().equals(player.getName()) && !Residence.getPermissionManager().isResidenceAdmin((CommandSender)player)) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return true;
                }
                if (!player.hasPermission("residence.chatkick")) {
                    Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                    return true;
                }
                String targetName = args[1];
                if (!chat.hasMember(targetName)) {
                    Residence.msg((CommandSender)player, lm.Chat_NotInChannel, new Object[0]);
                    return false;
                }
                chat.leave(targetName);
                Residence.getPlayerListener().removePlayerResidenceChat(targetName);
                Residence.msg((CommandSender)player, lm.Chat_Kicked, targetName, chat.getChannelName());
                return true;
            }
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Joins current or defined residence chat channel");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res rc (residence)", "Join residence chat channel."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
        path = String.valueOf(path) + "SubCommands.";
        c.get(String.valueOf(path) + "leave.Description", "Leaves current residence chat channel");
        c.get(String.valueOf(path) + "leave.Info", Arrays.asList("&eUsage: &6/res rc leave", "If you are in residence chat channel then you will leave it"));
        c.get(String.valueOf(path) + "setcolor.Description", "Sets residence chat channel text color");
        c.get(String.valueOf(path) + "setcolor.Info", Arrays.asList("&eUsage: &6/res rc setcolor [colorCode]", "Sets residence chat channel text color"));
        c.get(String.valueOf(path) + "setprefix.Description", "Sets residence chat channel prefix");
        c.get(String.valueOf(path) + "setprefix.Info", Arrays.asList("&eUsage: &6/res rc setprefix [newName]", "Sets residence chat channel prefix"));
        c.get(String.valueOf(path) + "kick.Description", "Kicks player from channel");
        c.get(String.valueOf(path) + "kick.Info", Arrays.asList("&eUsage: &6/res rc kick [player]", "Kicks player from channel"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "kick"), Arrays.asList("[playername]"));
    }
}

