/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.bekvon.bukkit.residence.text.help;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.rent.RentableLand;
import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.utils.GetTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class InformationPager {
    Residence plugin;

    public InformationPager(Residence plugin) {
        this.plugin = plugin;
    }

    public void printInfo(CommandSender sender, String title, String[] lines, int page) {
        this.printInfo(sender, title, Arrays.asList(lines), page);
    }

    public void printInfo(CommandSender sender, String title, List<String> lines, int page) {
        int perPage = 6;
        int start = (page - 1) * perPage;
        int end = start + perPage;
        int pagecount = (int)Math.ceil((double)lines.size() / (double)perPage);
        if (pagecount == 0) {
            pagecount = 1;
        }
        if (page > pagecount) {
            sender.sendMessage((Object)ChatColor.RED + Residence.msg(lm.Invalid_Page, new Object[0]));
            return;
        }
        Residence.msg(sender, lm.InformationPage_TopLine, title);
        Residence.msg(sender, lm.InformationPage_Page, Residence.msg(lm.General_GenericPages, String.format("%d", page), pagecount, lines.size()));
        int i = start;
        while (i < end) {
            if (lines.size() > i) {
                sender.sendMessage((Object)ChatColor.GREEN + lines.get(i));
            }
            ++i;
        }
        if (pagecount > page) {
            Residence.msg(sender, lm.InformationPage_NextPage, Residence.msg(lm.General_NextPage, new Object[0]));
        } else {
            Residence.msg(sender, lm.InformationPage_NoNextPage, new Object[0]);
        }
    }

    public void printListInfo(CommandSender sender, String targetPlayer, TreeMap<String, ClaimedResidence> ownedResidences, int page, boolean resadmin2) {
        int perPage = 20;
        if (sender instanceof Player) {
            perPage = 6;
        }
        int start = (page - 1) * perPage;
        int end = start + perPage;
        int pagecount = (int)Math.ceil((double)ownedResidences.size() / (double)perPage);
        if (page == -1) {
            start = 0;
            end = ownedResidences.size();
            page = 1;
            pagecount = 1;
        }
        if (pagecount == 0) {
            pagecount = 1;
        }
        if (page > pagecount) {
            sender.sendMessage((Object)ChatColor.RED + Residence.msg(lm.Invalid_Page, new Object[0]));
            return;
        }
        if (targetPlayer != null) {
            Residence.msg(sender, lm.InformationPage_TopLine, String.valueOf(Residence.msg(lm.General_Residences, new Object[0])) + " - " + targetPlayer);
        }
        Residence.msg(sender, lm.InformationPage_Page, Residence.msg(lm.General_GenericPages, String.format("%d", page), pagecount, ownedResidences.size()));
        String cmd2 = "res";
        if (resadmin2) {
            cmd2 = "resadmin";
        }
        if (ownedResidences.size() < end) {
            end = ownedResidences.size();
        }
        if (!(sender instanceof Player)) {
            this.printListWithDelay(sender, ownedResidences, start, resadmin2);
            return;
        }
        ArrayList<String> linesForConsole = new ArrayList<String>();
        int y = 0;
        for (Map.Entry<String, ClaimedResidence> resT : ownedResidences.entrySet()) {
            if (ownedResidences.size() < ++y) break;
            if (y <= start) continue;
            if (y > end) break;
            ClaimedResidence res = resT.getValue();
            StringBuilder StringB = new StringBuilder();
            StringB.append(" " + Residence.msg(lm.General_Owner, res.getOwner()));
            String worldInfo = "";
            if (res.getPermissions().has("hidden", FlagPermissions.FlagCombo.FalseOrNone) && res.getPermissions().has("coords", FlagPermissions.FlagCombo.TrueOrNone) || resadmin2) {
                worldInfo = String.valueOf(worldInfo) + "&6 (&3";
                CuboidArea area2 = res.getAreaArray()[0];
                worldInfo = String.valueOf(worldInfo) + Residence.msg(lm.General_CoordsTop, area2.getHighLoc().getBlockX(), area2.getHighLoc().getBlockY(), area2.getHighLoc().getBlockZ());
                worldInfo = String.valueOf(worldInfo) + "&6; &3";
                worldInfo = String.valueOf(worldInfo) + Residence.msg(lm.General_CoordsBottom, area2.getLowLoc().getBlockX(), area2.getLowLoc().getBlockY(), area2.getLowLoc().getBlockZ());
                worldInfo = String.valueOf(worldInfo) + "&6)";
                worldInfo = ChatColor.translateAlternateColorCodes((char)'&', (String)worldInfo);
                StringB.append("\n" + worldInfo);
            }
            StringB.append("\n " + Residence.msg(lm.General_CreatedOn, GetTime.getTime(res.getCreateTime())));
            String ExtraString = "";
            if (res.isForRent()) {
                if (res.isRented()) {
                    ExtraString = " " + Residence.msg(lm.Residence_IsRented, new Object[0]);
                    StringB.append("\n " + Residence.msg(lm.Residence_RentedBy, res.getRentedLand().player));
                } else {
                    ExtraString = " " + Residence.msg(lm.Residence_IsForRent, new Object[0]);
                }
                RentableLand rentable = res.getRentable();
                StringB.append("\n " + Residence.msg(lm.General_Cost, rentable.cost, rentable.days));
                StringB.append("\n " + Residence.msg(lm.Rentable_AllowRenewing, rentable.AllowRenewing));
                StringB.append("\n " + Residence.msg(lm.Rentable_StayInMarket, rentable.StayInMarket));
                StringB.append("\n " + Residence.msg(lm.Rentable_AllowAutoPay, rentable.AllowAutoPay));
            }
            if (res.isForSell()) {
                ExtraString = " " + Residence.msg(lm.Residence_IsForSale, new Object[0]);
                StringB.append("\n " + Residence.msg(lm.Economy_LandForSale, new Object[0]) + " " + res.getSellPrice());
            }
            String tpFlag = "";
            String moveFlag = "";
            if (sender instanceof Player && !res.isOwner(sender)) {
                tpFlag = res.getPermissions().playerHas((Player)sender, Flags.tp, true) ? (Object)ChatColor.DARK_GREEN + "T" : (Object)ChatColor.DARK_RED + "T";
                moveFlag = res.getPermissions().playerHas(sender.getName(), Flags.move, true) ? (Object)ChatColor.DARK_GREEN + "M" : (Object)ChatColor.DARK_RED + "M";
            }
            String msg = Residence.msg(lm.Residence_ResList, y, res.getName(), res.getWorld(), String.valueOf(tpFlag) + moveFlag, ExtraString);
            if (sender instanceof Player) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + Residence.getResidenceManager().convertToRaw(null, msg, StringB.toString(), new StringBuilder(String.valueOf(cmd2)).append(" tp ").append(res.getName()).toString())));
                continue;
            }
            linesForConsole.add(String.valueOf(msg) + " " + StringB.toString().replace("\n", ""));
        }
        if (targetPlayer != null) {
            this.ShowPagination(sender.getName(), pagecount, page, String.valueOf(cmd2) + " list " + targetPlayer);
        } else {
            this.ShowPagination(sender.getName(), pagecount, page, String.valueOf(cmd2) + " listall");
        }
    }

    private void printListWithDelay(final CommandSender sender, final TreeMap<String, ClaimedResidence> ownedResidences, final int start, final boolean resadmin2) {
        int i = start;
        for (Map.Entry<String, ClaimedResidence> resT : ownedResidences.entrySet()) {
            if (++i >= start + 100 || ownedResidences.size() <= i) break;
            ClaimedResidence res = resT.getValue();
            StringBuilder StringB = new StringBuilder();
            StringB.append(" " + Residence.msg(lm.General_Owner, res.getOwner()));
            String worldInfo = "";
            if (res.getPermissions().has("hidden", FlagPermissions.FlagCombo.FalseOrNone) && res.getPermissions().has("coords", FlagPermissions.FlagCombo.TrueOrNone) || resadmin2) {
                worldInfo = String.valueOf(worldInfo) + "&6 (&3";
                CuboidArea area2 = res.getAreaArray()[0];
                worldInfo = String.valueOf(worldInfo) + Residence.msg(lm.General_CoordsTop, area2.getHighLoc().getBlockX(), area2.getHighLoc().getBlockY(), area2.getHighLoc().getBlockZ());
                worldInfo = String.valueOf(worldInfo) + "&6; &3";
                worldInfo = String.valueOf(worldInfo) + Residence.msg(lm.General_CoordsBottom, area2.getLowLoc().getBlockX(), area2.getLowLoc().getBlockY(), area2.getLowLoc().getBlockZ());
                worldInfo = String.valueOf(worldInfo) + "&6)";
                worldInfo = ChatColor.translateAlternateColorCodes((char)'&', (String)worldInfo);
                StringB.append("\n" + worldInfo);
            }
            StringB.append("\n " + Residence.msg(lm.General_CreatedOn, GetTime.getTime(res.getCreateTime())));
            String ExtraString = "";
            if (res.isForRent()) {
                if (res.isRented()) {
                    ExtraString = " " + Residence.msg(lm.Residence_IsRented, new Object[0]);
                    StringB.append("\n " + Residence.msg(lm.Residence_RentedBy, res.getRentedLand().player));
                } else {
                    ExtraString = " " + Residence.msg(lm.Residence_IsForRent, new Object[0]);
                }
                RentableLand rentable = res.getRentable();
                StringB.append("\n " + Residence.msg(lm.General_Cost, rentable.cost, rentable.days));
                StringB.append("\n " + Residence.msg(lm.Rentable_AllowRenewing, rentable.AllowRenewing));
                StringB.append("\n " + Residence.msg(lm.Rentable_StayInMarket, rentable.StayInMarket));
                StringB.append("\n " + Residence.msg(lm.Rentable_AllowAutoPay, rentable.AllowAutoPay));
            }
            if (res.isForSell()) {
                ExtraString = " " + Residence.msg(lm.Residence_IsForSale, new Object[0]);
                StringB.append("\n " + Residence.msg(lm.Economy_LandForSale, new Object[0]) + " " + res.getSellPrice());
            }
            String msg = Residence.msg(lm.Residence_ResList, i + 1, res.getName(), res.getWorld(), "", ExtraString);
            msg = ChatColor.stripColor((String)(String.valueOf(msg) + " " + StringB.toString().replace("\n", "")));
            msg = msg.replaceAll("\\s{2}", " ");
            sender.sendMessage(msg);
        }
        if (ownedResidences.isEmpty()) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                InformationPager.this.printListWithDelay(sender, ownedResidences, start + 100, resadmin2);
            }
        }, 5);
    }

    public void ShowPagination(String target, int pageCount, int CurrentPage, String cmd2) {
        if (target.equalsIgnoreCase("console")) {
            return;
        }
        String separator = (Object)ChatColor.GOLD;
        String simbol = "\u25ac";
        int i = 0;
        while (i < 10) {
            separator = String.valueOf(separator) + simbol;
            ++i;
        }
        if (pageCount == 1) {
            return;
        }
        int NextPage = CurrentPage + 1;
        NextPage = CurrentPage < pageCount ? NextPage : CurrentPage;
        int Prevpage = CurrentPage - 1;
        Prevpage = CurrentPage > 1 ? Prevpage : CurrentPage;
        String prevCmd = "/" + cmd2 + " " + Prevpage;
        String prev = "\"\",{\"text\":\"" + separator + " " + Residence.msg(lm.General_PrevInfoPage, new Object[0]) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + prevCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + "<<<" + "\"}]}}}";
        String nextCmd = "/" + cmd2 + " " + NextPage;
        String next = " {\"text\":\"" + Residence.msg(lm.General_NextInfoPage, new Object[0]) + " " + separator + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + nextCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ">>>" + "\"}]}}}";
        if (CurrentPage >= pageCount) {
            next = "{\"text\":\"" + Residence.msg(lm.General_NextInfoPage, new Object[0]) + " " + separator + "\"}";
        }
        if (CurrentPage <= 1) {
            prev = "{\"text\":\"" + separator + " " + Residence.msg(lm.General_PrevInfoPage, new Object[0]) + "\"}";
        }
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + target + " [" + prev + "," + next + "]"));
    }

}

