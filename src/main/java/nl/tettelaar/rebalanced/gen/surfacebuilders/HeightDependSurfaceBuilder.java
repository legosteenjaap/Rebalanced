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

public class HeightDependSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

	private final int minHeight;

	public HeightDependSurfaceBuilder(Codec<TernarySurfaceConfig> codec, int minHeight) {
		super(codec);
		this.minHeight = minHeight;
	}

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int i, long l,
			TernarySurfaceConfig surfaceConfig) {
		// TODO Auto-generated method stub
		int y = height - 1;
		while (chunk.getBlockState(new BlockPos(x, y, z)) == Blocks.WATER.getDefaultState()) {
			y--;
		}
		y++;
		if (height > this.minHeight + (int)(noise * 2)) {
			chunk.setBlockState(new BlockPos(x, y, z), Blocks.GRASS_BLOCK.getDefaultState(), false);
		} else {
			chunk.setBlockState(new BlockPos(x, y, z), Blocks.STONE.getDefaultState(), false);
		}
		
	}

}
