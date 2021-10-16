package nl.tettelaar.rebalanced.init.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.impl.biome.modification.BiomeSelectionContextImpl;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.gen.BiomeCreator;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;

@SuppressWarnings("deprecation")
public class DoBiomeModifications {

	private final static String modid = Rebalanced.modid;

	private final static boolean isSimplyImprovedTerrainLoaded = RebalancedWorldGen.isSimplyImprovedTerrainLoaded;

	public static void init() {

		// REMOVE STUPID GEN STUFF
		BiomeModifications.create(new Identifier("remove_lakes_and_springs")).add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), (s) -> {
			s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.LAKE_WATER);
			s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.LAKE_LAVA);
			s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_LAVA);
			s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_WATER);
		});

		// SPAWNING STUFF
		BiomeModifications.create(new Identifier("wither_skeleton")).add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), context -> context.getSpawnSettings().addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WITHER_SKELETON, 1, 1, 1)));

		// GENERATION STUFF
		BiomeModifications.create(new Identifier(modid, "forest_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.WOODED_HILLS), (s) -> {
			// s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
			// Features.SAPLING_FOREST_DEFAULT);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.WOLF);
		});

		BiomeModifications.create(new Identifier(modid, "seagrass_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.excludeByKey(BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_TAIGA_HILLS, BiomeKeys.SNOWY_TAIGA_MOUNTAINS, BiomeKeys.SNOWY_MOUNTAINS, Biomes.SNOWY_GIANT_SPRUCE_TAIGA_KEY,  Biomes.SNOWY_GIANT_SPRUCE_TAIGA_HILLS_KEY, Biomes.SNOWY_GIANT_TREE_TAIGA_KEY ,Biomes.SNOWY_GIANT_TREE_TAIGA_HILLS_KEY).and(BiomeSelectors.categories(Biome.Category.PLAINS, Biome.Category.JUNGLE, Biome.Category.EXTREME_HILLS, Biome.Category.FOREST, Biome.Category.SWAMP, Biome.Category.TAIGA)), (s) -> {
			s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_NORMAL);
		});

		BiomeModifications.create(new Identifier(modid, "plains_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.PLAINS), (s) -> {
			s.setScale(0f);
		});

		BiomeModifications.create(new Identifier(modid, "swamp_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.SWAMP_HILLS), (s) -> {
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.SHEEP);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
			s.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
		});

		BiomeModifications.create(new Identifier(modid, "badlands_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.BADLANDS), (s) -> {
			s.setDepth(0.2F);
		});

		BiomeModifications.create(new Identifier(modid, "badlands_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.BADLANDS_PLATEAU, BiomeKeys.WOODED_BADLANDS_PLATEAU), (s) -> {
			s.setDepth(6F);
		});

		BiomeModifications.create(new Identifier(modid, "hills_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.WOODED_HILLS, BiomeKeys.BIRCH_FOREST_HILLS, BiomeKeys.TALL_BIRCH_HILLS, BiomeKeys.JUNGLE_HILLS, BiomeKeys.DARK_FOREST_HILLS, BiomeKeys.TAIGA_HILLS,
				BiomeKeys.SNOWY_TAIGA_HILLS, BiomeKeys.GIANT_TREE_TAIGA_HILLS, BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS), (s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "swamp_hills_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.SWAMP_HILLS), (s) -> {
			if (isSimplyImprovedTerrainLoaded) {
				s.setDepth(-0.15f);
				s.setScale(0.6f);
			}
		});

		BiomeModifications.create(new Identifier(modid, "river_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.RIVER), (s) -> {
			s.setDepth(RebalancedWorldGen.normalRiverDepth);
		});

		BiomeModifications.create(new Identifier(modid, "frozen_river_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), (s) -> {
			s.setDepth(RebalancedWorldGen.frozenRiverDepth);
		});

		BiomeModifications.create(new Identifier(modid, "change_to_river_category")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(Biomes.BADLANDS_RIVER_KEY, Biomes.JUNGLE_RIVER_KEY), (s) -> {
			s.setCategory(Biome.Category.RIVER);
		});

		BiomeModifications.create(new Identifier(modid, "jungle_river_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(Biomes.BADLANDS_RIVER_KEY, Biomes.JUNGLE_RIVER_KEY), (s) -> {
			s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_RIVER);
		});

		BiomeModifications.create(new Identifier(modid, "beach_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.BEACH), (s) -> {
			s.setDepth(RebalancedWorldGen.beachDepth);
			s.setScale(RebalancedWorldGen.beachScale);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.TURTLE);
		});

		BiomeModifications.create(new Identifier(modid, "savanna_plateau")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.SAVANNA_PLATEAU), (s) -> {
			s.setDepth(4F);
			s.getGenerationSettings().setBuiltInSurfaceBuilder(SurfaceBuilders.CONFIGURED_SAVANNA_PLATEAU_SURFACE);
		});

		BiomeModifications.create(new Identifier(modid, "stone_shore")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.STONE_SHORE), (s) -> {
			stoneShore(s, false);
		});

		BiomeModifications.create(new Identifier(modid, "snowy_stone_shore")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(Biomes.SNOWY_STONE_SHORE_KEY), (s) -> {
			stoneShore(s, true);
		});

		BiomeModifications.create(new Identifier(modid, "non_snowy_extreme_hills")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.MOUNTAINS, BiomeKeys.WOODED_MOUNTAINS), (s) -> {
			nonSnowyExtremeHills(s, false);
		});

		BiomeModifications.create(new Identifier(modid, "non_snowy_extreme_hills_plateau")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(Biomes.EXTREME_HILLS_PLATEAU_KEY), (s) -> {
			nonSnowyExtremeHills(s, true);
		});

		BiomeModifications.create(new Identifier(modid, "gravelly_extreme_hills")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.GRAVELLY_MOUNTAINS, BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS), (s) -> {
			gravellyExtremeHills(s);
		});

		BiomeModifications.create(new Identifier(modid, "snowy_extreme_hills")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.SNOWY_MOUNTAINS), (s) -> {
			snowyExtremeHills(s, false);
		});

		BiomeModifications.create(new Identifier(modid, "snowy_biome_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.categories(Biome.Category.ICY), (s) -> {
			snowyBiome(s);
		});

		BiomeModifications.create(new Identifier(modid, "normal_taiga_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.TAIGA_HILLS, BiomeKeys.TAIGA_MOUNTAINS, BiomeKeys.GIANT_SPRUCE_TAIGA, BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, BiomeKeys.GIANT_TREE_TAIGA, BiomeKeys.GIANT_TREE_TAIGA_HILLS), (s) -> {
					s.getWeather().setTemperature(0.4f);
				});

		BiomeModifications.create(new Identifier(modid, "snowy_taiga_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_TAIGA_HILLS, BiomeKeys.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_GIANT_SPRUCE_TAIGA_KEY, Biomes.SNOWY_GIANT_SPRUCE_TAIGA_HILLS_KEY, Biomes.SNOWY_GIANT_TREE_TAIGA_KEY, Biomes.SNOWY_GIANT_TREE_TAIGA_HILLS_KEY), (s) -> {
					snowyBiome(s);
					s.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 2, 4, 4));
				});

		BiomeModifications.create(new Identifier(modid, "desert_biome_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.categories(Biome.Category.DESERT), (s) -> {
			s.setDepth(0.2f);
			s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE_DESERT);
			s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE);
		});

		BiomeModifications.create(new Identifier(modid, "savanna_biome_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.categories(Biome.Category.SAVANNA), (s) -> {
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CHICKEN);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.LLAMA);
			s.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
			s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_DEFAULT);
			s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_WARM);
		});

		BiomeModifications.create(new Identifier(modid, "ocean_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN), (s) -> {
			normalOcean(s);
		});

		BiomeModifications.create(new Identifier(modid, "warm_ocean_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), (s) -> {
			warmOcean(s);
		});

		BiomeModifications.create(new Identifier(modid, "frozen_ocean_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN), (s) -> {
			frozenOcean(s);
		});

		BiomeModifications.create(new Identifier(modid, "jungle_biome_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.categories(Biome.Category.JUNGLE), (s) -> {
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.SHEEP);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
		});
	}

	public static void normalOcean(BiomeModificationContext s) {
		s.getGenerationSettings().setSurfaceBuilder(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getKey(SurfaceBuilders.CONFIGURED_OCEAN_SURFACE).get());
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE);
		s.setScale(0.03f);
	}

	public static void warmOcean(BiomeModificationContext s) {
		s.getGenerationSettings().setSurfaceBuilder(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getKey(SurfaceBuilders.CONFIGURED_SAND_OCEAN_SURFACE).get());
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE);
		s.setScale(0.03f);
	}

	public static void frozenOcean(BiomeModificationContext s) {
		s.getGenerationSettings().setSurfaceBuilder(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getKey(SurfaceBuilders.CONFIGURED_FROZEN_OCEAN_SURFACE).get());
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE);
		s.setScale(0.03f);
	}

	public static void snowyBiome(BiomeModificationContext s) {
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_DEFAULT);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_FOREST);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_PLAIN);

	}

	public static void nonSnowyExtremeHills(BiomeModificationContext s, boolean plateau) {
		s.getWeather().setTemperature(0.4f);
		s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_NORMAL);
		s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_FOREST);
		s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_TAIGA);
		s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_TALL_GRASS);
		if (!plateau) {
			s.setDepth(2f);
		}
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(1.5f);
		} else {
			s.setScale(0.7f);
		}
	}

	public static void snowyExtremeHills(BiomeModificationContext s, boolean plateau) {
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_LAVA);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_WATER);
		s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.POLAR_BEAR);
		s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.RABBIT);
		s.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 5, 4, 6));
		s.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.GOAT, 10, 4, 6));
		if (!plateau) {
			s.setDepth(2f);
		} else {
			s.setDepth(1f);
		}
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(1.5f);
		} else {
			s.setScale(0.7f);
		}
	}

	public static void gravellyExtremeHills(BiomeModificationContext s) {
		s.getWeather().setTemperature(0.4f);
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(1.5f);
		} else {
			s.setScale(0.7f);
		}
	}

	public static void hillsBiome(BiomeModificationContext s) {
		s.setDepth(RebalancedWorldGen.hillDepth);
		s.setScale(RebalancedWorldGen.hillScale);
	}

	public static void stoneShore(BiomeModificationContext s, boolean snowy) {
		s.setDepth(2F);
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(0.55f);
		} else {
			s.setScale(0.25f);
		}
		if (!snowy) {
			s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_NORMAL);
			s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_FOREST);
			s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_TAIGA);
			s.getGenerationSettings().setSurfaceBuilder(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getKey(SurfaceBuilders.CONFIGURED_STONE_SHORE_SURFACE).get());
			s.getWeather().setTemperature(0.4f);
		} else {
			s.getGenerationSettings().setSurfaceBuilder(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getKey(SurfaceBuilders.CONFIGURED_SNOWY_STONE_SHORE_SURFACE).get());
			s.getWeather().setTemperature(0f);
		}
	}

}
