package com.vulp.druidcraft.world.features;

import com.mojang.datafixers.Dynamic;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.MegaPineTreeFeature;
import net.minecraft.world.gen.feature.MegaTreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class MegaDarkwoodTreeFeature extends MegaPineTreeFeature {
    private static final BlockState TRUNK;
    private static final BlockState LEAF;
    private final boolean useBaseHeight;

    public MegaDarkwoodTreeFeature(Function<Dynamic<?>, ? extends MegaTreeFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace, boolean useBaseHeightIn) {
        super(configFactoryIn);
        this.useBaseHeight = useBaseHeightIn;
//        this.setSapling((IPlantable)BlockRegistry.darkwood_sapling);
    }

    @Override
	public boolean generate(ModifiableTestableWorld worldIn, Random rand, BlockPos position, Set<BlockPos> trunk, Set<BlockPos> changedBlocks, BlockBox box, MegaTreeFeatureConfig config) {
        int i = this.getHeight(rand, config);
        if (!this.checkTreeFitsAndReplaceGround(worldIn, position, i)) {
            return false;
        } else {
            this.makeTopLeaves(worldIn, rand, position.getX(), position.getZ(), position.getY() + i, 0, changedBlocks, box, config);

            for(int j = 0; j < i; ++j) {
                if (isAirOrLeaves(worldIn, position.up(j))) {
                    this.setLogBlockState(worldIn, rand, position.up(j), changedBlocks, box, config);
                }

                if (j < i - 1) {
                    if (isAirOrLeaves(worldIn, position.add(1, j, 0))) {
                        this.setLogBlockState(worldIn, rand, position.add(1, j, 0), changedBlocks, box, config);
                    }

                    if (isAirOrLeaves(worldIn, position.add(1, j, 1))) {
                        this.setLogBlockState(worldIn, rand, position.add(1, j, 1), changedBlocks, box, config);
                    }

                    if (isAirOrLeaves(worldIn, position.add(0, j, 1))) {
                        this.setLogBlockState(worldIn, rand, position.add(0, j, 1), changedBlocks, box, config);
                    }
                }
            }
            return true;
        }
    }

    static {
        TRUNK = BlockRegistry.darkwood_log.getDefaultState();
        LEAF = BlockRegistry.darkwood_leaves.getDefaultState();
    }

    private void makeTopLeaves(ModifiableTestableWorld modifiableTestableWorld, Random random, int i, int j, int k, int l, Set<BlockPos> set, BlockBox blockBox, MegaTreeFeatureConfig megaTreeFeatureConfig) {
        int m = random.nextInt(5) + megaTreeFeatureConfig.crownHeight;
        int n = 0;

        for(int o = k - m; o <= k; ++o) {
            int p = k - o;
            int q = l + MathHelper.floor((float)p / (float)m * 3.5F);
            this.makeSquaredLeafLayer(modifiableTestableWorld, random, new BlockPos(i, o, j), q + (p > 0 && q == n && (o & 1) == 0 ? 1 : 0), set, blockBox, megaTreeFeatureConfig);
            n = q;
        }

    }
}
