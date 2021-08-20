package nl.tettelaar.rebalanced.mixin.recipe;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.KnowledgeBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import nl.tettelaar.rebalanced.util.RecipeUtil;

@Mixin(KnowledgeBookItem.class)
public class KnowledgeBookItemClientMixin extends Item {

	public KnowledgeBookItemClientMixin(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rarity getRarity(ItemStack stack) {

		NbtCompound compoundTag = stack.getTag();
		World world = MinecraftClient.getInstance().world;
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (world != null && world.isClient) {
				Rarity highestRarity = null;
				for (Recipe<?> recipe : RecipeUtil.getRecipes(compoundTag, world)) {
					ItemStack item = recipe.getOutput();
					if (highestRarity != null) {
						switch (item.getRarity()) {
						case COMMON:
							break;
						case UNCOMMON:
							if (highestRarity != Rarity.COMMON) {
								highestRarity = item.getRarity();
							}
							break;
						case RARE:
							if (highestRarity != Rarity.COMMON && highestRarity != Rarity.UNCOMMON) {
								highestRarity = item.getRarity();
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
		return super.getRarity(stack);
	}

}
