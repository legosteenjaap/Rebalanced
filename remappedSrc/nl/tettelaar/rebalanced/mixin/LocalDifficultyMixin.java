package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.LocalDifficulty;

@Mixin(LocalDifficulty.class)
public class LocalDifficultyMixin {
	@Shadow @Final float localDifficulty;
	
	@Inject(method = "getLocalDifficulty", at = @At("HEAD"),cancellable = true)
	public void getLocalDifficulty (CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(localDifficulty + 2);
	}
	
}
