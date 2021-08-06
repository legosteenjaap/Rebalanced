package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

	@Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
	      if (blockEntity instanceof CampfireBlockEntity) {
	         CampfireBlockEntity campfireBlockEntity = (CampfireBlockEntity)blockEntity;
	         ItemStack itemStack = player.getStackInHand(hand);
	         Optional<CampfireCookingRecipe> optional = campfireBlockEntity.getRecipeFor(itemStack);
	         if (optional.isPresent() && ((!world.isClient() && !((ServerPlayerEntity) player).getRecipeBook().contains(optional.get())) && world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING))) {
	        	 cir.setReturnValue(ActionResult.FAIL);
	         }
	      }
	}

}
