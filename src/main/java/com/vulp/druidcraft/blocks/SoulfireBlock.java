package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.registry.ParticleRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SoulfireBlock extends Block {
    private final DyeColor color;
    private static final Map<DyeColor, int[]> DYE_COLOR_MAP = new HashMap<>();

    public SoulfireBlock(DyeColor color, Block.Settings properties) {
        super(properties);
        this.color = color;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView blockReader, BlockPos pos, EntityContext selectionContext) {
        return Block.createCuboidShape(5.0d, 5.0d, 5.0d, 11.0d, 11.0d, 11.0d);
    }

    static {
        DYE_COLOR_MAP.put(DyeColor.BLACK, new int[]{15, 15, 15});
        DYE_COLOR_MAP.put(DyeColor.RED, new int[]{255, 50, 40});
        DYE_COLOR_MAP.put(DyeColor.GREEN, new int[]{15, 150, 45});
        DYE_COLOR_MAP.put(DyeColor.BROWN, new int[]{130, 70, 45});
        DYE_COLOR_MAP.put(DyeColor.BLUE, new int[]{30, 60, 225});
        DYE_COLOR_MAP.put(DyeColor.PURPLE, new int[]{135, 45, 245});
        DYE_COLOR_MAP.put(DyeColor.CYAN, new int[]{20, 125, 130});
        DYE_COLOR_MAP.put(DyeColor.LIGHT_GRAY, new int[]{160, 160, 155});
        DYE_COLOR_MAP.put(DyeColor.GRAY, new int[]{90, 90, 90});
        DYE_COLOR_MAP.put(DyeColor.PINK, new int[]{255, 115, 170});
        DYE_COLOR_MAP.put(DyeColor.LIME, new int[]{135, 250, 35});
        DYE_COLOR_MAP.put(DyeColor.YELLOW, new int[]{240, 240, 50});
        DYE_COLOR_MAP.put(DyeColor.LIGHT_BLUE, new int[]{50, 200, 255});
        DYE_COLOR_MAP.put(DyeColor.MAGENTA, new int[]{230, 65, 170});
        DYE_COLOR_MAP.put(DyeColor.ORANGE, new int[]{240, 135, 30});
        DYE_COLOR_MAP.put(DyeColor.WHITE, new int[]{215, 215, 215});
    }

    private int[] getSmokeColorArray() {
        return DYE_COLOR_MAP.getOrDefault(getSmokeColor(), new int[]{0, 0, 0});
    }

    private DyeColor getSmokeColor() {
        return this.color;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.5D;
        double d2 = (double) pos.getZ() + 0.5D;
        int[] color = this.getSmokeColorArray();
        float limit = 0.1f;
        for (int i = 0; i < 7; i++) {
            float offset1 = Math.min(limit, Math.max(-limit, random.nextFloat() - 0.5f));
            float offset2 = Math.min(limit, Math.max(-limit, random.nextFloat() - 0.5f));
            float offset3 = Math.min(limit, Math.max(-limit, random.nextFloat() - 0.5f));
            world.addParticle(ParticleRegistry.magic_smoke, true, d0 + offset1, d1 + offset2, d2 + offset3, color[0] / 255.f, color[1] / 255.f, color[2] / 255.f);
        }
    }
}
