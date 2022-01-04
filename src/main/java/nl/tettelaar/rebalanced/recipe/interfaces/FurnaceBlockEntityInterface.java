package nl.tettelaar.rebalanced.recipe.interfaces;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface FurnaceBlockEntityInterface {

    public void setOwner(Player player);
    public Player getOwner();
    public boolean isCookingRecipe();
    public void addInspectingPlayer(ServerPlayer player);
    public void removeInspectingPlayer(ServerPlayer player);
    public List<ServerPlayer> getInspectingPlayers();

}
