package nl.tettelaar.rebalanced.gen.biomelayers;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum SpecialBiomesLayer implements RandomnessSourceLayer {
	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource context, int x, int y, int value) {
		
		PerlinNoiseSampler samp = context.getNoiseSampler();

		double noise = samp.sample(((double) x / 24), 0, ((double) y / 24));
		
		if (noise > 0.55) {
			return 1;
		} else if (noise < -0.2) {
			return 2;
		} else if (noise < 0.1 && noise > -0.1) {
			return 3;
		} else if (noise > 0.15 && noise < 0.2) {
			return 4;
		}
		return 0;
	}


}