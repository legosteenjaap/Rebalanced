package nl.tettelaar.rebalanced.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

@Mixin(LakeFeature.class)
public class DisableWaterLakeFeatureMixin {
	@Inject(method = "generate", at = @At("HEAD"), cancellable = true)
	public void generate(FeatureContext<SingleStateFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
		
		BlockPos blockPos = context.getOrigin();
	      StructureWorldAccess structureWorldAccess = context.getWorld();
	      Random random = context.getRandom();
		SingleStateFeatureConfig singleStateFeatureConfig;
		for (singleStateFeatureConfig = (SingleStateFeatureConfig) context.getConfig(); blockPos
				.getY() > structureWorldAccess.getBottomY() + 5
				&& structureWorldAccess.isAir(blockPos); blockPos = blockPos.down()) {
		}

		if (singleStateFeatureConfig.state.getMaterial() == Material.WATER) {
			cir.setReturnValue(false);
		}
	}
}
