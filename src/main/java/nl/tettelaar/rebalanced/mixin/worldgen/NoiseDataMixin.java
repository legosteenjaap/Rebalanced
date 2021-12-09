package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NoiseData.class)
public class NoiseDataMixin {
    @ModifyConstant(method = "bootstrap", constant = @Constant(intValue = 0, ordinal = 0))
    private static int changeBiomeSizeNormalNoise(int value) {
        return -2;
    }

    @ModifyConstant(method = "bootstrap", constant = @Constant(intValue = -2, ordinal = 0))
    private static int changeBiomeSizeLargeNoise(int value) {
        return -3;
    }

    @ModifyConstant(method = "bootstrap", constant = @Constant(intValue = -7, ordinal = 0))
    private static int changeRidgeSize(int value) {
        return -9;
    }
}
