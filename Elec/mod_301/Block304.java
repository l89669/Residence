/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.World
 */
package mod_301;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_301.Block301;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Block304
extends Block301 {
    public Block304() {
        this.func_149663_c("block_304");
    }

    @Override
    public TileEntity func_149915_a(World var1, int meta) {
        return new Tile304();
    }

    public static class Tile304
    extends TileEntity {
        @SideOnly(value=Side.CLIENT)
        public double func_145833_n() {
            return 100000.0;
        }
    }

}

