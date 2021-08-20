package nl.tettelaar.rebalanced.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.spongepowered.asm.mixin.Unique;

import com.google.common.collect.Lists;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class RecipeUtil {

	public static ItemStack createKnowledgeBook(List<Identifier> recipes) {
		NbtList listTag = new NbtList();
		int index = 0;
		for (Identifier recipe : recipes) {
			listTag.add(index, NbtString.of(recipe.toString()));
			index++;
		}
		
		NbtCompound tag = (NbtCompound) new NbtCompound().put("Recipes", listTag);
		ItemStack itemStack = new ItemStack(Items.KNOWLEDGE_BOOK.asItem());
		itemStack.getOrCreateTag().put("Recipes", listTag);
		return itemStack;
	}
	
	public static ItemStack createKnowledgeBook(List<Identifier> recipes, Random random) {
		return createKnowledgeBook(recipes.get(random.nextInt(recipes.size())));
	}
	
	public static ItemStack createKnowledgeBook(Identifier recipe) {
		return createKnowledgeBook(Arrays.asList(recipe));
	}
	
	// THIS CODE GETS LIST OF RECIPES

	@Unique
	public
	static List<Recipe<?>> getRecipes(NbtCompound compoundTag, World world) {
		NbtList listTag = compoundTag.getList("Recipes", 8);
		List<Recipe<?>> list = Lists.newArrayList();
		RecipeManager recipeManager = null;
		if (!world.isClient()) {
			recipeManager = world.getServer().getRecipeManager();
		} else {
			recipeManager = ((ClientWorld) world).getRecipeManager();
		}

		for (int i = 0; i < listTag.size(); ++i) {
			String string = listTag.getString(i);
			Optional<? extends Recipe<?>> optional = recipeManager.get(new Identifier(string));
			if (optional.isPresent()) {
				list.add(optional.get());
			}
		}
		return list;
	}

}
