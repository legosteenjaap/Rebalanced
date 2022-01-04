package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.network.NetworkingClient;
import nl.tettelaar.rebalanced.network.NetworkingServer;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceMenuInterface;
import nl.tettelaar.rebalanced.recipe.interfaces.RecipeBookInterface;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin extends AbstractContainerScreen<FurnaceMenu> {

    private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation("textures/gui/container/anvil.png");

    public AbstractFurnaceScreenMixin(FurnaceMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractFurnaceScreen;renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;II)V"))
    protected void render(AbstractFurnaceScreen screen, PoseStack poseStack, int mouseX, int mouseY) {
        FurnaceMenuInterface furnaceMenuInterface = (FurnaceMenuInterface)(Object)this.menu;
        Optional<Integer> XPCost = furnaceMenuInterface.getXPCost();
        Recipe<?> recipe = furnaceMenuInterface.getRecipe();
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        if (XPCost.isPresent()) {
            Component component = new TranslatableComponent("container.unlock.cost", XPCost.get());
            if (recipe != null && RecipeUtil.isUnlockable(minecraft.player, XPCost.get(), recipe)) {
                CraftingScreen.fill(poseStack, this.leftPos + 80 - 2, y + 60 - 2, this.leftPos + 82 + this.font.width(component), y + 60 + 10, 0x4F000000);
                this.font.drawShadow(poseStack, component, this.leftPos + 80, y + 60f, 8453920);
            } else if (recipe != null && ((RecipeBookInterface)this.minecraft.player.getRecipeBook()).isDiscovered(recipe)) {
                RenderSystem.setShaderTexture(0, ANVIL_LOCATION);
                this.blit(poseStack, this.leftPos + 78, y + 32, this.imageWidth + 1, 0, 28, 21);
                CraftingScreen.fill(poseStack,this.leftPos + 80 - 2, y + 60 - 2, this.leftPos + 82 + this.font.width(component) , y + 60 + 10, 0x4F000000);
                this.font.drawShadow(poseStack, component, this.leftPos + 80, y + 60f, 0xFF6060);
            } else if (!minecraft.player.getRecipeBook().contains(recipe)) {
                RenderSystem.setShaderTexture(0, ANVIL_LOCATION);
                this.blit(poseStack, this.leftPos + 78, y + 32, this.imageWidth + 1, 0, 28, 21);
            }
        }
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Inject(method = "removed", at = @At("HEAD"))
    public void removed(CallbackInfo ci) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(NetworkingClient.lastFurnacePos);
        ClientPlayNetworking.send(NetworkingClient.REMOVE_FURNACE_INSPECT, buf);
    }
}
