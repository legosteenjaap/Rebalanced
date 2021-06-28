package nl.tettelaar.rebalanced.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

@Mixin(EscapeDangerGoal.class)
public abstract class EscapeDangerGoalMixin extends Goal {

	final double distance = 15D;

	private int timer;

	@Shadow
	@Final
	protected PathAwareEntity mob;
	@Shadow
	@Final
	protected double speed;
	@Shadow
	protected double targetX;
	@Shadow
	protected double targetY;
	@Shadow
	protected double targetZ;
	@Shadow
	protected boolean active;
	
	@Shadow
	protected boolean findTarget() {
		return false;
	}

	@Override
	public void tick() {
		timer++;
		if (this.mob.getNavigation().isIdle()) {
			this.findTarget();
			this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed * 1.5);
		}
	}

	@Inject(method = "stop", at = @At("HEAD"))
	public void stop(CallbackInfo ci) {
		this.mob.setAttacker(null);
	}

	@Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
	public void canStart(CallbackInfoReturnable<Boolean> cir) {
		double x = mob.getX();
		double y = mob.getY();
		double z = mob.getZ();
		Box box = new Box(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance);
		List<PlayerEntity> players = mob.getEntityWorld().getEntitiesByType(
				EntityType.PLAYER, box, EntityPredicates.VALID_LIVING_ENTITY);
		for (PlayerEntity player : players) {
			
		}
	}
	
	@Inject(method = "findTarget", at = @At("HEAD"), cancellable = true)
	protected void findTarget(CallbackInfoReturnable<Boolean> cir) {
		Vec3d vec3d = NoPenaltyTargeting.find(this.mob, 100, 8);

		if (vec3d == null) {
			cir.setReturnValue(false);
		} else {
			this.targetX = vec3d.x;
			this.targetY = vec3d.y;
			this.targetZ = vec3d.z;
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "start", at = @At("HEAD"), cancellable = true)
	public void start(CallbackInfo ci) {

		timer = 0;

		double x = mob.getX();
		double y = mob.getY();
		double z = mob.getZ();

		Box box = new Box(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance);



		try {
			List<LivingEntity> entities = mob.getEntityWorld().getEntitiesByType(
					((EntityType<LivingEntity>) this.mob.getType()), box, EntityPredicates.VALID_LIVING_ENTITY);
			for (LivingEntity entity : entities) {
				if (entity.getAttacker() == null) {
					entity.setAttacker(this.mob.getAttacker());
				}
			}
		} catch (ClassCastException e) {

		}
		this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
		this.active = true;
		ci.cancel();

	}

	@Inject(method = "shouldContinue", at = @At("RETURN"), cancellable = true)
	public void shouldContinue(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(timer < 1000);
	}
}
