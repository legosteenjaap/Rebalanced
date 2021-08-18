package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.Iterator;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin extends HorizontalFacingBlock {

	protected CarvedPumpkinBlockMixin(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "onBlockAdded", at = @At("HEAD"), cancellable = true)
	private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
		ci.cancel();
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer instanceof ServerPlayerEntity) {
			trySpawnEntity(world, pos, (ServerPlayerEntity) placer);
		}
	}

	private void trySpawnEntity(World world, BlockPos pos, ServerPlayerEntity player) {
		BlockPattern.Result result = this.getSnowGolemPattern().searchAround(world, pos);
		int k;
		Iterator<?> var6;
		ServerPlayerEntity serverPlayerEntity2;
		int m;
		if (result != null && (player.getRecipeBook().contains(new Identifier("snow_golem")) || !world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING))) {
			for (k = 0; k < this.getSnowGolemPattern().getHeight(); ++k) {
				CachedBlockPosition cachedBlockPosition = result.translate(0, k, 0);
				world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
				world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
			}

			SnowGolemEntity snowGolemEntity = (SnowGolemEntity) EntityType.SNOW_GOLEM.create(world);
			BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
			snowGolemEntity.refreshPositionAndAngles((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D, 0.0F, 0.0F);
			world.spawnEntity(snowGolemEntity);
			var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, snowGolemEntity.getBoundingBox().expand(5.0D)).iterator();

			while (var6.hasNext()) {
				serverPlayerEntity2 = (ServerPlayerEntity) var6.next();
				Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity2, snowGolemEntity);
			}

			for (m = 0; m < this.getSnowGolemPattern().getHeight(); ++m) {
				CachedBlockPosition cachedBlockPosition2 = result.translate(0, m, 0);
				world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
			}
		} else if (player.getRecipeBook().contains(new Identifier("iron_golem")) || !world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING)) {
			result = this.getIronGolemPattern().searchAround(world, pos);
			if (result != null) {
				for (k = 0; k < this.getIronGolemPattern().getWidth(); ++k) {
					for (int l = 0; l < this.getIronGolemPattern().getHeight(); ++l) {
						CachedBlockPosition cachedBlockPosition3 = result.translate(k, l, 0);
						world.setBlockState(cachedBlockPosition3.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
						world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition3.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition3.getBlockState()));
					}
				}

				BlockPos blockPos2 = result.translate(1, 2, 0).getBlockPos();
				IronGolemEntity ironGolemEntity = (IronGolemEntity) EntityType.IRON_GOLEM.create(world);
				ironGolemEntity.setPlayerCreated(true);
				ironGolemEntity.refreshPositionAndAngles((double) blockPos2.getX() + 0.5D, (double) blockPos2.getY() + 0.05D, (double) blockPos2.getZ() + 0.5D, 0.0F, 0.0F);
				world.spawnEntity(ironGolemEntity);
				var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, ironGolemEntity.getBoundingBox().expand(5.0D)).iterator();

				while (var6.hasNext()) {
					serverPlayerEntity2 = (ServerPlayerEntity) var6.next();
					Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity2, ironGolemEntity);
				}

				for (m = 0; m < this.getIronGolemPattern().getWidth(); ++m) {
					for (int n = 0; n < this.getIronGolemPattern().getHeight(); ++n) {
						CachedBlockPosition cachedBlockPosition4 = result.translate(m, n, 0);
						world.updateNeighbors(cachedBlockPosition4.getBlockPos(), Blocks.AIR);
					}
				}
			}
		}
	}

	@Shadow
	private BlockPattern getSnowGolemPattern() {
		return null;
	}

	@Shadow
	private BlockPattern getIronGolemPattern() {
		return null;
	}

}
