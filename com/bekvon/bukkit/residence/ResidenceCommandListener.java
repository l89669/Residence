/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package com.bekvon.bukkit.residence;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.text.help.HelpEntry;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ResidenceCommandListener
extends Residence {
    private static List<String> AdminCommands = new ArrayList<String>();

    public static List<String> getAdminCommands() {
        if (AdminCommands.size() == 0) {
            AdminCommands = Residence.getCommandFiller().getCommands(false);
        }
        return AdminCommands;
    }

    /*
     * Exception decompiling
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:423)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:217)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:162)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:355)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:769)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:701)
        // org.benf.cfr.reader.Main.doJar(Main.java:134)
        // org.benf.cfr.reader.Main.main(Main.java:189)
        throw new IllegalStateException("Decompilation failed");
    }

    private static cmd getCmdClass(String[] args) {
        cmd cmdClass = null;
        try {
            Class nmsClass = Class.forName("com.bekvon.bukkit.residence.commands." + args[0].toLowerCase());
            if (cmd.class.isAssignableFrom(nmsClass)) {
                cmdClass = (cmd)nmsClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException nmsClass) {
            // empty catch block
        }
        return cmdClass;
    }

    public void sendUsage(CommandSender sender, String command2) {
        Residence.msg(sender, lm.General_DefaultUsage, command2);
    }

    private boolean commandHelp(String[] args, boolean resadmin2, CommandSender sender, Command command2) {
        if (Residence.helppages == null) {
            return false;
        }
        String helppath = this.getHelpPath(args);
        int page = 1;
        if (!args[args.length - 1].equalsIgnoreCase("?")) {
            try {
                page = Integer.parseInt(args[args.length - 1]);
            }
            catch (Exception ex) {
                Residence.msg(sender, lm.General_InvalidHelp, new Object[0]);
            }
        }
        if (command2.getName().equalsIgnoreCase("res")) {
            resadmin2 = false;
        }
        if (Residence.helppages.containesEntry(helppath)) {
            Residence.helppages.printHelp(sender, page, helppath, resadmin2);
        }
        return true;
    }

    private String getHelpPath(String[] args) {
        String helppath = "res";
        int i = 0;
        while (i < args.length) {
            if (args[i].equalsIgnoreCase("?")) break;
            helppath = String.valueOf(helppath) + "." + args[i];
            ++i;
        }
        if (!Residence.helppages.containesEntry(helppath) && args.length > 0) {
            return this.getHelpPath(Arrays.copyOf(args, args.length - 1));
        }
        return helppath;
    }
}

