package nl.tettelaar.rebalanced.gen.biomelayers;

import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum ApplyClimateLayer implements MergingLayer, IdentityCoordinateTransformer {
	INSTANCE;

	public int sample(LayerRandomnessSource context, LayerSampler continentSamp, LayerSampler climateSamp, int x, int z) {
		int continent = continentSamp.sample(this.transformX(x), this.transformZ(z));
		int climate = climateSamp.sample(this.transformX(x), this.transformZ(z));
		if (continent == BiomeIds.PLAINS) {
			return climate;
		} else {
			switch (climate) {
			case BiomeIds.DESERT:
				return BiomeIds.WARM_OCEAN;
			case BiomeIds.SAVANNA:
				return BiomeIds.LUKEWARM_OCEAN;
			case BiomeIds.PLAINS:
				return BiomeIds.OCEAN;
			case BiomeIds.TAIGA:
			case BiomeIds.SNOWY_TAIGA:
				return BiomeIds.COLD_OCEAN;
			case BiomeIds.SNOWY_TUNDRA:
				return BiomeIds.FROZEN_OCEAN;
			default:
				return BiomeIds.OCEAN;

			}
		}
	}

}