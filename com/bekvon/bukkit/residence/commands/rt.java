/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.RandomTeleport;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rt
implements cmd {
    @CommandAnnotation(simple=1, priority=2500)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        Iterator<RandomTeleport> iterator;
        RandomTeleport one2;
        if (args.length != 1 && args.length != 2 && args.length != 3) {
            return false;
        }
        if (!sender.hasPermission("residence.randomtp") && !resadmin2) {
            Residence.msg(sender, lm.General_NoPermission, new Object[0]);
            return true;
        }
        String wname = null;
        Player tPlayer = null;
        if (args.length > 1) {
            int i = 1;
            while (i < args.length) {
                block18 : {
                    for (RandomTeleport one2 : Residence.getConfigManager().getRandomTeleport()) {
                        if (!one2.getWorld().equalsIgnoreCase(args[i])) continue;
                        wname = one2.getWorld();
                        break block18;
                    }
                    Player p = Bukkit.getPlayer((String)args[i]);
                    if (p != null) {
                        tPlayer = p;
                    }
                }
                ++i;
            }
        }
        if (args.length > 1 && wname == null && tPlayer == null) {
            Residence.msg(sender, lm.Invalid_World, new Object[0]);
            String worlds = "";
            iterator = Residence.getConfigManager().getRandomTeleport().iterator();
            if (iterator.hasNext()) {
                one2 = iterator.next();
                worlds = String.valueOf(worlds) + one2.getWorld() + " ";
            }
            Residence.msg(sender, lm.RandomTeleport_WorldList, worlds);
            return true;
        }
        if (tPlayer == null && sender instanceof Player) {
            tPlayer = (Player)sender;
        }
        if (wname == null && tPlayer != null) {
            wname = tPlayer.getLocation().getWorld().getName();
        }
        if (wname == null && tPlayer == null) {
            Residence.msg(sender, lm.Invalid_World, new Object[0]);
            String worlds = "";
            iterator = Residence.getConfigManager().getRandomTeleport().iterator();
            if (iterator.hasNext()) {
                one2 = iterator.next();
                worlds = String.valueOf(worlds) + one2.getWorld() + " ";
            }
            Residence.msg(sender, lm.RandomTeleport_WorldList, worlds);
            return true;
        }
        if (tPlayer == null) {
            return false;
        }
        if (!sender.getName().equalsIgnoreCase(tPlayer.getName()) && !sender.hasPermission("residence.randomtp.admin")) {
            return false;
        }
        int sec = Residence.getConfigManager().getrtCooldown();
        if (Residence.getRandomTeleportMap().containsKey(tPlayer.getName()) && !resadmin2 && Residence.getRandomTeleportMap().get(tPlayer.getName()) + (long)(sec * 1000) > System.currentTimeMillis()) {
            int left = (int)((long)sec - (System.currentTimeMillis() - Residence.getRandomTeleportMap().get(tPlayer.getName())) / 1000);
            Residence.msg((CommandSender)tPlayer, lm.RandomTeleport_TpLimit, left);
            return true;
        }
        Location loc = Residence.getRandomTpManager().getRandomlocation(wname);
        Residence.getRandomTeleportMap().put(tPlayer.getName(), System.currentTimeMillis());
        if (loc == null) {
            Residence.msg(sender, lm.RandomTeleport_IncorrectLocation, sec);
            return true;
        }
        if (Residence.getConfigManager().getTeleportDelay() > 0 && !resadmin2) {
            Residence.msg((CommandSender)tPlayer, lm.RandomTeleport_TeleportStarted, loc.getX(), loc.getY(), loc.getZ(), Residence.getConfigManager().getTeleportDelay());
            Residence.getTeleportDelayMap().add(tPlayer.getName());
            Residence.getRandomTpManager().performDelaydTp(loc, tPlayer);
        } else {
            Residence.getRandomTpManager().performInstantTp(loc, tPlayer);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Teleports to random location in world");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res rt (worldname) (playerName)", "Teleports you to random location in defined world."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[worldname]", "[playername]"));
    }
}

