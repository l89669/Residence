/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
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
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class confirm
implements cmd {
    @CommandAnnotation(simple=1, priority=2400)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        Player player = null;
        String name = "Console";
        if (sender instanceof Player) {
            player = (Player)sender;
            name = player.getName();
        }
        if (args.length != 1) {
            return true;
        }
        String area2 = Residence.deleteConfirm.get(name);
        if (area2 == null) {
            Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
        } else {
            Residence.getResidenceManager().removeResidence(player, area2, resadmin2);
            Residence.deleteConfirm.remove(name);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Confirms removal of a residence.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res confirm", "Confirms removal of a residence."));
    }
}

