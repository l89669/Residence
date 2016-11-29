/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.api;

import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import java.util.ArrayList;

public interface ResidencePlayerInterface {
    public ArrayList<String> getResidenceList(String var1);

    public ArrayList<String> getResidenceList(String var1, boolean var2);

    public PermissionGroup getGroup(String var1);

    public int getMaxResidences(String var1);

    public int getMaxSubzones(String var1);

    public int getMaxRents(String var1);

    public ResidencePlayer getResidencePlayer(String var1);
}

