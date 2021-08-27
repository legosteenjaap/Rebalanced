package nl.tettelaar.rebalanced.gen.biomelayers;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;

public enum ClimateLayer implements RandomnessSourceLayer {
	INSTANCE;
	
	private final static double warmBorder = 0.6;
	private final static double desertEndBorder = 0.55;
	private final static double desertBeginBorder = 0.4;
	private final static double lukewarmBorder = 0.3;
	private final static double coldBorder = -0.1;
	private final static double frozenBorder = -0.2;
	private final static double treeBorder = -0.35;
	
	public int sample(LayerRandomnessSource context, int x, int y, int value) {

		PerlinNoiseSampler samp = context.getNoiseSampler();

		double noise = samp.sample(((double) x / 50), 0, ((double) y / 50));
		
		if (x == 0 && y == 0) {
			if (noise > desertBeginBorder && noise <= desertEndBorder) {
				return BiomeIds.SAVANNA;
			} else if (noise < treeBorder) {
				return BiomeIds.SNOWY_TAIGA;
			}
		}
		
		if (noise > lukewarmBorder && noise <= desertBeginBorder) {
			return BiomeIds.SAVANNA;
		} else if (noise > desertBeginBorder && noise <= desertEndBorder) {
			return BiomeIds.DESERT;
		} else if (noise > desertEndBorder && noise <= warmBorder) {
			return BiomeIds.SAVANNA;
		} else if (noise > warmBorder){ 
			return BiomeIds.JUNGLE;
		} else if (noise < coldBorder && noise >= frozenBorder) {
			return BiomeIds.TAIGA;
		} else if (noise < frozenBorder && noise >= treeBorder) {
			return BiomeIds.SNOWY_TAIGA;
		} else if (noise < treeBorder) {
			return BiomeIds.SNOWY_TUNDRA;
		} else {
			return BiomeIds.PLAINS;
		}
	}
}
