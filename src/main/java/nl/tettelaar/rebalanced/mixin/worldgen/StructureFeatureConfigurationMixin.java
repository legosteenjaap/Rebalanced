package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureFeatureConfiguration.class)
public class StructureFeatureConfigurationMixin {

    @Inject(method = "spacing", at = @At("RETURN"), cancellable = true)
    public void spacing(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() * 2);
    }

}
