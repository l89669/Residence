/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package com.bekvon.bukkit.residence.containers;

import com.bekvon.bukkit.residence.containers.ConfigReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface cmd {
    public boolean perform(String[] var1, boolean var2, Command var3, CommandSender var4);

    public void getLocale(ConfigReader var1, String var2);
}

