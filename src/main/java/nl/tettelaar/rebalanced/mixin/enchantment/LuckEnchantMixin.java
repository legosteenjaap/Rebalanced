package nl.tettelaar.rebalanced.mixin.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.LootBonusEnchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LootBonusEnchantment.class)
public class LuckEnchantMixin extends Enchantment {

	protected LuckEnchantMixin(Rarity weight, EnchantmentCategory type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
		// TODO Auto-generated constructor stub
	}

	public boolean isTreasureOnly() {
		return true;
	}

}
