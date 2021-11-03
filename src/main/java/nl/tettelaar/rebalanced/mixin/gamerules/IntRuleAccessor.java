package nl.tettelaar.rebalanced.mixin.gamerules;

import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;

@Mixin(GameRules.IntegerValue.class)
public interface IntRuleAccessor {

	@Invoker("create")
	public static GameRules.Type<GameRules.IntegerValue> create(int initialValue, BiConsumer<MinecraftServer, GameRules.IntegerValue> changeCallback) {
		throw new AssertionError();
    }
	
}
