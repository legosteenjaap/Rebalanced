package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerAdvancementManager.class)
public class AdvancementManagerMixin {

	@Inject(method = "lambda$apply$0", at = @At("HEAD"))
	private void removeAdvancements(Map<ResourceLocation, JsonElement> map, ResourceLocation resourceLocation, JsonElement jsonElemen, CallbackInfo ci) {
		JsonElement rewards = null;
		try {
			rewards = map.get(resourceLocation).getAsJsonObject().get("rewards");
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
						map.remove(id);
					}
				}
			}
		}
	}
}
