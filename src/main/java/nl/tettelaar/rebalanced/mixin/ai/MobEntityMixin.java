package nl.tettelaar.rebalanced.mixin.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity {

    @Shadow private LivingEntity target;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        if (this.target != null) {
            double rotX = target.getX() - this.getX();
            double rotZ = target.getZ() - this.getZ();

            float rot = (float) (Mth.atan2(rotZ, rotX) * 57.2957763671875D) - 90.0F;
            this.setYRot(this.wrapDegrees(this.getYRot(), rot, 90.0F));
            this.setYHeadRot(this.wrapDegrees(this.getYRot(), rot, 90.0F));
        }
    }

    protected float wrapDegrees(float from, float to, float max) {
        float f = Mth.wrapDegrees(to - from);
        if (f > max) {
            f = max;
        }

        if (f < -max) {
            f = -max;
        }

        float g = from + f;
        if (g < 0.0F) {
            g += 360.0F;
        } else if (g > 360.0F) {
            g -= 360.0F;
        }

        return g;
    }
}
