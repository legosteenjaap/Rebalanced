package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.world.gen.surfacebuilder.WoodedBadlandsSurfaceBuilder;

@Mixin(WoodedBadlandsSurfaceBuilder.class)
public abstract class WoodedBadlandsSurfaceBuilderMixin {
	@ModifyConstant(constant = @Constant(intValue = 86), method = "generate")
	private static int changeGrassHeight(int original) {
		return 193;
	}

	@ModifyConstant(constant = @Constant(intValue = 15, ordinal = 2), method = "generate")
	private static int changeTerracottaDeepness(int original) {
		return 128;
	}
}