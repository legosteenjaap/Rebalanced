package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.gen.SimpleRandom;

@Mixin(VanillaLayeredBiomeSource.class)
public class MixinVanillaLayeredBiomeSource {

	@Shadow @Final private BiomeLayerSampler biomeSampler;

	@Shadow @Final private Registry<Biome> biomeRegistry;

	@Unique
	private PerlinNoiseSampler caveBiomeNoise;

	@Shadow
	@Final
	private long seed;
	@Shadow
	@Final
	private boolean legacyBiomeInitLayer;
	@Shadow
	@Final
	private boolean largeBiomes;
	private BiomeLayerSampler biomeSamp;
	
	@Inject(method = "<init>", at = @At("RETURN"))
	private void makeNoise(long seed, boolean legacyBiomeInitLayer, boolean largeBiomes, Registry<Biome> biomeRegistry, CallbackInfo ci) {
		this.caveBiomeNoise = new PerlinNoiseSampler(new SimpleRandom(seed));
		biomeSamp = BiomeLayers.build(seed, legacyBiomeInitLayer, largeBiomes ? 8 : 7, 8);
	}

	/**
	 * @author SuperCoder79
	 */
	@Inject(method = "getBiomeForNoiseGen", at = @At("HEAD"), cancellable = true)
	public void getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> cir) {
		
		
		
		Biome surfaceBiome = this.biomeSamp.sample(this.biomeRegistry, biomeX, biomeZ);
		cir.setReturnValue(surfaceBiome);
		/*if (biomeY < 14) {
			double noise = this.caveBiomeNoise.sample(biomeX / 200.0, 0, biomeZ / 200.0) ;
			if (noise > 0.07 && !((surfaceBiome.getCategory() == Biome.Category.SWAMP) || (surfaceBiome.getCategory() == Biome.Category.OCEAN))) {
				cir.setReturnValue(this.biomeRegistry.get(BiomeKeys.LUSH_CAVES));
			} else if (noise < -0.07	) {
				cir.setReturnValue(this.biomeRegistry.get(BiomeKeys.DRIPSTONE_CAVES));
			} 
		} 
		if (cir.getReturnValue() == null) {
			cir.setReturnValue(surfaceBiome);
		}*/
	}
}