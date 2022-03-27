package nl.tettelaar.rebalanced.mixin.sleep;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import nl.tettelaar.rebalanced.Rebalanced;

@Mixin(ClientLevel.class)
public abstract class LongerDayCycleClientMixin extends Level {

	protected LongerDayCycleClientMixin(WritableLevelData properties, ResourceKey<Level> registryRef, Holder<DimensionType> dimensionType, Supplier<ProfilerFiller> profiler, boolean isClient, boolean debugWorld, long seed) {
		super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
		// TODO Auto-generated constructor stub
	}

	@Shadow @Final List<AbstractClientPlayer> players;
	
	int timeCounter = 1;
	
	@Redirect(method = "tickTime()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z", ordinal = 0))
	private boolean changeTimeSpeed(GameRules gameRules, GameRules.Key<GameRules.BooleanValue> gameRule) {
		timeCounter++;
		if(!gameRules.getBoolean(gameRule)) return false;
		if (Rebalanced.timeMultiplier <= timeCounter && !(players.size() == 0 && this.isDay())) {
			timeCounter = 1;
			return true;
		} else {
			return false;
		}
	}
}
