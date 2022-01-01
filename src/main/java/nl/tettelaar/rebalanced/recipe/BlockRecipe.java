package nl.tettelaar.rebalanced.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import nl.tettelaar.rebalanced.init.Recipes;

public class BlockRecipe implements Recipe<Container> {

    private ResourceLocation id;
    private ItemStack resultItemDisplay;

    public BlockRecipe(ResourceLocation resourceLocation, ItemStack resultItemDisplay) {
        this.id = resourceLocation;
        this.resultItemDisplay = resultItemDisplay;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return resultItemDisplay;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(Items.PISTON);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.BLOCK;
    }
}
