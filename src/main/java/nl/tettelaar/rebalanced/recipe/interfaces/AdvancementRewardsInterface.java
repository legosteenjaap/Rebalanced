package nl.tettelaar.rebalanced.recipe.interfaces;

import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.resources.ResourceLocation;
import nl.tettelaar.rebalanced.recipe.RecipeStatus;

public interface AdvancementRewardsInterface {

    public void setRecipeStatus(RecipeStatus status);
    public boolean hasRecipeStatus();

}
