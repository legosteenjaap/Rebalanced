/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package nl.tettelaar.rebalanced.recipe.brewing;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;

@Environment(value=EnvType.CLIENT)
public class BrewingStandScreen
        extends AbstractContainerScreen<BrewingStandMenu> implements RecipeUpdateListener {
    private static final ResourceLocation BREWING_STAND_LOCATION = new ResourceLocation("textures/gui/container/brewing_stand.png");
    private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};
    private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
    private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
    private boolean widthTooNarrow;

    public BrewingStandScreen(BrewingStandMenu brewingStandMenu, Inventory inventory, Component component) {
        super(brewingStandMenu, inventory, component);
        this.widthTooNarrow = this.width < 379;
        this.recipeBookComponent.init(this.width, this.height, Minecraft.getInstance(), this.widthTooNarrow, (RecipeBookMenu)this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, button -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            ((ImageButton)button).setPosition(this.leftPos + 5, this.height / 2 - 49);
        }));
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.recipeBookComponent.tick();
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
            this.renderBg(poseStack, f, i, j);
            this.recipeBookComponent.render(poseStack, i, j, f);
        } else {
            this.recipeBookComponent.render(poseStack, i, j, f);
            super.render(poseStack, i, j, f);
            this.recipeBookComponent.renderGhostRecipe(poseStack, this.leftPos, this.topPos, true, f);
        }
        this.renderTooltip(poseStack, i, j);
        this.recipeBookComponent.renderTooltip(poseStack, this.leftPos, this.topPos, i, j);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {
        int o;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BREWING_STAND_LOCATION);
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, k, l, 0, 0, this.imageWidth, this.imageHeight);
        int m = ((BrewingStandMenu)this.menu).getFuel();
        int n = Mth.clamp((18 * m + 20 - 1) / 20, 0, 18);
        if (n > 0) {
            this.blit(poseStack, k + 60, l + 44, 176, 29, n, 4);
        }
        if ((o = ((BrewingStandMenu)this.menu).getBrewingTicks()) > 0) {
            int p = (int)(28.0f * (1.0f - (float)o / 400.0f));
            if (p > 0) {
                this.blit(poseStack, k + 97, l + 16, 176, 0, 9, p);
            }
            if ((p = BUBBLELENGTHS[o / 2 % 7]) > 0) {
                this.blit(poseStack, k + 63, l + 14 + 29 - p, 185, 29 - p, 12, p);
            }
        }
    }

    @Override
    protected boolean isHovering(int i, int j, int k, int l, double d, double e) {
        return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(i, j, k, l, d, e);
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        if (this.recipeBookComponent.mouseClicked(d, e, i)) {
            this.setFocused(this.recipeBookComponent);
            return true;
        }
        if (this.widthTooNarrow && this.recipeBookComponent.isVisible()) {
            return true;
        }
        return super.mouseClicked(d, e, i);
    }
    @Override
    protected boolean hasClickedOutside(double d, double e, int i, int j, int k) {
        boolean bl = d < (double)i || e < (double)j || d >= (double)(i + this.imageWidth) || e >= (double)(j + this.imageHeight);
        return this.recipeBookComponent.hasClickedOutside(d, e, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, k) && bl;
    }

    @Override
    protected void slotClicked(Slot slot, int i, int j, ClickType clickType) {
        super.slotClicked(slot, i, j, clickType);
        this.recipeBookComponent.slotClicked(slot);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookComponent.recipesUpdated();
    }

    @Override
    public void removed() {
        this.recipeBookComponent.removed();
        super.removed();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookComponent;
    }
}

