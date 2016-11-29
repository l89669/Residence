/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Sign
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.bekvon.bukkit.residence.shopStuff;

import com.bekvon.bukkit.residence.CommentedYamlConfiguration;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.shopStuff.Board;
import com.bekvon.bukkit.residence.shopStuff.ShopVote;
import com.bekvon.bukkit.residence.shopStuff.Vote;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ShopSignUtil {
    List<Board> AllBoards = new ArrayList<Board>();
    private Residence plugin;

    public ShopSignUtil(Residence plugin) {
        this.plugin = plugin;
    }

    public void setAllSigns(List<Board> AllBoards) {
        this.AllBoards = AllBoards;
    }

    public List<Board> GetAllBoards() {
        return this.AllBoards;
    }

    public void removeBoard(Board Board2) {
        this.AllBoards.remove(Board2);
    }

    public void addBoard(Board Board2) {
        this.AllBoards.add(Board2);
    }

    public boolean exist(Board board) {
        List<Location> loc2 = board.GetLocations();
        for (Board one : this.AllBoards) {
            List<Location> loc1 = one.GetLocations();
            for (Location oneL : loc1) {
                if (!loc2.contains((Object)oneL)) continue;
                return true;
            }
        }
        return false;
    }

    public void LoadShopVotes() {
        File file = new File(this.plugin.getDataFolder(), "ShopVotes.yml");
        YamlConfiguration f = YamlConfiguration.loadConfiguration((File)file);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (!f.isConfigurationSection("ShopVotes")) {
            return;
        }
        ConfigurationSection ConfCategory = f.getConfigurationSection("ShopVotes");
        ArrayList categoriesList = new ArrayList(ConfCategory.getKeys(false));
        if (categoriesList.size() == 0) {
            return;
        }
        for (String category : categoriesList) {
            List List2 = ConfCategory.getStringList(category);
            ArrayList<ShopVote> VoteList = new ArrayList<ShopVote>();
            for (String oneEntry : List2) {
                if (!oneEntry.contains("%")) continue;
                String name = oneEntry.split("%")[0];
                UUID uuid = null;
                if (name.contains(":")) {
                    try {
                        uuid = UUID.fromString(name.split(":")[1]);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    name = name.split(":")[0];
                }
                int vote = -1;
                try {
                    String voteString = oneEntry.split("%")[1];
                    if (voteString.contains("!")) {
                        voteString = oneEntry.split("%")[1].split("!")[0];
                    }
                    vote = Integer.parseInt(voteString);
                }
                catch (Exception ex) {
                    continue;
                }
                if (vote < 0) {
                    vote = 0;
                } else if (vote > 10) {
                    vote = 10;
                }
                long time = 0;
                if (oneEntry.contains("!")) {
                    try {
                        time = Long.parseLong(oneEntry.split("!")[1]);
                    }
                    catch (Exception ex) {
                        time = System.currentTimeMillis();
                    }
                }
                VoteList.add(new ShopVote(name, uuid, vote, time));
            }
            ClaimedResidence res = Residence.getResidenceManager().getByName(category.replace("_", "."));
            if (res == null) continue;
            res.clearShopVotes();
            res.addShopVote(VoteList);
        }
    }

    public void saveShopVotes() {
        File f = new File(this.plugin.getDataFolder(), "ShopVotes.yml");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration((File)f);
        CommentedYamlConfiguration writer = new CommentedYamlConfiguration();
        conf.options().copyDefaults(true);
        writer.addComment("ShopVotes", "DO NOT EDIT THIS FILE BY HAND!");
        if (!conf.isConfigurationSection("ShopVotes")) {
            conf.createSection("ShopVotes");
        }
        for (ClaimedResidence res : Residence.getResidenceManager().getShops()) {
            if (res == null || res.GetShopVotes().isEmpty()) continue;
            String path = "ShopVotes." + res.getName().replace(".", "_");
            ArrayList<String> list2 = new ArrayList<String>();
            for (ShopVote oneVote : res.GetShopVotes()) {
                list2.add(String.valueOf(oneVote.getName()) + ":" + oneVote.getUuid().toString() + "%" + oneVote.getVote() + "!" + oneVote.getTime());
            }
            writer.set(path, list2);
        }
        try {
            writer.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vote getAverageVote(String resName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(resName);
        return this.getAverageVote(res);
    }

    public Vote getAverageVote(ClaimedResidence res) {
        if (res == null || res.GetShopVotes().isEmpty()) {
            return new Vote(Residence.getConfigManager().getVoteRangeTo() / 2, 0);
        }
        List<ShopVote> votes = res.GetShopVotes();
        double total = 0.0;
        for (ShopVote oneVote : votes) {
            total += (double)oneVote.getVote();
        }
        double vote = (double)((int)(total / (double)votes.size() * 100.0)) / 100.0;
        return new Vote(vote, votes.size());
    }

    public int getLikes(String resName) {
        ClaimedResidence res = Residence.getResidenceManager().getByName(resName);
        return this.getLikes(res);
    }

    public int getLikes(ClaimedResidence res) {
        if (res == null || res.GetShopVotes().isEmpty()) {
            return 0;
        }
        List<ShopVote> votes = res.GetShopVotes();
        int likes = 0;
        for (ShopVote oneVote : votes) {
            if (oneVote.getVote() < Residence.getConfigManager().getVoteRangeTo() / 2) continue;
            ++likes;
        }
        return likes;
    }

    public Map<String, Double> getSortedShopList() {
        Map allvotes = new HashMap<String, Double>();
        List<ClaimedResidence> shops = Residence.getResidenceManager().getShops();
        for (ClaimedResidence one : shops) {
            if (Residence.getConfigManager().isOnlyLike()) {
                allvotes.put(one.getName(), Double.valueOf(this.getLikes(one)));
                continue;
            }
            allvotes.put(one.getName(), this.getAverageVote(one).getVote());
        }
        allvotes = ShopSignUtil.sortByComparator(allvotes);
        return allvotes;
    }

    private static Map<String, Double> sortByComparator(Map<String, Double> allvotes) {
        LinkedList<Map.Entry<String, Double>> list2 = new LinkedList<Map.Entry<String, Double>>(allvotes.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Double>>(){

            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list2) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public void LoadSigns() {
        this.GetAllBoards().clear();
        File file = new File(this.plugin.getDataFolder(), "ShopSigns.yml");
        YamlConfiguration f = YamlConfiguration.loadConfiguration((File)file);
        if (!f.isConfigurationSection("ShopSigns")) {
            return;
        }
        ConfigurationSection ConfCategory = f.getConfigurationSection("ShopSigns");
        ArrayList categoriesList = new ArrayList(ConfCategory.getKeys(false));
        if (categoriesList.size() == 0) {
            return;
        }
        for (String category : categoriesList) {
            ConfigurationSection NameSection = ConfCategory.getConfigurationSection(category);
            Board newTemp = new Board();
            newTemp.setStartPlace(NameSection.getInt("StartPlace"));
            World w = Bukkit.getWorld((String)NameSection.getString("World"));
            if (w == null) continue;
            Location loc1 = new Location(w, (double)NameSection.getInt("TX"), (double)NameSection.getInt("TY"), (double)NameSection.getInt("TZ"));
            Location loc2 = new Location(w, (double)NameSection.getInt("BX"), (double)NameSection.getInt("BY"), (double)NameSection.getInt("BZ"));
            newTemp.setTopLoc(loc1);
            newTemp.setBottomLoc(loc2);
            this.addBoard(newTemp);
        }
    }

    public void saveSigns() {
        File f = new File(this.plugin.getDataFolder(), "ShopSigns.yml");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration((File)f);
        CommentedYamlConfiguration writer = new CommentedYamlConfiguration();
        conf.options().copyDefaults(true);
        writer.addComment("ShopSigns", "DO NOT EDIT THIS FILE BY HAND!");
        if (!conf.isConfigurationSection("ShopSigns")) {
            conf.createSection("ShopSigns");
        }
        int cat = 0;
        for (Board one : this.GetAllBoards()) {
            String path = "ShopSigns." + ++cat;
            writer.set(String.valueOf(path) + ".StartPlace", (Object)one.GetStartPlace());
            writer.set(String.valueOf(path) + ".World", (Object)one.GetWorld());
            writer.set(String.valueOf(path) + ".TX", (Object)one.getTopLoc().getBlockX());
            writer.set(String.valueOf(path) + ".TY", (Object)one.getTopLoc().getBlockY());
            writer.set(String.valueOf(path) + ".TZ", (Object)one.getTopLoc().getBlockZ());
            writer.set(String.valueOf(path) + ".BX", (Object)one.getBottomLoc().getBlockX());
            writer.set(String.valueOf(path) + ".BY", (Object)one.getBottomLoc().getBlockY());
            writer.set(String.valueOf(path) + ".BZ", (Object)one.getBottomLoc().getBlockZ());
        }
        try {
            writer.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean BoardUpdateDelayed() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                ShopSignUtil.this.BoardUpdate();
            }
        }, 20);
        return true;
    }

    public boolean BoardUpdate() {
        for (Board board : this.GetAllBoards()) {
            board.clearSignLoc();
            List<Location> SignsLocation = board.GetLocations();
            ArrayList<String> ShopNames = new ArrayList<String>(this.getSortedShopList().keySet());
            int Start = board.GetStartPlace();
            for (Location OneSignLoc : SignsLocation) {
                Block block = OneSignLoc.getBlock();
                if (!(block.getState() instanceof Sign)) continue;
                String Shop = "";
                if (ShopNames.size() > Start) {
                    Shop = ShopNames.get(Start);
                }
                ClaimedResidence res = Residence.getResidenceManager().getByName(Shop);
                Sign sign = (Sign)block.getState();
                if (Shop.equalsIgnoreCase("")) {
                    sign.setLine(0, "");
                    sign.setLine(1, "");
                    sign.setLine(2, "");
                    sign.setLine(3, "");
                    sign.update();
                    continue;
                }
                Vote vote = null;
                String votestat = "";
                if (Residence.getResidenceManager().getShops().size() >= Start) {
                    vote = this.getAverageVote(ShopNames.get(Start));
                    votestat = Residence.getConfigManager().isOnlyLike() ? (vote.getAmount() == 0 ? "" : Residence.msg(lm.Shop_ListLiked, this.getLikes(ShopNames.get(Start)))) : (vote.getAmount() == 0 ? "" : Residence.msg(lm.Shop_SignLines_4, vote.getVote(), vote.getAmount()));
                }
                sign.setLine(0, Residence.msg(lm.Shop_SignLines_1, Start + 1));
                sign.setLine(1, Residence.msg(lm.Shop_SignLines_2, res.getName()));
                sign.setLine(2, Residence.msg(lm.Shop_SignLines_3, res.getOwner()));
                sign.setLine(3, votestat);
                sign.update();
                board.addSignLoc(res.getName(), sign.getLocation());
                ++Start;
            }
        }
        return true;
    }

}

