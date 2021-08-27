package nl.tettelaar.rebalanced.mixin.foliage;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

@Mixin(OakSaplingGenerator.class)
public abstract class OakSaplingGeneratorMixin extends SaplingGenerator {

	@Override
	public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
		if (world.getBiome(pos).getCategory() == Biome.Category.SWAMP) {
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NO_REDRAW);
			if (ConfiguredFeatures.SWAMP_OAK.generate(world, chunkGenerator, random, pos)) {
				return true;
			} else {
				world.setBlockState(pos, state, Block.NO_REDRAW);
				return false;
			}
		}
		return super.generate(world, chunkGenerator, pos, state, random);
	}

}
