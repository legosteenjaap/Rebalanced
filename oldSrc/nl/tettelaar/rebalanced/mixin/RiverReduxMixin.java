package nl.tettelaar.rebalanced.mixin;

import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.NoiseToRiverLayer;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SmoothLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.LongFunction;

@Mixin(BiomeLayers.class)
public abstract class RiverReduxMixin {
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
        LayerFactory<T> layerFactory = parent;

        for(int i = 0; i < count; ++i) {
            layerFactory = layer.create(contextProvider.apply(seed + (long)i), layerFactory);
        }

        return layerFactory;
    }

    private static LongFunction biomeContext;

    @Inject(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;",
            at = @At("HEAD"))
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> void captureContext(boolean old, int biomeSize, int riverSize, LongFunction<C> contextProvider, CallbackInfoReturnable<LayerFactory<T>> cir) {
        biomeContext = contextProvider;
    }

    @Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/BiomeLayers;stack(JLnet/minecraft/world/biome/layer/type/ParentedLayer;Lnet/minecraft/world/biome/layer/util/LayerFactory;ILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 5))
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> scaleMore(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
        parent = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)biomeContext.apply(41L), parent);
        parent = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)biomeContext.apply(42L), parent);
        parent = stack(seed, layer, parent, count, contextProvider);
        parent = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)biomeContext.apply(46L), parent);
        parent = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)biomeContext.apply(47L), parent);
        return parent;
    }

    @Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/BiomeLayers;stack(JLnet/minecraft/world/biome/layer/type/ParentedLayer;Lnet/minecraft/world/biome/layer/util/LayerFactory;ILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;", ordinal = 6))
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> scaleRiverVar(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
        parent = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)biomeContext.apply(51L), parent);
        parent = stack(seed, layer, parent, count, contextProvider);
        parent = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)biomeContext.apply(44L), parent);
        return parent;
    }

    @Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/NoiseToRiverLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;"))
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> biggerRiver(NoiseToRiverLayer layer, LayerSampleContext<T> context, LayerFactory<T> parent) {
        parent = layer.create(context, parent);
        parent = ScaleLayer.NORMAL.create((LayerSampleContext<T>)biomeContext.apply(34L), parent);
        parent = ScaleLayer.NORMAL.create((LayerSampleContext<T>)biomeContext.apply(35L), parent);
        parent = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)biomeContext.apply(36L), parent);
        parent = SmoothLayer.INSTANCE.create((LayerSampleContext<T>)biomeContext.apply(37L), parent);
        return parent;
    }

}