package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;

@Mixin(VanillaLayeredBiomeSource.class)
public class LargerBiomeMixin {

	@Shadow
	@Final
	private long seed;
	@Shadow
	@Final
	private boolean legacyBiomeInitLayer;
	@Shadow
	@Final
	private boolean largeBiomes;
	@Shadow
	@Final
	private Registry<Biome> biomeRegistry;
	private BiomeLayerSampler biomeSamp;

	@Inject(method = "getBiomeForNoiseGen", at = @At("HEAD"), cancellable = true)
	public void getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> cir) {
		try {
			cir.setReturnValue(biomeSamp.sample(this.biomeRegistry, biomeX, biomeZ));
		} catch (NullPointerException e) {
			biomeSamp = BiomeLayers.build(seed, legacyBiomeInitLayer, largeBiomes ? 8 : 7, 8);
		}
	}

}