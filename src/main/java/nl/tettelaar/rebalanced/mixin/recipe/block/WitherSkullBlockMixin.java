package nl.tettelaar.rebalanced.mixin.recipe.block;

import java.util.Iterator;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

@Mixin(WitherSkullBlock.class)
public class WitherSkullBlockMixin {

	@Inject(method = "setPlacedBy", at = @At("HEAD"), cancellable = true)
	private void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
		if (placer instanceof ServerPlayer && (!((ServerPlayer) placer).getRecipeBook().contains(new ResourceLocation("wither")) && world.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING))) {
			ci.cancel();
		}
	}

	@Inject(method = "checkSpawn", at = @At("HEAD"), cancellable = true)
	private static void checkSpawn(Level world, BlockPos pos, SkullBlockEntity blockEntity, CallbackInfo ci) {
		ci.cancel();
	}

	
	@Redirect(method = "setPlacedBy	", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/WitherSkullBlock;checkSpawn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/SkullBlockEntity;)V"))
	private void replaceCheckSpawn(Level level, BlockPos pos, SkullBlockEntity blockEntity) {
		if (!level.isClientSide) {
			BlockState blockState = blockEntity.getBlockState();
			boolean bl = blockState.is(Blocks.WITHER_SKELETON_SKULL) || blockState.is(Blocks.WITHER_SKELETON_WALL_SKULL);
			if (bl && pos.getY() >= level.getMinBuildHeight() && level.getDifficulty() != Difficulty.PEACEFUL) {
				BlockPattern blockPattern = getOrCreateWitherFull();
				BlockPattern.BlockPatternMatch result = blockPattern.find(level, pos);
				if (result != null) {
					for (int i = 0; i < blockPattern.getWidth(); ++i) {
						for (int j = 0; j < blockPattern.getHeight(); ++j) {
							BlockInWorld cachedBlockPosition = result.getBlock(i, j, 0);
							level.setBlock(cachedBlockPosition.getPos(), Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
							level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, cachedBlockPosition.getPos(), Block.getId(cachedBlockPosition.getState()));
						}
					}

					WitherBoss witherEntity = (WitherBoss) EntityType.WITHER.create(level);
					BlockPos blockPos = result.getBlock(1, 2, 0).getPos();
					witherEntity.moveTo((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.55D, (double) blockPos.getZ() + 0.5D, result.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F, 0.0F);
					witherEntity.yBodyRot = result.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
					witherEntity.makeInvulnerable();
					Iterator var13 = level.getEntitiesOfClass(ServerPlayer.class, witherEntity.getBoundingBox().inflate(50.0D)).iterator();

					while (var13.hasNext()) {
						ServerPlayer serverPlayerEntity = (ServerPlayer) var13.next();
						CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity, witherEntity);
					}

					level.addFreshEntity(witherEntity);

					for (int k = 0; k < blockPattern.getWidth(); ++k) {
						for (int l = 0; l < blockPattern.getHeight(); ++l) {
							level.blockUpdated(result.getBlock(k, l, 0).getPos(), Blocks.AIR);
						}
					}

				}
			}
		}
	}

	@Shadow
	private static BlockPattern getOrCreateWitherFull() {
		return null;
	}


}
