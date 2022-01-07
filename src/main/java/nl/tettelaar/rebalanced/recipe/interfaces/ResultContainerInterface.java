package nl.tettelaar.rebalanced.recipe.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

public interface ResultContainerInterface {

    public boolean isRecipeUnlocked();
    public Optional<Integer> getXPCost(boolean isClient);
    public Recipe<?> getUsedRecipe();

}
