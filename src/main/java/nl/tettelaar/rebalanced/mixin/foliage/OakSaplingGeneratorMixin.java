package nl.tettelaar.rebalanced.mixin.foliage;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OakTreeGrower.class)
public abstract class OakSaplingGeneratorMixin extends AbstractTreeGrower {

	@Override
	public boolean growTree(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
		if (world.getBiome(pos).getBiomeCategory() == Biome.BiomeCategory.SWAMP) {
			world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
			if (TreeFeatures.SWAMP_OAK.place(world, chunkGenerator, random, pos)) {
				return true;
			} else {
				world.setBlock(pos, state, Block.UPDATE_INVISIBLE);
				return false;
			}
		}
		return super.growTree(world, chunkGenerator, pos, state, random);
	}

}
