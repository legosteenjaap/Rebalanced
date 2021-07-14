package nl.tettelaar.rebalanced.gen.biomelayers;

import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.type.DiagonalCrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum RemoveSmallIslandLayer implements DiagonalCrossSamplingLayer {
	INSTANCE;
	
	public int sample(LayerRandomnessSource context, int sw, int se, int ne, int nw, int center) {
		return isOcean(sw) && isOcean(se) && isOcean(ne) && isOcean(nw) ? nw : center;
	}

	private static boolean isOcean(int id) {
		return id == BiomeIds.WARM_OCEAN || id == BiomeIds.LUKEWARM_OCEAN || id == 0 || id == BiomeIds.COLD_OCEAN
				|| id == BiomeIds.FROZEN_OCEAN || id == BiomeIds.DEEP_WARM_OCEAN || id == BiomeIds.DEEP_LUKEWARM_OCEAN
				|| id == BiomeIds.DEEP_OCEAN || id == BiomeIds.DEEP_COLD_OCEAN || id == BiomeIds.DEEP_FROZEN_OCEAN;
	}

}
