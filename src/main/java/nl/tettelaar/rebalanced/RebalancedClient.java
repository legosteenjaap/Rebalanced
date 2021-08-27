package nl.tettelaar.rebalanced;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RebalancedClient implements ClientModInitializer {

	public static final String modid = Rebalanced.modid;
	public static final Identifier SHOW_FLOATING_ITEM_ID = new Identifier(modid, "show_floating_item");
	public static final Identifier RECEIVE_HAS_SPAWNPOINT_ID = new Identifier(modid, "receive_has_spawnpoint");
	public static final long latestRespawnTime = 10000;
	public static final long earliestRespawnTime = 22000;

	
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(SHOW_FLOATING_ITEM_ID, (client, handler, buf, responseSender) -> {
		    ItemStack item = buf.readItemStack();
			client.execute(() -> {
		    	client.gameRenderer.showFloatingItem(item);
		    });
		});
	}

	
	
}
