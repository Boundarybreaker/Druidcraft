package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
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
    }

    public LunarMothJarTileEntity(int color) {
        this();
        this.color = color;
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("Color", this.color);
        compound.putInt("AnimationAge", this.ageInTicks);
        compound.putBoolean("Hanging", this.lanternHanging);

        return compound;
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        this.color = compound.getInt("Color");
        this.ageInTicks = compound.getInt("AnimationAge");
        this.lanternHanging = compound.getBoolean("Hanging");

    }

    public void tick() {

        if (world != null && !world.isRemote) {
            if (facingAngle == 0.0f) {
                facingAngle = world.rand.nextInt(360);
            }
            if (modifierX == 0.0f) {
                modifierX = this.world.rand.nextInt(6) + 0.3f;
            }
            if (modifierY == 0.0f) {
                modifierY = this.world.rand.nextInt(6) + 0.3f;
                if (lanternHanging) {
                    modifierY += 0.1f;
                }
            }
            if (modifierZ == 0.0f) {
                modifierZ = this.world.rand.nextInt(6) + 0.3f;
            }

            if (world.rand.nextInt(50) == 0) {
                this.angleFlag = !this.angleFlag;
            }
            int angleModifier = angleFlag ? 1 : -1;
            float angleChange = world.rand.nextFloat() * angleModifier;
            this.facingAngle += angleChange;
            this.positionX = (MathHelper.sin(ageInTicks + modifierX) / 7.0f) + 0.5f;
            this.positionY = (MathHelper.sin(ageInTicks + modifierY) / 7.0f) + 0.3f;
            this.positionZ = (MathHelper.sin(ageInTicks + modifierZ) / 7.0f) + 0.5f;
            ++this.ageInTicks;
        }
    }

}