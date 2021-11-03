package nl.tettelaar.rebalanced;

import net.fabricmc.api.ClientModInitializer;
import nl.tettelaar.rebalanced.network.NetworkingClient;

public class RebalancedClient implements ClientModInitializer {

	public static final String modid = Rebalanced.modid;

	public static final long latestRespawnTime = 10000;
	public static final long earliestRespawnTime = 22000;

	@Override
	public void onInitializeClient() {
		NetworkingClient.init();
	}

	
	
}
