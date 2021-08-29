package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.AddBaseBiomesLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(AddBaseBiomesLayer.class)
public class AddBaseBiomeLayerMixin {

	@Shadow
	private static final int[] OLD_GROUP_1 = new int[] { 2, 4, 3, 6, 1, 5 };
	@Shadow
	private static final int[] DRY_BIOMES = new int[] { 2, 2, 2, 35, 35, 1 };
	@Shadow
	private static final int[] TEMPERATE_BIOMES = new int[] { 4, 29, 3, 1, 27, 6 };
	@Shadow
	private static final int[] COOL_BIOMES = new int[] { 4, 3, 5, 1 };
	@Shadow
	private static final int[] SNOWY_BIOMES = new int[] { 12, 12, 12, 30 };
	@Shadow
	private int[] chosenGroup1;

	@Inject(method = "sample", at = @At("HEAD"), cancellable = true)
	public void sample(LayerRandomnessSource context, int value, CallbackInfoReturnable<Integer> cir) {
		if (isOcean(value) || value == BiomeIds.MUSHROOM_FIELDS || value == BiomeIds.JUNGLE) {
			cir.setReturnValue(value);
		} else {
			switch (value) {
			case BiomeIds.PLAINS:
				cir.setReturnValue(getTemperateBiome(context));
				break;
			case BiomeIds.DESERT:
				int dBiome = this.chosenGroup1[context.nextInt(this.chosenGroup1.length)];
				if (dBiome != BiomeIds.SAVANNA && dBiome != BiomeIds.PLAINS) {
					cir.setReturnValue(dBiome);
				} else {
					cir.setReturnValue(BiomeIds.DESERT);
				}
				break;
			case BiomeIds.SNOWY_TUNDRA:
				int sBiome = SNOWY_BIOMES[context.nextInt(SNOWY_BIOMES.length)];
				if (sBiome != BiomeIds.ICE_SPIKES && sBiome != BiomeIds.SNOWY_MOUNTAINS) {
					cir.setReturnValue(sBiome);
				} else {
					cir.setReturnValue(BiomeIds.SNOWY_TUNDRA);
				}
				break;
			case BiomeIds.TAIGA:
				int tBiome = COOL_BIOMES[context.nextInt(COOL_BIOMES.length)];
				if (tBiome != BiomeIds.FOREST && tBiome != BiomeIds.PLAINS && tBiome != BiomeIds.MOUNTAINS) {
					cir.setReturnValue(tBiome);
				} else {
					cir.setReturnValue(BiomeIds.TAIGA);
				}
				break;
			
			default:
				cir.setReturnValue(value);
			}
		}
	}
	
	private static int getTemperateBiome(LayerRandomnessSource context) {
		int pBiome = TEMPERATE_BIOMES[context.nextInt(TEMPERATE_BIOMES.length)];
		if ((pBiome != BiomeIds.MOUNTAINS && pBiome != BiomeIds.TAIGA && pBiome != BiomeIds.SWAMP && (pBiome != BiomeIds.PLAINS || context.nextInt(20) == 0))) {
			return pBiome;
		} else {
			return getTemperateBiome(context);
		}
	}

	private static boolean isOcean(int id) {
		return id == BiomeIds.WARM_OCEAN || id == BiomeIds.LUKEWARM_OCEAN || id == 0 || id == BiomeIds.COLD_OCEAN
				|| id == BiomeIds.FROZEN_OCEAN || id == BiomeIds.DEEP_WARM_OCEAN || id == BiomeIds.DEEP_LUKEWARM_OCEAN
				|| id == BiomeIds.DEEP_OCEAN || id == BiomeIds.DEEP_COLD_OCEAN || id == BiomeIds.DEEP_FROZEN_OCEAN;
	}

}
