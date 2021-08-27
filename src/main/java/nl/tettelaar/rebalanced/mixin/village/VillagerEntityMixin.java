package nl.tettelaar.rebalanced.mixin.village;
import java.util.ArrayList;
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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.village.TradeOfferRebalanced;
import nl.tettelaar.rebalanced.village.TradeOffers;
import nl.tettelaar.rebalanced.village.TradeOffers.BuySupplyFactory;
import nl.tettelaar.rebalanced.village.TradeOffers.Factory;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	private static final boolean isVillagerTradeFixLoaded = FabricLoader.getInstance().isModLoaded("villagertradefix");

	// THIS CODE IS COPIED FROM THE VILLAGERFIX MOD
	// https://github.com/Globox1997/VillagerTradeFix/blob/master/src/main/java/net/villagerfix/mixin/VillagerEntityMixin.java

	private List<String> jobList = new ArrayList<String>();
	private List<TradeOfferList> offerList = new ArrayList<TradeOfferList>();	
	
	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	public void readCustomDataFromNbtMixin(NbtCompound nbt, CallbackInfo info) {
		for (int i = 0; i < nbt.getInt("JobCount"); ++i) {
			String jobString = "OldOffer" + i;
			jobList.add(nbt.getString(jobString + "OldWork"));
			if (nbt.contains(jobString, 10)) {
				offerList.add(new TradeOfferList(nbt.getCompound(jobString)));
			}
		}
	}

	// *********************************************************************************************************************************************************

	@Inject(method = "fillRecipes", at = @At("HEAD"), cancellable = true)
	private void fillRecipes(CallbackInfo ci) {
		VillagerData villagerData = this.getVillagerData();
		if (!isVillagerTradeFixLoaded || (this.jobList == null || !this.jobList.contains(((VillagerEntity) (Object) this).getVillagerData().getProfession().toString()))) {
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
			TradeOffer tradeOffer;
			if (factory instanceof BuySupplyFactory && random.nextInt(1) == 0) {
				tradeOffer = ((BuySupplyFactory)factory).createBuySupply(this, this.random);
				((TradeOfferRebalanced)tradeOffer).setTemporary();
			} else {
				tradeOffer = factory.create(this, this.random);
			}
			if (tradeOffer != null) {
				tradeList.add(tradeOffer);
			}
		}

		List<Pair<TradeOffers.Factory, Float>> knowledgeBookTrades = RecipeAPI.getKnowledgeBooksVillagerTrades(villagerData.getProfession(), villagerData.getLevel());

		if (knowledgeBookTrades != null) {
			Pair<TradeOffers.Factory, Float> knowledgeBookTrade = knowledgeBookTrades.get(this.random.nextInt(knowledgeBookTrades.size()));
			Factory factory = knowledgeBookTrade.getLeft();
			TradeOffer tradeOffer = factory.create(this, this.random);
			if (tradeOffer != null && this.random.nextFloat() <= knowledgeBookTrade.getRight()) {
				tradeList.add(tradeOffer);
				((TradeOfferRebalanced) tradeOffer).setTemporary();
			}
		}

	}

	@Inject(method = "afterUsing", at = @At("RETURN"))
	protected void afterUsing(TradeOffer offer, CallbackInfo ci) {
		if (((TradeOfferRebalanced) offer).isTemporary() && offer.getUses() == offer.getMaxUses()) {
			this.offers.remove(offer);
		}
	}


}
