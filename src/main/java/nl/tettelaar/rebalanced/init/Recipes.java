package nl.tettelaar.rebalanced.init;

import java.util.Arrays;

import nl.tettelaar.rebalanced.api.RecipeAPI;

public class Recipes {
	
	public static void init () {
		RecipeAPI.registerKnowledgeBook(Arrays.asList("stone_sword", "stone_pickaxe", "stone_axe", "stone_shovel", "stone_hoe"),
				Arrays.asList("chests/simple_dungeon", "chests/abandoned_mineshaft", "chests/buried_treasure", 
						"chests/desert_pyramid", "chests/igloo_chest", "chests/jungle_temple", "chests/pillager_outpost", "chests/shipwreck_supply", "chests/ruined_portal", "chests/underwater_ruin_small", "chests/underwater_ruin_big", "chests/spawn_bonus_chest"));
	}
	
}
