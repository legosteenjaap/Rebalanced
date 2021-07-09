package nl.tettelaar.rebalanced.mixin;

import java.util.function.LongFunction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.biome.layer.AddBambooJungleLayer;
import net.minecraft.world.biome.layer.AddBaseBiomesLayer;
import net.minecraft.world.biome.layer.AddDeepOceanLayer;
import net.minecraft.world.biome.layer.AddEdgeBiomesLayer;
import net.minecraft.world.biome.layer.AddHillsLayer;
import net.minecraft.world.biome.layer.AddMushroomIslandLayer;
import net.minecraft.world.biome.layer.AddSunflowerPlainsLayer;
import net.minecraft.world.biome.layer.ApplyRiverLayer;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.ContinentLayer;
import net.minecraft.world.biome.layer.EaseBiomeEdgeLayer;
import net.minecraft.world.biome.layer.IncreaseEdgeCurvatureLayer;
import net.minecraft.world.biome.layer.NoiseToRiverLayer;
import net.minecraft.world.biome.layer.OceanTemperatureLayer;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SimpleLandNoiseLayer;
import net.minecraft.world.biome.layer.SmoothLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import nl.tettelaar.rebalanced.gen.ClimateLayer;

@Mixin(BiomeLayers.class)
public class BiomeLayersMixin {
	@Shadow
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
		throw new AssertionError();
	}
	
	@Overwrite
	@SuppressWarnings({ "unchecked", "unused" })
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(boolean old, int biomeSize, int riverSize, LongFunction<C> contextProvider) {
		LayerFactory<T> layerFactory = ContinentLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1L));
	      layerFactory = ScaleLayer.FUZZY.create((LayerSampleContext<T>)contextProvider.apply(2000L), layerFactory);
	      //layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1L), layerFactory);
	      layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext<T>)contextProvider.apply(2001L), layerFactory);
	      //layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(2L), layerFactory);
	      //layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(50L), layerFactory);
	      //layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(70L), layerFactory);
	      //layerFactory = AddIslandLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(2L), layerFactory);
	      LayerFactory<T> layerFactory2 = OceanTemperatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(2L));
	      layerFactory2 = stack(2001L, ScaleLayer.NORMAL, layerFactory2, 6, contextProvider);
	      //layerFactory = ColdClimatesLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(2L), layerFactory);
	      //layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(3L), layerFactory);
	      //layerFactory = AddClimateLayers.AddTemperateBiomesLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(2L), layerFactory);
	      //layerFactory = AddClimateLayers.AddCoolBiomesLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(2L), layerFactory);
	      //layerFactory = AddClimateLayers.AddSpecialBiomesLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(3L), layerFactory);
	      //layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext<T>)contextProvider.apply(2002L), layerFactory);
	      //layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext<T>)contextProvider.apply(2003L), layerFactory);
	      //layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(4L), layerFactory);
	      layerFactory = ClimateLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(5L), layerFactory);
	      layerFactory = AddMushroomIslandLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(5L), layerFactory);
	      layerFactory = AddDeepOceanLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(4L), layerFactory);
	      layerFactory = stack(1000L, ScaleLayer.NORMAL, layerFactory, 0, contextProvider);
	      LayerFactory<T> layerFactory3 = stack(1000L, ScaleLayer.NORMAL, layerFactory, 0, contextProvider);
	      layerFactory3 = SimpleLandNoiseLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(100L), layerFactory3);
	      //LayerFactory<T> layerFactory4 = layerFactory;
	      LayerFactory<T> layerFactory4 = (new AddBaseBiomesLayer(old)).create((LayerSampleContext)contextProvider.apply(200L), layerFactory);
	      layerFactory4 = AddBambooJungleLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1001L), layerFactory4);
	      layerFactory4 = stack(1000L, ScaleLayer.NORMAL, layerFactory4, 2, contextProvider);
	      layerFactory4 = EaseBiomeEdgeLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1000L), layerFactory4);
	      LayerFactory<T> layerFactory5 = stack(1000L, ScaleLayer.NORMAL, layerFactory3, 2, contextProvider);
	      layerFactory4 = AddHillsLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1000L), layerFactory4, layerFactory5);
	      layerFactory3 = stack(1000L, ScaleLayer.NORMAL, layerFactory3, 2, contextProvider);
	      layerFactory3 = stack(1000L, ScaleLayer.NORMAL, layerFactory3, riverSize, contextProvider);
	      layerFactory3 = NoiseToRiverLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1L), layerFactory3);
	      layerFactory3 = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1000L), layerFactory3);
	      layerFactory4 = AddSunflowerPlainsLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1001L), layerFactory4);

	      for(int i = 0; i < biomeSize; ++i) {
	         layerFactory4 = ScaleLayer.NORMAL.create((LayerSampleContext<T>)contextProvider.apply((long)(1000 + i)), layerFactory4);
	         if (i == 0) {
	            layerFactory4 = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(3L), layerFactory4);
	         }

	         if (i == 1 || biomeSize == 1) {
	            layerFactory4 = AddEdgeBiomesLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1000L), layerFactory4);
	         }
	      }

	      layerFactory4 = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1000L), layerFactory4);
	      layerFactory4 = ApplyRiverLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(100L), layerFactory4, layerFactory3);
	      //layerFactory4 = ApplyOceanTemperatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(100L), layerFactory4, layerFactory2);
	      return layerFactory4;
	   }
	/*@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", 
			at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/biome/layer/AddColdClimatesLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;"))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> injected(AddColdClimatesLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}*/
	
	
}
