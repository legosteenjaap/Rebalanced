package nl.tettelaar.rebalanced.util;

import net.minecraft.world.World;
import nl.tettelaar.rebalanced.RebalancedClient;

public class TimeUtil {

	public static long getDayTime(World world) {
		return world.getTimeOfDay() % 24000;
	}
	
	public static boolean isIdealTimeToRespawn(World world) {
		return (getDayTime(world) < RebalancedClient.latestRespawnTime || getDayTime(world) > RebalancedClient.earliestRespawnTime) && !world.getDimension().hasFixedTime();
	}
	
}
