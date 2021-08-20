package nl.tettelaar.rebalanced.mixin.sleep;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.world.ClientWorld;
import nl.tettelaar.rebalanced.Rebalanced;

@Mixin(ClientWorld.class)
public class LongerDayCycleClientMixin {


	int timeCounter = 0;
	
	@Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
	protected void tickTime(CallbackInfo ci) {
		timeCounter++;
		if (Rebalanced.timeMultiplier == timeCounter) {
			timeCounter = 0;
		} else {
			ci.cancel();
		}
	}
}
