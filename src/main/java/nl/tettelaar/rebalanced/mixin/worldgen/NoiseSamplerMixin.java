package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.world.level.levelgen.NoiseSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseSampler.class)
public class NoiseSamplerMixin {
    @Inject(method = "getContinentalness", at = @At("RETURN"), cancellable = true)
    public void getContinentalness(double d, double e, double f, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(cir.getReturnValue() - 0.4);
    }
}