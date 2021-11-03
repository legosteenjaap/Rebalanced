package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingMenu.class)
public abstract class SmithingScreenHandlerMixin extends ItemCombinerMenu {

	@Shadow
	@Final
	private Level level;
	@Nullable
	@Shadow
	private UpgradeRecipe selectedRecipe;
	@Shadow
	@Final
	private List<UpgradeRecipe> recipes;

	public SmithingScreenHandlerMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
		super(type, syncId, playerInventory, context);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
	private void createResult(CallbackInfo ci) {
		List<UpgradeRecipe> recipes = this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, this.inputSlots, this.level);

		boolean foundRecipe = false;
		for (UpgradeRecipe recipe : recipes) {
			if (!level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING) || (level.isClientSide() && ((LocalPlayer) this.player).getRecipeBook().contains(recipe)) || (!level.isClientSide && ((ServerPlayer) player).getRecipeBook().contains(recipe))) {
				this.selectedRecipe = recipe;
				ItemStack itemStack = this.selectedRecipe.assemble(this.inputSlots);
				this.resultSlots.setRecipeUsed(this.selectedRecipe);
				this.resultSlots.setItem(0, itemStack);
				foundRecipe = true;
				break;
			}
		}

		if (!foundRecipe) {
	         this.resultSlots.setItem(0, ItemStack.EMPTY);
		}
		
		ci.cancel();
	}

}
