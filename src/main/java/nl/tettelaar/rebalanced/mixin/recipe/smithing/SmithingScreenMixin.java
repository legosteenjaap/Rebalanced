package nl.tettelaar.rebalanced.mixin.recipe.smithing;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ItemCombinerMenu;
import nl.tettelaar.rebalanced.recipe.interfaces.SmithingMenuInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(SmithingScreen.class)
public class SmithingScreenMixin <T extends ItemCombinerMenu> extends ItemCombinerScreen<T> {

    private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation("textures/gui/container/anvil.png");
    @Shadow @Final
    private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation("textures/gui/container/smithing.png");

    public SmithingScreenMixin(T itemCombinerMenu, Inventory inventory, Component component, ResourceLocation resourceLocation) {
        super(itemCombinerMenu, inventory, component, resourceLocation);
    }

    @Inject(method = "renderLabels", at = @At("RETURN"), cancellable = true)
    protected void renderLabels(PoseStack poseStack, int i, int j, CallbackInfo ci) {
        RenderSystem.disableBlend();
        super.renderLabels(poseStack, i, j);
        SmithingMenuInterface smithingMenuInterface = ((SmithingMenuInterface)(Object)this.menu);
        Optional<Integer> XPCost = smithingMenuInterface.getXPCost();
        if (!smithingMenuInterface.canUseRecipe() && XPCost.isPresent()) {
            Component component = new TranslatableComponent("container.unlock.cost", XPCost.get());
            int color;
            if (smithingMenuInterface.isUnlockable()) {
                color = 8453920;
            } else {
                color = 0xFF6060;
            }
                RenderSystem.setShaderTexture(0, ANVIL_LOCATION);
                int x = this.imageWidth - 63 - this.font.width(component) / 2;
            int y = 69;
            AnvilScreen.fill(poseStack, x - 2, 67, x + this.font.width(component) + 2, 79, 0x4F000000);
            this.font.drawShadow(poseStack, component, (float)x, 69.0f, color);
        }



    }
    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, this.SMITHING_LOCATION);
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, k, l, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(poseStack, k + 59, l + 20, 0, this.imageHeight + (((ItemCombinerMenu)this.menu).getSlot(0).hasItem() ? 0 : 16), 110, 16);
        SmithingMenuInterface smithingMenuInterface = ((SmithingMenuInterface)(Object)this.menu);
        if ((((ItemCombinerMenu)this.menu).getSlot(0).hasItem() || ((ItemCombinerMenu)this.menu).getSlot(1).hasItem()) && (!smithingMenuInterface.isUnlockable() && !smithingMenuInterface.canUseRecipe())) {
            this.blit(poseStack, k + 99, l + 45, this.imageWidth, 0, 28, 21);
        }
    }
}
