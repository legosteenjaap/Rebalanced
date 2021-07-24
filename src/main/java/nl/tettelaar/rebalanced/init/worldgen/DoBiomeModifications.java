package nl.tettelaar.rebalanced.init.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
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

public class DoBiomeModifications {

	private final static String modid = Rebalanced.modid;

	private final static boolean isSimplyImprovedTerrainLoaded = RebalancedWorldGen.isSimplyImprovedTerrainLoaded;
	
	@SuppressWarnings("deprecation")
	public static void init() {

		// REMOVE STUPID GEN STUFF
		BiomeModifications.create(new Identifier("remove_lakes_and_springs")).add(ModificationPhase.ADDITIONS,
				BiomeSelectors.foundInOverworld(),
				(s) -> {
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.LAKE_WATER);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.LAKE_LAVA);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_LAVA);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_LAVA_DOUBLE);
				});
		
		// SPAWNING STUFF
		BiomeModifications.create(new Identifier("wither_skeleton")).add(ModificationPhase.ADDITIONS,
				BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
				context -> context.getSpawnSettings().addSpawn(SpawnGroup.MONSTER,
						new SpawnSettings.SpawnEntry(EntityType.WITHER_SKELETON, 1, 1, 1)));
		
		BiomeModifications.create(new Identifier("w")).add(ModificationPhase.ADDITIONS,
				BiomeSelectors.foundInOverworld(),
				context -> context.getSpawnSettings().addSpawn(SpawnGroup.MONSTER,
						new SpawnSettings.SpawnEntry(EntityType.EVOKER, 100, 1, 1)));

		// GENERATION STUFF
		BiomeModifications.create(new Identifier(modid, "badlands_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.BADLANDS_PLATEAU), (s) -> {
					s.setDepth(8F);
				});

		BiomeModifications.create(new Identifier(modid, "badlands_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.WOODED_BADLANDS_PLATEAU), (s) -> {
					s.setDepth(8F);
				});

		BiomeModifications.create(new Identifier(modid, "wooded_hills_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.WOODED_HILLS), (s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "birch_forest_hills_gen")).add(
				ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST_HILLS), (s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "tall_birch_hills_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.TALL_BIRCH_HILLS), (s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "jungle_hills_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.JUNGLE_HILLS), (s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "dark_forest_hills_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST_HILLS), (s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "taiga_hills_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.TAIGA_HILLS), (s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "snowy_taiga_hills_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA_HILLS), (s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "giant_tree_taiga_hills_gen")).add(
				ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.GIANT_TREE_TAIGA_HILLS),
				(s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "giant_spruce_taiga_hills_gen")).add(
				ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS),
				(s) -> {
					hillsBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "bamboo_jungle_hills_gen")).add(
				ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(BiomeKeys.BAMBOO_JUNGLE_HILLS), (s) -> {
					hillsBiome(s);
				});



		BiomeModifications.create(new Identifier(modid, "river_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.RIVER), (s) -> {
					s.setDepth(-1.5F);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_LAVA);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PROTOTYPE_SPRING_WATER);
					s.getWeather().setPrecipitation(Biome.Precipitation.RAIN);
				});

		BiomeModifications.create(new Identifier(modid, "river_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), (s) -> {
					s.setDepth(-1F);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_LAVA);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PROTOTYPE_SPRING_WATER);
				});

		BiomeModifications.create(new Identifier(modid, "river_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(Biomes.BADLANDS_RIVER_KEY), (s) -> {
					s.setCategory(Biome.Category.RIVER);
				});

		BiomeModifications.create(new Identifier(modid, "beach_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.BEACH), (s) -> {
					s.setDepth(0.05F);
					s.setScale(0.0001F);
					s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.TURTLE);
					s.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnEntry(EntityType.TURTLE, 1, 0, 1));
				});

		BiomeModifications.create(new Identifier(modid, "savanna_plateau")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.SAVANNA_PLATEAU), (s) -> {
					s.setDepth(4F);
				});

		BiomeModifications.create(new Identifier(modid, "stone_shore")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.STONE_SHORE), (s) -> {
					s.setDepth(5F);
					if (isSimplyImprovedTerrainLoaded) {
						s.setScale(0.55f);
					} else {
						s.setScale(0.25f);
					}
					s.getWeather().setTemperature(0.4f);
					s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
							ConfiguredFeatures.PATCH_GRASS_NORMAL);
					s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
							ConfiguredFeatures.PATCH_GRASS_FOREST);
					s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
							ConfiguredFeatures.PATCH_GRASS_TAIGA);
					s.getGenerationSettings().setSurfaceBuilder(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER
							.getKey(BiomeCreator.CONFIGURED_STONE_SHORE_SURFACE).get());
				});
		BiomeModifications.create(new Identifier(modid, "extreme_hills")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.MOUNTAINS), (s) -> {
					nonSnowyExtremeHills(s);
				});

		BiomeModifications.create(new Identifier(modid, "extreme_hills")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.WOODED_MOUNTAINS), (s) -> {
					nonSnowyExtremeHills(s);
				});

		BiomeModifications.create(new Identifier(modid, "extreme_hills")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.TAIGA_MOUNTAINS), (s) -> {
					nonSnowyExtremeHills(s);
				});

		BiomeModifications.create(new Identifier(modid, "extreme_hills")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.TAIGA_MOUNTAINS), (s) -> {
					nonSnowyExtremeHills(s);
				});

		BiomeModifications.create(new Identifier(modid, "extreme_hills")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(Biomes.EXTREME_HILLS_PLATEAU_KEY), (s) -> {
					s.getWeather().setTemperature(0.4f);
				});

		BiomeModifications.create(new Identifier(modid, "extreme_hills")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.GRAVELLY_MOUNTAINS), (s) -> {
					gravellyExtremeHills(s);
				});

		BiomeModifications.create(new Identifier(modid, "extreme_hills")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS), (s) -> {
					gravellyExtremeHills(s);
				});

		BiomeModifications.create(new Identifier(modid, "snowy_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.categories(Biome.Category.ICY), (s) -> {
					snowyBiome(s);
				});
		BiomeModifications.create(new Identifier(modid, "snowy_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA), (s) -> {
					snowyBiome(s);
				});
		BiomeModifications.create(new Identifier(modid, "snowy_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA_HILLS), (s) -> {
					snowyBiome(s);
				});
		BiomeModifications.create(new Identifier(modid, "snowy_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA_MOUNTAINS), (s) -> {
					snowyBiome(s);
				});

		BiomeModifications.create(new Identifier(modid, "desert_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.categories(Biome.Category.DESERT), (s) -> {
					s.setDepth(0.3f);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE_DESERT);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE);
				});

		BiomeModifications.create(new Identifier(modid, "savanna_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.categories(Biome.Category.SAVANNA), (s) -> {
					s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.SHEEP);
					s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CHICKEN);
					s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
					s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_DEFAULT);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_WARM);
				});

		BiomeModifications.create(new Identifier(modid, "taiga_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.categories(Biome.Category.DESERT), (s) -> {
					s.setDepth(0.2f);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE);
				});

		BiomeModifications.create(new Identifier(modid, "ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.OCEAN), (s) -> {
					normalOcean(s);
				});
		BiomeModifications.create(new Identifier(modid, "ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN), (s) -> {
					normalOcean(s);
				});
		BiomeModifications.create(new Identifier(modid, "ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN), (s) -> {
					normalOcean(s);
				});
		BiomeModifications.create(new Identifier(modid, "ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.DEEP_COLD_OCEAN), (s) -> {
					normalOcean(s);
				});

		BiomeModifications.create(new Identifier(modid, "warm_ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), (s) -> {
					warmOcean(s);
				});
		BiomeModifications.create(new Identifier(modid, "warm_ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN), (s) -> {
					warmOcean(s);
				});
		BiomeModifications.create(new Identifier(modid, "warm_ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.DEEP_LUKEWARM_OCEAN), (s) -> {
					warmOcean(s);
				});

		BiomeModifications.create(new Identifier(modid, "frozen_ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN), (s) -> {
					frozenOcean(s);
				});
		BiomeModifications.create(new Identifier(modid, "frozen_ocean_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BiomeKeys.DEEP_FROZEN_OCEAN), (s) -> {
					frozenOcean(s);
				});
		BiomeModifications.create(new Identifier(modid, "jungle_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.categories(Biome.Category.JUNGLE), (s) -> {
				});
	}

	public static void normalOcean(BiomeModificationContext s) {
		s.getGenerationSettings().setSurfaceBuilder(
				BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getKey(BiomeCreator.CONFIGURED_OCEAN_SURFACE).get());
	}

	public static void warmOcean(BiomeModificationContext s) {
		s.getGenerationSettings().setSurfaceBuilder(
				BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getKey(BiomeCreator.CONFIGURED_SAND_OCEAN_SURFACE).get());
		
	}

	public static void frozenOcean(BiomeModificationContext s) {
		s.getGenerationSettings().setSurfaceBuilder(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER
				.getKey(BiomeCreator.CONFIGURED_FROZEN_OCEAN_SURFACE).get());
	}

	@SuppressWarnings("deprecation")
	public static void snowyBiome(BiomeModificationContext s) {
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_SUGAR_CANE);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_TAIGA_GRASS);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_GRASS_NORMAL);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_GRASS_BADLANDS);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_DEFAULT);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_FOREST);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FLOWER_PLAIN);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PATCH_GRASS_TAIGA_2);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.BROWN_MUSHROOM_TAIGA);
		s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.RED_MUSHROOM_TAIGA);
	}

	@SuppressWarnings("deprecation")
	public static void nonSnowyExtremeHills(BiomeModificationContext s) {
		s.getWeather().setTemperature(0.3f);
		//s.getGenerationSettings().setBuiltInSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.PATCH_GRASS_NORMAL);
		s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.PATCH_GRASS_FOREST);
		s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.PATCH_GRASS_TAIGA);
		s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.PATCH_TALL_GRASS);
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(1.5f);
		} else {
			s.setScale(0.7f);
		}
	}

	@SuppressWarnings("deprecation")
	public static void gravellyExtremeHills(BiomeModificationContext s) {
		s.getWeather().setTemperature(0.3f);
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(1.5f);
		} else {
			s.setScale(0.7f);
		}
	}

	@SuppressWarnings("deprecation")
	public static void hillsBiome(BiomeModificationContext s) {
		s.setDepth(0.11F);
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(1.2f);
		} else {
			s.setScale(0.6f);
		}
	}
	
}
