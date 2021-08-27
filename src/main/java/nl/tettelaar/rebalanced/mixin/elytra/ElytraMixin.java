package nl.tettelaar.rebalanced.mixin.elytra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class ElytraMixin extends Entity {

	private int elytraWetCooldown = 0;
	
	public ElytraMixin(EntityType<?> type, World world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}

	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	
	@Inject(method = "isFallFlying", at = @At("RETURN"), cancellable = true)
	public void isFallFlying(CallbackInfoReturnable<Boolean> cir) {
		
		ItemStack itemStack = this.getEquippedStack(EquipmentSlot.CHEST);
        if ((itemStack.isOf(Items.ELYTRA) && ElytraItem.isUsable(itemStack)) && elytraWetCooldown < 20 && this.isWet()) {
			elytraWetCooldown = 3000;
		}
		if (elytraWetCooldown != 0) {
			this.setFlag(Entity.FALL_FLYING_FLAG_INDEX, false);
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
 