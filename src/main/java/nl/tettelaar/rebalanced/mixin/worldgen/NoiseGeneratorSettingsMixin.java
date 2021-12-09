package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin {

    @ModifyConstant(method = "overworld", constant = @Constant(intValue = 2, ordinal = 0))
    private static int removeGenLimit(int orgValue) {
        return 0;
    }

    @ModifyConstant(method = "overworld", constant = @Constant(intValue = 384, ordinal = 0))
    private static int raiseWorldHeight(int orgValue) {
        return 1024;
    }

}
