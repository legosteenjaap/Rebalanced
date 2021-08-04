package nl.tettelaar.rebalanced.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.entity.EquipmentSlot;

@Mixin(InfinityEnchantment.class)
public class InfinityEnchantMixin extends Enchantment{
	
	protected InfinityEnchantMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
		// TODO Auto-generated constructor stub
	}

	public boolean isTreasure() {
		return true;
	}
	
}
