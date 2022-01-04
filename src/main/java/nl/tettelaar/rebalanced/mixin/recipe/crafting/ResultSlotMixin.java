package nl.tettelaar.rebalanced.mixin.recipe.crafting;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import nl.tettelaar.rebalanced.recipe.interfaces.ResultContainerInterface;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ResultSlot.class)
public class ResultSlotMixin extends Slot {

    @Shadow @Final
    private CraftingContainer craftSlots;

    public ResultSlotMixin(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPickup(Player player) {
        ResultContainerInterface resultContainer = ((ResultContainerInterface)this.container);
        Optional<Integer> XPCost = resultContainer.getXPCost();
        Optional<CraftingRecipe> recipe = player.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftSlots, player.level);
        boolean isUnlockable;
        if (XPCost.isPresent()) {
            if (player instanceof ServerPlayer) {
                isUnlockable = RecipeUtil.isUnlockable((ServerPlayer) player, XPCost.get(), recipe.get());
            } else {
                isUnlockable = RecipeUtil.isUnlockable((LocalPlayer) player, XPCost.get(), recipe.get());
            }
        } else {
            isUnlockable = false;
        }
        if (resultContainer.isRecipeUnlocked() || (XPCost.isPresent() && recipe.isPresent() && isUnlockable)){
            return true;
        }
        return false;
    }

    @Inject(method = "onTake", at = @At("HEAD"), cancellable = true)
    public void onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        ResultContainerInterface resultContainer = ((ResultContainerInterface) this.container);
        Optional<Integer> XPCost = resultContainer.getXPCost();
        Optional<CraftingRecipe> recipe = player.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftSlots, player.level);
        if (player instanceof ServerPlayer && !player.isCreative() && !resultContainer.isRecipeUnlocked() && (XPCost.isPresent() && recipe.isPresent() && RecipeUtil.isUnlockable((ServerPlayer) player, XPCost.get(), recipe.get()))) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.setExperienceLevels(serverPlayer.experienceLevel - resultContainer.getXPCost().get());
        }
    }


}
