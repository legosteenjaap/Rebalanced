package nl.tettelaar.rebalanced.mixin.recipe.book;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.recipe.PlayerRecipeInterface;
import nl.tettelaar.rebalanced.recipe.ServerRecipeBookInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(ServerPlayer.class)
public class PlayerDiscoverMixin implements PlayerRecipeInterface {

    @Shadow @Final
    private ServerRecipeBook recipeBook = new ServerRecipeBook();

    @Shadow @Final public MinecraftServer server;

    @Override
    public int discoverRecipes(Collection<Recipe<?>> recipes) {
        return ((ServerRecipeBookInterface)(Object)this.recipeBook).discoverRecipes(recipes, (ServerPlayer)(Object)this);
    }

    @Override
    public void discoverRecipesByKey(ResourceLocation[] resourceLocations) {
        ArrayList<Recipe<?>> recipes = Lists.newArrayList();
        for (ResourceLocation resourceLocation : resourceLocations) {
            this.server.getRecipeManager().byKey(resourceLocation).ifPresent(recipes::add);
        }
        this.discoverRecipes(recipes);
    }
}
