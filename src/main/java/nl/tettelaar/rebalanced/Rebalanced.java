package nl.tettelaar.rebalanced;

import net.fabricmc.api.ModInitializer;
import nl.tettelaar.rebalanced.init.LootTables;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;
import nl.tettelaar.rebalanced.init.Recipes;
import nl.tettelaar.rebalanced.network.NetworkingServer;

public class Rebalanced implements ModInitializer {
	public static final String modid = "rebalanced";

	public static final int timeMultiplier = 1;

	@Override
	public void onInitialize() {
		NetworkingServer.init();
		RebalancedWorldGen.init();
		Recipes.init();
		LootTables.init();
	}
}
