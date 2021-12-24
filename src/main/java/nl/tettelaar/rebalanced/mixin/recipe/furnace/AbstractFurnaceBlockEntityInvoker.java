package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityInvoker {

    @Accessor("litTime")
    public int getLitTime();

    @Accessor("recipeType")
    public RecipeType getRecipeType();

}
