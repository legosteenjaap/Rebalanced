package nl.tettelaar.rebalanced.init;

import net.fabricmc.loader.api.FabricLoader;
import nl.tettelaar.rebalanced.init.worldgen.DoBiomeModifications;

public class RebalancedWorldGen {
	
	public static void init() {
		DoBiomeModifications.init();
	}

	

}
