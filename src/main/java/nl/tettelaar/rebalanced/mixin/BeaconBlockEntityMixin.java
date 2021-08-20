package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.block.entity.BeaconBlockEntity;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

	@ModifyVariable(method = "applyPlayerEffects", at = @At("STORE"), ordinal = 0)
	private static double injected(double beaconRange) {
	  return beaconRange * 10D;
	}
	
}
