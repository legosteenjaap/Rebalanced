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

public class GravellySurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	public GravellySurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
		super(codec);
	}

	public void generate(Random random, Chunk chunk, Biome biome, int i, int j, int k, double d, BlockState blockState,
			BlockState blockState2, int l, long m, TernarySurfaceConfig ternarySurfaceConfig) {

		SurfaceBuilder.DEFAULT.generate(random, chunk, biome, i, j, k, d, blockState, blockState2, l, m,
				SurfaceBuilder.GRAVEL_CONFIG);

	}
}