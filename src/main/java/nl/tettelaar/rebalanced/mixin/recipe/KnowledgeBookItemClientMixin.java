package nl.tettelaar.rebalanced.mixin.recipe;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.KnowledgeBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.BaseText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
	public Text getName(ItemStack stack) {
		NbtCompound compoundTag = stack.getTag();
		MinecraftClient client = MinecraftClient.getInstance();
		World world = client.world;
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (world != null && world.isClient) {
				ItemStack output = RecipeUtil.getRecipeOutput(compoundTag, world);
				if (output != null) {
					return ((BaseText) output.getName()).append(Text.of(" ")).append(new TranslatableText(this.getTranslationKey(stack)));
				}
			}
		}
		return super.getName(stack);
	}
	
	@Override
	public Rarity getRarity(ItemStack stack) {

		NbtCompound compoundTag = stack.getTag();
		MinecraftClient client = MinecraftClient.getInstance();
		World world = client.world;
		if (compoundTag != null && compoundTag.contains("Recipes", 9)) {
			if (world != null && world.isClient) {
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
