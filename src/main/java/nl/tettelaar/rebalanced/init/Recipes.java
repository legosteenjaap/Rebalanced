package nl.tettelaar.rebalanced.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.mixin.recipe.brewing.MenuTypeInvoker;
import nl.tettelaar.rebalanced.recipe.BlockRecipe;

public class Recipes {

	private static final String COD = "cooked_cod;cooked_cod_from_campfire_cooking;cooked_cod_from_smoking";
	private static final String SALMON = "cooked_salmon;cooked_salmon_from_campfire_cooking;cooked_salmon_from_smoking";
	private static final String KELP = "dried_kelp_from_smelting;fried_kelp_from_campfire_cooking;dried_kelp_from_smokin";
	private static final String PORKCHOP = "cooked_porkchop;cooked_porkchop_from_campfire_cooking;cooked_porkchop_from_smoking";
	private static final String BEEF = "cooked_beef;cooked_beef_from_campfire_cooking;cooked_beef_from_smoking";
	private static final String CHICKEN = "cooked_chicken;cooked_chicken_from_campfire_cooking;cooked_chicken_from_smoking";
	private static final String RABBIT = "cooked_chicken;cooked_chicken_from_campfire_cooking;cooked_chicken_from_smoking";
	private static final String MUTTON = "cooked_mutton;cooked_mutton_from_campfire_cooking;cooked_mutton_from_smoking";
	private static final String BED = "white_bed;orange_bed;magenta_bed;light_blue_bed;yellow_bed;lime_bed;pink_bed;grad_bed;light_gray_bed;cyan_bed;purple_bed;blue_bed;brown_bed;green_bed;red_bed;black_bed";

	private static final List<String> COMMON = Arrays.asList("abandoned_mineshaft", "bastion_other", "desert_pyramid", "igloo_chest", "jungle_temple", "nether_bridge", "pillager_outpost", "ruined_portal", "shipwreck_map", "shipwreck_supply", "simple_dungeon", "spawn_bonus_chest", "underwater_ruin_big", "underwater_ruin_small", "woodland_mansion", "stronghold_corridor", "village/village_armorer", "village/village_butcher", "village/village_cartographer", "village/village_mason", "village/village_shepherd", "village/village_tannery", "village/village_weaponsmith", "village/village_desert_house", "village/village_plains_house", "village/village_savanna_house", "village/village_snowy_house", "village/village_taiga_house", "village/village_fisher", "village/village_fletcher", "village/village_temple", "village/village_toolsmith");
	private static final List<String> UNCOMMON = Arrays.asList("bastion_bridge", "bastion_treasure", "buried_treasure", "desert_pyramid", "jungle_temple", "nether_bridge", "shipwreck_treasure", "simple_dungeon", "stronghold_corridor", "underwater_ruin_big", "woodland_mansion");
	private static final List<String> RARE = Arrays.asList("bastion_treasure", "buried_treasure", "nether_bridge", "shipwreck_treasure", "stronghold_library", "stronghold_corridor", "woodland_mansion", "end_city_treasure");

	private static final List<String> OVERWORLD = Arrays.asList("abandoned_mineshaft", "buried_treasure", "desert_pyramid", "end_city_treasure", "igloo_chest", "jungle_temple", "pillager_outpost", "shipwreck_map", "shipwrek_supply", "shipwreck_treasure", "simple_dungeon", "spawn_bonus_chest", "stronghold_corridor", "stronghold_crossing", "stronghold_library", "underwater_ruin_big", "underwater_ruin_small", "woodland_mansion", "village/village_armorer", "village/village_butcher", "village/village_cartographer", "village/village_mason", "village/village_shepherd", "village/village_tannery", "village/village_weaponsmith", "village/village_desert_house", "village/village_plains_house", "village/village_savanna_house", "village/village_snowy_house", "village/village_taiga_house", "village/village_fisher", "village/village_fletcher", "village/village_temple", "village/village_toolsmith");
	private static final List<String> NETHER = Arrays.asList("bastion_bridge", "bastion_hoglin_stable", "bastion_other", "bastion_treasure", "nether_bridge", "ruined_portal");
	private static final List<String> END = Arrays.asList("end_city_treasure");
	private static final List<String> AQUATIC = Arrays.asList("shipwreck_map", "shipwrek_supply", "shipwreck_treasure", "underwater_ruin_small", "underwater_ruin_big");
	private static final List<String> CAVE = Arrays.asList("abandoned_mineshaft", "simple_dungeon", "stronghold_corridor", "stronghold_crossing", "stronghold_library");
	private static final List<String> VILLAGERS = Arrays.asList("village/village_armorer", "village/village_butcher", "village/village_cartographer", "village/village_mason", "village/village_shepherd", "village/village_tannery", "village/village_weaponsmith", "village/village_desert_house", "village/village_plains_house", "village/village_savanna_house", "village/village_snowy_house", "village/village_taiga_house", "village/village_fisher", "village/village_fletcher", "village/village_temple", "village/village_toolsmith");
	private static final List<String> ILLAGERS = Arrays.asList("pillager_outpost", "woodland_mansion");
	private static final List<String> PIGLINS = Arrays.asList("bastion_bridge", "bastion_hoglin_stable", "bastion_other", "bastion_treasure");

	private static final List<String> PAPER = Arrays.asList("shipwreck_map", "village/village_cartographer", "stronghold_library");
	private static final List<String> ARMOR = Arrays.asList("stronghold_corridor", "village/village_armorer");
	private static final List<String> TOOLS = Arrays.asList("village/village_toolsmith");
	private static final List<String> WEAPONS = Arrays.asList("village/village_weaponsmith");
	private static final List<String> MEAT = Arrays.asList("village/village_butcher");
	private static final List<String> FISHING = Arrays.asList("village/village_fisher");
	private static final List<String> MAPS = Arrays.asList("village/village_cartographer", "shipwreck_map");
	private static final List<String> MAGIC = Arrays.asList("village_temple");
	private static final List<String> ARROWS = Arrays.asList("village/village_fletcher");
	private static final List<String> LEATHER = Arrays.asList("village/village_tannery");
	private static final List<String> WOOL = Arrays.asList("village/village_shepherd");
	private static final List<String> MASON = Arrays.asList("village/village_mason");

	private static final List<String> JUNGLE = Arrays.asList("jungle_pyramid");
	private static final List<String> SWAMP = Arrays.asList();
	private static final List<String> BADLANDS = Arrays.asList();
	private static final List<String> DESERT = Arrays.asList("village/village_desert_house", "desert_pyramid");
	private static final List<String> PLAINS = Arrays.asList("village_plains_house");
	private static final List<String> SAVANNA = Arrays.asList("village/village_savanna_house");
	private static final List<String> SNOWY = Arrays.asList("village/village_snowy_house", "igloo_chest");
	private static final List<String> TAIGA = Arrays.asList("village/village_taiga_house");

	public static final RecipeSerializer<BlockRecipe> BLOCK_RECIPE = RecipeSerializer.register("block_recipe", new BlockRecipe.Serializer());
	public static final RecipeType<BlockRecipe> BLOCK = RecipeType.register("block");
	//public static final RecipeType<SmokingRecipe> BREWING = RecipeType.register("brewing");
	//public static final MenuType<BrewingStandMenu> BREWING_STAND = register(BrewingStandMenu::new);

	public static void init() {
		initXPCost();
		initKnowledgeBooks();
		initBlockRecipes();
		initRequiredRecipes();
		RecipeAPI.addExcludedDiscoverableRecipes(Arrays.asList(new ResourceLocation("dried_kelp")));

		//MenuScreensInvoker.register(BREWING_STAND, BrewingStandScreen::new);
	}

	private static MenuType register(MenuType.MenuSupplier menuSupplier) {
		return Registry.register(Registry.MENU, "brewing_stand", MenuTypeInvoker.initMenuType(menuSupplier));
	}

	public static void initRequiredRecipes () {
		RecipeAPI.registerRequiredRecipes("iron_sword", Arrays.asList("stone_sword"));
		RecipeAPI.registerRequiredRecipes("iron_pickaxe", Arrays.asList("stone_pickaxe"));
		RecipeAPI.registerRequiredRecipes("iron_axe", Arrays.asList("stone_axe"));
		RecipeAPI.registerRequiredRecipes("iron_shovel", Arrays.asList("stone_shovel"));
		RecipeAPI.registerRequiredRecipes("iron_hoe", Arrays.asList("stone_hoe"));

		RecipeAPI.registerRequiredRecipes("gold_sword", Arrays.asList("stone_sword"));
		RecipeAPI.registerRequiredRecipes("gold_pickaxe", Arrays.asList("stone_pickaxe"));
		RecipeAPI.registerRequiredRecipes("gold_axe", Arrays.asList("stone_axe"));
		RecipeAPI.registerRequiredRecipes("gold_shovel", Arrays.asList("stone_shovel"));
		RecipeAPI.registerRequiredRecipes("gold_hoe", Arrays.asList("stone_hoe"));
		
		RecipeAPI.registerRequiredRecipes("diamond_sword", Arrays.asList("iron_sword"));
		RecipeAPI.registerRequiredRecipes("diamond_pickaxe", Arrays.asList("iron_pickaxe"));
		RecipeAPI.registerRequiredRecipes("diamond_axe", Arrays.asList("iron_axe"));
		RecipeAPI.registerRequiredRecipes("diamond_shovel", Arrays.asList("iron_shovel"));
		RecipeAPI.registerRequiredRecipes("diamond_hoe", Arrays.asList("iron_hoe"));
		RecipeAPI.registerRequiredRecipes("diamond_helmet", Arrays.asList("iron_helmet"));
		RecipeAPI.registerRequiredRecipes("diamond_chestplate", Arrays.asList("iron_chestplate"));
		RecipeAPI.registerRequiredRecipes("diamond_leggings", Arrays.asList("iron_leggings"));
		RecipeAPI.registerRequiredRecipes("diamond_boots", Arrays.asList("iron_boots"));

		RecipeAPI.registerRequiredRecipes("netherite_sword_smithing", Arrays.asList("diamond_sword"));
		RecipeAPI.registerRequiredRecipes("netherite_pickaxe_smithing", Arrays.asList("diamond_pickaxe"));
		RecipeAPI.registerRequiredRecipes("netherite_axe_smithing", Arrays.asList("diamond_axe"));
		RecipeAPI.registerRequiredRecipes("netherite_shovel_smithing", Arrays.asList("diamond_shovel"));
		RecipeAPI.registerRequiredRecipes("netherite_hoe_smithing", Arrays.asList("diamond_hoe"));
		RecipeAPI.registerRequiredRecipes("diamond_helmet", Arrays.asList("iron_helmet"));
		RecipeAPI.registerRequiredRecipes("diamond_chestplate", Arrays.asList("iron_chestplate"));
		RecipeAPI.registerRequiredRecipes("diamond_leggings", Arrays.asList("iron_leggings"));
		RecipeAPI.registerRequiredRecipes("diamond_boots", Arrays.asList("iron_boots"));

		RecipeAPI.registerRequiredRecipes("rabbit_stew", Arrays.asList("cooked_rabbit", "cooked_rabbit_from_campfire_cooking", "cooked_rabbit_from_smoking", "baked_potato", "baked_potato_from_campfire_cooking", "baked_potato_from_smoking"));
		RecipeAPI.registerRequiredRecipes("ender_chest", Arrays.asList("eye_of_ender"));
		RecipeAPI.registerRequiredRecipes("lodestone", Arrays.asList("compass"));
		RecipeAPI.registerRequiredRecipes("crossbow", Arrays.asList("bow"));
		RecipeAPI.registerRequiredRecipes("map", Arrays.asList("compass"));
	}

	//blocks
		/*
		conduit
		chiseled polished blackstone
		chiseled nether bricks
		chiseled quartz block
		chiseled sandstone
		chiseled redsandstone
		chiseled stone bricks
		chiseled deepslate

		end crystal
		respawn anchor
		blast furnace
		lodestone
		brewing stand
		lectern
		anvil
		soul campfire
		bed
		enchanting table
		grindstone
		fletching table
		cartography table
		smoker
		smithing table
		soul torch
		soul lantern

		jack o lantern
		bookshelf
		tinted glass
		scaffolding
		trapped chest
		item frame
		armor stand
		campfire
		loom
		composter
		beehive
		stonecutter
		cauldron
		juke box
		lantern


		chains
		brick
		banner
		barrel
		flower pot
		end rod
		painting
		iron bars
		candle

		*/

	//redstone
		/*
		piston
		hopper
		dispenser
		observer
		tnt

		daylight detector
		target block
		tripwire hook
		rail
		powered rail
		minecart
		note block

		redstone lamp
		iron door
		iron trapdoor
		warped fungus on a stick
		carrot on a stick
		lighting rod
		 */

	//food
		/*
		cake
		golden carrot
		golden apple
		suspicious stew

		cooked beef
		cooked porkchop
		cooked mutton
		cooked salmon

		baked potato
		bread
		cooked chicken
		cooked cod
		cooked rabbit

		beetroot soup
		mushroom stew
		rabbit stew
		pumpkin pie
		 */

	//tools
		/*
		tipped arrow

		netherite armor
		netherite tools

		diamond armor
		diamond tools

		iron armor
		iron tools

		firework
		firework star
		crossbow
		turtle helmet

		spectral arrow
		lead
		leather horse armor
		fishing rod
		shield

		bucket
		clock
		compass
		map
		shears
		flint and steel

		gold tools
		gold armor
		fire charge

		bow
		stone tools
		 */

		//misc
		/*
		banner pattern creeper
		banner pattern skull

		banner pattern flower

		book and quill
		 */

	public static void initXPCost () {
		//BLOCKS
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.BRICK),59);
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.TINTED_GLASS, Items.SCAFFOLDING, Items.CAMPFIRE, Items.COMPOSTER, Items.BEEHIVE, Items.STONECUTTER),10);
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.CHISELED_RED_SANDSTONE), 15);

		//REDSTONE
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.RAIL, Items.POWERED_RAIL, Items.MINECART),15);

		//TOOLS
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.STONE_SWORD, Items.STONE_PICKAXE, Items.STONE_SHOVEL, Items.STONE_AXE, Items.STONE_HOE, Items.BOW),10);
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.GOLDEN_SWORD, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.FIRE_CHARGE),5);
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.FLINT_AND_STEEL, Items.SHEARS, Items.BUCKET, Items.COMPASS, Items.CLOCK, Items.MAP, Items.SPYGLASS),5);
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.IRON_SWORD, Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE),20);

		//ARMOR
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.IRON_BOOTS, Items.IRON_LEGGINGS, Items.IRON_CHESTPLATE, Items.IRON_BOOTS),20);
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.GOLDEN_BOOTS, Items.GOLDEN_LEGGINGS, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_HELMET),5);

		//FOOD
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.BEETROOT_SOUP, Items.MUSHROOM_STEW, Items.RABBIT_STEW, Items.PUMPKIN_PIE, Items.BAKED_POTATO, Items.BREAD), 5);
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.COOKED_CHICKEN, Items.COOKED_COD, Items.COOKED_RABBIT), 15);
		RecipeAPI.setItemsXPCost(Arrays.asList(Items.COOKED_MUTTON, Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.COOKED_SALMON),20);

	}

	private static void initKnowledgeBooks() {

		//LOOT TABLES
		//block
		RecipeAPI.registerKnowledgeBook(Arrays.asList("conduit"), AQUATIC, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("chiseled_polished_blackstone;chiseled_polished_blackstone_from_polished_blackstone_stonecutting;chiseled_polished_blackstone_from_blackstone_stonecutting"), PIGLINS, 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("chiseled_nether_bricks;chiseled_nether_bricks_from_nether_bricks_stonecutting"), Arrays.asList("nether_bridge"), 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("chiseled_quartz_block;chiseled_quartz_block_from_quartz_block_stonecutting"), Arrays.asList("nether_bridge"), 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("chiseled_sandstone;chiseled_sandstone_from_sandstone_stonecutting"), DESERT, 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("chiseled_sandstone;chiseled_sandstone_from_sandstone_stonecutting"), DESERT, 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("chiseled_stone_bricks_block;chiseled_stone_bricks_from_stone_bricks_stonecutting"), CAVE, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("chiseled_deepslate;chiseled_deepslate_from_cobbled_deepslate_stonecutting"), CAVE, 4);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("respawn_anchor", "brewing"), NETHER, 15);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("soul_torch", "soul_campfire", "soul_lantern"), NETHER, 15);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("lodestone"), NETHER, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("end_crystal"), END, 5);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("blast_furnace"), ARMOR, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("smoker"), MEAT, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("grindstone"), WEAPONS, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("anvil"), TOOLS, 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("anvil"), WEAPONS, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("anvil"), ARMOR, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(BED), WOOL, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("enchanting_table"), MAGIC, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("enchanting_table"), RARE, 5);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("composter", "campfire"), OVERWORLD, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("beehive"), PLAINS, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("tinted_glass"), CAVE, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("stonecutter"), MASON, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("scaffolding"), JUNGLE, 3);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("brick"), MASON, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("brick"), COMMON, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("end_rod"), END, 5);

		//redstone
		RecipeAPI.registerKnowledgeBook(Arrays.asList("rail", "powered_rail", "minecart"), CAVE, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("piston", "hopper", "dispenser", "observer", "tnt"), CAVE, 1);

		//tools
		RecipeAPI.registerKnowledgeBook(Arrays.asList("tipped_arrow"), END, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("tipped_arrow"), NETHER, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("diamond_boots", "diamond_leggings", "diamond_chestplate", "diamond_helmet", "diamond_sword", "diamond_pickaxe", "diamond_axe", "diamond_shovel", "diamond_hoe"), RARE, 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("iron_boots", "iron_leggings", "iron_chestplate", "iron_helmet", "iron_sword", "iron_pickaxe", "iron_axe", "iron_shovel", "iron_hoe"), UNCOMMON, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("stone_sword", "stone_pickaxe", "stone_axe", "stone_shovel", "stone_hoe", "bow"), COMMON, 30);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("stone_pickaxe", "stone_axe", "stone_shovel", "stone_hoe", "bow"), TOOLS, 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("stone_sword"), WEAPONS, 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("iron_boots", "iron_leggings", "iron_chestplate", "iron_helmet"), ARMOR, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("stone_sword", "stone_pickaxe", "stone_axe", "stone_shovel", "stone_hoe"), ARMOR, 10);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("iron_pickaxe", "iron_axe", "iron_shovel", "iron_hoe"), TOOLS, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("iron_sword", "shield"), WEAPONS, 5);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("crossbow"), ILLAGERS, 2);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("turtle_helmet"), AQUATIC, 2);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("spectral_arrow", "lead", "leather_horse_armor", "fishing_rod", "shield"), UNCOMMON, 2);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("flint_and_steel", "shears", "map", "compass", "clock", "bucket", "spyglass"), COMMON, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("golden_boots", "golden_leggings", "golden_chestplate", "golden_helmet", "golden_sword", "golden_pickaxe", "golden_axe", "golden_shovel", "golden_hoe", "fire_charge"), NETHER, 10);

		//food
		RecipeAPI.registerKnowledgeBook(Arrays.asList("beetroot_soup", "mushroom_stew", "rabbit_stew_from_brown_mushroom;rabbit_stew_from_red_mushroom", "pumpkin_pie"), COMMON, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("baked_potato", "bread", "cooked_chicken", "cooked_cod", "cooked_rabbit"), COMMON, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("cooked_mutton", "cooked_beef", "cooked_porkchop", "cooked_salmon"), MEAT, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("cooked_mutton", "cooked_beef", "cooked_porkchop", "cooked_salmon"), RARE, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("golden_apple", "golden_carrot", "cake"), MEAT, 1);

		//misc
		RecipeAPI.registerKnowledgeBook(Arrays.asList("flower_banner_pattern"), PLAINS, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("creeper_banner_pattern"), CAVE, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("skull_banner_pattern"), Arrays.asList("nether_bridge"), 1);


		// VILLAGERS
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.ARMORER, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.ARMORER, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.ARMORER, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.ARMORER, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("blast_furnace"), 20, 40, 0.7f, VillagerProfession.ARMORER, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(RABBIT, PORKCHOP), 3, 10, 0.5f, VillagerProfession.BUTCHER, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(BEEF, CHICKEN, MUTTON), 3, 10, 0.5f, VillagerProfession.BUTCHER, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(RABBIT, PORKCHOP), 3, 10, 0.5f, VillagerProfession.BUTCHER, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(RABBIT, PORKCHOP), 3, 10, 0.5f, VillagerProfession.BUTCHER, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("smoker"), 20, 40, 0.7f, VillagerProfession.BUTCHER, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.CARTOGRAPHER, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.CARTOGRAPHER, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.CARTOGRAPHER, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.CARTOGRAPHER, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("cartography_table"), 20, 40, 0.7f, VillagerProfession.CARTOGRAPHER, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.CLERIC, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.CLERIC, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.CLERIC, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.CLERIC, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("brewing_stand"), 20, 40, 0.7f, VillagerProfession.CLERIC, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("golden_hoe"), 3, 10, 0.5f, VillagerProfession.FARMER, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.FARMER, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.FARMER, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.FARMER, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("composter"), 20, 40, 0.7f, VillagerProfession.FARMER, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(COD), 10, 32, 0.2f, VillagerProfession.FISHERMAN, 1, 5);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(SALMON, KELP), 10, 32, 0.2f, VillagerProfession.FISHERMAN, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("fishing_rod", "compass", "spyglass"), 3, 10, 0.8f, VillagerProfession.FISHERMAN, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(SALMON), 10, 32, 0.4f, VillagerProfession.FISHERMAN, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(COD), 10, 32, 0.7f, VillagerProfession.FISHERMAN, 3, 3);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(SALMON), 3, 10, 0.7f, VillagerProfession.FISHERMAN, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("barrel"), 20, 40, 1f, VillagerProfession.FISHERMAN, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.FLETCHER, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.FLETCHER, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.FLETCHER, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.FLETCHER, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("fletching_table"), 20, 40, 0.7f, VillagerProfession.FLETCHER, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.LEATHERWORKER, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.LEATHERWORKER, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.LEATHERWORKER, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.LEATHERWORKER, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("cauldron"), 20, 40, 0.7f, VillagerProfession.LEATHERWORKER, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.LIBRARIAN, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.LIBRARIAN, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.LIBRARIAN, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.LIBRARIAN, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("lectern"), 20, 40, 0.7f, VillagerProfession.LIBRARIAN, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.MASON, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.MASON, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.MASON, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.MASON, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("stonecutter"), 20, 40, 0.7f, VillagerProfession.MASON, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.SHEPHERD, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.SHEPHERD, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.SHEPHERD, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.SHEPHERD, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("loom"), 20, 40, 0.7f, VillagerProfession.SHEPHERD, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("golden_pickaxe", "golden_axe", "golden_shovel", "golden_hoe"), 3, 10, 0.5f, VillagerProfession.TOOLSMITH, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.TOOLSMITH, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.TOOLSMITH, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.TOOLSMITH, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("smithing_table"), 20, 40, 0.7f, VillagerProfession.TOOLSMITH, 5, 1);

		RecipeAPI.registerKnowledgeBook(Arrays.asList("gold_sword", "gold_axe"), 3, 10, 0.5f, VillagerProfession.WEAPONSMITH, 1, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.WEAPONSMITH, 2, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.WEAPONSMITH, 3, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList(""), 3, 10, 0.5f, VillagerProfession.WEAPONSMITH, 4, 1);
		RecipeAPI.registerKnowledgeBook(Arrays.asList("grindstone"), 20, 40, 0.7f, VillagerProfession.WEAPONSMITH, 5, 1);
	}

	private static void initBlockRecipes() {
		RecipeAPI.registerBlockRecipe(new ResourceLocation("iron_golem"), Items.IRON_BLOCK);
		RecipeAPI.registerBlockRecipe(new ResourceLocation("snow_golem"), Items.SNOW_BLOCK);
		RecipeAPI.registerBlockRecipe(new ResourceLocation("wither"), Items.SOUL_SAND);
		RecipeAPI.registerBlockRecipe(new ResourceLocation("nether_portal"), Items.OBSIDIAN);
	}

}
