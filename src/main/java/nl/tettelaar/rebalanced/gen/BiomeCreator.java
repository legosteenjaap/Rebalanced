package nl.tettelaar.rebalanced.gen;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import nl.tettelaar.rebalanced.gen.surfacebuilders.GravellySurfaceBuilder;
import nl.tettelaar.rebalanced.gen.surfacebuilders.HeightDependSurfaceBuilder;
import nl.tettelaar.rebalanced.gen.surfacebuilders.SavannaBeachSurfaceBuilder;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;
import nl.tettelaar.rebalanced.mixin.worldgen.DefaultBiomeInvoker;

public class BiomeCreator {
	
	public static SavannaBeachSurfaceBuilder SAVANNA_BEACH_SURFACE = new SavannaBeachSurfaceBuilder(TernarySurfaceConfig.CODEC);
	public static GravellySurfaceBuilder GRAVELLY_SURFACE = new GravellySurfaceBuilder(TernarySurfaceConfig.CODEC);

	public static HeightDependSurfaceBuilder STONE_SHORE_SURFACE = new HeightDependSurfaceBuilder(TernarySurfaceConfig.CODEC, 96);

	public static HeightDependSurfaceBuilder ELEVATED_EXTREME_HILLS_SURFACE = new HeightDependSurfaceBuilder(TernarySurfaceConfig.CODEC, 90);

	private static final TernarySurfaceConfig SAVANNA_BEACH_CONFIG = new TernarySurfaceConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
	private static final TernarySurfaceConfig GRAVELLY_CONFIG = new TernarySurfaceConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
	public static final TernarySurfaceConfig SAND_STONE_CONFIG = new TernarySurfaceConfig(Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState());

	public static final TernarySurfaceConfig STONE_SAND_UNDERWATER_CONFIG = new TernarySurfaceConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
	public static final TernarySurfaceConfig NORMAL_UNDERWATER_CONFIG = new TernarySurfaceConfig(Blocks.GRAVEL.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.GRAVEL.getDefaultState());

	public static final TernarySurfaceConfig STONE_SHORE_CONFIG = new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());

	public static final TernarySurfaceConfig ELEVATED_EXTREME_HILLS_CONFIG = new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());

	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SAVANNA_BEACH_SURFACE = SAVANNA_BEACH_SURFACE.withConfig(SAVANNA_BEACH_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_GRAVELLY_SURFACE = GRAVELLY_SURFACE.withConfig(GRAVELLY_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SANDSTONE_RIVER_SURFACE = SurfaceBuilder.DEFAULT.withConfig(SAND_STONE_CONFIG);

	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_STONE_SHORE_SURFACE = STONE_SHORE_SURFACE.withConfig(STONE_SHORE_CONFIG);

	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_ELEVATED_EXTREME_HILLS_SURFACE = ELEVATED_EXTREME_HILLS_SURFACE.withConfig(ELEVATED_EXTREME_HILLS_CONFIG);

	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_OCEAN_SURFACE = SurfaceBuilder.DEFAULT.withConfig(NORMAL_UNDERWATER_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SAND_OCEAN_SURFACE = SurfaceBuilder.DEFAULT.withConfig(STONE_SAND_UNDERWATER_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_FROZEN_OCEAN_SURFACE = SurfaceBuilder.FROZEN_OCEAN.withConfig(NORMAL_UNDERWATER_CONFIG);

	public static Biome createSavannaBeach(float depth, float scale, float temperature, float downfall, int waterColor) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.TURTLE, 1, 1, 1));

		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
		builder2.surfaceBuilder(CONFIGURED_SAVANNA_BEACH_SURFACE);
		builder2.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
		builder2.structureFeature(ConfiguredStructureFeatures.BURIED_TREASURE);
		builder2.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);

		builder2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addDefaultGrass(builder2);
		DefaultBiomeFeatures.addSavannaTrees(builder2);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.BEACH).depth(depth).scale(scale).temperature(temperature).downfall(downfall)
				.effects((new BiomeEffects.Builder()).waterColor(waterColor).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createGravellyBeach(boolean snowy) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();

		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
		builder2.surfaceBuilder(CONFIGURED_GRAVELLY_SURFACE);
		builder2.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
		builder2.structureFeature(ConfiguredStructureFeatures.BURIED_TREASURE);
		builder2.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);

		builder2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addDefaultGrass(builder2);
		float temp = snowy ? -0.5f : 0.25f;
		return (new Biome.Builder()).precipitation(snowy ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN).category(Biome.Category.BEACH).depth(0.02f).scale(0f).temperature(temp).downfall(0.8f)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(temp)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createDesertRiver(float depth, float scale) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addDesertMobs(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(CONFIGURED_SANDSTONE_RIVER_SURFACE);

		DefaultBiomeFeatures.addFossils(builder2);

		DefaultBiomeFeatures.addDefaultUndergroundStructures(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addDefaultFlowers(builder2);
		DefaultBiomeFeatures.addDefaultGrass(builder2);
		DefaultBiomeFeatures.addDesertDeadBushes(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		DefaultBiomeFeatures.addDesertFeatures(builder2);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.RIVER).depth(-0.75f).scale(0f).temperature(2.0F).downfall(0.0F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(2.0F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createExtremeHillsRiver() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addFarmAnimals(builder);
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 5, 4, 6));
		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.STONE);
		DefaultBiomeFeatures.addDefaultUndergroundStructures(builder2);
		builder2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addMountainTrees(builder2);

		DefaultBiomeFeatures.addDefaultFlowers(builder2);
		DefaultBiomeFeatures.addDefaultGrass(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addDefaultVegetation(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		DefaultBiomeFeatures.addEmeraldOre(builder2);
		DefaultBiomeFeatures.addInfestedStone(builder2);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.RIVER).depth(RebalancedWorldGen.normalRiverDepth).scale(0f).temperature(0.2F).downfall(0.3F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(0.2F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createSnowyGiantTreeTaiga(float depth, float scale, boolean spruce) {
	      SpawnSettings.Builder builder = new SpawnSettings.Builder();
	      DefaultBiomeFeatures.addFarmAnimals(builder);
	      builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4));
	      builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
	      builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
	      if (spruce) {
	         DefaultBiomeFeatures.addBatsAndMonsters(builder);
	      } else {
	         DefaultBiomeFeatures.addCaveMobs(builder);
	         DefaultBiomeFeatures.addMonsters(builder, 100, 25, 100);
	      }

	      GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GIANT_TREE_TAIGA);
	      DefaultBiomeFeatures.addDefaultUndergroundStructures(builder2);
	      builder2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL).structureFeature(ConfiguredStructureFeatures.IGLOO);
	      DefaultBiomeFeatures.addLandCarvers(builder2);
	      DefaultBiomeFeatures.addDefaultLakes(builder2);
	      DefaultBiomeFeatures.addAmethystGeodes(builder2);
	      DefaultBiomeFeatures.addDungeons(builder2);
	      DefaultBiomeFeatures.addMossyRocks(builder2);
	      DefaultBiomeFeatures.addLargeFerns(builder2);
	      DefaultBiomeFeatures.addMineables(builder2);
	      DefaultBiomeFeatures.addDefaultOres(builder2);
	      DefaultBiomeFeatures.addDefaultDisks(builder2);
	      builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, spruce ? ConfiguredFeatures.TREES_GIANT_SPRUCE : ConfiguredFeatures.TREES_GIANT);
	      DefaultBiomeFeatures.addDefaultFlowers(builder2);
	      DefaultBiomeFeatures.addGiantTaigaGrass(builder2);
	      DefaultBiomeFeatures.addDefaultMushrooms(builder2);
	      DefaultBiomeFeatures.addDefaultVegetation(builder2);
	      DefaultBiomeFeatures.addSprings(builder2);
	      DefaultBiomeFeatures.addSweetBerryBushesSnowy(builder2);
	      DefaultBiomeFeatures.addFrozenTopLayer(builder2);
	      return (new Biome.Builder()).precipitation(Biome.Precipitation.SNOW).category(Biome.Category.TAIGA).depth(depth).scale(scale).temperature(-0.5f).downfall(0.8F).effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(-0.5f)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	   }
	   
	   public static Biome createBirchRiver(float depth, float scale, boolean tallTrees) {
		      SpawnSettings.Builder builder = new SpawnSettings.Builder();
		      DefaultBiomeFeatures.addFarmAnimals(builder);
		      DefaultBiomeFeatures.addBatsAndMonsters(builder);
		      GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		      DefaultBiomeFeatures.addDefaultUndergroundStructures(builder2);
		      DefaultBiomeFeatures.addLandCarvers(builder2);
		      DefaultBiomeFeatures.addAmethystGeodes(builder2);
		      DefaultBiomeFeatures.addDungeons(builder2);
		      DefaultBiomeFeatures.addMineables(builder2);
		      DefaultBiomeFeatures.addDefaultOres(builder2);
		      DefaultBiomeFeatures.addDefaultDisks(builder2);
		      if (tallTrees) {
		         DefaultBiomeFeatures.addTallBirchTrees(builder2);
		      } else {
		         DefaultBiomeFeatures.addBirchTrees(builder2);
		      }

		      DefaultBiomeFeatures.addDefaultFlowers(builder2);
		      DefaultBiomeFeatures.addForestGrass(builder2);
		      DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		      DefaultBiomeFeatures.addDefaultVegetation(builder2);
		      DefaultBiomeFeatures.addSprings(builder2);
		      DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		      return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.RIVER).depth(RebalancedWorldGen.normalRiverDepth).scale(0F).temperature(0.6F).downfall(0.6F).effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(0.6F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
		   }

	   //MAKE THIS BIOME CATEGORY RIVER
	   
	public static Biome createJungleRiver() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addJungleMobs(builder);
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 40, 1, 2)).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 3)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 1, 1, 2));
		builder.playerSpawnFriendly();
		return DefaultBiomeInvoker.invokeCreateJungleFeatures(RebalancedWorldGen.normalRiverDepth, 0F, 0.9F, false, false, false, builder);
	}

	public static Biome createTropicalBeach(float depth, float scale, float temperature, float downfall, int waterColor, boolean snowy, boolean stony) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();

		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.TURTLE, 5, 2, 5));

		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(stony ? ConfiguredSurfaceBuilders.STONE : ConfiguredSurfaceBuilders.DESERT);

		builder2.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
		builder2.structureFeature(ConfiguredStructureFeatures.BURIED_TREASURE);
		builder2.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);

		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_RIVER);

		builder2.structureFeature(stony ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		DefaultBiomeFeatures.addAmethystGeodes(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addDefaultFlowers(builder2);
		DefaultBiomeFeatures.addDefaultGrass(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addDefaultVegetation(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.BEACH).depth(0.0F).scale(0.025F).temperature(0.6F).downfall(0.4F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(0.8F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createTaigaRiver(boolean frozen) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addFarmAnimals(builder);
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4)).spawn(SpawnGroup.CREATURE,
				new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 2, 6));
		builder.playerSpawnFriendly();

		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(CONFIGURED_GRAVELLY_SURFACE);

		DefaultBiomeFeatures.addDefaultUndergroundStructures(builder2);
		builder2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		DefaultBiomeFeatures.addAmethystGeodes(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addLargeFerns(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addTaigaTrees(builder2);
		DefaultBiomeFeatures.addDefaultFlowers(builder2);
		DefaultBiomeFeatures.addTaigaGrass(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addDefaultVegetation(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		if (frozen) {
			DefaultBiomeFeatures.addSweetBerryBushesSnowy(builder2);
		} else {
			DefaultBiomeFeatures.addSweetBerryBushes(builder2);
		}
		float temp = frozen ? -0.5F : 0.25F;
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		return (new Biome.Builder()).precipitation(frozen ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN).category(Biome.Category.RIVER).depth(RebalancedWorldGen.normalRiverDepth).scale(0F).temperature(temp).downfall(frozen ? 0.4F : 0.8F)
				.effects((new BiomeEffects.Builder()).waterColor(frozen ? 4020182 : 4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(temp)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}
}
