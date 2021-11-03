package nl.tettelaar.rebalanced.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Boat.class)
public abstract class BoatEntityMixin {
    @ModifyConstant(constant = @Constant(doubleValue = 0.20000000298023224D), method = "tick()V")
    private static double expandBoxWidth(double original) {
        return original + 0.1D;
    }

    @ModifyConstant(constant = @Constant(doubleValue = -0.009999999776482582D), method = "tick()V")
    private static double expandBoxHeight(double original) {
        return original * -1;
    }

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getBbWidth()F", ordinal = 0))
    private float makeExceptionForHorse(Entity entity) {
        if (entity instanceof AbstractHorse) return 0;
        return entity.getBbWidth();
    }
}