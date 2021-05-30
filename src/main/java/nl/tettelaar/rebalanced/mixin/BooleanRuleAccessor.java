package nl.tettelaar.rebalanced.mixin;

import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;

@Mixin(GameRules.BooleanRule.class)
public interface BooleanRuleAccessor {
	
	@Invoker("create")
	public static GameRules.Type<GameRules.BooleanRule> create(boolean initialValue, BiConsumer<MinecraftServer, GameRules.BooleanRule> changeCallback) {
        throw new AssertionError();
     }
}
