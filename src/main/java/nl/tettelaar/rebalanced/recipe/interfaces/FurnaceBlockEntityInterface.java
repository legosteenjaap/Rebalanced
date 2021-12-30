package nl.tettelaar.rebalanced.recipe.interfaces;

import net.minecraft.world.entity.player.Player;

public interface FurnaceBlockEntityInterface {

    public void setOwner(Player player);
    public Player getOwner();
    public boolean isCookingRecipe();
}
