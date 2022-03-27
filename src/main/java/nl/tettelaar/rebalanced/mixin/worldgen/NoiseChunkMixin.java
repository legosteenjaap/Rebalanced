package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoiseChunk.class)
public class NoiseChunkMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/NoiseSampler;makeBaseNoiseFiller(Lnet/minecraft/world/level/levelgen/NoiseChunk;Lnet/minecraft/world/level/levelgen/NoiseChunk$NoiseFiller;Z)Lnet/minecraft/world/level/levelgen/NoiseChunk$BlockStateFiller;"), index = 2)
    private boolean injected(boolean noodleCaves) {
        return false;
    }

}
