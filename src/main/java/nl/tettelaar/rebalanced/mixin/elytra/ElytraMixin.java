package nl.tettelaar.rebalanced.mixin.elytra;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class ElytraMixin extends Entity {

	private int elytraWetCooldown = 0;
	
	public ElytraMixin(EntityType<?> type, Level world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}

	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot slot);

	
	@Inject(method = "isFallFlying", at = @At("RETURN"), cancellable = true)
	public void isFallFlying(CallbackInfoReturnable<Boolean> cir) {
		
		ItemStack itemStack = this.getItemBySlot(EquipmentSlot.CHEST);
        if ((itemStack.is(Items.ELYTRA) && ElytraItem.isFlyEnabled(itemStack)) && elytraWetCooldown < 20 && this.isInWaterRainOrBubble()) {
			elytraWetCooldown = 3000;
		}
		if (elytraWetCooldown != 0) {
			this.setSharedFlag(Entity.FLAG_FALL_FLYING, false);
			cir.setReturnValue(false);
		}
		if (elytraWetCooldown != 0) {
			elytraWetCooldown = elytraWetCooldown - 1;
			if (elytraWetCooldown < 0) {
				elytraWetCooldown = 0;
			}
		}
	}
}
 