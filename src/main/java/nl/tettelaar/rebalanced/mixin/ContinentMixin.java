package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.ContinentLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(ContinentLayer.class)
public class ContinentMixin {

	private static int mainContinentSize = 3;

	@Overwrite
	public int sample(LayerRandomnessSource context, int x, int y) {
		
		PerlinNoiseSampler samp = context.getNoiseSampler();

		double noise = samp.sample((double) x / 4, 0, (double) y / 4);

		if (x <= mainContinentSize && x >=-mainContinentSize && y <= mainContinentSize && y >= -mainContinentSize) {
			noise += (0.3 - (0.03 * (MathHelper.abs(x) + MathHelper.abs(y))));
		}
		
		if (noise > 0.25) {
			return BiomeIds.PLAINS;
		} else {
			return BiomeIds.OCEAN;
		}
		
		
		/*if (context.nextInt(10) == 0 || (x == 0 && y == 0)) {
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
			} else {
				return BiomeIds.PLAINS;
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
			} else {
				return BiomeIds.OCEAN;
			}
		}*/
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
