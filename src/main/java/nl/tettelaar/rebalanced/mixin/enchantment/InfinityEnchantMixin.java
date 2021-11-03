package nl.tettelaar.rebalanced.mixin.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.ArrowInfiniteEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArrowInfiniteEnchantment.class)
public class InfinityEnchantMixin extends Enchantment{
	
	protected InfinityEnchantMixin(Rarity weight, EnchantmentCategory type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
		// TODO Auto-generated constructor stub
	}

	public boolean isTreasureOnly() {
		return true;
	}
	
}
