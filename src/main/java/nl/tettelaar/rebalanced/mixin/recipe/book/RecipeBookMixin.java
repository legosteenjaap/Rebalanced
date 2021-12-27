package nl.tettelaar.rebalanced.mixin.recipe.book;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.recipe.interfaces.RecipeBookInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(RecipeBook.class)
public class RecipeBookMixin implements RecipeBookInterface {



    @Shadow @Final
    protected final Set<ResourceLocation> known = Sets.newHashSet();


    @Inject(method = "remove(Lnet/minecraft/resources/ResourceLocation;)V", at = @At("HEAD"))
    protected void remove(ResourceLocation resourceLocation, CallbackInfo ci) {
        this.discovered.remove(resourceLocation);
    }

    @Inject(method = "copyOverData", at = @At("HEAD"))
    public void copyOverData(RecipeBook recipeBook, CallbackInfo ci) {
        this.discovered.clear();
        this.discovered.addAll(this.discovered);
    }

    @Inject(method = "add(Lnet/minecraft/resources/ResourceLocation;)V", at = @At("HEAD"))
    public void add(ResourceLocation resourceLocation, CallbackInfo ci) {
        this.discovered.remove(resourceLocation);
    }

    @Override
    public boolean discover(ResourceLocation resourceLocation) {
        if (!this.known.contains(resourceLocation)) {
            this.discovered.add(resourceLocation);
            return true;
        }
        return false;
    }

    @Override
    public boolean discover(Recipe<?> recipe) {
        if (!recipe.isSpecial()) {
            return this.discover(recipe.getId());
        }
        return false;
    }

    @Override
    public boolean isDiscovered(Recipe<?> recipe) {
        if (recipe == null) {
            return false;
        }
        return this.discovered.contains(recipe.getId());
    }
}