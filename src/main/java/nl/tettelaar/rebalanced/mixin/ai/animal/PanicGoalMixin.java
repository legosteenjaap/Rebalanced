package nl.tettelaar.rebalanced.mixin.ai.animal;

import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PanicGoal.class)
public abstract class 	PanicGoalMixin extends Goal {

	final double distance = 30D;

	private static final float speedMultiplier = 1.2f;

	private int timer;

	@Shadow	@Final protected PathfinderMob mob;
	@Shadow	@Final protected double speedModifier;
	@Shadow	protected double posX;
	@Shadow	protected double posY;
	@Shadow	protected double posZ;
	@Shadow	protected boolean isRunning;

	@Shadow
	protected boolean findRandomPosition() {
		return false;
	}

	@Override
	public void tick() {
		timer++;
		if (this.mob.getNavigation().isDone()) {
			this.findRandomPosition();
			this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier * speedMultiplier);
		}
	}

	@Inject(method = "stop", at = @At("HEAD"))
	public void stop(CallbackInfo ci) {
		this.mob.setLastHurtByMob(null);
	}

	@Inject(method = "findRandomPosition", at = @At("HEAD"), cancellable = true)
	protected void findRandomPosition(CallbackInfoReturnable<Boolean> cir) {
		Vec3 vec3d = DefaultRandomPos.getPos(this.mob, 12, 6);

		if (vec3d == null) {
			cir.setReturnValue(false);
		} else {
			this.posX = vec3d.x;
			this.posY = vec3d.y;
			this.posZ = vec3d.z;
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "start", at = @At("HEAD"), cancellable = true)
	public void start(CallbackInfo ci) {

		timer = 0;

		double x = mob.getX();
		double y = mob.getY();
		double z = mob.getZ();

		AABB box = new AABB(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance);

		try {
			List<Entity> entities = mob.getCommandSenderWorld().getEntities(this.mob, box, EntitySelector.LIVING_ENTITY_STILL_ALIVE);

			for (Entity entity : entities) {
				if (entity instanceof Animal) {
					Animal animalEntity = (Animal) entity;
					if (animalEntity.getLastHurtByMob() == null) {
						animalEntity.setLastHurtByMob(this.mob.getLastHurtByMob());
					}
				}
			}

		} catch (ClassCastException e) {

		}
		this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier * speedMultiplier);
		this.isRunning = true;
		ci.cancel();

	}

	@Inject(method = "isRunning", at = @At("RETURN"), cancellable = true)
	public void isRunning(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(timer < 500);
	}
}
