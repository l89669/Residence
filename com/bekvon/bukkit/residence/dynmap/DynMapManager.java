/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.dynmap.DynmapAPI
 *  org.dynmap.markers.AreaMarker
 *  org.dynmap.markers.MarkerAPI
 *  org.dynmap.markers.MarkerSet
 */
package com.bekvon.bukkit.residence.dynmap;

import com.bekvon.bukkit.residence.ConfigManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.dynmap.AreaStyle;
import com.bekvon.bukkit.residence.economy.TransactionManager;
import com.bekvon.bukkit.residence.economy.rent.RentManager;
import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.utils.GetTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

public class DynMapManager {
    Residence plugin;
    public DynmapAPI api;
    MarkerAPI markerapi;
    MarkerSet set;
    private Map<String, AreaMarker> resareas = new HashMap<String, AreaMarker>();
    private int schedId = -1;

    public DynMapManager(Residence plugin) {
        this.plugin = plugin;
    }

    public MarkerSet getMarkerSet() {
        return this.set;
    }

    public void fireUpdateAdd(final ClaimedResidence res, final int deep) {
        if (this.api == null || this.set == null) {
            return;
        }
        if (res == null) {
            return;
        }
        if (this.schedId != -1) {
            Bukkit.getServer().getScheduler().cancelTask(this.schedId);
        }
        this.schedId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                DynMapManager.access$0(DynMapManager.this, -1);
                DynMapManager.this.handleResidenceAdd(res.getName(), res, deep);
            }
        }, 10);
    }

    public void fireUpdateRemove(ClaimedResidence res, int deep) {
        if (this.api == null || this.set == null) {
            return;
        }
        if (res == null) {
            return;
        }
        this.handleResidenceRemove(res.getName(), res, deep);
    }

    private static String formatInfoWindow(String resid, ClaimedResidence res, String resName) {
        if (res == null) {
            return null;
        }
        if (res.getName() == null) {
            return null;
        }
        if (res.getOwner() == null) {
            return null;
        }
        String v = "<div class=\"regioninfo\"><div class=\"infowindow\"><span style=\"font-size:140%;font-weight:bold;\">%regionname%</span><br /> " + ChatColor.stripColor((String)Residence.msg(lm.General_Owner, "")) + "<span style=\"font-weight:bold;\">%playerowners%</span><br />";
        if (Residence.getConfigManager().DynMapShowFlags) {
            v = String.valueOf(v) + ChatColor.stripColor((String)Residence.msg(lm.General_ResidenceFlags, "")) + "<br /><span style=\"font-weight:bold;\">%flags%</span>";
        }
        v = String.valueOf(v) + "</div></div>";
        if (Residence.getRentManager().isForRent(res.getName())) {
            v = "<div class=\"regioninfo\"><div class=\"infowindow\">" + ChatColor.stripColor((String)Residence.msg(lm.Rentable_Land, "")) + "<span style=\"font-size:140%;font-weight:bold;\">%regionname%</span><br />" + ChatColor.stripColor((String)Residence.msg(lm.General_Owner, "")) + "<span style=\"font-weight:bold;\">%playerowners%</span><br />" + ChatColor.stripColor((String)Residence.msg(lm.Residence_RentedBy, "")) + "<span style=\"font-weight:bold;\">%renter%</span><br /> " + ChatColor.stripColor((String)Residence.msg(lm.General_LandCost, "")) + "<span style=\"font-weight:bold;\">%rent%</span><br /> " + ChatColor.stripColor((String)Residence.msg(lm.Rent_Days, "")) + "<span style=\"font-weight:bold;\">%rentdays%</span><br /> " + ChatColor.stripColor((String)Residence.msg(lm.Rentable_AllowRenewing, "")) + "<span style=\"font-weight:bold;\">%renew%</span><br /> " + ChatColor.stripColor((String)Residence.msg(lm.Rent_Expire, "")) + "<span style=\"font-weight:bold;\">%expire%</span></div></div>";
        }
        if (Residence.getTransactionManager().isForSale(res.getName())) {
            v = "<div class=\"regioninfo\"><div class=\"infowindow\">" + ChatColor.stripColor((String)Residence.msg(lm.Economy_LandForSale, " ")) + "<span style=\"font-size:140%;font-weight:bold;\">%regionname%</span><br /> " + ChatColor.stripColor((String)Residence.msg(lm.General_Owner, "")) + "<span style=\"font-weight:bold;\">%playerowners%</span><br />" + ChatColor.stripColor((String)Residence.msg(lm.Economy_SellAmount, "")) + "<span style=\"font-weight:bold;\">%price%</span><br /></div></div>";
        }
        v = v.replace("%regionname%", resName);
        v = v.replace("%playerowners%", res.getOwner());
        String m = res.getEnterMessage();
        v = v.replace("%entermsg%", m != null ? m : "");
        m = res.getLeaveMessage();
        v = v.replace("%leavemsg%", m != null ? m : "");
        ResidencePermissions p = res.getPermissions();
        String flgs = "";
        Map<String, Boolean> all = Residence.getPermissionManager().getAllFlags().getFlags();
        String[] FLAGS = new String[all.size()];
        int ii = 0;
        for (Map.Entry<String, Boolean> one : all.entrySet()) {
            FLAGS[ii] = one.getKey();
            ++ii;
        }
        int i = 0;
        while (i < FLAGS.length) {
            if (p.isSet(FLAGS[i])) {
                if (flgs.length() > 0) {
                    flgs = String.valueOf(flgs) + "<br/>";
                }
                boolean f = p.has(FLAGS[i], false);
                flgs = String.valueOf(flgs) + FLAGS[i] + ": " + f;
                v = v.replace("%flag." + FLAGS[i] + "%", Boolean.toString(f));
            } else {
                v = v.replace("%flag." + FLAGS[i] + "%", "");
            }
            ++i;
        }
        v = v.replace("%flags%", flgs);
        RentManager rentmgr = Residence.getRentManager();
        TransactionManager transmgr = Residence.getTransactionManager();
        if (rentmgr.isForRent(res.getName())) {
            long time;
            boolean isrented = rentmgr.isRented(resid);
            v = v.replace("%isrented%", Boolean.toString(isrented));
            String id = "";
            if (isrented) {
                id = rentmgr.getRentingPlayer(resid);
            }
            v = v.replace("%renter%", id);
            v = v.replace("%rent%", String.valueOf(rentmgr.getCostOfRent(resid)));
            v = v.replace("%rentdays%", String.valueOf(rentmgr.getRentDays(resid)));
            boolean renew = rentmgr.getRentableRepeatable(resid);
            v = v.replace("%renew%", String.valueOf(renew));
            String expire = "";
            if (isrented && (time = rentmgr.getRentedLand((String)resid).endTime) != 0) {
                expire = GetTime.getTime(time);
            }
            v = v.replace("%expire%", expire);
        }
        if (transmgr.isForSale(res.getName())) {
            boolean forsale = transmgr.isForSale(resid);
            v = v.replace("%isforsale%", Boolean.toString(transmgr.isForSale(resid)));
            String price = "";
            if (forsale) {
                price = Integer.toString(transmgr.getSaleAmount(resid));
            }
            v = v.replace("%price%", price);
        }
        return v;
    }

    private static boolean isVisible(String id, String worldname) {
        List<String> visible = Residence.getConfigManager().DynMapVisibleRegions;
        List<String> hidden = Residence.getConfigManager().DynMapHiddenRegions;
        if (visible != null && visible.size() > 0 && !visible.contains(id) && !visible.contains("world:" + worldname)) {
            return false;
        }
        if (hidden != null && hidden.size() > 0 && (hidden.contains(id) || hidden.contains("world:" + worldname))) {
            return false;
        }
        return true;
    }

    private static void addStyle(String resid, AreaMarker m) {
        AreaStyle as = new AreaStyle();
        int sc = 16711680;
        int fc = 16711680;
        try {
            sc = Integer.parseInt(as.strokecolor.substring(1), 16);
            fc = Residence.getRentManager().isForRent(resid) && !Residence.getRentManager().isRented(resid) ? Integer.parseInt(as.forrentstrokecolor.substring(1), 16) : (Residence.getRentManager().isForRent(resid) && Residence.getRentManager().isRented(resid) ? Integer.parseInt(as.rentedstrokecolor.substring(1), 16) : (Residence.getTransactionManager().isForSale(resid) ? Integer.parseInt(as.forsalestrokecolor.substring(1), 16) : Integer.parseInt(as.fillcolor.substring(1), 16)));
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        m.setLineStyle(as.strokeweight, as.strokeopacity, sc);
        m.setFillStyle(as.fillopacity, fc);
        m.setRangeY((double)as.y, (double)as.y);
    }

    private void handleResidenceAdd(String resid, ClaimedResidence res, int depth) {
        if (res == null) {
            return;
        }
        boolean hidden = res.getPermissions().has("hidden", false);
        if (hidden && Residence.getConfigManager().DynMapHideHidden) {
            this.fireUpdateRemove(res, depth);
            return;
        }
        for (Map.Entry<String, CuboidArea> oneArea : res.getAreaMap().entrySet()) {
            String id = String.valueOf(oneArea.getKey()) + "." + resid;
            String name = res.getName();
            double[] x = new double[2];
            double[] z = new double[2];
            String resName = res.getName();
            if (res.getAreaMap().size() > 1) {
                resName = String.valueOf(res.getName()) + " (" + oneArea.getKey() + ")";
            }
            String desc = DynMapManager.formatInfoWindow(resid, res, resName);
            if (!DynMapManager.isVisible(resid, res.getWorld())) {
                return;
            }
            Location l0 = oneArea.getValue().getLowLoc();
            Location l1 = oneArea.getValue().getHighLoc();
            x[0] = l0.getX();
            z[0] = l0.getZ();
            x[1] = l1.getX() + 1.0;
            z[1] = l1.getZ() + 1.0;
            AreaMarker marker = null;
            if (this.resareas.containsKey(id)) {
                marker = this.resareas.get(id);
                this.resareas.remove(id);
                marker.deleteMarker();
            }
            if ((marker = this.set.createAreaMarker(id, name, true, res.getWorld(), x, z, false)) == null) {
                return;
            }
            if (Residence.getConfigManager().DynMapLayer3dRegions) {
                marker.setRangeY(Math.min(l0.getY(), l1.getY()), Math.max(l0.getY(), l1.getY()));
            }
            marker.setDescription(desc);
            DynMapManager.addStyle(resid, marker);
            this.resareas.put(id, marker);
            if (depth > Residence.getConfigManager().DynMapLayerSubZoneDepth) continue;
            List<ClaimedResidence> subids = res.getSubzones();
            for (ClaimedResidence one : subids) {
                this.handleResidenceAdd(one.getName(), one, depth + 1);
            }
        }
    }

    public void handleResidenceRemove(String resid, ClaimedResidence res, int depth) {
        if (resid == null) {
            return;
        }
        if (res == null) {
            return;
        }
        for (Map.Entry<String, CuboidArea> oneArea : res.getAreaMap().entrySet()) {
            String id = String.valueOf(oneArea.getKey()) + "." + resid;
            if (this.resareas.containsKey(id)) {
                AreaMarker marker = this.resareas.remove(id);
                marker.deleteMarker();
            }
            if (depth > Residence.getConfigManager().DynMapLayerSubZoneDepth + 1) continue;
            List<ClaimedResidence> subids = res.getSubzones();
            for (ClaimedResidence one : subids) {
                this.handleResidenceRemove(one.getName(), one, depth + 1);
            }
        }
    }

    public void activate() {
        this.markerapi = this.api.getMarkerAPI();
        if (this.markerapi == null) {
            Bukkit.getConsoleSender().sendMessage("[Residence] Error loading dynmap marker API!");
            return;
        }
        if (this.set != null) {
            this.set.deleteMarkerSet();
            this.set = null;
        }
        this.set = this.markerapi.getMarkerSet("residence.markerset");
        if (this.set == null) {
            this.set = this.markerapi.createMarkerSet("residence.markerset", "Residence", null, false);
        } else {
            this.set.setMarkerSetLabel("Residence");
        }
        if (this.set == null) {
            Bukkit.getConsoleSender().sendMessage("Error creating marker set");
            return;
        }
        this.set.setLayerPriority(1);
        this.set.setHideByDefault(false);
        Bukkit.getConsoleSender().sendMessage("[Residence] DynMap residence activated!");
        for (Map.Entry<String, ClaimedResidence> one : Residence.getResidenceManager().getResidences().entrySet()) {
            Residence.getDynManager().fireUpdateAdd(one.getValue(), one.getValue().getSubzoneDeep());
            this.handleResidenceAdd(one.getValue().getName(), one.getValue(), one.getValue().getSubzoneDeep());
        }
    }

    static /* synthetic */ void access$0(DynMapManager dynMapManager, int n) {
        dynMapManager.schedId = n;
    }

}

