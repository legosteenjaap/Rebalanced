package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.KnowledgeBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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
				
				//THIS CODE GETS LIST OF RECIPES
				
				NbtList listTag = compoundTag.getList("Recipes", 8);
				List<Recipe<?>> list = Lists.newArrayList();
				RecipeManager recipeManager = world.getServer().getRecipeManager();

				for (int i = 0; i < listTag.size(); ++i) {
					String string = listTag.getString(i);
					Optional<? extends Recipe<?>> optional = recipeManager.get(new Identifier(string));
					if (!optional.isPresent()) {
					}

					list.add(optional.get());
				}

				ServerPlayerEntity player = (ServerPlayerEntity) user;

				//THIS CODE MAKES SURE THAT THE PLAYER IS COMPENSATED FOR THE RECIPES THAT THEY ALREADY HAVE
				
				for (Recipe<?> recipe : list) {
					if (player.getRecipeBook().contains(recipe)) {
						user.incrementStat(Stats.USED.getOrCreateStat(this));
						user.playSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, SoundCategory.PLAYERS, 1f, 1f);
						Random random = user.world.getRandom();
						int ranInt = random.nextInt(7) + 3;
						switch (recipe.getOutput().getRarity()) {
						case UNCOMMON:
							ranInt *= 2;
							break;
						case RARE:
							ranInt *= 3;
							break;
						case EPIC:
							ranInt *= 4;
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

		//THIS CODE REMOVES THE RECIPE BOOK FROM THE PLAYER
		
		user.setStackInHand(hand, ItemStack.EMPTY);
	}
}
