package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceMenuInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {FurnaceBlockEntity.class, SmokerBlockEntity.class, BlastFurnaceBlockEntity.class})
public abstract class FurnaceBlockEntityMixin extends AbstractFurnaceBlockEntity {

    protected FurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntityType, blockPos, blockState, recipeType);
    }

    @Inject(method = "createMenu", at = @At("RETURN"))
    protected void createMenu(int i, Inventory inventory, CallbackInfoReturnable<AbstractContainerMenu> cir) {
        ((FurnaceMenuInterface)(Object)cir.getReturnValue()).setBlockEntity(this);
    }

}
