package nl.tettelaar.rebalanced.mixin.recipe.crafting;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import nl.tettelaar.rebalanced.recipe.interfaces.ResultContainerInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultSlot.class)
public class ResultSlotMixin extends Slot {

    public ResultSlotMixin(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPickup(Player player) {
        ResultContainerInterface resultContainer = ((ResultContainerInterface)this.container);
        if (resultContainer.isRecipeUnlocked() || resultContainer.isUnlockable(player)){
            return true;
        }
        return false;
    }

    @Inject(method = "onTake", at = @At("HEAD"), cancellable = true)
    public void onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        ResultContainerInterface resultContainer = ((ResultContainerInterface) this.container);
        if (player instanceof ServerPlayer && !player.isCreative() && !resultContainer.isRecipeUnlocked() && resultContainer.isUnlockable(player )) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.setExperienceLevels(serverPlayer.experienceLevel - resultContainer.getXPCost().get());
        }
    }


}
