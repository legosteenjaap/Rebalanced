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
		case BiomeIds.SNOWY_TUNDRA:
			switch (specialBiome) {
			case 3:
				return BiomeIds.SNOWY_MOUNTAINS;
			}
			break;
		case BiomeIds.DESERT:
			if (specialBiome == 2) return context.nextInt(2) == 0 ? BiomeIds.WOODED_BADLANDS_PLATEAU : BiomeIds.BADLANDS_PLATEAU;
			break;
		case BiomeIds.SAVANNA:
			if (specialBiome == 1) return context.nextInt(3) == 0 ? BiomeIds.SHATTERED_SAVANNA : BiomeIds.SHATTERED_SAVANNA_PLATEAU;
			break;
		case BiomeIds.JUNGLE:
			if (specialBiome == 2) return BiomeIds.BAMBOO_JUNGLE;
			break;
		case BiomeIds.PLAINS:
			if (specialBiome == 2) {
				return BiomeIds.SWAMP;	
			}
			break;
		case BiomeIds.TAIGA:
			switch (specialBiome) {
			case 1:
				return BiomeIds.GIANT_SPRUCE_TAIGA;
			case 2:
				return BiomeIds.GIANT_TREE_TAIGA;
			case 3:
				return BiomeIds.MOUNTAINS;
			}
			break;
		case BiomeIds.SNOWY_TAIGA:
			switch (specialBiome) {
			case 1:
				return BuiltinRegistries.BIOME.getRawId(Biomes.SNOWY_GIANT_SPRUCE_TAIGA);
			case 2:
				return BuiltinRegistries.BIOME.getRawId(Biomes.SNOWY_GIANT_TREE_TAIGA);
			case 3:
				return BiomeIds.SNOWY_MOUNTAINS;
			}
			break;
		}
		
		switch (continent) {
		case BiomeIds.DESERT:
			if (context.nextInt(75) == 0) return BiomeIds.SAVANNA;
			break;
		case BiomeIds.SNOWY_TUNDRA:
			if (context.nextInt(100) == 0) return BiomeIds.ICE_SPIKES;
		}
		
		return continent;
		
	}
}
