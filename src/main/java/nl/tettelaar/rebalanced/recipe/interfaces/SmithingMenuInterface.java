package nl.tettelaar.rebalanced.recipe.interfaces;

import java.util.Optional;

public interface SmithingMenuInterface {

    public boolean canUseRecipe();
    public boolean isUnlockable();
    public Optional<Integer> getXPCost();

}
