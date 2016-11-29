/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 *  org.getspout.spoutapi.gui.GenericPopup
 *  org.getspout.spoutapi.gui.Screen
 *  org.getspout.spoutapi.gui.Widget
 */
package com.bekvon.bukkit.residence.spout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.Widget;

public class ResidencePopup
extends GenericPopup {
    int xspacing = 15;
    int yspacing = 5;
    int xsize = 70;
    int ysize = 15;
    HashMap<String, Widget> wigs = new HashMap();
    HashMap<String, Object> metaData = new HashMap();
    public String type;

    public ResidencePopup(PopupType ptype) {
        this.type = ptype.toString();
    }

    public HashMap<String, Object> getMetaData() {
        return this.metaData;
    }

    public void setPopupType(String t) {
        this.type = t;
    }

    public String getPopupType() {
        return this.type;
    }

    public Screen gridAttachWidget(Plugin plugin, Widget widget, int column, int row) {
        return this.gridAttachWidget(null, plugin, widget, column, row);
    }

    public Screen gridAttachWidget(String wID, Plugin plugin, Widget widget, int column, int row) {
        widget.setX(this.xspacing + column * this.xspacing + column * this.xsize);
        widget.setY(this.yspacing + row * this.yspacing + row * this.ysize);
        widget.setWidth(this.xsize);
        widget.setHeight(this.ysize);
        if (wID != null) {
            this.wigs.put(wID, widget);
        }
        this.setDirty(true);
        return super.attachWidget(plugin, widget);
    }

    public Widget getWidget(String wID) {
        return this.wigs.get(wID);
    }

    public ArrayList<String> getWidgetIDs() {
        ArrayList<String> ids = new ArrayList<String>();
        for (String id : this.wigs.keySet()) {
            ids.add(id);
        }
        return ids;
    }

    public Screen removeWidget(Widget widget) {
        this.wigs.values().remove((Object)widget);
        return super.removeWidget(widget);
    }

    public Screen removeWidgets(Plugin p) {
        Iterator<Widget> it = this.wigs.values().iterator();
        while (it.hasNext()) {
            Widget next = it.next();
            if (next.getPlugin() != p) continue;
            it.remove();
        }
        return super.removeWidgets(p);
    }

    public void setGridXSpacing(int xspace) {
        this.xspacing = xspace;
    }

    public void setGridYSpacing(int yspace) {
        this.yspacing = yspace;
    }

    public void setGridXSize(int xs) {
        this.xsize = xs;
    }

    public void setGridYSize(int ys) {
        this.ysize = ys;
    }

    public static enum PopupType {
        GENERIC,
        FLAG_GUI,
        INFO_GUI;
        

        private PopupType(String string2, int n2) {
        }
    }

}

