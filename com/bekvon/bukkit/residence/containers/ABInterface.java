/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.containers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ABInterface {
    public void send(CommandSender var1, String var2);

    public void send(Player var1, String var2);

    public void sendTitle(Player var1, Object var2, Object var3);
}

