package nl.tettelaar.rebalanced.mixin.ai.monster;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(AbstractSkeletonEntity.class)
public class SkeletonEntityMixin extends HostileEntity {

	protected SkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "createArrowProjectile", at = @At("HEAD"), cancellable = true)
	protected void createArrowProjectile(ItemStack arrow, float damageModifier, CallbackInfoReturnable<PersistentProjectileEntity> cir) {
		cir.setReturnValue(ProjectileUtil.createArrowProjectile(this, arrow, damageModifier * 2));
	}

}
