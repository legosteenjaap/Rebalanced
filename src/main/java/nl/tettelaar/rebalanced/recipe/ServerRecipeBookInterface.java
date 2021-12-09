package nl.tettelaar.rebalanced.recipe;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Collection;

public interface ServerRecipeBookInterface {

    public int discoverRecipes(Collection<Recipe<?>> recipes, ServerPlayer player);

}
