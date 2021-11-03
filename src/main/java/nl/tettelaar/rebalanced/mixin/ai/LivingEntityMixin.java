package nl.tettelaar.rebalanced.mixin.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "getAttributeBaseValue", at = @At("HEAD"), cancellable = true)
	public void getAttributeBaseValue(Attribute attribute, CallbackInfoReturnable<Double> cir) {
		if (this.getType() != EntityType.PLAYER && (attribute.getDescriptionId().equals("attribute.name.generic.attack_damage") || attribute.getDescriptionId().equals("attribute.name.generic.max_health"))) {
			cir.setReturnValue(this.getAttributes().getBaseValue(attribute) * 1.2);
		}
	}
	
	@Inject(method = "getAttributeValue", at = @At("HEAD"), cancellable = true)
	public void getAttributeValue(Attribute attribute, CallbackInfoReturnable<Double> cir) {
		if (this.getType() != EntityType.PLAYER && (attribute.getDescriptionId().equals("attribute.name.generic.attack_damage") || attribute.getDescriptionId().equals("attribute.name.generic.max_health"))) {
			cir.setReturnValue(this.getAttributes().getValue(attribute) * 1.2);
		}
	}

	@Shadow
	public AttributeMap getAttributes() {
		return null;
	}

}
