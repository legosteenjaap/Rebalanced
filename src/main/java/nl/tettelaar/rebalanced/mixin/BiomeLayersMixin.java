package nl.tettelaar.rebalanced.mixin;

import java.util.function.LongFunction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.layer.AddColdClimatesLayer;
import net.minecraft.world.biome.layer.ApplyOceanTemperatureLayer;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.IncreaseEdgeCurvatureLayer;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import nl.tettelaar.rebalanced.gen.biomelayers.ClimateLayer;
import nl.tettelaar.rebalanced.gen.biomelayers.RemoveSmallIslandLayer;
import nl.tettelaar.rebalanced.gen.biomelayers.SpecialBiomesLayer;


@Mixin(BiomeLayers.class)
@SuppressWarnings("unchecked")
public class BiomeLayersMixin {

	private static LongFunction<?> contextProvider;

    @Inject(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;",
            at = @At("HEAD"))
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> void captureContext(boolean old, int biomeSize, int riverSize, LongFunction<C> contextProvider, CallbackInfoReturnable<LayerFactory<T>> cir) {
        BiomeLayersMixin.contextProvider = contextProvider;
    }
	
    @Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/IncreaseEdgeCurvatureLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 0))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> addRemoveSmallIslandLayer(
			IncreaseEdgeCurvatureLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		layerFactory = RemoveSmallIslandLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(1L), layerFactory);
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/ScaleLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 0))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeFuzzyScaleLayer(
			ScaleLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/ScaleLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 1))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeNormalScaleLayer1(
			ScaleLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/IncreaseEdgeCurvatureLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 1))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeIncreaseEdgeCurvatureLayer1(
			IncreaseEdgeCurvatureLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/IncreaseEdgeCurvatureLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 2))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeIncreaseEdgeCurvatureLayer2(
			IncreaseEdgeCurvatureLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/IncreaseEdgeCurvatureLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 3))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeIncreaseEdgeCurvatureLayer3(
			IncreaseEdgeCurvatureLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/AddColdClimatesLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;"))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeColdClimatesLayer(
			AddColdClimatesLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/IncreaseEdgeCurvatureLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 4))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeIncreaseEdgeCurvatureLayer4(
			IncreaseEdgeCurvatureLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/ScaleLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 2))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeNormalScaleLayer2(
			ScaleLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/ScaleLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 3))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeNormalScaleLayer3(
			ScaleLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/IncreaseEdgeCurvatureLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 5))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeIncreaseEdgeCurvatureLayer5AndAddClimateAndSpecialBiomeLayers(
			IncreaseEdgeCurvatureLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory) {
		layerFactory = ClimateLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(5L), layerFactory);
	    layerFactory = SpecialBiomesLayer.INSTANCE.create((LayerSampleContext<T>)contextProvider.apply(5L), layerFactory);
		return layerFactory;
	}
	
	@Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/ApplyOceanTemperatureLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;"))
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> removeApplyOceanTemperatureLayer(
			ApplyOceanTemperatureLayer layer, LayerSampleContext<?> context, LayerFactory<T> layerFactory1, LayerFactory<T> layerFactory2) {
		return layerFactory1;
	}

}
