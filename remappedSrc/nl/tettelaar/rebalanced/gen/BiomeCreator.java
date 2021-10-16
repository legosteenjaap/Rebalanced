package nl.tettelaar.rebalanced.gen;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import nl.tettelaar.rebalanced.gen.surfacebuilders.GravellySurfaceBuilder;
import nl.tettelaar.rebalanced.gen.surfacebuilders.SavannaBeachSurfaceBuilder;
import nl.tettelaar.rebalanced.gen.surfacebuilders.HeightDependSurfaceBuilder;
import nl.tettelaar.rebalanced.mixin.DefaultBiomeInvoker;

public class BiomeCreator {

	public static SavannaBeachSurfaceBuilder SAVANNA_BEACH_SURFACE = new SavannaBeachSurfaceBuilder(
			TernarySurfaceConfig.CODEC);
	public static GravellySurfaceBuilder GRAVELLY_SURFACE = new GravellySurfaceBuilder(TernarySurfaceConfig.CODEC);

	public static HeightDependSurfaceBuilder STONE_SHORE_SURFACE = new HeightDependSurfaceBuilder(
			TernarySurfaceConfig.CODEC, 145);

	public static HeightDependSurfaceBuilder ELEVATED_EXTREME_HILLS_SURFACE = new HeightDependSurfaceBuilder(
			TernarySurfaceConfig.CODEC, 128);

	private static final TernarySurfaceConfig SAVANNA_BEACH_CONFIG = new TernarySurfaceConfig(
			Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
	private static final TernarySurfaceConfig GRAVELLY_CONFIG = new TernarySurfaceConfig(Blocks.SAND.getDefaultState(),
			Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
	public static final TernarySurfaceConfig SAND_STONE_CONFIG = new TernarySurfaceConfig(
			Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState());

	public static final TernarySurfaceConfig STONE_SAND_UNDERWATER_CONFIG = new TernarySurfaceConfig(
			Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.SAND.getDefaultState());
	public static final TernarySurfaceConfig NORMAL_UNDERWATER_CONFIG = new TernarySurfaceConfig(
			Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.GRAVEL.getDefaultState());

	public static final TernarySurfaceConfig STONE_SHORE_CONFIG = new TernarySurfaceConfig(
			Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());

	public static final TernarySurfaceConfig ELEVATED_EXTREME_HILLS_CONFIG = new TernarySurfaceConfig(
			Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());

	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SAVANNA_BEACH_SURFACE = SAVANNA_BEACH_SURFACE
			.withConfig(SAVANNA_BEACH_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_GRAVELLY_SURFACE = GRAVELLY_SURFACE
			.withConfig(GRAVELLY_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SANDSTONE_RIVER_SURFACE = SurfaceBuilder.DEFAULT
			.withConfig(SAND_STONE_CONFIG);

	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_STONE_SHORE_SURFACE = STONE_SHORE_SURFACE
			.withConfig(STONE_SHORE_CONFIG);

	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_ELEVATED_EXTREME_HILLS_SURFACE = ELEVATED_EXTREME_HILLS_SURFACE
			.withConfig(ELEVATED_EXTREME_HILLS_CONFIG);
	
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_OCEAN_SURFACE = SurfaceBuilder.DEFAULT.withConfig(NORMAL_UNDERWATER_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SAND_OCEAN_SURFACE = SurfaceBuilder.DEFAULT.withConfig(STONE_SAND_UNDERWATER_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_FROZEN_OCEAN_SURFACE = SurfaceBuilder.FROZEN_OCEAN.withConfig(NORMAL_UNDERWATER_CONFIG);

	public static Biome createSavannaBeach(float depth, float scale, float temperature, float downfall,
			int waterColor) {
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
		return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.SAVANNA)
				.depth(depth).scale(scale).temperature(temperature).downfall(downfall)
				.effects((new BiomeEffects.Builder()).waterColor(waterColor).waterFogColor(329011).fogColor(12638463)
						.skyColor(DefaultBiomeInvoker.invokeGetSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE)
						.build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createGravellyBeach(float depth, float scale, float temperature, float downfall,
			int waterColor) {
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
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.TAIGA).depth(depth)
				.scale(scale).temperature(temperature).downfall(0.8f)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463)
						.skyColor(DefaultBiomeInvoker.invokeGetSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE)
						.build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createDesertRiver(float depth, float scale) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addDesertMobs(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder())
				.surfaceBuilder(CONFIGURED_SANDSTONE_RIVER_SURFACE);

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
		return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.RIVER)
				.depth(-0.75f).scale(0f).temperature(2.0F).downfall(0.0F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463)
						.skyColor(DefaultBiomeInvoker.invokeGetSkyColor(2.0F)).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createExtremeHillsRiver() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addFarmAnimals(builder);
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 5, 4, 6));
		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder())
				.surfaceBuilder(ConfiguredSurfaceBuilders.STONE);
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
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.EXTREME_HILLS)
				.depth(-1f).scale(0.1f).temperature(0.2F).downfall(0.3F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463)
						.skyColor(DefaultBiomeInvoker.invokeGetSkyColor(0.2F)).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

}
