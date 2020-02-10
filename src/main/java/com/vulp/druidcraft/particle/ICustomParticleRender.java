package com.vulp.druidcraft.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

//TODO: what should I do in Blaze3D?
public interface ICustomParticleRender extends ParticleTextureSheet {

    ICustomParticleRender PARTICLE_SHEET_TRANSLUCENT_GLOW = new ICustomParticleRender() {
        @Override
        public void begin(BufferBuilder buffer, TextureManager textureManager) {
//            RenderHelper.disableStandardItemLighting();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            textureManager.bindTexture(new Identifier("textures/atlas/particles.png")); //TODO: is this the right place?
            RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA.value, GlStateManager.DstFactor.ONE.value);
            RenderSystem.alphaFunc(516, 0.003921569F);
            RenderSystem.disableCull();
            buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
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
        public void begin(BufferBuilder buffer, TextureManager textureManager) {
//            RenderHelper.disableStandardItemLighting();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            textureManager.bindTexture(new Identifier("textures/atlas/particles.png"));
            buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
        }

        @Override
        public void draw(Tessellator tess) {
            tess.draw();
        }

        public String toString() {
            return "CUSTOM";
        }
    };
}
