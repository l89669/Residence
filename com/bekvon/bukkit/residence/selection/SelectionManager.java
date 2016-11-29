/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Player$Spigot
 *  org.bukkit.permissions.Permission
 *  org.bukkit.permissions.PermissionDefault
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package com.bekvon.bukkit.residence.selection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.SelectionSides;
import com.bekvon.bukkit.residence.containers.Visualizer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.utils.ParticleEffects;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class SelectionManager {
    protected Map<String, Location> playerLoc1;
    protected Map<String, Location> playerLoc2;
    protected Server server;
    private Residence plugin;
    private HashMap<String, Visualizer> vMap = new HashMap();
    public static final int MIN_HEIGHT = 0;
    Permission p = new Permission("residence.bypass.ignorey", PermissionDefault.FALSE);
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$selection$SelectionManager$Direction;

    public SelectionManager(Server server2, Residence plugin) {
        this.plugin = plugin;
        this.server = server2;
        this.playerLoc1 = Collections.synchronizedMap(new HashMap());
        this.playerLoc2 = Collections.synchronizedMap(new HashMap());
    }

    public void updateLocations(Player player, Location loc1, Location loc2) {
        if (loc1 != null && loc2 != null) {
            this.playerLoc1.put(player.getName(), loc1);
            this.playerLoc2.put(player.getName(), loc2);
            this.updateForY(player);
            this.afterSelectionUpdate(player);
        }
    }

    public void placeLoc1(Player player, Location loc) {
        this.placeLoc1(player, loc, false);
    }

    public void placeLoc1(Player player, Location loc, boolean show2) {
        if (loc != null) {
            this.playerLoc1.put(player.getName(), loc);
            this.updateForY(player);
            if (show2) {
                this.afterSelectionUpdate(player);
            }
        }
    }

    public void placeLoc2(Player player, Location loc) {
        this.placeLoc2(player, loc, false);
    }

    public void placeLoc2(Player player, Location loc, boolean show2) {
        if (loc != null) {
            this.playerLoc2.put(player.getName(), loc);
            this.updateForY(player);
            if (show2) {
                this.afterSelectionUpdate(player);
            }
        }
    }

    private void updateForY(Player player) {
        if (Residence.getConfigManager().isSelectionIgnoreY() && this.hasPlacedBoth(player.getName()) && !player.hasPermission(this.p)) {
            this.qsky(player);
            this.qbedrock(player);
        }
    }

    public void afterSelectionUpdate(Player player) {
        if (this.hasPlacedBoth(player.getName())) {
            Visualizer v = new Visualizer(player);
            v.setAreas(this.getSelectionCuboid(player));
            this.showBounds(player, v);
        }
    }

    public Location getPlayerLoc1(Player player) {
        return this.getPlayerLoc1(player.getName());
    }

    public Location getPlayerLoc1(String player) {
        return this.playerLoc1.get(player);
    }

    public Location getPlayerLoc2(Player player) {
        return this.getPlayerLoc2(player.getName());
    }

    public Location getPlayerLoc2(String player) {
        return this.playerLoc2.get(player);
    }

    public CuboidArea getSelectionCuboid(Player player) {
        return this.getSelectionCuboid(player.getName());
    }

    public CuboidArea getSelectionCuboid(String player) {
        return new CuboidArea(this.getPlayerLoc1(player), this.getPlayerLoc2(player));
    }

    public boolean hasPlacedBoth(String player) {
        if (this.playerLoc1.containsKey(player) && this.playerLoc2.containsKey(player)) {
            return true;
        }
        return false;
    }

    public void showSelectionInfoInActionBar(Player player) {
        if (!Residence.getConfigManager().useActionBarOnSelection()) {
            return;
        }
        String pname = player.getName();
        CuboidArea cuboidArea = new CuboidArea(this.getPlayerLoc1(pname), this.getPlayerLoc2(pname));
        String Message = Residence.msg(lm.Select_TotalSize, cuboidArea.getSize());
        ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (Residence.getConfigManager().enableEconomy()) {
            Message = String.valueOf(Message) + " " + Residence.msg(lm.General_LandCost, (int)Math.ceil((double)cuboidArea.getSize() * group.getCostPerBlock()));
        }
        Residence.getAB().send(player, Message);
    }

    public void showSelectionInfo(Player player) {
        String pname = player.getName();
        if (this.hasPlacedBoth(pname)) {
            Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
            CuboidArea cuboidArea = new CuboidArea(this.getPlayerLoc1(pname), this.getPlayerLoc2(pname));
            Residence.msg((CommandSender)player, lm.Select_TotalSize, cuboidArea.getSize());
            ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
            PermissionGroup group = rPlayer.getGroup();
            if (Residence.getConfigManager().enableEconomy()) {
                Residence.msg((CommandSender)player, lm.General_LandCost, (int)Math.ceil((double)cuboidArea.getSize() * group.getCostPerBlock()));
            }
            player.sendMessage((Object)ChatColor.YELLOW + "X" + Residence.msg(lm.General_Size, cuboidArea.getXSize()));
            player.sendMessage((Object)ChatColor.YELLOW + "Y" + Residence.msg(lm.General_Size, cuboidArea.getYSize()));
            player.sendMessage((Object)ChatColor.YELLOW + "Z" + Residence.msg(lm.General_Size, cuboidArea.getZSize()));
            Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
            Visualizer v = new Visualizer(player);
            v.setAreas(this.getSelectionCuboid(player));
            this.showBounds(player, v);
        } else {
            Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
        }
    }

    public void showBounds(final Player player, final Visualizer v) {
        if (!Residence.getConfigManager().useVisualizer()) {
            return;
        }
        this.vMap.put(player.getName(), v);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (!v.getAreas().isEmpty()) {
                    SelectionManager.this.MakeBorders(player, false);
                }
                if (!v.getErrorAreas().isEmpty()) {
                    SelectionManager.this.MakeBorders(player, true);
                }
            }
        });
    }

    public List<Location> getLocations(Location lowLoc, Location loc, Double TX, Double TY, Double TZ, Double Range, boolean StartFromZero) {
        double eachCollumn = Residence.getConfigManager().getVisualizerRowSpacing();
        double eachRow = Residence.getConfigManager().getVisualizerCollumnSpacing();
        if (TX == 0.0) {
            TX = eachCollumn + eachCollumn * 0.1;
        }
        if (TY == 0.0) {
            TY = eachRow + eachRow * 0.1;
        }
        if (TZ == 0.0) {
            TZ = eachCollumn + eachCollumn * 0.1;
        }
        double CollumnStart = eachCollumn;
        double RowStart = eachRow;
        if (StartFromZero) {
            CollumnStart = 0.0;
            RowStart = 0.0;
        }
        ArrayList<Location> locList = new ArrayList<Location>();
        if (lowLoc.getWorld() != loc.getWorld()) {
            return locList;
        }
        double x = CollumnStart;
        while (x < TX) {
            Location CurrentX = lowLoc.clone();
            if (TX > eachCollumn + eachCollumn * 0.1) {
                CurrentX.add(x, 0.0, 0.0);
            }
            double y = RowStart;
            while (y < TY) {
                Location CurrentY = CurrentX.clone();
                if (TY > eachRow + eachRow * 0.1) {
                    CurrentY.add(0.0, y, 0.0);
                }
                double z = CollumnStart;
                while (z < TZ) {
                    double dist;
                    Location CurrentZ = CurrentY.clone();
                    if (TZ > eachCollumn + eachCollumn * 0.1) {
                        CurrentZ.add(0.0, 0.0, z);
                    }
                    if ((dist = loc.distance(CurrentZ)) < Range) {
                        locList.add(CurrentZ.clone());
                    }
                    z += eachCollumn;
                }
                y += eachRow;
            }
            x += eachCollumn;
        }
        return locList;
    }

    public List<Location> GetLocationsWallsByData(Location loc, Double TX, Double TY, Double TZ, Location lowLoc, SelectionSides Sides, double Range) {
        ArrayList<Location> locList = new ArrayList<Location>();
        if (Sides.ShowNorthSide()) {
            locList.addAll(this.getLocations(lowLoc.clone(), loc.clone(), TX, TY, 0.0, Range, false));
        }
        if (Sides.ShowSouthSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, 0.0, TZ.doubleValue()), loc.clone(), TX, TY, 0.0, Range, false));
        }
        if (Sides.ShowWestSide()) {
            locList.addAll(this.getLocations(lowLoc.clone(), loc.clone(), 0.0, TY, TZ, Range, false));
        }
        if (Sides.ShowEastSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(TX.doubleValue(), 0.0, 0.0), loc.clone(), 0.0, TY, TZ, Range, false));
        }
        if (Sides.ShowTopSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, TY.doubleValue(), 0.0), loc.clone(), TX, 0.0, TZ, Range, false));
        }
        if (Sides.ShowBottomSide()) {
            locList.addAll(this.getLocations(lowLoc.clone(), loc.clone(), TX, 0.0, TZ, Range, false));
        }
        return locList;
    }

    public List<Location> GetLocationsCornersByData(Location loc, Double TX, Double TY, Double TZ, Location lowLoc, SelectionSides Sides, double Range) {
        ArrayList<Location> locList = new ArrayList<Location>();
        if (Sides.ShowBottomSide() && Sides.ShowNorthSide()) {
            locList.addAll(this.getLocations(lowLoc.clone(), loc.clone(), TX, 0.0, 0.0, Range, true));
        }
        if (Sides.ShowTopSide() && Sides.ShowNorthSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, TY.doubleValue(), 0.0), loc.clone(), TX, 0.0, 0.0, Range, true));
        }
        if (Sides.ShowBottomSide() && Sides.ShowSouthSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, 0.0, TZ.doubleValue()), loc.clone(), TX, 0.0, 0.0, Range, true));
        }
        if (Sides.ShowTopSide() && Sides.ShowSouthSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, TY.doubleValue(), TZ.doubleValue()), loc.clone(), TX, 0.0, 0.0, Range, true));
        }
        if (Sides.ShowWestSide() && Sides.ShowNorthSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, 0.0, 0.0), loc.clone(), 0.0, TY, 0.0, Range, true));
        }
        if (Sides.ShowEastSide() && Sides.ShowNorthSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(TX.doubleValue(), 0.0, 0.0), loc.clone(), 0.0, TY, 0.0, Range, true));
        }
        if (Sides.ShowSouthSide() && Sides.ShowWestSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, 0.0, TZ.doubleValue()), loc.clone(), 0.0, TY, 0.0, Range, true));
        }
        if (Sides.ShowSouthSide() && Sides.ShowEastSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(TX.doubleValue(), 0.0, TZ.doubleValue()), loc.clone(), 0.0, TY + 1.0, 0.0, Range, true));
        }
        if (Sides.ShowWestSide() && Sides.ShowBottomSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, 0.0, 0.0), loc.clone(), 0.0, 0.0, TZ, Range, true));
        }
        if (Sides.ShowEastSide() && Sides.ShowBottomSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(TX.doubleValue(), 0.0, 0.0), loc.clone(), 0.0, 0.0, TZ, Range, true));
        }
        if (Sides.ShowWestSide() && Sides.ShowTopSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(0.0, TY.doubleValue(), 0.0), loc.clone(), 0.0, 0.0, TZ, Range, true));
        }
        if (Sides.ShowEastSide() && Sides.ShowTopSide()) {
            locList.addAll(this.getLocations(lowLoc.clone().add(TX.doubleValue(), TY.doubleValue(), 0.0), loc.clone(), 0.0, 0.0, TZ, Range, true));
        }
        return locList;
    }

    public boolean MakeBorders(final Player player, final boolean error) {
        final Visualizer v = this.vMap.get(player.getName());
        if (v == null) {
            return false;
        }
        List<CuboidArea> areas = null;
        areas = !error ? v.getAreas() : v.getErrorAreas();
        Location loc = player.getLocation();
        int Range = Residence.getConfigManager().getVisualizerRange();
        final ArrayList<Location> locList = new ArrayList<Location>();
        final ArrayList<Location> locList2 = new ArrayList<Location>();
        final boolean same = v.isSameLoc();
        if (!same) {
            for (CuboidArea area2 : areas) {
                if (area2 == null) continue;
                CuboidArea cuboidArea = new CuboidArea(area2.getLowLoc(), area2.getHighLoc());
                cuboidArea.getHighLoc().add(1.0, 1.0, 1.0);
                SelectionSides Sides = new SelectionSides();
                double PLLX = loc.getBlockX() - Range;
                double PLLZ = loc.getBlockZ() - Range;
                double PLLY = loc.getBlockY() - Range;
                double PLHX = loc.getBlockX() + Range;
                double PLHZ = loc.getBlockZ() + Range;
                double PLHY = loc.getBlockY() + Range;
                if ((double)cuboidArea.getLowLoc().getBlockX() < PLLX) {
                    cuboidArea.getLowLoc().setX(PLLX);
                    Sides.setWestSide(false);
                }
                if ((double)cuboidArea.getHighLoc().getBlockX() > PLHX) {
                    cuboidArea.getHighLoc().setX(PLHX);
                    Sides.setEastSide(false);
                }
                if ((double)cuboidArea.getLowLoc().getBlockZ() < PLLZ) {
                    cuboidArea.getLowLoc().setZ(PLLZ);
                    Sides.setNorthSide(false);
                }
                if ((double)cuboidArea.getHighLoc().getBlockZ() > PLHZ) {
                    cuboidArea.getHighLoc().setZ(PLHZ);
                    Sides.setSouthSide(false);
                }
                if ((double)cuboidArea.getLowLoc().getBlockY() < PLLY) {
                    cuboidArea.getLowLoc().setY(PLLY);
                    Sides.setBottomSide(false);
                }
                if ((double)cuboidArea.getHighLoc().getBlockY() > PLHY) {
                    cuboidArea.getHighLoc().setY(PLHY);
                    Sides.setTopSide(false);
                }
                double TX = cuboidArea.getXSize() - 1;
                double TY = cuboidArea.getYSize() - 1;
                double TZ = cuboidArea.getZSize() - 1;
                if (!error && v.getId() != -1) {
                    Bukkit.getScheduler().cancelTask(v.getId());
                } else if (error && v.getErrorId() != -1) {
                    Bukkit.getScheduler().cancelTask(v.getErrorId());
                }
                locList.addAll(this.GetLocationsWallsByData(loc, TX, TY, TZ, cuboidArea.getLowLoc().clone(), Sides, Range));
                locList2.addAll(this.GetLocationsCornersByData(loc, TX, TY, TZ, cuboidArea.getLowLoc().clone(), Sides, Range));
            }
            v.setLoc(player.getLocation());
        } else if (error) {
            locList.addAll(v.getErrorLocations());
            locList2.addAll(v.getErrorLocations2());
        } else {
            locList.addAll(v.getLocations());
            locList2.addAll(v.getLocations2());
        }
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                ArrayList<Location> trimed2;
                ArrayList<Location> trimed;
                block32 : {
                    int size = locList.size();
                    int errorSize = locList2.size();
                    int timesMore = 1;
                    int errorTimesMore = 1;
                    if (size > Residence.getConfigManager().getVisualizerSidesCap() && !same) {
                        timesMore = size / Residence.getConfigManager().getVisualizerSidesCap() + 1;
                    }
                    if (errorSize > Residence.getConfigManager().getVisualizerFrameCap() && !same) {
                        errorTimesMore = errorSize / Residence.getConfigManager().getVisualizerFrameCap() + 1;
                    }
                    trimed = new ArrayList<Location>();
                    trimed2 = new ArrayList<Location>();
                    try {
                        int i;
                        Location l;
                        boolean spigot = Residence.isSpigot();
                        if (spigot) {
                            int i2;
                            Location l2;
                            if (!error) {
                                i2 = 0;
                                while (i2 < locList.size()) {
                                    l2 = (Location)locList.get(i2);
                                    player.spigot().playEffect(l2, Residence.getConfigManager().getSelectedSpigotSides(), 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 1, 128);
                                    if (!same) {
                                        trimed.add(l2);
                                    }
                                    i2 += timesMore;
                                }
                            } else {
                                i2 = 0;
                                while (i2 < locList.size()) {
                                    l2 = (Location)locList.get(i2);
                                    player.spigot().playEffect(l2, Residence.getConfigManager().getOverlapSpigotSides(), 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 1, 128);
                                    if (!same) {
                                        trimed.add(l2);
                                    }
                                    i2 += timesMore;
                                }
                            }
                            if (!error) {
                                i2 = 0;
                                while (i2 < locList2.size()) {
                                    l2 = (Location)locList2.get(i2);
                                    player.spigot().playEffect(l2, Residence.getConfigManager().getSelectedSpigotFrame(), 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 1, 128);
                                    if (!same) {
                                        trimed2.add(l2);
                                    }
                                    i2 += errorTimesMore;
                                }
                            } else {
                                i2 = 0;
                                while (i2 < locList2.size()) {
                                    l2 = (Location)locList2.get(i2);
                                    player.spigot().playEffect(l2, Residence.getConfigManager().getOverlapSpigotFrame(), 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 1, 128);
                                    if (!same) {
                                        trimed2.add(l2);
                                    }
                                    i2 += errorTimesMore;
                                }
                            }
                            break block32;
                        }
                        if (!error) {
                            i = 0;
                            while (i < locList.size()) {
                                l = (Location)locList.get(i);
                                Residence.getConfigManager().getSelectedSides().display(0.0f, 0.0f, 0.0f, 0.0f, 1, l, player);
                                if (!same) {
                                    trimed.add(l);
                                }
                                i += timesMore;
                            }
                        } else {
                            i = 0;
                            while (i < locList.size()) {
                                l = (Location)locList.get(i);
                                Residence.getConfigManager().getOverlapSides().display(0.0f, 0.0f, 0.0f, 0.0f, 1, l, player);
                                if (!same) {
                                    trimed.add(l);
                                }
                                i += timesMore;
                            }
                        }
                        if (!error) {
                            i = 0;
                            while (i < locList2.size()) {
                                l = (Location)locList2.get(i);
                                Residence.getConfigManager().getSelectedFrame().display(0.0f, 0.0f, 0.0f, 0.0f, 1, l, player);
                                if (!same) {
                                    trimed2.add(l);
                                }
                                i += errorTimesMore;
                            }
                        } else {
                            i = 0;
                            while (i < locList2.size()) {
                                l = (Location)locList2.get(i);
                                Residence.getConfigManager().getOverlapFrame().display(0.0f, 0.0f, 0.0f, 0.0f, 1, l, player);
                                if (!same) {
                                    trimed2.add(l);
                                }
                                i += errorTimesMore;
                            }
                        }
                    }
                    catch (Exception e) {
                        return;
                    }
                }
                if (!same) {
                    if (error) {
                        v.setErrorLocations(trimed);
                        v.setErrorLocations2(trimed2);
                    } else {
                        v.setLocations(trimed);
                        v.setLocations2(trimed2);
                    }
                }
            }
        });
        if (v.isOnce()) {
            return true;
        }
        if (v.getStart() + (long)Residence.getConfigManager().getVisualizerShowFor() < System.currentTimeMillis()) {
            return false;
        }
        int scid = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (player.isOnline()) {
                    SelectionManager.this.MakeBorders(player, error);
                }
            }
        }, (long)Residence.getConfigManager().getVisualizerUpdateInterval() * 1);
        if (!error) {
            v.setId(scid);
        } else {
            v.setErrorId(scid);
        }
        return true;
    }

    public void vert(Player player, boolean resadmin2) {
        if (this.hasPlacedBoth(player.getName())) {
            this.sky(player, resadmin2);
            this.bedrock(player, resadmin2);
        } else {
            Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
        }
    }

    public void qsky(Player player) {
        int y1 = this.playerLoc1.get(player.getName()).getBlockY();
        int y2 = this.playerLoc2.get(player.getName()).getBlockY();
        int newy = player.getLocation().getWorld().getMaxHeight() - 1;
        if (y1 > y2) {
            this.playerLoc1.get(player.getName()).setY((double)newy);
        } else {
            this.playerLoc2.get(player.getName()).setY((double)newy);
        }
    }

    public void qbedrock(Player player) {
        int y2;
        int y1 = this.playerLoc1.get(player.getName()).getBlockY();
        if (y1 < (y2 = this.playerLoc2.get(player.getName()).getBlockY())) {
            boolean newy = false;
            this.playerLoc1.get(player.getName()).setY((double)newy ? 1 : 0);
        } else {
            boolean newy = false;
            this.playerLoc2.get(player.getName()).setY((double)newy ? 1 : 0);
        }
    }

    public void sky(Player player, boolean resadmin2) {
        if (this.hasPlacedBoth(player.getName())) {
            ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
            PermissionGroup group = rPlayer.getGroup();
            int y1 = this.playerLoc1.get(player.getName()).getBlockY();
            int y2 = this.playerLoc2.get(player.getName()).getBlockY();
            int newy = player.getLocation().getWorld().getMaxHeight() - 1;
            if (y1 > y2) {
                if (!resadmin2) {
                    if (group.getMaxHeight() < newy) {
                        newy = group.getMaxHeight();
                    }
                    if (newy - y2 > group.getMaxY() - 1) {
                        newy = y2 + (group.getMaxY() - 1);
                    }
                }
                this.playerLoc1.get(player.getName()).setY((double)newy);
            } else {
                if (!resadmin2) {
                    if (group.getMaxHeight() < newy) {
                        newy = group.getMaxHeight();
                    }
                    if (newy - y1 > group.getMaxY() - 1) {
                        newy = y1 + (group.getMaxY() - 1);
                    }
                }
                this.playerLoc2.get(player.getName()).setY((double)newy);
            }
            Residence.msg((CommandSender)player, lm.Select_Sky, new Object[0]);
        } else {
            Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
        }
    }

    public void bedrock(Player player, boolean resadmin2) {
        if (this.hasPlacedBoth(player.getName())) {
            int y2;
            ResidencePlayer rPlayer = Residence.getPlayerManager().getResidencePlayer(player);
            PermissionGroup group = rPlayer.getGroup();
            int y1 = this.playerLoc1.get(player.getName()).getBlockY();
            if (y1 < (y2 = this.playerLoc2.get(player.getName()).getBlockY())) {
                int newy = 0;
                if (!resadmin2) {
                    if (newy < group.getMinHeight()) {
                        newy = group.getMinHeight();
                    }
                    if (y2 - newy > group.getMaxY() - 1) {
                        newy = y2 - (group.getMaxY() - 1);
                    }
                }
                this.playerLoc1.get(player.getName()).setY((double)newy);
            } else {
                int newy = 0;
                if (!resadmin2) {
                    if (newy < group.getMinHeight()) {
                        newy = group.getMinHeight();
                    }
                    if (y1 - newy > group.getMaxY() - 1) {
                        newy = y1 - (group.getMaxY() - 1);
                    }
                }
                this.playerLoc2.get(player.getName()).setY((double)newy);
            }
            Residence.msg((CommandSender)player, lm.Select_Bedrock, new Object[0]);
        } else {
            Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
        }
    }

    public void clearSelection(Player player) {
        this.playerLoc1.remove(player.getName());
        this.playerLoc2.remove(player.getName());
    }

    public void selectChunk(Player player) {
        Chunk chunk = player.getWorld().getChunkAt(player.getLocation());
        int xcoord = chunk.getX() * 16;
        int zcoord = chunk.getZ() * 16;
        boolean ycoord = false;
        int xmax = xcoord + 15;
        int zmax = zcoord + 15;
        int ymax = player.getLocation().getWorld().getMaxHeight() - 1;
        this.playerLoc1.put(player.getName(), new Location(player.getWorld(), (double)xcoord, (double)ycoord ? 1 : 0, (double)zcoord));
        this.playerLoc2.put(player.getName(), new Location(player.getWorld(), (double)xmax, (double)ymax, (double)zmax));
        Residence.msg((CommandSender)player, lm.Select_Success, new Object[0]);
    }

    public boolean worldEdit(Player player) {
        Residence.msg((CommandSender)player, lm.General_WorldEditNotFound, new Object[0]);
        return false;
    }

    public boolean worldEditUpdate(Player player) {
        Residence.msg((CommandSender)player, lm.General_WorldEditNotFound, new Object[0]);
        return false;
    }

    public void selectBySize(Player player, int xsize, int ysize, int zsize) {
        Location myloc = player.getLocation();
        Location loc1 = new Location(myloc.getWorld(), (double)(myloc.getBlockX() + xsize), (double)(myloc.getBlockY() + ysize), (double)(myloc.getBlockZ() + zsize));
        Location loc2 = new Location(myloc.getWorld(), (double)(myloc.getBlockX() - xsize), (double)(myloc.getBlockY() - ysize), (double)(myloc.getBlockZ() - zsize));
        this.placeLoc1(player, loc1, false);
        this.placeLoc2(player, loc2, true);
        Residence.msg((CommandSender)player, lm.Select_Success, new Object[0]);
        this.showSelectionInfo(player);
    }

    public void modify(Player player, boolean shift, double amount) {
        if (!this.hasPlacedBoth(player.getName())) {
            Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
            return;
        }
        Direction d = SelectionManager.getDirection(player);
        if (d == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Direction, new Object[0]);
            return;
        }
        CuboidArea area2 = new CuboidArea(this.playerLoc1.get(player.getName()), this.playerLoc2.get(player.getName()));
        switch (SelectionManager.$SWITCH_TABLE$com$bekvon$bukkit$residence$selection$SelectionManager$Direction()[d.ordinal()]) {
            case 2: {
                double oldy = area2.getLowLoc().getBlockY();
                if ((oldy -= amount) < 0.0) {
                    Residence.msg((CommandSender)player, lm.Select_TooLow, new Object[0]);
                    oldy = 0.0;
                }
                area2.getLowLoc().setY(oldy);
                if (shift) {
                    double oldy2 = area2.getHighLoc().getBlockY();
                    area2.getHighLoc().setY(oldy2 -= amount);
                    Residence.msg((CommandSender)player, lm.Shifting_Down, amount);
                    break;
                }
                Residence.msg((CommandSender)player, lm.Expanding_Down, amount);
                break;
            }
            case 5: {
                double oldx = area2.getLowLoc().getBlockX();
                area2.getLowLoc().setX(oldx -= amount);
                if (shift) {
                    double oldx2 = area2.getHighLoc().getBlockX();
                    area2.getHighLoc().setX(oldx2 -= amount);
                    Residence.msg((CommandSender)player, lm.Shifting_West, amount);
                    break;
                }
                Residence.msg((CommandSender)player, lm.Expanding_West, amount);
                break;
            }
            case 6: {
                double oldz = area2.getLowLoc().getBlockZ();
                area2.getLowLoc().setZ(oldz -= amount);
                if (shift) {
                    double oldz2 = area2.getHighLoc().getBlockZ();
                    area2.getHighLoc().setZ(oldz2 -= amount);
                    Residence.msg((CommandSender)player, lm.Shifting_North, amount);
                    break;
                }
                Residence.msg((CommandSender)player, lm.Expanding_North, amount);
                break;
            }
            case 3: {
                double oldx = area2.getHighLoc().getBlockX();
                area2.getHighLoc().setX(oldx += amount);
                if (shift) {
                    double oldx2 = area2.getLowLoc().getBlockX();
                    area2.getLowLoc().setX(oldx2 += amount);
                    Residence.msg((CommandSender)player, lm.Shifting_East, amount);
                    break;
                }
                Residence.msg((CommandSender)player, lm.Expanding_East, amount);
                break;
            }
            case 4: {
                double oldz = area2.getHighLoc().getBlockZ();
                area2.getHighLoc().setZ(oldz += amount);
                if (shift) {
                    double oldz2 = area2.getLowLoc().getBlockZ();
                    area2.getLowLoc().setZ(oldz2 += amount);
                    Residence.msg((CommandSender)player, lm.Shifting_South, amount);
                    break;
                }
                Residence.msg((CommandSender)player, lm.Expanding_South, amount);
                break;
            }
            case 1: {
                double oldy = area2.getHighLoc().getBlockY();
                if ((oldy += amount) > (double)(player.getLocation().getWorld().getMaxHeight() - 1)) {
                    Residence.msg((CommandSender)player, lm.Select_TooHigh, new Object[0]);
                    oldy = player.getLocation().getWorld().getMaxHeight() - 1;
                }
                area2.getHighLoc().setY(oldy);
                if (shift) {
                    double oldy2 = area2.getLowLoc().getBlockY();
                    area2.getLowLoc().setY(oldy2 += amount);
                    Residence.msg((CommandSender)player, lm.Shifting_Up, amount);
                    break;
                }
                Residence.msg((CommandSender)player, lm.Expanding_Up, amount);
                break;
            }
        }
        this.updateLocations(player, area2.getHighLoc(), area2.getLowLoc());
    }

    public boolean contract(Player player, double amount) {
        return this.contract(player, amount, false);
    }

    public boolean contract(Player player, double amount, boolean resadmin2) {
        if (!this.hasPlacedBoth(player.getName())) {
            Residence.msg((CommandSender)player, lm.Select_Points, new Object[0]);
            return false;
        }
        Direction d = SelectionManager.getDirection(player);
        if (d == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Direction, new Object[0]);
            return false;
        }
        CuboidArea area2 = new CuboidArea(this.playerLoc1.get(player.getName()), this.playerLoc2.get(player.getName()));
        switch (SelectionManager.$SWITCH_TABLE$com$bekvon$bukkit$residence$selection$SelectionManager$Direction()[d.ordinal()]) {
            case 2: {
                double oldy = area2.getHighLoc().getBlockY();
                if ((oldy -= amount) > (double)(player.getLocation().getWorld().getMaxHeight() - 1)) {
                    Residence.msg((CommandSender)player, lm.Select_TooHigh, new Object[0]);
                    oldy = player.getLocation().getWorld().getMaxHeight() - 1;
                }
                area2.getHighLoc().setY(oldy);
                Residence.msg((CommandSender)player, lm.Contracting_Down, amount);
                break;
            }
            case 5: {
                double oldx = area2.getHighLoc().getBlockX();
                area2.getHighLoc().setX(oldx -= amount);
                Residence.msg((CommandSender)player, lm.Contracting_West, amount);
                break;
            }
            case 6: {
                double oldz = area2.getHighLoc().getBlockZ();
                area2.getHighLoc().setZ(oldz -= amount);
                Residence.msg((CommandSender)player, lm.Contracting_North, amount);
                break;
            }
            case 3: {
                double oldx = area2.getLowLoc().getBlockX();
                area2.getLowLoc().setX(oldx += amount);
                Residence.msg((CommandSender)player, lm.Contracting_East, amount);
                break;
            }
            case 4: {
                double oldz = area2.getLowLoc().getBlockZ();
                area2.getLowLoc().setZ(oldz += amount);
                Residence.msg((CommandSender)player, lm.Contracting_South, amount);
                break;
            }
            case 1: {
                double oldy = area2.getLowLoc().getBlockY();
                if ((oldy += amount) < 0.0) {
                    Residence.msg((CommandSender)player, lm.Select_TooLow, new Object[0]);
                    oldy = 0.0;
                }
                area2.getLowLoc().setY(oldy);
                Residence.msg((CommandSender)player, lm.Contracting_Up, amount);
                break;
            }
        }
        this.updateLocations(player, area2.getHighLoc(), area2.getLowLoc());
        return true;
    }

    private static Direction getDirection(Player player) {
        int yaw = (int)player.getLocation().getYaw();
        if (yaw < 0) {
            yaw += 360;
        }
        yaw += 45;
        int facing = (yaw %= 360) / 90;
        float pitch = player.getLocation().getPitch();
        if (pitch < -50.0f) {
            return Direction.UP;
        }
        if (pitch > 50.0f) {
            return Direction.DOWN;
        }
        if (facing == 1) {
            return Direction.MINUSX;
        }
        if (facing == 3) {
            return Direction.PLUSX;
        }
        if (facing == 2) {
            return Direction.MINUSZ;
        }
        if (facing == 0) {
            return Direction.PLUSZ;
        }
        return null;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$selection$SelectionManager$Direction() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$com$bekvon$bukkit$residence$selection$SelectionManager$Direction;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Direction.values().length];
        try {
            arrn[Direction.DOWN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Direction.MINUSX.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Direction.MINUSZ.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Direction.PLUSX.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Direction.PLUSZ.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Direction.UP.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$com$bekvon$bukkit$residence$selection$SelectionManager$Direction = arrn;
        return $SWITCH_TABLE$com$bekvon$bukkit$residence$selection$SelectionManager$Direction;
    }

    public static enum Direction {
        UP,
        DOWN,
        PLUSX,
        PLUSZ,
        MINUSX,
        MINUSZ;
        

        private Direction(String string2, int n2) {
        }
    }

}

