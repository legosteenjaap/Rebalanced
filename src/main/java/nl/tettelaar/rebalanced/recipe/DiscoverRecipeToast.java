package nl.tettelaar.rebalanced.recipe;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class DiscoverRecipeToast implements Toast {
    private static final long DISPLAY_TIME = 5000L;
    private static final Component TITLE_TEXT = new TranslatableComponent("recipe.toast.discover.title");
    private static final Component DESCRIPTION_TEXT = new TranslatableComponent("recipe.toast.discover.description");
    private final List<Recipe<?>> recipes = Lists.newArrayList();
    private long lastChanged;
    private boolean changed;

    public DiscoverRecipeToast(Recipe<?> recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public Toast.Visibility render(PoseStack poseStack, ToastComponent toastComponent, long l) {
        if (this.changed) {
            this.lastChanged = l;
            this.changed = false;
        }
        if (this.recipes.isEmpty()) {
            return Toast.Visibility.HIDE;
        }
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        toastComponent.blit(poseStack, 0, 0, 0, 32, this.width(), this.height());
        toastComponent.getMinecraft().font.draw(poseStack, TITLE_TEXT, 30.0f, 7.0f, -11534256);
        toastComponent.getMinecraft().font.draw(poseStack, DESCRIPTION_TEXT, 30.0f, 18.0f, -16777216);
        Recipe<?> recipe = this.recipes.get((int)(l / Math.max(1L, 5000L / (long)this.recipes.size()) % (long)this.recipes.size()));
        ItemStack itemStack = recipe.getToastSymbol();
        PoseStack poseStack2 = RenderSystem.getModelViewStack();
        poseStack2.pushPose();
        poseStack2.scale(0.6f, 0.6f, 1.0f);
        RenderSystem.applyModelViewMatrix();
        toastComponent.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(itemStack, 3, 3);
        poseStack2.popPose();
        RenderSystem.applyModelViewMatrix();
        toastComponent.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(recipe.getResultItem(), 8, 8);
        return l - this.lastChanged >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    private void addItem(Recipe<?> recipe) {
        this.recipes.add(recipe);
        this.changed = true;
    }

    public static void discoverOrUpdate(ToastComponent toastComponent, Recipe<?> recipe) {
        DiscoverRecipeToast recipeToast = toastComponent.getToast(DiscoverRecipeToast.class, NO_TOKEN);
        if (recipeToast == null) {
            toastComponent.addToast(new DiscoverRecipeToast(recipe));
        } else {
            recipeToast.addItem(recipe);
        }
    }

}
