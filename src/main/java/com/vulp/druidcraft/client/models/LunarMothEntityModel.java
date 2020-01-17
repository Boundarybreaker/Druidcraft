package com.vulp.druidcraft.client.models;

import com.vulp.druidcraft.entities.LunarMothEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class LunarMothEntityModel<T extends LunarMothEntity> extends EntityModel<T> {
    private final ModelPart main;
    private final ModelPart body;
    private final ModelPart antennae;
    private final ModelPart wing1;
    private final ModelPart wing2;

    public LunarMothEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        main = new ModelPart(this);
        main.setPivot(0.0F, 21.0F, 0.0F);

        body = new ModelPart(this, 0, 0);
        body.setPivot(0.0F, -1.0F, 0.0F);
        main.addChild(body);
        body.addCuboid(-1.0F, -1.0F, -3.0F, 2, 2, 6, 0.0F, false);

        antennae = new ModelPart(this, 4, 8);
        antennae.setPivot(0.0F, -2.0F, -3.0F);
        main.addChild(antennae);
        antennae.addCuboid(-2.5F, 0.0F, -3.0F, 2, 0, 3, 0.0F, false);
        antennae.addCuboid(0.5F, 0.0F, -3.0F, 2, 0, 3, 0.0F, true);

        wing1 = new ModelPart(this, 0, 8);
        wing1.setPivot(-1.0F, -2.0F, 1.5F);
        main.addChild(wing1);
        wing1.addCuboid(-6.0F, 0.0F, -5.5F, 6, 0, 14, 0.0F, false);

        wing2 = new ModelPart(this, 0, 8);
        wing2.setPivot(1.0F, -2.0F, 1.5F);
        main.addChild(wing2);
        wing2.addCuboid(0.0F, 0.0F, -5.5F, 6, 0, 14, 0.0F, true);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
//        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
        main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.roll = x;
        modelRenderer.pitch = y;
        modelRenderer.yaw = z;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        this.setRotationAngle(main, -0.3491F, 0.0F, 0.0F);
        setRotationAngle(antennae, -0.6109F, 0.0F, 0.0F);
        setRotationAngle(wing1, 0.0F, 0.0F, MathHelper.cos(entity.age * 1.3F) * 3.1415927F * 0.25F);
        setRotationAngle(wing2, 0.0F, 0.0F, -(MathHelper.cos(entity.age * 1.3F) * 3.1415927F * 0.25F));
    }
}