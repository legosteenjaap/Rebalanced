package nl.tettelaar.rebalanced.mixin.recipe.book.knowledge;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.KnowledgeBookItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import nl.tettelaar.rebalanced.util.RecipeUtil;

@Mixin(KnowledgeBookItem.class)
public class KnowledgeBookItemClientMixin extends Item {

	public KnowledgeBookItemClientMixin(Properties settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}


	@Override
	public Component getName(ItemStack stack) {
		CompoundTag compoundTag = stack.getTag();
		Minecraft client = Minecraft.getInstance();
		Level world = client.level;
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (world != null && world.isClientSide) {
				ItemStack output = RecipeUtil.getRecipeOutput(compoundTag, world);
				if (output != null) {
					return ((BaseComponent) output.getHoverName()).append(Component.nullToEmpty(" ")).append(new TranslatableComponent(this.getDescriptionId(stack)));
				}
			}
		}
		return super.getName(stack);
	}
	
	@Override
	public Rarity getRarity(ItemStack stack) {

		CompoundTag compoundTag = stack.getTag();
		Minecraft client = Minecraft.getInstance();
		Level world = client.level;
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (world != null && world.isClientSide) {
				ItemStack output = RecipeUtil.getRecipeOutput(compoundTag, world);
				if (output != null) {
					switch (output.getRarity()) {
					case COMMON:
						return Rarity.UNCOMMON;
					default:
						return Rarity.RARE;
					}
				}
			}
		}
		return super.getRarity(stack);
	}

}
