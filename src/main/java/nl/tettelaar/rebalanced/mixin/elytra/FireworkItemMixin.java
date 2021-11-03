package nl.tettelaar.rebalanced.mixin.elytra;

import java.util.Random;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public class FireworkItemMixin extends Item {

	public FireworkItemMixin(Properties settings) {
		super(settings);
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		//user.getItemCooldownManager().set(this, 1000);
		cir.setReturnValue(InteractionResultHolder.pass(user.getItemInHand(hand)));
	}
	
}
