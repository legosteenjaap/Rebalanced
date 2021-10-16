package nl.tettelaar.rebalanced.gen.biomelayers;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum ClimateLayer implements RandomnessSourceLayer {
	INSTANCE;
	
	/*private final static double warmBorder = 0.6;
	private final static double desertEndBorder = 0.55;
	private final static double desertBeginBorder = 0.4;
	private final static double lukewarmBorder = 0.3;
	private final static double swampAndExtremeHillsBorder = 0.15;
	private final static double plainsBorder = 0.25;
	private final static double coldBorder = -0.1;
	private final static double frozenBorder = -0.2;
	private final static double treeBorder = -0.35;*/

	private final static double warmBorder = 0.05;
	private final static double desertBeginBorder = 0.12;
	private final static double desertEndBorder = 0.22;
	private final static double lukewarmBorder = 0.28;
	private final static double plainsBorder = 0.3;
	private final static double normalForestBorder = 0.35;
	private final static double swampAndExtremeHillsBorder = 0.4;
	private final static double coldBorder = 0.47;
	private final static double frozenBorder = 0.51;
	private final static double treeBorder = 0.54;

	public int sample(LayerRandomnessSource context, int x, int y, int value) {

		PerlinNoiseSampler samp = context.getNoiseSampler();

		double noise = samp.sample(((double) x / 65), 0, ((double) y / 65));

		noise = Math.abs(noise);

		if (x == 0 && y == 0) {
			if (noise > desertBeginBorder && noise <= desertEndBorder) {
				return BiomeIds.SAVANNA;
			} else if (noise > treeBorder) {
				return BiomeIds.SNOWY_TAIGA;
			}
		}

		if (noise < warmBorder) {
			return BiomeIds.JUNGLE;
		} else if (noise < desertBeginBorder) {
			return BiomeIds.SAVANNA;
		} else if (noise < desertEndBorder) {
			return BiomeIds.DESERT;
		} else if (noise < lukewarmBorder) {
			return BiomeIds.SAVANNA;
		} else if (noise < plainsBorder) {
			return 2187;
		} else if (noise < normalForestBorder) {
			return 2718;
		} else if (noise < swampAndExtremeHillsBorder) {
			return BiomeIds.PLAINS;
		} else if (noise < coldBorder) {
			return BiomeIds.TAIGA;
		} else if (noise < frozenBorder) {
			return BiomeIds.SNOWY_TAIGA;
		} else {
			return BiomeIds.SNOWY_TUNDRA;
		}


		
		/*if (noise > swampAndExtremeHillsBorder && noise <= plainsBorder) {
			return 2718;
		} else if (noise > plainsBorder && noise <= lukewarmBorder) {
			return 2187;
		} else if (noise > lukewarmBorder && noise <= desertBeginBorder) {
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
		}*/
	}
}
