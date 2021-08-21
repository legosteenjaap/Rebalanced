package nl.tettelaar.rebalanced.mixin.worldgen;

import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;

@Mixin(BuiltinBiomes.class)
public class BiomeRawIdMixin {

	@Shadow
	@Final
	private static Int2ObjectMap<RegistryKey<Biome>> BY_RAW_ID = new Int2ObjectArrayMap<RegistryKey<Biome>>();

	@Inject(method = "fromRawId", at = @At("HEAD"), cancellable = true)
	private static void fromRawId(int rawId, CallbackInfoReturnable<RegistryKey<Biome>> cir) {
		if (!BY_RAW_ID.containsKey(rawId)) {
			Optional<RegistryKey<Biome>> biome = BuiltinRegistries.BIOME.getKey(BuiltinRegistries.BIOME.get(rawId));
			if (biome.isPresent()) {
				cir.setReturnValue(biome.get());
			}
		}
	}

}
