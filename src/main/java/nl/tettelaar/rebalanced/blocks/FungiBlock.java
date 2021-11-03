package nl.tettelaar.rebalanced.blocks;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FungiBlock extends Block {
   public static final int MAX_DISTANCE = 17;
   public static final IntegerProperty DISTANCE;
   public static final BooleanProperty PERSISTENT;
   
   public FungiBlock(BlockBehaviour.Properties settings) {
      super(settings);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(DISTANCE, 1)).setValue(PERSISTENT, false));
   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Integer)state.getValue(DISTANCE) == MAX_DISTANCE && !(Boolean)state.getValue(PERSISTENT);
   }

   public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
      if (!(Boolean)state.getValue(PERSISTENT) && (Integer)state.getValue(DISTANCE) == MAX_DISTANCE) {
         dropResources(state, world, pos);
         world.removeBlock(pos, false);
      }

   }

   public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
      world.setBlock(pos, updateDistanceFromLogs(state, world, pos), Block.UPDATE_ALL);
   }

   public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
      return 1;
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
      int i = getDistanceFromLog(neighborState) + 1;
      if (i != 1 || (Integer)state.getValue(DISTANCE) != i) {
         world.scheduleTick(pos, this, 1);
      }

      return state;
   }

   private static BlockState updateDistanceFromLogs(BlockState state, LevelAccessor world, BlockPos pos) {
      int i = MAX_DISTANCE;
      BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
      Direction[] var5 = Direction.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Direction direction = var5[var7];
         mutable.setWithOffset(pos, (Direction)direction);
         i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
         if (i == 1) {
            break;
         }
      }

      return (BlockState)state.setValue(DISTANCE, i);
   }

   private static int getDistanceFromLog(BlockState state) {
      if (state.is(BlockTags.LOGS)) {
         return 0;
      } else {
         return state.getBlock() instanceof FungiBlock ? (Integer)state.getValue(DISTANCE) : MAX_DISTANCE;
      }
   }

   public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
      if (world.isRainingAt(pos.above())) {
         if (random.nextInt(15) == 1) {
            BlockPos blockPos = pos.below();
            BlockState blockState = world.getBlockState(blockPos);
            if (!blockState.canOcclude() || !blockState.isFaceSturdy(world, blockPos, Direction.UP)) {
               double d = (double)pos.getX() + random.nextDouble();
               double e = (double)pos.getY() - 0.05D;
               double f = (double)pos.getZ() + random.nextDouble();
               world.addParticle(ParticleTypes.DRIPPING_WATER, d, e, f, 0.0D, 0.0D, 0.0D);
            }
         }
      }
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(DISTANCE, PERSISTENT);
   }

   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      return updateDistanceFromLogs((BlockState)this.defaultBlockState().setValue(PERSISTENT, true), ctx.getLevel(), ctx.getClickedPos());
   }

   static {
      DISTANCE = IntegerProperty.create("distance", 1, MAX_DISTANCE);
      PERSISTENT = BlockStateProperties.PERSISTENT;
   }
}