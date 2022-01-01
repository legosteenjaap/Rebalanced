package nl.tettelaar.rebalanced.mixin.recipe.brewing;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MenuType.class)
public interface MenuTypeInvoker {

    @Invoker("<init>")
    public static <T extends AbstractContainerMenu> MenuType<T>  initMenuType(MenuType.MenuSupplier menuSupplier) {
        throw new AssertionError();
    }

}
