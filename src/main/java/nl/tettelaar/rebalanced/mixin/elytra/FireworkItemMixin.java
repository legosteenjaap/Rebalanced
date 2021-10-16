package nl.tettelaar.rebalanced.mixin.elytra;

import java.util.Random;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

@Mixin(FireworkItem.class)
public class FireworkItemMixin extends Item {

	public FireworkItemMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		//user.getItemCooldownManager().set(this, 1000);
		cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
	}
	
}
