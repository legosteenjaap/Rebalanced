package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.AddEdgeBiomesLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import nl.tettelaar.rebalanced.init.worldgen.Biomes;

@Mixin(AddEdgeBiomesLayer.class)
public class AddEdgeBiomesLayerMixin {

	@Shadow
	private boolean isBadlands(int id) {
		return id == 37 || id == 38 || id == 39 || id == 165 || id == 166 || id == 167 || id == BuiltinRegistries.BIOME.getRawId(Biomes.BADLANDS_LOW_PLATEAU);
	}

	@Inject(method = "isBadlands", at = @At("RETURN"), cancellable = true)
	private void isBadlands(int id, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) cir.setReturnValue(id == BuiltinRegistries.BIOME.getRawId(Biomes.BADLANDS_LOW_PLATEAU));
	}
	
	@Inject(method = "isWooded", at = @At("RETURN"), cancellable = true)
	private static void isWooded(int id, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) cir.setReturnValue(id == BuiltinRegistries.BIOME.getRawId(Biomes.JUNGLE_PLATEAU));
	}
	
	@Inject(method = "sample", at = @At("RETURN"), cancellable = true)
	public void sample(LayerRandomnessSource context, int n, int e, int s, int w, int center, CallbackInfoReturnable<Integer> cir) {
		if (isBadlands(center) && cir.getReturnValue() == BiomeIds.DESERT) cir.setReturnValue(BiomeIds.BADLANDS);
	}

}
