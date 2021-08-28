package nl.tettelaar.rebalanced.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import nl.tettelaar.rebalanced.Rebalanced;

public class NetworkingClient {

	public static final String modid = Rebalanced.modid;
	
	public static final Identifier SHOW_FLOATING_ITEM_ID = new Identifier(modid, "show_floating_item");
	public static final Identifier RECEIVE_HAS_SPAWNPOINT_ID = new Identifier(modid, "receive_has_spawnpoint");
	
	public static Boolean hasSpawnPoint = null;
	
	public static void init () {
		ClientPlayNetworking.registerGlobalReceiver(SHOW_FLOATING_ITEM_ID, (client, handler, buf, responseSender) -> {
		    ItemStack item = buf.readItemStack();
			client.execute(() -> {
		    	client.gameRenderer.showFloatingItem(item);
		    });
		});
		
		ClientPlayNetworking.registerGlobalReceiver(RECEIVE_HAS_SPAWNPOINT_ID, (client, handler, buf, responseSender) -> {
			boolean hasSpawnPoint = buf.readBoolean();
			client.execute(() -> {
		    	NetworkingClient.hasSpawnPoint = hasSpawnPoint;
		    });
		});
	}
	
}
