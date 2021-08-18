package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.AreaHelper;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelMixin extends Item {

	public FlintAndSteelMixin(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "useOnBlock", at = @At("RETURN"))
	public void onPlaced(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		if (context.getPlayer() instanceof ServerPlayerEntity && (((ServerPlayerEntity) context.getPlayer()).getRecipeBook().contains(new Identifier("nether_portal")) || !(context.getWorld().getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING)))) {
			if (isOverworldOrNether(context.getWorld())) {
				Optional<AreaHelper> optional = AreaHelper.getNewPortal(context.getWorld(), context.getBlockPos().offset(context.getSide()), Direction.Axis.X);
				if (optional.isPresent()) {
					((AreaHelper) optional.get()).createPortal();
				}
			}
		}
	}

	private static boolean isOverworldOrNether(World world) {
		return world.getRegistryKey() == World.OVERWORLD || world.getRegistryKey() == World.NETHER;
	}

}
