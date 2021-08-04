package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;

@Mixin(VanillaLayeredBiomeSource.class)
public class VanillaLayeredBiomeSourceMixin {
	
	@ModifyConstant(constant = @Constant(intValue = 6), method = "<init>")
	private static int largeBiomes(int original) {
		return 1;
	}
	
	@ModifyConstant(constant = @Constant(intValue = 4), method = "<init>")
	private static int normalBiomes(int original) {
		return 6;
	}
	
}
