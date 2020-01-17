package com.vulp.druidcraft.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MagicGlitterParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteSet;

    public MagicGlitterParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider sprite) {
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
        this.maxAge = this.random.nextInt(10) + 10;
        this.setSprite(sprite);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ < this.maxAge && this.scale > 0.0F) {
            this.setSprite(this.spriteSet);
            this.scale = ((float)this.age / (float)this.maxAge) * 0.3f;
            this.velocityX += this.random.nextFloat() / 1000.0F * (float) (this.random.nextBoolean() ? 1 : -1);
            this.velocityZ += this.random.nextFloat() / 1000.0F * (float) (this.random.nextBoolean() ? 1 : -1);
            this.velocityY -= this.random.nextFloat() / 5000.0F;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
        } else {
            this.markDead();
        }

    }

    @Override
    public int getColorMultiplier(float partialTick) {
        float f = ((float) this.age + partialTick) / (float) this.maxAge;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getColorMultiplier(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }


        return this.age / this.maxAge * (j | k << 16);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteSet;

        public Factory(SpriteProvider sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(DefaultParticleType typeIn, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MagicGlitterParticle particle = new MagicGlitterParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            return particle;
        }
    }
}
