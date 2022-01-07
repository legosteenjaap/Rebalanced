package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Tuple;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.Blocks;
import nl.tettelaar.rebalanced.init.Recipes;
import nl.tettelaar.rebalanced.recipe.BlockRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonElement;
import nl.tettelaar.rebalanced.api.RecipeAPI;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

	@Shadow private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;
	@Shadow private Map<ResourceLocation, Recipe<?>> byName = ImmutableMap.of();

	@Inject(method = "apply", at = @At("RETURN"))
	protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
		HashMap<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> newRecipes = new HashMap<>(this.recipes);

		HashMap<ResourceLocation, Recipe<? extends Container>> blockRecipes = new HashMap<>();

		
		for (ResourceLocation entry : RecipeAPI.getBlockRecipeList().keySet()) {
			blockRecipes.put(entry, new BlockRecipe(entry, new ItemStack(RecipeAPI.getBlockRecipeList().get(entry))));
		}
		newRecipes.put(Recipes.BLOCK, blockRecipes);
		this.recipes = newRecipes;
		if (!RecipeAPI.updatedWithRecipeManager)RecipeAPI.updateWithRecipeManager((RecipeManager)(Object)this);
	}

	@ModifyVariable(method = "apply", at = @At("STORE"), ordinal = 0)
	private ImmutableMap.Builder<ResourceLocation, Recipe<?>> addBlockRecipes(ImmutableMap.Builder<ResourceLocation, Recipe<?>> builder) {
		for (ResourceLocation entry : RecipeAPI.getBlockRecipeList().keySet()) {
			builder.put(entry, new BlockRecipe(entry, new ItemStack(RecipeAPI.getBlockRecipeList().get(entry))));
		}
		return builder;
	}

}
