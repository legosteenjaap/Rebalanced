package nl.tettelaar.rebalanced.mixin.recipe.unlock;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeHolder.class)
interface RecipeHolderMixin {
    @Shadow
    public void setRecipeUsed(@Nullable Recipe<?> var1);

    @Inject(method = "setRecipeUsed(Lnet/minecraft/world/level/Level;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/crafting/Recipe;)Z", at = @At("HEAD"), cancellable = true)
    default public void setRecipeUsed(Level level, ServerPlayer serverPlayer, Recipe<?> recipe, CallbackInfoReturnable<Boolean> cir) {
        this.setRecipeUsed(recipe);
        cir.setReturnValue(true);
    }

}
