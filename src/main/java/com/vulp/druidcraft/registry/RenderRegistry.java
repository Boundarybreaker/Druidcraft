package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.gui.screen.inventory.BeetleInventoryScreen;
import com.vulp.druidcraft.client.renders.BeetleEntityRender;
import com.vulp.druidcraft.client.renders.DreadfishEntityRender;
import com.vulp.druidcraft.client.renders.LunarMothEntityRender;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RenderRegistry
{
    public static void registryRenders()
    {
        // ENTITIES
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.beetle_entity, (entityRenderDispatcher, context) -> new BeetleEntityRender(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.dreadfish_entity, (entityRenderDispatcher, context) -> new DreadfishEntityRender(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.lunar_moth_entity, (entityRenderDispatcher, context) -> new LunarMothEntityRender(entityRenderDispatcher));

        // SCREENS
        ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier(Druidcraft.MODID, "beetle_inv"), (syncid, id, player, buf) -> {
            int beetleId = buf.readInt();
            Text label = buf.readText();
            return new BeetleInventoryScreen(syncid, player.inventory, label, beetleId);
        });

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                BlockRegistry.darkwood_leaves,
                BlockRegistry.elder_leaves,
                BlockRegistry.darkwood_sapling,
                BlockRegistry.elder_sapling,
                BlockRegistry.potted_darkwood_sapling,
                BlockRegistry.potted_elder_sapling,
                BlockRegistry.rope_lantern,
                BlockRegistry.rope,
                BlockRegistry.ceramic_lantern,
                BlockRegistry.fiery_torch,
                BlockRegistry.wall_fiery_torch,
                BlockRegistry.blueberry_bush,
                BlockRegistry.hemp_crop);
    }
}
