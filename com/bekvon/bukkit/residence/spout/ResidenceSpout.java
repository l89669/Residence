/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.getspout.spoutapi.gui.Button
 *  org.getspout.spoutapi.gui.Color
 *  org.getspout.spoutapi.gui.Control
 *  org.getspout.spoutapi.gui.GenericButton
 *  org.getspout.spoutapi.gui.GenericLabel
 *  org.getspout.spoutapi.gui.GenericTextField
 *  org.getspout.spoutapi.gui.InGameHUD
 *  org.getspout.spoutapi.gui.Label
 *  org.getspout.spoutapi.gui.PopupScreen
 *  org.getspout.spoutapi.gui.Screen
 *  org.getspout.spoutapi.gui.TextField
 *  org.getspout.spoutapi.gui.Widget
 *  org.getspout.spoutapi.player.SpoutPlayer
 */
package com.bekvon.bukkit.residence.spout;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.spout.ResidencePopup;
import java.util.HashMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.Control;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ResidenceSpout {
    protected static HashMap<Player, Widget> screens = new HashMap();
    Residence plugin;

    public ResidenceSpout(Residence plug) {
        this.plugin = plug;
    }

    public void showResidenceFlagGUI(SpoutPlayer player, String resname, boolean resadmin2) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(resname);
        if (res.getPermissions().hasResidencePermission((CommandSender)player, false)) {
            Color fieldcolor = new Color(0.0f, 0.0f, 0.3f, 1.0f);
            Color textPrimaryColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
            Color textSecondaryColor = new Color(1.0f, 1.0f, 0.0f, 1.0f);
            Color hoverColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
            ResidencePopup popup = new ResidencePopup(ResidencePopup.PopupType.FLAG_GUI);
            popup.getMetaData().put("admin", resadmin2);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel("Admin: ").setTextColor(textPrimaryColor), 3, 1);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel(Boolean.toString(resadmin2)).setTextColor(textSecondaryColor), 4, 1);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel("Residence: ").setTextColor(textPrimaryColor), 0, 0);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel("Flag: ").setTextColor(textPrimaryColor), 0, 1);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel("Player: ").setTextColor(textPrimaryColor), 0, 2);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel("Group: ").setTextColor(textPrimaryColor), 0, 3);
            popup.gridAttachWidget("ResidenceName", (Plugin)this.plugin, (Widget)new GenericLabel(resname).setTextColor(textSecondaryColor), 1, 0);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel("Owner: ").setTextColor(textPrimaryColor), 0, 4);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel("World: ").setTextColor(textPrimaryColor), 0, 5);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel(res.getOwner()).setTextColor(textSecondaryColor), 1, 4);
            popup.gridAttachWidget((Plugin)this.plugin, (Widget)new GenericLabel(res.getWorld()).setTextColor(textSecondaryColor), 1, 5);
            GenericTextField flag = new GenericTextField();
            flag.setTooltip("The name of the flag...");
            flag.setColor(textSecondaryColor);
            flag.setFieldColor(fieldcolor);
            popup.gridAttachWidget("FlagName", (Plugin)this.plugin, (Widget)flag, 1, 1);
            GenericTextField playername = new GenericTextField();
            playername.setTooltip("The name of the player...");
            playername.setColor(textSecondaryColor);
            playername.setFieldColor(fieldcolor);
            popup.gridAttachWidget("PlayerName", (Plugin)this.plugin, (Widget)playername, 1, 2);
            GenericTextField groupname = new GenericTextField();
            groupname.setTooltip("The name of the group...");
            groupname.setColor(textSecondaryColor);
            groupname.setFieldColor(fieldcolor);
            popup.gridAttachWidget("GroupName", (Plugin)this.plugin, (Widget)groupname, 1, 3);
            GenericButton truebutton = new GenericButton("SetTrue");
            truebutton.setTooltip("Set the flag to true.");
            truebutton.setColor(textSecondaryColor);
            truebutton.setHoverColor(hoverColor);
            popup.gridAttachWidget("TrueButton", (Plugin)this.plugin, (Widget)truebutton, 2, 1);
            GenericButton falsebutton = new GenericButton("SetFalse");
            falsebutton.setTooltip("Set the flag to false.");
            falsebutton.setColor(textSecondaryColor);
            falsebutton.setHoverColor(hoverColor);
            popup.gridAttachWidget("FalseButton", (Plugin)this.plugin, (Widget)falsebutton, 2, 2);
            GenericButton removebutton = new GenericButton("Remove");
            removebutton.setTooltip("Remove the flag.");
            removebutton.setColor(textSecondaryColor);
            removebutton.setHoverColor(hoverColor);
            popup.gridAttachWidget("RemoveButton", (Plugin)this.plugin, (Widget)removebutton, 2, 3);
            GenericButton removeallbutton = new GenericButton("RemoveAll");
            removeallbutton.setTooltip("Remove all flags from the player or group...");
            removeallbutton.setColor(textSecondaryColor);
            removeallbutton.setHoverColor(hoverColor);
            popup.gridAttachWidget("RemoveAllButton", (Plugin)this.plugin, (Widget)removeallbutton, 3, 3);
            screens.put((Player)player, (Widget)popup);
            player.getMainScreen().attachPopupScreen((PopupScreen)popup);
        } else {
            Residence.msg((CommandSender)player, lm.General_NoPermission, new Object[0]);
        }
    }
}

