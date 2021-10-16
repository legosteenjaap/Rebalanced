package nl.tettelaar.rebalanced.gen.surfacebuilders;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class HeightDependSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

	private final int minHeight;

	public HeightDependSurfaceBuilder(Codec<TernarySurfaceConfig> codec, int minHeight) {
		super(codec);
		this.minHeight = minHeight;
	}

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed,
			TernarySurfaceConfig surfaceBlocks) {
		if (height > this.minHeight) {
			chunk.setBlockState(new BlockPos(x, height, z), Blocks.GRASS_BLOCK.getDefaultState(), false);
		}
	}

}
