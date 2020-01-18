package com.vulp.druidcraft.items;

import com.vulp.druidcraft.api.IKnifeable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class KnifeItem extends Item {

    public KnifeItem(Item.Settings properties) {
        super(properties);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResult.PASS;

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof IKnifeable) {
            ActionResult result = ((IKnifeable) state.getBlock()).onKnife(context);
            if (result != ActionResult.PASS) {
                return result;
            }
        }

        if (player.isSneaking() && state.contains(Properties.HORIZONTAL_FACING)) {
            BlockState state1 = cycleProperty(state, Properties.HORIZONTAL_FACING);
            world.setBlockState(pos, state1, 18);
            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }

    private static <T extends Comparable<T>> BlockState cycleProperty(BlockState state, Property<T> propertyIn) {
        return state.with(propertyIn, getAdjacentValue(propertyIn.getValues(), state.get(propertyIn)));
    }

    private static <T> T getAdjacentValue(Iterable<T> itr, @Nullable T current) {
        return Util.next(itr, current);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (worldIn == null) return;
        tooltip.add(new TranslatableText("item.druidcraft.knife.description1"));
    }
}
