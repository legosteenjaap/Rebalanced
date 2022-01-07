package nl.tettelaar.rebalanced.api;

import java.rmi.registry.Registry;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import nl.tettelaar.rebalanced.village.TradeOffers;

public class RecipeAPI {

	private static List<ResourceLocation> removedRecipeAdvancements = new ArrayList<>();
	private static ArrayList<Tuple<List<List<ResourceLocation>>, List<ResourceLocation>>> knowledgeBooksLootTable = new ArrayList<>();
	private static ArrayList<Tuple<List<Item>, List<ResourceLocation>>> knowledgeBooksItemLootTable = new ArrayList<>();
	private static HashMap<VillagerProfession, HashMap<Integer, List<Tuple<TradeOffers.Factory, Float>>>> KnowledgeBooksVillagerTrades = new HashMap<>();
	private static Map<ResourceLocation, Item> blockRecipeList = new HashMap<>();
	private static HashMap<ResourceLocation, List<ResourceLocation>> requiredRecipesMap = new HashMap<>();
	private static List<Tuple<TradeOffers.Factory, Float>> wanderingTraderKnowledgeBooks = new ArrayList<>();
	private static HashMap<Item, Integer> ItemXPCost = new HashMap<>();
	private static HashMap<ResourceLocation, Integer> RecipeXPCost = new HashMap<>();
	private static ArrayList<ResourceLocation> ExcludedDiscoverableRecipes = new ArrayList<>();

	public static boolean updatedWithRecipeManager = false;
	public static RecipeManager recipeManager = null;
	public static void updateWithRecipeManager(RecipeManager recipeManager) {
		for (ResourceLocation id : RecipeXPCost.keySet()) {
			Optional<? extends Recipe<?>> recipe = recipeManager.byKey(id);
			if (recipe.isPresent()) setItemXPCost(recipe.get().getResultItem().getItem(), RecipeXPCost.get(id));
		}
		for (Tuple<List<Item>, List<ResourceLocation>> entry : knowledgeBooksItemLootTable) {
			for (Item item : entry.getA()) {
				for (Recipe<?> recipe : getRecipesWithItem(item.getDefaultInstance(), recipeManager)) {
					registerKnowledgeBook(Arrays.asList(recipe.getId().toString()), entry.getB().stream().map(resourceLocation -> {
						return resourceLocation.toString();
					}).collect(Collectors.toList()), 1);
				}
			}
		}
		for (Item item : ItemXPCost.keySet()) {
			removedRecipeAdvancements.addAll(getRecipesWithItem(item.getDefaultInstance(), recipeManager).stream().map((recipe) -> {
				return recipe.getId();
			}).collect(Collectors.toList()));
		}
		updatedWithRecipeManager = true;
		RecipeAPI.recipeManager = recipeManager;
	}

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

	public static void registerBlockRecipe(ResourceLocation name, Item displayItem) {
		blockRecipeList.put(name, displayItem);
	}

	public static void setItemXPCost(Item item, int cost) {
		ItemXPCost.put(item, cost);
	}

	public static void setItemsXPCost(List<Item> items, int cost) {
		for (Item item : items) {
			ItemXPCost.put(item, cost);
		}
	}

	public static void setRecipeXPCost(List<String> recipes, int cost) {
		for (String recipe : recipes) {
			RecipeXPCost.put(new ResourceLocation(recipe), cost);
		}
	}

	public static void addExcludedDiscoverableRecipes(List<ResourceLocation> recipes) {
		ExcludedDiscoverableRecipes.addAll(recipes);
	}

	private static void registerKnowledgeBookID(List<List<ResourceLocation>> recipeLists, List<ResourceLocation> loottables) {
		knowledgeBooksLootTable.add(new Tuple<List<List<ResourceLocation>>, List<ResourceLocation>>(recipeLists, loottables));
		for (List<ResourceLocation> recipeList : recipeLists) {
			removedRecipeAdvancements.addAll(recipeList);
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
			loottablesID.add(new ResourceLocation("chests/" + loottable));
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(recipesID, loottablesID);
		}
	}

	public static void registerKnowledgeBookItem(List<Item> items, List<String> loottables, int weight) {

		knowledgeBooksItemLootTable.add(new Tuple<List<Item>, List<ResourceLocation>>(items, loottables.stream().map((string) -> {
			return new ResourceLocation("chests/" + string);
		}).collect(Collectors.toList())));
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
		 if(requiredRecipes != null && requiredRecipes.size() > 0) return requiredRecipes;
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

	public static Map<ResourceLocation, Item> getBlockRecipeList() {
		return blockRecipeList;
	}

	public static Optional<Integer> getItemXPCost(Item item) {
		return Optional.ofNullable(ItemXPCost.get(item));
	}

	public static boolean hasXPCost(Item item) {
		return getItemXPCost(item).isPresent();
	}

	public static List<ResourceLocation> getDiscoverableRecipes(RecipeManager recipeManager) {
		ArrayList<ResourceLocation> discoverableRecipes = new ArrayList<>();
		ItemXPCost.keySet().forEach((Item item) -> {
			discoverableRecipes.addAll(RecipeAPI.getRecipesWithDiscoverableItem(new ItemStack(item), recipeManager).stream().map((Function<Recipe, ResourceLocation>) Recipe::getId).collect(Collectors.toList()));
		});
		discoverableRecipes.removeAll(ExcludedDiscoverableRecipes);
		return discoverableRecipes;
	}

	public static List<Recipe<?>> getRecipesWithItem(ItemStack item, RecipeManager recipeManager) {
		ArrayList<Recipe<?>> recipes = (ArrayList<Recipe<?>>) recipeManager.getRecipes().stream().collect(Collectors.toList());
		recipeManager.getRecipes().stream().forEach((recipe) -> {
			if (!recipe.getResultItem().getItem().equals(item.getItem())) recipes.remove(recipe);
		});
		return recipes;
	}

	public static Collection<Recipe<?>> getRecipesWithDiscoverableItem(ItemStack item, RecipeManager recipeManager) {
		ArrayList<Recipe<?>> recipes = new ArrayList<>();
		for (Recipe<?> recipe : recipeManager.getRecipes()) {
			if (recipe.getResultItem().getItem().equals(item.getItem()) && RecipeAPI.isDiscoverable(recipe)) {
				recipes.add(recipe);
			}
		}
		return recipes;
	}

	public static boolean isDiscoverable (Recipe recipe) {
		return ItemXPCost.get(recipe.getResultItem().getItem()) != null && !ExcludedDiscoverableRecipes.contains(recipe.getId());
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