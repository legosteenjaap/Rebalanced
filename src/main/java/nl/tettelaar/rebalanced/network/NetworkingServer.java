package nl.tettelaar.rebalanced.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import nl.tettelaar.rebalanced.Rebalanced;

public class NetworkingServer {

	public static final String modid = Rebalanced.modid;
	
	public static final Identifier PLAYER_HAS_SPAWNPOINT_ID = new Identifier(modid, "player_has_spawnpoint");
	
	public static void init () {
		ServerPlayNetworking.registerGlobalReceiver(PLAYER_HAS_SPAWNPOINT_ID, (server, player, handler, packet, packetSender) -> {
			server.execute(() -> {
				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(player.getSpawnPointPosition() != null);
		    	ServerPlayNetworking.send(player, NetworkingClient.RECEIVE_HAS_SPAWNPOINT_ID, buf);
		    });
		});
	}
}
