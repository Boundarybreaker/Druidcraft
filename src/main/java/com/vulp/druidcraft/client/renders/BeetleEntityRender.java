package com.vulp.druidcraft.client.renders;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.BeetleEntityModel;
import com.vulp.druidcraft.entities.BeetleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BeetleEntityRender extends MobEntityRenderer<BeetleEntity, BeetleEntityModel<BeetleEntity>>
{
    private static final Identifier BEETLE = new Identifier(Druidcraft.MODID, "textures/entity/beetle/beetle.png");

    public BeetleEntityRender(EntityRenderDispatcher manager)
    {
        super(manager, new BeetleEntityModel<>(), 1.2f);
    }

    @Override
    public Identifier getTexture(BeetleEntity entity) {
        return BEETLE;
    }
}