package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.blocks.GrowthLampBlock;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.Random;

public class GrowthLampTileEntity extends BlockEntity implements Tickable {

	public GrowthLampTileEntity() {
		this(TileEntityRegistry.growth_lamp);
	}

	public GrowthLampTileEntity(BlockEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public void tick() {
		if (world != null) {
			if (world.random.nextInt(40) == 0) {

				Random random = world.random;

				float yMod = 0.0f;
				if (world.getBlockState(pos).get(GrowthLampBlock.HANGING)) {
					yMod = 0.05f;
				}

				if (world.isClient) {
					for (int i = 0; i < 3; i++) {
						world.addParticle(ParticleRegistry.magic_smoke, (double) pos.getX() + 0.20f + (random.nextInt(7) / 10.0f), pos.getY() + 0.05f + yMod + (random.nextInt(6) / 10.0f), (double) pos.getZ() + 0.20f + (random.nextInt(7) / 10.0f), 215 / 255.0f, 255 / 255.0f, 65 / 255.0f);
					}
				}

				// MOB GROWTH
				List<AnimalEntity> animalEntities = world.getEntities(AnimalEntity.class, new Box(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3), (entity) -> true);
				if (animalEntities.size() != 0) {
					for (int j = 0; j < animalEntities.size(); j++) {
						if (animalEntities.get(j).isBaby()) {
							if (animalEntities.get(j).getBreedingAge() >= 10) {
								animalEntities.get(j).growUp(random.nextInt(2) + 1);
							} else {
								animalEntities.get(j).growUp(1);
							}
						}
					}
				}

				// PLANT GROWTH
				for (int x = -2; x < 3; x++) {
					for (int y = -2; y < 3; y++) {
						for (int z = -2; z < 3; z++) {

							BlockPos newPos = pos.add(x, y, z);

							if (world.getBlockState(newPos).getBlock() instanceof Fertilizable) {
								world.getBlockTickScheduler().schedule(newPos, world.getBlockState(newPos).getBlock(), 0);
							}
						}
					}
				}
			}
		}
	}
}
