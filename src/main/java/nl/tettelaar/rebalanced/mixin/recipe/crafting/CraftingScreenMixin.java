package nl.tettelaar.rebalanced.mixin.recipe.crafting;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import nl.tettelaar.rebalanced.mixin.recipe.book.RecipeBookWidgetInvoker;
import nl.tettelaar.rebalanced.recipe.interfaces.CraftingMenuInterface;
import nl.tettelaar.rebalanced.recipe.interfaces.RecipeBookInterface;
import nl.tettelaar.rebalanced.recipe.interfaces.ResultContainerInterface;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameRules;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends AbstractContainerScreen<CraftingMenu> implements RecipeUpdateListener {

    @Shadow @Final private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
    private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation("textures/gui/container/anvil.png");

    public CraftingScreenMixin(CraftingMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Inject(method = "renderBg", at = @At("RETURN"), cancellable = true)
    protected void renderBg(PoseStack poseStack, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        Minecraft client = ((RecipeBookWidgetInvoker)recipeBookComponent).getMinecraft();
        CraftingContainer input = ((CraftingMenuInvoker)this.menu).getCraftSlots();
        Optional<CraftingRecipe> recipe = client.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, client.level);
        if (recipe.isPresent() && !input.isEmpty() && !((RecipeBookWidgetInvoker)recipeBookComponent).getBook().contains(recipe.get()) && ((RecipeBookWidgetInvoker)recipeBookComponent).getMinecraft().level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING)) {
            ResultContainerInterface resultContainer = ((ResultContainerInterface)((CraftingMenuInterface)this.menu).getResultContainer());
            if (!minecraft.player.isCreative()) {
                Optional<Integer> XPCost = resultContainer.getXPCost();
                if (XPCost.isPresent() && ((RecipeBookInterface)((RecipeBookWidgetInvoker)recipeBookComponent).getBook()).isDiscovered(recipe.get())) {
                    Component component = new TranslatableComponent("container.unlock.cost", XPCost.get());
                    if (RecipeUtil.isUnlockable(minecraft.player, XPCost.get(), recipe.get())) {
                        CraftingScreen.fill(poseStack, this.leftPos + 90 - 2, y + 60 - 2, this.leftPos + 92 + this.font.width(component), y + 60 + 10, 0x4F000000);
                        this.font.drawShadow(poseStack, component, this.leftPos + 90, y + 60f, 8453920);
                    } else {
                        this.blit(poseStack, this.leftPos + 87, y + 33, this.imageWidth + 1, 0, 28, 21);
                        CraftingScreen.fill(poseStack,this.leftPos + 90 - 2, y + 60 - 2, this.leftPos + 92 + this.font.width(component) , y + 60 + 10, 0x4F000000);
                        this.font.drawShadow(poseStack, component, this.leftPos + 90, y + 60f, 0xFF6060);
                    }
                } else {
                    this.blit(poseStack, this.leftPos + 87, y + 33, this.imageWidth + 1, 0, 28, 21);
                }
            }
        }
    }


}
