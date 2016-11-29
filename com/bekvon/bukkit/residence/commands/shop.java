/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.shopStuff.Board;
import com.bekvon.bukkit.residence.shopStuff.ShopListener;
import com.bekvon.bukkit.residence.shopStuff.ShopVote;
import com.bekvon.bukkit.residence.shopStuff.Vote;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class shop
implements cmd {
    @CommandAnnotation(simple=1, priority=1700)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        int page = 1;
        try {
            if (args.length > 0) {
                page = Integer.parseInt(args[args.length - 1]);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ((args.length == 2 || args.length == 3 || args.length == 4) && (args[1].equalsIgnoreCase("votes") || args[1].equalsIgnoreCase("likes"))) {
            ClaimedResidence res;
            int VotePage;
            VotePage = 1;
            res = null;
            if (args.length == 2) {
                res = Residence.getResidenceManager().getByLoc(player.getLocation());
                if (res == null) {
                    Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                    return true;
                }
            } else if (args.length == 3) {
                res = Residence.getResidenceManager().getByName(args[2]);
                if (res == null) {
                    try {
                        VotePage = Integer.parseInt(args[2]);
                        res = Residence.getResidenceManager().getByLoc(player.getLocation());
                        if (res == null) {
                            Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                            return true;
                        }
                    }
                    catch (Exception ex) {
                        Residence.msg((CommandSender)player, lm.General_UseNumbers, new Object[0]);
                        return true;
                    }
                }
            } else if (args.length == 4) {
                res = Residence.getResidenceManager().getByName(args[2]);
                if (res == null) {
                    Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                    return true;
                }
                try {
                    VotePage = Integer.parseInt(args[3]);
                }
                catch (Exception ex) {
                    Residence.msg((CommandSender)player, lm.General_UseNumbers, new Object[0]);
                    return true;
                }
            }
            if (res == null) {
                Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                return true;
            }
            List<ShopVote> VoteList = res.GetShopVotes();
            String separator = (Object)ChatColor.GOLD;
            String simbol = "\u25ac";
            int i = 0;
            while (i < 5) {
                separator = String.valueOf(separator) + simbol;
                ++i;
            }
            int pagecount = (int)Math.ceil((double)VoteList.size() / 10.0);
            if (page > pagecount || page < 1) {
                Residence.msg(sender, lm.Shop_NoVotes, new Object[0]);
                return true;
            }
            Residence.msg((CommandSender)player, lm.Shop_VotesTopLine, separator, res.getName(), VotePage, pagecount, separator);
            int start = VotePage * 10 - 9;
            int end = VotePage * 10 + 1;
            int position = 0;
            int i2 = start;
            for (ShopVote one : VoteList) {
                if (++position < start) continue;
                if (position >= end) break;
                Date dNow = new Date(one.getTime());
                SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                ft.setTimeZone(TimeZone.getTimeZone(Residence.getConfigManager().getTimeZone()));
                String timeString = ft.format(dNow);
                Object[] arrobject = new Object[4];
                arrobject[0] = i2;
                arrobject[1] = one.getName();
                arrobject[2] = Residence.getConfigManager().isOnlyLike() ? "" : Integer.valueOf(one.getVote());
                arrobject[3] = timeString;
                String message2 = Residence.msg(lm.Shop_VotesList, arrobject);
                player.sendMessage(message2);
                ++i2;
            }
            if (pagecount == 1) {
                return true;
            }
            int NextPage = page + 1;
            NextPage = page < pagecount ? NextPage : page;
            int Prevpage = page - 1;
            Prevpage = page > 1 ? Prevpage : page;
            String prevCmd = "/res shop votes " + res.getName() + " " + Prevpage;
            String prev = "[\"\",{\"text\":\"" + separator + " " + Residence.msg(lm.General_PrevInfoPage, new Object[0]) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + prevCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + "<<<" + "\"}]}}}";
            String nextCmd = "/res shop votes " + res.getName() + " " + NextPage;
            String next = " {\"text\":\"" + Residence.msg(lm.General_NextInfoPage, new Object[0]) + " " + separator + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + nextCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ">>>" + "\"}]}}}]";
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + prev + "," + next));
            return true;
        }
        if ((args.length == 2 || args.length == 3) && args[1].equalsIgnoreCase("list")) {
            int Shoppage = 1;
            if (args.length == 3) {
                try {
                    Shoppage = Integer.parseInt(args[2]);
                }
                catch (Exception ex) {
                    Residence.msg((CommandSender)player, lm.General_UseNumbers, new Object[0]);
                    return true;
                }
            }
            Map<String, Double> ShopList = Residence.getShopSignUtilManager().getSortedShopList();
            String separator = (Object)ChatColor.GOLD;
            String simbol = "\u25ac";
            int i = 0;
            while (i < 5) {
                separator = String.valueOf(separator) + simbol;
                ++i;
            }
            int pagecount = (int)Math.ceil((double)ShopList.size() / 10.0);
            if (page > pagecount || page < 1) {
                Residence.msg(sender, lm.Shop_NoVotes, new Object[0]);
                return true;
            }
            Residence.msg((CommandSender)player, lm.Shop_ListTopLine, separator, Shoppage, pagecount, separator);
            int start = Shoppage * 10 - 9;
            int end = Shoppage * 10 + 1;
            int position = 0;
            int i3 = start;
            for (Map.Entry<String, Double> one : ShopList.entrySet()) {
                if (++position < start) continue;
                if (position >= end) break;
                Vote vote = Residence.getShopSignUtilManager().getAverageVote(one.getKey());
                String votestat = "";
                votestat = Residence.getConfigManager().isOnlyLike() ? (vote.getAmount() == 0 ? "" : Residence.msg(lm.Shop_ListLiked, Residence.getShopSignUtilManager().getLikes(one.getKey()))) : (vote.getAmount() == 0 ? "" : Residence.msg(lm.Shop_ListVoted, vote.getVote(), vote.getAmount()));
                ClaimedResidence res = Residence.getResidenceManager().getByName(one.getKey());
                String owner = Residence.getResidenceManager().getByName(one.getKey()).getOwner();
                String message3 = Residence.msg(lm.Shop_List, i3, one.getKey(), owner, votestat);
                String desc = res.getShopDesc() == null ? Residence.msg(lm.Shop_NoDesc, new Object[0]) : Residence.msg(lm.Shop_Desc, ChatColor.translateAlternateColorCodes((char)'&', (String)res.getShopDesc().replace("/n", "\n")));
                String prev = "[\"\",{\"text\":\" " + message3 + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/res tp " + one.getKey() + " \"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + desc + "\"}]}}}]";
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + prev));
                ++i3;
            }
            if (pagecount == 1) {
                return true;
            }
            int NextPage = page + 1;
            NextPage = page < pagecount ? NextPage : page;
            int Prevpage = page - 1;
            Prevpage = page > 1 ? Prevpage : page;
            String prevCmd = "/res shop list " + Prevpage;
            String prev = "[\"\",{\"text\":\"" + separator + " " + Residence.msg(lm.General_PrevInfoPage, new Object[0]) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + prevCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + "<<<" + "\"}]}}}";
            String nextCmd = "/res shop list " + NextPage;
            String next = " {\"text\":\"" + Residence.msg(lm.General_NextInfoPage, new Object[0]) + " " + separator + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + nextCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ">>>" + "\"}]}}}]";
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + prev + "," + next));
            return true;
        }
        if (args.length == 2 && args[1].equalsIgnoreCase("DeleteBoard")) {
            if (!resadmin2) {
                Residence.msg((CommandSender)player, lm.General_AdminOnly, new Object[0]);
                return true;
            }
            ShopListener.Delete.add(player.getName());
            Residence.msg((CommandSender)player, lm.Shop_DeleteBoard, new Object[0]);
            return true;
        }
        if (args.length > 2 && args[1].equalsIgnoreCase("setdesc")) {
            ClaimedResidence res = null;
            String desc = "";
            if (args.length >= 2) {
                res = Residence.getResidenceManager().getByLoc(player.getLocation());
                if (res == null) {
                    Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                    return true;
                }
                int i = 2;
                while (i < args.length) {
                    desc = String.valueOf(desc) + args[i];
                    if (i < args.length - 1) {
                        desc = String.valueOf(desc) + " ";
                    }
                    ++i;
                }
            }
            if (res == null) {
                return true;
            }
            if (!res.isOwner(player) && !resadmin2) {
                Residence.msg((CommandSender)player, lm.Residence_NonAdmin, new Object[0]);
                return true;
            }
            res.setShopDesc(desc);
            Residence.msg((CommandSender)player, lm.Shop_DescChange, ChatColor.translateAlternateColorCodes((char)'&', (String)desc));
            return true;
        }
        if (args.length == 3 && args[1].equalsIgnoreCase("createboard")) {
            if (!resadmin2) {
                Residence.msg((CommandSender)player, lm.General_AdminOnly, new Object[0]);
                return true;
            }
            if (!Residence.getSelectionManager().hasPlacedBoth(player.getName())) {
                Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
                return true;
            }
            int place = 1;
            try {
                place = Integer.parseInt(args[2]);
            }
            catch (Exception ex) {
                Residence.msg((CommandSender)player, lm.General_UseNumbers, new Object[0]);
                return true;
            }
            if (place < 1) {
                place = 1;
            }
            Location loc1 = Residence.getSelectionManager().getPlayerLoc1(player.getName());
            Location loc2 = Residence.getSelectionManager().getPlayerLoc2(player.getName());
            if (loc1.getBlockY() < loc2.getBlockY()) {
                Residence.msg((CommandSender)player, lm.Shop_InvalidSelection, new Object[0]);
                return true;
            }
            Board newTemp = new Board();
            newTemp.setStartPlace(place);
            newTemp.setTopLoc(loc1);
            newTemp.setBottomLoc(loc2);
            if (Residence.getShopSignUtilManager().exist(newTemp)) {
                sender.sendMessage(Residence.msg(lm.Shop_BoardExist, new Object[0]));
                return true;
            }
            Residence.getShopSignUtilManager().addBoard(newTemp);
            Residence.msg((CommandSender)player, lm.Shop_NewBoard, new Object[0]);
            Residence.getShopSignUtilManager().BoardUpdate();
            Residence.getShopSignUtilManager().saveSigns();
            return true;
        }
        if ((args.length == 2 || args.length == 3 || args.length == 4) && (args[1].equalsIgnoreCase("vote") || args[1].equalsIgnoreCase("like"))) {
            String resName = "";
            int vote = 5;
            ClaimedResidence res = null;
            if (args.length == 3) {
                if (Residence.getConfigManager().isOnlyLike()) {
                    res = Residence.getResidenceManager().getByName(args[2]);
                    if (res == null) {
                        Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                        return true;
                    }
                    vote = Residence.getConfigManager().getVoteRangeTo();
                } else {
                    res = Residence.getResidenceManager().getByLoc(player.getLocation());
                    if (res == null) {
                        Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                        return true;
                    }
                    try {
                        vote = Integer.parseInt(args[2]);
                    }
                    catch (Exception ex) {
                        Residence.msg((CommandSender)player, lm.General_UseNumbers, new Object[0]);
                        return true;
                    }
                }
            } else if (args.length == 2 && Residence.getConfigManager().isOnlyLike()) {
                res = Residence.getResidenceManager().getByLoc(player.getLocation());
                if (res == null) {
                    Residence.msg((CommandSender)player, lm.Residence_NotIn, new Object[0]);
                    return true;
                }
                vote = Residence.getConfigManager().getVoteRangeTo();
            } else if (args.length == 4 && !Residence.getConfigManager().isOnlyLike()) {
                res = Residence.getResidenceManager().getByName(args[2]);
                if (res == null) {
                    Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                    return true;
                }
                try {
                    vote = Integer.parseInt(args[3]);
                }
                catch (Exception ex) {
                    Residence.msg((CommandSender)player, lm.General_UseNumbers, new Object[0]);
                    return true;
                }
            } else if (args.length == 3 && !Residence.getConfigManager().isOnlyLike()) {
                res = Residence.getResidenceManager().getByLoc(player.getLocation());
                if (res == null) {
                    Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
                    return true;
                }
                try {
                    vote = Integer.parseInt(args[3]);
                }
                catch (Exception ex) {
                    Residence.msg((CommandSender)player, lm.General_UseNumbers, new Object[0]);
                    return true;
                }
            } else {
                return false;
            }
            resName = res.getName();
            if (!res.getPermissions().has("shop", false)) {
                Residence.msg((CommandSender)player, lm.Shop_CantVote, new Object[0]);
                return true;
            }
            if (vote < Residence.getConfigManager().getVoteRangeFrom() || vote > Residence.getConfigManager().getVoteRangeTo()) {
                Residence.msg((CommandSender)player, lm.Shop_VotedRange, Residence.getConfigManager().getVoteRangeFrom(), Residence.getConfigManager().getVoteRangeTo());
                return true;
            }
            if (!res.GetShopVotes().isEmpty()) {
                List<ShopVote> list2 = res.GetShopVotes();
                boolean found = false;
                for (ShopVote OneVote : list2) {
                    if (!OneVote.getName().equalsIgnoreCase(player.getName()) && (OneVote.getUuid() == null || OneVote.getUuid() != player.getUniqueId())) continue;
                    if (Residence.getConfigManager().isOnlyLike()) {
                        Residence.msg((CommandSender)player, lm.Shop_AlreadyLiked, resName);
                        return true;
                    }
                    Residence.msg((CommandSender)player, lm.Shop_VoteChanged, OneVote.getVote(), vote, resName);
                    OneVote.setVote(vote);
                    OneVote.setName(player.getName());
                    OneVote.setTime(System.currentTimeMillis());
                    found = true;
                    break;
                }
                if (!found) {
                    ShopVote newVote = new ShopVote(player.getName(), player.getUniqueId(), vote, System.currentTimeMillis());
                    list2.add(newVote);
                    if (Residence.getConfigManager().isOnlyLike()) {
                        Residence.msg((CommandSender)player, lm.Shop_Liked, resName);
                    } else {
                        Residence.msg((CommandSender)player, lm.Shop_Voted, vote, resName);
                    }
                }
            } else {
                ShopVote newVote = new ShopVote(player.getName(), player.getUniqueId(), vote, System.currentTimeMillis());
                res.addShopVote(newVote);
                if (Residence.getConfigManager().isOnlyLike()) {
                    Residence.msg((CommandSender)player, lm.Shop_Liked, resName);
                } else {
                    Residence.msg((CommandSender)player, lm.Shop_Voted, vote, resName);
                }
            }
            Residence.getShopSignUtilManager().saveShopVotes();
            Residence.getShopSignUtilManager().BoardUpdate();
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Manage residence shop");
        c.get(String.valueOf(path) + "Info", Arrays.asList("Manages residence shop feature"));
        path = String.valueOf(path) + "SubCommands.";
        c.get(String.valueOf(path) + "list.Description", "Shows list of res shops");
        c.get(String.valueOf(path) + "list.Info", Arrays.asList("&eUsage: &6/res shop list", "Shows full list of all residences with shop flag"));
        c.get(String.valueOf(path) + "vote.Description", "Vote for residence shop");
        c.get(String.valueOf(path) + "vote.Info", Arrays.asList("&eUsage: &6/res shop vote <residence> [amount]", "Votes for current or defined residence"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "vote"), Arrays.asList("[residence]", "10"));
        c.get(String.valueOf(path) + "like.Description", "Give like for residence shop");
        c.get(String.valueOf(path) + "like.Info", Arrays.asList("&eUsage: &6/res shop like <residence>", "Gives like for residence shop"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "like"), Arrays.asList("[residenceshop]"));
        c.get(String.valueOf(path) + "votes.Description", "Shows res shop votes");
        c.get(String.valueOf(path) + "votes.Info", Arrays.asList("&eUsage: &6/res shop votes <residence> <page>", "Shows full vote list of current or defined residence shop"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "votes"), Arrays.asList("[residenceshop]"));
        c.get(String.valueOf(path) + "likes.Description", "Shows res shop likes");
        c.get(String.valueOf(path) + "likes.Info", Arrays.asList("&eUsage: &6/res shop likes <residence> <page>", "Shows full like list of current or defined residence shop"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "likes"), Arrays.asList("[residenceshop]"));
        c.get(String.valueOf(path) + "setdesc.Description", "Sets residence shop description");
        c.get(String.valueOf(path) + "setdesc.Info", Arrays.asList("&eUsage: &6/res shop setdesc [text]", "Sets residence shop description. Color code supported. For new line use /n"));
        c.get(String.valueOf(path) + "createboard.Description", "Create res shop board");
        c.get(String.valueOf(path) + "createboard.Info", Arrays.asList("&eUsage: &6/res shop createboard [place]", "Creates res shop board from selected area. Place - position from which to start filling board"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "createboard"), Arrays.asList("1"));
        c.get(String.valueOf(path) + "deleteboard.Description", "Deletes res shop board");
        c.get(String.valueOf(path) + "deleteboard.Info", Arrays.asList("&eUsage: &6/res shop deleteboard", "Deletes res shop board bi right clicking on one of signs"));
    }
}

