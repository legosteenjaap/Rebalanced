package nl.tettelaar.rebalanced.gen;

import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum ClimateLayer implements RandomnessSourceLayer {
	INSTANCE;
	
	private static double warmBorder = 0.4;
	private static double lukewarmBorder = 0.25;
	private static double coldBorder = -0.15;
	private static double frozenBorder = -0.25;
	private static double treeBorder = -0.35;
	
	public int sample(LayerRandomnessSource context, int x, int y, int value) {

		PerlinNoiseSampler samp = context.getNoiseSampler();

		double noise = samp.sample(((double) x / 40), 0, ((double) y / 40));

		
		
		if (value == BiomeIds.PLAINS) {
			if (noise > lukewarmBorder && noise <= warmBorder) {
				return BiomeIds.SAVANNA;
			} else if (noise > warmBorder) {
				return BiomeIds.DESERT;
			} else if (noise < coldBorder && noise >= frozenBorder) {
				return BiomeIds.TAIGA;
			} else if (noise < frozenBorder && noise >= treeBorder) {
				return BiomeIds.SNOWY_TAIGA;
			} else if (noise < treeBorder) {
				return BiomeIds.SNOWY_TUNDRA;
			}
		} else {
			if (noise > lukewarmBorder && noise <= warmBorder) {
				return BiomeIds.LUKEWARM_OCEAN;
			} else if (noise > warmBorder) {
				return BiomeIds.WARM_OCEAN;
			} else if (noise < coldBorder && noise >= frozenBorder) {
				return BiomeIds.COLD_OCEAN;
			} else if (noise < frozenBorder) {
				return BiomeIds.FROZEN_OCEAN;
			}
		}
		return value;
		// return context.nextInt(10) == 0 ? BiomeIds.PLAINS : BiomeIds.OCEAN;

		/*
		 * int xDiv = x % 4; int yDiv = y % 4;
		 * 
		 * if (xDiv == 0 && yDiv == 0) { if (x == 0 && y == 0) { return BiomeIds.PLAINS;
		 * } switch (context.nextInt(3)) { case 0: return BiomeIds.PLAINS; case 1:
		 * return BiomeIds.SNOWY_TUNDRA; case 2: return BiomeIds.DESERT; default: throw
		 * new AssertionError(); } } else { return BiomeIds.OCEAN; }
		 */

		// return BiomeIds.OCEAN;
	}
}
