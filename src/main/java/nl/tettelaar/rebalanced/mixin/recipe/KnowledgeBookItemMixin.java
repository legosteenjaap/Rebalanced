package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.KnowledgeBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nl.tettelaar.rebalanced.network.NetworkingClient;
import nl.tettelaar.rebalanced.util.RecipeUtil;

@Mixin(KnowledgeBookItem.class)
public class KnowledgeBookItemMixin extends Item {

	public KnowledgeBookItemMixin(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void useHead(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		ItemStack itemStack = user.getStackInHand(hand);

		NbtCompound compoundTag = itemStack.getTag();
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (!world.isClient) {
				ServerPlayerEntity player = (ServerPlayerEntity) user;
				ItemStack output = RecipeUtil.getRecipeOutput(compoundTag, world);
				if (output != null && RecipeUtil.playerCanUnlockRecipe(compoundTag, world, player)) {
					if (RecipeUtil.playerHasAllRecipes(compoundTag, world, player)) {
						Random random = user.world.getRandom();
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
							ExperienceOrbEntity.spawn((ServerWorld) user.world, user.getPos(), random.nextInt(3));
						}

					} else {
						PacketByteBuf buf = PacketByteBufs.create();
						ServerPlayNetworking.send((ServerPlayerEntity) user, NetworkingClient.SHOW_FLOATING_ITEM_ID, buf.writeItemStack(output));
					}
				} else {
					cir.setReturnValue(TypedActionResult.fail(itemStack));
				}

			} else {
				cir.setReturnValue(TypedActionResult.fail(itemStack));
			}
			
		}
		user.playSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, SoundCategory.PLAYERS, 1f, 1f);
	}

	@Inject(method = "use", at = @At("RETURN"), cancellable = true)
	public void useReturn(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {

		// THIS CODE REMOVES THE RECIPE BOOK FROM THE PLAYER

		user.setStackInHand(hand, ItemStack.EMPTY);
	}

}
