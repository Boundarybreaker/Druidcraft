package com.vulp.druidcraft.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MagicMistParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteSet;

    public MagicMistParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider sprite) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteSet = sprite;
        this.velocityX = this.velocityX * 0.009999999776482582d;
        this.velocityY = this.velocityY * 0.009999999776482582d;
        this.velocityZ = this.velocityZ * 0.009999999776482582d;
        this.scale = 0.3f;
        this.gravityStrength = 0.002f;
        this.colorRed = (float) velocityX;
        this.colorGreen = (float) velocityY;
        this.colorBlue = (float) velocityZ;
        this.maxAge = this.random.nextInt(20) + 10;
        this.setSprite(sprite);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ < this.maxAge && this.colorAlpha > 1.0F) {
            this.colorAlpha = (float)this.maxAge - (float)this.age / (float)this.maxAge;
            this.velocityX += this.random.nextFloat() / 7500.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.velocityZ += this.random.nextFloat() / 7500.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.velocityY -= this.random.nextFloat() / 2000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
        } else {
            this.markDead();
        }

    }

    @Override
    public int getColorMultiplier(float partialTick) {
        float f = ((float)this.age + partialTick) / (float)this.maxAge;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getColorMultiplier(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteSet;

        public Factory(SpriteProvider sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(DefaultParticleType typeIn, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MagicMistParticle particle = new MagicMistParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            return particle;
        }
    }
}