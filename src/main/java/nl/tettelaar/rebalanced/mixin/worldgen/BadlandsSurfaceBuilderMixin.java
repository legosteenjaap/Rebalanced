package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.gen.surfacebuilder.BadlandsSurfaceBuilder;

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
