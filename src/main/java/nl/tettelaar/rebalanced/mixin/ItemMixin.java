package nl.tettelaar.rebalanced.mixin;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemLike {

    @Shadow @Final private int maxStackSize;
    @Shadow @Final private FoodProperties foodProperties;

    @Inject(method = "getMaxStackSize", at = @At("HEAD"), cancellable = true)
    private void getMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        if ((foodProperties != null || this.equals(Items.GLISTERING_MELON_SLICE)) && (this.maxStackSize == 64 || this.maxStackSize == 16)) cir.setReturnValue(8);
    }

}
