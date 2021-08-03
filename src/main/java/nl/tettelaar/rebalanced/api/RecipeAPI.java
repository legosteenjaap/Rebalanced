package nl.tettelaar.rebalanced.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import nl.tettelaar.rebalanced.TradeOffers;

public class RecipeAPI {

	private static ArrayList<Pair<List<Identifier>, List<Identifier>>> knowledgeBooksLootTable = new ArrayList<>();
	private static List<Identifier> removedRecipeAdvancements;
	private static HashMap<VillagerProfession, HashMap<Integer, List<KnowledgeBookTrade>>> villagerRecipes = new HashMap<>();
	
	private static void registerKnowledgeBookID(List<Identifier> recipes, List<Identifier> loottables) {
		knowledgeBooksLootTable.add(new Pair<List<Identifier>, List<Identifier>>(recipes, loottables));
	}

	public static void registerKnowledgeBook(List<String> recipes, List<String> loottables, int weight) {

		ArrayList<Identifier> recipesID = new ArrayList<>();
		ArrayList<Identifier> loottablesID = new ArrayList<>();

		for (String recipe : recipes) {
			recipesID.add(new Identifier(recipe));
		}

		for (String loottable : loottables) {
			loottablesID.add(new Identifier(loottable));
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(recipesID, loottablesID);
		}
	}
	
	private static void registerKnowledgeBookID(List<Identifier> recipes, int minPrice, int maxPrice, VillagerProfession villager, int level) {
		if (villagerRecipes.get(villager) == null) {
			HashMap<Integer, List<KnowledgeBookTrade>> tradesMap = new HashMap<>();
			tradesMap.put(level, Arrays.asList(new KnowledgeBookTrade(minPrice, maxPrice, recipes)));
			villagerRecipes.put(villager, tradesMap);
		} else if (villagerRecipes.get(villager).get(level) == null) {
			villagerRecipes.get(villager).put(level, Arrays.asList(new KnowledgeBookTrade(minPrice, maxPrice, recipes)));
		} else {
			ArrayList<KnowledgeBookTrade> trades = new ArrayList<>(villagerRecipes.get(villager).get(level));
			trades.add(new KnowledgeBookTrade(minPrice, maxPrice, recipes));
			villagerRecipes.get(villager).put(level, trades);
		}
	}

	public static void registerKnowledgeBook(List<String> recipes, int minPrice, int maxPrice, VillagerProfession villager, int level, int weight) {
		ArrayList<Identifier> recipesID = new ArrayList<>();

		for (String recipe : recipes) {
			recipesID.add(new Identifier(recipe));
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(recipesID, minPrice, maxPrice, villager, level);
		}
	}
	
	public static List<Identifier> getRemovedRecipeAdvancements() {
		if (RecipeAPI.removedRecipeAdvancements == null) {
			ArrayList<Identifier> removedRecipeAdvancements = new ArrayList<>();
			for (Pair<List<Identifier>, List<Identifier>> entry : getKnowledgeBooksLootTable()) {
				for (Identifier recipe : entry.getLeft()) {
					removedRecipeAdvancements.add(recipe);
				}
			}
			RecipeAPI.removedRecipeAdvancements = removedRecipeAdvancements;
		}
		return RecipeAPI.removedRecipeAdvancements;
	}

	public static ArrayList<Pair<List<Identifier>, List<Identifier>>> getKnowledgeBooksLootTable() {
		return knowledgeBooksLootTable;
	}
	
	public static List<KnowledgeBookTrade> getKnowledgeBooksVillager(VillagerProfession villager, int level) {
		HashMap<Integer, List<KnowledgeBookTrade>> map = villagerRecipes.get(villager);
		if (map != null) return map.get(level);
		return null;
	}

	private static class KnowledgeBookTrade implements TradeOffers.Factory {
		
		private final int minPrice;
		private final int maxPrice;
		private final List<Identifier> recipes;
		
		private KnowledgeBookTrade(int minPrice, int maxPrice, List<Identifier> recipes) {
			this.minPrice = minPrice;
			this.maxPrice = maxPrice;
			this.recipes = recipes;
		}

		@Override
		public @Nullable TradeOffer create(Entity entity, Random random) {
			NbtList listTag = new NbtList();
			listTag.add(0, NbtString.of(recipes.get(random.nextInt(recipes.size())).toString()));
			NbtCompound tag = (NbtCompound) new NbtCompound().put("Recipes", listTag);
			ItemStack itemStack = new ItemStack(Items.KNOWLEDGE_BOOK.asItem());
			itemStack.getOrCreateTag().put("Recipes", listTag);
			return new TradeOffer(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), itemStack, 1, 5,
					0.05f);
		}
		
	}
	
}