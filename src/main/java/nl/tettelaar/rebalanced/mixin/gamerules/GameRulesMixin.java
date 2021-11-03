package nl.tettelaar.rebalanced.mixin.gamerules;

import java.util.Map;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import nl.tettelaar.rebalanced.ChangedGameRules;

@Mixin(GameRules.class)
public class GameRulesMixin  {

	

	@Shadow
	@Final
	private static Map<GameRules.Key<?>, GameRules.Type<?>> GAME_RULE_TYPES;

	@Inject(method = "register", at = @At("HEAD"), cancellable = true)
	private static <T extends GameRules.Value<T>> void register(String name, GameRules.Category category,
			GameRules.Type<?> type, CallbackInfoReturnable<Key<?>> cir) {
		switch (name) {
		case "reducedDebugInfo":
			type = ChangedGameRules.REDUCED_DEBUG_INFO_TYPE;
			break;
		case "doLimitedCrafting":
			type = ChangedGameRules.DO_LIMITED_CRAFTING_TYPE;
			break;
		case "spawnRadius":
			type = ChangedGameRules.SPAWN_RADIUS_TYPE;
			break;
		case "announceAdvancements":
			type = ChangedGameRules.ANNOUNCE_ADVANCEMENTS_TYPE;
			break;
		}
		
		GameRules.Key<T> key = new Key<T>(name, category);
		GameRules.Type<?> type2 = (Type<?>) GAME_RULE_TYPES.put(key, type);
		if (type2 != null) {
			throw new IllegalStateException("Duplicate game rule registration for " + name);
		} else {
			cir.setReturnValue(key);
		}
		
	}
	
	@Shadow
	private static <T extends GameRules.Value<T>> GameRules.Key<T> register(String name, GameRules.Category category,
			GameRules.Type<?> type) {
		GameRules.Key<T> key = new Key<T>(name, category);
		Type<?> type2 = (Type<?>) GAME_RULE_TYPES.put(key, type);
		if (type2 != null) {
			throw new IllegalStateException("Duplicate game rule registration for " + name);
		} else {
			return key;
		}

	}

}
