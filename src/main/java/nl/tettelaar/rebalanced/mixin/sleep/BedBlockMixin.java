package nl.tettelaar.rebalanced.mixin.sleep;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public class BedBlockMixin {
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		if (player.experienceLevel < 10 && world.dimensionType().bedWorks() && !world.isDay() && !world.isThundering()) {
            if (player instanceof ServerPlayer) {
            	((ServerPlayer) player).setRespawnPosition(world.dimension(), pos, 0.0F, false, true);
            }
            TranslatableComponent text = new TranslatableComponent("sleep.try.xp");
			player.displayClientMessage(text , true);
			cir.setReturnValue(InteractionResult.FAIL);
		}
	}
	

}
