package nl.tettelaar.rebalanced.mixin.recipe.discover;

import com.google.common.collect.Sets;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.recipe.RecipeBookInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(RecipeBook.class)
public class RecipeBookMixin implements RecipeBookInterface {



    @Shadow @Final
    protected Set<ResourceLocation> known = Sets.newHashSet();


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
    public void discover(ResourceLocation resourceLocation) {
        this.discovered.add(resourceLocation);
        this.known.remove(resourceLocation);
    }

    @Override
    public void discover(Recipe<?> recipe) {
        if (!recipe.isSpecial()) {
            this.discover(recipe.getId());
        }
    }

    @Override
    public boolean isDiscovered(Recipe<?> recipe) {
        if (recipe == null) {
            return false;
        }
        return this.discovered.contains(recipe.getId());
    }
}