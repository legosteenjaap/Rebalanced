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
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import nl.tettelaar.rebalanced.village.TradeOffers;

public class RecipeAPI {

	private static List<Identifier> removedRecipeAdvancements = new ArrayList<>();
	private static ArrayList<Pair<List<List<Identifier>>, List<Identifier>>> knowledgeBooksLootTable = new ArrayList<>();
	private static HashMap<VillagerProfession, HashMap<Integer, List<Pair<TradeOffers.Factory, Float>>>> KnowledgeBooksVillagerTrades = new HashMap<>();
	private static List<Identifier> blockRecipeList = new ArrayList<>();
	private static HashMap<Identifier, List<Identifier>> requiredRecipesMap = new HashMap<>();
	
	public static void registerRequiredRecipe (Identifier recipe, List<Identifier> requiredRecipes) {
		requiredRecipesMap.put(recipe, requiredRecipes);
	}
	
	public static void registerBlockRecipe (Identifier name) {
		blockRecipeList.add(name);
	}
	
	private static void registerKnowledgeBookID(List<List<Identifier>> recipes, List<Identifier> loottables) {
		knowledgeBooksLootTable.add(new Pair<List<List<Identifier>>, List<Identifier>>(recipes, loottables));
		for (List<Identifier> e : recipes) {
			removedRecipeAdvancements.addAll(e);
		}
		
	}

	public static void registerKnowledgeBook(List<String> recipes, List<String> loottables, int weight) {

		ArrayList<List<Identifier>> recipesID = new ArrayList<>();
		ArrayList<Identifier> loottablesID = new ArrayList<>();

		for (String recipe : recipes) {
			List<String> splitRecipes = Arrays.asList(recipe.split(";"));
			ArrayList<Identifier> RecipesToId = new ArrayList<>();
			for (String RecipeId : splitRecipes) {
				RecipesToId.add(new Identifier(RecipeId));
			}
			recipesID.add(RecipesToId);
		}

		for (String loottable : loottables) {
			loottablesID.add(new Identifier(loottable));
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(recipesID, loottablesID);
		}
	}
	
	private static void registerKnowledgeBookID(List<List<Identifier>> recipes, int minPrice, int maxPrice, float chance, VillagerProfession villager, int level) {
		if (KnowledgeBooksVillagerTrades.get(villager) == null) {
			HashMap<Integer, List<Pair<TradeOffers.Factory, Float>>> tradesMap = new HashMap<>();
			tradesMap.put(level, Arrays.asList(new Pair<TradeOffers.Factory, Float> (new KnowledgeBookTrade(minPrice, maxPrice, recipes), chance)));
			KnowledgeBooksVillagerTrades.put(villager, tradesMap);
		} else if (KnowledgeBooksVillagerTrades.get(villager).get(level) == null) {
			KnowledgeBooksVillagerTrades.get(villager).put(level, Arrays.asList(new Pair<TradeOffers.Factory, Float> (new KnowledgeBookTrade(minPrice, maxPrice, recipes), chance)));
		} else {
			ArrayList<Pair<TradeOffers.Factory, Float>> trades = new ArrayList<>(KnowledgeBooksVillagerTrades.get(villager).get(level));
			trades.add(new Pair<TradeOffers.Factory, Float>(new KnowledgeBookTrade(minPrice, maxPrice, recipes), chance));
			KnowledgeBooksVillagerTrades.get(villager).put(level, trades);
		}
		for (List<Identifier> recipeList : recipes) {
			removedRecipeAdvancements.addAll(recipeList);
		}
	}

	public static void registerKnowledgeBook(List<String> recipes, int minPrice, int maxPrice, float chance, VillagerProfession villager, int level, int weight) {
		ArrayList<List<Identifier>> recipesID = new ArrayList<>();

		for (String recipe : recipes) {
			List<String> splitRecipes = Arrays.asList(recipe.split(";"));
			ArrayList<Identifier> RecipesToId = new ArrayList<>();
			for (String RecipeId : splitRecipes) {
				RecipesToId.add(new Identifier(RecipeId));
				
			}
			recipesID.add(RecipesToId);
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(recipesID, minPrice, maxPrice, chance, villager, level);
		}
	}
	
	public static List<Identifier> getRequiredRecipes (Identifier recipe) {
		return requiredRecipesMap.get(recipe);
	}
	
	public static List<Identifier> getRemovedRecipeAdvancements() {
		return RecipeAPI.removedRecipeAdvancements;
	}

	public static ArrayList<Pair<List<List<Identifier>>, List<Identifier>>> getKnowledgeBooksLootTable() {
		return knowledgeBooksLootTable;
	}
	
	public static List<Pair<TradeOffers.Factory, Float>> getKnowledgeBooksVillagerTrades(VillagerProfession villager, int level) {
		HashMap<Integer, List<Pair<TradeOffers.Factory, Float>>> map = KnowledgeBooksVillagerTrades.get(villager);
		if (map != null) return map.get(level);
		return null;
	}

	public static List<Identifier> getBlockRecipeList () {
		return blockRecipeList;
	}

	public static class KnowledgeBookTrade implements TradeOffers.Factory {
		
		private final int minPrice;
		private final int maxPrice;
		private final List<List<Identifier>> recipes;
		
		private KnowledgeBookTrade(int minPrice, int maxPrice, List<List<Identifier>> recipes) {
			this.minPrice = minPrice;
			this.maxPrice = maxPrice;
			this.recipes = recipes;
		}
		
		@Override
		public @Nullable TradeOffer create(Entity entity, Random random) {
			return new TradeOffer(new ItemStack(Items.EMERALD, random.nextInt(maxPrice - minPrice) + minPrice), RecipeUtil.createKnowledgeBook(recipes.get(random.nextInt(recipes.size()))), 1, 15,
					0.05f);
		}
		
	}
	
}