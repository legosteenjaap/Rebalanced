package nl.tettelaar.rebalanced.gen.biomelayers;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import nl.tettelaar.rebalanced.init.worldgen.Biomes;

public enum ApplySpecialBiomeLayer implements MergingLayer, IdentityCoordinateTransformer {
	INSTANCE;

	public int sample(LayerRandomnessSource context, LayerSampler continentSamp, LayerSampler specialBiomeSamp, int x, int z) {
		int continent = continentSamp.sample(this.transformX(x), this.transformZ(z));
		int specialBiome = specialBiomeSamp.sample(this.transformX(x), this.transformZ(z));
		
		
		
		switch (continent) {
		case BiomeIds.SAVANNA:
			if (specialBiome == 1) {
				return BiomeIds.JUNGLE;
			}
			break;
		case BiomeIds.DESERT:
			if (specialBiome == 2) {
				return context.nextInt(2) == 0 ? BiomeIds.WOODED_BADLANDS_PLATEAU : BiomeIds.BADLANDS_PLATEAU;
			}
			break;
		case BiomeIds.PLAINS:
			if (specialBiome == 2) {
				return BiomeIds.SWAMP;	
			}
			break;
		case BiomeIds.TAIGA:
			if (specialBiome == 1) {
				return BiomeIds.GIANT_SPRUCE_TAIGA;
			} else if (specialBiome == 2) {
				return BiomeIds.GIANT_TREE_TAIGA;
			}
			break;
		case BiomeIds.SNOWY_TAIGA:
			if (specialBiome == 1) {
				return BuiltinRegistries.BIOME.getRawId(Biomes.SNOWY_GIANT_SPRUCE_TAIGA);
			} else if (specialBiome == 2) {
				return BuiltinRegistries.BIOME.getRawId(Biomes.SNOWY_GIANT_TREE_TAIGA);
			}
			break;
		}
		
		switch (continent) {
		case BiomeIds.DESERT:
			if (context.nextInt(75) == 0) return BiomeIds.SAVANNA;
			break;
		}
		
		return continent;
		
	}
}
