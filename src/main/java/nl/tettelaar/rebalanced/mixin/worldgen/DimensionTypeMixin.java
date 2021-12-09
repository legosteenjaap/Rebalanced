package nl.tettelaar.rebalanced.mixin.worldgen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.OptionalLong;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {

    @Shadow @Final
    public static final ResourceLocation OVERWORLD_EFFECTS = new ResourceLocation("overworld");

    @Shadow @Final @Mutable
    protected static final DimensionType DEFAULT_OVERWORLD = DimensionType.create(OptionalLong.empty(), true, false, false, true, 1.0, false, false, true, false, true, -64, 1024, 384, BlockTags.INFINIBURN_OVERWORLD.getName(), OVERWORLD_EFFECTS, 0.0f);

}
