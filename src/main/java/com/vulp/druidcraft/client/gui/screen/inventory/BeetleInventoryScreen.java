package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BeetleInventoryScreen extends AbstractInventoryScreen<BeetleInventoryContainer> {
    private static final Identifier BEETLE_GUI_TEXTURES = new Identifier(Druidcraft.MODID, "textures/gui/container/beetle.png");
    private final BeetleEntity beetleEntity;
    private float mousePosx;
    private float mousePosY;

    public BeetleInventoryScreen(int syncId, PlayerInventory inventory, Text text, int beetleId) {
        super(new BeetleInventoryContainer(syncId, inventory, beetleId), inventory, text);
        this.width = 257;
        this.height = 238;
        this.beetleEntity = container.getBeetle();
        this.passEvents = false;
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        this.font.draw(this.title.asFormattedString(), 18.0F, 6.0F, 4210752);
        this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 49.0F, 144.0F, 4210752);
    }

    @Override
    protected void drawBackground(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BEETLE_GUI_TEXTURES);
        int i = (this.width - this.width) / 2;
        int j = (this.height - this.height) / 2;
        this.blit(i, j, 0, 0, this.width, this.height);
        if (this.beetleEntity instanceof BeetleEntity) {
            BeetleEntity beetleEntity = this.beetleEntity;
            if (beetleEntity.hasChest()) {
                for (int k = 0; k < 7; k++) {
                    this.blit(i + 84, j + 17 + (k * 18), 0, this.height, 162, 18);
                }
            }
        }

        // mousePosY NEEDS CALIBRATION!
        InventoryScreen.drawEntity(i + 43, j + 62, 17, (float)(i + 43) - this.mousePosx, (float)(j + 48) - this.mousePosY, this.beetleEntity);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.mousePosx = (float)p_render_1_;
        this.mousePosY = (float)p_render_2_;
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.drawMouseoverTooltip(p_render_1_, p_render_2_);
    }
}
