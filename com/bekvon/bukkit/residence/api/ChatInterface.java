/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.chat.ChatChannel;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public interface ChatInterface {
    public boolean setChannel(String var1, String var2);

    public boolean setChannel(String var1, ClaimedResidence var2);

    public boolean removeFromChannel(String var1);

    public ChatChannel getChannel(String var1);

    public ChatChannel getPlayerChannel(String var1);
}

