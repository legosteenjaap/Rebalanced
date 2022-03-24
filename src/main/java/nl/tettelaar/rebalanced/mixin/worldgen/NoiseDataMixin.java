package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseData.class)
public class NoiseDataMixin {

    /*@Inject(method = "bootstrap", at = @At("RETURN"), cancellable = true)
    private static void bootstrap(CallbackInfoReturnable<NormalNoise.NoiseParameters> cir) {
        register(RebalancedWorldGen.CAVE_LIMITER, -8, 1.0, new double[0]);
    }

    @Shadow
    private static void register(ResourceKey<NormalNoise.NoiseParameters> resourceKey, int i, double d, double ... ds) {
        BuiltinRegistries.register(BuiltinRegistries.NOISE, resourceKey, new NormalNoise.NoiseParameters(i, d, ds));
    }*/

    @ModifyConstant(method = "bootstrap", constant = @Constant(intValue = 0, ordinal = 0))
    private static int changeBiomeSizeNormalNoise(int value) {
        return -3;
    }

    @ModifyConstant(method = "bootstrap", constant = @Constant(intValue = -2, ordinal = 0))
    private static int changeBiomeSizeLargeNoise(int value) {
        return -4;
    }

    /*@ModifyConstant(method = "bootstrap", constant = @Constant(intValue = -7, ordinal = 0))
    private static int changeRidgeSize(int value) {
        return -9;
    }*/
}
