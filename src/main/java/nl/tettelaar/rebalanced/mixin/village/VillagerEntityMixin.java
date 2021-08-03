package nl.tettelaar.rebalanced.mixin.village;

import java.util.Iterator;
import java.util.List;
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
import nl.tettelaar.rebalanced.TradeOffers;
import nl.tettelaar.rebalanced.TradeOffers.Factory;
import nl.tettelaar.rebalanced.api.RecipeAPI;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "fillRecipes", at = @At("HEAD"), cancellable = true)
	private void fillRecipes(CallbackInfo ci) {
		VillagerData villagerData = this.getVillagerData();
		Int2ObjectMap<TradeOffers.Factory[]> trades = (Int2ObjectMap<Factory[]>) TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(villagerData.getProfession());

		if (trades != null && !trades.isEmpty()) {
			TradeOffers.Factory[] factory = (TradeOffers.Factory[]) trades.get(villagerData.getLevel());
			if (factory != null) {
				TradeOfferList tradeOfferList = this.getOffers();
				this.fillTradesFromPool(tradeOfferList, factory, 2, villagerData);
			}
		}
		ci.cancel();
	}

	@Shadow
	public VillagerData getVillagerData() {
		return null;
	}

	protected void fillTradesFromPool(TradeOfferList tradeList, TradeOffers.Factory[] tradePool, int count, VillagerData villagerData) {
		Set<Integer> trades = Sets.newHashSet();
		if (tradePool.length > count) {
			while (trades.size() < count) {
				trades.add(this.random.nextInt(tradePool.length));
			}
		} else {
			for (int i = 0; i < tradePool.length; ++i) {
				trades.add(i);
			}
		}

		Iterator<Integer> iter = trades.iterator();

		while (iter.hasNext()) {
			Integer integer = (Integer) iter.next();
			TradeOffers.Factory factory = tradePool[integer];
			TradeOffer tradeOffer = factory.create(this, this.random);
			if (tradeOffer != null) {
				tradeList.add(tradeOffer);
			}
		}

		List<?> knowledgeBookTrades = RecipeAPI.getKnowledgeBooksVillager(villagerData.getProfession(), villagerData.getLevel());

		if (knowledgeBookTrades != null) {
			Factory factory = ((TradeOffers.Factory)knowledgeBookTrades.get(this.random.nextInt(knowledgeBookTrades.size())));
			TradeOffer tradeOffer = factory.create(this, this.random);
			if (tradeOffer != null) {
				tradeList.add(tradeOffer);
			}
		}

	}

}
