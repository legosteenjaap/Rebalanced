package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceBlockEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {FurnaceBlock.class, BlastFurnaceBlock.class, SmokerBlock.class})
public class FurnaceBlockMixin {

    @Inject(method = "openContainer", at = @At("HEAD"), cancellable = true)
    private void openContainer(Level level, BlockPos blockPos, Player player, CallbackInfo ci) {
        FurnaceBlockEntityInterface furnaceBlockEntityInterface = (FurnaceBlockEntityInterface)(AbstractFurnaceBlockEntity)level.getBlockEntity(blockPos);
        furnaceBlockEntityInterface.setOwner(player);
    }

}
