package nl.tettelaar.rebalanced.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.AreaHelper;

@Mixin(AbstractFireBlock.class)
public class FireBlockMixin {
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (isOverworldOrNether(world) && itemStack.getItem() == Items.NETHER_STAR) {
			Optional<AreaHelper> optional = AreaHelper.getNewPortal(world, pos, Direction.Axis.X);
			if (optional.isPresent()) {
				((AreaHelper) optional.get()).createPortal();
				if (!player.getAbilities().creativeMode) {	
				itemStack.decrement(1);
				}
				if (!world.isClient) {
					world.playSound(null, pos, SoundEvents.ENTITY_WITHER_AMBIENT , SoundCategory.BLOCKS, 0.2f, 1f);
		        }
				return ActionResult.success(world.isClient());
			}
		}
		return ActionResult.FAIL;

	}

	@Inject(method = "onBlockAdded", at = @At("HEAD"), cancellable = true)
	private void injected(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify,
			CallbackInfo ci) {
		if (!oldState.isOf(state.getBlock())) {
			if (!state.canPlaceAt(world, pos)) {
				world.removeBlock(pos, false);
			}
		}
		ci.cancel();
	}

	@Invoker("isOverworldOrNether")
	private static boolean isOverworldOrNether(World world) {
		throw new AssertionError();
	}
}
