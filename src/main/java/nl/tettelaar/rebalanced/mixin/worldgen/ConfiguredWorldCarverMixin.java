package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Function;

@Mixin(ConfiguredWorldCarver.class)
public class ConfiguredWorldCarverMixin {

    @Inject(method = "carve", at = @At("HEAD"), cancellable = true)
    public void carve(CarvingContext carvingContext, ChunkAccess chunkAccess, Function<BlockPos, Biome> function, Random random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

}
