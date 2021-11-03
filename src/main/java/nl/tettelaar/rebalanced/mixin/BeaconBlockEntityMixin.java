package nl.tettelaar.rebalanced.mixin;

import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

	@ModifyVariable(method = "applyEffects", at = @At("STORE"), ordinal = 0)
	private static double injected(double beaconRange) {
	  return beaconRange * 10D;
	}
	
}
