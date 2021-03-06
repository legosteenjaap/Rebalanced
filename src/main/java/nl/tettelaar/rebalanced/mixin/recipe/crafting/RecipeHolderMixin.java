package nl.tettelaar.rebalanced.mixin.recipe.crafting;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(RecipeHolder.class)
interface RecipeHolderMixin {
    @Shadow
    public void setRecipeUsed(@Nullable Recipe<?> var1);

    @Inject(method = "setRecipeUsed(Lnet/minecraft/world/level/Level;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/crafting/Recipe;)Z", at = @At("HEAD"), cancellable = true)
    default public void setRecipeUsed(Level level, ServerPlayer serverPlayer, Recipe<?> recipe, CallbackInfoReturnable<Boolean> cir) {
        this.setRecipeUsed(recipe);
        cir.setReturnValue(true);
    }

    @Redirect(method = "awardUsedRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardRecipes(Ljava/util/Collection;)I"))
    default public int addOtherRecipes(Player player, Collection<Recipe<?>> collection) {
        Recipe<?> recipe = collection.stream().collect(Collectors.toList()).get(0);
        Item unlockedItem = recipe.getResultItem().getItem();
        if (RecipeAPI.isDiscoverable(recipe) && !player.level.isClientSide) {
            return player.awardRecipes(RecipeAPI.getRecipesWithDiscoverableItem(unlockedItem.getDefaultInstance(), player.getServer().getRecipeManager()));
        } else {
            return player.awardRecipes(collection);
        }
    }
}
