package nl.tettelaar.rebalanced.mixin;

import net.minecraft.world.DifficultyInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DifficultyInstance.class)
public class LocalDifficultyMixin {
	@Shadow @Final float effectiveDifficulty;
	
	@Inject(method = "getEffectiveDifficulty", at = @At("HEAD"),cancellable = true)
	public void getEffectiveDifficulty (CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(effectiveDifficulty + 2);
	}
	
}
