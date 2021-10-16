package nl.tettelaar.rebalanced.mixin.recipe;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.recipebook.ClientRecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetInvoker {
    @Accessor
    MinecraftClient getClient();

    @Accessor
    ClientRecipeBook getRecipeBook();
}
