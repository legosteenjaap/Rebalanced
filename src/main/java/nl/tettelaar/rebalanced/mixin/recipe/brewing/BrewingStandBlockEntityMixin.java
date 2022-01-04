package nl.tettelaar.rebalanced.mixin.recipe.brewing;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import nl.tettelaar.rebalanced.recipe.brewing.BrewingStandMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {

    @Inject(method = "createMenu", at = @At("HEAD"), cancellable = true)
    protected void createMenu(int i, Inventory inventory, CallbackInfoReturnable<AbstractContainerMenu> cir) {
        inventory.player.displayClientMessage(new TranslatableComponent("rebalanced.testing.unfinished"), true);
        cir.setReturnValue(null);
        //cir.setReturnValue(new BrewingStandMenu(i, inventory));
    }

}
