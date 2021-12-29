package nl.tettelaar.rebalanced.mixin.recipe.crafting;

import net.minecraft.network.chat.TranslatableComponent;
import nl.tettelaar.rebalanced.mixin.recipe.book.RecipeBookWidgetInvoker;
import nl.tettelaar.rebalanced.recipe.interfaces.CraftingMenuInterface;
import nl.tettelaar.rebalanced.recipe.interfaces.ResultContainerInterface;
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

    public CraftingScreenMixin(CraftingMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Inject(method = "renderBg", at = @At("RETURN"), cancellable = true)
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        Minecraft client = ((RecipeBookWidgetInvoker)recipeBookComponent).getMinecraft();
        CraftingContainer input = ((CraftingScreenHandlerInvoker)this.menu).getCraftSlots();
        Optional<CraftingRecipe> recipe = client.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, client.level);
        if (recipe.isPresent() && !input.isEmpty() && !((RecipeBookWidgetInvoker)recipeBookComponent).getBook().contains(recipe.get()) && ((RecipeBookWidgetInvoker)recipeBookComponent).getMinecraft().level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING)) {
            ResultContainerInterface resultContainer = ((ResultContainerInterface)((CraftingMenuInterface)this.menu).getResultContainer());
            if (!minecraft.player.isCreative()) {
                if (resultContainer.getXPCost().isPresent()) {
                    Component component = new TranslatableComponent("container.unlock.cost", resultContainer.getXPCost().get());
                    if (resultContainer.isUnlockable(minecraft.player)) {
                        CraftingScreen.fill(matrices, x + 167 - 2, y + 60 - 2, x + 167 + this.font.width(component), y + 60 + 12, 0x4F000000);
                        this.font.drawShadow(matrices, component, x + 167f, y + 60f, 8453920);
                    } else {
                        this.blit(matrices, x + 164, y + 33, this.imageWidth + 1, 0, 28, 21);
                        CraftingScreen.fill(matrices,x + 167 - 2, y + 60 - 2, x + 167 + this.font.width(component) , y + 60 + 12, 0x4F000000);
                        this.font.drawShadow(matrices, component, x + 167f, y + 60f, 0xFF6060);
                    }
                } else {
                    this.blit(matrices, x + 164, y + 33, this.imageWidth + 1, 0, 28, 21);
                }
            }
        }
    }


}
