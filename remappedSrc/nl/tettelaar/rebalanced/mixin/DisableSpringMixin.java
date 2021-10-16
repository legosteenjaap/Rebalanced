package nl.tettelaar.rebalanced.mixin;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DefaultBiomeFeatures.class)
public class DisableSpringMixin {

	@Redirect(method = "addSprings", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/GenerationSettings$Builder;feature(Lnet/minecraft/world/gen/GenerationStep$Feature;Lnet/minecraft/world/gen/feature/ConfiguredFeature;)Lnet/minecraft/world/biome/GenerationSettings$Builder;", ordinal = 0))
	private static GenerationSettings.Builder disableSprings(GenerationSettings.Builder builder,
			GenerationStep.Feature step, ConfiguredFeature<?, ?> feature) {
		

		return builder;
	}

}
