package nl.tettelaar.rebalanced.xp.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface XPBlockInterface {

    public int getXPCost(Player player, BlockPos blockPos, Level level);
    public boolean isLevel();

}
