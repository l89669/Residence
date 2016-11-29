/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.TabCompleter
 *  org.bukkit.util.StringUtil
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.Residence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabComplete
implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command2, String label, String[] args) {
        ArrayList<String> completionList = new ArrayList<String>();
        Set<String> Commands = Residence.getHelpPages().getSubCommands(sender, args);
        String PartOfCommand = args[args.length - 1];
        StringUtil.copyPartialMatches((String)PartOfCommand, Commands, completionList);
        Collections.sort(completionList);
        return completionList;
    }
}

