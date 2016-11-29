/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.PluginManager
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.EconomyInterface;
import com.bekvon.bukkit.residence.economy.ResidenceBank;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.utils.GetTime;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

public class LeaseManager {
    private Map<String, Long> leaseExpireTime;
    ResidenceManager manager;

    public LeaseManager(ResidenceManager m) {
        this.manager = m;
        this.leaseExpireTime = Collections.synchronizedMap(new HashMap());
    }

    public boolean leaseExpires(String area2) {
        return this.leaseExpireTime.containsKey(area2);
    }

    public String getExpireTime(String area2) {
        if (this.leaseExpireTime.containsKey(area2)) {
            return GetTime.getTime(this.leaseExpireTime.get(area2));
        }
        return null;
    }

    public void removeExpireTime(String area2) {
        this.leaseExpireTime.remove(area2);
    }

    public void setExpireTime(String area2, int days) {
        this.setExpireTime(null, area2, days);
    }

    public void setExpireTime(Player player, String area2, int days) {
        if (this.manager.getByName(area2) != null) {
            this.leaseExpireTime.put(area2, LeaseManager.daysToMs(days) + System.currentTimeMillis());
            if (player != null) {
                Residence.msg((CommandSender)player, lm.Economy_LeaseRenew, this.getExpireTime(area2));
            }
        } else if (player != null) {
            Residence.msg((CommandSender)player, lm.Invalid_Area, new Object[0]);
        }
    }

    public void renewArea(String area2, Player player) {
        if (!this.leaseExpires(area2)) {
            Residence.msg((CommandSender)player, lm.Economy_LeaseNotExpire, new Object[0]);
            return;
        }
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        int max = group.getMaxLeaseTime();
        int add = group.getLeaseGiveTime();
        int rem = this.daysRemaining(area2);
        EconomyInterface econ = Residence.getEconomyManager();
        if (econ != null) {
            double cost = group.getLeaseRenewCost();
            ClaimedResidence res = this.manager.getByName(area2);
            area2 = res.getName();
            int amount = (int)Math.ceil((double)res.getTotalSize() * cost);
            if (cost != 0.0) {
                if (econ.canAfford(player.getName(), amount)) {
                    econ.subtract(player.getName(), amount);
                    econ.add("Lease Money", amount);
                    Residence.msg((CommandSender)player, lm.Economy_MoneyCharged, String.format("%d", amount), econ.getName());
                } else {
                    Residence.msg((CommandSender)player, lm.Economy_NotEnoughMoney, new Object[0]);
                    return;
                }
            }
        }
        if (rem + add > max) {
            this.setExpireTime(player, area2, max);
            Residence.msg((CommandSender)player, lm.Economy_LeaseRenewMax, new Object[0]);
            Residence.msg((CommandSender)player, lm.Economy_LeaseRenew, this.getExpireTime(area2));
            return;
        }
        Long get = this.leaseExpireTime.get(area2);
        if (get != null) {
            get = get + LeaseManager.daysToMs(add);
            this.leaseExpireTime.put(area2, get);
        } else {
            this.leaseExpireTime.put(area2, LeaseManager.daysToMs(add));
        }
        Residence.msg((CommandSender)player, lm.Economy_LeaseRenew, this.getExpireTime(area2));
    }

    public int getRenewCost(ClaimedResidence res) {
        double cost = res.getOwnerGroup().getLeaseRenewCost();
        int amount = (int)Math.ceil((double)res.getTotalSize() * cost);
        return amount;
    }

    private static long daysToMs(int days) {
        return (long)days * 24 * 60 * 60 * 1000;
    }

    private static int msToDays(long ms) {
        return (int)Math.ceil((double)ms / 1000.0 / 60.0 / 60.0 / 24.0);
    }

    private int daysRemaining(String area2) {
        Long get = this.leaseExpireTime.get(area2);
        if (get <= System.currentTimeMillis()) {
            return 0;
        }
        return LeaseManager.msToDays((int)(get - System.currentTimeMillis()));
    }

    public void doExpirations() {
        Set<Map.Entry<String, Long>> set2 = this.leaseExpireTime.entrySet();
        Iterator<Map.Entry<String, Long>> it = set2.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> next = it.next();
            if (next.getValue() > System.currentTimeMillis()) continue;
            String resname = next.getKey();
            ClaimedResidence res = Residence.getResidenceManager().getByName(resname);
            if (res == null) {
                it.remove();
                continue;
            }
            resname = res.getName();
            boolean renewed = false;
            String owner = res.getPermissions().getOwner();
            PermissionGroup group = res.getOwnerGroup();
            int cost = this.getRenewCost(res);
            if (Residence.getConfigManager().enableEconomy() && Residence.getConfigManager().autoRenewLeases()) {
                if (cost == 0) {
                    renewed = true;
                } else if (res.getBank().hasEnough(cost)) {
                    res.getBank().subtract(cost);
                    renewed = true;
                    if (Residence.getConfigManager().debugEnabled()) {
                        System.out.println("Lease Renewed From Residence Bank: " + resname);
                    }
                } else if (Residence.getEconomyManager().canAfford(owner, cost) && Residence.getEconomyManager().subtract(owner, cost)) {
                    renewed = true;
                    if (Residence.getConfigManager().debugEnabled()) {
                        System.out.println("Lease Renewed From Economy: " + resname);
                    }
                }
            }
            if (!renewed) {
                if (Residence.getConfigManager().enabledRentSystem() && Residence.getRentManager().isRented(resname)) continue;
                ResidenceDeleteEvent resevent = new ResidenceDeleteEvent(null, res, ResidenceDeleteEvent.DeleteCause.LEASE_EXPIRE);
                Residence.getServ().getPluginManager().callEvent((Event)resevent);
                if (resevent.isCancelled()) continue;
                this.manager.removeResidence(next.getKey());
                it.remove();
                if (!Residence.getConfigManager().debugEnabled()) continue;
                System.out.println("Lease NOT removed, Removing: " + resname);
                continue;
            }
            if (Residence.getConfigManager().enableEconomy() && Residence.getConfigManager().enableLeaseMoneyAccount()) {
                Residence.getEconomyManager().add("Lease Money", cost);
            }
            if (Residence.getConfigManager().debugEnabled()) {
                System.out.println("Lease Renew Old: " + next.getValue());
            }
            next.setValue(System.currentTimeMillis() + LeaseManager.daysToMs(group.getLeaseGiveTime()));
            if (!Residence.getConfigManager().debugEnabled()) continue;
            System.out.println("Lease Renew New: " + next.getValue());
        }
    }

    public void resetLeases() {
        String[] list2;
        this.leaseExpireTime.clear();
        String[] arrstring = list2 = this.manager.getResidenceList();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            ClaimedResidence res;
            String item = arrstring[n2];
            if (item != null && (res = Residence.getResidenceManager().getByName(item)) != null) {
                this.setExpireTime(null, item, res.getOwnerGroup().getLeaseGiveTime());
            }
            ++n2;
        }
        System.out.println("[Residence] - Set default leases.");
    }

    public Map<String, Long> save() {
        return this.leaseExpireTime;
    }

    public void updateLeaseName(String oldName, String newName) {
        if (this.leaseExpireTime.containsKey(oldName)) {
            this.leaseExpireTime.put(newName, this.leaseExpireTime.get(oldName));
            this.leaseExpireTime.remove(oldName);
        }
    }

    public static LeaseManager load(Map root, ResidenceManager m) {
        LeaseManager l = new LeaseManager(m);
        if (root != null) {
            for (Object val : root.values()) {
                if (val instanceof Long) continue;
                root.remove(val);
            }
            l.leaseExpireTime = Collections.synchronizedMap(root);
        }
        return l;
    }
}

