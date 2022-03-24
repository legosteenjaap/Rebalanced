package nl.tettelaar.rebalanced.mixin.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Advancement.Builder.class)
public interface AdvancementBuilderAccessor {

    @Accessor
    public AdvancementRewards getRewards();

    @Invoker("<init>")
    public static Advancement.Builder initBuilder() {
        throw new AssertionError();
    }

}
