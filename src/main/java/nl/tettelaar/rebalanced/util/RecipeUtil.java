package nl.tettelaar.rebalanced.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

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
	
}
