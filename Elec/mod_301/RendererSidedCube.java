/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 *  org.lwjgl.opengl.GL11
 */
package mod_301;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public abstract class RendererSidedCube
extends TileEntitySpecialRenderer {
    public abstract ResourceLocation getTexture(int var1);

    public void render_cube(double maxX, double maxY, double maxZ) {
        Tessellator t = Tessellator.field_78398_a;
        Vec3 v1 = RendererSidedCube.newV3((- maxX) / 2.0, 0.0, (- maxZ) / 2.0);
        Vec3 v2 = RendererSidedCube.newV3((- maxX) / 2.0, 0.0, maxZ / 2.0);
        Vec3 v3 = RendererSidedCube.newV3((- maxX) / 2.0, maxY, maxZ / 2.0);
        Vec3 v4 = RendererSidedCube.newV3((- maxX) / 2.0, maxY, (- maxZ) / 2.0);
        Vec3 v5 = RendererSidedCube.newV3(maxX / 2.0, 0.0, (- maxZ) / 2.0);
        Vec3 v6 = RendererSidedCube.newV3(maxX / 2.0, 0.0, maxZ / 2.0);
        Vec3 v7 = RendererSidedCube.newV3(maxX / 2.0, maxY, maxZ / 2.0);
        Vec3 v8 = RendererSidedCube.newV3(maxX / 2.0, maxY, (- maxZ) / 2.0);
        GL11.glPushMatrix();
        this.func_147499_a(this.getTexture(4));
        t.func_78382_b();
        t.func_78375_b(-1.0f, 0.0f, 0.0f);
        RendererSidedCube.addVertex(v1, 0.0, 1.0);
        RendererSidedCube.addVertex(v2, 1.0, 1.0);
        RendererSidedCube.addVertex(v3, 1.0, 0.0);
        RendererSidedCube.addVertex(v4, 0.0, 0.0);
        t.func_78381_a();
        this.func_147499_a(this.getTexture(5));
        t.func_78382_b();
        t.func_78375_b(1.0f, 0.0f, 0.0f);
        RendererSidedCube.addVertex(v8, 1.0, 0.0);
        RendererSidedCube.addVertex(v7, 0.0, 0.0);
        RendererSidedCube.addVertex(v6, 0.0, 1.0);
        RendererSidedCube.addVertex(v5, 1.0, 1.0);
        t.func_78381_a();
        this.func_147499_a(this.getTexture(2));
        t.func_78382_b();
        t.func_78375_b(0.0f, 0.0f, -1.0f);
        RendererSidedCube.addVertex(v4, 1.0, 0.0);
        RendererSidedCube.addVertex(v8, 0.0, 0.0);
        RendererSidedCube.addVertex(v5, 0.0, 1.0);
        RendererSidedCube.addVertex(v1, 1.0, 1.0);
        t.func_78381_a();
        this.func_147499_a(this.getTexture(3));
        t.func_78382_b();
        t.func_78375_b(0.0f, 0.0f, 1.0f);
        RendererSidedCube.addVertex(v3, 0.0, 0.0);
        RendererSidedCube.addVertex(v2, 0.0, 1.0);
        RendererSidedCube.addVertex(v6, 1.0, 1.0);
        RendererSidedCube.addVertex(v7, 1.0, 0.0);
        t.func_78381_a();
        this.func_147499_a(this.getTexture(1));
        t.func_78382_b();
        t.func_78375_b(0.0f, 1.0f, 0.0f);
        RendererSidedCube.addVertex(v3, 0.0, 0.0);
        RendererSidedCube.addVertex(v7, 1.0, 0.0);
        RendererSidedCube.addVertex(v8, 1.0, 1.0);
        RendererSidedCube.addVertex(v4, 0.0, 1.0);
        t.func_78381_a();
        this.func_147499_a(this.getTexture(0));
        t.func_78382_b();
        t.func_78375_b(0.0f, -1.0f, 0.0f);
        RendererSidedCube.addVertex(v1, 0.0, 1.0);
        RendererSidedCube.addVertex(v5, 1.0, 1.0);
        RendererSidedCube.addVertex(v6, 1.0, 0.0);
        RendererSidedCube.addVertex(v2, 0.0, 0.0);
        t.func_78381_a();
        GL11.glPopMatrix();
    }

    public static Vec3 newV3(double x, double y, double z) {
        return Vec3.func_72443_a((double)x, (double)y, (double)z);
    }

    public static void addVertex(Vec3 vec3, double texU, double texV) {
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78374_a(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c, texU, texV);
    }
}

