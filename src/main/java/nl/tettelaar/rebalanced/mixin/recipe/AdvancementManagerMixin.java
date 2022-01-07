package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.*;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.world.item.crafting.RecipeManager;
import nl.tettelaar.rebalanced.recipe.interfaces.AdvancementRewardsInterface;
import nl.tettelaar.rebalanced.recipe.RecipeStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerAdvancementManager.class)
public class AdvancementManagerMixin {

	@Redirect(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/AdvancementList;add(Ljava/util/Map;)V"))
	private void removeAdvancements(AdvancementList advancementList, Map<ResourceLocation, Advancement.Builder> map) {
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
						JsonElement recipeJSON = iterator.next();
						ResourceLocation id = new ResourceLocation(recipeJSON.getAsString());
						List<ResourceLocation> removedRecipes = RecipeAPI.getRemovedRecipeAdvancements();
						List<ResourceLocation> discoverableRecipes = RecipeAPI.getDiscoverableRecipes(RecipeAPI.recipeManager);
						if (!RecipeAPI.updatedWithRecipeManager) throw new AssertionError();
						if (discoverableRecipes != null && discoverableRecipes.contains(id)) {
							AdvancementRewardsInterface advancementRewardsInterface = ((AdvancementRewardsInterface)((AdvancementBuilderAccessor)map.get(idAdvancement)).getRewards());
							if (!advancementRewardsInterface.hasRecipeStatus()) {
								advancementRewardsInterface.setRecipeStatus(RecipeStatus.DISCOVERED);
							}
						} else if (removedRecipes != null && removedRecipes.contains(id)) {
							hashMap.remove(idAdvancement);
						}
					}
				}
			}
		}
		advancementList.add(hashMap);
	}

}
