/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tool
implements cmd {
    @CommandAnnotation(simple=1, priority=1600)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
        Residence.msg((CommandSender)player, lm.Select_Tool, new Object[]{Material.getMaterial((int)Residence.getConfigManager().getSelectionTooldID())});
        Residence.msg((CommandSender)player, lm.General_InfoTool, new Object[]{Material.getMaterial((int)Residence.getConfigManager().getInfoToolID())});
        Residence.msg((CommandSender)player, lm.General_Separator, new Object[0]);
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Shows residence selection and info tool names");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res tool"));
    }
}

