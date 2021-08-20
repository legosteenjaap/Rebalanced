package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonElement;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.profiler.Profiler;
import nl.tettelaar.rebalanced.api.RecipeAPI;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

	@Shadow private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;
	
	
	@Inject(method = "apply", at = @At("RETURN"))
	protected void apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
		HashMap<RecipeType<?>, Map<Identifier, Recipe<?>>> newRecipes = new HashMap(this.recipes);

		HashMap<Identifier, Recipe<?>> craftingRecipes = new HashMap(newRecipes.get(RecipeType.CRAFTING));

		
		for (Identifier blockRecipe : RecipeAPI.getBlockRecipeList()) {
			craftingRecipes.put(blockRecipe, new ShapedRecipe(blockRecipe, "", 1, 1, DefaultedList.copyOf(Ingredient.ofItems(Items.AIR), Ingredient.ofItems(Items.AIR)), new ItemStack(Blocks.AIR)));
		}
		newRecipes.remove(RecipeType.CRAFTING);
		newRecipes.put(RecipeType.CRAFTING, craftingRecipes);
		this.recipes = newRecipes;
		
	}

}
