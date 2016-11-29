/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.PluginManager
 */
package com.bekvon.bukkit.residence.economy.rent;

import com.bekvon.bukkit.residence.ConfigManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.MarketRentInterface;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.Visualizer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.rent.RentableLand;
import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.event.ResidenceRentEvent;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.utils.GetTime;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

public class RentManager
implements MarketRentInterface {
    private Set<ClaimedResidence> rentedLand = new HashSet<ClaimedResidence>();
    private Set<ClaimedResidence> rentableLand = new HashSet<ClaimedResidence>();

    @Override
    public RentedLand getRentedLand(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.getRentedLand(res);
    }

    public RentedLand getRentedLand(ClaimedResidence res) {
        if (res == null) {
            return null;
        }
        return res.isRented() ? res.getRentedLand() : null;
    }

    @Override
    public List<String> getRentedLands(String playername) {
        return this.getRentedLands(playername, false);
    }

    public List<String> getRentedLands(String playername, boolean onlyHidden) {
        ArrayList<String> rentedLands = new ArrayList<String>();
        for (ClaimedResidence res : this.rentedLand) {
            if (res == null || !res.isRented() || !res.getRentedLand().player.equals(playername)) continue;
            String world = " ";
            ClaimedResidence topres = res.getTopParent();
            world = topres.getWorld();
            boolean hidden = topres.getPermissions().has("hidden", false);
            if (onlyHidden && !hidden) continue;
            rentedLands.add(String.valueOf(Residence.msg(lm.Residence_List, "", res.getName(), world)) + Residence.msg(lm.Rent_Rented, new Object[0]));
        }
        return rentedLands;
    }

    public List<ClaimedResidence> getRents(String playername) {
        return this.getRents(playername, false);
    }

    public List<ClaimedResidence> getRents(String playername, boolean onlyHidden) {
        return this.getRents(playername, onlyHidden, null);
    }

    public List<ClaimedResidence> getRents(String playername, boolean onlyHidden, World world) {
        ArrayList<ClaimedResidence> rentedLands = new ArrayList<ClaimedResidence>();
        for (ClaimedResidence res : this.rentedLand) {
            if (res == null || !res.isRented() || !res.getRentedLand().player.equalsIgnoreCase(playername)) continue;
            ClaimedResidence topres = res.getTopParent();
            boolean hidden = topres.getPermissions().has("hidden", false);
            if (onlyHidden && !hidden || world != null && !world.getName().equalsIgnoreCase(res.getWorld())) continue;
            rentedLands.add(res);
        }
        return rentedLands;
    }

    public TreeMap<String, ClaimedResidence> getRentsMap(String playername, boolean onlyHidden, World world) {
        TreeMap<String, ClaimedResidence> rentedLands = new TreeMap<String, ClaimedResidence>();
        for (ClaimedResidence res : this.rentedLand) {
            if (res == null || !res.isRented() || !res.getRentedLand().player.equalsIgnoreCase(playername)) continue;
            ClaimedResidence topres = res.getTopParent();
            boolean hidden = topres.getPermissions().has("hidden", false);
            if (onlyHidden && !hidden || world != null && !world.getName().equalsIgnoreCase(res.getWorld())) continue;
            rentedLands.put(res.getName(), res);
        }
        return rentedLands;
    }

    public List<String> getRentedLandsList(Player player) {
        return this.getRentedLandsList(player.getName());
    }

    public List<String> getRentedLandsList(String playername) {
        ArrayList<String> rentedLands = new ArrayList<String>();
        for (ClaimedResidence res : this.rentedLand) {
            if (res == null || !res.isRented() || !res.getRentedLand().player.equalsIgnoreCase(playername)) continue;
            rentedLands.add(res.getName());
        }
        return rentedLands;
    }

    @Override
    public void setForRent(Player player, String landName, int amount, int days, boolean AllowRenewing, boolean resadmin2) {
        this.setForRent(player, landName, amount, days, AllowRenewing, Residence.getConfigManager().isRentStayInMarket(), resadmin2);
    }

    @Override
    public void setForRent(Player player, String landName, int amount, int days, boolean AllowRenewing, boolean StayInMarket, boolean resadmin2) {
        this.setForRent(player, landName, amount, days, AllowRenewing, StayInMarket, Residence.getConfigManager().isRentAllowAutoPay(), resadmin2);
    }

    @Override
    public void setForRent(Player player, String landName, int amount, int days, boolean AllowRenewing, boolean StayInMarket, boolean AllowAutoPay, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.setForRent(player, res, amount, days, AllowRenewing, StayInMarket, AllowAutoPay, resadmin2);
    }

    public void setForRent(Player player, ClaimedResidence res, int amount, int days, boolean AllowRenewing, boolean StayInMarket, boolean AllowAutoPay, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        if (!Residence.getConfigManager().enabledRentSystem()) {
            Residence.msg((CommandSender)player, lm.Economy_MarketDisabled, new Object[0]);
            return;
        }
        if (res.isForSell() && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Economy_SellRentFail, new Object[0]);
            return;
        }
        if (res.isParentForSell() && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Economy_ParentSellRentFail, new Object[0]);
            return;
        }
        if (!resadmin2) {
            if (!res.getPermissions().hasResidencePermission((CommandSender)player, true)) {
                Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
                return;
            }
            ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
            PermissionGroup group = rPlayer.getGroup();
            if (this.getRentableCount(player.getName()) >= group.getMaxRentables()) {
                Residence.msg((CommandSender)player, lm.Residence_MaxRent, new Object[0]);
                return;
            }
        }
        if (!this.rentableLand.contains(res)) {
            ResidenceRentEvent revent = new ResidenceRentEvent(res, player, ResidenceRentEvent.RentEventType.RENTABLE);
            Residence.getServ().getPluginManager().callEvent((Event)revent);
            if (revent.isCancelled()) {
                return;
            }
            RentableLand newrent = new RentableLand();
            newrent.days = days;
            newrent.cost = amount;
            newrent.AllowRenewing = AllowRenewing;
            newrent.StayInMarket = StayInMarket;
            newrent.AllowAutoPay = AllowAutoPay;
            res.setRentable(newrent);
            this.rentableLand.add(res);
            Residence.msg((CommandSender)player, lm.Residence_ForRentSuccess, res.getResidenceName(), amount, days);
        } else {
            Residence.msg((CommandSender)player, lm.Residence_AlreadyRent, new Object[0]);
        }
    }

    @Override
    public void rent(Player player, String landName, boolean AutoPay, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.rent(player, res, AutoPay, resadmin2);
    }

    public void rent(Player player, ClaimedResidence res, boolean AutoPay, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        if (!Residence.getConfigManager().enabledRentSystem()) {
            Residence.msg((CommandSender)player, lm.Rent_Disabled, new Object[0]);
            return;
        }
        if (res.isOwner(player)) {
            Residence.msg((CommandSender)player, lm.Economy_OwnerRentFail, new Object[0]);
            return;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        if (!resadmin2 && this.getRentCount(player.getName()) >= rPlayer.getMaxRents()) {
            Residence.msg((CommandSender)player, lm.Residence_MaxRent, new Object[0]);
            return;
        }
        if (!res.isForRent()) {
            Residence.msg((CommandSender)player, lm.Residence_NotForRent, new Object[0]);
            return;
        }
        if (res.isRented()) {
            this.printRentInfo(player, res.getName());
            return;
        }
        RentableLand land = res.getRentable();
        if (Residence.getEconomyManager().canAfford(player.getName(), land.cost)) {
            ResidenceRentEvent revent = new ResidenceRentEvent(res, player, ResidenceRentEvent.RentEventType.RENT);
            Residence.getServ().getPluginManager().callEvent((Event)revent);
            if (revent.isCancelled()) {
                return;
            }
            if (!land.AllowAutoPay && AutoPay) {
                Residence.msg((CommandSender)player, lm.Residence_CantAutoPay, new Object[0]);
                AutoPay = false;
            }
            if (Residence.getEconomyManager().transfer(player.getName(), res.getPermissions().getOwner(), land.cost)) {
                RentedLand newrent = new RentedLand();
                newrent.player = player.getName();
                newrent.startTime = System.currentTimeMillis();
                newrent.endTime = System.currentTimeMillis() + RentManager.daysToMs(land.days);
                newrent.AutoPay = AutoPay;
                res.setRented(newrent);
                this.rentedLand.add(res);
                Residence.getSignUtil().CheckSign(res);
                Visualizer v = new Visualizer(player);
                v.setAreas(res);
                Residence.getSelectionManager().showBounds(player, v);
                res.getPermissions().copyUserPermissions(res.getPermissions().getOwner(), player.getName());
                res.getPermissions().clearPlayersFlags(res.getPermissions().getOwner());
                res.getPermissions().setPlayerFlag(player.getName(), "admin", FlagPermissions.FlagState.TRUE);
                Residence.msg((CommandSender)player, lm.Residence_RentSuccess, res.getName(), land.days);
                if (Residence.getSchematicManager() != null && Residence.getConfigManager().RestoreAfterRentEnds && !Residence.getConfigManager().SchematicsSaveOnFlagChange && res.getPermissions().has("backup", true)) {
                    Residence.getSchematicManager().save(res);
                }
            } else {
                player.sendMessage((Object)ChatColor.RED + "Error, unable to transfer money...");
            }
        } else {
            Residence.msg((CommandSender)player, lm.Economy_NotEnoughMoney, new Object[0]);
        }
    }

    public void payRent(Player player, String landName, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.payRent(player, res, resadmin2);
    }

    public void payRent(Player player, ClaimedResidence res, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        if (!Residence.getConfigManager().enabledRentSystem()) {
            Residence.msg((CommandSender)player, lm.Rent_Disabled, new Object[0]);
            return;
        }
        if (!res.isForRent()) {
            Residence.msg((CommandSender)player, lm.Residence_NotForRent, new Object[0]);
            return;
        }
        if (res.isRented() && !this.getRentingPlayer(res).equals(player.getName()) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Rent_NotByYou, new Object[0]);
            return;
        }
        RentableLand land = res.getRentable();
        RentedLand rentedLand = res.getRentedLand();
        if (rentedLand == null) {
            Residence.msg((CommandSender)player, lm.Residence_NotRented, new Object[0]);
            return;
        }
        if (!land.AllowRenewing) {
            Residence.msg((CommandSender)player, lm.Rent_OneTime, new Object[0]);
            return;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (!resadmin2 && group.getMaxRentDays() != -1 && RentManager.msToDays(rentedLand.endTime - System.currentTimeMillis() + RentManager.daysToMs(land.days)) >= group.getMaxRentDays()) {
            Residence.msg((CommandSender)player, lm.Rent_MaxRentDays, group.getMaxRentDays());
            return;
        }
        if (Residence.getEconomyManager().canAfford(player.getName(), land.cost)) {
            ResidenceRentEvent revent = new ResidenceRentEvent(res, player, ResidenceRentEvent.RentEventType.RENT);
            Residence.getServ().getPluginManager().callEvent((Event)revent);
            if (revent.isCancelled()) {
                return;
            }
            if (Residence.getEconomyManager().transfer(player.getName(), res.getPermissions().getOwner(), land.cost)) {
                rentedLand.endTime += RentManager.daysToMs(land.days);
                Residence.getSignUtil().CheckSign(res);
                Visualizer v = new Visualizer(player);
                v.setAreas(res);
                Residence.getSelectionManager().showBounds(player, v);
                Residence.msg((CommandSender)player, lm.Rent_Extended, land.days, res.getName());
                Residence.msg((CommandSender)player, lm.Rent_Expire, GetTime.getTime(rentedLand.endTime));
            } else {
                player.sendMessage((Object)ChatColor.RED + "Error, unable to transfer money...");
            }
        } else {
            Residence.msg((CommandSender)player, lm.Economy_NotEnoughMoney, new Object[0]);
        }
    }

    @Override
    public void unrent(Player player, String landName, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.unrent(player, res, resadmin2);
    }

    public void unrent(Player player, ClaimedResidence res, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        RentedLand rent = res.getRentedLand();
        if (rent == null) {
            Residence.msg((CommandSender)player, lm.Residence_NotRented, new Object[0]);
            return;
        }
        if (resadmin2 || rent.player.equals(player.getName()) || res.isOwner(player) && player.hasPermission("residence.market.evict")) {
            ResidenceRentEvent revent = new ResidenceRentEvent(res, player, ResidenceRentEvent.RentEventType.UNRENTABLE);
            Residence.getServ().getPluginManager().callEvent((Event)revent);
            if (revent.isCancelled()) {
                return;
            }
            this.rentedLand.remove(res);
            res.setRented(null);
            if (!res.getRentable().AllowRenewing && !res.getRentable().StayInMarket) {
                this.rentableLand.remove(res);
                res.setRentable(null);
            }
            boolean backup = res.getPermissions().has("backup", false);
            if (Residence.getConfigManager().isRemoveLwcOnUnrent()) {
                Residence.getResidenceManager().removeLwcFromResidence(player, res);
            }
            res.getPermissions().applyDefaultFlags();
            if (Residence.getSchematicManager() != null && Residence.getConfigManager().RestoreAfterRentEnds && backup) {
                Residence.getSchematicManager().load(res);
                res.getPermissions().setFlag("backup", FlagPermissions.FlagState.TRUE);
            }
            Residence.getSignUtil().CheckSign(res);
            Residence.msg((CommandSender)player, lm.Residence_Unrent, res.getName());
        } else {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
        }
    }

    private static long daysToMs(int days) {
        return (long)days * 24 * 60 * 60 * 1000;
    }

    private static int msToDays(long ms) {
        return (int)Math.ceil((double)ms / 1000.0 / 60.0 / 60.0 / 24.0);
    }

    @Override
    public void removeFromForRent(Player player, String landName, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.removeFromForRent(player, res, resadmin2);
    }

    public void removeFromForRent(Player player, ClaimedResidence res, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        if (!res.getPermissions().hasResidencePermission((CommandSender)player, true) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
            return;
        }
        if (this.rentableLand.contains(res)) {
            ResidenceRentEvent revent = new ResidenceRentEvent(res, player, ResidenceRentEvent.RentEventType.UNRENT);
            Residence.getServ().getPluginManager().callEvent((Event)revent);
            if (revent.isCancelled()) {
                return;
            }
            this.rentableLand.remove(res);
            res.setRentable(null);
            res.getPermissions().applyDefaultFlags();
            Residence.getSignUtil().CheckSign(res);
            Residence.msg((CommandSender)player, lm.Residence_RemoveRentable, res.getResidenceName());
        } else {
            Residence.msg((CommandSender)player, lm.Residence_NotForRent, new Object[0]);
        }
    }

    @Override
    public void removeFromRent(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.removeFromRent(res);
    }

    public void removeFromRent(ClaimedResidence res) {
        this.rentedLand.remove(res);
    }

    @Override
    public void removeRentable(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.removeRentable(res);
    }

    public void removeRentable(ClaimedResidence res) {
        if (res == null) {
            return;
        }
        this.removeFromRent(res);
        this.rentableLand.remove(res);
        Residence.getSignUtil().removeSign(res.getName());
    }

    @Override
    public boolean isForRent(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.isForRent(res);
    }

    public boolean isForRent(ClaimedResidence res) {
        if (res == null) {
            return false;
        }
        return this.rentableLand.contains(res);
    }

    public RentableLand getRentableLand(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.getRentableLand(res);
    }

    public RentableLand getRentableLand(ClaimedResidence res) {
        if (res == null) {
            return null;
        }
        if (res.isForRent()) {
            return res.getRentable();
        }
        return null;
    }

    @Override
    public boolean isRented(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.isRented(res);
    }

    public boolean isRented(ClaimedResidence res) {
        if (res == null) {
            return false;
        }
        return this.rentedLand.contains(res);
    }

    @Override
    public String getRentingPlayer(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.getRentingPlayer(res);
    }

    public String getRentingPlayer(ClaimedResidence res) {
        if (res == null) {
            return null;
        }
        return res.isRented() ? res.getRentedLand().player : null;
    }

    @Override
    public int getCostOfRent(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.getCostOfRent(res);
    }

    public int getCostOfRent(ClaimedResidence res) {
        if (res == null) {
            return 0;
        }
        return res.isForRent() ? res.getRentable().cost : 0;
    }

    @Override
    public boolean getRentableRepeatable(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.getRentableRepeatable(res);
    }

    public boolean getRentableRepeatable(ClaimedResidence res) {
        if (res == null) {
            return false;
        }
        return res.isForRent() ? res.getRentable().AllowRenewing : false;
    }

    @Override
    public boolean getRentedAutoRepeats(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.getRentedAutoRepeats(res);
    }

    public boolean getRentedAutoRepeats(ClaimedResidence res) {
        if (res == null) {
            return false;
        }
        return this.getRentableRepeatable(res) ? (this.rentedLand.contains(res) ? res.getRentedLand().AutoPay : false) : false;
    }

    @Override
    public int getRentDays(String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        return this.getRentDays(res);
    }

    public int getRentDays(ClaimedResidence res) {
        if (res == null) {
            return 0;
        }
        return res.isForRent() ? res.getRentable().days : 0;
    }

    @Override
    public void checkCurrentRents() {
        HashSet<ClaimedResidence> t = new HashSet<ClaimedResidence>();
        t.addAll(this.rentedLand);
        for (ClaimedResidence res : t) {
            RentedLand land;
            if (res == null || (land = res.getRentedLand()) == null || land.endTime > System.currentTimeMillis()) continue;
            if (Residence.getConfigManager().debugEnabled()) {
                System.out.println("Rent Check: " + res.getName());
            }
            ResidenceRentEvent revent = new ResidenceRentEvent(res, null, ResidenceRentEvent.RentEventType.RENT_EXPIRE);
            Residence.getServ().getPluginManager().callEvent((Event)revent);
            if (revent.isCancelled()) continue;
            RentableLand rentable = res.getRentable();
            if (!rentable.AllowRenewing) {
                if (!rentable.StayInMarket) {
                    this.rentableLand.remove(res);
                    res.setRentable(null);
                }
                this.rentedLand.remove(res);
                res.setRented(null);
                res.getPermissions().applyDefaultFlags();
                Residence.getSignUtil().CheckSign(res);
                continue;
            }
            if (land.AutoPay && rentable.AllowAutoPay) {
                if (!Residence.getEconomyManager().canAfford(land.player, rentable.cost)) {
                    if (!rentable.StayInMarket) {
                        this.rentableLand.remove(res);
                        res.setRentable(null);
                    }
                    this.rentedLand.remove(res);
                    res.setRented(null);
                    res.getPermissions().applyDefaultFlags();
                } else if (!Residence.getEconomyManager().transfer(land.player, res.getPermissions().getOwner(), rentable.cost)) {
                    if (!rentable.StayInMarket) {
                        this.rentableLand.remove(res);
                        res.setRentable(null);
                    }
                    this.rentedLand.remove(res);
                    res.setRented(null);
                    res.getPermissions().applyDefaultFlags();
                } else {
                    land.endTime = System.currentTimeMillis() + RentManager.daysToMs(rentable.days);
                }
                Residence.getSignUtil().CheckSign(res);
                continue;
            }
            if (!rentable.StayInMarket) {
                this.rentableLand.remove(res);
                res.setRentable(null);
            }
            this.rentedLand.remove(res);
            res.setRented(null);
            boolean backup = res.getPermissions().has("backup", false);
            res.getPermissions().applyDefaultFlags();
            if (Residence.getSchematicManager() != null && Residence.getConfigManager().RestoreAfterRentEnds && backup) {
                Residence.getSchematicManager().load(res);
                Residence.getSignUtil().CheckSign(res);
                res.getPermissions().setFlag("backup", FlagPermissions.FlagState.TRUE);
                break;
            }
            Residence.getSignUtil().CheckSign(res);
        }
    }

    @Override
    public void setRentRepeatable(Player player, String landName, boolean value, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.setRentRepeatable(player, res, value, resadmin2);
    }

    public void setRentRepeatable(Player player, ClaimedResidence res, boolean value, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        RentableLand land = res.getRentable();
        if (!res.isOwner(player) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Residence_NotOwner, new Object[0]);
            return;
        }
        if (land == null || !res.isOwner(player) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Residence_NotOwner, new Object[0]);
            return;
        }
        land.AllowRenewing = value;
        if (!value && this.isRented(res)) {
            res.getRentedLand().AutoPay = false;
        }
        if (value) {
            Residence.msg((CommandSender)player, lm.Rentable_EnableRenew, res.getResidenceName());
        } else {
            Residence.msg((CommandSender)player, lm.Rentable_DisableRenew, res.getResidenceName());
        }
    }

    @Override
    public void setRentedRepeatable(Player player, String landName, boolean value, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.setRentedRepeatable(player, res, value, resadmin2);
    }

    public void setRentedRepeatable(Player player, ClaimedResidence res, boolean value, boolean resadmin2) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        RentedLand land = res.getRentedLand();
        if (land == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        if (!res.getRentable().AllowAutoPay && value) {
            Residence.msg((CommandSender)player, lm.Residence_CantAutoPay, new Object[0]);
            return;
        }
        if (!land.player.equals(player.getName()) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Residence_NotOwner, new Object[0]);
            return;
        }
        if (!land.player.equals(player.getName()) && !resadmin2) {
            Residence.msg((CommandSender)player, lm.Residence_NotOwner, new Object[0]);
            return;
        }
        land.AutoPay = value;
        if (value) {
            Residence.msg((CommandSender)player, lm.Rent_EnableRenew, res.getResidenceName());
        } else {
            Residence.msg((CommandSender)player, lm.Rent_DisableRenew, res.getResidenceName());
        }
        Residence.getSignUtil().CheckSign(res);
    }

    public void printRentInfo(Player player, String landName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(landName);
        this.printRentInfo(player, res);
    }

    public void printRentInfo(Player player, ClaimedResidence res) {
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        RentableLand rentable = res.getRentable();
        RentedLand rented = res.getRentedLand();
        if (rentable != null) {
            Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
            Residence.msg((CommandSender)player, lm.General_Land, res.getName());
            Residence.msg((CommandSender)player, lm.General_Cost, rentable.cost, rentable.days);
            Residence.msg((CommandSender)player, lm.Rentable_AllowRenewing, rentable.AllowRenewing);
            Residence.msg((CommandSender)player, lm.Rentable_StayInMarket, rentable.StayInMarket);
            Residence.msg((CommandSender)player, lm.Rentable_AllowAutoPay, rentable.AllowAutoPay);
            if (rented != null) {
                Residence.msg((CommandSender)player, lm.Residence_RentedBy, rented.player);
                if (rented.player.equals(player.getName()) || res.isOwner(player) || Residence.isResAdminOn(player)) {
                    player.sendMessage(String.valueOf(rented.AutoPay ? Residence.msg(lm.Rent_AutoPayTurnedOn, new Object[0]) : Residence.msg(lm.Rent_AutoPayTurnedOff, new Object[0])) + "\n");
                }
                Residence.msg((CommandSender)player, lm.Rent_Expire, GetTime.getTime(rented.endTime));
            } else {
                Residence.msg((CommandSender)player, lm.General_Status, Residence.msg(lm.General_Available, new Object[0]));
            }
            Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
        } else {
            Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
            Residence.msg((CommandSender)player, lm.Residence_NotForRent, new Object[0]);
            Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
        }
    }

    public void printRentableResidences(Player player, int page) {
        Residence.msg((CommandSender)player, lm.Rentable_Land, new Object[0]);
        StringBuilder sbuild = new StringBuilder();
        sbuild.append((Object)ChatColor.GREEN);
        int perpage = 10;
        int pagecount = (int)Math.ceil((double)this.rentableLand.size() / (double)perpage);
        if (page < 1) {
            page = 1;
        }
        int z = 0;
        for (ClaimedResidence res : this.rentableLand) {
            if (res == null || ++z <= (page - 1) * perpage) continue;
            if (z > (page - 1) * perpage + perpage) break;
            boolean rented = res.isRented();
            if (!res.getRentable().AllowRenewing && rented) continue;
            String rentedBy = "";
            String hover = "";
            if (rented) {
                RentedLand rent = res.getRentedLand();
                rentedBy = Residence.msg(lm.Residence_RentedBy, rent.player);
                hover = GetTime.getTime(rent.endTime);
            }
            String msg = Residence.msg(lm.Rent_RentList, z, res.getName(), res.getRentable().cost, res.getRentable().days, res.getRentable().AllowRenewing, res.getOwner(), rentedBy);
            if (!hover.equalsIgnoreCase("")) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + player.getName() + " {\"text\":\"\",\"extra\":[{\"text\":\"" + msg + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"\u00a72" + hover + "\"}}]}"));
                continue;
            }
            player.sendMessage(msg);
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
        String prevCmd = "/res market list rent " + Prevpage;
        String prev = "[\"\",{\"text\":\"" + separator + " " + Residence.msg(lm.General_PrevInfoPage, new Object[0]) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + prevCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + "<<<" + "\"}]}}}";
        String nextCmd = "/res market list rent " + NextPage;
        String next = " {\"text\":\"" + Residence.msg(lm.General_NextInfoPage, new Object[0]) + " " + separator + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + nextCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ">>>" + "\"}]}}}]";
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + player.getName() + " " + prev + "," + next));
    }

    @Override
    public int getRentCount(String player) {
        int count = 0;
        for (ClaimedResidence res : this.rentedLand) {
            if (!res.getRentedLand().player.equalsIgnoreCase(player)) continue;
            ++count;
        }
        return count;
    }

    @Override
    public int getRentableCount(String player) {
        int count = 0;
        for (ClaimedResidence res : this.rentableLand) {
            if (res == null || !res.isOwner(player)) continue;
            ++count;
        }
        return count;
    }

    @Override
    public Set<ClaimedResidence> getRentableResidences() {
        return this.rentableLand;
    }

    @Override
    public Set<ClaimedResidence> getCurrentlyRentedResidences() {
        return this.rentedLand;
    }

    public void load(Map<String, Object> root) {
        if (root == null) {
            return;
        }
        this.rentableLand.clear();
        Map rentables = (Map)root.get("Rentables");
        for (Map.Entry rent : rentables.entrySet()) {
            RentableLand one = RentManager.loadRentable((Map)rent.getValue());
            ClaimedResidence res = Residence.getResidenceManager().getByName((String)rent.getKey());
            if (res == null) continue;
            res.setRentable(one);
            this.rentableLand.add(res);
        }
        Map rented = (Map)root.get("Rented");
        for (Map.Entry rent : rented.entrySet()) {
            RentedLand one = RentManager.loadRented((Map)rent.getValue());
            ClaimedResidence res = Residence.getResidenceManager().getByName((String)rent.getKey());
            if (res == null) continue;
            res.setRented(one);
            this.rentedLand.add(res);
        }
    }

    public Map<String, Object> save() {
        HashMap<String, Object> root = new HashMap<String, Object>();
        HashMap<String, Map<String, Object>> rentables = new HashMap<String, Map<String, Object>>();
        for (ClaimedResidence res : this.rentableLand) {
            if (res == null || res.getRentable() == null) continue;
            rentables.put(res.getName(), res.getRentable().save());
        }
        HashMap<String, Map<String, Object>> rented = new HashMap<String, Map<String, Object>>();
        for (ClaimedResidence res : this.rentedLand) {
            if (res == null || res.getRentedLand() == null) continue;
            rented.put(res.getName(), res.getRentedLand().save());
        }
        root.put("Rentables", rentables);
        root.put("Rented", rented);
        return root;
    }

    private static RentableLand loadRentable(Map<String, Object> map) {
        RentableLand newland = new RentableLand();
        newland.cost = (Integer)map.get("Cost");
        newland.days = (Integer)map.get("Days");
        newland.AllowRenewing = (Boolean)map.get("Repeatable");
        if (map.containsKey("StayInMarket")) {
            newland.StayInMarket = (Boolean)map.get("StayInMarket");
        }
        if (map.containsKey("AllowAutoPay")) {
            newland.AllowAutoPay = (Boolean)map.get("AllowAutoPay");
        }
        return newland;
    }

    private static RentedLand loadRented(Map<String, Object> map) {
        RentedLand newland = new RentedLand();
        newland.player = (String)map.get("Player");
        newland.startTime = (Long)map.get("StartTime");
        newland.endTime = (Long)map.get("EndTime");
        newland.AutoPay = (Boolean)map.get("AutoRefresh");
        return newland;
    }
}

