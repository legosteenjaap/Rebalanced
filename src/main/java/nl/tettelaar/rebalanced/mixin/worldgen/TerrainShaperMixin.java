package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.level.biome.TerrainShaper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TerrainShaper.class)
public class TerrainShaperMixin {

    @Shadow @Final private static final ToFloatFunction<Float> NO_TRANSFORM = float_ -> float_.floatValue();

    private static float getModifiedOffset(float f) {
        return f < 0.0f ? f : f * 4.0f;
    }

    private static float getModifiedJaggedness(float f) {
        return f * 4.0f;
    }

    @ModifyVariable(method = "overworld", at = @At("STORE"), ordinal = 0)
    private static ToFloatFunction<Float> modifyOffset(ToFloatFunction<Float> function) {
        if (function.equals(NO_TRANSFORM)) {
            return TerrainShaperMixin::getModifiedOffset;
        }
        return function;
    }

    @Inject(method = "getAmplifiedOffset", at = @At("RETURN"), cancellable = true)
    private static void updateAmplifiedOffset(float f, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(getModifiedOffset(f));
    }

    @Inject(method = "getAmplifiedJaggedness", at = @At("RETURN"), cancellable = true)
    private static void updateAmplifiedJaggedness(float f, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(getModifiedJaggedness(f));
    }

    @ModifyVariable(method = "overworld", at = @At("STORE"), ordinal = 0)
    private static ToFloatFunction<Float> modifyJaggedness(ToFloatFunction<Float> function) {
        if (function.equals(NO_TRANSFORM)) {
            return TerrainShaperMixin::getModifiedJaggedness;
        }
        return function;
    }

}
