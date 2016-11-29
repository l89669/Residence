/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.event;

import com.bekvon.bukkit.residence.event.ResidenceEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class ResidenceRenameEvent
extends ResidenceEvent {
    protected String NewResName;
    protected String OldResName;
    protected ClaimedResidence res;

    public ResidenceRenameEvent(ClaimedResidence resref, String NewName, String OldName) {
        super("RESIDENCE_RENAME", resref);
        this.NewResName = NewName;
        this.OldResName = OldName;
        this.res = resref;
    }

    public String getNewResidenceName() {
        return this.NewResName;
    }

    public String getOldResidenceName() {
        return this.OldResName;
    }

    @Override
    public ClaimedResidence getResidence() {
        return this.res;
    }
}

