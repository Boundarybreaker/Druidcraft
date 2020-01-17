package com.vulp.druidcraft.client.models;

import com.vulp.druidcraft.entities.DreadfishEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class DreadfishEntityModel extends EntityModel<DreadfishEntity> {
    private ModelPart spine1;
    private ModelPart spine2;
    private ModelPart head1;
    private ModelPart head2;
    private ModelPart tail;
    private ModelPart topFin;
    private ModelPart sidefinR;
    private ModelPart sidefinL;

    public DreadfishEntityModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.spine1 = new ModelPart(this, 0, 0);
        this.spine1.addCuboid(-1.5F, -2.5F, -3.0F, 3, 5, 6, 0.0F, false);
        this.spine1.setPivot(0.0F, 21.0F, 6.0F);

        this.spine2 = new ModelPart(this, 18, 0);
        this.spine2.addCuboid(-1.0F, -2.5F, 0.0F, 2, 4, 4, 0.0F, false);
        this.spine2.setPivot(0.0F, 0.0F, 3.0F);
        this.spine1.addChild(this.spine2);

        this.head1 = new ModelPart(this, 8, 11);
        this.head1.addCuboid(-1.0F, -2.0F, -3.0F, 2, 3, 3, 0.0F, false);
        this.head1.setPivot(0.0F, 0.0F, -3.0F);
        this.spine1.addChild(this.head1);

        this.head2 = new ModelPart(this, 0, 20);
        this.head2.addCuboid(-1.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F, false);
        this.head2.setPivot(0.0F, 1.0F, 0.0F);
        this.head1.addChild(this.head2);

        this.tail = new ModelPart(this, 0, 11);
        this.tail.addCuboid(0.0F, -2.5F, 0.0F, 0, 5, 4, 0.0F, false);
        this.tail.setPivot(0.0F, 0.0F, 4.0F);
        this.spine2.addChild(this.tail);

        this.sidefinR = new ModelPart(this, 16, 20);
        this.sidefinR.addCuboid(-2.0F, 0.0F, -1.0F, 2, 0, 2, 0.0F, false);
        this.sidefinR.setPivot(-1.5F, 1.5F, -2.0F);
        this.spine1.addChild(this.sidefinR);
        this.sidefinR.yaw = -0.7853982F;

        this.sidefinL = new ModelPart(this, 0, 25);
        this.sidefinL.addCuboid(0.0F, 0.0F, -1.0F, 2, 0, 2, 0.0F, false);
        this.sidefinL.setPivot(1.5F, 1.5F, -2.0F);
        this.spine1.addChild(this.sidefinL);
        this.sidefinL.yaw = 0.7853982F;

        this.topFin = new ModelPart(this, 10, 20);
        this.topFin.addCuboid(0.0F, -7.5F, 6.0F, 0, 2, 3, 0.0F, false);
        this.topFin.setPivot(0.0F, 3.0F, -6.0F);
        this.spine1.addChild(this.topFin);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        spine1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(DreadfishEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        float f = 1.5F;
        float f1 = 1.5F;
        float j = 1.0F;
        if (entity.isSitting()) {
            j = 0.5F;
        }
        this.spine2.pitch = -f * 0.25F * MathHelper.sin(f1 * 0.6F * entity.age * j);
        }
}