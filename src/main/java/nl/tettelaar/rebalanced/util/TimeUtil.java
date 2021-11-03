package nl.tettelaar.rebalanced.util;

import net.minecraft.world.level.Level;
import nl.tettelaar.rebalanced.RebalancedClient;

public class TimeUtil {

	public static long getDayTime(Level world) {
		return world.getDayTime() % 24000;
	}
	
	public static boolean isIdealTimeToRespawn(Level world) {
		return (getDayTime(world) < RebalancedClient.latestRespawnTime || getDayTime(world) > RebalancedClient.earliestRespawnTime) && !world.dimensionType().hasFixedTime();
	}
	
}
