package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.Iterator;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WitherSkullBlock;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

@Mixin(WitherSkullBlock.class)
public class WitherSkullBlockMixin {

	@Inject(method = "onPlaced", at = @At("HEAD"), cancellable = true)
	private void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
		if (placer instanceof ServerPlayerEntity && (!((ServerPlayerEntity) placer).getRecipeBook().contains(new Identifier("wither")) && world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING))) {
			ci.cancel();
		}
	}

	@Inject(method = "onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/SkullBlockEntity;)V", at = @At("HEAD"), cancellable = true)
	private static void onPlaced(World world, BlockPos pos, SkullBlockEntity blockEntity, CallbackInfo ci) {
		ci.cancel();
	}

	
	@Redirect(method = "onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/WitherSkullBlock;onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/SkullBlockEntity;)V"))
	private void replaceOnPlaced(World world, BlockPos pos, SkullBlockEntity blockEntity) {
		if (!world.isClient) {
			BlockState blockState = blockEntity.getCachedState();
			boolean bl = blockState.isOf(Blocks.WITHER_SKELETON_SKULL) || blockState.isOf(Blocks.WITHER_SKELETON_WALL_SKULL);
			if (bl && pos.getY() >= world.getBottomY() && world.getDifficulty() != Difficulty.PEACEFUL) {
				BlockPattern blockPattern = getWitherBossPattern();
				BlockPattern.Result result = blockPattern.searchAround(world, pos);
				if (result != null) {
					for (int i = 0; i < blockPattern.getWidth(); ++i) {
						for (int j = 0; j < blockPattern.getHeight(); ++j) {
							CachedBlockPosition cachedBlockPosition = result.translate(i, j, 0);
							world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
							world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
						}
					}

					WitherEntity witherEntity = (WitherEntity) EntityType.WITHER.create(world);
					BlockPos blockPos = result.translate(1, 2, 0).getBlockPos();
					witherEntity.refreshPositionAndAngles((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.55D, (double) blockPos.getZ() + 0.5D, result.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F, 0.0F);
					witherEntity.bodyYaw = result.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
					witherEntity.onSummoned();
					Iterator var13 = world.getNonSpectatingEntities(ServerPlayerEntity.class, witherEntity.getBoundingBox().expand(50.0D)).iterator();

					while (var13.hasNext()) {
						ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) var13.next();
						Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, witherEntity);
					}

					world.spawnEntity(witherEntity);

					for (int k = 0; k < blockPattern.getWidth(); ++k) {
						for (int l = 0; l < blockPattern.getHeight(); ++l) {
							world.updateNeighbors(result.translate(k, l, 0).getBlockPos(), Blocks.AIR);
						}
					}

				}
			}
		}
	}

	@Shadow
	private static BlockPattern getWitherBossPattern() {
		return null;
	}


}
