package nl.tettelaar.rebalanced.mixin.recipe.book;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.recipe.interfaces.RecipeBookInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(RecipeButton.class)
public abstract class RecipeButtonMixin extends AbstractWidget {

    @Shadow @Final
    private static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");

    @Shadow private RecipeBook book;

    @Shadow private int currentIndex;

    public RecipeButtonMixin(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    @Inject(method = "renderButton", at = @At("RETURN"))
    public void renderButton(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        Recipe<?> recipe = this.getOrderedRecipes().get(currentIndex);
        Minecraft minecraft = Minecraft.getInstance();
        if (((RecipeBookInterface)(Object)book).isDiscovered(recipe) && !minecraft.player.isCreative()) {
            Optional<Integer> XPCost = RecipeAPI.getItemXPCost(recipe.getResultItem().getItem());
            if (XPCost.isPresent()) {
                boolean enoughXP = minecraft.player.experienceLevel >= XPCost.get();
                RenderSystem.depthFunc(0x207);
                RenderSystem.enableTexture();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
                this.blit(poseStack, this.x + 13, this.y + 13, enoughXP ? 0 : 10, 247, 9, 9);
            }
        }
    }

    @Shadow
    private List<Recipe<?>> getOrderedRecipes() {
        throw new AssertionError();
    }

}
