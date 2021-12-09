package nl.tettelaar.rebalanced.mixin.recipe.unlock;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.recipe.ResultContainerInterface;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ResultContainer.class)
public abstract class ResultContainerMixin implements RecipeHolder, ResultContainerInterface, Container {

    @Unique
    private boolean isRecipeUnlocked;

    @Shadow @Nullable private Recipe<?> recipeUsed;

    @Unique private Optional<Integer> XPCost = Optional.empty();

    @Override
    public boolean setRecipeUsed(Level level, ServerPlayer serverPlayer, Recipe<?> recipe) {
        this.setRecipeUsed(recipe);
        isRecipeUnlocked = !level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING) || serverPlayer.getRecipeBook().contains(recipe);
        setXPCost(recipe.getResultItem().getItem());
        return true;
    }

    @Inject(method = "setItem", at = @At("HEAD"), cancellable = true)
    public void setItem(int i, ItemStack itemStack, CallbackInfo ci) {
        setXPCost(itemStack.getItem());
    }

    private void setXPCost (Item item) {
        Optional<Integer> OptXPCost = RecipeAPI.getRecipeXPCost(item);
        if (item != Items.AIR) XPCost = OptXPCost;
        if (XPCost.isPresent() && XPCost.get() > 99) XPCost = Optional.of(99);
    }

    @Override
    public boolean isRecipeUnlocked() {
        return isRecipeUnlocked;
    }

    @Override
    public Optional<Integer> getXPCost() {
        return XPCost;
    }

    @Override
    public boolean isUnlockable(Player player) {
        return this.getXPCost().isPresent() && (player.experienceLevel >= this.getXPCost().get() || player.isCreative());
    }

    @Shadow
    public ItemStack getItem(int i) {
        throw new AssertionError();
    }

}
