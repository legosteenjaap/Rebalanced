package nl.tettelaar.rebalanced.init.worldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.gen.BiomeCreator;

public class SurfaceBuilders {

	private final static String modid = Rebalanced.modid;
	
	public static void init() {
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
	
}
