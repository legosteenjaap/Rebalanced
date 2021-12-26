package nl.tettelaar.rebalanced.recipe.interfaces;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public interface ClientboundRecipePacketInterface {

    public void setIsDiscover();
    public boolean IsDiscover();
    public List<ResourceLocation> getDiscovered ();
    public void setDiscovered(List<ResourceLocation> discovered);

}
