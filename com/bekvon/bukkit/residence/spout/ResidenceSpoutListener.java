/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.getspout.spoutapi.event.screen.ButtonClickEvent
 *  org.getspout.spoutapi.gui.Button
 *  org.getspout.spoutapi.gui.GenericLabel
 *  org.getspout.spoutapi.gui.GenericPopup
 *  org.getspout.spoutapi.gui.GenericTextField
 *  org.getspout.spoutapi.gui.InGameHUD
 *  org.getspout.spoutapi.gui.Screen
 *  org.getspout.spoutapi.gui.Widget
 *  org.getspout.spoutapi.player.SpoutPlayer
 */
package com.bekvon.bukkit.residence.spout;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.spout.ResidencePopup;
import java.util.HashMap;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ResidenceSpoutListener
implements Listener {
    protected HashMap<Player, GenericPopup> popups;

    @EventHandler(priority=EventPriority.NORMAL)
    public void onButtonClick(ButtonClickEvent event) {
        ResidencePopup.PopupType type;
        ResidencePopup screen;
        if (Residence.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        SpoutPlayer p = event.getPlayer();
        if (event.getScreen() instanceof ResidencePopup && (type = ResidencePopup.PopupType.valueOf((screen = (ResidencePopup)event.getScreen()).getPopupType())) == ResidencePopup.PopupType.FLAG_GUI) {
            ResidencePopup popup = screen;
            String flagval = null;
            String flag = null;
            String player = null;
            ClaimedResidence res = null;
            String group = null;
            boolean resadmin2 = (Boolean)popup.getMetaData().get("admin");
            Button button = event.getButton();
            if (button.getText().equalsIgnoreCase("Close")) {
                event.getPlayer().getMainScreen().removeWidget((Widget)screen);
                return;
            }
            if (button.getText().equalsIgnoreCase("RemoveAll")) {
                flagval = "removeall";
            } else if (button.getText().equalsIgnoreCase("SetTrue")) {
                flagval = "true";
            } else if (button.getText().equalsIgnoreCase("SetFalse")) {
                flagval = "false";
            } else if (button.getText().equalsIgnoreCase("Remove")) {
                flagval = "remove";
            }
            player = ((GenericTextField)popup.getWidget("PlayerName")).getText();
            group = ((GenericTextField)popup.getWidget("GroupName")).getText();
            flag = ((GenericTextField)popup.getWidget("FlagName")).getText();
            res = Residence.getResidenceManager().getByName(((GenericLabel)popup.getWidget("ResidenceName")).getText());
            if (res == null || flagval == null || flagval.equalsIgnoreCase("") || (flag == null || flag.equalsIgnoreCase("")) && !flagval.equalsIgnoreCase("removeall")) {
                return;
            }
            if ((player == null || player.equalsIgnoreCase("")) && (group == null || group.equalsIgnoreCase(""))) {
                res.getPermissions().setFlag((CommandSender)p, flag, flagval, resadmin2);
            } else if (group != null && !group.equalsIgnoreCase("")) {
                if (flagval.equalsIgnoreCase("removeall")) {
                    res.getPermissions().removeAllGroupFlags((Player)p, group, resadmin2);
                } else {
                    res.getPermissions().setGroupFlag((Player)p, group, flag, flagval, resadmin2);
                }
            } else if (player != null && !player.equalsIgnoreCase("")) {
                if (flagval.equalsIgnoreCase("removeall")) {
                    res.getPermissions().removeAllPlayerFlags((CommandSender)p, player, resadmin2);
                } else {
                    res.getPermissions().setPlayerFlag((CommandSender)p, player, flag, flagval, resadmin2, true);
                }
            }
        }
    }
}

