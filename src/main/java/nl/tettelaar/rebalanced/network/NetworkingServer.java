package nl.tettelaar.rebalanced.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import nl.tettelaar.rebalanced.Rebalanced;

public class NetworkingServer {

	public static final String modid = Rebalanced.modid;
	
	public static final ResourceLocation PLAYER_HAS_SPAWNPOINT_ID = new ResourceLocation(modid, "player_has_spawnpoint");
	
	public static void init () {
		ServerPlayNetworking.registerGlobalReceiver(PLAYER_HAS_SPAWNPOINT_ID, (server, player, handler, packet, packetSender) -> {
			server.execute(() -> {
				FriendlyByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(player.getRespawnPosition() != null);
		    	ServerPlayNetworking.send(player, NetworkingClient.RECEIVE_HAS_SPAWNPOINT_ID, buf);
		    });
		});
	}
}
