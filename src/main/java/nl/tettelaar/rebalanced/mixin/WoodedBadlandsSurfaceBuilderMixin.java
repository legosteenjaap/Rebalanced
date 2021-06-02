package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.WoodedBadlandsSurfaceBuilder;

@Mixin(WoodedBadlandsSurfaceBuilder.class)
public abstract class WoodedBadlandsSurfaceBuilderMixin {
	@ModifyConstant(constant = @Constant(intValue = 86), method = "generate")
	private static int generate(int original) {
		return 193;
	}
}