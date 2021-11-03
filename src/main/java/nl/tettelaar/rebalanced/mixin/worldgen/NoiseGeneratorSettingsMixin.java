package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NoiseData.class)
public class NoiseGeneratorSettingsMixin {
    @ModifyConstant(method = "registerBiomeNoises", constant = @Constant(intValue = -10, ordinal = 0))
    private static int changeFirstTempOctave(int value) {
        return -12;
    }

    @ModifyConstant(method = "registerBiomeNoises", constant = @Constant(intValue = -8, ordinal = 0))
    private static int changeFirstHumidOctave(int value) {
        return -12;
    }

    @ModifyConstant(method = "registerBiomeNoises", constant = @Constant(intValue = -9, ordinal = 0))
    private static int changeFirstContinentalOctave(int value) {
        return -11;
    }
}
