package nl.tettelaar.rebalanced.mixin.recipe.stonecutter;


import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import nl.tettelaar.rebalanced.network.NetworkingClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(StonecutterMenu.class)
public class StonecutterMenuServerMixin {

    @Shadow
    private List<StonecutterRecipe> recipes;
    @Shadow
    @Final
    private Level level;
    @Shadow
    @Final
    Slot resultSlot;
    @Shadow
    @Final
    private DataSlot selectedRecipeIndex;

    @Unique
    private Player player;

    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At("RETURN"))
    public void init(int i, Inventory inventory, final ContainerLevelAccess containerLevelAccess, CallbackInfo ci) {
        player = inventory.player;
    }

    @Inject(method = "setupRecipeList", at = @At("HEAD"), cancellable = true)
    private void setupRecipeList(Container input, ItemStack stack, CallbackInfo ci) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            ArrayList<StonecutterRecipe> recipes = new ArrayList<StonecutterRecipe>(this.level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, input, this.level));
            ArrayList<StonecutterRecipe> trueRecipes = new ArrayList<>();
            for (StonecutterRecipe recipe : recipes) {
                if ((((ServerPlayer) player).getRecipeBook().contains(recipe) || !level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING))) {
                    trueRecipes.add(recipe);
                }
            }
            this.recipes = trueRecipes;
        }
        ci.cancel();
    }


}
