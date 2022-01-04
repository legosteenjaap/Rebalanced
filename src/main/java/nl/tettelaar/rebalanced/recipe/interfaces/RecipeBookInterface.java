package nl.tettelaar.rebalanced.recipe.interfaces;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;

public interface RecipeBookInterface {

    Set<ResourceLocation> discovered = Sets.newHashSet();

    public boolean discover(ResourceLocation resourceLocation);
    public boolean discover(Recipe<?> recipe);
    public boolean isDiscovered(Recipe<?> recipe);
    default Set<ResourceLocation> getDiscoveredRecipes () {
        return discovered;
    }

}
