package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.AddBaseBiomesLayer;
import net.minecraft.world.biome.layer.BiomeLayers;
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
		
		
		//cir.setReturnValue(value);
		
		if (isOcean(value) || value == BiomeIds.MUSHROOM_FIELDS) {
			cir.setReturnValue(value);
		} else {
			switch (value) {
			case BiomeIds.PLAINS:
				int pBiome = TEMPERATE_BIOMES[context.nextInt(TEMPERATE_BIOMES.length)];
				if (pBiome != BiomeIds.MOUNTAINS && pBiome != BiomeIds.TAIGA) {
					cir.setReturnValue(pBiome);
				} else {
					cir.setReturnValue(context.nextInt(2) == 0 ? BiomeIds.FOREST : BiomeIds.PLAINS);
				}
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
				cir.setReturnValue(SNOWY_BIOMES[context.nextInt(SNOWY_BIOMES.length)]);
				break;
			case BiomeIds.TAIGA:
				int tBiome = COOL_BIOMES[context.nextInt(COOL_BIOMES.length)];
				if (tBiome != BiomeIds.FOREST && tBiome != BiomeIds.PLAINS) {
					cir.setReturnValue(tBiome);
				} else {
					cir.setReturnValue(BiomeIds.TAIGA);
				}
				break;
			default:
				cir.setReturnValue(value);
			}
		}
		
		
		/*int i = (value & 3840) >> 8;
		int j = value & -3841;
		if (!isOcean(value) && value != BiomeIds.MUSHROOM_FIELDS) {
			switch (j) {
			case 1:
				if (i > 0) {
					cir.setReturnValue(context.nextInt(3) == 0 ? 39 : 38);
					break;
				}

				cir.setReturnValue(this.chosenGroup1[context.nextInt(this.chosenGroup1.length)]);
				break;
			case 2:
				if (i > 0) {
					cir.setReturnValue(21);
					break;
				}

				cir.setReturnValue(TEMPERATE_BIOMES[context.nextInt(TEMPERATE_BIOMES.length)]);
				break;
			case 3:
				if (i > 0) {
					cir.setReturnValue(32);
					break;
				}

				cir.setReturnValue(COOL_BIOMES[context.nextInt(COOL_BIOMES.length)]);
				break;
			case 4:
				cir.setReturnValue(SNOWY_BIOMES[context.nextInt(SNOWY_BIOMES.length)]);
				break;
			default:
				cir.setReturnValue(14);
			}
		} else {
			cir.setReturnValue(value);
		}*/

	}

	private static boolean isOcean(int id) {
		return id == BiomeIds.WARM_OCEAN || id == BiomeIds.LUKEWARM_OCEAN || id == 0 || id == BiomeIds.COLD_OCEAN
				|| id == BiomeIds.FROZEN_OCEAN || id == BiomeIds.DEEP_WARM_OCEAN || id == BiomeIds.DEEP_LUKEWARM_OCEAN
				|| id == BiomeIds.DEEP_OCEAN || id == BiomeIds.DEEP_COLD_OCEAN || id == BiomeIds.DEEP_FROZEN_OCEAN;
	}

}
