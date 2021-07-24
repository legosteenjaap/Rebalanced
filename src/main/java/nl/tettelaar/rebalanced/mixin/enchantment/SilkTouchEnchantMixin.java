package nl.tettelaar.rebalanced.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.entity.EquipmentSlot;

@Mixin(SilkTouchEnchantment.class)
public class SilkTouchEnchantMixin extends Enchantment{
	
	protected SilkTouchEnchantMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
		// TODO Auto-generated constructor stub
	}

	public boolean isTreasure() {
		return true;
	}
	
}
