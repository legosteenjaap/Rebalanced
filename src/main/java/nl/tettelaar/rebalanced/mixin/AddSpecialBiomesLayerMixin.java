package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.biome.layer.AddClimateLayers.AddSpecialBiomesLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(AddSpecialBiomesLayer.class)
public class AddSpecialBiomesLayerMixin {
	
	@Overwrite
    public int sample(LayerRandomnessSource context, int value) {
		return value;
	}
}
