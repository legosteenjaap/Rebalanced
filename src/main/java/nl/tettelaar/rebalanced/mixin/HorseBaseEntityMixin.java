package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.swing.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

@Mixin(AbstractHorse.class)
public abstract class HorseBaseEntityMixin extends Animal {

    protected HorseBaseEntityMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected AABB makeBoundingBox() {
        if (this.isPassenger() && this.getVehicle() instanceof Boat) {
            AABB box = super.makeBoundingBox();
            AABB changedBox = box.setMinY(box.minY + this.getVehicle().getBbHeight());
            return changedBox;
        }
        return super.makeBoundingBox();
    }

}