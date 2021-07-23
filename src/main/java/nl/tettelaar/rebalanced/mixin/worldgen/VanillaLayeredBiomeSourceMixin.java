package nl.tettelaar.rebalanced.mixin.worldgen;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;

@Mixin(VanillaLayeredBiomeSource.class)
public class VanillaLayeredBiomeSourceMixin {
	
	@ModifyConstant(constant = @Constant(intValue = 6), method = "<init>")
	private static int largeBiomes(int original) {
		return 1;
	}
	
	@ModifyConstant(constant = @Constant(intValue = 4), method = "<init>")
	private static int normalBiomes(int original) {
		return 5;
	}
	
}
