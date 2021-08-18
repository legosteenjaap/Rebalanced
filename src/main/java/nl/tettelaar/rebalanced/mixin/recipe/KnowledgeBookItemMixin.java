package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.KnowledgeBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nl.tettelaar.rebalanced.RebalancedClient;

@Mixin(KnowledgeBookItem.class)
public class KnowledgeBookItemMixin extends Item {

	public KnowledgeBookItemMixin(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void useHead(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult> cir) {
		ItemStack itemStack = user.getStackInHand(hand);

		NbtCompound compoundTag = itemStack.getTag();
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (!world.isClient) {

				ServerPlayerEntity player = (ServerPlayerEntity) user;

				// THIS CODE MAKES SURE THAT THE PLAYER IS COMPENSATED FOR THE RECIPES THAT THEY
				// ALREADY HAVE

				for (Recipe<?> recipe : getRecipes(compoundTag, world)) {
					ItemStack item = recipe.getOutput();
					PacketByteBuf buf = PacketByteBufs.create();
					ServerPlayNetworking.send((ServerPlayerEntity) user, RebalancedClient.SHOW_FLOATING_ITEM_ID, buf.writeItemStack(item));
					if (player.getRecipeBook().contains(recipe)) {
						user.incrementStat(Stats.USED.getOrCreateStat(this));
						user.playSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, SoundCategory.PLAYERS, 1f, 1f);
						Random random = user.world.getRandom();
						int ranInt = random.nextInt(7) + 3;
						switch (item.getRarity()) {
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

					}
				}

			} else {
				user.playSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, SoundCategory.BLOCKS, 1f, 1f);
				cir.setReturnValue(TypedActionResult.fail(itemStack));
			}
		}
	}

	@Inject(method = "use", at = @At("RETURN"), cancellable = true)
	public void useReturn(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult> cir) {

		// THIS CODE REMOVES THE RECIPE BOOK FROM THE PLAYER

		user.setStackInHand(hand, ItemStack.EMPTY);
	}

	// THIS CODE GETS LIST OF RECIPES

	@Unique
	private List<Recipe<?>> getRecipes(NbtCompound compoundTag, World world) {
		NbtList listTag = compoundTag.getList("Recipes", 8);
		List<Recipe<?>> list = Lists.newArrayList();
		RecipeManager recipeManager = null;
		if (!world.isClient()) {
			recipeManager = world.getServer().getRecipeManager();
		} else {
			recipeManager = ((ClientWorld) world).getRecipeManager();
		}
		

		for (int i = 0; i < listTag.size(); ++i) {
			String string = listTag.getString(i);
			Optional<? extends Recipe<?>> optional = recipeManager.get(new Identifier(string));
			if (optional.isPresent()) {
				list.add(optional.get());
			}
		}
		return list;
	}

	@Override
	public Rarity getRarity(ItemStack stack) {

		NbtCompound compoundTag = stack.getTag();
		World world = MinecraftClient.getInstance().world;
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (world.isClient) {

				Rarity highestRarity = null;
				for (Recipe<?> recipe : getRecipes(compoundTag, world)) {
					ItemStack item = recipe.getOutput();
					if (highestRarity != null) {
						switch (item.getRarity()) {
						case COMMON:
							break;
						case UNCOMMON:
							if (highestRarity != Rarity.COMMON) {
								highestRarity = item.getRarity();
								;
							}
							break;
						case RARE:
							if (highestRarity != Rarity.COMMON && highestRarity != Rarity.UNCOMMON) {
								highestRarity = item.getRarity();
								;
							}
						default:
						case EPIC:
							highestRarity = item.getRarity();
						}

					} else {
						highestRarity = item.getRarity();
					}
				}
				if (highestRarity != null) {
					return highestRarity;
				}
			}
		}

		return Rarity.UNCOMMON;

	}

}
