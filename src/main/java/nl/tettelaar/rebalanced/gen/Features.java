package nl.tettelaar.rebalanced.gen;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.HeightmapDecoratorConfig;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.decorator.WaterDepthThresholdDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import nl.tettelaar.rebalanced.Rebalanced;

public class Features {

	static DataPool.Builder<BlockState> pool() {
		return DataPool.builder();
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(Identifier id, ConfiguredFeature<FC, ?> configuredFeature) {
		return (ConfiguredFeature<FC, ?>) Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
	}

	public static ConfiguredFeature<?, ?> SAPLING_FOREST_DEFAULT = null;

	public static void init () {
		SAPLING_FOREST_DEFAULT = register(new Identifier(Rebalanced.modid, "sapling_forest_default"), (ConfiguredFeature<?, ?>) Feature.FLOWER.configure(Features.Configs.SAPLING_FOREST_CONFIG).decorate(Features.Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER)/*.decorate(Decorator.COUNT.configure(new CountConfig(1)))*/.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(5, 0.1F, 1))));/*.decorate(Features.Decorators.SPREAD_32_ABOVE)/*.decorate(Features.Decorators.SQUARE_HEIGHTMAP)*/
	}

	protected static final class Decorators {
		public static final BeehiveTreeDecorator VERY_RARE_BEEHIVES_TREES = new BeehiveTreeDecorator(0.002F);
		public static final BeehiveTreeDecorator REGULAR_BEEHIVES_TREES = new BeehiveTreeDecorator(0.02F);
		public static final BeehiveTreeDecorator MORE_BEEHIVES_TREES = new BeehiveTreeDecorator(0.05F);
		public static final ConfiguredDecorator<HeightmapDecoratorConfig> HEIGHTMAP;
		public static final ConfiguredDecorator<HeightmapDecoratorConfig> TOP_SOLID_HEIGHTMAP;
		public static final ConfiguredDecorator<HeightmapDecoratorConfig> HEIGHTMAP_WORLD_SURFACE;
		public static final ConfiguredDecorator<HeightmapDecoratorConfig> HEIGHTMAP_OCEAN_FLOOR;
		public static final ConfiguredDecorator<HeightmapDecoratorConfig> HEIGHTMAP_SPREAD_DOUBLE;
		public static final ConfiguredDecorator<?> SPREAD_32_ABOVE;
		public static final ConfiguredDecorator<?> HEIGHTMAP_OCEAN_FLOOR_NO_WATER;
		public static final ConfiguredDecorator<?> SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER;
		public static final ConfiguredDecorator<?> SQUARE_HEIGHTMAP;
		public static final ConfiguredDecorator<?> SQUARE_HEIGHTMAP_SPREAD_DOUBLE;
		public static final ConfiguredDecorator<?> SQUARE_TOP_SOLID_HEIGHTMAP;

		static {
			HEIGHTMAP = Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING));
			TOP_SOLID_HEIGHTMAP = Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.OCEAN_FLOOR_WG));
			HEIGHTMAP_WORLD_SURFACE = Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.WORLD_SURFACE_WG));
			HEIGHTMAP_OCEAN_FLOOR = Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.OCEAN_FLOOR));
			HEIGHTMAP_SPREAD_DOUBLE = Decorator.HEIGHTMAP_SPREAD_DOUBLE.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING));
			SPREAD_32_ABOVE = Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE);
			HEIGHTMAP_OCEAN_FLOOR_NO_WATER = HEIGHTMAP_OCEAN_FLOOR.decorate(Decorator.WATER_DEPTH_THRESHOLD.configure(new WaterDepthThresholdDecoratorConfig(0)));
			SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER = (ConfiguredDecorator<?>) HEIGHTMAP_OCEAN_FLOOR_NO_WATER.spreadHorizontally();
			SQUARE_HEIGHTMAP = (ConfiguredDecorator<?>) HEIGHTMAP.spreadHorizontally();
			SQUARE_HEIGHTMAP_SPREAD_DOUBLE = (ConfiguredDecorator<?>) HEIGHTMAP_SPREAD_DOUBLE.spreadHorizontally();
			SQUARE_TOP_SOLID_HEIGHTMAP = (ConfiguredDecorator<?>) TOP_SOLID_HEIGHTMAP.spreadHorizontally();
		}
	}

	public static final class Configs {

		public static final RandomPatchFeatureConfig SAPLING_FOREST_CONFIG;

		static {
			SAPLING_FOREST_CONFIG = (new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(Features.pool().add(Blocks.OAK_SAPLING.getDefaultState(), 4).add(Blocks.BIRCH_SAPLING.getDefaultState(), 1)), SimpleBlockPlacer.INSTANCE)).tries(1).build();
		}
	}
}
