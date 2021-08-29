package nl.tettelaar.rebalanced.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.swing.*;

@Mixin(HorseBaseEntity.class)
public abstract class HorseBaseEntityMixin extends AnimalEntity {

    protected HorseBaseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Box calculateBoundingBox() {
        if (this.hasVehicle() && this.getVehicle() instanceof BoatEntity) {
            Box box = super.calculateBoundingBox();
            Box changedBox = box.withMinY(box.minY + this.getVehicle().getHeight());
            return changedBox;
        }
        return super.calculateBoundingBox();
    }

}