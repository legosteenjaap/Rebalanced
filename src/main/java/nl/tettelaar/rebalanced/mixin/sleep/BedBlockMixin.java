package nl.tettelaar.rebalanced.mixin.sleep;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BedBlock.class)
public class BedBlockMixin {
	@Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		if (player.experienceLevel < 10 && world.getDimension().isBedWorking() && !world.isDay() && !world.isThundering()) {
            
            TranslatableText text = new TranslatableText("sleep.try.xp");
			player.sendMessage(text , true);
			cir.setReturnValue(ActionResult.FAIL);
		}
	}
	

}
