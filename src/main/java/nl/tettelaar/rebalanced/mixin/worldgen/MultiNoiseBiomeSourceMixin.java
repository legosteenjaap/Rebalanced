package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.ImmutableList;

import net.minecraft.world.biome.source.MultiNoiseBiomeSource;

@Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin {

	   @Shadow @Final @Mutable private static MultiNoiseBiomeSource.NoiseParameters DEFAULT_NOISE_PARAMETERS = new MultiNoiseBiomeSource.NoiseParameters(-11, ImmutableList.of(1.0D, 1.0D));

	
}
