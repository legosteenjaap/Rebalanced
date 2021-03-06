package nl.tettelaar.rebalanced.init.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;

@SuppressWarnings("deprecation")
public class DoBiomeModifications {

	private final static String modid = Rebalanced.modid;

	public static void init() {


		// SPAWNING STUFF
		BiomeModifications.create(new ResourceLocation("wither_skeleton")).add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY), context -> context.getSpawnSettings().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 1, 1, 1)));

		// GENERATION STUFF
		BiomeModifications.create(new ResourceLocation(modid, "forest_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(Biomes.FOREST), (s) -> {
			// s.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION,
			// Features.SAPLING_FOREST_DEFAULT);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.WOLF);
		});

		BiomeModifications.create(new ResourceLocation(modid, "seagrass_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.foundInOverworld(), (s) -> {
			s.getGenerationSettings().removeBuiltInFeature(AquaticPlacements.SEAGRASS_SIMPLE.value());
			s.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE.value());
		});


		BiomeModifications.create(new ResourceLocation(modid, "swamp_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.includeByKey(Biomes.SWAMP), (s) -> {
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.SHEEP);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
			s.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
		});


		BiomeModifications.create(new ResourceLocation(modid, "snowy_biome_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.categories(Biome.BiomeCategory.ICY), (s) -> {
			snowyBiome(s);
		});

		BiomeModifications.create(new ResourceLocation(modid, "normal_taiga_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(Biomes.TAIGA), (s) -> {
				});

		BiomeModifications.create(new ResourceLocation(modid, "snowy_taiga_biome_gen")).add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.includeByKey(Biomes.SNOWY_TAIGA), (s) -> {
					snowyBiome(s);
					s.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 4, 4));
				});

		BiomeModifications.create(new ResourceLocation(modid, "desert_biome_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.categories(Biome.BiomeCategory.DESERT), (s) -> {
			s.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.PATCH_SUGAR_CANE_DESERT.value());
			s.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.PATCH_SUGAR_CANE.value());
		});

		BiomeModifications.create(new ResourceLocation(modid, "savanna_biome_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.categories(Biome.BiomeCategory.SAVANNA), (s) -> {
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CHICKEN);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.LLAMA);
			s.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
			s.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.FLOWER_DEFAULT.value());
			s.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.FLOWER_WARM.value());
		});

		BiomeModifications.create(new ResourceLocation(modid, "jungle_biome_gen")).add(ModificationPhase.POST_PROCESSING, BiomeSelectors.categories(Biome.BiomeCategory.JUNGLE), (s) -> {
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.SHEEP);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
			s.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
		});
	}


	public static void snowyBiome(BiomeModificationContext s) {
		s.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.PATCH_SUGAR_CANE.value());
		s.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.FLOWER_DEFAULT.value());
		s.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.FLOWER_FLOWER_FOREST.value());
		s.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.FLOWER_PLAINS.value());

	}

}