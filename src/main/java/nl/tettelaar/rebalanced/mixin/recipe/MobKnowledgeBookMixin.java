package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.util.RecipeUtil;

@Mixin(MobEntity.class)
public abstract class MobKnowledgeBookMixin extends LivingEntity {

	protected MobKnowledgeBookMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "initEquipment", at = @At("RETURN"), cancellable = true)
	protected void initEquipment(LocalDifficulty difficulty, CallbackInfo ci) {
		if (this.random.nextFloat() > 0.9 && !world.isClient()) {
			PlayerEntity player = this.world.getClosestPlayer(this.getX(), this.getY(), this.getZ(), 50, null);
			List<Identifier> recipes = RecipeAPI.getKnowledgeBooksMobEquipment((EntityType<? extends MobEntity>) this.getType());

			if (player != null && recipes != null) {
				RecipeBook recipeBook = ((ServerPlayerEntity) player).getRecipeBook();

				for (Identifier recipe : recipes) {
					if (!recipeBook.contains(recipe)) {
						if (getEquippedStack(EquipmentSlot.MAINHAND) == ItemStack.EMPTY) {
							equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
						}
						equipStack(EquipmentSlot.OFFHAND, RecipeUtil.createKnowledgeBook(recipe));
						setEquipmentDropChance(EquipmentSlot.OFFHAND, 1f);
						
						break;
					}
				}
			}
		}
	}

	@Shadow
	public void equipStack(EquipmentSlot slot, ItemStack stack) {

	}

	@Shadow
	public ItemStack getEquippedStack(EquipmentSlot slot) {
		throw new AssertionError();
	}

	@Shadow
	public void setEquipmentDropChance(EquipmentSlot slot, float chance) {
		
	}


}
