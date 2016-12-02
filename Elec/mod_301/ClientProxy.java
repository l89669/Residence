/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  cpw.mods.fml.client.registry.ClientRegistry
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 */
package mod_301;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import mod_301.Block301;
import mod_301.Block302;
import mod_301.Block303;
import mod_301.Block304;
import mod_301.Render301;
import mod_301.Render302;
import mod_301.Render303;
import mod_301.Render304;
import mod_301.mod_301;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy
extends mod_301 {
    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().field_71441_e;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public void registerTileEntitySpecialRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(Block301.Tile301.class, (TileEntitySpecialRenderer)new Render301());
        ClientRegistry.bindTileEntitySpecialRenderer(Block302.Tile302.class, (TileEntitySpecialRenderer)new Render302());
        ClientRegistry.bindTileEntitySpecialRenderer(Block303.Tile303.class, (TileEntitySpecialRenderer)new Render303());
        ClientRegistry.bindTileEntitySpecialRenderer(Block304.Tile304.class, (TileEntitySpecialRenderer)new Render304());
    }
}

