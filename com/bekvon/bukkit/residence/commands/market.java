/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.ConfigReader;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class market
implements cmd {
    /*
     * Exception decompiling
     */
    @CommandAnnotation(simple=1, priority=2600)
    @Override
    public boolean perform(String[] args, boolean resadmin, Command command, CommandSender sender) {
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

    private static boolean commandResMarketRent(String[] args, boolean resadmin2, Player player) {
        if (args.length < 2 || args.length > 4) {
            return false;
        }
        boolean repeat = Residence.getConfigManager().isRentPlayerAutoPay();
        ClaimedResidence res = null;
        if (args.length == 4) {
            if (args[3].equalsIgnoreCase("t") || args[3].equalsIgnoreCase("true")) {
                repeat = true;
            } else if (args[3].equalsIgnoreCase("f") || args[3].equalsIgnoreCase("false")) {
                repeat = false;
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Boolean, new Object[0]);
                return true;
            }
        }
        if (args.length == 2) {
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
        } else if (args.length > 2) {
            res = Residence.getResidenceManager().getByName(args[2]);
        }
        if (res != null) {
            Residence.getRentManager().rent(player, res, repeat, resadmin2);
        } else {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        }
        return true;
    }

    private static boolean commandResMarketPayRent(String[] args, boolean resadmin2, Player player) {
        if (args.length != 2 && args.length != 3) {
            return false;
        }
        ClaimedResidence res = null;
        res = args.length == 2 ? Residence.getResidenceManager().getByLoc(player.getLocation()) : Residence.getResidenceManager().getByName(args[2]);
        if (res != null) {
            Residence.getRentManager().payRent(player, res, resadmin2);
        } else {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
        }
        return true;
    }

    private static boolean commandResMarketRentable(String[] args, boolean resadmin2, Player player) {
        int cost;
        int days;
        if (args.length < 5 || args.length > 8) {
            return false;
        }
        if (!Residence.getConfigManager().enabledRentSystem()) {
            Residence.msg((CommandSender)player, lm.Rent_Disabled, new Object[0]);
            return true;
        }
        try {
            cost = Integer.parseInt(args[3]);
        }
        catch (Exception ex) {
            Residence.msg((CommandSender)player, lm.Invalid_Cost, new Object[0]);
            return true;
        }
        try {
            days = Integer.parseInt(args[4]);
        }
        catch (Exception ex) {
            Residence.msg((CommandSender)player, lm.Invalid_Days, new Object[0]);
            return true;
        }
        boolean AllowRenewing = Residence.getConfigManager().isRentAllowRenewing();
        if (args.length >= 6) {
            String ag = args[5];
            if (ag.equalsIgnoreCase("t") || ag.equalsIgnoreCase("true")) {
                AllowRenewing = true;
            } else if (ag.equalsIgnoreCase("f") || ag.equalsIgnoreCase("false")) {
                AllowRenewing = false;
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Boolean, new Object[0]);
                return true;
            }
        }
        boolean StayInMarket = Residence.getConfigManager().isRentStayInMarket();
        if (args.length >= 7) {
            String ag = args[6];
            if (ag.equalsIgnoreCase("t") || ag.equalsIgnoreCase("true")) {
                StayInMarket = true;
            } else if (ag.equalsIgnoreCase("f") || ag.equalsIgnoreCase("false")) {
                StayInMarket = false;
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Boolean, new Object[0]);
                return true;
            }
        }
        boolean AllowAutoPay = Residence.getConfigManager().isRentAllowAutoPay();
        if (args.length >= 8) {
            String ag = args[7];
            if (ag.equalsIgnoreCase("t") || ag.equalsIgnoreCase("true")) {
                AllowAutoPay = true;
            } else if (ag.equalsIgnoreCase("f") || ag.equalsIgnoreCase("false")) {
                AllowAutoPay = false;
            } else {
                Residence.msg((CommandSender)player, lm.Invalid_Boolean, new Object[0]);
                return true;
            }
        }
        Residence.getRentManager().setForRent(player, args[2], cost, days, AllowRenewing, StayInMarket, AllowAutoPay, resadmin2);
        return true;
    }

    private static boolean commandResMarketAutoPay(String[] args, boolean resadmin2, Player player) {
        boolean value;
        if (!Residence.getConfigManager().enableEconomy()) {
            Residence.msg((CommandSender)player, lm.Economy_MarketDisabled, new Object[0]);
            return true;
        }
        if (args.length != 3 && args.length != 4) {
            return false;
        }
        String barg = "";
        ClaimedResidence res = null;
        if (args.length == 3) {
            barg = args[2];
            res = Residence.getResidenceManager().getByLoc(player.getLocation());
        } else {
            barg = args[3];
            res = Residence.getResidenceManager().getByName(args[2]);
        }
        if (barg.equalsIgnoreCase("true") || barg.equalsIgnoreCase("t")) {
            value = true;
        } else if (barg.equalsIgnoreCase("false") || barg.equalsIgnoreCase("f")) {
            value = false;
        } else {
            Residence.msg((CommandSender)player, lm.Invalid_Boolean, new Object[0]);
            return true;
        }
        if (res == null) {
            Residence.msg((CommandSender)player, lm.Invalid_Residence, new Object[0]);
            return true;
        }
        if (res.isRented() && res.getRentedLand().player.equalsIgnoreCase(player.getName())) {
            Residence.getRentManager().setRentedRepeatable(player, res.getName(), value, resadmin2);
        } else if (res.isForRent()) {
            Residence.getRentManager().setRentRepeatable(player, res.getName(), value, resadmin2);
        } else {
            Residence.msg((CommandSender)player, lm.Economy_RentReleaseInvalid, (Object)ChatColor.YELLOW + res.getName() + (Object)ChatColor.RED);
        }
        return true;
    }

    private static boolean commandResMarketList(String[] args, Player player, int page) {
        if (!Residence.getConfigManager().enableEconomy()) {
            Residence.msg((CommandSender)player, lm.Economy_MarketDisabled, new Object[0]);
            return true;
        }
        Residence.msg((CommandSender)player, lm.General_MarketList, new Object[0]);
        if (args.length < 3) {
            return false;
        }
        if (args[2].equalsIgnoreCase("sell")) {
            Residence.getTransactionManager().printForSaleResidences(player, page);
            return true;
        }
        if (args[2].equalsIgnoreCase("rent")) {
            if (Residence.getConfigManager().enabledRentSystem()) {
                Residence.getRentManager().printRentableResidences(player, page);
            }
            return true;
        }
        return false;
    }

    @Override
    public void getLocale(ConfigReader c, String path) {
        c.get(String.valueOf(path) + "Description", "Buy, Sell, or Rent Residences");
        c.get(String.valueOf(path) + "Info", Arrays.asList("&eUsage: &6/res market ? for more Info"));
        path = String.valueOf(path) + "SubCommands.";
        c.get(String.valueOf(path) + "Info.Description", "Get economy Info on residence");
        c.get(String.valueOf(path) + "Info.Info", Arrays.asList("&eUsage: &6/res market Info [residence]", "Shows if the Residence is for sale or for rent, and the cost."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "Info"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "list.Description", "Lists rentable and for sale residences.");
        c.get(String.valueOf(path) + "list.Info", Arrays.asList("&eUsage: &6/res market list [rent/sell]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "list"), Arrays.asList("rent%%sell"));
        c.get(String.valueOf(path) + "list.SubCommands.rent.Description", "Lists rentable residences.");
        c.get(String.valueOf(path) + "list.SubCommands.rent.Info", Arrays.asList("&eUsage: &6/res market list rent"));
        c.get(String.valueOf(path) + "list.SubCommands.sell.Description", "Lists for sale residences.");
        c.get(String.valueOf(path) + "list.SubCommands.sell.Info", Arrays.asList("&eUsage: &6/res market list sell"));
        c.get(String.valueOf(path) + "sell.Description", "Sell a residence");
        c.get(String.valueOf(path) + "sell.Info", Arrays.asList("&eUsage: &6/res market sell [residence] [amount]", "Puts a residence for sale for [amount] of money.", "Another player can buy the residence with /res market buy"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "sell"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "sign.Description", "Set market sign");
        c.get(String.valueOf(path) + "sign.Info", Arrays.asList("&eUsage: &6/res market sign [residence]", "Sets market sign you are looking at."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "sign"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "buy.Description", "Buy a residence");
        c.get(String.valueOf(path) + "buy.Info", Arrays.asList("&eUsage: &6/res market buy [residence]", "Buys a Residence if its for sale."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "buy"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "unsell.Description", "Stops selling a residence");
        c.get(String.valueOf(path) + "unsell.Info", Arrays.asList("&eUsage: &6/res market unsell [residence]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "unsell"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "rent.Description", "ent a residence");
        c.get(String.valueOf(path) + "rent.Info", Arrays.asList("&eUsage: &6/res market rent [residence] <AutoPay>", "Rents a residence.  Autorenew can be either true or false.  If true, the residence will be automatically re-rented upon expire if the residence owner has allowed it."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "rent"), Arrays.asList("[cresidence]", "true%%false"));
        c.get(String.valueOf(path) + "rentable.Description", "Make a residence rentable.");
        c.get(String.valueOf(path) + "rentable.Info", Arrays.asList("&eUsage: &6/res market rentable [residence] [cost] [days] <AllowRenewing> <StayInMarket> <AllowAutoPay>", "Makes a residence rentable for [cost] money for every [days] number of days.", "If <AllowRenewing> is true, the residence will be able to be rented again before rent expires.", "If <StayInMarket> is true, the residence will stay in market after last renter will be removed.", "If <AllowAutoPay> is true, money for rent will be automaticaly taken from players balance if he chosen that option when renting"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "rentable"), Arrays.asList("[residence]", "1000", "7", "true", "true", "true"));
        c.get(String.valueOf(path) + "autopay.Description", "Sets residence AutoPay to given value");
        c.get(String.valueOf(path) + "autopay.Info", Arrays.asList("&eUsage: &6/res market autopay <residence> [true/false]"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "autopay"), Arrays.asList("[residence]%%true%%false", "true%%false"));
        c.get(String.valueOf(path) + "payrent.Description", "Pays rent for defined residence");
        c.get(String.valueOf(path) + "payrent.Info", Arrays.asList("&eUsage: &6/res market payrent <residence>"));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "payrent"), Arrays.asList("[residence]"));
        c.get(String.valueOf(path) + "confirm.Description", "Confirms residence unrent/release action");
        c.get(String.valueOf(path) + "confirm.Info", Arrays.asList("&eUsage: &6/res market confirm"));
        c.get(String.valueOf(path) + "release.Description", "Remove a residence from rent or rentable.");
        c.get(String.valueOf(path) + "release.Info", Arrays.asList("&eUsage: &6/res market release [residence]", "If you are the renter, this command releases the rent on the house for you.", "If you are the owner, this command makes the residence not for rent anymore."));
        Residence.getLocaleManager().CommandTab.put(Arrays.asList(this.getClass().getSimpleName(), "release"), Arrays.asList("[residence]"));
    }
}

