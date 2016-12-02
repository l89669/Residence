/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.Mod
 *  cpw.mods.fml.common.Mod$EventHandler
 *  cpw.mods.fml.common.Mod$Instance
 *  cpw.mods.fml.common.SidedProxy
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.network.IGuiHandler
 *  cpw.mods.fml.common.network.NetworkRegistry
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 */
package mod_301;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import mod_301.Block301;
import mod_301.Block302;
import mod_301.Block303;
import mod_301.Block304;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@Mod(modid="mod_301", name="mod_301", version="0.1")
public class mod_301
implements IGuiHandler {
    @SidedProxy(clientSide="mod_301.ClientProxy", serverSide="mod_301.mod_301")
    public static mod_301 proxy;
    @Mod.Instance(value="mod_301")
    public static mod_301 instance;

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void registerTileEntitySpecialRenderer() {
    }

    public World getClientWorld() {
        return null;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerBlock((Block)new Block301(), (String)"301");
        GameRegistry.registerBlock((Block)new Block302(), (String)"302");
        GameRegistry.registerBlock((Block)new Block303(), (String)"303");
        GameRegistry.registerBlock((Block)new Block304(), (String)"304");
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)instance, (IGuiHandler)proxy);
        proxy.registerTileEntitySpecialRenderer();
        GameRegistry.registerTileEntity(Block301.Tile301.class, (String)"301");
        GameRegistry.registerTileEntity(Block302.Tile302.class, (String)"302");
        GameRegistry.registerTileEntity(Block303.Tile303.class, (String)"303");
        GameRegistry.registerTileEntity(Block304.Tile304.class, (String)"304");
    }

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}

