package nl.tettelaar.rebalanced.gen.surfacebuilders;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class GravellySurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	public GravellySurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
		super(codec);
	}

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int i, long l,
			TernarySurfaceConfig surfaceConfig) {
		
		SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, i, l,
				SurfaceBuilder.GRAVEL_CONFIG);
		// TODO Auto-generated method stub
		
	}
}