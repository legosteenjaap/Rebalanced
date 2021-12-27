package nl.tettelaar.rebalanced.mixin.xpbar;

import net.minecraft.client.gui.Font;
import nl.tettelaar.rebalanced.render.interfaces.FontInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.class)
public class FontMixin implements FontInterface {

    @Unique private static boolean hasTransparency = false;

    @Override
    public void enableTransparency() {
        hasTransparency = true;
    }

    @Override
    public void disableTransparency() {
        hasTransparency = false;
    }

    @Inject(method = "adjustColor", at = @At("HEAD"), cancellable = true)
    private static void adjustColor(int i, CallbackInfoReturnable<Integer> cir) {
        if (hasTransparency) {
            cir.setReturnValue(i);
        }
    }

}
