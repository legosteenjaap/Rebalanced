package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceBlockEntityInterface;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceMenuInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceMenu.class)
public abstract class AbstractFurnaceMenuMixin extends RecipeBookMenu<Container> implements FurnaceMenuInterface {

    @Unique AbstractFurnaceBlockEntity blockEntity;
    private Player player;

    public AbstractFurnaceMenuMixin(MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V", at = @At("RETURN"))
    public void AbstractFurnaceMenu(MenuType<?> menuType, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookType recipeBookType, int i, Inventory inventory, Container container, ContainerData containerData, CallbackInfo ci) {
        this.player = inventory.player;
        this.addSlotListener(new ContainerListener() {
            @Override
            public void slotChanged(AbstractContainerMenu abstractContainerMenu, int i, ItemStack itemStack) {
                if ((i == 1 || i == 0) && blockEntity != null) ((FurnaceBlockEntityInterface)(Object)blockEntity).setOwner(player);
            }

            @Override
            public void dataChanged(AbstractContainerMenu abstractContainerMenu, int i, int j) {

            }
        });
    }

    @Override
    public void setBlockEntity(AbstractFurnaceBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }
}
