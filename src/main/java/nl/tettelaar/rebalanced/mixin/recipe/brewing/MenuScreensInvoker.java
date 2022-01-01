package nl.tettelaar.rebalanced.mixin.recipe.brewing;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import nl.tettelaar.rebalanced.recipe.BrewingStandMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(MenuScreens.class)
public interface MenuScreensInvoker {

    @Invoker("register")
    public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<BrewingStandMenu> menuType, MenuScreens.ScreenConstructor<M, U> screenConstructor) {
        throw new AssertionError();
    }

    @Accessor("SCREENS")
    public static Map<MenuType<?>, MenuScreens.ScreenConstructor<?, ?>> getScreens() {
        throw new AssertionError();
    }


}
