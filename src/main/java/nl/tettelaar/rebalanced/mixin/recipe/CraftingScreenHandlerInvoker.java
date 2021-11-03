package nl.tettelaar.rebalanced.mixin.recipe;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingMenu.class)
public interface CraftingScreenHandlerInvoker {

    @Accessor
    CraftingContainer getCraftSlots();

}
