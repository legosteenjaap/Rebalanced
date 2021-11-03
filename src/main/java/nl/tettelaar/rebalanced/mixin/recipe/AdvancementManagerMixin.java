package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import nl.tettelaar.rebalanced.api.RecipeAPI;

@Mixin(AdvancementList.class)
public class AdvancementManagerMixin {

	@ModifyVariable(method = "add", at = @At("HEAD"), ordinal = 0)
	private Map<ResourceLocation, Advancement.Builder> removeRecipes(Map<ResourceLocation, Advancement.Builder> map) {

		HashMap<ResourceLocation, Advancement.Builder> hashMap = Maps.newHashMap(map);

		List<ResourceLocation> advancements = new ArrayList<ResourceLocation>(hashMap.keySet());
		for (ResourceLocation idAdvancement : advancements) {
			JsonElement rewards = null;
			try {
				rewards = hashMap.get(idAdvancement).serializeToJson().get("rewards");
			} catch (NullPointerException e) {
				
			}
			if (rewards != null && rewards.isJsonObject()) {
				JsonElement recipes = rewards.getAsJsonObject().get("recipes");
				if (recipes != null && recipes.isJsonArray()) {
					Iterator<JsonElement> iterator = recipes.getAsJsonArray().iterator();
					while (iterator.hasNext()) {
						JsonElement recipe = iterator.next();
						ResourceLocation id = new ResourceLocation(recipe.getAsString());
						List<ResourceLocation> removedRecipes = RecipeAPI.getRemovedRecipeAdvancements();
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
