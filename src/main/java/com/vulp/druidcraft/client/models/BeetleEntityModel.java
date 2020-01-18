package com.vulp.druidcraft.client.models;

import com.vulp.druidcraft.entities.BeetleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;

@Environment(EnvType.CLIENT)
public class BeetleEntityModel<T extends BeetleEntity> extends AnimalModel<T> {

    private final ModelPart body_1;
    private final ModelPart head;
    private final ModelPart antennae_L;
    private final ModelPart antennae_R;
    private final ModelPart mandibles;
    private final ModelPart body_2;
    private final ModelPart leg_L_front;
    private final ModelPart leg_L_middle;
    private final ModelPart leg_L_back;
    private final ModelPart leg_R_front;
    private final ModelPart leg_R_middle;
    private final ModelPart leg_R_back;
    private final ModelPart harness;
    private final ModelPart chest_L_front;
    private final ModelPart chest_L_back;
    private final ModelPart chest_R_front;
    private final ModelPart chest_R_back;
    private final ModelPart saddle_main;
    private final ModelPart saddle_front;
    private final ModelPart saddle_back;

    public BeetleEntityModel() {

        this.textureWidth = 112;
        this.textureHeight = 112;

        this.body_1 = new ModelPart(this, 0, 0);
        this.body_1.setPivot(0.0F, 16.5F, -1.0F);
        this.body_1.addCuboid(-11.0F, -16.5F, -9.0F, 22, 18, 24, 0.0F, false);

        this.head = new ModelPart(this, 68, 42);
        this.head.setPivot(0.0F, -5.5F, -9.0F);
        this.body_1.addChild(this.head);
        this.head.addCuboid(-5.0F, -5.0F, -8.0F, 10, 10, 8, 0.0F, false);

        this.antennae_L = new ModelPart(this, 0, 0);
        this.antennae_L.setPivot(-2.0F, -3.0F, -8.0F);
        this.head.addChild(this.antennae_L);
        this.antennae_L.addCuboid(0.0F, -3.0F, -2.0F, 0, 3, 2, 0.0F, false);

        this.antennae_R = new ModelPart(this, 0, 3);
        this.antennae_R.setPivot(2.0F, -3.0F, -8.0F);
        this.head.addChild(this.antennae_R);
        this.antennae_R.addCuboid(0.0F, -3.0F, -2.0F, 0, 3, 2, 0.0F, false);

        this.mandibles = new ModelPart(this, 65, 60);
        this.mandibles.setPivot(0.0F, 4.0F, -8.0F);
        this.head.addChild(this.mandibles);
        this.mandibles.addCuboid(-5.0F, 0.0F, -3.0F, 10, 0, 3, 0.0F, false);

        this.body_2 = new ModelPart(this, 68, 0);
        this.body_2.setPivot(0.0F, -6.5F, 15.0F);
        this.body_1.addChild(body_2);
        this.body_2.addCuboid(-8.0F, -8.0F, 0.0F, 16, 16, 6, 0.0F, false);

        this.leg_L_front = new ModelPart(this, 0, 15);
        this.leg_L_front.setPivot(-6.0F, 6.0F, -21.0F);
        this.body_2.addChild(this.leg_L_front);
        this.leg_L_front.addCuboid(-8.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_L_middle = new ModelPart(this, 0, 15);
        this.leg_L_middle.setPivot(-6.0F, 6.0F, -12.0F);
        this.body_2.addChild(this.leg_L_middle);
        this.leg_L_middle.addCuboid(-8.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_L_back = new ModelPart(this, 0, 15);
        this.leg_L_back.setPivot(-6.0F, 6.0F, -3.0F);
        this.body_2.addChild(this.leg_L_back);
        this.leg_L_back.addCuboid(-8.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_R_front = new ModelPart(this, 0, 15);
        this.leg_R_front.setPivot(6.0F, 6.0F, -21.0F);
        this.body_2.addChild(this.leg_R_front);
        this.leg_R_front.addCuboid(0.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_R_middle = new ModelPart(this, 0, 15);
        this.leg_R_middle.setPivot(6.0F, 6.0F, -12.0F);
        this.body_2.addChild(this.leg_R_middle);
        this.leg_R_middle.addCuboid(0.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_R_back = new ModelPart(this, 0, 15);
        this.leg_R_back.setPivot(6.0F, 6.0F, -3.0F);
        this.body_2.addChild(this.leg_R_back);
        this.leg_R_back.addCuboid(0.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.harness = new ModelPart(this, 0, 42);
        this.harness.setPivot(0.0F, 5.625F, 1.25F);
        this.body_1.addChild(this.harness);
        this.harness.addCuboid(-11.0F, -22.125F, -10.25F, 22, 18, 24, 0.3F, false);

        this.chest_L_front = new ModelPart(this, 0, 0);
        this.chest_L_front.setPivot(-12.7F, 1.5F, -17.0F);
        this.body_2.addChild(this.chest_L_front);
        this.chest_L_front.addCuboid(-1.3F, -3.5F, -4.0F, 3, 7, 8, 0.0F, false);

        this.chest_L_back = new ModelPart(this, 0, 0);
        this.chest_L_back.setPivot(-12.7F, 1.5F, -7.0F);
        this.body_2.addChild(this.chest_L_back);
        this.chest_L_back.addCuboid(-1.3F, -3.5F, -4.0F, 3, 7, 8, 0.0F, false);

        this.chest_R_front = new ModelPart(this, 0, 0);
        this.chest_R_front.setPivot(12.7F, 1.5F, -17.0F);
        this.body_2.addChild(this.chest_R_front);
        this.chest_R_front.addCuboid(-1.7F, -3.5F, -4.0F, 3, 7, 8, 0.0F, false);

        this.chest_R_back = new ModelPart(this, 0, 0);
        this.chest_R_back.setPivot(12.7F, 1.5F, -7.0F);
        this.body_2.addChild(this.chest_R_back);
        this.chest_R_back.addCuboid(-1.7F, -3.5F, -4.0F, 3, 7, 8, 0.0F, false);

        this.saddle_main = new ModelPart(this, 44, 84);
        this.saddle_main.setPivot(0.0F, -22.625F, 1.75F);
        this.harness.addChild(this.saddle_main);
        this.saddle_main.addCuboid(-7.0F, -0.5F, -10.0F, 14, 1, 20, 0.0F, false);

        this.saddle_front = new ModelPart(this, 78, 105);
        this.saddle_front.setPivot(0.0F, -23.125F, -6.75F);
        this.harness.addChild(this.saddle_front);
        this.saddle_front.addCuboid(-7.0F, -1.0F, -1.5F, 14, 2, 3, 0.0F, false);

        this.saddle_back = new ModelPart(this, 44, 105);
        this.saddle_back.setPivot(0.0F, -23.125F, 10.25F);
        this.harness.addChild(this.saddle_back);
        this.saddle_back.addCuboid(-7.0F, -1.0F, -1.5F, 14, 2, 3, 0.0F, false);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Collections.singleton(body_1);
    }

    private void rotationAngles(ModelPart rendererModel, float x, float y, float z) {
        rendererModel.roll = x;
        rendererModel.pitch = y;
        rendererModel.yaw = z;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {

        this.head.pitch = headYaw * 0.012F;
        this.head.roll = headPitch * 0.012F;
        this.rotationAngles(this.mandibles, 0.4363F, 0.0F, 0.0F);
        this.rotationAngles(this.leg_L_front, 0.0F, -0.1745F, -0.6109F);
        this.rotationAngles(this.leg_L_middle, 0.0F, 0.0F, -0.6109F);
        this.rotationAngles(this.leg_L_back, 0.0F, 0.1745F, -0.6109F);
        this.rotationAngles(this.leg_R_front, 0.0F, 0.1745F, 0.6109F);
        this.rotationAngles(this.leg_R_middle, 0.0F, 0.0F, 0.6109F);
        this.rotationAngles(this.leg_R_back, 0.0F, -0.1745F, 0.6109F);
        this.rotationAngles(this.saddle_front, 0.2618F, 0.0F, 0.0F);
        this.rotationAngles(this.saddle_back, -0.2618F, 0.0F, 0.0F);
        float k;
        if (entity.hasPassengers()) {
            k = 8.0F;
        } else {
            k = 4.0F;
        }
        float f3 = -(MathHelper.cos(entity.limbAngle * 0.6662F * k + 0.0F) * 2.0F) * entity.limbDistance;
        float f4 = -(MathHelper.cos(entity.limbAngle * 0.6662F * k + (float)Math.PI) * 2.0F) * entity.limbDistance;
        float f5 = -(MathHelper.cos(entity.limbAngle * 0.6662F * k + ((float)Math.PI / 2F)) * 2.0F) * entity.limbDistance;
        this.leg_R_front.pitch += f3;
        this.leg_L_front.pitch += -f3;
        this.leg_R_middle.pitch += f4;
        this.leg_L_middle.pitch += -f4;
        this.leg_R_back.pitch += f5;
        this.leg_L_back.pitch += -f5;

        if (entity.isTamed()) {
            float j1 = (float) Math.abs(((entity.getHealth() / entity.getMaximumHealth()) - 1) * 0.75);
            this.rotationAngles(this.antennae_L, j1, 0.0F, 0.0F);
            this.rotationAngles(this.antennae_R, j1, 0.0F, 0.0F);
        }

        if (entity.hasSaddle()) {
            this.harness.visible = true;
            this.saddle_main.visible = true;
            this.saddle_front.visible = true;
            this.saddle_back.visible = true;
        } else {
            this.harness.visible = false;
            this.saddle_main.visible = false;
            this.saddle_front.visible = false;
            this.saddle_back.visible = false;
        }

        if (entity.hasChest()) {
            this.chest_L_front.visible = true;
            this.chest_L_back.visible = true;
            this.chest_R_front.visible = true;
            this.chest_R_back.visible = true;
        } else {
            this.chest_L_front.visible = false;
            this.chest_L_back.visible = false;
            this.chest_R_front.visible = false;
            this.chest_R_back.visible = false;
        }
    }
}