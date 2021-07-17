package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class ElytraMixin extends Entity {

	private int elytraCooldown;
	
	public ElytraMixin(EntityType<?> type, World world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "isFallFlying", at = @At("HEAD"), cancellable = true)
	public void isFallFlying(CallbackInfoReturnable<Boolean> cir) {
		if ((world.isRaining() && world.hasRain(new BlockPos(this.getPos())))|| this.isSubmergedInWater() && elytraCooldown == 0) {
			this.setFlag(Entity.FALL_FLYING_FLAG_INDEX, false);
			cir.setReturnValue(false);
			elytraCooldown = 5;
		} else if (elytraCooldown > 0) {
			elytraCooldown--;
		}
	}
}
