package com.vulp.druidcraft.client.renders;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.DreadfishEntityModel;
import com.vulp.druidcraft.entities.DreadfishEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class DreadfishEntityRender extends MobEntityRenderer<DreadfishEntity, DreadfishEntityModel>
{
    private static final Identifier DREADFISH_HEALTH_FULL = new Identifier(Druidcraft.MODID, "textures/entity/dreadfish/dreadfish_0.png");
    private static final Identifier DREADFISH_HEALTH_HIGH = new Identifier(Druidcraft.MODID, "textures/entity/dreadfish/dreadfish_1.png");
    private static final Identifier DREADFISH_HEALTH_MEDIUM = new Identifier(Druidcraft.MODID, "textures/entity/dreadfish/dreadfish_2.png");
    private static final Identifier DREADFISH_HEALTH_LOW = new Identifier(Druidcraft.MODID, "textures/entity/dreadfish/dreadfish_3.png");

    public DreadfishEntityRender(EntityRenderDispatcher manager)
    {
        super(manager, new DreadfishEntityModel(), 0.4f);
    }

    @Override
    public Identifier getTexture(DreadfishEntity entity) {
        if (entity.isTamed()) {
            if (entity.getHealth() >= entity.getMaximumHealth()) {
                return DREADFISH_HEALTH_FULL;
            } else if ((entity.getHealth() < entity.getMaximumHealth()) && (entity.getHealth() >= 16.0f)) {
                return DREADFISH_HEALTH_HIGH;
            } else if ((entity.getHealth() < 16.0f) && (entity.getHealth() >= 8.0f)) {
                return DREADFISH_HEALTH_MEDIUM;
            } else if ((entity.getHealth() < 8.0f)) {
                return DREADFISH_HEALTH_LOW;
            }
            else return DREADFISH_HEALTH_FULL;
        }
        else return DREADFISH_HEALTH_FULL;
    }

    @Override
    protected void setupTransforms(DreadfishEntity entityLiving, MatrixStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupTransforms(entityLiving, stack, ageInTicks, rotationYaw, partialTicks);
        float f = 1.0F;
        float f1 = 1.0F;
        float f2 = f * 4.3F * MathHelper.sin(f1 * 0.6F * ageInTicks);
        stack.push();
        stack.translate(0.0F, 0.0F, -0.4F);
        stack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(f2));
        stack.pop();
    }
}