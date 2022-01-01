package nl.tettelaar.rebalanced.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import com.google.common.collect.Lists;
import nl.tettelaar.rebalanced.api.RecipeAPI;

public class RecipeUtil {

	public static ItemStack createKnowledgeBook(List<ResourceLocation> recipes) {
		ListTag listTag = new ListTag();
		int index = 0;
		for (ResourceLocation recipe : recipes) {
			listTag.add(index, StringTag.valueOf(recipe.toString()));
			index++;
		}

		ItemStack itemStack = new ItemStack(Items.KNOWLEDGE_BOOK.asItem());
		itemStack.getOrCreateTag().put("Recipes", listTag);
		return itemStack;
	}

	public static ItemStack createKnowledgeBook(List<ResourceLocation> recipes, Random random) {
		return createKnowledgeBook(recipes.get(random.nextInt(recipes.size())));
	}

	public static ItemStack createKnowledgeBook(ResourceLocation recipe) {
		return createKnowledgeBook(Arrays.asList(recipe));
	}

	// THIS CODE GETS LIST OF RECIPES

	public static List<Recipe<?>> getRecipes(CompoundTag compoundTag, Level world) {
		ListTag listTag = compoundTag.getList("Recipes", 8);
		List<Recipe<?>> list = Lists.newArrayList();
		RecipeManager recipeManager = null;
		if (!world.isClientSide()) {
			recipeManager = world.getServer().getRecipeManager();
		} else {
			recipeManager = ((ClientLevel) world).getRecipeManager();
		}

		for (int i = 0; i < listTag.size(); ++i) {
			String string = listTag.getString(i);
			Optional<? extends Recipe<?>> optional = recipeManager.byKey(new ResourceLocation(string));
			if (optional.isPresent()) {
				list.add(optional.get());
			}
		}
		return list;
	}

	// Returns null if there are recipes with other outputs
	public static ItemStack getRecipeOutput(CompoundTag compoundTag, Level world) {
		ItemStack output = null;
		for (Recipe<?> recipe : RecipeUtil.getRecipes(compoundTag, world)) {
			if (output == null || recipe.getResultItem().is(output.getItem())) {
				output = recipe.getResultItem();
			} else {
				return null;
			}
		}
		return output;
	}

	public static boolean hasAllRequiredRecipes(CompoundTag compoundTag, Level world, ServerPlayer player) {
		List<ResourceLocation> recipesID = RecipeAPI.getRequiredRecipes(Registry.ITEM.getKey((getRecipeOutput(compoundTag, world).getItem())));
		if (recipesID != null) {
			for (ResourceLocation recipeID : recipesID) {
				if (!player.getRecipeBook().contains(recipeID)) {
					player.displayClientMessage(new TranslatableComponent("item.knowledge_book.require_recipe"), true);
					return false;
				}
			}
		}
		return true;
	}

	public static boolean playerHasAllRecipes(CompoundTag compoundTag, Level world, ServerPlayer player) {
		for (Recipe<?> recipe : RecipeUtil.getRecipes(compoundTag, world)) {
			if (!player.getRecipeBook().contains(recipe))
				return false;
		}
		return true;
	}

	public static boolean isUnlockable (Player player, int XPCOST) {
		return player.experienceLevel >= XPCOST || player.isCreative();
	}
}
