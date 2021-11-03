package nl.tettelaar.rebalanced.init;

import net.fabricmc.loader.api.FabricLoader;
import nl.tettelaar.rebalanced.init.worldgen.DoBiomeModifications;

public class RebalancedWorldGen {

	public static final int mainContinentSize = 4;
	
	public static final boolean disableRiver = false;

	public static final boolean isSimplyImprovedTerrainLoaded = FabricLoader.getInstance()
			.isModLoaded("simplyimprovedterrain");

	public static final float normalRiverDepth = -0.6f;
	
	public static final float frozenRiverDepth = -0.65f;
	
	public static final float dryRiverDepth = -0.5f;

	public static final float riverScale = 0f;

	public static final float hillDepth = 0.15f;

	public static final float hillScale = isSimplyImprovedTerrainLoaded ? 1.2f : 0.6f;

	public static final float beachDepth = 0.05f;

	public static final float beachScale = 0f;
	
	public static void init() {
		DoBiomeModifications.init();
	}

	

}
