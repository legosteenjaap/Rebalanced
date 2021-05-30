package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;

@Mixin(MeleeAttackGoal.class)
public class AttackGoalMixin {
	@ModifyVariable(method = "attack", at = @At("HEAD"), ordinal = 0)
	private double injected(double squaredDistance) {
		return squaredDistance - 4;
	}
}