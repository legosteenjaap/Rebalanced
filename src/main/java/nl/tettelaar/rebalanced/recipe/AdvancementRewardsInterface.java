package nl.tettelaar.rebalanced.recipe;

import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.resources.ResourceLocation;

public interface AdvancementRewardsInterface {

    public void setRecipeStatus(RecipeStatus status);
    public boolean hasRecipeStatus();

}
