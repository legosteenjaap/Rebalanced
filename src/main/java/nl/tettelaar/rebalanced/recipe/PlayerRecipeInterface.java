package nl.tettelaar.rebalanced.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Collection;

public interface PlayerRecipeInterface {

    public int discoverRecipes(Collection<Recipe<?>> recipes);
    public void discoverRecipesByKey(ResourceLocation[] resourceLocations);

}
