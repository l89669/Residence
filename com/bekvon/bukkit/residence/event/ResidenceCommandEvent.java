/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package com.bekvon.bukkit.residence.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ResidenceCommandEvent
extends Event
implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    protected boolean cancelled = false;
    protected String cmd;
    protected String[] arglist;
    CommandSender commandsender;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ResidenceCommandEvent(String command2, String[] args, CommandSender sender) {
        this.arglist = args;
        this.cmd = command2;
        this.commandsender = sender;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean bln) {
        this.cancelled = bln;
    }

    public String getCommand() {
        return this.cmd;
    }

    public String[] getArgs() {
        return this.arglist;
    }

    public CommandSender getSender() {
        return this.commandsender;
    }
}

