package nl.tettelaar.rebalanced.recipe.interfaces;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;

public interface RecipeBookInterface {

    final Set<ResourceLocation> discovered = Sets.newHashSet();

    public void discover(ResourceLocation resourceLocation);
    public void discover(Recipe<?> recipe);
    public boolean isDiscovered(Recipe<?> recipe);
    default Set<ResourceLocation> getDiscoveredRecipes () {
        return discovered;
    }

}
