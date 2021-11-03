package nl.tettelaar.rebalanced.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import nl.tettelaar.rebalanced.Rebalanced;

public class NetworkingClient {

	public static final String modid = Rebalanced.modid;
	
	public static final ResourceLocation SHOW_FLOATING_ITEM_ID = new ResourceLocation(modid, "show_floating_item");
	public static final ResourceLocation RECEIVE_HAS_SPAWNPOINT_ID = new ResourceLocation(modid, "receive_has_spawnpoint");
	
	public static Boolean hasSpawnPoint = null;
	
	public static void init () {
		ClientPlayNetworking.registerGlobalReceiver(SHOW_FLOATING_ITEM_ID, (client, handler, buf, responseSender) -> {
		    ItemStack item = buf.readItem();
			client.execute(() -> {
		    	client.gameRenderer.displayItemActivation(item);
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
