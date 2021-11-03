package nl.tettelaar.rebalanced.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderEyeItem.class)
public class DisableEndPortalMixin {

	@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
	public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		cir.setReturnValue(InteractionResult.FAIL);
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		cir.setReturnValue(InteractionResultHolder.pass(user.getItemInHand(hand)));
	}
	
}
