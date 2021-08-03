package nl.tettelaar.rebalanced.init;

import java.util.Arrays;

import net.minecraft.village.VillagerProfession;
import nl.tettelaar.rebalanced.api.RecipeAPI;

public class Recipes {
	
	public static void init () {
		RecipeAPI.registerKnowledgeBook(Arrays.asList("stone_sword", "stone_pickaxe", "stone_axe", "stone_shovel", "stone_hoe"),
				Arrays.asList("chests/simple_dungeon", "chests/abandoned_mineshaft", "chests/buried_treasure", 
						"chests/desert_pyramid", "chests/igloo_chest", "chests/jungle_temple", "chests/pillager_outpost", "chests/shipwreck_supply", "chests/ruined_portal", "chests/underwater_ruin_small", "chests/underwater_ruin_big", "chests/spawn_bonus_chest"), 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("iron_sword", "iron_pickaxe", "iron_axe", "iron_shovel", "iron_hoe", "shield", "bow", "iron_boots", "iron_leggings", "iron_chestplate", "iron_helmet"),
				Arrays.asList("chests/simple_dungeon", "chests/abandoned_mineshaft", "chests/buried_treasure", 
						"chests/desert_pyramid", "chests/igloo_chest", "chests/jungle_temple", "chests/pillager_outpost", "chests/endcity_treasure", "chests/stronghold_corridor", "chests/stronghold_crossing", "chests/woodland_mansion", "chests/bastion_treasure", "chests/bastion_bridge", "chests/bastion_other", "chests/nether_bridge"), 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("diamond_sword", "diamond_pickaxe", "diamond_axe", "diamond_shovel", "diamond_hoe", "diamond_boots", "diamond_leggings", "diamond_chestplate", "diamond_helmet", "enchantment_table", "anvil"),
				Arrays.asList("chests/abandoned_mineshaft", "chests/buried_treasure", 
						"chests/desert_pyramid", "chests/igloo_chest", "chests/jungle_temple", "chests/pillager_outpost", "chests/end_city_treasure", "chests/stronghold_corridor", "chests/stronghold_crossing", "chests/woodland_mansion", "chests/bastion_treasure", "chests/bastion_bridge", "chests/bastion_other",  "chests/nether_bridge"), 2);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("netherite_sword", "netherite_pickaxe", "netherite_axe", "netherite_shovel", "netherite_hoe", "netherite_boots", "netherite_leggings", "netherite_chestplate", "netherite_helmet"),
				Arrays.asList("chests/endcity_treasure", "chests/bastion_treasure", "chests/nether_bridge"), 1);
		
		
		
		//VILLAGERS
		//RecipeAPI.registerKnowledgeBook(Arrays.asList());
		
		RecipeAPI.registerKnowledgeBook(Arrays.asList("bread", "cake"), 3, 10, VillagerProfession.FARMER, 1, 1);

	}
	
}
