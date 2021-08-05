package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.gen.chunk.StructureConfig;

@Mixin(StructureConfig.class)
public class StructureConfigMixin {
	@Inject(method = "getSpacing", at = @At("RETURN"), cancellable = true)
	public void getSpacing(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(cir.getReturnValue() * 8);
	}
}
