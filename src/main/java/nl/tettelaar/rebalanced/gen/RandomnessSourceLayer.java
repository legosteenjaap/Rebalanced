package nl.tettelaar.rebalanced.gen;

import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public interface RandomnessSourceLayer extends IdentityCoordinateTransformer {
	default <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent) {
	      return () -> {
	         R layerSampler = parent.make();
	         return context.createSampler((x, y) -> {
	            context.initSeed((long)x, (long)y);
	            return this.sample(context, x, y, layerSampler.sample(this.transformX(x), this.transformZ(y)));
	         }, layerSampler);
	      };
	   }
	
	int sample(LayerRandomnessSource context, int x, int y, int value);
}