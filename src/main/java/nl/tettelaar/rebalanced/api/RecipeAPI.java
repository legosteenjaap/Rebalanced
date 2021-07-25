package nl.tettelaar.rebalanced.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class RecipeAPI {

	private static ArrayList<Pair<List<Identifier>, List<Identifier>>> knowledgeBooksLootTable = new ArrayList<>();
	private static List<Identifier> removedRecipeAdvancements;

	public static void registerKnowledgeBookID(List<Identifier> recipes, List<Identifier> loottables) {
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

}
