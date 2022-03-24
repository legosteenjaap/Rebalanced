package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.storage.loot.PredicateManager;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.recipe.interfaces.AdvancementRewardsInterface;
import nl.tettelaar.rebalanced.recipe.RecipeStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

	@Shadow private AdvancementList advancements = new AdvancementList();

	@Shadow @Final
	private PredicateManager predicateManager;

	/*@Redirect(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Maps;newHashMap()Ljava/util/HashMap;"))
	private HashMap<ResourceLocation, Advancement.Builder> injected() {

	}*/

	@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("RETURN"), cancellable = true)
	protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
		HashMap<ResourceLocation, Advancement.Builder> newAdvancements = Maps.newHashMap();
		Advancement.Builder root = AdvancementBuilderAccessor.initBuilder();
		for (Recipe<?> recipe : RecipeAPI.recipeManager.getRecipes()) {
			if (RecipeAPI.needsRecipeAdvancement(recipe.getResultItem().getItem()) && !RecipeAPI.getRemovedRecipeAdvancements().contains(recipe.getId())) {
				ResourceLocation advancementID = new ResourceLocation(recipe.getId().getNamespace(), "recipes/" + recipe.getResultItem().getItem().getItemCategory().getRecipeFolderName() + "/" + recipe.getId().getPath());
				Advancement.Builder builder = AdvancementBuilderAccessor.initBuilder();
				AdvancementRewards.Builder rewards = AdvancementRewards.Builder.recipe(recipe.getId());
				ArrayList<String> criteria = new ArrayList<>();
				for (Ingredient ingredient : recipe.getIngredients()) {
					for (ItemStack itemStack : ingredient.getItems()) {
						ResourceLocation resourceLocation = Registry.ITEM.getKey(itemStack.getItem());
						if (!criteria.contains("has_" + resourceLocation.getPath())) {
							JsonObject criterion = new JsonObject();
							criterion.addProperty("trigger", "minecraft:inventory_changed");
							criterion.add("conditions", JsonParser.parseString("{\"items\":[{\"items\": [\"" + resourceLocation.toString() + "\"]}]}"));
							builder.addCriterion("has_" + resourceLocation.getPath(), Criterion.criterionFromJson(criterion, new DeserializationContext(advancementID, this.predicateManager)));
							criteria.add("has_" + resourceLocation.getPath());
						}
					}
				}
				builder.parent(new ResourceLocation("minecraft:recipes/root"));
				builder.rewards(rewards.build());
				builder.requirements(RequirementsStrategy.OR.createRequirements(criteria));
				newAdvancements.put(advancementID, builder);
			}
		}
		advancements.add(newAdvancements);
	}


	@Redirect(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/AdvancementList;add(Ljava/util/Map;)V"))
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
