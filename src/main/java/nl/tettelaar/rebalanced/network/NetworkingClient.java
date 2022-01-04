package nl.tettelaar.rebalanced.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.Rebalanced;
import org.lwjgl.system.CallbackI;

import java.util.Optional;

public class NetworkingClient {

	public static final String modid = Rebalanced.modid;
	
	public static final ResourceLocation SHOW_FLOATING_ITEM_ID = new ResourceLocation(modid, "show_floating_item");
	public static final ResourceLocation PLAYER_HAS_SPAWNPOINT_ID = new ResourceLocation(modid, "player_has_spawnpoint");
	public static final ResourceLocation DO_LIMITEDCRAFTING_ID = new ResourceLocation(modid, "do_limitedcrafting");
	public static final ResourceLocation REMOVE_FURNACE_INSPECT = new ResourceLocation(modid, "remove_furnace_inspect");

	public static BlockPos lastFurnacePos = null;

	public static Boolean hasSpawnPoint = null;
	public static Boolean doLimitedCrafting = true;
	private static Recipe<?> furnaceRecipe = null;

	public static void init () {
		ClientPlayNetworking.registerGlobalReceiver(SHOW_FLOATING_ITEM_ID, (client, handler, buf, responseSender) -> {
		    ItemStack item = buf.readItem();
			client.execute(() -> {
		    	client.gameRenderer.displayItemActivation(item);
		    });
		});
		
		ClientPlayNetworking.registerGlobalReceiver(NetworkingServer.RETURN_PLAYER_HAS_SPAWNPOINT_ID, (client, handler, buf, responseSender) -> {
			boolean hasSpawnPoint = buf.readBoolean();
			client.execute(() -> {
		    	NetworkingClient.hasSpawnPoint = hasSpawnPoint;
		    });
		});

		ClientPlayNetworking.registerGlobalReceiver(NetworkingServer.RETURN_DO_LIMITEDCRAFTING_ID, (client, handler, buf, responseSender) -> {
			boolean doLimitedCrafting = buf.readBoolean();
			client.execute(() -> {
				NetworkingClient.doLimitedCrafting = doLimitedCrafting;
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(NetworkingServer.FURNACE_RECIPE, (client, handler, buf, responseSender) -> {
			Optional<? extends Recipe<?>> recipe;
			if (buf.readBoolean()) {
				ResourceLocation furnaceRecipe = buf.readResourceLocation();
				recipe = client.player.level.getRecipeManager().byKey(furnaceRecipe);
			} else {
				recipe = Optional.empty();
			}
			client.execute(() -> {
				NetworkingClient.furnaceRecipe = recipe.orElse(null);
			});
		});


	}

	public static Recipe<?> getFurnaceRecipe() {
		return furnaceRecipe;
	}
	
}
