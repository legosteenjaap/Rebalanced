package nl.tettelaar.rebalanced.mixin;

import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;

@Mixin(GameRules.IntRule.class)
public interface IntRuleAccessor {

	@Invoker("create")
	public static GameRules.Type<GameRules.IntRule> create(int initialValue, BiConsumer<MinecraftServer, GameRules.IntRule> changeCallback) {
		throw new AssertionError();
    }
	
}
