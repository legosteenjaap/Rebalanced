package nl.tettelaar.rebalanced;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RebalancedClient implements ClientModInitializer {

	public static final String modid = Rebalanced.modid;

	public static final long latestRespawnTime = 10000;
	public static final long earliestRespawnTime = 22000;

	@Override
	public void onInitializeClient() {
		
	}

	
	
}
