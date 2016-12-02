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

public class Render302
extends TileEntitySpecialRenderer {
    int facing;

    public void func_147500_a(TileEntity tileEntity, double x, double y, double z, float f) {
        this.facing = tileEntity.func_145831_w().func_72805_g(tileEntity.field_145851_c, tileEntity.field_145848_d, tileEntity.field_145849_e);
        double scale = 0.0625;
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)(y - 1.5), (double)z);
        GL11.glTranslatef((float)0.5f, (float)0.0f, (float)0.5f);
        GL11.glRotatef((float)((float)this.getDirection() * 270.0f + 270.0f), (float)0.0f, (float)1.0f, (float)0.0f);
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
        ModelRenderer Shape1;
        ModelRenderer Shape2;
        ModelRenderer Shape3;
        ModelRenderer Shape4;
        ModelRenderer Shape5;
        ModelRenderer Shape6;
        ModelRenderer Shape7;
        ModelRenderer Shape8;
        ModelRenderer Shape9;
        ModelRenderer Shape10;
        ModelRenderer Shape11;
        ModelRenderer Shape12;

        public ModelNew() {
            this.field_78090_t = 128;
            this.field_78089_u = 64;
            this.Shape1 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape1.func_78789_a(0.0f, 0.0f, 0.0f, 4, 24, 4);
            this.Shape1.func_78793_a(-2.0f, 24.0f, -2.0f);
            this.Shape1.func_78787_b(128, 64);
            this.Shape1.field_78809_i = true;
            this.setRotation(this.Shape1, 0.0f, 0.0f, 0.0f);
            this.Shape2 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape2.func_78789_a(0.0f, 0.0f, 0.0f, 31, 1, 1);
            this.Shape2.func_78793_a(-33.0f, 42.5f, 0.0f);
            this.Shape2.func_78787_b(128, 64);
            this.Shape2.field_78809_i = true;
            this.setRotation(this.Shape2, 0.0f, 0.0f, 0.0f);
            this.Shape3 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape3.func_78789_a(0.0f, 0.0f, 0.0f, 29, 1, 1);
            this.Shape3.func_78793_a(-1.0f, 25.5f, 0.0f);
            this.Shape3.func_78787_b(128, 64);
            this.Shape3.field_78809_i = true;
            this.setRotation(this.Shape3, 0.0f, 0.0f, 2.495821f);
            this.Shape4 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape4.func_78789_a(0.0f, 0.0f, 0.0f, 17, 1, 1);
            this.Shape4.func_78793_a(-26.0f, 43.3f, 0.0f);
            this.Shape4.func_78787_b(128, 64);
            this.Shape4.field_78809_i = true;
            this.setRotation(this.Shape4, 0.0f, 0.0f, -2.565847f);
            this.Shape5 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape5.func_78789_a(0.0f, 0.0f, 0.0f, 30, 1, 1);
            this.Shape5.func_78793_a(-42.06667f, 33.0f, 0.0f);
            this.Shape5.func_78787_b(128, 64);
            this.Shape5.field_78809_i = true;
            this.setRotation(this.Shape5, 0.0f, 0.0f, 0.0f);
            this.Shape6 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape6.func_78789_a(0.0f, 0.0f, 0.0f, 8, 1, 1);
            this.Shape6.func_78793_a(-11.8f, 43.3f, 0.0f);
            this.Shape6.func_78787_b(128, 64);
            this.Shape6.field_78809_i = true;
            this.setRotation(this.Shape6, 0.0f, 0.0f, -2.234021f);
            this.Shape7 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape7.func_78789_a(0.0f, 0.0f, 0.0f, 7, 3, 3);
            this.Shape7.func_78793_a(-3.0f, 28.0f, -1.0f);
            this.Shape7.func_78787_b(128, 64);
            this.Shape7.field_78809_i = true;
            this.setRotation(this.Shape7, 0.0f, 0.0f, 2.495821f);
            this.Shape8 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape8.func_78789_a(0.0f, 0.0f, 0.0f, 3, 8, 3);
            this.Shape8.func_78793_a(14.0f, 35.0f, -1.0f);
            this.Shape8.func_78787_b(128, 64);
            this.Shape8.field_78809_i = true;
            this.setRotation(this.Shape8, 0.0f, 0.0f, 0.0f);
            this.Shape9 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape9.func_78789_a(0.0f, 0.0f, 0.0f, 15, 2, 2);
            this.Shape9.func_78793_a(2.0f, 44.0f, -1.0f);
            this.Shape9.func_78787_b(128, 64);
            this.Shape9.field_78809_i = true;
            this.setRotation(this.Shape9, 0.0f, 0.0f, 0.0f);
            this.Shape10 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape10.func_78789_a(0.0f, 0.0f, 0.0f, 7, 3, 3);
            this.Shape10.func_78793_a(-10.0f, 41.0f, -1.0f);
            this.Shape10.func_78787_b(128, 64);
            this.Shape10.field_78809_i = true;
            this.setRotation(this.Shape10, 0.0f, 0.0f, 0.0f);
            this.Shape11 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape11.func_78789_a(0.0f, 0.0f, 0.0f, 1, 11, 1);
            this.Shape11.func_78793_a(15.0f, 33.0f, 0.0f);
            this.Shape11.func_78787_b(128, 64);
            this.Shape11.field_78809_i = true;
            this.setRotation(this.Shape11, 0.0f, 0.0f, 0.0f);
            this.Shape12 = new ModelRenderer((ModelBase)this, 0, 0);
            this.Shape12.func_78789_a(0.0f, 0.0f, 0.0f, 10, 1, 1);
            this.Shape12.func_78793_a(-22.06667f, 34.0f, 0.0f);
            this.Shape12.func_78787_b(128, 64);
            this.Shape12.field_78809_i = true;
            this.setRotation(this.Shape12, 0.0f, 0.0f, -2.942418f);
        }

        public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
            super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
            this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
            this.Shape1.func_78785_a(f5);
            this.Shape2.func_78785_a(f5);
            this.Shape3.func_78785_a(f5);
            this.Shape4.func_78785_a(f5);
            this.Shape5.func_78785_a(f5);
            this.Shape6.func_78785_a(f5);
            this.Shape7.func_78785_a(f5);
            this.Shape8.func_78785_a(f5);
            this.Shape9.func_78785_a(f5);
            this.Shape10.func_78785_a(f5);
            this.Shape11.func_78785_a(f5);
            this.Shape12.func_78785_a(f5);
        }

        private void setRotation(ModelRenderer model, float x, float y, float z) {
            model.field_78795_f = x;
            model.field_78796_g = y;
            model.field_78808_h = z;
        }
    }

}

