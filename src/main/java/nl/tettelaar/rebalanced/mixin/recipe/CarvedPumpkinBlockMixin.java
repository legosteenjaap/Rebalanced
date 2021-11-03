package nl.tettelaar.rebalanced.mixin.recipe;

import java.util.Iterator;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin extends HorizontalDirectionalBlock {

	protected CarvedPumpkinBlockMixin(Properties settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "onPlace", at = @At("HEAD"), cancellable = true)
	private void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
		ci.cancel();
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer instanceof ServerPlayer) {
			trySpawnEntity(world, pos, (ServerPlayer) placer);
		}
	}

	private void trySpawnEntity(Level world, BlockPos pos, ServerPlayer player) {
		BlockPattern.BlockPatternMatch result = this.getOrCreateSnowGolemFull().find(world, pos);
		int k;
		Iterator<?> var6;
		ServerPlayer serverPlayerEntity2;
		int m;
		if (result != null && (player.getRecipeBook().contains(new ResourceLocation("snow_golem")) || !world.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING))) {
			for (k = 0; k < this.getOrCreateSnowGolemFull().getHeight(); ++k) {
				BlockInWorld cachedBlockPosition = result.getBlock(0, k, 0);
				world.setBlock(cachedBlockPosition.getPos(), Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
				world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, cachedBlockPosition.getPos(), Block.getId(cachedBlockPosition.getState()));
			}

			SnowGolem snowGolemEntity = (SnowGolem) EntityType.SNOW_GOLEM.create(world);
			BlockPos blockPos = result.getBlock(0, 2, 0).getPos();
			snowGolemEntity.moveTo((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D, 0.0F, 0.0F);
			world.addFreshEntity(snowGolemEntity);
			var6 = world.getEntitiesOfClass(ServerPlayer.class, snowGolemEntity.getBoundingBox().inflate(5.0D)).iterator();

			while (var6.hasNext()) {
				serverPlayerEntity2 = (ServerPlayer) var6.next();
				CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity2, snowGolemEntity);
			}

			for (m = 0; m < this.getOrCreateSnowGolemFull().getHeight(); ++m) {
				BlockInWorld cachedBlockPosition2 = result.getBlock(0, m, 0);
				world.blockUpdated(cachedBlockPosition2.getPos(), Blocks.AIR);
			}
		} else if (player.getRecipeBook().contains(new ResourceLocation("iron_golem")) || !world.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING)) {
			result = this.getOrCreateIronGolemFull().find(world, pos);
			if (result != null) {
				for (k = 0; k < this.getOrCreateIronGolemFull().getWidth(); ++k) {
					for (int l = 0; l < this.getOrCreateIronGolemFull().getHeight(); ++l) {
						BlockInWorld cachedBlockPosition3 = result.getBlock(k, l, 0);
						world.setBlock(cachedBlockPosition3.getPos(), Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
						world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, cachedBlockPosition3.getPos(), Block.getId(cachedBlockPosition3.getState()));
					}
				}

				BlockPos blockPos2 = result.getBlock(1, 2, 0).getPos();
				IronGolem ironGolemEntity = (IronGolem) EntityType.IRON_GOLEM.create(world);
				ironGolemEntity.setPlayerCreated(true);
				ironGolemEntity.moveTo((double) blockPos2.getX() + 0.5D, (double) blockPos2.getY() + 0.05D, (double) blockPos2.getZ() + 0.5D, 0.0F, 0.0F);
				world.addFreshEntity(ironGolemEntity);
				var6 = world.getEntitiesOfClass(ServerPlayer.class, ironGolemEntity.getBoundingBox().inflate(5.0D)).iterator();

				while (var6.hasNext()) {
					serverPlayerEntity2 = (ServerPlayer) var6.next();
					CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity2, ironGolemEntity);
				}

				for (m = 0; m < this.getOrCreateIronGolemFull().getWidth(); ++m) {
					for (int n = 0; n < this.getOrCreateIronGolemFull().getHeight(); ++n) {
						BlockInWorld cachedBlockPosition4 = result.getBlock(m, n, 0);
						world.blockUpdated(cachedBlockPosition4.getPos(), Blocks.AIR);
					}
				}
			}
		}
	}

	@Shadow
	private BlockPattern getOrCreateSnowGolemFull() {
		return null;
	}

	@Shadow
	private BlockPattern getOrCreateIronGolemFull() {
		return null;
	}

}
