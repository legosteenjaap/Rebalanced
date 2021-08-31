package nl.tettelaar.rebalanced.init;

import net.fabricmc.loader.api.FabricLoader;
import nl.tettelaar.rebalanced.gen.Features;
import nl.tettelaar.rebalanced.init.worldgen.Biomes;
import nl.tettelaar.rebalanced.init.worldgen.DoBiomeModifications;
import nl.tettelaar.rebalanced.init.worldgen.SurfaceBuilders;

public class RebalancedWorldGen {

	public static final int mainContinentSize = 4;
	
	public static final boolean disableRiver = false;

	public static final boolean isSimplyImprovedTerrainLoaded = FabricLoader.getInstance()
			.isModLoaded("simplyimprovedterrain");

	public static final float normalRiverDepth = -0.6f;
	
	public static final float frozenRiverDepth = -0.5f;
	
	public static final float dryRiverDepth = -0.4f;

	public static final float riverScale = 0f;

	public static final float hillDepth = 0.11f;

	public static final float hillScale = isSimplyImprovedTerrainLoaded ? 1.2f : 0.6f;

	public static final float beachDepth = 0.05f;

	public static final float beachScale = 0f;
	
	public static void init() {
		Features.init();
		SurfaceBuilders.init();
		Biomes.init();
		DoBiomeModifications.init();
	}

	

}
