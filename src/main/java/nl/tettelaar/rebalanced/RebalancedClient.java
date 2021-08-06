package nl.tettelaar.rebalanced;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RebalancedClient implements ClientModInitializer {

	public static final String modid = Rebalanced.modid;
	public static final Identifier SHOW_FLOATING_ITEM_ID = new Identifier(modid, "show_floating_item");
	
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(SHOW_FLOATING_ITEM_ID, (client, handler, buf, responseSender) -> {
		    ItemStack item = buf.readItemStack();
			client.execute(() -> {
		    	System.out.println("test");
		    	client.gameRenderer.showFloatingItem(item);
		    });
		});
	}

	
	
}
