package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.MathHelper;
import java.util.Random;

public class OreBlock extends net.minecraft.block.OreBlock {
    private final int minXP;
    private final int maxXP;

    public OreBlock(Block.Settings properties, int minXP, int maxXP) {
        super(properties);
        this.minXP = minXP;
        this.maxXP = maxXP;
    }

    @Override
    protected int getExperienceWhenMined(Random rand) {
        return MathHelper.nextInt(rand, minXP, maxXP);
    }
}