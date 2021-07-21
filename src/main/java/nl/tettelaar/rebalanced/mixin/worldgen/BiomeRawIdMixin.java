package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import nl.tettelaar.rebalanced.init.worldgen.Biomes;

@Mixin(BuiltinBiomes.class)
public class BiomeRawIdMixin {
	
	@Inject(method = "fromRawId", at = @At("HEAD"), cancellable = true)
	private static void fromRawId(int rawId, CallbackInfoReturnable<RegistryKey<Biome>> cir) {
		switch (rawId) {
		case 188:
			cir.setReturnValue(Biomes.SNOWY_GIANT_SPRUCE_TAIGA_KEY);
		}
	}

}
