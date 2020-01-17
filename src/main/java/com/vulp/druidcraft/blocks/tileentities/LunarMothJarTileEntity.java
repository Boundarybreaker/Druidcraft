package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.blocks.LunarMothJarBlock;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.MathHelper;

public class LunarMothJarTileEntity extends BlockEntity implements Tickable {
    public int ageInTicks;
    public int color;
    public float positionX;
    public float positionY;
    public float positionZ;
    public float modifierX;
    public float modifierY;
    public float modifierZ;
    public float facingAngle;
    public boolean angleFlag;
    public boolean lanternHanging;

    public LunarMothJarTileEntity () {
        super(TileEntityRegistry.lunar_moth_jar);
        this.color = world.getBlockState(this.pos).get(LunarMothJarBlock.COLOR);
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        compound.putInt("Color", this.color);
        compound.putInt("AnimationAge", this.ageInTicks);
        compound.putBoolean("Hanging", this.lanternHanging);

        return compound;
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        this.color = compound.getInt("Color");
        this.ageInTicks = compound.getInt("AnimationAge");
        this.lanternHanging = compound.getBoolean("Hanging");

    }

    @Override
    public void tick() {

        if (world != null && !world.isClient) {
            if (facingAngle == 0.0f) {
                facingAngle = world.random.nextInt(360);
            }
            if (modifierX == 0.0f) {
                modifierX = this.world.random.nextInt(6) + 0.3f;
            }
            if (modifierY == 0.0f) {
                modifierY = this.world.random.nextInt(6) + 0.3f;
                if (lanternHanging) {
                    modifierY += 0.1f;
                }
            }
            if (modifierZ == 0.0f) {
                modifierZ = this.world.random.nextInt(6) + 0.3f;
            }

            if (world.random.nextInt(50) == 0) {
                this.angleFlag = !this.angleFlag;
            }
            int angleModifier = angleFlag ? 1 : -1;
            float angleChange = world.random.nextFloat() * angleModifier;
            this.facingAngle += angleChange;
            this.positionX = (MathHelper.sin(ageInTicks + modifierX) / 7.0f) + 0.5f;
            this.positionY = (MathHelper.sin(ageInTicks + modifierY) / 7.0f) + 0.3f;
            this.positionZ = (MathHelper.sin(ageInTicks + modifierZ) / 7.0f) + 0.5f;
            ++this.ageInTicks;
        }
    }

}