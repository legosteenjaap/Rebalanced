package nl.tettelaar.rebalanced.mixin.recipe;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends HandledScreen<CraftingScreenHandler> implements RecipeBookProvider {

    @Shadow @Final private RecipeBookWidget recipeBook = new RecipeBookWidget();

    public CraftingScreenMixin(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "drawBackground", at = @At("RETURN"), cancellable = true)
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        MinecraftClient client = ((RecipeBookWidgetInvoker)recipeBook).getClient();
        CraftingInventory input = ((CraftingScreenHandlerInvoker)this.handler).getInput();
        Optional<CraftingRecipe> recipe = client.world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, input, client.world);
        if (recipe.isPresent() && !input.isEmpty() && !((RecipeBookWidgetInvoker)recipeBook).getRecipeBook().contains(recipe.get()) && ((RecipeBookWidgetInvoker)recipeBook).getClient().world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING)) {
            //this.drawTexture(matrices, i + 87, j + 33, this.backgroundWidth + 1, 0, 28, 21);
            this.textRenderer.drawWithShadow(matrices, Text.of("TESTTESTTESTTESTTESTTEST"), i + 87f, j + 53f, 8453920);
        }
    }


}
