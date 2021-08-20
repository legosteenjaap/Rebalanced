package nl.tettelaar.rebalanced;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import nl.tettelaar.rebalanced.init.LootTables;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;
import nl.tettelaar.rebalanced.init.Recipes;

public class Rebalanced implements ModInitializer {
	public static final String modid = "rebalanced";

	public static final int timeMultiplier = 2;
	public static final Identifier PLAYER_HAS_SPAWNPOINT_ID = new Identifier(modid, "player_has_spawnpoint");

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(PLAYER_HAS_SPAWNPOINT_ID, (server, player, handler, packet, packetSender) -> {
			PacketByteBuf buf = PacketByteBufs.create();
			server.execute(() -> {
		    	ServerPlayNetworking.send(player, RebalancedClient.RECEIVE_HAS_SPAWNPOINT_ID, (PacketByteBuf) buf.writeBoolean(player.isSpawnPointSet()));
		    });
		});
		RebalancedWorldGen.init();
		Recipes.init();
		LootTables.init();
	}
}
