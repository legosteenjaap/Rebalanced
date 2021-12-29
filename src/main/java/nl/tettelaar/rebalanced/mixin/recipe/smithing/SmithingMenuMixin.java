package nl.tettelaar.rebalanced.mixin.recipe.smithing;

import java.util.List;
import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.network.NetworkingClient;
import nl.tettelaar.rebalanced.recipe.interfaces.SmithingMenuInterface;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu implements SmithingMenuInterface {

	@Shadow
	@Final
	private Level level;
	@Nullable
	@Shadow
	private UpgradeRecipe selectedRecipe;
	@Shadow
	@Final
	private List<UpgradeRecipe> recipes;

	public SmithingMenuMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
		super(type, syncId, playerInventory, context);
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeBoolean(player.getLevel().getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING));
		NetworkingClient.doLimitedCrafting = true;
		ClientPlayNetworking.send(NetworkingClient.DO_LIMITEDCRAFTING_ID, buf);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
	private void createResult(CallbackInfo ci) {
		List<UpgradeRecipe> recipes = this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, this.inputSlots, this.level);
		if (recipes.isEmpty()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
		}
		boolean foundRecipe = false;
		for (UpgradeRecipe recipe : recipes) {
			this.selectedRecipe = recipe;
			ItemStack itemStack = this.selectedRecipe.assemble(this.inputSlots);
			this.resultSlots.setRecipeUsed(this.selectedRecipe);
			this.resultSlots.setItem(0, itemStack);
			foundRecipe = true;
			break;
		}
		
		ci.cancel();
	}

	@Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
	protected void mayPickup(Player player, boolean bl, CallbackInfoReturnable<Boolean> cir) {
		if (!isUnlockable()) cir.setReturnValue(false);
	}

	@Inject(method = "onTake", at = @At("HEAD"))
	protected void onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
		if (!this.canUseRecipe() && this.isUnlockable() && getXPCost().isPresent()) {
			player.experienceLevel = player.experienceLevel - getXPCost().get();
		}
	}

	@Override
	public boolean isUnlockable() {
		if (this.selectedRecipe == null) {
			return false;
		}
		Optional<Integer> XPCost = RecipeAPI.getItemXPCost(selectedRecipe.getResultItem().getItem());
		return (XPCost.isPresent() && RecipeUtil.isUnlockable(player, XPCost.get()));
	}

	@Override
	public boolean canUseRecipe() {
		return (level.isClientSide() && (!level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING) || ((LocalPlayer) this.player).getRecipeBook().contains(this.selectedRecipe))) || (!level.isClientSide && (!NetworkingClient.doLimitedCrafting || ((ServerPlayer) player).getRecipeBook().contains(this.selectedRecipe)));
	}

	@Override
	public Optional<Integer> getXPCost() {
		if (this.selectedRecipe != null) return RecipeAPI.getItemXPCost(this.selectedRecipe.getResultItem().getItem());
		return Optional.empty();
	}

}
