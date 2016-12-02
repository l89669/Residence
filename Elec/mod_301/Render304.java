/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package mod_301;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class Render304
extends TileEntitySpecialRenderer {
    int facing;

    public void func_147500_a(TileEntity tileEntity, double x, double y, double z, float f) {
        this.facing = tileEntity.func_145831_w().func_72805_g(tileEntity.field_145851_c, tileEntity.field_145848_d, tileEntity.field_145849_e);
        double scale = 0.0625;
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)(y - 1.5), (double)z);
        GL11.glTranslatef((float)0.5f, (float)0.0f, (float)0.5f);
        GL11.glRotatef((float)((float)this.getDirection() * 270.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glScaled((double)scale, (double)scale, (double)scale);
        this.func_147499_a(new ResourceLocation("mod_301", "textures/render/301.png"));
        ModelNew.m.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glPopMatrix();
    }

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

    public static class ModelNew
    extends ModelBase {
        static ModelNew m = new ModelNew();
        ModelRenderer Shape00;
        ModelRenderer Shape1A;
        ModelRenderer Shape2A;
        ModelRenderer Shape3A;
        ModelRenderer Shape4A;
        ModelRenderer Shape5A;
        ModelRenderer Shape6A;
        ModelRenderer Shape7A;
        ModelRenderer Shape8A;
        ModelRenderer Shape1B;
        ModelRenderer Shape2B;
        ModelRenderer Shape3B;
        ModelRenderer Shape4B;
        ModelRenderer Shape6B;
        ModelRenderer Shape7B;
        ModelRenderer Shape8B;
        ModelRenderer Shape5B;

        public ModelNew() {
            this.field_78090_t = 128;
            this.field_78089_u = 64;
            this.Shape00 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape00.func_78789_a(0.0f, 0.0f, 0.0f, 2, 24, 2);
            this.Shape00.func_78793_a(-1.0f, 24.0f, -1.0f);
            this.Shape00.func_78787_b(128, 64);
            this.Shape00.field_78809_i = true;
            this.setRotation(this.Shape00, 0.0f, 0.0f, 0.0f);
            this.Shape1A = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape1A.func_78789_a(0.0f, 0.0f, 0.0f, 30, 1, 1);
            this.Shape1A.func_78793_a(-42.06667f, 33.0f, 0.0f);
            this.Shape1A.func_78787_b(128, 64);
            this.Shape1A.field_78809_i = true;
            this.setRotation(this.Shape1A, 0.0f, 0.0f, 0.0f);
            this.Shape2A = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape2A.func_78789_a(0.0f, 0.0f, 0.0f, 32, 1, 1);
            this.Shape2A.func_78793_a(-33.0f, 42.5f, 0.0f);
            this.Shape2A.func_78787_b(128, 64);
            this.Shape2A.field_78809_i = true;
            this.setRotation(this.Shape2A, 0.0f, 0.0f, 0.0f);
            this.Shape3A = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape3A.func_78789_a(0.0f, 0.0f, 0.0f, 30, 1, 1);
            this.Shape3A.func_78793_a(0.0f, 25.0f, 0.0f);
            this.Shape3A.func_78787_b(128, 64);
            this.Shape3A.field_78809_i = true;
            this.setRotation(this.Shape3A, 0.0f, 0.0f, 2.495821f);
            this.Shape4A = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape4A.func_78789_a(0.0f, 0.0f, 0.0f, 17, 1, 1);
            this.Shape4A.func_78793_a(-26.0f, 43.3f, 0.0f);
            this.Shape4A.func_78787_b(128, 64);
            this.Shape4A.field_78809_i = true;
            this.setRotation(this.Shape4A, 0.0f, 0.0f, -2.565634f);
            this.Shape5A = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape5A.func_78789_a(0.0f, 0.0f, 0.0f, 10, 1, 1);
            this.Shape5A.func_78793_a(-22.06667f, 34.0f, 0.0f);
            this.Shape5A.func_78787_b(128, 64);
            this.Shape5A.field_78809_i = true;
            this.setRotation(this.Shape5A, 0.0f, 0.0f, -2.949606f);
            this.Shape6A = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape6A.func_78789_a(0.0f, 0.0f, 0.0f, 8, 1, 1);
            this.Shape6A.func_78793_a(-11.8f, 43.3f, 0.0f);
            this.Shape6A.func_78787_b(128, 64);
            this.Shape6A.field_78809_i = true;
            this.setRotation(this.Shape6A, 0.0f, 0.0f, -2.234021f);
            this.Shape7A = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape7A.func_78789_a(0.0f, 0.0f, 0.0f, 8, 3, 3);
            this.Shape7A.func_78793_a(-2.0f, 27.5f, -1.0f);
            this.Shape7A.func_78787_b(128, 64);
            this.Shape7A.field_78809_i = true;
            this.setRotation(this.Shape7A, 0.0f, 0.0f, 2.495821f);
            this.Shape8A = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape8A.func_78789_a(0.0f, 0.0f, 0.0f, 8, 3, 3);
            this.Shape8A.func_78793_a(-10.0f, 41.0f, -1.0f);
            this.Shape8A.func_78787_b(128, 64);
            this.Shape8A.field_78809_i = true;
            this.setRotation(this.Shape8A, 0.0f, 0.0f, 0.0f);
            this.Shape1B = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape1B.func_78789_a(0.0f, 0.0f, 0.0f, 30, 1, 1);
            this.Shape1B.func_78793_a(12.0f, 33.0f, 0.0f);
            this.Shape1B.func_78787_b(128, 64);
            this.Shape1B.field_78809_i = true;
            this.setRotation(this.Shape1B, 0.0f, 0.0f, 0.0f);
            this.Shape2B = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape2B.func_78789_a(0.0f, 0.0f, 0.0f, 32, 1, 1);
            this.Shape2B.func_78793_a(1.0f, 42.5f, 0.0f);
            this.Shape2B.func_78787_b(128, 64);
            this.Shape2B.field_78809_i = true;
            this.setRotation(this.Shape2B, 0.0f, 0.0f, 0.0f);
            this.Shape3B = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape3B.func_78789_a(0.0f, 0.0f, 0.0f, 30, 1, 1);
            this.Shape3B.func_78793_a(1.0f, 24.4f, 0.0f);
            this.Shape3B.func_78787_b(128, 64);
            this.Shape3B.field_78809_i = true;
            this.setRotation(this.Shape3B, 0.0f, 0.0f, 0.6457718f);
            this.Shape4B = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape4B.func_78789_a(0.0f, 0.0f, 0.0f, 17, 1, 1);
            this.Shape4B.func_78793_a(25.0f, 42.5f, 0.0f);
            this.Shape4B.func_78787_b(128, 64);
            this.Shape4B.field_78809_i = true;
            this.setRotation(this.Shape4B, 0.0f, 0.0f, -0.5759587f);
            this.Shape6B = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape6B.func_78789_a(0.0f, 0.0f, 0.0f, 8, 1, 1);
            this.Shape6B.func_78793_a(11.0f, 43.0f, 0.0f);
            this.Shape6B.func_78787_b(128, 64);
            this.Shape6B.field_78809_i = true;
            this.setRotation(this.Shape6B, 0.0f, 0.0f, -0.9075712f);
            this.Shape7B = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape7B.func_78789_a(0.0f, 0.0f, 0.0f, 8, 3, 3);
            this.Shape7B.func_78793_a(4.0f, 25.0f, -1.0f);
            this.Shape7B.func_78787_b(128, 64);
            this.Shape7B.field_78809_i = true;
            this.setRotation(this.Shape7B, 0.0f, 0.0f, 0.6457718f);
            this.Shape8B = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape8B.func_78789_a(0.0f, 0.0f, 0.0f, 8, 3, 3);
            this.Shape8B.func_78793_a(2.0f, 41.0f, -1.0f);
            this.Shape8B.func_78787_b(128, 64);
            this.Shape8B.field_78809_i = true;
            this.setRotation(this.Shape8B, 0.0f, 0.0f, 0.0f);
            this.Shape5B = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape5B.func_78789_a(0.0f, 0.0f, 0.0f, 10, 1, 1);
            this.Shape5B.func_78793_a(22.0f, 33.0f, 0.0f);
            this.Shape5B.func_78787_b(128, 64);
            this.Shape5B.field_78809_i = true;
            this.setRotation(this.Shape5B, 0.0f, 0.0f, -0.1919862f);
        }

        public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
            super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
            this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
            this.Shape00.func_78785_a(f5);
            this.Shape1A.func_78785_a(f5);
            this.Shape2A.func_78785_a(f5);
            this.Shape3A.func_78785_a(f5);
            this.Shape4A.func_78785_a(f5);
            this.Shape5A.func_78785_a(f5);
            this.Shape6A.func_78785_a(f5);
            this.Shape7A.func_78785_a(f5);
            this.Shape8A.func_78785_a(f5);
            this.Shape1B.func_78785_a(f5);
            this.Shape2B.func_78785_a(f5);
            this.Shape3B.func_78785_a(f5);
            this.Shape4B.func_78785_a(f5);
            this.Shape6B.func_78785_a(f5);
            this.Shape7B.func_78785_a(f5);
            this.Shape8B.func_78785_a(f5);
            this.Shape5B.func_78785_a(f5);
        }

        private void setRotation(ModelRenderer model, float x, float y, float z) {
            model.field_78795_f = x;
            model.field_78796_g = y;
            model.field_78808_h = z;
        }
    }

}

