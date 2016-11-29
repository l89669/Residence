/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 */
package com.bekvon.bukkit.residence.chat;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ChatInterface;
import com.bekvon.bukkit.residence.chat.ChatChannel;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Server;

public class ChatManager
implements ChatInterface {
    protected Map<String, ChatChannel> channelmap = new HashMap<String, ChatChannel>();
    protected Server server = Residence.getServ();

    @Override
    public boolean setChannel(String player, String resName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(resName);
        if (res == null) {
            return false;
        }
        return this.setChannel(player, res);
    }

    @Override
    public boolean setChannel(String player, ClaimedResidence res) {
        this.removeFromChannel(player);
        if (!this.channelmap.containsKey(res.getName())) {
            this.channelmap.put(res.getName(), new ChatChannel(res.getName(), res.getChatPrefix(), res.getChannelColor()));
        }
        this.channelmap.get(res.getName()).join(player);
        return true;
    }

    @Override
    public boolean removeFromChannel(String player) {
        for (ChatChannel chan : this.channelmap.values()) {
            if (!chan.hasMember(player)) continue;
            chan.leave(player);
            return true;
        }
        return false;
    }

    @Override
    public ChatChannel getChannel(String channel) {
        return this.channelmap.get(channel);
    }

    @Override
    public ChatChannel getPlayerChannel(String player) {
        for (ChatChannel chan : this.channelmap.values()) {
            if (!chan.hasMember(player)) continue;
            return chan;
        }
        return null;
    }
}

