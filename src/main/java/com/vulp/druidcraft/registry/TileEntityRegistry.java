package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
import com.vulp.druidcraft.blocks.tileentities.GrowthLampTileEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TileEntityRegistry {

    public static BlockEntityType<CrateTileEntity> crate;
    public static BlockEntityType<GrowthLampTileEntity> growth_lamp;

    public static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.Builder<T> builder)
    {
        BlockEntityType<T> type = builder.build(null);
        return Registry.register(Registry.BLOCK_ENTITY, new Identifier(Druidcraft.MODID, id), type);
    }
}
