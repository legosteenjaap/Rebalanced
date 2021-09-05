package nl.tettelaar.rebalanced;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.RunArgs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
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
