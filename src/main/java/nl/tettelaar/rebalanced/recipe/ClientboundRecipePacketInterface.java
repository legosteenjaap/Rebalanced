package nl.tettelaar.rebalanced.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public interface ClientboundRecipePacketInterface {

    public List<ResourceLocation> getDiscovered ();
    public void setDiscovered(List<ResourceLocation> discovered);

}
