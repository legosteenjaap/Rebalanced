package nl.tettelaar.rebalanced.mixin.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingScreenHandler.class)
public interface CraftingScreenHandlerInvoker {

    @Accessor
    CraftingInventory getInput();

}
