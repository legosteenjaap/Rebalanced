package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(SmithingScreenHandler.class)
public abstract class SmithingScreenHandlerMixin extends ForgingScreenHandler {

	@Shadow
	@Final
	private World world;
	@Nullable
	@Shadow
	private SmithingRecipe currentRecipe;
	@Shadow
	@Final
	private List<SmithingRecipe> recipes;

	public SmithingScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
	private void updateResult(CallbackInfo ci) {
		List<SmithingRecipe> recipes = this.world.getRecipeManager().getAllMatches(RecipeType.SMITHING, this.input, this.world);

		boolean foundRecipe = false;
		for (SmithingRecipe recipe : recipes) {
			if (!world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING) || (world.isClient() && ((ClientPlayerEntity) this.player).getRecipeBook().contains(recipe)) || (!world.isClient && ((ServerPlayerEntity) player).getRecipeBook().contains(recipe))) {
				this.currentRecipe = recipe;
				ItemStack itemStack = this.currentRecipe.craft(this.input);
				this.output.setLastRecipe(this.currentRecipe);
				this.output.setStack(0, itemStack);
				foundRecipe = true;
				break;
			}
		}

		if (!foundRecipe) {
	         this.output.setStack(0, ItemStack.EMPTY);
		}
		
		ci.cancel();
	}

}
