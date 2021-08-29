package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.MergingLayer;
import nl.tettelaar.rebalanced.init.worldgen.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.AddHillsLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.layer.util.NorthWestCoordinateTransformer;

@Mixin(AddHillsLayer.class)
public abstract class AddHillsLayerMixin implements MergingLayer, NorthWestCoordinateTransformer {

	private static final int plateauValleyChance = 18;

	@Inject(method = "sample", at = @At("HEAD"), cancellable = true)
	public void sampleHead(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z, CallbackInfoReturnable<Integer> cir) {

		int i = sampler1.sample(this.transformX(x + 1), this.transformZ(z + 1));

		//REMOVE RANDOM SNOWY MOUNTAINS
		if (i == BiomeIds.SNOWY_TUNDRA) cir.setReturnValue(i);

		//REMOVE RANDOM ISLANDS
		if (i == BiomeIds.DEEP_OCEAN || i == BiomeIds.DEEP_LUKEWARM_OCEAN || i == BiomeIds.DEEP_COLD_OCEAN || i == BiomeIds.DEEP_FROZEN_OCEAN)
			cir.setReturnValue(i);

		if (i == BiomeIds.SAVANNA) cir.setReturnValue(context.nextInt(2) == 0 ? BiomeIds.SAVANNA : BuiltinRegistries.BIOME.getRawId(Biomes.SAVANNA_HILLS));

		if ((i == BiomeIds.BADLANDS_PLATEAU || i == BiomeIds.WOODED_BADLANDS_PLATEAU) && context.nextInt(plateauValleyChance) != 0) cir.setReturnValue(i);

		if (i == BiomeIds.SAVANNA_PLATEAU && context.nextInt(plateauValleyChance) == 0) cir.setReturnValue(BiomeIds.SAVANNA);

		if (i == BuiltinRegistries.BIOME.getRawId(Biomes.JUNGLE_PLATEAU) && context.nextInt(plateauValleyChance) == 0) cir.setReturnValue(BiomeIds.JUNGLE);
	}

}