/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class signupdate
implements cmd {
    @CommandAnnotation(simple=0, priority=5700)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        if (args.length == 1) {
            if (!resadmin2) {
                Residence.msg(sender, lm.General_NoPermission, new Object[0]);
                return true;
            }
            int number = Residence.getSignUtil().updateAllSigns();
            Residence.msg(sender, lm.Sign_Updated, number);
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Updated residence signs");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res signupdate"));
    }
}

