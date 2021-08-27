package nl.tettelaar.rebalanced.mixin.worldgen;

import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.BadlandsSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

@Mixin(BadlandsSurfaceBuilder.class)
public class BadlandsSurfaceBuilderMixin {

	/*
	 * @ModifyConstant(constant = @Constant(intValue = 15), slice = @Slice(from
	 * = @At(ordinal = 2, value = "HEAD")), method = "generate") private static int
	 * changeTerracottaDeepness(int original) { return 20; }
	 */

	@ModifyConstant(constant = @Constant(intValue = 15, ordinal = 2), method = "generate")
	private static int changeTerracottaDeepness(int original) {
		return 128;
	}
}
