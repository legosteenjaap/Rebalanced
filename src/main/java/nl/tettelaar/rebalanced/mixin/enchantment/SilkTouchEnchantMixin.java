package nl.tettelaar.rebalanced.mixin.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.UntouchingEnchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(UntouchingEnchantment.class)
public class SilkTouchEnchantMixin extends Enchantment{
	
	protected SilkTouchEnchantMixin(Rarity weight, EnchantmentCategory type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
		// TODO Auto-generated constructor stub
	}

	public boolean isTreasureOnly() {
		return true;
	}
	
}
