package nl.tettelaar.rebalanced.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext.GenerationSettingsContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.gen.BiomeCreator;
import nl.tettelaar.rebalanced.mixin.DefaultBiomeInvoker;

@SuppressWarnings("deprecation")
public class RebalancedWorldGen {

	private final static String modid = Rebalanced.modid;

	public static final int mainContinentSize = 6;
	
	private final static boolean disableRiver = true;
	
	private final static boolean isSimplyImprovedTerrainLoaded = FabricLoader.getInstance()
			.isModLoaded("simplyimprovedterrain");

	// PLATEAU STUFF
	private static final Biome JUNGLE_PLATEAU = DefaultBiomeInvoker.invokeCreateJungle(4.5F, 0.25F, 10, 1, 1);
	public static final RegistryKey<Biome> JUNGLE_PLATEAU_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "jungle_plateau"));

	private static final Biome BADLANDS_LOW_PLATEAU = DefaultBiomeInvoker.invokeCreateNormalBadlands(4F, 0.025F, true);
	public static final RegistryKey<Biome> BADLANDS_LOW_PLATEAU_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "badlands_low_plateau"));

	private static final Biome EXTREME_HILLS_PLATEAU = DefaultBiomeCreator.createMountains(3.3f,
			isSimplyImprovedTerrainLoaded ? 1.4f : 0.8f, ConfiguredSurfaceBuilders.GRASS, false);
	public static final RegistryKey<Biome> EXTREME_HILLS_PLATEAU_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "extreme_hills_plateau"));

	// HILLS STUFF

	private static final Biome SAVANNA_HILLS = DefaultBiomeCreator.createSavanna(0.11f,
			isSimplyImprovedTerrainLoaded ? 1.2f : 0.7f, 1f, false, false);
	public static final RegistryKey<Biome> SAVANNA_HILLS_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "savanna_hills"));

	// BEACH STUFF
	private static final Biome SAVANNA_BEACH = BiomeCreator.createSavannaBeach(0.05f, 0.0001f, 1.2f, 0f, 4159204);
	public static final RegistryKey<Biome> SAVANNA_BEACH_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "savanna_beach"));

	private static final Biome GRAVELLY_BEACH = BiomeCreator.createGravellyBeach(0.02f, 0.0001f, 0.25f, 0.8f, 4159204);
	public static final RegistryKey<Biome> GRAVELLY_BEACH_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "gravelly_beach"));

	// RIVER STUFF
	private static final Biome DESERT_RIVER = BiomeCreator.createDesertRiver(-1.5f, 0.1f);
	public static final RegistryKey<Biome> DESERT_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "desert_river"));

	private static final Biome BADLANDS_RIVER = DefaultBiomeInvoker.createBadlands(ConfiguredSurfaceBuilders.BADLANDS,
			-0.5f, 0.1f, false, false);
	public static final RegistryKey<Biome> BADLANDS_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "badlands_river"));

	private static final Biome EXTREME_HILLS_RIVER = BiomeCreator.createExtremeHillsRiver();
	public static final RegistryKey<Biome> EXTREME_HILLS_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "extreme_hills_river"));

	private static final Biome SAVANNA_RIVER = DefaultBiomeCreator.createSavanna(-1.5f, 0.1f, 1f, false, false);
	public static final RegistryKey<Biome> SAVANNA_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "savanna_river"));

	public static void init() {
		doBiomeModifications();
		registerFeatures();
		registerSurfaceBuilders();
		registerBiomes();
		addBiomeVariants();
	}

	public static void registerFeatures() {
	}

	public static void registerBiomes() {
		Registry.register(BuiltinRegistries.BIOME, JUNGLE_PLATEAU_KEY.getValue(), JUNGLE_PLATEAU);
		Registry.register(BuiltinRegistries.BIOME, BADLANDS_LOW_PLATEAU_KEY.getValue(), BADLANDS_LOW_PLATEAU);
		Registry.register(BuiltinRegistries.BIOME, SAVANNA_BEACH_KEY.getValue(), SAVANNA_BEACH);
		Registry.register(BuiltinRegistries.BIOME, GRAVELLY_BEACH_KEY.getValue(), GRAVELLY_BEACH);
		Registry.register(BuiltinRegistries.BIOME, DESERT_RIVER_KEY.getValue(), DESERT_RIVER);
		Registry.register(BuiltinRegistries.BIOME, BADLANDS_RIVER_KEY.getValue(), BADLANDS_RIVER);
		Registry.register(BuiltinRegistries.BIOME, EXTREME_HILLS_RIVER_KEY.getValue(), EXTREME_HILLS_RIVER);
		Registry.register(BuiltinRegistries.BIOME, EXTREME_HILLS_PLATEAU_KEY.getValue(), EXTREME_HILLS_PLATEAU);
		Registry.register(BuiltinRegistries.BIOME, SAVANNA_HILLS_KEY.getValue(), SAVANNA_HILLS);
		Registry.register(BuiltinRegistries.BIOME, SAVANNA_RIVER_KEY.getValue(), SAVANNA_RIVER);
	}

	public static void registerSurfaceBuilders() {
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "savanna_beach"),
				BiomeCreator.SAVANNA_BEACH_SURFACE);
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "gravelly"), BiomeCreator.GRAVELLY_SURFACE);
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "elevated_extreme_hills"),
				BiomeCreator.ELEVATED_EXTREME_HILLS_SURFACE);
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "stone_shore"),
				BiomeCreator.STONE_SHORE_SURFACE);

		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "elevated_extreme_hills"),
				BiomeCreator.CONFIGURED_ELEVATED_EXTREME_HILLS_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "stone_shore"),
				BiomeCreator.CONFIGURED_STONE_SHORE_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "ocean"),
				BiomeCreator.CONFIGURED_OCEAN_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "frozen_ocean"),
				BiomeCreator.CONFIGURED_FROZEN_OCEAN_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "sand_ocean"),
				BiomeCreator.CONFIGURED_SAND_OCEAN_SURFACE);
	}

	public static void addBiomeVariants() {
		OverworldBiomes.addEdgeBiome(BiomeKeys.BADLANDS_PLATEAU, BADLANDS_LOW_PLATEAU_KEY, 1.3);
		OverworldBiomes.addEdgeBiome(BiomeKeys.WOODED_BADLANDS_PLATEAU, BADLANDS_LOW_PLATEAU_KEY, 1.3);
		/*OverworldBiomes.addBiomeVariant(BiomeKeys.PLAINS, BiomeKeys.FOREST, 0.1);
		OverworldBiomes.addBiomeVariant(BiomeKeys.PLAINS, BiomeKeys.SWAMP, 0.1);
		OverworldBiomes.addBiomeVariant(BiomeKeys.PLAINS, BiomeKeys.DARK_FOREST, 0.1);
		OverworldBiomes.addBiomeVariant(BiomeKeys.PLAINS, BiomeKeys.BIRCH_FOREST, 0.1);
		OverworldBiomes.addBiomeVariant(BiomeKeys.PLAINS, BiomeKeys.TAIGA, 0.1);
		OverworldBiomes.addBiomeVariant(BiomeKeys.TAIGA, BiomeKeys.GIANT_TREE_TAIGA, 0.1);
		OverworldBiomes.addBiomeVariant(BiomeKeys.TAIGA, BiomeKeys.GIANT_SPRUCE_TAIGA, 0.1);*/
		OverworldBiomes.addHillsBiome(BiomeKeys.MOUNTAINS, EXTREME_HILLS_PLATEAU_KEY, 1f);
		OverworldBiomes.addHillsBiome(BiomeKeys.JUNGLE, JUNGLE_PLATEAU_KEY, 0.25);

		OverworldBiomes.addShoreBiome(BiomeKeys.SAVANNA, SAVANNA_BEACH_KEY, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.SAVANNA_PLATEAU, SAVANNA_BEACH_KEY, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.SHATTERED_SAVANNA, SAVANNA_BEACH_KEY, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.SHATTERED_SAVANNA_PLATEAU, SAVANNA_BEACH_KEY, 1f);

		OverworldBiomes.addHillsBiome(BiomeKeys.SAVANNA, SAVANNA_HILLS_KEY, 0.6f);
		OverworldBiomes.addHillsBiome(BiomeKeys.PLAINS, BiomeKeys.WOODED_HILLS, 0.2f);

		if (!disableRiver) {
			OverworldBiomes.setRiverBiome(BiomeKeys.SAVANNA, SAVANNA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(SAVANNA_HILLS_KEY, SAVANNA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.SAVANNA_PLATEAU, SAVANNA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.SHATTERED_SAVANNA, SAVANNA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.SHATTERED_SAVANNA_PLATEAU, SAVANNA_RIVER_KEY);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.DESERT, DESERT_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.DESERT_HILLS, DESERT_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.DESERT_LAKES, DESERT_RIVER_KEY);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.BADLANDS_PLATEAU, BADLANDS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.WOODED_BADLANDS_PLATEAU, BADLANDS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BADLANDS_LOW_PLATEAU_KEY, BADLANDS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.BADLANDS, BADLANDS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.ERODED_BADLANDS, BADLANDS_RIVER_KEY);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_TAIGA, BiomeKeys.FROZEN_RIVER);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_TAIGA_HILLS, BiomeKeys.FROZEN_RIVER);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_TAIGA_MOUNTAINS, BiomeKeys.FROZEN_RIVER);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_TUNDRA, BiomeKeys.FROZEN_RIVER);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_BEACH, BiomeKeys.FROZEN_RIVER);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_MOUNTAINS, BiomeKeys.FROZEN_RIVER);

			OverworldBiomes.setRiverBiome(BiomeKeys.SWAMP, BiomeKeys.SWAMP);
			OverworldBiomes.setRiverBiome(BiomeKeys.SWAMP_HILLS, BiomeKeys.SWAMP_HILLS);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.MOUNTAINS, EXTREME_HILLS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.WOODED_MOUNTAINS, EXTREME_HILLS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.STONE_SHORE, EXTREME_HILLS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.GRAVELLY_MOUNTAINS, EXTREME_HILLS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS, EXTREME_HILLS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.TAIGA_MOUNTAINS, EXTREME_HILLS_RIVER_KEY);
		}
		//OverworldBiomes.addBiomeVariant(BiomeKeys.FOREST, BiomeKeys.DESERT, 1, OverworldClimate.DRY);
		OverworldBiomes.addShoreBiome(BiomeKeys.DESERT, BiomeKeys.DESERT, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.DESERT_HILLS, BiomeKeys.DESERT_HILLS, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.DESERT_LAKES, BiomeKeys.DESERT_LAKES, 1f);
		

		OverworldBiomes.addShoreBiome(BiomeKeys.TAIGA, GRAVELLY_BEACH_KEY, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.TAIGA_HILLS, GRAVELLY_BEACH_KEY, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.GIANT_TREE_TAIGA, GRAVELLY_BEACH_KEY, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.GIANT_SPRUCE_TAIGA, GRAVELLY_BEACH_KEY, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.TAIGA, GRAVELLY_BEACH_KEY, 1f);

		OverworldBiomes.addShoreBiome(BiomeKeys.BADLANDS, BiomeKeys.BADLANDS, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.WOODED_BADLANDS_PLATEAU, BiomeKeys.WOODED_BADLANDS_PLATEAU, 1f);
		OverworldBiomes.addShoreBiome(BADLANDS_LOW_PLATEAU_KEY, BADLANDS_LOW_PLATEAU_KEY, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.BADLANDS_PLATEAU, BiomeKeys.BADLANDS_PLATEAU, 1f);
		OverworldBiomes.addShoreBiome(BiomeKeys.ERODED_BADLANDS, BiomeKeys.ERODED_BADLANDS, 1f);

		

		/*OverworldBiomes.addBiomeVariant(BiomeKeys.FROZEN_OCEAN, BiomeKeys.OCEAN, 1f);
		OverworldBiomes.addBiomeVariant(BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_OCEAN, 0.8f);
		OverworldBiomes.addBiomeVariant(BiomeKeys.MOUNTAINS, BiomeKeys.WOODED_MOUNTAINS, 0.2);
		OverworldBiomes.addBiomeVariant(BiomeKeys.SNOWY_TUNDRA, BiomeKeys.FROZEN_OCEAN, 0.2f);
		OverworldBiomes.addBiomeVariant(BiomeKeys.SNOWY_BEACH, BiomeKeys.FROZEN_OCEAN, 0.2);*/
		OverworldBiomes.addHillsBiome(BiomeKeys.DARK_FOREST, BiomeKeys.DARK_FOREST_HILLS, 1f);
	}

	public static void doBiomeModifications() {

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
					s.setDepth(-1.5F);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.SPRING_LAVA);
					s.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.PROTOTYPE_SPRING_WATER);
				});

		BiomeModifications.create(new Identifier(modid, "river_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(BADLANDS_RIVER_KEY), (s) -> {
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
				BiomeSelectors.includeByKey(EXTREME_HILLS_PLATEAU_KEY), (s) -> {
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
		//s.setDepth(-0.1f);
	}

	public static void gravellyExtremeHills(BiomeModificationContext s) {
		s.getWeather().setTemperature(0.3f);
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(1.5f);
		} else {
			s.setScale(0.7f);
		}
		//s.setDepth(-0.1f);
	}

	public static void hillsBiome(BiomeModificationContext s) {
		// s.setDepth(0.11F);
		// s.setScale(0.8F);
		s.setDepth(0.11F);
		if (isSimplyImprovedTerrainLoaded) {
			s.setScale(1.2f);
		} else {
			s.setScale(0.6f);
		}
	}
	
	public static void addLushCave (BiomeModificationContext s) {
		GenerationSettingsContext gen = s.getGenerationSettings();
		gen.addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.LUSH_CAVES_CEILING_VEGETATION);
		gen.addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.CAVE_VINES);
		gen.addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.LUSH_CAVES_CLAY);
		gen.addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.LUSH_CAVES_VEGETATION);
		gen.addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.ROOTED_AZALEA_TREES);
		gen.addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPORE_BLOSSOM);
		gen.addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				ConfiguredFeatures.CLASSIC_VINES_CAVE_FEATURE);
	}

}
