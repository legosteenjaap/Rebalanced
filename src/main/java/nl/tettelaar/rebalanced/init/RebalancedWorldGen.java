package nl.tettelaar.rebalanced.init;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import nl.tettelaar.rebalanced.init.worldgen.DoBiomeModifications;

public class RebalancedWorldGen {

	public static final ResourceKey<NormalNoise.NoiseParameters> CAVE_LIMITER = createKey("cave_limiter");

	private static ResourceKey<NormalNoise.NoiseParameters> createKey(String string) {
		return ResourceKey.create(Registry.NOISE_REGISTRY, new ResourceLocation(string));
	}

	public static void init() {
		DoBiomeModifications.init();
	}

	

}
