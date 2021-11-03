package nl.tettelaar.rebalanced.mixin.ai.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSkeleton.class)
public class SkeletonEntityMixin extends Monster {

	protected SkeletonEntityMixin(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "getArrow", at = @At("HEAD"), cancellable = true)
	protected void getArrow(ItemStack arrow, float damageModifier, CallbackInfoReturnable<AbstractArrow> cir) {
		cir.setReturnValue(ProjectileUtil.getMobArrow(this, arrow, damageModifier * 2));
	}

}
