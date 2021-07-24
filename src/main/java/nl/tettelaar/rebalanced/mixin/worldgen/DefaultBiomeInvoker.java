package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

@Mixin(DefaultBiomeCreator.class)
public interface DefaultBiomeInvoker {
	@Invoker("createJungle")
	public static Biome invokeCreateJungle (float depth, float scale, int parrotWeight, int parrotMaxGroupSize, int ocelotMaxGroupSize) {
		throw new AssertionError();
	}
	
	@Invoker("createNormalBadlands")
	public static Biome invokeCreateNormalBadlands (float depth, float scale, boolean bl) {
		throw new AssertionError();
	}
	@Invoker("createBeach")
	public static Biome invokeCreateBeach(float depth, float scale, float temperature, float downfall, int waterColor, boolean snowy, boolean stony) {
		throw new AssertionError();
	}
	
	@Invoker("createBadlands")
	public static Biome  createBadlands(ConfiguredSurfaceBuilder<TernarySurfaceConfig> configuredSurfaceBuilder, float depth, float scale, boolean bl, boolean bl2) {
		throw new AssertionError();
	}
	
	@Invoker("getSkyColor")
	public static int invokeGetSkyColor(float temperature) {
		throw new AssertionError();
	}
	@Invoker("createJungleFeatures")
	public static Biome invokeCreateJungleFeatures (float depth, float scale, float downfall, boolean bamboo, boolean edge, boolean modified, SpawnSettings.Builder builder) {
		throw new AssertionError();
	}
}
