package com.vulp.druidcraft.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

//TODO: what should I do in Blaze3D?
public interface ICustomParticleRender extends ParticleTextureSheet {

    ICustomParticleRender PARTICLE_SHEET_TRANSLUCENT_GLOW = new ICustomParticleRender() {
        @Override
        public void begin(BufferBuilder buffer, TextureManager textureManager) {
//            RenderHelper.disableStandardItemLighting();
            GlStateManager.enableAlphaTest();
            GlStateManager.enableBlend();
            GlStateManager.depthMask(false);
            textureManager.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEX);
            GlStateManager.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA.value, GlStateManager.DstFactor.ONE.value);
            GlStateManager.alphaFunc(516, 0.003921569F);
            GlStateManager.disableCull();
            buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }
        @Override
        public void draw(Tessellator tess) {
            tess.draw();
        }
        public String toString() {
            return "PARTICLE_SHEET_TRANSLUCENT_GLOW";
        }
    };

    ICustomParticleRender CUSTOM = new ICustomParticleRender() {
        @Override
        public void begin(BufferBuilder p_217600_1_, TextureManager p_217600_2_) {
//            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            p_217600_2_.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEX);
            p_217600_1_.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator p_217599_1_) {
            p_217599_1_.draw();
        }

        public String toString() {
            return "CUSTOM";
        }
    };
}
