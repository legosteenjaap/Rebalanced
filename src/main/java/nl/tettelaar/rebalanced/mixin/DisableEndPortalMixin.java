package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

@Mixin(EnderEyeItem.class)
public class DisableEndPortalMixin {

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		cir.setReturnValue(ActionResult.FAIL);
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
	}
	
}
