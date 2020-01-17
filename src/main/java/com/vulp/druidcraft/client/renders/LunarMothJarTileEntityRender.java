package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import com.vulp.druidcraft.client.models.LunarMothJarTileEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LunarMothJarTileEntityRender extends BlockEntityRenderer<LunarMothJarTileEntity> {
    private static final Identifier MOTH_TURQUOISE = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_turquoise.png");
    private static final Identifier MOTH_WHITE = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_white.png");
    private static final Identifier MOTH_LIME = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_lime.png");
    private static final Identifier MOTH_ORANGE = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_orange.png");
    private static final Identifier MOTH_PINK = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_pink.png");
    private static final Identifier MOTH_YELLOW = new Identifier(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_yellow.png");
    private static LunarMothJarTileEntityModel modelMoth = new LunarMothJarTileEntityModel();
    private static boolean rotationDir = true;

    public LunarMothJarTileEntityRender(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(LunarMothJarTileEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
//        GlStateManager.pushMatrix();
//        GlStateManager.translated(x, y, z);
        // It's already at the tile entity's position
        // ((float)x + tileEntityIn.positionX, (float)y + tileEntityIn.positionY, (float)z + tileEntityIn.positionZ);
        if (blockEntity.getWorld().random.nextInt(30) == 0) {
            rotationDir = !rotationDir;
        }
        float angleModifier = rotationDir ? 1.0f : -1.0f;
        GlStateManager.rotatef(angleModifier, 0.0F, 0.0F, 1.0F);

        if (blockEntity.color == 1) {
            this.dispatcher.textureManager.bindTexture(MOTH_TURQUOISE);
        }
        else if (blockEntity.color == 3) {
            this.dispatcher.textureManager.bindTexture(MOTH_LIME);
        }
        else if (blockEntity.color == 4) {
            this.dispatcher.textureManager.bindTexture(MOTH_YELLOW);
        }
        else if (blockEntity.color == 5) {
            this.dispatcher.textureManager.bindTexture(MOTH_ORANGE);
        }
        else if (blockEntity.color == 6) {
            this.dispatcher.textureManager.bindTexture(MOTH_PINK);
        }
        else {
            this.dispatcher.textureManager.bindTexture(MOTH_WHITE);
        }

        GlStateManager.enableCull();
        modelMoth.renderWithMoth(blockEntity, matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), light, overlay, 1F, 1F, 1F, 1F);
//        GlStateManager.popMatrix();
        matrices.pop();
    }
}