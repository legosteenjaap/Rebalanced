package nl.tettelaar.rebalanced.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
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
        return Recipes.BLOCK_RECIPE;
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.BLOCK;
    }

    public static class Serializer
            implements RecipeSerializer<BlockRecipe> {

        @Override
        public BlockRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            ItemStack displayItem = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "displayItem"));
            return new BlockRecipe(resourceLocation, displayItem);
        }

        @Override
        public BlockRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            ItemStack displayItem = friendlyByteBuf.readItem();
            return new BlockRecipe(resourceLocation, displayItem);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, BlockRecipe recipe) {
            friendlyByteBuf.writeItem(recipe.resultItemDisplay);
        }
    }

}
