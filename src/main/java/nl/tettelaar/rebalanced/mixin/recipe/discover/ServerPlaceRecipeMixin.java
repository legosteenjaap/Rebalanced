package nl.tettelaar.rebalanced.mixin.recipe.discover;

import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.recipe.RecipeBookInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlaceRecipe.class)
public abstract class ServerPlaceRecipeMixin <C extends Container> implements PlaceRecipe<Integer> {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final StackedContents stackedContents = new StackedContents();
    protected Inventory inventory;
    protected RecipeBookMenu<C> menu;

    private static boolean isUnlockedOrDiscovered(Recipe<?> recipe, ServerPlayer serverPlayer) {
        RecipeBook recipeBook = serverPlayer.getRecipeBook();
        RecipeBookInterface recipeBookInterface = (RecipeBookInterface) (Object)serverPlayer.getRecipeBook();
        return recipeBook.contains(recipe) || recipeBookInterface.isDiscovered(recipe);
    }

    @Overwrite
    public void recipeClicked(ServerPlayer serverPlayer, @Nullable Recipe<C> recipe, boolean bl) {
        if (recipe == null || !isUnlockedOrDiscovered(recipe,serverPlayer)){
            return;
        }
        this.inventory = serverPlayer.getInventory();
        if (!this.testClearGrid() && !serverPlayer.isCreative()) {
            return;
        }
        this.stackedContents.clear();
        serverPlayer.getInventory().fillStackedContents(this.stackedContents);
        this.menu.fillCraftSlotsStackedContents(this.stackedContents);
        if (this.stackedContents.canCraft(recipe, null)) {
            this.handleRecipeClicked(recipe, bl);
        } else {
            this.clearGrid(true);
            serverPlayer.connection.send(new ClientboundPlaceGhostRecipePacket(serverPlayer.containerMenu.containerId, recipe));
        }
        serverPlayer.getInventory().setChanged();
    }

    @Shadow
    protected void handleRecipeClicked(Recipe<C> recipe, boolean bl) {
    }

    @Shadow
    protected void clearGrid(boolean bl) {
    }

    @Shadow
    private boolean testClearGrid() {
        throw new AssertionError();
    }
}
