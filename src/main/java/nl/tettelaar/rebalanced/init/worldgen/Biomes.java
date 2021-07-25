package nl.tettelaar.rebalanced.init.worldgen;

import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.gen.BiomeCreator;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;
import nl.tettelaar.rebalanced.mixin.worldgen.DefaultBiomeInvoker;

public class Biomes {

	private final static String modid = Rebalanced.modid;

	private final static boolean isSimplyImprovedTerrainLoaded = RebalancedWorldGen.isSimplyImprovedTerrainLoaded;

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
	
	private static final Biome TROPICAL_BEACH = BiomeCreator.createTropicalBeach(0.05f, 0.0001f, 1.2f, 0f, 4159204, false, false);
	public static final RegistryKey<Biome> TROPICAL_BEACH_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "tropical_beach"));

	private static final Biome GRAVELLY_BEACH = BiomeCreator.createGravellyBeach(false);
	public static final RegistryKey<Biome> GRAVELLY_BEACH_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "gravelly_beach"));

	// RIVER STUFF
	private static final Biome DESERT_RIVER = BiomeCreator.createDesertRiver(RebalancedWorldGen.dryRiverDepth, 0.1f);
	public static final RegistryKey<Biome> DESERT_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "desert_river"));

	private static final Biome BADLANDS_RIVER = DefaultBiomeInvoker.createBadlands(ConfiguredSurfaceBuilders.BADLANDS,
			RebalancedWorldGen.dryRiverDepth, 0.1f, false, false);
	public static final RegistryKey<Biome> BADLANDS_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "badlands_river"));

	private static final Biome EXTREME_HILLS_RIVER = BiomeCreator.createExtremeHillsRiver();
	public static final RegistryKey<Biome> EXTREME_HILLS_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "extreme_hills_river"));

	private static final Biome SAVANNA_RIVER = DefaultBiomeCreator.createSavanna(RebalancedWorldGen.dryRiverDepth, 0.1f, 1f, false, false);
	public static final RegistryKey<Biome> SAVANNA_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "savanna_river"));
	
	private static final Biome JUNGLE_RIVER = BiomeCreator.createJungleRiver();
	public static final RegistryKey<Biome> JUNGLE_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "jungle_river"));
	
	private static final Biome TAIGA_RIVER = BiomeCreator.createTaigaRiver(false);
	public static final RegistryKey<Biome> TAIGA_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "taiga_river"));
	
	private static final Biome SNOWY_TAIGA_RIVER = BiomeCreator.createTaigaRiver(true);
	public static final RegistryKey<Biome> SNOWY_TAIGA_RIVER_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "snowy_taiga_river"));

	//SNOWY BIOME VARIANTS
	public static final Biome SNOWY_GIANT_TREE_TAIGA = BiomeCreator.createSnowyGiantTreeTaiga(0.2f, 0.2f, false);
	public static final RegistryKey<Biome> SNOWY_GIANT_TREE_TAIGA_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "snowy_giant_tree_taiga"));

	private static final Biome SNOWY_GIANT_TREE_TAIGA_HILLS = BiomeCreator.createSnowyGiantTreeTaiga(0.11f, isSimplyImprovedTerrainLoaded ? 1.2f : 0.6f, false);
	public static final RegistryKey<Biome> SNOWY_GIANT_TREE_TAIGA_HILLS_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "snowy_giant_tree_taiga_hills"));
	
	public static final Biome SNOWY_GIANT_SPRUCE_TAIGA = BiomeCreator.createSnowyGiantTreeTaiga(0.2f, 0.2f, true);
	public static final RegistryKey<Biome> SNOWY_GIANT_SPRUCE_TAIGA_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "snowy_giant_spruce_taiga"));

	private static final Biome SNOWY_GIANT_SPRUCE_TAIGA_HILLS = BiomeCreator.createSnowyGiantTreeTaiga(0.11f, isSimplyImprovedTerrainLoaded ? 1.2f : 0.6f, true);
	public static final RegistryKey<Biome> SNOWY_GIANT_SPRUCE_TAIGA_HILLS_KEY = RegistryKey.of(Registry.BIOME_KEY,
			new Identifier(modid, "snowy_giant_spruce_taiga_hills"));
	
	public static void init() {
		registerBiomes();
		addBiomeVariants();
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
		Registry.register(BuiltinRegistries.BIOME, JUNGLE_RIVER_KEY.getValue(), JUNGLE_RIVER);
		Registry.register(BuiltinRegistries.BIOME, SNOWY_GIANT_TREE_TAIGA_KEY.getValue(), SNOWY_GIANT_TREE_TAIGA);
		Registry.register(BuiltinRegistries.BIOME, SNOWY_GIANT_TREE_TAIGA_HILLS_KEY.getValue(), SNOWY_GIANT_TREE_TAIGA_HILLS);
		Registry.register(BuiltinRegistries.BIOME, SNOWY_GIANT_SPRUCE_TAIGA_KEY.getValue(), SNOWY_GIANT_SPRUCE_TAIGA);
		Registry.register(BuiltinRegistries.BIOME, SNOWY_GIANT_SPRUCE_TAIGA_HILLS_KEY.getValue(), SNOWY_GIANT_SPRUCE_TAIGA_HILLS);
		Registry.register(BuiltinRegistries.BIOME, TROPICAL_BEACH_KEY.getValue(), TROPICAL_BEACH);
		Registry.register(BuiltinRegistries.BIOME, TAIGA_RIVER_KEY.getValue(), TAIGA_RIVER);
		Registry.register(BuiltinRegistries.BIOME, SNOWY_TAIGA_RIVER_KEY.getValue(), SNOWY_TAIGA_RIVER);
		

	}
	
	@SuppressWarnings("deprecation")
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

		if (!RebalancedWorldGen.disableRiver) {
			OverworldBiomes.setRiverBiome(BiomeKeys.SAVANNA, SAVANNA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(SAVANNA_HILLS_KEY, SAVANNA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.SAVANNA_PLATEAU, SAVANNA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.SHATTERED_SAVANNA, SAVANNA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.SHATTERED_SAVANNA_PLATEAU, SAVANNA_RIVER_KEY);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.JUNGLE, JUNGLE_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.JUNGLE_EDGE, JUNGLE_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.JUNGLE_HILLS, JUNGLE_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.BAMBOO_JUNGLE, JUNGLE_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.BAMBOO_JUNGLE_HILLS, JUNGLE_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.MODIFIED_JUNGLE, JUNGLE_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.MODIFIED_JUNGLE_EDGE, JUNGLE_RIVER_KEY);
			OverworldBiomes.setRiverBiome(JUNGLE_PLATEAU_KEY, JUNGLE_RIVER_KEY);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.DESERT, DESERT_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.DESERT_HILLS, DESERT_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.DESERT_LAKES, DESERT_RIVER_KEY);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.BADLANDS_PLATEAU, BADLANDS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.WOODED_BADLANDS_PLATEAU, BADLANDS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BADLANDS_LOW_PLATEAU_KEY, BADLANDS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.BADLANDS, BADLANDS_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.ERODED_BADLANDS, BADLANDS_RIVER_KEY);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_TAIGA, SNOWY_TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_TAIGA_HILLS, SNOWY_TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_TAIGA_MOUNTAINS, SNOWY_TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(SNOWY_GIANT_SPRUCE_TAIGA_HILLS_KEY, SNOWY_TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(SNOWY_GIANT_TREE_TAIGA_HILLS_KEY, SNOWY_TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(SNOWY_GIANT_SPRUCE_TAIGA_KEY, SNOWY_TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(SNOWY_GIANT_TREE_TAIGA_KEY, SNOWY_TAIGA_RIVER_KEY);
				
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_TUNDRA, BiomeKeys.FROZEN_RIVER);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_BEACH, BiomeKeys.FROZEN_RIVER);
			OverworldBiomes.setRiverBiome(BiomeKeys.SNOWY_MOUNTAINS, BiomeKeys.FROZEN_RIVER);
			
			OverworldBiomes.setRiverBiome(BiomeKeys.TAIGA, TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.TAIGA_HILLS, TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.TAIGA_MOUNTAINS, TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.GIANT_TREE_TAIGA_HILLS, TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.GIANT_SPRUCE_TAIGA, TAIGA_RIVER_KEY);
			OverworldBiomes.setRiverBiome(BiomeKeys.GIANT_TREE_TAIGA, TAIGA_RIVER_KEY);
			

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
		
		OverworldBiomes.addShoreBiome(BiomeKeys.JUNGLE, TROPICAL_BEACH_KEY, 1F);
		OverworldBiomes.addShoreBiome(BiomeKeys.JUNGLE_EDGE, TROPICAL_BEACH_KEY, 1F);
		OverworldBiomes.addShoreBiome(BiomeKeys.JUNGLE_HILLS, TROPICAL_BEACH_KEY, 1F);
		OverworldBiomes.addShoreBiome(BiomeKeys.BAMBOO_JUNGLE, TROPICAL_BEACH_KEY, 1F);
		OverworldBiomes.addShoreBiome(BiomeKeys.BAMBOO_JUNGLE_HILLS, TROPICAL_BEACH_KEY, 1F);
		OverworldBiomes.addShoreBiome(BiomeKeys.MODIFIED_JUNGLE, TROPICAL_BEACH_KEY, 1F);
		OverworldBiomes.addShoreBiome(BiomeKeys.MODIFIED_JUNGLE_EDGE, TROPICAL_BEACH_KEY, 1F);
		OverworldBiomes.addShoreBiome(JUNGLE_PLATEAU_KEY, TROPICAL_BEACH_KEY, 1F);

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
		
		OverworldBiomes.addShoreBiome(SNOWY_GIANT_SPRUCE_TAIGA_KEY, BiomeKeys.SNOWY_BEACH, 1f);
		OverworldBiomes.addShoreBiome(SNOWY_GIANT_SPRUCE_TAIGA_HILLS_KEY, BiomeKeys.SNOWY_BEACH, 1f);
		OverworldBiomes.addShoreBiome(SNOWY_GIANT_TREE_TAIGA_KEY, BiomeKeys.SNOWY_BEACH, 1f);
		OverworldBiomes.addShoreBiome(SNOWY_GIANT_TREE_TAIGA_HILLS_KEY, BiomeKeys.SNOWY_BEACH, 1f);
	}
	
}
