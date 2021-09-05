package nl.tettelaar.rebalanced.mixin.ai;

import net.fabricmc.loader.util.sat4j.core.Vec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeleeAttackGoal.class)
public class AttackGoalMixin {

	@Shadow @Final protected PathAwareEntity mob;

	@ModifyVariable(method = "attack", at = @At("HEAD"), ordinal = 0)
	private double injected(double squaredDistance) {
		return squaredDistance - 4;
	}

	@Inject(method = "attack", at = @At("HEAD"), cancellable = true)
	protected void attack(LivingEntity target, double squaredDistance, CallbackInfo ci) {
		double mobY = mob.getY();
		double mobHeight = mob.getHeight();
		Vec3d eyePosMob = new Vec3d(mob.getX(), mob.getY() + mob.getEyeHeight(mob.getPose()), mob.getZ());
		Vec3d eyePosTarget = new Vec3d(target.getX(), target.getY() + target.getEyeHeight(mob.getPose()), target.getZ());
		Vec3d posTarget = target.getPos();
		boolean canAttackEye = mob.world.raycast(new RaycastContext(eyePosMob, eyePosTarget, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mob)).getType() != HitResult.Type.BLOCK;
		boolean canAttackFeet = mob.world.raycast(new RaycastContext(eyePosMob, posTarget, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mob)).getType() != HitResult.Type.BLOCK;
		if (!canAttackEye && !canAttackFeet) ci.cancel();
	}

}