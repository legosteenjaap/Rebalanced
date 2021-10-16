package nl.tettelaar.rebalanced.mixin;

import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.GameRules;

@Mixin(GameRules.Type.class)
public interface GameRuleTypeAccessor <T extends GameRules.Rule<T>>{
	@Accessor
	Function<GameRules.Type<T>, T> getRuleFactory();
}
