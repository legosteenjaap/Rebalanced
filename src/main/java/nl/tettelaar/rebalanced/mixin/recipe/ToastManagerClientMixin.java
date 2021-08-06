package nl.tettelaar.rebalanced.mixin.recipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.Window;

@Mixin(ToastManager.class)
public class ToastManagerClientMixin {

	@Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getScaledWidth()I"))
	private int changePos(Window window) {
	  return window.getScaledWidth() / 2 ;
	}
	
}
