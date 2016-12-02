/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package mod_301;

import mod_301.RendererSidedCube;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class Render301
extends RendererSidedCube {
    public int facing = 3;

    int getDirection() {
        switch (this.facing) {
            case 2: {
                return 0;
            }
            case 5: {
                return 1;
            }
            case 3: {
                return 2;
            }
        }
        return 3;
    }

    @Override
    public ResourceLocation getTexture(int side) {
        return new ResourceLocation("mod_301", "textures/render/301.png");
    }

    public void func_147500_a(TileEntity tileEntity, double x, double y, double z, float f) {
        this.facing = tileEntity.func_145831_w().func_72805_g(tileEntity.field_145851_c, tileEntity.field_145848_d, tileEntity.field_145849_e);
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)y, (double)z);
        GL11.glTranslatef((float)0.5f, (float)0.0f, (float)0.5f);
        GL11.glRotatef((float)((float)this.getDirection() * 270.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        this.render_cube(0.25, 2.0, 0.25);
        GL11.glPushMatrix();
        GL11.glTranslated((double)0.0, (double)1.0, (double)1.2);
        this.render_cube(0.1, 0.9, 0.1);
        GL11.glTranslated((double)0.0, (double)0.175, (double)0.0);
        this.render_cube(0.25, 0.55, 0.25);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)0.0, (double)1.7, (double)0.0);
        GL11.glRotatef((float)270.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        this.render_cube(0.15, 3.0, 0.15);
        GL11.glTranslated((double)0.0, (double)0.4, (double)0.0);
        this.render_cube(0.25, 0.6, 0.25);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)0.0, (double)1.9, (double)0.0);
        GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        this.render_cube(0.15, 1.4, 0.15);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)0.0, (double)0.85, (double)-1.3);
        GL11.glRotatef((float)270.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        this.render_cube(0.15, 3.6, 0.15);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)0.0, (double)0.2, (double)0.0);
        GL11.glRotatef((float)297.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        this.render_cube(0.15, 3.3, 0.15);
        GL11.glTranslated((double)0.0, (double)0.4, (double)0.0);
        this.render_cube(0.25, 0.6, 0.25);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)0.0, (double)1.25, (double)-2.0);
        GL11.glRotatef((float)63.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        this.render_cube(0.15, 1.0, 0.15);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)0.0, (double)0.85, (double)-4.4);
        GL11.glRotatef((float)60.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        this.render_cube(0.15, 1.7, 0.15);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}

