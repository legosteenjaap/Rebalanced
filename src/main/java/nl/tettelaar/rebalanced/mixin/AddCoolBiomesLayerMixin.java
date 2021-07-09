package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.biome.layer.AddClimateLayers.AddCoolBiomesLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(AddCoolBiomesLayer.class)
public class AddCoolBiomesLayerMixin {

	@Overwrite
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
		return center;
	}
	
}
