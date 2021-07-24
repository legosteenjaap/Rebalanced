package nl.tettelaar.rebalanced.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.entity.EquipmentSlot;

@Mixin(LuckEnchantment.class)
public class LuckEnchantMixin extends Enchantment {

	protected LuckEnchantMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
		// TODO Auto-generated constructor stub
	}

	public boolean isTreasure() {
		return true;
	}

}
