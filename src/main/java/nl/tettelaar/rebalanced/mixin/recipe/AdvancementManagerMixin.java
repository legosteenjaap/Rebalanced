package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.util.Identifier;
import nl.tettelaar.rebalanced.api.RecipeAPI;

@Mixin(AdvancementManager.class)
public class AdvancementManagerMixin {

	@ModifyVariable(method = "load", at = @At("HEAD"), ordinal = 0)
	private Map<Identifier, Advancement.Task> removeRecipes(Map<Identifier, Advancement.Task> map) {

		HashMap<Identifier, Advancement.Task> hashMap = Maps.newHashMap(map);

		List<Identifier> advancements = new ArrayList<Identifier>(hashMap.keySet());
		for (Identifier idAdvancement : advancements) {
			JsonElement rewards = hashMap.get(idAdvancement).toJson().get("rewards");
			if (rewards.isJsonObject()) {
				JsonElement recipes = rewards.getAsJsonObject().get("recipes");
				if (recipes != null && recipes.isJsonArray()) {
					Iterator<JsonElement> iterator = recipes.getAsJsonArray().iterator();
					while (iterator.hasNext()) {
						JsonElement recipe = iterator.next();
						Identifier id = new Identifier(recipe.getAsString());
						List<Identifier> removedRecipes = RecipeAPI.getRemovedRecipeAdvancements();
						if (removedRecipes != null && removedRecipes.contains(id)) {
							hashMap.remove(idAdvancement);
						}
					}
				}
			}
		}
		return hashMap;
	}

}
