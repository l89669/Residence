/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.economy;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.MarketBuyInterface;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.Visualizer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.EconomyInterface;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TransactionManager
implements MarketBuyInterface {
    private Set<ClaimedResidence> sellAmount = new HashSet<ClaimedResidence>();

    public static boolean chargeEconomyMoney(Player player, double chargeamount) {
        EconomyInterface econ = Residence.getEconomyManager();
        if (econ == null) {
            Residence.msg((CommandSender)player, lm.Economy_MarketDisabled, new Object[0]);
            return false;
        }
        if (!econ.canAfford(player.getName(), chargeamount)) {
            Residence.msg((CommandSender)player, lm.Economy_NotEnoughMoney, new Object[0]);
            return false;
        }
        econ.subtract(player.getName(), chargeamount);
        try {
            Residence.msg((CommandSender)player, lm.Economy_MoneyCharged, chargeamount, econ.getName());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return true;
    }

    public static boolean giveEconomyMoney(Player player, int amount) {
        if (player == null) {
            return false;
        }
        if (amount == 0) {
            return true;
        }
        EconomyInterface econ = Residence.getEconomyManager();
        if (econ == null) {
            Residence.msg((CommandSender)player, lm.Economy_MarketDisabled, new Object[0]);
            return false;
        }
        econ.add(player.getName(), amount);
        Residence.msg((CommandSender)player, lm.Economy_MoneyAdded, String.format("%d", amount), econ.getName());
        return true;
    }

    public void putForSale(String areaname, Player player, int amount, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        this.putForSale(res, player, amount, resadmin2);
    }

    public void putForSale(ClaimedResidence res, Player player, int amount, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        if (Residence.getConfigManager().enabledRentSystem() && !resadmin2) {
            if (res.isForRent()) {
                Residence.msg((CommandSender)player, lm.Economy_RentSellFail, new Object[0]);
                return;
            }
            if (res.isSubzoneForRent()) {
                Residence.msg((CommandSender)player, lm.Economy_SubzoneRentSellFail, new Object[0]);
                return;
            }
            if (res.isParentForRent()) {
                Residence.msg((CommandSender)player, lm.Economy_ParentRentSellFail, new Object[0]);
                return;
            }
        }
        if (!Residence.getConfigManager().isSellSubzone() && res.isSubzone()) {
            Residence.msg((CommandSender)player, lm.Economy_SubzoneSellFail, new Object[0]);
            return;
        }
        if (!resadmin2) {
            boolean cansell;
            if (!Residence.getConfigManager().enableEconomy() || Residence.getEconomyManager() == null) {
                Residence.msg((CommandSender)player, lm.Economy_MarketDisabled, new Object[0]);
                return;
            }
            ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
            boolean bl = cansell = rPlayer.getGroup().canSellLand() || player.hasPermission("residence.sell");
            if (!cansell && !resadmin2) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return;
            }
            if (amount <= 0) {
                Residence.msg((CommandSender)player, lm.Invalid_Amount, new Object[0]);
                return;
            }
        }
        if (!res.isOwner(player) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return;
        }
        if (this.sellAmount.contains(res)) {
            Residence.msg((CommandSender)player, lm.Economy_AlreadySellFail, new Object[0]);
            return;
        }
        res.setSellPrice(amount);
        this.sellAmount.add(res);
        Residence.getSignUtil().CheckSign(res);
        Residence.msg((CommandSender)player, lm.Residence_ForSale, res.getName(), amount);
    }

    @Override
    public boolean putForSale(String areaname, int amount) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        return this.putForSale(res, amount);
    }

    public boolean putForSale(ClaimedResidence res, int amount) {
        if (res == null) {
            return false;
        }
        if (Residence.getConfigManager().enabledRentSystem() && (res.isForRent() || res.isSubzoneForRent() || res.isParentForRent())) {
            return false;
        }
        if (this.sellAmount.contains(res)) {
            return false;
        }
        res.setSellPrice(amount);
        this.sellAmount.add(res);
        return true;
    }

    @Override
    public void buyPlot(String areaname, Player player, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        this.buyPlot(res, player, resadmin2);
    }

    public void buyPlot(ClaimedResidence res, Player player, boolean resadmin2) {
        EconomyInterface econ;
        if (res == null || !res.isForSell()) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (!resadmin2) {
            boolean canbuy;
            if (!Residence.getConfigManager().enableEconomy() || Residence.getEconomyManager() == null) {
                Residence.msg((CommandSender)player, lm.Economy_MarketDisabled, new Object[0]);
                return;
            }
            boolean bl = canbuy = group.canBuyLand() || player.hasPermission("residence.buy");
            if (!canbuy && !resadmin2) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return;
            }
        }
        if (res.getPermissions().getOwner().equals(player.getName())) {
            Residence.msg((CommandSender)player, lm.Economy_OwnerBuyFail, new Object[0]);
            return;
        }
        if (Residence.getResidenceManager().getOwnedZoneCount(player.getName()) >= rPlayer.getMaxRes() && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Residence_TooMany, new Object[0]);
            return;
        }
        Server serv = Residence.getServ();
        int amount = res.getSellPrice();
        if (!resadmin2 && !group.buyLandIgnoreLimits()) {
            CuboidArea[] areas;
            CuboidArea[] arrcuboidArea = areas = res.getAreaArray();
            int n = arrcuboidArea.length;
            int n2 = 0;
            while (n2 < n) {
                CuboidArea thisarea = arrcuboidArea[n2];
                if (!res.isSubzone() && !res.isSmallerThanMax(player, thisarea, resadmin2) || res.isSubzone() && !res.isSmallerThanMaxSubzone(player, thisarea, resadmin2)) {
                    Residence.msg((CommandSender)player, lm.Residence_BuyTooBig, new Object[0]);
                    return;
                }
                ++n2;
            }
        }
        if ((econ = Residence.getEconomyManager()) == null) {
            Residence.msg((CommandSender)player, lm.Economy_MarketDisabled, new Object[0]);
            return;
        }
        String buyerName = player.getName();
        String sellerName = res.getPermissions().getOwner();
        Player sellerNameFix = Residence.getServ().getPlayer(sellerName);
        if (sellerNameFix != null) {
            sellerName = sellerNameFix.getName();
        }
        if (econ.canAfford(buyerName, amount)) {
            if (!econ.transfer(buyerName, sellerName, amount)) {
                player.sendMessage((Object)ChatColor.RED + "Error, could not transfer " + amount + " from " + buyerName + " to " + sellerName);
                return;
            }
            res.getPermissions().setOwner(player.getName(), true);
            res.getPermissions().applyDefaultFlags();
            this.removeFromSale(res);
            if (Residence.getConfigManager().isRemoveLwcOnBuy()) {
                Residence.getResidenceManager().removeLwcFromResidence(player, res);
            }
            Residence.getSignUtil().CheckSign(res);
            Visualizer v = new Visualizer(player);
            v.setAreas(res);
            Residence.getSelectionManager().showBounds(player, v);
            Residence.msg((CommandSender)player, lm.Economy_MoneyCharged, String.format("%d", amount), econ.getName());
            Residence.msg((CommandSender)player, lm.Residence_Bought, res.getResidenceName());
            Player seller = serv.getPlayer(sellerName);
            if (seller != null && seller.isOnline()) {
                seller.sendMessage(Residence.msg(lm.Residence_Buy, player.getName(), res.getResidenceName()));
                seller.sendMessage(Residence.msg(lm.Economy_MoneyCredit, String.format("%d", amount), econ.getName()));
            }
        } else {
            Residence.msg((CommandSender)player, lm.Economy_NotEnoughMoney, new Object[0]);
        }
    }

    public void removeFromSale(Player player, String areaname, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        this.removeFromSale(player, res, resadmin2);
    }

    public void removeFromSale(Player player, ClaimedResidence res, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Area, new Object[0]);
            return;
        }
        if (!res.isForSell()) {
            Residence.msg((CommandSender)player, lm.Residence_NotForSale, new Object[0]);
            return;
        }
        if (res.isOwner(player) || resadmin2) {
            this.removeFromSale(res);
            Residence.getSignUtil().CheckSign(res);
            Residence.msg((CommandSender)player, lm.Residence_StopSelling, new Object[0]);
        } else {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
        }
    }

    @Override
    public void removeFromSale(String areaname) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        this.removeFromSale(res);
    }

    public void removeFromSale(ClaimedResidence res) {
        if (res == null) {
            return;
        }
        this.sellAmount.remove(res);
        Residence.getSignUtil().removeSign(res);
    }

    @Override
    public boolean isForSale(String areaname) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        return this.isForSale(res);
    }

    public boolean isForSale(ClaimedResidence res) {
        if (res == null) {
            return false;
        }
        return this.sellAmount.contains(res);
    }

    public boolean viewSaleInfo(String areaname, Player player) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        return this.viewSaleInfo(res, player);
    }

    public boolean viewSaleInfo(ClaimedResidence res, Player player) {
        String etime;
        if (res == null || !res.isForSell()) {
            return false;
        }
        if (!this.sellAmount.contains(res)) {
            return false;
        }
        Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
        Residence.msg((CommandSender)player, lm.Area_Name, res.getName());
        Residence.msg((CommandSender)player, lm.Economy_SellAmount, res.getSellPrice());
        if (Residence.getConfigManager().useLeases() && (etime = Residence.getLeaseManager().getExpireTime(res.getName())) != null) {
            Residence.msg((CommandSender)player, lm.Economy_LeaseExpire, etime);
        }
        Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
        return true;
    }

    public void printForSaleResidences(Player player, int page) {
        ArrayList<ClaimedResidence> toRemove = new ArrayList<ClaimedResidence>();
        Residence.msg((CommandSender)player, lm.Economy_LandForSale, new Object[0]);
        StringBuilder sbuild = new StringBuilder();
        sbuild.append((Object)ChatColor.GREEN);
        int perpage = 10;
        int pagecount = (int)Math.ceil((double)this.sellAmount.size() / (double)perpage);
        if (page < 1) {
            page = 1;
        }
        int z = 0;
        for (ClaimedResidence res : this.sellAmount) {
            if (++z <= (page - 1) * perpage) continue;
            if (z > (page - 1) * perpage + perpage) break;
            if (res == null) {
                --z;
                toRemove.add(res);
                continue;
            }
            Residence.msg((CommandSender)player, lm.Economy_SellList, z, res.getName(), res.getSellPrice(), res.getOwner());
        }
        for (ClaimedResidence one : toRemove) {
            this.sellAmount.remove(one);
        }
        String separator = (Object)ChatColor.GOLD;
        String simbol = "\u25ac";
        int i = 0;
        while (i < 10) {
            separator = String.valueOf(separator) + simbol;
            ++i;
        }
        if (pagecount == 1) {
            return;
        }
        int NextPage = page + 1;
        NextPage = page < pagecount ? NextPage : page;
        int Prevpage = page - 1;
        Prevpage = page > 1 ? Prevpage : page;
        String prevCmd = "/res market list sell " + Prevpage;
        String prev = "[\"\",{\"text\":\"" + separator + " " + Residence.msg(lm.General_PrevInfoPage, new Object[0]) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + prevCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + "<<<" + "\"}]}}}";
        String nextCmd = "/res market list sell " + NextPage;
        String next = " {\"text\":\"" + Residence.msg(lm.General_NextInfoPage, new Object[0]) + " " + separator + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + nextCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ">>>" + "\"}]}}}]";
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + player.getName() + " " + prev + "," + next));
    }

    public void clearSales() {
        for (ClaimedResidence res : this.sellAmount) {
            if (res == null) continue;
            res.setSellPrice(-1);
        }
        this.sellAmount.clear();
        System.out.println("[Residence] - ReInit land selling.");
    }

    @Override
    public int getSaleAmount(String areaname) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(areaname);
        return this.getSaleAmount(res);
    }

    public int getSaleAmount(ClaimedResidence res) {
        if (res == null) {
            return -1;
        }
        return res.getSellPrice();
    }

    public void load(Map<String, Integer> root) {
        if (root == null) {
            return;
        }
        for (Map.Entry<String, Integer> one : root.entrySet()) {
            ClaimedResidence res = Residence.getResidenceManager().getByName(one.getKey());
            if (res == null) continue;
            res.setSellPrice(one.getValue());
            this.sellAmount.add(res);
        }
    }

    @Override
    public Map<String, Integer> getBuyableResidences() {
        HashMap<String, Integer> list2 = new HashMap<String, Integer>();
        for (ClaimedResidence res : this.sellAmount) {
            if (res == null) continue;
            list2.put(res.getName(), res.getSellPrice());
        }
        return list2;
    }

    public Map<String, Integer> save() {
        return this.getBuyableResidences();
    }
}

