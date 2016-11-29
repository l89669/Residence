/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 */
package com.bekvon.bukkit.residence.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

public class CuboidArea {
    protected Location highPoints;
    protected Location lowPoints;
    protected String worldName;

    public CuboidArea() {
    }

    public CuboidArea(Location startLoc, Location endLoc) {
        int highy;
        int lowy;
        int highx;
        int lowx;
        int lowz;
        int highz;
        if (startLoc.getBlockX() > endLoc.getBlockX()) {
            highx = startLoc.getBlockX();
            lowx = endLoc.getBlockX();
        } else {
            highx = endLoc.getBlockX();
            lowx = startLoc.getBlockX();
        }
        if (startLoc.getBlockY() > endLoc.getBlockY()) {
            highy = startLoc.getBlockY();
            lowy = endLoc.getBlockY();
        } else {
            highy = endLoc.getBlockY();
            lowy = startLoc.getBlockY();
        }
        if (startLoc.getBlockZ() > endLoc.getBlockZ()) {
            highz = startLoc.getBlockZ();
            lowz = endLoc.getBlockZ();
        } else {
            highz = endLoc.getBlockZ();
            lowz = startLoc.getBlockZ();
        }
        this.highPoints = new Location(startLoc.getWorld(), (double)highx, (double)highy, (double)highz);
        this.lowPoints = new Location(startLoc.getWorld(), (double)lowx, (double)lowy, (double)lowz);
        this.worldName = startLoc.getWorld().getName();
    }

    public boolean isAreaWithinArea(CuboidArea area2) {
        if (this.containsLoc(area2.highPoints) && this.containsLoc(area2.lowPoints)) {
            return true;
        }
        return false;
    }

    public boolean containsLoc(Location loc) {
        if (loc == null) {
            return false;
        }
        if (!loc.getWorld().getName().equals(this.worldName)) {
            return false;
        }
        if (this.lowPoints.getBlockX() > loc.getBlockX()) {
            return false;
        }
        if (this.highPoints.getBlockX() < loc.getBlockX()) {
            return false;
        }
        if (this.lowPoints.getBlockZ() > loc.getBlockZ()) {
            return false;
        }
        if (this.highPoints.getBlockZ() < loc.getBlockZ()) {
            return false;
        }
        if (this.lowPoints.getBlockY() > loc.getBlockY()) {
            return false;
        }
        if (this.highPoints.getBlockY() < loc.getBlockY()) {
            return false;
        }
        return true;
    }

    public boolean checkCollision(CuboidArea area2) {
        if (!area2.getWorld().equals((Object)this.getWorld())) {
            return false;
        }
        if (area2.containsLoc(this.lowPoints) || area2.containsLoc(this.highPoints) || this.containsLoc(area2.highPoints) || this.containsLoc(area2.lowPoints)) {
            return true;
        }
        return CuboidArea.advCuboidCheckCollision(this.highPoints, this.lowPoints, area2.highPoints, area2.lowPoints);
    }

    private static boolean advCuboidCheckCollision(Location A1High, Location A1Low, Location A2High, Location A2Low) {
        int A1HX = A1High.getBlockX();
        int A1LX = A1Low.getBlockX();
        int A1HY = A1High.getBlockY();
        int A1LY = A1Low.getBlockY();
        int A1HZ = A1High.getBlockZ();
        int A1LZ = A1Low.getBlockZ();
        int A2HX = A2High.getBlockX();
        int A2LX = A2Low.getBlockX();
        int A2HY = A2High.getBlockY();
        int A2LY = A2Low.getBlockY();
        int A2HZ = A2High.getBlockZ();
        int A2LZ = A2Low.getBlockZ();
        if ((A1HX >= A2LX && A1HX <= A2HX || A1LX >= A2LX && A1LX <= A2HX || A2HX >= A1LX && A2HX <= A1HX || A2LX >= A1LX && A2LX <= A1HX) && (A1HY >= A2LY && A1HY <= A2HY || A1LY >= A2LY && A1LY <= A2HY || A2HY >= A1LY && A2HY <= A1HY || A2LY >= A1LY && A2LY <= A1HY) && (A1HZ >= A2LZ && A1HZ <= A2HZ || A1LZ >= A2LZ && A1LZ <= A2HZ || A2HZ >= A1LZ && A2HZ <= A1HZ || A2LZ >= A1LZ && A2LZ <= A1HZ)) {
            return true;
        }
        return false;
    }

    public long getSize() {
        int xsize = this.highPoints.getBlockX() - this.lowPoints.getBlockX() + 1;
        int zsize = this.highPoints.getBlockZ() - this.lowPoints.getBlockZ() + 1;
        if (!Residence.getConfigManager().isNoCostForYBlocks()) {
            int ysize = this.highPoints.getBlockY() - this.lowPoints.getBlockY() + 1;
            return xsize * ysize * zsize;
        }
        return xsize * zsize;
    }

    public int getXSize() {
        return this.highPoints.getBlockX() - this.lowPoints.getBlockX() + 1;
    }

    public int getYSize() {
        return this.highPoints.getBlockY() - this.lowPoints.getBlockY() + 1;
    }

    public int getZSize() {
        return this.highPoints.getBlockZ() - this.lowPoints.getBlockZ() + 1;
    }

    public Location getHighLoc() {
        return this.highPoints;
    }

    public Location getLowLoc() {
        return this.lowPoints;
    }

    public World getWorld() {
        return this.highPoints.getWorld();
    }

    public void save(DataOutputStream out) throws IOException {
        out.writeUTF(this.highPoints.getWorld().getName());
        out.writeInt(this.highPoints.getBlockX());
        out.writeInt(this.highPoints.getBlockY());
        out.writeInt(this.highPoints.getBlockZ());
        out.writeInt(this.lowPoints.getBlockX());
        out.writeInt(this.lowPoints.getBlockY());
        out.writeInt(this.lowPoints.getBlockZ());
    }

    public static CuboidArea load(DataInputStream in) throws IOException {
        CuboidArea newArea = new CuboidArea();
        Server server2 = Residence.getServ();
        World world = server2.getWorld(in.readUTF());
        int highx = in.readInt();
        int highy = in.readInt();
        int highz = in.readInt();
        int lowx = in.readInt();
        int lowy = in.readInt();
        int lowz = in.readInt();
        newArea.highPoints = new Location(world, (double)highx, (double)highy, (double)highz);
        newArea.lowPoints = new Location(world, (double)lowx, (double)lowy, (double)lowz);
        newArea.worldName = world.getName();
        return newArea;
    }

    public Map<String, Object> save() {
        LinkedHashMap<String, Object> root = new LinkedHashMap<String, Object>();
        root.put("X1", this.highPoints.getBlockX());
        root.put("Y1", this.highPoints.getBlockY());
        root.put("Z1", this.highPoints.getBlockZ());
        root.put("X2", this.lowPoints.getBlockX());
        root.put("Y2", this.lowPoints.getBlockY());
        root.put("Z2", this.lowPoints.getBlockZ());
        return root;
    }

    public static CuboidArea load(Map<String, Object> root, World world) throws Exception {
        if (root == null) {
            throw new Exception("Invalid residence physical location...");
        }
        CuboidArea newArea = new CuboidArea();
        int x1 = (Integer)root.get("X1");
        int y1 = (Integer)root.get("Y1");
        int z1 = (Integer)root.get("Z1");
        int x2 = (Integer)root.get("X2");
        int y2 = (Integer)root.get("Y2");
        int z2 = (Integer)root.get("Z2");
        newArea.highPoints = new Location(world, (double)x1, (double)y1, (double)z1);
        newArea.lowPoints = new Location(world, (double)x2, (double)y2, (double)z2);
        newArea.worldName = world.getName();
        return newArea;
    }

    public List<ResidenceManager.ChunkRef> getChunks() {
        ArrayList<ResidenceManager.ChunkRef> chunks = new ArrayList<ResidenceManager.ChunkRef>();
        Location high = this.highPoints;
        Location low = this.lowPoints;
        int lowX = ResidenceManager.ChunkRef.getChunkCoord(low.getBlockX());
        int lowZ = ResidenceManager.ChunkRef.getChunkCoord(low.getBlockZ());
        int highX = ResidenceManager.ChunkRef.getChunkCoord(high.getBlockX());
        int highZ = ResidenceManager.ChunkRef.getChunkCoord(high.getBlockZ());
        int x = lowX;
        while (x <= highX) {
            int z = lowZ;
            while (z <= highZ) {
                chunks.add(new ResidenceManager.ChunkRef(x, z));
                ++z;
            }
            ++x;
        }
        return chunks;
    }
}

