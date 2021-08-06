package nl.tettelaar.rebalanced;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class FungiBlock extends Block {
   public static final int MAX_DISTANCE = 15;
   public static final IntProperty DISTANCE;
   public static final BooleanProperty PERSISTENT;
   
   public FungiBlock(AbstractBlock.Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(DISTANCE, 1)).with(PERSISTENT, false));
   }

   public boolean hasRandomTicks(BlockState state) {
      return (Integer)state.get(DISTANCE) == MAX_DISTANCE && !(Boolean)state.get(PERSISTENT);
   }

   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!(Boolean)state.get(PERSISTENT) && (Integer)state.get(DISTANCE) == MAX_DISTANCE) {
         dropStacks(state, world, pos);
         world.removeBlock(pos, false);
      }

   }

   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), Block.NOTIFY_ALL);
   }

   public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
      return 1;
   }

   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
      int i = getDistanceFromLog(neighborState) + 1;
      if (i != 1 || (Integer)state.get(DISTANCE) != i) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      return state;
   }

   private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
      int i = MAX_DISTANCE;
      BlockPos.Mutable mutable = new BlockPos.Mutable();
      Direction[] var5 = Direction.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Direction direction = var5[var7];
         mutable.set(pos, (Direction)direction);
         i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
         if (i == 1) {
            break;
         }
      }

      return (BlockState)state.with(DISTANCE, i);
   }

   private static int getDistanceFromLog(BlockState state) {
      if (state.isIn(BlockTags.LOGS)) {
         return 0;
      } else {
         return state.getBlock() instanceof FungiBlock ? (Integer)state.get(DISTANCE) : MAX_DISTANCE;
      }
   }

   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (world.hasRain(pos.up())) {
         if (random.nextInt(15) == 1) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            if (!blockState.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
               double d = (double)pos.getX() + random.nextDouble();
               double e = (double)pos.getY() - 0.05D;
               double f = (double)pos.getZ() + random.nextDouble();
               world.addParticle(ParticleTypes.DRIPPING_WATER, d, e, f, 0.0D, 0.0D, 0.0D);
            }
         }
      }
   }

   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(DISTANCE, PERSISTENT);
   }

   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return updateDistanceFromLogs((BlockState)this.getDefaultState().with(PERSISTENT, true), ctx.getWorld(), ctx.getBlockPos());
   }

   static {
      DISTANCE = IntProperty.of("distance", 1, MAX_DISTANCE);
      PERSISTENT = Properties.PERSISTENT;
   }
}