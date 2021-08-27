package nl.tettelaar.rebalanced.mixin.village;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Sets;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.util.Pair;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.village.TradeOfferRebalanced;
import nl.tettelaar.rebalanced.village.TradeOffers;
import nl.tettelaar.rebalanced.village.TradeOffers.Factory;
import nl.tettelaar.rebalanced.village.TradeOffers.SellItemFactory;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity {

	public WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "fillRecipes", at = @At("HEAD"), cancellable = true)
	protected void fillRecipes(CallbackInfo ci) {
		TradeOffers.Factory[] factorys = (TradeOffers.Factory[]) TradeOffers.WANDERING_TRADER_TRADES.get(1);
		TradeOffers.Factory[] factorys2 = (TradeOffers.Factory[]) TradeOffers.WANDERING_TRADER_TRADES.get(2);
		if (factorys != null && factorys2 != null) {
			TradeOfferList tradeOfferList = this.getOffers();
			this.fillRecipesFromPool(tradeOfferList, factorys, 10);
			int i = this.random.nextInt(factorys2.length);
			TradeOffers.Factory factory = factorys2[i];
			TradeOffer tradeOffer;
			if (factory instanceof SellItemFactory && this.random.nextInt(4) == 0) {
				tradeOffer = ((SellItemFactory) factory).createWanderingTraderDeal(this, this.random);
			} else {
				tradeOffer = factory.create(this, this.random);
			}
			if (tradeOffer != null) {
				tradeOfferList.add(tradeOffer);
			}
		}
		ci.cancel();
	}

	
	protected void fillRecipesFromPool(TradeOfferList tradeOfferList, TradeOffers.Factory[] pool, int count) {
		Set<Integer> set = Sets.newHashSet();
		if (pool.length > count) {
			while (set.size() < count) {
				set.add(this.random.nextInt(pool.length));
			}
		} else {
			for (int i = 0; i < pool.length; ++i) {
				set.add(i);
			}
		}

		Iterator<Integer> var9 = set.iterator();

		while (var9.hasNext()) {
			Integer integer = (Integer) var9.next();
			TradeOffers.Factory factory = pool[integer];
			TradeOffer tradeOffer;
			if (factory instanceof SellItemFactory && this.random.nextInt(4) == 0) {
				tradeOffer = ((SellItemFactory) factory).createWanderingTraderDeal(this, this.random);
			} else {
				tradeOffer = factory.create(this, this.random);
			}
			if (tradeOffer != null) {
				tradeOfferList.add(tradeOffer);
			}
		}
		
		List<Pair<TradeOffers.Factory, Float>> knowledgeBookTrades = RecipeAPI.getWanderingTraderBooks();

		if (knowledgeBookTrades != null) {
			Pair<TradeOffers.Factory, Float> knowledgeBookTrade = knowledgeBookTrades.get(this.random.nextInt(knowledgeBookTrades.size()));
			Factory factory = knowledgeBookTrade.getLeft();
			TradeOffer tradeOffer = factory.create(this, this.random);
			if (tradeOffer != null && this.random.nextFloat() <= knowledgeBookTrade.getRight()) {
				tradeOfferList.add(tradeOffer);
				((TradeOfferRebalanced) tradeOffer).setTemporary();
			}
		}
	}

}
