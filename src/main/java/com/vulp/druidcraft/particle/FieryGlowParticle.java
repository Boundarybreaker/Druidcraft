package com.vulp.druidcraft.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class FieryGlowParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteSet;
    private final float rotSpeed;
    private int fizzleAge;
    private float fizzleScale;
    private boolean fizzleTriggered;

    public FieryGlowParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, SpriteProvider sprite) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.spriteSet = sprite;
        this.velocityX = this.velocityX * 0.009999999776482582D + motionX;
        this.velocityY = this.velocityY * 0.009999999776482582D + motionY;
        this.velocityZ = this.velocityZ * 0.009999999776482582D + motionZ;
        this.x += (this.random.nextFloat() - this.random.nextFloat()) * 0.05F;
        this.y += (this.random.nextFloat() - this.random.nextFloat()) * 0.05F;
        this.z += (this.random.nextFloat() - this.random.nextFloat()) * 0.05F;
        this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        this.rotSpeed = ((float)Math.random() - 0.5F) * 0.05F;
        this.fizzleAge = 0;
        this.fizzleScale = 0;
        this.fizzleTriggered = false;
        this.angle = (float)Math.random() * 6.2831855F;
        this.setSprite(spriteSet);
    }

    @Override
    public float getSize(float p_217561_1_) {
        double f;
        if (age / maxAge <= 0.3 || !(this.fizzleAge == 0)) {
            f = MathHelper.cos(age/2) * -0.035 + 0.125;
        } else {
            f = this.getFizzleScale() * ((age / maxAge) / this.getFizzleAge());
        }
        return (float) f;
    }

    public void setFizzleScale(float fizzleScale) {
        this.fizzleScale = fizzleScale;
    }

    public float getFizzleScale() {
        return this.fizzleScale;
    }

    public void setFizzleAge(int fizzleAge) {
        this.fizzleAge = fizzleAge;
    }

    public int getFizzleAge() {
        return this.fizzleAge;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ICustomParticleRender.PARTICLE_SHEET_TRANSLUCENT_GLOW;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.prevAngle = this.angle;
            this.angle += 3.1415927F * this.rotSpeed * 2.0F;
            this.velocityX *= 0.9599999785423279D;
            this.velocityY *= 0.9599999785423279D;
            this.velocityZ *= 0.9599999785423279D;
            if (this.age / this.maxAge <= 0.3 && fizzleTriggered == false) {
                fizzleTriggered = true;
                this.setFizzleAge(this.age);
                this.setFizzleScale(this.scale);
            }
            if (this.onGround) {
                this.velocityX *= 0.699999988079071D;
                this.velocityZ *= 0.699999988079071D;
            }
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
            FieryGlowParticle particle = new FieryGlowParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            return particle;

        }
    }
}
