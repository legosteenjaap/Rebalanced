package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.layer.AddMushroomIslandLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(AddMushroomIslandLayer.class)
public class MoreIslandsMixin {

	@Inject(method = "sample", at = @At("HEAD"), cancellable = true)
	public void sample(LayerRandomnessSource context, int sw, int se, int ne, int nw, int center, CallbackInfoReturnable<Integer> cir) {
	      switch (center) {
	      case BiomeIds.DEEP_WARM_OCEAN:
	      case BiomeIds.WARM_OCEAN:
	    	  if (isIslandPossible(sw, se, ne, nw, center, center) && context.nextInt(25) == 0) cir.setReturnValue(context.nextInt(3) == 0 ? BiomeIds.JUNGLE: BiomeIds.BAMBOO_JUNGLE);
	    	  break;
	      case BiomeIds.DEEP_LUKEWARM_OCEAN:
	      case BiomeIds.LUKEWARM_OCEAN:
	    	  //if (isIslandPossible(sw, se, ne, nw, center, center) && context.nextInt(25) == 0) cir.setReturnValue(context.nextInt(3) == 0 ? BiomeIds.SAVANNA_PLATEAU : BiomeIds.SAVANNA);
	    	  if (isIslandPossible(sw, se, ne, nw, center, center) && context.nextInt(50) == 0) cir.setReturnValue(context.nextInt(3) == 0 ? BiomeIds.SHATTERED_SAVANNA_PLATEAU : BiomeIds.SHATTERED_SAVANNA);
	    	  break;
	      case BiomeIds.DEEP_OCEAN:
	      case BiomeIds.OCEAN:
	    	  if (isIslandPossible(sw, se, ne, nw, center, center) && context.nextInt(20) == 0) cir.setReturnValue(BiomeIds.FOREST);
	    	  if (isIslandPossible(sw, se, ne, nw, center, BiomeIds.DEEP_OCEAN) && context.nextInt(100) == 0) cir.setReturnValue(BiomeIds.MUSHROOM_FIELDS);
	    	  break;
	      case BiomeIds.DEEP_COLD_OCEAN:
	      case BiomeIds.COLD_OCEAN:
	    	  if (isIslandPossible(sw, se, ne, nw, center, center) && context.nextInt(20) == 0) cir.setReturnValue(context.nextInt(3) == 0 ? BiomeIds.TAIGA: BiomeIds.GIANT_SPRUCE_TAIGA);
	    	  if (isIslandPossible(sw, se, ne, nw, center, center) && context.nextInt(40) == 0) cir.setReturnValue(BiomeIds.MOUNTAINS);
	    	  break;
	      case BiomeIds.DEEP_FROZEN_OCEAN:
	      case BiomeIds.FROZEN_OCEAN:
	    	  if (isIslandPossible(sw, se, ne, nw, center, center) && context.nextInt(20) == 0) cir.setReturnValue(context.nextInt(3) == 0 ? BiomeIds.SNOWY_TAIGA_HILLS :BiomeIds.SNOWY_TAIGA);
	    	  if (isIslandPossible(sw, se, ne, nw, center, center) && context.nextInt(80) == 0) cir.setReturnValue(BiomeIds.ICE_SPIKES);
	    	  break;
	      }
	      if (cir.getReturnValue() == null) cir.setReturnValue(center);
	}
	
	public boolean isIslandPossible (int sw , int se, int ne, int nw, int center, int oceanType) {
		return sw == oceanType && se == oceanType && ne == oceanType && nw == oceanType && center == oceanType;
	}
	
}