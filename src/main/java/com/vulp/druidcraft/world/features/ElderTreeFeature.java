package com.vulp.druidcraft.world.features;

import com.mojang.datafixers.Dynamic;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;

import java.util.function.Function;

public class ElderTreeFeature /*extends AbstractTreeFeature<BranchedTreeFeatureConfig>*/ {

    private static final BlockState TRUNK = BlockRegistry.elder_log.getDefaultState();
    private static final BlockState BASE = BlockRegistry.elder_wood.getDefaultState();
    private static final BlockState LEAF = BlockRegistry.elder_leaves.getDefaultState();

    public ElderTreeFeature(Function<Dynamic<?>, ? extends BranchedTreeFeatureConfig> config) {
//        super(config);
//        this.setSapling((IPlantable)BlockRegistry.elder_sapling);
    }

//    @Override
//    protected boolean generate(ModifiableTestableWorld worldIn, Random rand, BlockPos position, Set<BlockPos> set, Set<BlockPos> changedBlocks, BlockBox boundsIn, BranchedTreeFeatureConfig config) {
//
//        int height = rand.nextInt(2) + 6;
//        boolean canGrow = true;
//
//        if (position.getY() >= 1 && position.getY() + height + 1 <= 256) {
//            for (int cy = position.getY(); cy <= position.getY() + 1 + height; ++cy) {
//                int k = 1;
//
//                if (cy == position.getY()) {
//                    k = 0;
//                }
//
//                if (cy >= position.getY() + 1 + height - 2) {
//                    k = 2;
//                }
//
//                BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
//
//                for (int cx = position.getX() - k; cx <= position.getX() + k && canGrow; ++cx) {
//                    for (int cz = position.getZ() - k; cz <= position.getZ() + k && canGrow; ++cz) {
//                        if (cy >= 0 && cy < 256) {
//                            if (!func_214587_a(worldIn, blockpos$mutableblockpos.set(cx, cy, cz))) {
//                                canGrow = false;
//                            }
//                        } else {
//                            canGrow = false;
//                        }
//                    }
//                }
//            }
//
//            if (!canGrow) {
//                return false;
//            } else if (isSoil(worldIn, position.down(), getSapling()) && position.getY() < worldIn.getMaxHeight() - height - 1) {
//                this.setDirtAt(worldIn, position.down(), position);
//                int posX = position.getX();
//                int posZ = position.getZ();
//                int posY = 0;
//                int logSide1 = rand.nextInt(3);
//                int logSide2 = rand.nextInt(3);
//                if (logSide2 == logSide1) {
//                    logSide2 = rand.nextInt(3);
//                    if (logSide2 == logSide1) {
//                        logSide2 = 4;
//                    }
//                }
//
//                for (int base = 0; base < height; ++base) {
//                    int currentY = position.getY() + base;
//
//                    BlockPos blockpos = new BlockPos(posX, currentY, posZ);
//                    if (isAirOrLeaves(worldIn, blockpos)) {
//                        if (base == 0) {
//                            if (rand.nextBoolean()) {
//                                if (isAir(worldIn, blockpos.north().down())) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.north().down(), boundsIn, true);
//                                } else {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.north(), boundsIn, true);
//                                }
//                            }
//                            if (rand.nextBoolean()) {
//                                if (isAir(worldIn, blockpos.east().down())) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.east().down(), boundsIn, true);
//                                } else {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.east(), boundsIn, true);
//                                }
//                            }
//                            if (rand.nextBoolean()) {
//                                if (isAir(worldIn, blockpos.south().down())) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.south().down(), boundsIn, true);
//                                } else {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.south(), boundsIn, true);
//                                }
//                            }
//                            if (rand.nextBoolean()) {
//                                if (isAir(worldIn, blockpos.west().down())) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.west().down(), boundsIn, true);
//                                } else {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.west(), boundsIn, true);
//                                }
//                            }
//                            if (rand.nextBoolean()) {
//                                if (isAir(worldIn, blockpos.north().east().down())) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.north().east().down(), boundsIn, true);
//                                } else {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.north().east(), boundsIn, true);
//                                }
//                            }
//                            if (rand.nextBoolean()) {
//                                if (isAir(worldIn, blockpos.north().west().down())) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.north().west().down(), boundsIn, true);
//                                } else {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.north().west(), boundsIn, true);
//                                }
//                            }
//                            if (rand.nextBoolean()) {
//                                if (isAir(worldIn, blockpos.south().east().down())) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.south().east().down(), boundsIn, true);
//                                } else {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.south().east(), boundsIn, true);
//                                }
//                            }
//                            if (rand.nextBoolean()) {
//                                if (isAir(worldIn, blockpos.south().west().down())) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.south().west().down(), boundsIn, true);
//                                } else {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.south().west(), boundsIn, true);
//                                }
//                            }
//                            this.placeLogAt(changedBlocks, worldIn, blockpos, boundsIn, false);
//                        }
//
//                        if (base <= height - 1) {
//                            this.placeLogAt(changedBlocks, worldIn, blockpos, boundsIn, false);
//                            if (base == height - 2 || base == height - 3) {
//                                if (logSide1 == 0) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.north(1), boundsIn, Direction.Axis.Z);
//                                }
//                                if (logSide1 == 1) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.east(1), boundsIn, Direction.Axis.X);
//                                }
//                                if (logSide1 == 2) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.south(1), boundsIn, Direction.Axis.Z);
//                                }
//                                if (logSide1 == 3) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.west(1), boundsIn, Direction.Axis.X);
//                                }
//                            }
//                            if (base == height - 2 || base == height - 3) {
//                                if (logSide1 == 0) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.north(1), boundsIn, Direction.Axis.Z);
//                                }
//                                if (logSide1 == 1) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.east(1), boundsIn, Direction.Axis.X);
//                                }
//                                if (logSide1 == 2) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.south(1), boundsIn, Direction.Axis.Z);
//                                }
//                                if (logSide1 == 3) {
//                                    this.placeLogAt(changedBlocks, worldIn, blockpos.west(1), boundsIn, Direction.Axis.X);
//                                }
//                            }
//                        }
//                        posY = currentY;
//                    }
//                }
//
//                BlockPos blockpos2 = new BlockPos(posX, posY, posZ).down();
//                for (int leafLayer1and3X = -3; leafLayer1and3X <= 3; ++leafLayer1and3X) {
//                    for (int leafLayer1and3Z = -3; leafLayer1and3Z <= 3; ++leafLayer1and3Z) {
//                        if (!((leafLayer1and3X == 3 || leafLayer1and3X == -3) && (leafLayer1and3Z == 3 || leafLayer1and3Z == -3))) {
//                            if ((leafLayer1and3X == -3 && (leafLayer1and3Z == -2 || leafLayer1and3Z == 2)) || (leafLayer1and3X == 3 && (leafLayer1and3Z == -2 || leafLayer1and3Z == 2)) || (leafLayer1and3Z == -3 && (leafLayer1and3X == -2 || leafLayer1and3X == 2)) || (leafLayer1and3Z == 3 && (leafLayer1and3X == -2 || leafLayer1and3X == 2))) {
//                                if (rand.nextBoolean() || rand.nextBoolean()) {
//                                    this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z), boundsIn);
//                                }
//                                if (rand.nextBoolean() || rand.nextBoolean()) {
//                                    this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z), boundsIn);
//                                }
//                            } else {
//                                this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z), boundsIn);
//                                this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z), boundsIn);
//                            }
//                        }
//                    }
//                }
//
//                blockpos2 = blockpos2.up();
//
//                for (int leafLayer4X = -2; leafLayer4X <= 2; ++leafLayer4X) {
//                    for (int leafLayer4Z = -2; leafLayer4Z <= 2; ++leafLayer4Z) {
//                        if (!((leafLayer4X == -2 || leafLayer4X == 2) && (leafLayer4Z == -2 || leafLayer4Z == 2))) {
//                            this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(leafLayer4X, 0, leafLayer4Z), boundsIn);
//                        } else {
//                            if (rand.nextBoolean()) {
//                                this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(leafLayer4X, 0, leafLayer4Z), boundsIn);
//                            }
//                        }
//                    }
//                }
//
//                blockpos2 = blockpos2.up();
//
//                for (int leafLayer5X = -1; leafLayer5X <= 1; ++leafLayer5X) {
//                    for (int leafLayer5Z = -1; leafLayer5Z <= 1; ++leafLayer5Z) {
//                        if (!((leafLayer5X == -1 || leafLayer5X == 1) && (leafLayer5Z == -1 || leafLayer5Z == 1)))
//                            this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(leafLayer5X, 0, leafLayer5Z), boundsIn);
//                    }
//                }
//
//                blockpos2 = blockpos2.down(3);
//                for (int leafLayer2X = -3; leafLayer2X <= 3; ++leafLayer2X) {
//                    for (int leafLayer2Z = -3; leafLayer2Z <= 3; ++leafLayer2Z) {
//                        if (!((leafLayer2X == -3 || leafLayer2X == 3) && (leafLayer2Z == -3 || leafLayer2Z == 3)))
//                            this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(leafLayer2X, 0, leafLayer2Z), boundsIn);
//                    }
//                }
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }
//
//    private void placeLogAt(Set<BlockPos> setPos, IWorldWriter writer, BlockPos pos, MutableBoundingBox boundingBox, boolean isBaseWood) {
//        BlockState blockType = TRUNK;
//        if (isBaseWood) {
//            blockType = BASE;
//        }
//        this.setLogState(setPos, writer, pos, blockType, boundingBox);
//    }
//
//    private void placeLogAt(Set<BlockPos> setPos, IWorldWriter writer, BlockPos pos, MutableBoundingBox boundingBox, Direction.Axis setAxis) {
//        BlockState blockType = TRUNK.getBlock().getDefaultState().with(LogBlock.AXIS, setAxis);
//        this.setLogState(setPos, writer, pos, blockType, boundingBox);
//    }
//
//    private void placeLeafAt(Set<BlockPos> worldIn, IWorldGenerationReader writer, BlockPos pos, MutableBoundingBox boundingBox) {
//        if (isAirOrLeaves(writer, pos)) {
//            this.setLogState(worldIn, writer, pos, LEAF, boundingBox);
//        }
//    }
}