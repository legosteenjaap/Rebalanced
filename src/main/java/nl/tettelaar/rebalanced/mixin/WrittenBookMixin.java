package nl.tettelaar.rebalanced.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.WrittenBookItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WrittenBookItem.class)
public class WrittenBookMixin extends Item implements Vanishable {

	public WrittenBookMixin(Properties settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

}
