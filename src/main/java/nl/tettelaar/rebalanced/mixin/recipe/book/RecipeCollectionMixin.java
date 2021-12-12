package nl.tettelaar.rebalanced.mixin.recipe.book;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.recipe.RecipeBookInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mixin(RecipeCollection.class)
public class RecipeCollectionMixin {

    @Shadow @Final private List<Recipe<?>> recipes;
    private final Set<Recipe<?>> discovered = Sets.newHashSet();

    @Shadow @Final final private Set<Recipe<?>> known = Sets.newHashSet();
    @Shadow @Final private final Set<Recipe<?>> craftable = Sets.newHashSet();
    @Shadow @Final private final Set<Recipe<?>> fitsDimensions = Sets.newHashSet();

    @Overwrite
    public void updateKnownRecipes(RecipeBook recipeBook) {
        for (Recipe<?> recipe : this.recipes) {
            if (!(recipeBook.contains(recipe) || ((RecipeBookInterface)recipeBook).isDiscovered(recipe))) continue;
            this.known.add(recipe);
        }
    }

    @Overwrite
    public void canCraft(StackedContents stackedContents, int i, int j, RecipeBook recipeBook) {
        for (Recipe<?> recipe : this.recipes) {
            boolean bl = recipe.canCraftInDimensions(i, j) && (recipeBook.contains(recipe) || ((RecipeBookInterface)recipeBook).isDiscovered(recipe));
            if (bl) {
                this.fitsDimensions.add(recipe);
            } else {
                this.fitsDimensions.remove(recipe);
            }
            if (bl && stackedContents.canCraft(recipe, null)) {
                this.craftable.add(recipe);
                continue;
            }
            this.craftable.remove(recipe);
        }
    }

    @Overwrite
    public List<Recipe<?>> getRecipes(boolean bl) {
        ArrayList<Recipe<?>> list = com.google.common.collect.Lists.newArrayList();
        Set<Recipe<?>> set = bl ? this.craftable : this.fitsDimensions;
        for (Recipe<?> recipe : this.recipes) {
            if (!set.contains(recipe)) continue;
            list.add(recipe);
        }
        return list;
    }

    @Overwrite
    public List<Recipe<?>> getDisplayRecipes(boolean bl) {
        ArrayList<Recipe<?>> list = Lists.newArrayList();
        for (Recipe<?> recipe : this.recipes) {
            if (!this.fitsDimensions.contains(recipe) || this.craftable.contains(recipe) != bl) continue;
            list.add(recipe);
        }
        return list;
    }

}
