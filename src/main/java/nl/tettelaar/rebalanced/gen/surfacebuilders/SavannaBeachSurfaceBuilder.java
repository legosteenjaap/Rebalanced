package nl.tettelaar.rebalanced.gen.surfacebuilders;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class SavannaBeachSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	public SavannaBeachSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
		super(codec);
	}



	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int i, long l,
			TernarySurfaceConfig surfaceConfig) {
		// TODO Auto-generated method stub
		if (noise > 2.5D) {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, i, l,
					SurfaceBuilder.GRASS_CONFIG);
		} else {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, i, l,
					SurfaceBuilder.SAND_CONFIG);
		}
	}
}