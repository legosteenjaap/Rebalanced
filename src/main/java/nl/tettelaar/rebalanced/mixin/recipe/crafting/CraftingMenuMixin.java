package nl.tettelaar.rebalanced.mixin.recipe.crafting;

import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import nl.tettelaar.rebalanced.recipe.interfaces.CraftingMenuInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin implements CraftingMenuInterface {

    @Shadow @Final private final ResultContainer resultSlots = new ResultContainer();

    @Override
    public ResultContainer getResultContainer() {
        return resultSlots;
    }

}
