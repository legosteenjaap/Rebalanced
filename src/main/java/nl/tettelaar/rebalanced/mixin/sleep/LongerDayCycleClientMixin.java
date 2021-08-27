package nl.tettelaar.rebalanced.mixin.sleep;

import java.util.List;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanRule;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import nl.tettelaar.rebalanced.Rebalanced;

@Mixin(ClientWorld.class)
public abstract class LongerDayCycleClientMixin extends World {

	protected LongerDayCycleClientMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
		super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
		// TODO Auto-generated constructor stub
	}

	@Shadow @Final List<AbstractClientPlayerEntity> players;
	
	int timeCounter = 1;
	
	@Redirect(method = "tickTime()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z", ordinal = 0))
	private boolean changeTimeSpeed(GameRules gameRules, GameRules.Key<BooleanRule> gameRule) {
		timeCounter++;
		if (Rebalanced.timeMultiplier <= timeCounter && !(players.size() == 0 && this.isDay())) {
			timeCounter = 1;
			return true;
		} else {
			return false;
		}
	}
}
