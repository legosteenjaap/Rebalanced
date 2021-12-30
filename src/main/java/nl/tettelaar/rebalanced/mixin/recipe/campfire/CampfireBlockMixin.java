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
public class CampfireBlockMixin implements XPBlockInterface {

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
	      if (blockEntity instanceof CampfireBlockEntity) {
	         CampfireBlockEntity campfireBlockEntity = (CampfireBlockEntity)blockEntity;
	         ItemStack itemStack = player.getItemInHand(hand);
	         Optional<CampfireCookingRecipe> optional = campfireBlockEntity.getCookableRecipe(itemStack);
	         if (optional.isPresent() && ((!level.isClientSide() && !((ServerPlayer) player).getRecipeBook().contains(optional.get())) && level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING))) {
				 int XPCost = getXPCost(player, pos, level);
				 if (player.experienceLevel >= XPCost) {
					((ServerPlayer) player).getRecipeBook().addRecipes(Arrays.asList(optional.get()),(ServerPlayer) player);
					((ServerPlayer)player).giveExperienceLevels(-XPCost);
				} else {
					cir.setReturnValue(InteractionResult.FAIL);
				}
	         }
	      }
	}

	@Override
	public int getXPCost(Player player, BlockPos blockPos, Level level) {
		Optional<CampfireCookingRecipe> optionalRecipeMain = ((CampfireBlockEntity)level.getBlockEntity(blockPos)).getCookableRecipe(player.getItemInHand(InteractionHand.MAIN_HAND));
		Optional<CampfireCookingRecipe> optionalRecipeOff = ((CampfireBlockEntity)level.getBlockEntity(blockPos)).getCookableRecipe(player.getItemInHand(InteractionHand.OFF_HAND));
		CampfireCookingRecipe recipe = null;
		if(optionalRecipeMain.isPresent()) {
			recipe = optionalRecipeMain.get();
		} else if (optionalRecipeOff.isPresent()) {
			recipe = optionalRecipeOff.get();
		}
		if (player.isShiftKeyDown()) recipe = null;
		if (recipe != null) {
			Optional<Integer> XPCost = RecipeAPI.getItemXPCost(recipe.getResultItem().getItem());
			if (XPCost.isPresent() && (!(player instanceof LocalPlayer) || !((LocalPlayer)player).getRecipeBook().contains(recipe))) return XPCost.get();
		}
		return 0;
	}

	@Override
	public boolean isLevel() {
		return true;
	}
}
