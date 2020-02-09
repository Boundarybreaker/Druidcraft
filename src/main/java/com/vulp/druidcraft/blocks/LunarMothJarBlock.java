package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.registry.ParticleRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class LunarMothJarBlock extends RopeableLanternBlock {

    public static final IntProperty COLOR = IntProperty.of("color", 1, 6);

    public LunarMothJarBlock(Block.Settings properties, int mothColor) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(HANGING, false).with(ROPED, false).with(WATERLOGGED, false).with(COLOR, mothColor));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        VoxelShape lantern_grounded = VoxelShapes.union(Block.createCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 8.0f, 12.0f), Block.createCuboidShape(5.0f, 8.0f, 5.0f, 11.0f, 10.0f, 11.0f));
        VoxelShape lantern_hanging = Block.createCuboidShape(4.0f, 1.0f, 4.0f, 12.0f, 12.0f, 12.0f);

        if (state.get(HANGING)) {
            return VoxelShapes.union(lantern_hanging, VoxelShapes.union(Block.createCuboidShape(5.0f, 14.0f, 5.0f, 14.0f, 14.0f, 11.0f), Block.createCuboidShape(6.0f, 14.0f, 6.0f, 10.0f, 16.0f, 10.0f)));
        }
        return lantern_grounded;
    }

    public int[] colorLib(BlockState stateIn, Random rand) {
        if (stateIn.get(COLOR) == 1) {
            if (rand.nextBoolean()) {
                return new int[]{80, 255, 155};
            } else {
                return new int[]{145, 255, 185};
            }
        }
        if (stateIn.get(COLOR) == 2) {
            if (rand.nextBoolean()) {
                return new int[]{245, 240, 220};
            } else {
                return new int[]{255, 255, 255};
            }
        }
        if (stateIn.get(COLOR) == 3) {
            if (rand.nextBoolean()) {
                return new int[]{175, 255, 105};
            } else {
                return new int[]{215, 255, 150};
            }
        }
        if (stateIn.get(COLOR) == 4) {
            if (rand.nextBoolean()) {
                return new int[]{255, 255, 150};
            } else {
                return new int[]{255, 255, 200};
            }
        }
        if (stateIn.get(COLOR) == 5) {
            if (rand.nextBoolean()) {
                return new int[]{255, 190, 75};
            } else {
                return new int[]{255, 220, 130};
            }
        }
        if (stateIn.get(COLOR) == 6) {
            if (rand.nextBoolean()) {
                return new int[]{255, 180, 230};
            } else {
                return new int[]{255, 225, 255};
            }
        }
        return new int[]{0, 0, 0};
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HANGING, ROPED, WATERLOGGED, COLOR);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.2D;
        double d2 = (double) pos.getZ() + 0.5D;
        if (state.get(HANGING)) {
            d1 = (double) pos.getY() + 0.3D;
        }
        int[] color = colorLib(state, random);
        float limit = 0.15f;
        float offset0 = Math.min(limit, Math.max(-limit, random.nextFloat() - 0.5f));
        float offset1 = Math.min(limit, Math.max(-limit, random.nextFloat() - 0.5f));
        float offset2 = Math.min(limit, Math.max(-limit, random.nextFloat() - 0.5f));
        world.addParticle(ParticleRegistry.magic_glitter, false, d0 + offset0, d1 + offset1, d2 + offset2, color[0] / 255F, color[1] / 255F, color[2] / 255F);
    }
}