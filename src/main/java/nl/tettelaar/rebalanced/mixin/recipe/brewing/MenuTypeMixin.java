package nl.tettelaar.rebalanced.mixin.recipe.brewing;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MenuType.class)
public class MenuTypeMixin {

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static <T extends AbstractContainerMenu> void register(String string, MenuType.MenuSupplier<T> menuSupplier, CallbackInfoReturnable<MenuType<T>> cir) {
        if (string.equals("brewing_stand")) cir.setReturnValue(null);
    }
}
