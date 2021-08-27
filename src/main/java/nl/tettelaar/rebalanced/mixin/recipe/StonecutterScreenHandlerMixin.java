package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(StonecutterScreenHandler.class)
public class StonecutterScreenHandlerMixin {

	@Shadow
	private List<StonecuttingRecipe> availableRecipes;
	@Shadow
	@Final
	private World world;
	@Shadow
	@Final
	Slot outputSlot;
	@Shadow
	@Final
	private Property selectedRecipe;

	@Unique
	private PlayerEntity player;

	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("RETURN"))
	private void init(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context, CallbackInfo ci) {
		this.player = playerInventory.player;
	}

	@Inject(method = "updateInput", at = @At("HEAD"), cancellable = true)
	private void updateInput(Inventory input, ItemStack stack, CallbackInfo ci) {
		this.availableRecipes.clear();
		this.selectedRecipe.set(-1);
		this.outputSlot.setStack(ItemStack.EMPTY);
		if (!stack.isEmpty()) {
			ArrayList<StonecuttingRecipe> recipes = new ArrayList<StonecuttingRecipe>(this.world.getRecipeManager().getAllMatches(RecipeType.STONECUTTING, input, this.world));
			ArrayList<StonecuttingRecipe> trueRecipes = new ArrayList<>();
			for (StonecuttingRecipe recipe : recipes) {
				System.out.println(world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING));
				if ((world.isClient() && (((ClientPlayerEntity) player).getRecipeBook().contains(recipe)) || (!world.isClient && ((ServerPlayerEntity)player).getRecipeBook().contains(recipe)))) {
					trueRecipes.add(recipe);
				}
			}
			this.availableRecipes = trueRecipes;
		}
		ci.cancel();
	}

}
