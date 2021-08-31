package nl.tettelaar.rebalanced.init.worldgen;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.gen.BiomeCreator;
import nl.tettelaar.rebalanced.gen.surfacebuilders.GravellySurfaceBuilder;
import nl.tettelaar.rebalanced.gen.surfacebuilders.HeightDependSurfaceBuilder;
import nl.tettelaar.rebalanced.gen.surfacebuilders.SavannaBeachSurfaceBuilder;
import nl.tettelaar.rebalanced.gen.surfacebuilders.SavannaPlateauSurfaceBuilder;

public class SurfaceBuilders {

	private final static String modid = Rebalanced.modid;

	//SURFACE BUILDERS
	public static SavannaBeachSurfaceBuilder SAVANNA_BEACH_SURFACE = new SavannaBeachSurfaceBuilder(TernarySurfaceConfig.CODEC);
	public static SavannaPlateauSurfaceBuilder SAVANNA_PLATEAU_SURFACE = new SavannaPlateauSurfaceBuilder(TernarySurfaceConfig.CODEC);
	public static GravellySurfaceBuilder GRAVELLY_SURFACE = new GravellySurfaceBuilder(TernarySurfaceConfig.CODEC);
	public static HeightDependSurfaceBuilder STONE_SHORE_SURFACE = new HeightDependSurfaceBuilder(TernarySurfaceConfig.CODEC, 96, false);
	public static HeightDependSurfaceBuilder SNOWY_STONE_SHORE_SURFACE = new HeightDependSurfaceBuilder(TernarySurfaceConfig.CODEC, 90, true);

	//SURFACE BUILDER CONFIGS
	public static final TernarySurfaceConfig SAVANNA_BEACH_CONFIG = new TernarySurfaceConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
	public static final TernarySurfaceConfig SAND_STONE_CONFIG = new TernarySurfaceConfig(Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState());
	public static final TernarySurfaceConfig STONE_SAND_UNDERWATER_CONFIG = new TernarySurfaceConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
	public static final TernarySurfaceConfig NORMAL_UNDERWATER_CONFIG = new TernarySurfaceConfig(Blocks.GRAVEL.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.GRAVEL.getDefaultState());
	public static final TernarySurfaceConfig STONE_SHORE_CONFIG = new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());
	public static final TernarySurfaceConfig SNOWY_STONE_SHORE_CONFIG = new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), Blocks.STONE.getDefaultState());

	//CONFIGURED SURFACE BUILDERS
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SAVANNA_BEACH_SURFACE = SAVANNA_BEACH_SURFACE.withConfig(SAVANNA_BEACH_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SAVANNA_PLATEAU_SURFACE = SAVANNA_PLATEAU_SURFACE.withConfig(SurfaceBuilder.GRASS_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_GRAVELLY_SURFACE = GRAVELLY_SURFACE.withConfig(SNOWY_STONE_SHORE_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SANDSTONE_RIVER_SURFACE = SurfaceBuilder.DEFAULT.withConfig(SAND_STONE_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_OCEAN_SURFACE = SurfaceBuilder.DEFAULT.withConfig(NORMAL_UNDERWATER_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SAND_OCEAN_SURFACE = SurfaceBuilder.DEFAULT.withConfig(STONE_SAND_UNDERWATER_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_FROZEN_OCEAN_SURFACE = SurfaceBuilder.FROZEN_OCEAN.withConfig(NORMAL_UNDERWATER_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_STONE_SHORE_SURFACE = STONE_SHORE_SURFACE.withConfig(STONE_SHORE_CONFIG);
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SNOWY_STONE_SHORE_SURFACE = SNOWY_STONE_SHORE_SURFACE.withConfig(STONE_SHORE_CONFIG);


	public static void init() {
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "savanna_beach"), SAVANNA_BEACH_SURFACE);
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "savanna_plateau"), SAVANNA_PLATEAU_SURFACE);
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "gravelly"), GRAVELLY_SURFACE);
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "stone_shore"), STONE_SHORE_SURFACE);
		Registry.register(Registry.SURFACE_BUILDER, new Identifier(modid, "snowy_stone_shore"), SNOWY_STONE_SHORE_SURFACE);

		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "savanna_beach"), CONFIGURED_SAVANNA_BEACH_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "savanna_plateau"), CONFIGURED_SAVANNA_PLATEAU_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "gravelly"), CONFIGURED_GRAVELLY_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "sandstone_river"), CONFIGURED_SANDSTONE_RIVER_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "ocean"), CONFIGURED_OCEAN_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "sand_ocean"), CONFIGURED_SAND_OCEAN_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "frozen_ocean"), CONFIGURED_FROZEN_OCEAN_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "stone_shore"), CONFIGURED_STONE_SHORE_SURFACE);
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(modid, "snowy_stone_shore"), CONFIGURED_SNOWY_STONE_SHORE_SURFACE);
	}
}
