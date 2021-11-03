package nl.tettelaar.rebalanced.mixin.recipe;

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

    @Shadow @Final private RecipeBookComponent recipeBook = new RecipeBookComponent();

    public CraftingScreenMixin(CraftingMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Inject(method = "drawBackground", at = @At("RETURN"), cancellable = true)
    protected void drawBackground(PoseStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        Minecraft client = ((RecipeBookWidgetInvoker)recipeBook).getMinecraft();
        CraftingContainer input = ((CraftingScreenHandlerInvoker)this.menu).getCraftSlots();
        Optional<CraftingRecipe> recipe = client.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, client.level);
        if (recipe.isPresent() && !input.isEmpty() && !((RecipeBookWidgetInvoker)recipeBook).getBook().contains(recipe.get()) && ((RecipeBookWidgetInvoker)recipeBook).getMinecraft().level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING)) {
            //this.drawTexture(matrices, i + 87, j + 33, this.backgroundWidth + 1, 0, 28, 21);
            this.font.drawShadow(matrices, Component.nullToEmpty("TESTTESTTESTTESTTESTTEST"), i + 87f, j + 53f, 8453920);
        }
    }


}
