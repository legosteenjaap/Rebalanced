package nl.tettelaar.rebalanced.api;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ClientAPI {

    private static int XPCost = 0;
    private static boolean isLevel;

    static int altTotalXP;
    static int altXPLevel;
    static float altXPProgress;

    public static void setXPCost(int experiencePoints) {
        XPCost = experiencePoints;
    }

    public static void setIsLevel(boolean isLevel) {
        ClientAPI.isLevel = isLevel;
    }

    public static int getXPCost() {
        return XPCost;
    }

    public static boolean isLevel() {
        return isLevel;
    }

    public static int getAltTotalXP() {
        return altTotalXP;
    }

    public static int getAltXPLevel() {
        return altXPLevel;
    }

    public static float getAltXPProgress() {
        return altXPProgress;
    }

    public static void updateAltXP(int cost, boolean isLevel) {
        Player player = Minecraft.getInstance().player;
        altXPProgress = player.experienceProgress;
        altTotalXP = player.totalExperience;
        altXPLevel = player.experienceLevel;
        if (!isLevel) {
            cost = -cost;
            altXPProgress += (float) cost / (float) getXpNeededForNextLevel(altXPLevel);
            altTotalXP = Mth.clamp(altTotalXP + cost, 0, Integer.MAX_VALUE);
            while (altXPProgress < 0.0f) {
                float f = altXPProgress * (float) getXpNeededForNextLevel(altXPLevel);
                if (altXPLevel > 0) {
                    altXPLevel = altXPLevel - 1;
                    altXPProgress = 1.0f + f / (float) getXpNeededForNextLevel(altXPLevel);
                    continue;
                }
                altXPLevel = altXPLevel - 1;
                altXPProgress = 0.0f;
            }
            while (altXPProgress >= 1.0f) {
                altXPProgress = (altXPProgress - 1.0f) * (float) getXpNeededForNextLevel(altXPLevel);
                altXPLevel++;
                altXPProgress /= (float) getXpNeededForNextLevel(altXPLevel);
            }
        } else {
            altXPLevel = altXPLevel - cost;
        }
    }

    private static int getXpNeededForNextLevel(int experienceLevel) {
        if (experienceLevel >= 30) {
            return 112 + (experienceLevel - 30) * 9;
        }
        if (experienceLevel >= 15) {
            return 37 + (experienceLevel - 15) * 5;
        }
        return 7 + experienceLevel * 2;
    }

}
