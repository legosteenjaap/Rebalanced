package nl.tettelaar.rebalanced.mixin.gamerules;

import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;

@Mixin(GameRules.BooleanValue.class)
public interface BooleanRuleAccessor {
	
	@Invoker("create")
	public static GameRules.Type<GameRules.BooleanValue> create(boolean initialValue, BiConsumer<MinecraftServer, GameRules.BooleanValue> changeCallback) {
        throw new AssertionError();
     }
}
