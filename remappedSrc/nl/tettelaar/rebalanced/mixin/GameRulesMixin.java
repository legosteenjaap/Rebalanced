package nl.tettelaar.rebalanced.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Type;
import nl.tettelaar.rebalanced.ChangedGameRules;

@Mixin(GameRules.class)
public class GameRulesMixin  {

	

	@Shadow
	@Final
	private static Map<GameRules.Key<?>, GameRules.Type<?>> RULE_TYPES;

	@Inject(method = "register", at = @At("HEAD"), cancellable = true)
	private static <T extends GameRules.Rule<T>> void register(String name, GameRules.Category category,
			GameRules.Type<T> type, CallbackInfoReturnable cir) {
		switch (name) {
		case "reducedDebugInfo":
			type = (Type<T>) ChangedGameRules.REDUCED_DEBUG_INFO_TYPE;
			break;
		case "doLimitedCrafting":
			type = (Type<T>) ChangedGameRules.DO_LIMITED_CRAFTING_TYPE;
			break;
		case "spawnRadius":
			type = (Type<T>) ChangedGameRules.SPAWN_RADIUS_TYPE;
			break;
		case "announceAdvancements":
			type = (Type<T>) ChangedGameRules.ANNOUNCE_ADVANCEMENTS_TYPE;
			break;
		}
		
		GameRules.Key<T> key = new GameRules.Key(name, category);
		GameRules.Type<?> type2 = (GameRules.Type) RULE_TYPES.put(key, type);
		if (type2 != null) {
			throw new IllegalStateException("Duplicate game rule registration for " + name);
		} else {
			cir.setReturnValue(key);
		}
		
	}
	@Shadow
	private static <T extends GameRules.Rule<T>> GameRules.Key<T> register(String name, GameRules.Category category,
			GameRules.Type<T> type) {
		GameRules.Key<T> key = new GameRules.Key(name, category);
		GameRules.Type<?> type2 = (GameRules.Type) RULE_TYPES.put(key, type);
		if (type2 != null) {
			throw new IllegalStateException("Duplicate game rule registration for " + name);
		} else {
			return key;
		}

	}

}
