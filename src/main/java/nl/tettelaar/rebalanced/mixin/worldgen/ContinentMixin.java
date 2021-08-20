package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.ContinentLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;

@Mixin(ContinentLayer.class)
public class ContinentMixin {

	@Overwrite
	public int sample(LayerRandomnessSource context, int x, int y) {
		
		PerlinNoiseSampler samp = context.getNoiseSampler();

		double noise = samp.sample((double) x / 16, 0, (double) y / 16);

		if (x <= RebalancedWorldGen.mainContinentSize && x >=-RebalancedWorldGen.mainContinentSize && y <= RebalancedWorldGen.mainContinentSize && y >= -RebalancedWorldGen.mainContinentSize) {
			noise += 1 - (MathHelper.abs(x) + MathHelper.abs(y)) / (2 * (RebalancedWorldGen.mainContinentSize));
		}
		
		if (x == 0 && y == 0 && noise < 0.15) {
			return BiomeIds.PLAINS;
		}
		
		if (noise > 0.15) {
			return BiomeIds.PLAINS;
		} else if (noise < -0.4) {
			return BiomeIds.DEEP_OCEAN;
		} else {
			return BiomeIds.OCEAN;
		}
		
	}
}
