package nl.tettelaar.rebalanced.mixin.recipe.discover;

import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RecipeButton.class)
public class RecipeButtonMixin {
    @ModifyConstant(method = "renderButton", constant = @Constant(intValue = 29, ordinal = 0))
    private int setUnlockable(int value) {
        return 231;
    }
}
