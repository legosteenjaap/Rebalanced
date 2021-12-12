package nl.tettelaar.rebalanced.mixin.recipe.block;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireChargeItem.class)
public class FireChargeMixin extends Item {

	public FireChargeMixin(Properties settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "useOnBlock", at = @At("RETURN"))
	public void onPlaced(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (context.getPlayer() instanceof ServerPlayer && (((ServerPlayer) context.getPlayer()).getRecipeBook().contains(new ResourceLocation("nether_portal")) || !(context.getLevel().getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING)))) {
			if (isOverworldOrNether(context.getLevel())) {
				Optional<PortalShape> optional = PortalShape.findEmptyPortalShape(context.getLevel(), context.getClickedPos().relative(context.getClickedFace()), Direction.Axis.X);
				if (optional.isPresent()) {
					((PortalShape) optional.get()).createPortalBlocks();
				}
			}
		}
	}

	private static boolean isOverworldOrNether(Level world) {
		return world.dimension() == Level.OVERWORLD || world.dimension() == Level.NETHER;
	}

}
