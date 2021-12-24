package nl.tettelaar.rebalanced.recipe;

import net.minecraft.world.entity.player.Player;

public interface FurnaceBlockEntityInterface {

    public void setOwner(Player player);
    public Player getOwner();
}
