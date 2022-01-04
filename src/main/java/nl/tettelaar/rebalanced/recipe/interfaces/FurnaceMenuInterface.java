package nl.tettelaar.rebalanced.recipe.interfaces;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

import java.util.Optional;

public interface FurnaceMenuInterface {

    public void setBlockEntity(AbstractFurnaceBlockEntity blockEntity);
    public Optional<Integer> getXPCost();
    public Recipe<?> getRecipe();

}
