package nl.tettelaar.rebalanced.mixin.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.recipe.interfaces.AdvancementRewardsInterface;
import nl.tettelaar.rebalanced.recipe.interfaces.PlayerRecipeInterface;
import nl.tettelaar.rebalanced.recipe.RecipeStatus;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(AdvancementRewards.class)
public class AdvancementRewardsMixin implements AdvancementRewardsInterface {

    @Unique private RecipeStatus status = null;

    @Override
    public void setRecipeStatus(RecipeStatus status) {
        this.status = status;
    }

    @Override
    public boolean hasRecipeStatus() {
        return this.status != null;
    }

    @Redirect(method = "grant", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;awardRecipesByKey([Lnet/minecraft/resources/ResourceLocation;)V"))
    private void changeRecipesStatus(ServerPlayer serverPlayer, ResourceLocation[] recipes) {
        if (status != null) {
            switch (status) {
                case LOCKED:
                    ArrayList<Recipe<?>> recipeList = new ArrayList<>();
                    for (ResourceLocation resourceLocation : recipes) {
                        serverPlayer.getServer().getRecipeManager().byKey(resourceLocation).ifPresent(recipeList::add);
                    }
                    serverPlayer.resetRecipes(recipeList);
                    break;
                case UNLOCKED:
                    serverPlayer.awardRecipesByKey(recipes);
                    break;
                case DISCOVERED:
                    ((PlayerRecipeInterface) serverPlayer).discoverRecipesByKey(recipes);
                    break;
            }
        } else {
            for (ResourceLocation id : recipes) {
                Optional<? extends Recipe<?>> recipe = serverPlayer.getServer().getRecipeManager().byKey(id);
                if (recipe.isPresent() && !RecipeAPI.isDiscoverable(recipe.get())) serverPlayer.awardRecipesByKey(recipes);
            }
            ((PlayerRecipeInterface) serverPlayer).discoverRecipesByKey(recipes);
        }
    }

    @Inject(method = "deserialize", at = @At("RETURN"), cancellable = true)
    private static void deserialize(JsonObject jsonObject, CallbackInfoReturnable<AdvancementRewards> cir) throws JsonParseException {
        RecipeStatus status = null;
        if (jsonObject.has("recipestatus")) {
            String recipeStatus = GsonHelper.getAsString(jsonObject, "recipestatus");

            switch (recipeStatus) {
                case "UNLOCKED":
                    status = RecipeStatus.UNLOCKED;
                    break;
                case "LOCKED":
                    status = RecipeStatus.LOCKED;
                    break;
                case "DISCOVERED":
                    status = RecipeStatus.DISCOVERED;
                    break;
                default:
                    throw new JsonParseException("This is not a recipe status: " + recipeStatus);
            }
        }
        if (status != null)
            ((AdvancementRewardsInterface) (Object) cir.getReturnValue()).setRecipeStatus(status);
    }


}
