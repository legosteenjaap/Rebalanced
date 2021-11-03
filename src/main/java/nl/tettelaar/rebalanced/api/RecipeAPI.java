package nl.tettelaar.rebalanced.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import nl.tettelaar.rebalanced.village.TradeOffers;

public class RecipeAPI {

	private static List<ResourceLocation> removedRecipeAdvancements = new ArrayList<>();
	private static ArrayList<Tuple<List<List<ResourceLocation>>, List<ResourceLocation>>> knowledgeBooksLootTable = new ArrayList<>();
	private static HashMap<VillagerProfession, HashMap<Integer, List<Tuple<TradeOffers.Factory, Float>>>> KnowledgeBooksVillagerTrades = new HashMap<>();
	private static List<ResourceLocation> blockRecipeList = new ArrayList<>();
	private static HashMap<ResourceLocation, List<ResourceLocation>> requiredRecipesMap = new HashMap<>();
	private static List<Tuple<TradeOffers.Factory, Float>> wanderingTraderKnowledgeBooks = new ArrayList<>();
	private static HashMap<ResourceLocation, Float> RecipeXPCost = new HashMap<>();

	public static void registerWanderingTraderKnowledgeBookID(List<List<ResourceLocation>> recipes, int minPrice, int maxPrice, float chance) {
		wanderingTraderKnowledgeBooks.add(new Tuple<TradeOffers.Factory, Float>(new KnowledgeBookTrade(minPrice, maxPrice, recipes), chance));
	}

	public static void registerWanderingTraderKnowledgeBook(List<String> recipes, int minPrice, int maxPrice, float chance, int weight) {
		ArrayList<List<ResourceLocation>> recipesID = new ArrayList<>();
		for (String recipe : recipes) {

			List<String> splitRecipes = Arrays.asList(recipe.split(";"));
			ArrayList<ResourceLocation> RecipesToId = new ArrayList<>();
			for (String RecipeId : splitRecipes) {
				RecipesToId.add(new ResourceLocation(RecipeId));
			}
			recipesID.add(RecipesToId);

		}
		for (int i = 0; i < weight; i++) {
			registerWanderingTraderKnowledgeBookID(recipesID, minPrice, maxPrice, chance);
		}
	}

	public static void registerRequiredRecipesID(ResourceLocation recipe, List<ResourceLocation> requiredRecipes) {
		requiredRecipesMap.put(recipe, requiredRecipes);
	}
	
	public static void registerRequiredRecipes(String recipe, List<String> requiredRecipes) {
		ArrayList<ResourceLocation> requiredRecipesID = new ArrayList<>();
		for (String requiredRecipe : requiredRecipes) {
			requiredRecipesID.add(new ResourceLocation(requiredRecipe));
		}
		registerRequiredRecipesID(new ResourceLocation(recipe), requiredRecipesID);
	}

	public static void registerBlockRecipe(ResourceLocation name) {
		blockRecipeList.add(name);
	}

	public static void setRecipeXPCost(String item, float cost) {
		RecipeXPCost.put(new ResourceLocation(item), cost);
	}


	private static void registerKnowledgeBookID(List<List<ResourceLocation>> recipes, List<ResourceLocation> loottables) {
		knowledgeBooksLootTable.add(new Tuple<List<List<ResourceLocation>>, List<ResourceLocation>>(recipes, loottables));
		for (List<ResourceLocation> e : recipes) {
			removedRecipeAdvancements.addAll(e);
		}

	}

	public static void registerKnowledgeBook(List<String> recipes, List<String> loottables, int weight) {
		ArrayList<List<ResourceLocation>> recipesID = new ArrayList<>();
		ArrayList<ResourceLocation> loottablesID = new ArrayList<>();

		for (String recipe : recipes) {
			List<String> splitRecipes = Arrays.asList(recipe.split(";"));
			ArrayList<ResourceLocation> RecipesToId = new ArrayList<>();
			for (String RecipeId : splitRecipes) {
				RecipesToId.add(new ResourceLocation(RecipeId));
			}
			recipesID.add(RecipesToId);
		}

		for (String loottable : loottables) {
			loottablesID.add(new ResourceLocation(loottable));
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(recipesID, loottablesID);
		}
	}

	private static void registerKnowledgeBookID(List<List<ResourceLocation>> recipes, int minPrice, int maxPrice, float chance, VillagerProfession villager, int level) {
		if (KnowledgeBooksVillagerTrades.get(villager) == null) {
			HashMap<Integer, List<Tuple<TradeOffers.Factory, Float>>> tradesMap = new HashMap<>();
			tradesMap.put(level, Arrays.asList(new Tuple<TradeOffers.Factory, Float>(new KnowledgeBookTrade(minPrice, maxPrice, recipes), chance)));
			KnowledgeBooksVillagerTrades.put(villager, tradesMap);
		} else if (KnowledgeBooksVillagerTrades.get(villager).get(level) == null) {
			KnowledgeBooksVillagerTrades.get(villager).put(level, Arrays.asList(new Tuple<TradeOffers.Factory, Float>(new KnowledgeBookTrade(minPrice, maxPrice, recipes), chance)));
		} else {
			ArrayList<Tuple<TradeOffers.Factory, Float>> trades = new ArrayList<>(KnowledgeBooksVillagerTrades.get(villager).get(level));
			trades.add(new Tuple<TradeOffers.Factory, Float>(new KnowledgeBookTrade(minPrice, maxPrice, recipes), chance));
			KnowledgeBooksVillagerTrades.get(villager).put(level, trades);
		}
		for (List<ResourceLocation> recipeList : recipes) {
			removedRecipeAdvancements.addAll(recipeList);
		}
	}

	public static void registerKnowledgeBook(List<String> recipes, int minPrice, int maxPrice, float chance, VillagerProfession villager, int level, int weight) {
		ArrayList<List<ResourceLocation>> recipesID = new ArrayList<>();

		for (String recipe : recipes) {
			List<String> splitRecipes = Arrays.asList(recipe.split(";"));
			ArrayList<ResourceLocation> RecipesToId = new ArrayList<>();
			for (String RecipeId : splitRecipes) {
				RecipesToId.add(new ResourceLocation(RecipeId));

			}
			recipesID.add(RecipesToId);
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(recipesID, minPrice, maxPrice, chance, villager, level);
		}
	}

	public static List<Tuple<TradeOffers.Factory, Float>> getWanderingTraderBooks() {
		return wanderingTraderKnowledgeBooks;
	}

	public static List<ResourceLocation> getRequiredRecipes(ResourceLocation recipe) {
		 List<ResourceLocation> requiredRecipes = requiredRecipesMap.get(recipe);
		 if(requiredRecipes != null) return requiredRecipes;
		 return null;
	}

	public static List<ResourceLocation> getRemovedRecipeAdvancements() {
		return RecipeAPI.removedRecipeAdvancements;
	}

	public static ArrayList<Tuple<List<List<ResourceLocation>>, List<ResourceLocation>>> getKnowledgeBooksLootTable() {
		return knowledgeBooksLootTable;
	}

	public static List<Tuple<TradeOffers.Factory, Float>> getKnowledgeBooksVillagerTrades(VillagerProfession villager, int level) {
		HashMap<Integer, List<Tuple<TradeOffers.Factory, Float>>> map = KnowledgeBooksVillagerTrades.get(villager);
		if (map != null)
			return map.get(level);
		return null;
	}

	public static List<ResourceLocation> getBlockRecipeList() {
		return blockRecipeList;
	}

	public static float getRecipeXPCost(ResourceLocation item) {
		return RecipeXPCost.get(item);
	}

	public static class KnowledgeBookTrade implements TradeOffers.Factory {

		private final int minPrice;
		private final int maxPrice;
		private final List<List<ResourceLocation>> recipes;

		private KnowledgeBookTrade(int minPrice, int maxPrice, List<List<ResourceLocation>> recipes) {
			this.minPrice = minPrice;
			this.maxPrice = maxPrice;
			this.recipes = recipes;
		}

		@Override
		public @Nullable MerchantOffer create(Entity entity, Random random) {
			return new MerchantOffer(new ItemStack(Items.EMERALD, random.nextInt(maxPrice - minPrice) + minPrice), RecipeUtil.createKnowledgeBook(recipes.get(random.nextInt(recipes.size()))), 1, 15, 0.05f);
		}

	}

}