package nl.tettelaar.rebalanced;

import java.util.Iterator;

import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanRule;
import net.minecraft.world.GameRules.IntRule;
import net.minecraft.world.GameRules.Type;
import nl.tettelaar.rebalanced.mixin.gamerules.BooleanRuleAccessor;
import nl.tettelaar.rebalanced.mixin.gamerules.IntRuleAccessor;

public class ChangedGameRules {
	public static final Type<BooleanRule> REDUCED_DEBUG_INFO_TYPE = BooleanRuleAccessor.create(true, (server, rule) -> {
		byte b = (byte) (rule.get() ? 22 : 23);
		Iterator<ServerPlayerEntity> var3 = server.getPlayerManager().getPlayerList().iterator();

		while (var3.hasNext()) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) var3.next();
			serverPlayerEntity.networkHandler
					.sendPacket(new EntityStatusS2CPacket(serverPlayerEntity, (byte) b));
		}

	});;
	
	public static final Type<BooleanRule> DO_LIMITED_CRAFTING_TYPE = create(true);
	
	public static final Type<IntRule> SPAWN_RADIUS_TYPE = create(500);
	
	public static final Type<BooleanRule> ANNOUNCE_ADVANCEMENTS_TYPE = create(false);
	
	private static GameRules.Type<GameRules.BooleanRule> create(boolean initialValue) {
		return BooleanRuleAccessor.create(initialValue, (server, rule) -> {
        });
    }
	
	private static GameRules.Type<GameRules.IntRule> create(int initialValue) {
        return IntRuleAccessor.create(initialValue, (server, rule) -> {
        });
     }
}
