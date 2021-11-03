package nl.tettelaar.rebalanced.mixin.gamerules;

import java.util.function.Function;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRules.Type.class)
public interface GameRuleTypeAccessor <T extends GameRules.Value<T>>{
	@Accessor
	Function<GameRules.Type<T>, T> getConstructor();
}
