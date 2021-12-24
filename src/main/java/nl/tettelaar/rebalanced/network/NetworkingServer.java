package nl.tettelaar.rebalanced.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;
import nl.tettelaar.rebalanced.Rebalanced;

public class NetworkingServer {

	public static final String modid = Rebalanced.modid;
	
	public static final ResourceLocation RETURN_PLAYER_HAS_SPAWNPOINT_ID = new ResourceLocation(modid, "return_player_has_spawnpoint");
	public static final ResourceLocation RETURN_DO_LIMITEDCRAFTING_ID = new ResourceLocation(modid, "return_do_limitedcrafting");


	public static void init () {
		ServerPlayNetworking.registerGlobalReceiver(NetworkingClient.PLAYER_HAS_SPAWNPOINT_ID, (server, player, handler, packet, packetSender) -> {
			server.execute(() -> {
				FriendlyByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(player.getRespawnPosition() != null);
		    	ServerPlayNetworking.send(player, RETURN_PLAYER_HAS_SPAWNPOINT_ID, buf);
		    });
		});

		ServerPlayNetworking.registerGlobalReceiver(NetworkingClient.DO_LIMITEDCRAFTING_ID, (server, player, handler, packet, packetSender) -> {
			server.execute(() -> {
				FriendlyByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(player.getLevel().getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING));
				ServerPlayNetworking.send(player, RETURN_DO_LIMITEDCRAFTING_ID, buf);
			});
		});


	}
}
