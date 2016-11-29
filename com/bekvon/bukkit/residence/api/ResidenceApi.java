/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ChatInterface;
import com.bekvon.bukkit.residence.api.MarketBuyInterface;
import com.bekvon.bukkit.residence.api.MarketRentInterface;
import com.bekvon.bukkit.residence.api.ResidenceInterface;
import com.bekvon.bukkit.residence.api.ResidencePlayerInterface;

public class ResidenceApi {
    public static MarketBuyInterface getMarketBuyManager() {
        return Residence.getMarketBuyManagerAPI();
    }

    public static MarketRentInterface getMarketRentManager() {
        return Residence.getMarketRentManagerAPI();
    }

    public static ResidencePlayerInterface getPlayerManager() {
        return Residence.getPlayerManagerAPI();
    }

    public static ChatInterface getChatManager() {
        return Residence.getResidenceChatAPI();
    }

    public static ResidenceInterface getResidenceManager() {
        return Residence.getResidenceManagerAPI();
    }
}

