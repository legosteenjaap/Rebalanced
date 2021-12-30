package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceBlockEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlock.class)
public class AbstractFurnaceBlockMixin {

    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack, CallbackInfo ci) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof FurnaceBlockEntityInterface && livingEntity instanceof Player) {
            ((FurnaceBlockEntityInterface)(Object)blockEntity).setOwner((Player)livingEntity);
        }
    }
}
