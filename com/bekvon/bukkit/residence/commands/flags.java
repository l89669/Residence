/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.cmd;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class flags
implements cmd {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode;

    @CommandAnnotation(simple=1, priority=1200)
    @Override
    public boolean perform(String[] args, boolean resadmin2, Command command2, CommandSender sender) {
        int page = 1;
        try {
            if (args.length > 0) {
                page = Integer.parseInt(args[args.length - 1]);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (resadmin2) {
            Bukkit.dispatchCommand((CommandSender)sender, (String)("resadmin flags ? " + page));
        } else {
            Bukkit.dispatchCommand((CommandSender)sender, (String)("res flags ? " + page));
        }
        return true;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "List of flags");
        c.get(String.valueOf(path) + "Info", Arrays.asList("For flag values, usually true allows the action, and false denys the action."));
        Flags[] arrflags = Flags.values();
        int n = arrflags.length;
        int n2 = 0;
        while (n2 < n) {
            Flags fl = arrflags[n2];
            String pt = String.valueOf(path) + "SubCommands." + fl.getName();
            c.get(String.valueOf(pt) + ".Description", fl.getDesc());
            String forSet = "set/pset";
            switch (flags.$SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode()[fl.getFlagMode().ordinal()]) {
                case 1: 
                case 4: {
                    forSet = "pset";
                    break;
                }
                case 2: {
                    forSet = "set";
                    break;
                }
            }
            c.get(String.valueOf(pt) + ".Info", Arrays.asList("&eUsage: &6/res " + forSet + " <residence> " + fl.getName() + " true/false/remove"));
            ++n2;
        }
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "pset"), Arrays.asList("[residence]", "[flag]", "[true%%false%%remove]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "set"), Arrays.asList("[residence]", "[flag]", "[true%%false%%remove]"));
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Flags.FlagMode.values().length];
        try {
            arrn[Flags.FlagMode.Both.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Flags.FlagMode.Group.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Flags.FlagMode.Player.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Flags.FlagMode.Residence.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode = arrn;
        return $SWITCH_TABLE$com$bekvon$bukkit$residence$containers$Flags$FlagMode;
    }
}

