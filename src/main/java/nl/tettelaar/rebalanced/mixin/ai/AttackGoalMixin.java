package nl.tettelaar.rebalanced.mixin.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeleeAttackGoal.class)
public class AttackGoalMixin {

	@Shadow @Final protected PathfinderMob mob;

	@ModifyVariable(method = "checkAndPerformAttack", at = @At("HEAD"), ordinal = 0)
	private double injected(double squaredDistance) {
		return squaredDistance - 4;
	}

	@Inject(method = "checkAndPerformAttack", at = @At("HEAD"), cancellable = true)
	protected void checkAndPerformAttack(LivingEntity target, double squaredDistance, CallbackInfo ci) {
		double mobY = mob.getY();
		double mobHeight = mob.getBbHeight();
		Vec3 eyePosMob = new Vec3(mob.getX(), mob.getY() + mob.getEyeHeight(mob.getPose()), mob.getZ());
		Vec3 eyePosTarget = new Vec3(target.getX(), target.getY() + target.getEyeHeight(mob.getPose()), target.getZ());
		Vec3 posTarget = target.position();
		boolean canAttackEye = mob.level.clip(new ClipContext(eyePosMob, eyePosTarget, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob)).getType() != HitResult.Type.BLOCK;
		boolean canAttackFeet = mob.level.clip(new ClipContext(eyePosMob, posTarget, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob)).getType() != HitResult.Type.BLOCK;
		if (!canAttackEye && !canAttackFeet) ci.cancel();
	}

}