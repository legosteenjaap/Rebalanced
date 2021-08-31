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
import nl.tettelaar.rebalanced.gen.surfacebuilders.SavannaPlateauSurfaceBuilder;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;
import nl.tettelaar.rebalanced.init.worldgen.SurfaceBuilders;
import nl.tettelaar.rebalanced.mixin.worldgen.DefaultBiomeInvoker;

public class BiomeCreator {

	public static Biome createJungleBeach() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();

		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.TURTLE, 5, 2, 5));

		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder())/*.surfaceBuilder(ConfiguredSurfaceBuilders.DESERT)*/;

		//IS TEMPORARY WILL SWITCH WHEN I FIND A MOD WITH GOOD PALM TREES
		builder2.surfaceBuilder(SurfaceBuilders.CONFIGURED_SAVANNA_BEACH_SURFACE);

		builder2.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
		builder2.structureFeature(ConfiguredStructureFeatures.BURIED_TREASURE);
		builder2.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);
		builder2.structureFeature(ConfiguredStructureFeatures.JUNGLE_PYRAMID);

		DefaultBiomeFeatures.addJungleTrees(builder2);

		builder2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_JUNGLE);
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
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.BEACH).depth(RebalancedWorldGen.beachDepth).scale(RebalancedWorldGen.riverScale).temperature(0.95F).downfall(0.9F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(0.8F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createSavannaBeach() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.TURTLE, 3, 2, 3));

		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
		builder2.surfaceBuilder(SurfaceBuilders.CONFIGURED_SAVANNA_BEACH_SURFACE);
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
		return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.BEACH).depth(RebalancedWorldGen.beachDepth).scale(RebalancedWorldGen.beachScale).temperature(1.2f).downfall(0f)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(1.2f)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createGravellyBeach(boolean snowy) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();

		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
		builder2.surfaceBuilder(SurfaceBuilders.CONFIGURED_GRAVELLY_SURFACE);
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
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		float temp = snowy ? -0.5f : 0.25f;
		return (new Biome.Builder()).precipitation(snowy ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN).category(Biome.Category.BEACH).depth(RebalancedWorldGen.beachDepth).scale(RebalancedWorldGen.beachScale).temperature(temp).downfall(0.8f)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(temp)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createDesertRiver() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addDesertMobs(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(SurfaceBuilders.CONFIGURED_SANDSTONE_RIVER_SURFACE);

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
		return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.RIVER).depth(RebalancedWorldGen.dryRiverDepth).scale(RebalancedWorldGen.riverScale).temperature(2.0F).downfall(0.0F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(2.0F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createExtremeHillsRiver(boolean frozen) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4)).spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5));;
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
		if (!frozen)builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_RIVER);
		float temp = frozen ? -0.5F : 0.2F;
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.RIVER).depth(RebalancedWorldGen.normalRiverDepth).scale(RebalancedWorldGen.riverScale).temperature(temp).downfall(0.3F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(temp)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createBirchForestRiver() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4)).spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5));;
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
		DefaultBiomeFeatures.addBirchTrees(builder2);
		DefaultBiomeFeatures.addDefaultFlowers(builder2);
		DefaultBiomeFeatures.addForestGrass(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addDefaultVegetation(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_RIVER);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.RIVER).depth(RebalancedWorldGen.normalRiverDepth).scale(RebalancedWorldGen.riverScale).temperature(0.6F).downfall(0.6F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(0.6F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createJungleRiver() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 1, 1, 4));
		DefaultBiomeFeatures.addJungleMobs(builder);
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 40, 1, 2)).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 3)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 1, 1, 2));
		builder.playerSpawnFriendly();
		return DefaultBiomeInvoker.invokeCreateJungleFeatures(RebalancedWorldGen.normalRiverDepth, 0F, 0.9F, false, false, false, builder);
	}



	public static Biome createTaigaRiver(boolean frozen) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4)).spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5));;
		DefaultBiomeFeatures.addFarmAnimals(builder);
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4)).spawn(SpawnGroup.CREATURE,
				new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 2, 6));
		builder.playerSpawnFriendly();

		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(SurfaceBuilders.CONFIGURED_GRAVELLY_SURFACE);

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
			builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_RIVER);
		}
		float temp = frozen ? -0.5F : 0.25F;
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		return (new Biome.Builder()).precipitation(frozen ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN).category(Biome.Category.RIVER).depth(RebalancedWorldGen.normalRiverDepth).scale(RebalancedWorldGen.riverScale).temperature(temp).downfall(frozen ? 0.4F : 0.8F)
				.effects((new BiomeEffects.Builder()).waterColor(frozen ? 4020182 : 4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(temp)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build())
				.build();
	}

	public static Biome createDarkForestRiver() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4)).spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5));;
		DefaultBiomeFeatures.addFarmAnimals(builder);
		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		builder2.structureFeature(ConfiguredStructureFeatures.MANSION);
		DefaultBiomeFeatures.addDefaultUndergroundStructures(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		DefaultBiomeFeatures.addAmethystGeodes(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.DARK_FOREST_VEGETATION_BROWN);
		DefaultBiomeFeatures.addForestFlowers(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addDefaultFlowers(builder2);
		DefaultBiomeFeatures.addForestGrass(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addDefaultVegetation(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_RIVER);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.RIVER).depth(RebalancedWorldGen.normalRiverDepth).scale(RebalancedWorldGen.riverScale).temperature(0.7F).downfall(0.8F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(0.7F)).grassColorModifier(BiomeEffects.GrassColorModifier.DARK_FOREST).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
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
		return (new Biome.Builder()).precipitation(Biome.Precipitation.SNOW).category(Biome.Category.TAIGA).depth(depth).scale(scale).temperature(-0.5f).downfall(0.8F)
				.effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(DefaultBiomeInvoker.invokeGetSkyColor(-0.5f)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

}
