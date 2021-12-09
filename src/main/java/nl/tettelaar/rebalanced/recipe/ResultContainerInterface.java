package nl.tettelaar.rebalanced.recipe;

import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public interface ResultContainerInterface {

    public boolean isRecipeUnlocked();
    public Optional<Integer> getXPCost();
    public boolean isUnlockable(Player player);
}
