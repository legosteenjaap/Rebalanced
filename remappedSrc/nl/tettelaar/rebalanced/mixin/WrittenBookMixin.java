package nl.tettelaar.rebalanced.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Vanishable;
import net.minecraft.item.WrittenBookItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WrittenBookItem.class)
public class WrittenBookMixin extends Item implements Vanishable {

	public WrittenBookMixin(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

}
