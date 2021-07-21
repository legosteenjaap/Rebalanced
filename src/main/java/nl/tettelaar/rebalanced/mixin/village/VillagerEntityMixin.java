package nl.tettelaar.rebalanced.mixin.village;

import java.util.Iterator;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import nl.tettelaar.rebalanced.util.TradeOffers;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "fillRecipes", at = @At("HEAD"), cancellable = true)
	private void fillRecipes(CallbackInfo ci) {
		VillagerData villagerData = this.getVillagerData();
		Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap = (Int2ObjectMap) TradeOffers.PROFESSION_TO_LEVELED_TRADE
				.get(villagerData.getProfession());
		if (int2ObjectMap != null && !int2ObjectMap.isEmpty()) {
			TradeOffers.Factory[] factorys = (TradeOffers.Factory[]) int2ObjectMap.get(villagerData.getLevel());
			if (factorys != null) {
				TradeOfferList tradeOfferList = this.getOffers();
				this.fillRecipesFromPool(tradeOfferList, factorys, 2);
			}
		}
		ci.cancel();
	}

	@Shadow
	public VillagerData getVillagerData() {
		return null;
	}
	
	protected void fillRecipesFromPool(TradeOfferList recipeList, TradeOffers.Factory[] pool, int count) {
	      Set<Integer> set = Sets.newHashSet();
	      if (pool.length > count) {
	         while(set.size() < count) {
	            set.add(this.random.nextInt(pool.length));
	         }
	      } else {
	         for(int i = 0; i < pool.length; ++i) {
	            set.add(i);
	         }
	      }

	      Iterator var9 = set.iterator();

	      while(var9.hasNext()) {
	         Integer integer = (Integer)var9.next();
	         TradeOffers.Factory factory = pool[integer];
	         TradeOffer tradeOffer = factory.create(this, this.random);
	         if (tradeOffer != null) {
	            recipeList.add(tradeOffer);
	         }
	      }

	   }

}
