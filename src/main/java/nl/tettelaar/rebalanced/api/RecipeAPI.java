package nl.tettelaar.rebalanced.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import nl.tettelaar.rebalanced.TradeOffers;
import nl.tettelaar.rebalanced.util.RecipeUtil;

public class RecipeAPI {

	private static List<Identifier> removedRecipeAdvancements = new ArrayList<>();
	private static ArrayList<Pair<List<Identifier>, List<Identifier>>> knowledgeBooksLootTable = new ArrayList<>();
	private static HashMap<VillagerProfession, HashMap<Integer, List<Pair<TradeOffers.Factory, Float>>>> KnowledgeBooksVillagerTrades = new HashMap<>();
	private static HashMap<EntityType<? extends MobEntity>, List<Identifier>> knowledgeBooksMobEquipment = new HashMap<>();
	
	private static void registerKnowledgeBookID(List<Identifier> recipes, List<Identifier> loottables) {
		knowledgeBooksLootTable.add(new Pair<List<Identifier>, List<Identifier>>(recipes, loottables));
		removedRecipeAdvancements.addAll(recipes);
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
	
	private static void registerKnowledgeBookID(List<Identifier> recipes, int minPrice, int maxPrice, float chance, VillagerProfession villager, int level) {
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
		removedRecipeAdvancements.addAll(recipes);
	}

	public static void registerKnowledgeBook(List<String> recipes, int minPrice, int maxPrice, float chance, VillagerProfession villager, int level, int weight) {
		ArrayList<Identifier> recipesID = new ArrayList<>();

		for (String recipe : recipes) {
			recipesID.add(new Identifier(recipe));
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(recipesID, minPrice, maxPrice, chance, villager, level);
		}
	}
	
	private static void registerKnowledgeBookID(EntityType<? extends MobEntity> entity, List<Identifier> recipes) {
		if (knowledgeBooksMobEquipment.containsKey(entity)) {
			List<Identifier> allRecipes = knowledgeBooksMobEquipment.get(entity);
			allRecipes.addAll(recipes);
			knowledgeBooksMobEquipment.put(entity, allRecipes);
		} else {
			knowledgeBooksMobEquipment.put(entity, recipes);
		}
		
		removedRecipeAdvancements.addAll(recipes);
		
	}
	
	public static void registerKnowledgeBook(EntityType<? extends MobEntity> entity, List<String> recipes, int weight) {
		ArrayList<Identifier> recipesID = new ArrayList<>();

		for (String recipe : recipes) {
			recipesID.add(new Identifier(recipe));
		}

		for (int i = 0; i < weight; i++) {
			registerKnowledgeBookID(entity, recipesID);
		}
	}
	
	public static List<Identifier> getRemovedRecipeAdvancements() {
		return RecipeAPI.removedRecipeAdvancements;
	}

	public static ArrayList<Pair<List<Identifier>, List<Identifier>>> getKnowledgeBooksLootTable() {
		return knowledgeBooksLootTable;
	}
	
	public static List<Pair<TradeOffers.Factory, Float>> getKnowledgeBooksVillagerTrades(VillagerProfession villager, int level) {
		HashMap<Integer, List<Pair<TradeOffers.Factory, Float>>> map = KnowledgeBooksVillagerTrades.get(villager);
		if (map != null) return map.get(level);
		return null;
	}
	
	public static List<Identifier> getKnowledgeBooksMobEquipment(EntityType<? extends MobEntity> entity) {
		return knowledgeBooksMobEquipment.get(entity);
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
			return new TradeOffer(new ItemStack(Items.EMERALD, random.nextInt(maxPrice - minPrice) + minPrice), RecipeUtil.createKnowledgeBook(recipes, random), 1, 5,
					0.05f);
		}
		
	}
	
}