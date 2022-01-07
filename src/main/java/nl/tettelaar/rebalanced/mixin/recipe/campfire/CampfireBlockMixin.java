package nl.tettelaar.rebalanced.mixin.recipe.campfire;

import java.util.Arrays;
import java.util.Optional;

import net.minecraft.client.player.LocalPlayer;
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
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.xp.interfaces.XPBlockInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
	      if (blockEntity instanceof CampfireBlockEntity) {
	         CampfireBlockEntity campfireBlockEntity = (CampfireBlockEntity)blockEntity;
	         ItemStack itemStack = player.getItemInHand(hand);
	         Optional<CampfireCookingRecipe> recipe = campfireBlockEntity.getCookableRecipe(itemStack);
	         if (recipe.isPresent() && ((!level.isClientSide() && !((ServerPlayer) player).getRecipeBook().contains(recipe.get())) && level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING))) {
				 Optional<Integer> XPCost = RecipeAPI.getItemXPCost(recipe.get().getResultItem().getItem());
				 if (XPCost.isPresent()) {
					 if (player.experienceLevel >= XPCost.get()) {
						 ((ServerPlayer) player).getRecipeBook().addRecipes(RecipeAPI.getRecipesWithDiscoverableItem(recipe.get().getResultItem(), level.getRecipeManager()), (ServerPlayer) player);
						 ((ServerPlayer) player).giveExperienceLevels(-XPCost.get());
					 } else {
						 cir.setReturnValue(InteractionResult.FAIL);
					 }
				 }
	         }
	      }
	}

}
