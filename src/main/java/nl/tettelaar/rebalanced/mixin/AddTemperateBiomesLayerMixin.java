package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.layer.AddClimateLayers;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(AddClimateLayers.AddTemperateBiomesLayer.class)
public class AddTemperateBiomesLayerMixin {

	@Inject(method = "sample", at = @At("HEAD"), cancellable = true)
	public void sample(LayerRandomnessSource context, int n, int e, int s, int w, int center, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(center);
	}

}
