package nl.tettelaar.rebalanced.mixin.recipe.book.knowledge;

import java.util.Random;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.KnowledgeBookItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import nl.tettelaar.rebalanced.network.NetworkingClient;
import nl.tettelaar.rebalanced.util.RecipeUtil;

@Mixin(KnowledgeBookItem.class)
public class KnowledgeBookItemMixin extends Item {

	public KnowledgeBookItemMixin(Properties settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void useHead(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		ItemStack itemStack = user.getItemInHand(hand);

		CompoundTag compoundTag = itemStack.getTag();
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (!world.isClientSide) {
				ServerPlayer player = (ServerPlayer) user;
				ItemStack output = RecipeUtil.getRecipeOutput(compoundTag, world);
				if (output != null && RecipeUtil.playerCanUnlockRecipe(compoundTag, world, player)) {
					if (RecipeUtil.playerHasAllRecipes(compoundTag, world, player)) {
						Random random = user.level.getRandom();
						int ranInt = random.nextInt(7) + 3;
						switch (output.getRarity()) {
						case UNCOMMON:
							ranInt *= 2;
							break;
						case RARE:
							ranInt *= 3;
							break;
						case EPIC:
							ranInt *= 4;
							break;
						default:
							break;

						}
						for (int i = 0; i < ranInt; i++) {
							ExperienceOrb.award((ServerLevel) user.level, user.position(), random.nextInt(3));
						}

					} else {
						FriendlyByteBuf buf = PacketByteBufs.create();
						ServerPlayNetworking.send((ServerPlayer) user, NetworkingClient.SHOW_FLOATING_ITEM_ID, buf.writeItem(output));
					}
				} else {
					cir.setReturnValue(InteractionResultHolder.fail(itemStack));
				}

			} else {
				cir.setReturnValue(InteractionResultHolder.fail(itemStack));
			}
			
		}
		user.playNotifySound(SoundEvents.VILLAGER_WORK_LIBRARIAN, SoundSource.PLAYERS, 1f, 1f);
	}

	@Inject(method = "use", at = @At("RETURN"), cancellable = true)
	public void useReturn(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {

		// THIS CODE REMOVES THE RECIPE BOOK FROM THE PLAYER

		user.setItemInHand(hand, ItemStack.EMPTY);
	}

}
