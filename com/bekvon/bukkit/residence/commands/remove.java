/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class remove
implements cmd {
    @CommandAnnotation(simple=1, priority=2300)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        ClaimedResidence res = null;
        String senderName = sender.getName();
        if (args.length == 2) {
            res = Residence.getResidenceManager().getByName(args[1]);
        } else if (sender instanceof Player && args.length == 1) {
            res = Residence.getResidenceManager().getByLoc(((Player)sender).getLocation());
        }
        if (res == null) {
            Residence.msg(sender, lm.Invalid_Residence, new Object[0]);
            return true;
        }
        if (res.isSubzone() && !sender.hasPermission("residence.delete.subzone") && !resadmin2) {
            Residence.msg(sender, lm.Subzone_CantDelete, new Object[0]);
            return true;
        }
        if (res.isSubzone() && sender.hasPermission("residence.delete.subzone") && !resadmin2 && Residence.getConfigManager().isPreventSubZoneRemoval() && !res.getParent().isOwner(sender) && !res.getPermissions().playerHas(sender.getName(), Flags.admin, FlagPermissions.FlagCombo.OnlyTrue)) {
            Residence.msg(sender, lm.Subzone_CantDeleteNotOwnerOfParent, new Object[0]);
            return true;
        }
        if (!res.isSubzone() && sender.hasPermission("residence.delete") && !resadmin2 && !res.isOwner(sender)) {
            Residence.msg(sender, lm.Residence_CantDeleteResidence, new Object[0]);
            return true;
        }
        if (!(res.isSubzone() || sender.hasPermission("residence.delete") || resadmin2)) {
            Residence.msg(sender, lm.Residence_CantDeleteResidence, new Object[0]);
            return true;
        }
        if (Residence.deleteConfirm.containsKey(senderName)) {
            Residence.deleteConfirm.remove(senderName);
        }
        String resname = res.getName();
        if (!Residence.deleteConfirm.containsKey(senderName) || !resname.equalsIgnoreCase(Residence.deleteConfirm.get(senderName))) {
            String cmd2 = "res";
            if (resadmin2) {
                cmd2 = "resadmin";
            }
            if (sender instanceof Player) {
                String raw = "";
                raw = res.isSubzone() ? Residence.getResidenceManager().convertToRaw(null, Residence.msg(lm.Subzone_DeleteConfirm, res.getResidenceName()), "Click to confirm", String.valueOf(cmd2) + " confirm") : Residence.getResidenceManager().convertToRaw(null, Residence.msg(lm.Residence_DeleteConfirm, res.getResidenceName()), "Click to confirm", String.valueOf(cmd2) + " confirm");
                if (Residence.msg(lm.Subzone_DeleteConfirm, res.getResidenceName()).length() > 0) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + raw));
                }
            } else if (res.isSubzone()) {
                Residence.msg(sender, lm.Subzone_DeleteConfirm, res.getResidenceName());
            } else {
                Residence.msg(sender, lm.Residence_DeleteConfirm, res.getResidenceName());
            }
            Residence.deleteConfirm.put(senderName, resname);
        } else {
            Residence.getResidenceManager().removeResidence(sender, resname, resadmin2);
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Remove residences.");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res remove <residence name>"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName()), Arrays.asList("[residence]"));
    }
}

