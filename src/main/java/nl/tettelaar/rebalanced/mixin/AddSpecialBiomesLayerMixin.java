package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.layer.AddClimateLayers.AddSpecialBiomesLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(AddSpecialBiomesLayer.class)
public class AddSpecialBiomesLayerMixin {
	
	@Inject(method = "sample", at = @At("HEAD"), cancellable = true)
    public void sample(LayerRandomnessSource context, int value, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(value);
	}
}
