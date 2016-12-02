/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.material.Material
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mod_301;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Block301
extends BlockContainer {
    public Block301() {
        super(Material.field_151576_e);
        this.func_149711_c(2.0f);
        this.func_149752_b(5.0f);
        this.func_149647_a(CreativeTabs.field_78028_d);
        this.func_149663_c("block_301");
    }

    public void func_149689_a(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        world.func_72921_c(x, y, z, Block301.getPlayerSight(player), 3);
    }

    public TileEntity func_149915_a(World var1, int meta) {
        return new Tile301();
    }

    public boolean func_149646_a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return false;
    }

    public boolean func_149662_c() {
        return false;
    }

    public static int getPlayerSight(EntityLivingBase player) {
        int heading = MathHelper.func_76128_c((double)((double)(player.field_70177_z * 4.0f / 360.0f) + 0.5)) & 3;
        int pitch = Math.round(player.field_70125_A);
        if (pitch >= 65) {
            return 1;
        }
        if (pitch <= -65) {
            return 0;
        }
        switch (heading) {
            case 0: {
                return 3;
            }
            case 1: {
                return 4;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 5;
            }
        }
        return 0;
    }

    public static class Tile301
    extends TileEntity {
        @SideOnly(value=Side.CLIENT)
        public double func_145833_n() {
            return 100000.0;
        }
    }

}

