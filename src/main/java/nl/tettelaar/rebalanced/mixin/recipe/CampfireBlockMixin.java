package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
	      if (blockEntity instanceof CampfireBlockEntity) {
	         CampfireBlockEntity campfireBlockEntity = (CampfireBlockEntity)blockEntity;
	         ItemStack itemStack = player.getItemInHand(hand);
	         Optional<CampfireCookingRecipe> optional = campfireBlockEntity.getCookableRecipe(itemStack);
	         if (optional.isPresent() && ((!world.isClientSide() && !((ServerPlayer) player).getRecipeBook().contains(optional.get())) && world.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING))) {
	        	 cir.setReturnValue(InteractionResult.FAIL);
	         }
	      }
	}

}
