package nl.tettelaar.rebalanced.mixin.worldgen;

import java.util.Optional;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;

@Mixin(MultiNoiseBiomeSource.Preset.class)
public class NetherBiomeMixin {
	
	@Shadow @Final @Mutable public static MultiNoiseBiomeSource.Preset NETHER = new MultiNoiseBiomeSource.Preset(new Identifier("nether"), (preset, biomeRegistry, seed) -> {
        return MultiNoiseBiomeSourceAccessor.createMultiNoiseBiomeSource(seed, ImmutableList.of(Pair.of(new Biome.MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.2F), () -> {
           return (Biome)biomeRegistry.getOrThrow(BiomeKeys.NETHER_WASTES);
        }), Pair.of(new Biome.MixedNoisePoint(-0.5F, -0.2F, 0.2F, 0.2F, 0F), () -> {
           return (Biome)biomeRegistry.getOrThrow(BiomeKeys.SOUL_SAND_VALLEY);
        }), Pair.of(new Biome.MixedNoisePoint(0.2F, 0.1F, 0.5F, 0.3F, 0F), () -> {
           return (Biome)biomeRegistry.getOrThrow(BiomeKeys.CRIMSON_FOREST);
        }), Pair.of(new Biome.MixedNoisePoint(0.3F, 0.2F, 0.7F, 0.6F, 0.2F), () -> {
           return (Biome)biomeRegistry.getOrThrow(BiomeKeys.WARPED_FOREST);
        }), Pair.of(new Biome.MixedNoisePoint(0.2F, 0.5F, -0.5F, -0.2F, 0F), () -> {
           return (Biome)biomeRegistry.getOrThrow(BiomeKeys.BASALT_DELTAS);
        })), Optional.of(Pair.of(biomeRegistry, preset)));
     });
	
	
}
