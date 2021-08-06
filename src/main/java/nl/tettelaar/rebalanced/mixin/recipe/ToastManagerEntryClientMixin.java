package nl.tettelaar.rebalanced.mixin.recipe;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;

@Mixin(ToastManager.Entry.class)
public class ToastManagerEntryClientMixin <T extends Toast>{

    @Shadow @Final private T instance;
	
	@Shadow
	private float getDisappearProgress(long time) {
        throw new AssertionError();
     }
	
	@Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V"))
	private void changePos(MatrixStack matrixStack, double x, double y, double z) {
		double originalX = x + (this.instance.getWidth() * this.getDisappearProgress(Util.getMeasuringTimeMs()));
		double originalY = y / this.instance.getHeight();
		matrixStack.translate(originalX - this.instance.getWidth() / 2, originalY - this.instance.getHeight() + (this.instance.getHeight() * this.getDisappearProgress(Util.getMeasuringTimeMs())), z);
	}
	
}
