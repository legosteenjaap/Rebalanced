package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.impl.screenhandler.client.ClientNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.network.NetworkingClient;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceBlockEntityInterface;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceMenuInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractFurnaceMenu.class)
public abstract class AbstractFurnaceMenuMixin extends RecipeBookMenu<Container> implements FurnaceMenuInterface {

    @Unique AbstractFurnaceBlockEntity blockEntity;
    private Player player;
    private Recipe<?> recipe;

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

    @Override
    public Optional<Integer> getXPCost () {
        if (recipe != NetworkingClient.getFurnaceRecipe()) {
            recipe = NetworkingClient.getFurnaceRecipe();
        }
        if (recipe != null) {
            return RecipeAPI.getItemXPCost(recipe.getResultItem().getItem());
        }
        return Optional.empty();
    }

    @Override
    public Recipe<?> getRecipe() {
        return recipe;
    }

}
