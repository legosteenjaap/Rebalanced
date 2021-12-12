package nl.tettelaar.rebalanced.mixin.recipe.book;

import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeBookComponent.class)
public interface RecipeBookWidgetInvoker {
    @Accessor
    Minecraft getMinecraft();

    @Accessor
    ClientRecipeBook getBook();
}
