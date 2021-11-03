package nl.tettelaar.rebalanced;

import java.util.Iterator;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Type;
import nl.tettelaar.rebalanced.mixin.gamerules.BooleanRuleAccessor;
import nl.tettelaar.rebalanced.mixin.gamerules.IntRuleAccessor;

public class ChangedGameRules {
	public static final Type<BooleanValue> REDUCED_DEBUG_INFO_TYPE = BooleanRuleAccessor.create(true, (server, rule) -> {
		byte b = (byte) (rule.get() ? 22 : 23);
		Iterator<ServerPlayer> var3 = server.getPlayerList().getPlayers().iterator();

		while (var3.hasNext()) {
			ServerPlayer serverPlayerEntity = (ServerPlayer) var3.next();
			serverPlayerEntity.connection
					.send(new ClientboundEntityEventPacket(serverPlayerEntity, (byte) b));
		}

	});;
	
	public static final Type<BooleanValue> DO_LIMITED_CRAFTING_TYPE = create(true);
	
	public static final Type<IntegerValue> SPAWN_RADIUS_TYPE = create(500);
	
	public static final Type<BooleanValue> ANNOUNCE_ADVANCEMENTS_TYPE = create(false);
	
	private static GameRules.Type<GameRules.BooleanValue> create(boolean initialValue) {
		return BooleanRuleAccessor.create(initialValue, (server, rule) -> {
        });
    }
	
	private static GameRules.Type<GameRules.IntegerValue> create(int initialValue) {
        return IntRuleAccessor.create(initialValue, (server, rule) -> {
        });
     }
}
