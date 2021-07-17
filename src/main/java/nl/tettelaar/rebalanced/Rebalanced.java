package nl.tettelaar.rebalanced;

import net.fabricmc.api.ModInitializer;
import nl.tettelaar.rebalanced.init.LootTables;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;
import nl.tettelaar.rebalanced.init.Recipes;

public class Rebalanced implements ModInitializer {
	public static final String modid = "rebalanced";

	public static final int timeMultiplier = 2;
	
	@Override
	public void onInitialize() {
		RebalancedWorldGen.init();
		Recipes.init();
		LootTables.init();
	}
}
