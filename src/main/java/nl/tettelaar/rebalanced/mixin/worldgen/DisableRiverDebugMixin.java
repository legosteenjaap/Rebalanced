package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.biome.layer.ApplyRiverLayer;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

@Mixin(ApplyRiverLayer.class)
public class DisableRiverDebugMixin implements MergingLayer, IdentityCoordinateTransformer {
	
	
	@Overwrite
	public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
	      int i = sampler1.sample(this.transformX(x), this.transformZ(z));
	      return i;
	      /*int j = sampler2.sample(this.transformX(x), this.transformZ(z));
	      if (BiomeLayers.isOcean(i)) {
	         return i;
	      } else if (j == BiomeIds.RIVER) {
	         if (i == BiomeIds.SNOWY_TUNDRA) {
	            return i;
	        	 //return BiomeIds.FROZEN_RIVER;
	         } else {
	            return i;
	        	 //return i != BiomeIds.MUSHROOM_FIELDS && i != BiomeIds.MUSHROOM_FIELD_SHORE ? j & 255 : BiomeIds.MUSHROOM_FIELD_SHORE;
	         }
	      } else {
	         return i;
	      }*/
	   }
}
