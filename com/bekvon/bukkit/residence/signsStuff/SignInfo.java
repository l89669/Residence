/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.signsStuff;

import com.bekvon.bukkit.residence.signsStuff.Signs;
import java.util.ArrayList;
import java.util.List;

public class SignInfo {
    List<Signs> AllSigns = new ArrayList<Signs>();

    public void setAllSigns(List<Signs> AllSigns) {
        this.AllSigns = AllSigns;
    }

    public List<Signs> GetAllSigns() {
        return this.AllSigns;
    }

    public void removeSign(Signs sign) {
        this.AllSigns.remove(sign);
    }

    public void addSign(Signs sign) {
        this.AllSigns.add(sign);
    }
}

