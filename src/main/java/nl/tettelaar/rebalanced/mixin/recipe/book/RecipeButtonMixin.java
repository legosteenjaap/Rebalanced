package nl.tettelaar.rebalanced.mixin.recipe.book;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.recipe.interfaces.RecipeCollectionInterface;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

@Mixin(RecipeButton.class)
public abstract class RecipeButtonMixin extends AbstractWidget {

    @Shadow @Final
    private static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");

    @Shadow private RecipeBook book;

    @Shadow private float animationTime;

    @Shadow private RecipeCollection collection;

    @Shadow private float time;

    @Shadow private int currentIndex;

    @Shadow private RecipeBookMenu<?> menu;


    public RecipeButtonMixin(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    /*@Overwrite
    public void renderButton(PoseStack poseStack, int i, int j, float f) {
        if (!Screen.hasControlDown()) {
            this.time += f;
        }
        List<Recipe<?>> orderedRecipes = this.getOrderedRecipes();
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
        int k = 29;
        if (!this.collection.hasCraftable()) {
            k += 25;
        }

        int l = 206;

        /*if (((RecipeCollectionInterface)(Object)collection).isDiscovered(orderedRecipes.get(currentIndex))) {
            l = l - 25;
        }*

        /*if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
            l += 25;
        }*
        boolean bl = this.animationTime > 0.0f;
        PoseStack poseStack2 = RenderSystem.getModelViewStack();
        if (bl) {
            float g = 1.0f + 0.1f * (float)Math.sin(this.animationTime / 15.0f * (float)Math.PI);
            poseStack2.pushPose();
            poseStack2.translate(this.x + 8, this.y + 12, 0.0);
            poseStack2.scale(g, g, 1.0f);
            poseStack2.translate(-(this.x + 8), -(this.y + 12), 0.0);
            RenderSystem.applyModelViewMatrix();
            this.animationTime -= f;
        }
        this.blit(poseStack, this.x, this.y, k, l, this.width, this.height);

        this.currentIndex = Mth.floor(this.time / 30.0f) % orderedRecipes.size();
        ItemStack itemStack = orderedRecipes.get(this.currentIndex).getResultItem();
        int m = 4;
        if (this.collection.hasSingleResultItem() && this.getOrderedRecipes().size() > 1) {
            minecraft.getItemRenderer().renderAndDecorateItem(itemStack, this.x + m + 1, this.y + m + 1, 0, 10);
            --m;
        }
        minecraft.getItemRenderer().renderAndDecorateFakeItem(itemStack, this.x + m, this.y + m);
        RenderSystem.depthFunc(0x207);
        RenderSystem.enableTexture();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
        this.blit(poseStack, this.x + 13, this.y + 13, 0, 247, 9, 9);
        if (bl) {
            poseStack2.popPose();
            RenderSystem.applyModelViewMatrix();
        }


    }*/

    @Inject(method = "renderButton", at = @At("RETURN"))
    public void renderButton(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        Recipe<?> recipe = this.getOrderedRecipes().get(currentIndex);
        Minecraft minecraft = Minecraft.getInstance();
        if (((RecipeCollectionInterface)(Object)collection).isDiscovered(recipe) && !minecraft.player.isCreative()) {
            boolean enoughXP = minecraft.player.experienceLevel >= RecipeAPI.getRecipeXPCost(recipe.getResultItem().getItem()).get();
            RenderSystem.depthFunc(0x207);
            RenderSystem.enableTexture();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
            this.blit(poseStack, this.x + 13, this.y + 13, enoughXP ? 0 : 10, 247, 9, 9);
        }
    }

    @Shadow
    private List<Recipe<?>> getOrderedRecipes() {
        throw new AssertionError();
    }

}
