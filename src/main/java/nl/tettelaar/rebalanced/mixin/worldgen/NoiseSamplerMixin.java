package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import nl.tettelaar.rebalanced.init.RebalancedWorldGen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(NoiseSampler.class)
public class NoiseSamplerMixin {

    @Unique @Final
    private NormalNoise caveLimiter;

    int x;
    int y;
    int z;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void NoiseSampler(NoiseSettings noiseSettings, boolean bl, long l, Registry<NormalNoise.NoiseParameters> registry, WorldgenRandom.Algorithm algorithm, CallbackInfo ci) {
        PositionalRandomFactory positionalRandomFactory = algorithm.newInstance(l).forkPositional();
        this.caveLimiter = Noises.instantiate(registry, positionalRandomFactory, RebalancedWorldGen.CAVE_LIMITER);
    }

    @ModifyArgs(method = "calculateBaseNoise", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/NoiseSampler;calculateBaseNoise(IIILnet/minecraft/world/level/levelgen/TerrainInfo;DZZLnet/minecraft/world/level/levelgen/blending/Blender;)D"))
    private void injected(Args args) {
        this.x = args.get(0);
        this.y = args.get(1);
        this.z = args.get(2);
        //if (this.caveLimiter.getValue(x, y, z) < 0) args.set(5,  true);
    }

    @ModifyVariable(method = "calculateBaseNoise(IIILnet/minecraft/world/level/levelgen/TerrainInfo;DZZLnet/minecraft/world/level/levelgen/blending/Blender;)D", at = @At("STORE"), ordinal = 10)
    private double injected(double x) {
        /*double caveLimitNoise = this.caveLimiter.getValue(this.x, this.y, this.z);
        if (caveLimitNoise < 0.9 && caveLimitNoise > 0) {
            return x * (caveLimitNoise);
        }*/
        return 0;
    }

}