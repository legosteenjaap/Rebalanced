package nl.tettelaar.rebalanced.mixin.ai;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "getAttributeBaseValue", at = @At("HEAD"), cancellable = true)
	public void getAttributeBaseValue(EntityAttribute attribute, CallbackInfoReturnable<Double> cir) {
		if (this.getType() != EntityType.PLAYER && (attribute.getTranslationKey().equals("attribute.name.generic.attack_damage") || attribute.getTranslationKey().equals("attribute.name.generic.max_health"))) {
			cir.setReturnValue(this.getAttributes().getBaseValue(attribute) * 1.5);
		}
	}
	
	@Inject(method = "getAttributeValue", at = @At("HEAD"), cancellable = true)
	public void getAttributeValue(EntityAttribute attribute, CallbackInfoReturnable<Double> cir) {
		if (this.getType() != EntityType.PLAYER && (attribute.getTranslationKey().equals("attribute.name.generic.attack_damage") || attribute.getTranslationKey().equals("attribute.name.generic.max_health"))) {
			cir.setReturnValue(this.getAttributes().getValue(attribute) * 1.5);
		}
	}

	@Shadow
	public AttributeContainer getAttributes() {
		return null;
	}

}
