package nl.tettelaar.rebalanced.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemConvertible {

    @Shadow @Final private int maxCount;

    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void getMaxCount(CallbackInfoReturnable<Integer> cir) {
        if ((this.isFood() || this.equals(Items.GLISTERING_MELON_SLICE)) && (this.maxCount == 64 || this.maxCount == 16)) cir.setReturnValue(8);
    }

    @Shadow
    public boolean isFood() {
        throw new AssertionError();
    }

}
