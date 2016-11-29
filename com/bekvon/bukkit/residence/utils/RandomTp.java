/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.World$Environment
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.RandomTeleport;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class RandomTp {
    static int miny = 63;
    private Residence plugin;

    public RandomTp(Residence plugin) {
        this.plugin = plugin;
    }

    public Location getRandomlocation(String WorldName) {
        if (WorldName == null) {
            return null;
        }
        Random random = new Random(System.currentTimeMillis());
        boolean ok = false;
        double x = 0.0;
        double z = 0.0;
        int tries = 0;
        RandomTeleport rtloc = null;
        for (RandomTeleport one : Residence.getConfigManager().getRandomTeleport()) {
            if (!one.getWorld().equalsIgnoreCase(WorldName)) continue;
            rtloc = one;
            break;
        }
        if (rtloc == null) {
            return null;
        }
        World world = rtloc.getCenter().getWorld();
        if (world == null) {
            return null;
        }
        int inerrange = rtloc.getMinCord();
        int outerrange = rtloc.getMaxCord();
        int maxtries = Residence.getConfigManager().getrtMaxTries();
        int centerX = rtloc.getCenter().getBlockX();
        int centerY = rtloc.getCenter().getBlockZ();
        Location loc = null;
        block1 : while (!ok) {
            ClaimedResidence res;
            if (++tries > maxtries) {
                return null;
            }
            x = (double)(random.nextInt(outerrange * 2) - outerrange) + 0.5 + (double)centerX;
            if (x > (double)(inerrange * -1) && x < (double)inerrange || (z = (double)(random.nextInt(outerrange * 2) - outerrange) + 0.5 + (double)centerY) > (double)(inerrange * -1) && z < (double)inerrange) continue;
            loc = new Location(world, x, (double)world.getMaxHeight(), z);
            int dir = random.nextInt(359);
            int max = loc.getWorld().getMaxHeight();
            max = loc.getWorld().getEnvironment() == World.Environment.NETHER ? 100 : max;
            loc.setYaw((float)dir);
            int i = max;
            while (i > 0) {
                loc.setY((double)i);
                Block block = loc.getBlock();
                Block block2 = loc.clone().add(0.0, 1.0, 0.0).getBlock();
                Block block3 = loc.clone().add(0.0, -1.0, 0.0).getBlock();
                if (!Residence.getNms().isEmptyBlock(block3) && Residence.getNms().isEmptyBlock(block) && Residence.getNms().isEmptyBlock(block2)) break;
                if (i <= 3) {
                    loc = null;
                    continue block1;
                }
                --i;
            }
            if (!Residence.getNms().isEmptyBlock(loc.getBlock()) || loc.clone().add(0.0, -1.0, 0.0).getBlock().getState().getType() == Material.LAVA || loc.clone().add(0.0, -1.0, 0.0).getBlock().getState().getType() == Material.STATIONARY_LAVA || loc.clone().add(0.0, -1.0, 0.0).getBlock().getState().getType() == Material.WATER || loc.clone().add(0.0, -1.0, 0.0).getBlock().getState().getType() == Material.STATIONARY_WATER || (res = Residence.getResidenceManager().getByLoc(loc)) != null) continue;
            loc.setY(loc.getY() + 2.0);
            break;
        }
        return loc;
    }

    public void performDelaydTp(final Location loc, final Player targetPlayer) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (!Residence.getTeleportDelayMap().contains(targetPlayer.getName()) && Residence.getConfigManager().getTeleportDelay() > 0) {
                    return;
                }
                if (Residence.getTeleportDelayMap().contains(targetPlayer.getName())) {
                    Residence.getTeleportDelayMap().remove(targetPlayer.getName());
                }
                targetPlayer.teleport(loc);
                Residence.msg((CommandSender)targetPlayer, lm.RandomTeleport_TeleportSuccess, loc.getX(), loc.getY(), loc.getZ());
            }
        }, (long)Residence.getConfigManager().getTeleportDelay() * 20);
    }

    public void performInstantTp(Location loc, Player targetPlayer) {
        targetPlayer.teleport(loc);
        Residence.msg((CommandSender)targetPlayer, lm.RandomTeleport_TeleportSuccess, loc.getX(), loc.getY(), loc.getZ());
    }

}

