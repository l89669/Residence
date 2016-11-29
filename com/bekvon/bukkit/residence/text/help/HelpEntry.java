/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.text.help;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.HelpLines;
import com.bekvon.bukkit.residence.containers.lm;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HelpEntry {
    protected String name;
    protected String desc;
    protected String[] lines;
    protected List<HelpEntry> subentrys;
    protected static int linesPerPage = 8;

    public HelpEntry(String entryname) {
        this.name = entryname;
        this.subentrys = new ArrayList<HelpEntry>();
        this.lines = new String[0];
    }

    public String getName() {
        if (this.name == null) {
            return "";
        }
        return this.name;
    }

    public void setName(String inname) {
        this.name = inname;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public String getDescription() {
        if (this.desc == null) {
            return "";
        }
        return this.desc;
    }

    public void printHelp(CommandSender sender, int page, boolean resadmin2, String path) {
        List<HelpLines> helplines = this.getHelpData(sender, resadmin2);
        path = "/" + path.replace(".", " ") + " ";
        int pagecount = (int)Math.ceil((double)helplines.size() / (double)linesPerPage);
        if (page > pagecount || page < 1) {
            Residence.msg(sender, lm.Invalid_Help, new Object[0]);
            return;
        }
        String separator = Residence.msg(lm.InformationPage_Separator, new Object[0]);
        if (!(sender instanceof Player)) {
            separator = "----------";
        }
        sender.sendMessage(String.valueOf(separator) + " " + Residence.msg(lm.General_HelpPageHeader, path, page, pagecount) + " " + separator);
        int start = linesPerPage * (page - 1);
        int end = start + linesPerPage;
        int i = start;
        while (i < end) {
            if (helplines.size() > i) {
                if (helplines.get(i).getCommand() != null) {
                    HelpEntry sub = this.getSubEntry(helplines.get(i).getCommand());
                    String desc = "";
                    int y = 0;
                    String[] arrstring = sub.lines;
                    int n = arrstring.length;
                    int n2 = 0;
                    while (n2 < n) {
                        String one = arrstring[n2];
                        desc = String.valueOf(desc) + one;
                        if (++y < sub.lines.length) {
                            desc = String.valueOf(desc) + "\n";
                        }
                        ++n2;
                    }
                    if (resadmin2) {
                        path = path.replace("/res ", "/resadmin ");
                    }
                    String msg = "[\"\",{\"text\":\"" + helplines.get(i).getDesc() + "\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + path + helplines.get(i).getCommand() + " \"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + desc + "\"}]}}}]";
                    if (sender instanceof Player) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + msg));
                    } else {
                        sender.sendMessage(helplines.get(i).getDesc());
                    }
                } else {
                    sender.sendMessage(helplines.get(i).getDesc());
                }
            }
            ++i;
        }
        if (pagecount == 1) {
            return;
        }
        int NextPage = page + 1;
        NextPage = page < pagecount ? NextPage : page;
        int Prevpage = page - 1;
        Prevpage = page > 1 ? Prevpage : page;
        String baseCmd = resadmin2 ? "resadmin" : "res";
        String prevCmd = !this.name.equalsIgnoreCase("res") ? "/" + baseCmd + " " + this.name + " ? " + Prevpage : "/" + baseCmd + " ? " + Prevpage;
        String prev = "[\"\",{\"text\":\"" + separator + " " + Residence.msg(lm.General_PrevInfoPage, new Object[0]) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + prevCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + "<<<" + "\"}]}}}";
        String nextCmd = !this.name.equalsIgnoreCase("res") ? "/" + baseCmd + " " + this.name + " ? " + NextPage : "/" + baseCmd + " ? " + NextPage;
        String next = " {\"text\":\"" + Residence.msg(lm.General_NextInfoPage, new Object[0]) + " " + separator + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + nextCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ">>>" + "\"}]}}}]";
        if (sender instanceof Player) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tellraw " + sender.getName() + " " + prev + "," + next));
        }
    }

    public void printHelp(CommandSender sender, int page, String path, boolean resadmin2) {
        HelpEntry subEntry = this.getSubEntry(path);
        if (subEntry != null) {
            subEntry.printHelp(sender, page, resadmin2, path);
        } else {
            Residence.msg(sender, lm.Invalid_Help, new Object[0]);
        }
    }

    /*
     * Exception decompiling
     */
    private List<HelpLines> getHelpData(CommandSender sender, boolean resadmin) {
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

    public boolean containesEntry(String name) {
        if (this.getSubEntry(name) != null) {
            return true;
        }
        return false;
    }

    public HelpEntry getSubEntry(String name) {
        String[] split = name.split("\\.");
        HelpEntry entry = this;
        String[] arrstring = split;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String entryname = arrstring[n2];
            if ((entry = entry.findSubEntry(entryname)) == null) {
                return null;
            }
            ++n2;
        }
        return entry;
    }

    private HelpEntry findSubEntry(String name) {
        for (HelpEntry entry : this.subentrys) {
            if (!entry.getName().equalsIgnoreCase(name)) continue;
            return entry;
        }
        return null;
    }

    public void addSubEntry(HelpEntry entry) {
        if (!this.subentrys.contains(entry)) {
            this.subentrys.add(entry);
        }
    }

    public void removeSubEntry(HelpEntry entry) {
        if (this.subentrys.contains(entry)) {
            this.subentrys.remove(entry);
        }
    }

    public int getSubEntryCount() {
        return this.subentrys.size();
    }

    public static HelpEntry parseHelp(FileConfiguration node, String key) {
        String[] split = key.split("\\.");
        String thisname = split[split.length - 1];
        HelpEntry entry = new HelpEntry(thisname);
        ConfigurationSection keysnode = node.getConfigurationSection(key);
        Set keys = null;
        if (keysnode != null) {
            keys = keysnode.getKeys(false);
        }
        if (keys != null) {
            List stringList;
            if (keys.contains("Info") && (stringList = node.getStringList(String.valueOf(key) + ".Info")) != null) {
                entry.lines = new String[stringList.size()];
                int i = 0;
                while (i < stringList.size()) {
                    entry.lines[i] = ChatColor.translateAlternateColorCodes((char)'&', (String)((String)stringList.get(i)));
                    ++i;
                }
            }
            if (keys.contains("Description")) {
                entry.desc = node.getString(String.valueOf(key) + ".Description");
            }
            if (keys.contains("SubCommands")) {
                Set subcommandkeys = node.getConfigurationSection(String.valueOf(key) + ".SubCommands").getKeys(false);
                if (key.equalsIgnoreCase("CommandHelp.SubCommands.res")) {
                    subcommandkeys.clear();
                    for (String one : Residence.getCommandFiller().getCommands()) {
                        subcommandkeys.add(one);
                    }
                }
                for (String subkey : subcommandkeys) {
                    entry.subentrys.add(HelpEntry.parseHelp(node, String.valueOf(key) + ".SubCommands." + subkey));
                }
            }
        }
        return entry;
    }

    /*
     * Exception decompiling
     */
    public Set<String> getSubCommands(CommandSender sender, String[] args) {
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
}

