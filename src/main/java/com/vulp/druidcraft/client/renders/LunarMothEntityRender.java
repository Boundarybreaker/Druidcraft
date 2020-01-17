package com.vulp.druidcraft.client.renders;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.LunarMothEntityModel;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LunarMothEntityRender extends MobEntityRenderer<LunarMothEntity, LunarMothEntityModel<LunarMothEntity>>
{
    private static final Identifier MOTH_TURQUOISE = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_turquoise.png");
    private static final Identifier MOTH_WHITE = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_white.png");
    private static final Identifier MOTH_LIME = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_lime.png");
    private static final Identifier MOTH_ORANGE = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_orange.png");
    private static final Identifier MOTH_PINK = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_pink.png");
    private static final Identifier MOTH_YELLOW = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_yellow.png");

    public LunarMothEntityRender(EntityRenderDispatcher manager)
    {
        super(manager, new LunarMothEntityModel<>(), 0.2f);
    }

    @Override
    public Identifier getTexture(LunarMothEntity entity) {
        switch (entity.getColor()) {
            case TURQUOISE:
                return MOTH_TURQUOISE;
            case WHITE:
                return MOTH_WHITE;
            case LIME:
                return MOTH_LIME;
            case ORANGE:
                return MOTH_ORANGE;
            case PINK:
                return MOTH_PINK;
            case YELLOW:
                return MOTH_YELLOW;
        }
        return MOTH_WHITE;
    }
}
