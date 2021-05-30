package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Vanishable;
import net.minecraft.item.WrittenBookItem;

@Mixin(WrittenBookItem.class)
public class WrittenBookMixin extends Item implements Vanishable {

	public WrittenBookMixin(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

}
