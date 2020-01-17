package com.vulp.druidcraft.client.models;

import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class LunarMothJarTileEntityModel extends Model {
    private final ModelPart main;
    private final ModelPart left_wing;
    private final ModelPart right_wing;

    public LunarMothJarTileEntityModel() {
        super(RenderLayer::getEntityCutoutNoCull);
        this.textureWidth = 8;
        this.textureHeight = 8;

        this.main = new ModelPart(this);
        this.main.setPivot(0.0F, 22.0F, 7.5F);

        this.left_wing = new ModelPart(this, 0, 0);
        this.left_wing.setPivot(0.0F, 0.0F, 0.0F);
        this.main.addChild(this.left_wing);
        this.left_wing.addCuboid(-2.0F, 0.0F, -2.5F, 2, 0, 5, 0.0F, false);

        this.right_wing = new ModelPart(this, 0, 0);
        this.right_wing.setPivot(0.0F, 0.0F, 0.0F);
        this.main.addChild(this.right_wing);
        this.right_wing.addCuboid(0.0F, 0.0F, -2.5F, 2, 0, 5, 0.0F, true);
    }

    public void renderWithMoth(LunarMothJarTileEntity be, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        setRotationAngle(be.ageInTicks);
        render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        //        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
        this.main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void RotationAngles(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.roll = x;
        modelRenderer.pitch = y;
        modelRenderer.yaw = z;
    }

    public void setRotationAngle(float ageInTicks) {
        RotationAngles(this.main, -0.6109F, 0.0F, 0.0F);
        RotationAngles(this.left_wing, 0.0F, 0.0F, MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F);
        RotationAngles(this.right_wing, 0.0F, 0.0F, -(MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F));
    }
}