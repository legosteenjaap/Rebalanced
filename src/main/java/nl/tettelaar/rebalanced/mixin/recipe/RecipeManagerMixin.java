package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonElement;
import nl.tettelaar.rebalanced.api.RecipeAPI;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

	@Shadow private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;
	
	@Inject(method = "apply", at = @At("RETURN"))
	protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
		HashMap<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> newRecipes = new HashMap<>(this.recipes);

		HashMap<ResourceLocation, Recipe<? extends Container>> craftingRecipes = new HashMap<>(newRecipes.get(RecipeType.CRAFTING));

		
		for (ResourceLocation blockRecipe : RecipeAPI.getBlockRecipeList()) {
			craftingRecipes.put(blockRecipe, new ShapedRecipe(blockRecipe, "", 1, 1, NonNullList.of(Ingredient.of(Items.AIR), Ingredient.of(Items.AIR)), new ItemStack(Blocks.AIR)));
		}
		newRecipes.remove(RecipeType.CRAFTING);
		newRecipes.put(RecipeType.CRAFTING, craftingRecipes);
		this.recipes = newRecipes;
		
	}

}
