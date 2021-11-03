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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.village.TradeOfferRebalanced;
import nl.tettelaar.rebalanced.village.TradeOffers;
import nl.tettelaar.rebalanced.village.TradeOffers.BuySupplyFactory;
import nl.tettelaar.rebalanced.village.TradeOffers.Factory;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin extends AbstractVillager {
	public VillagerEntityMixin(EntityType<? extends AbstractVillager> entityType, Level world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	private static final boolean isVillagerTradeFixLoaded = FabricLoader.getInstance().isModLoaded("villagertradefix");

	// THIS CODE IS COPIED FROM THE VILLAGERFIX MOD
	// https://github.com/Globox1997/VillagerTradeFix/blob/master/src/main/java/net/villagerfix/mixin/VillagerEntityMixin.java

	private List<String> jobList = new ArrayList<String>();
	private List<MerchantOffers> offerList = new ArrayList<MerchantOffers>();	
	
	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveDataMixin(CompoundTag nbt, CallbackInfo info) {
		for (int i = 0; i < nbt.getInt("JobCount"); ++i) {
			String jobString = "OldOffer" + i;
			jobList.add(nbt.getString(jobString + "OldWork"));
			if (nbt.contains(jobString, 10)) {
				offerList.add(new MerchantOffers(nbt.getCompound(jobString)));
			}
		}
	}

	// *********************************************************************************************************************************************************

	@Inject(method = "updateTrades", at = @At("HEAD"), cancellable = true)
	private void updateTrades(CallbackInfo ci) {
		VillagerData villagerData = this.getVillagerData();
		if (!isVillagerTradeFixLoaded || (this.jobList == null || !this.jobList.contains(((Villager) (Object) this).getVillagerData().getProfession().toString()))) {
			Int2ObjectMap<TradeOffers.Factory[]> trades = (Int2ObjectMap<Factory[]>) TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(villagerData.getProfession());

			if (trades != null && !trades.isEmpty()) {
				TradeOffers.Factory[] factory = (TradeOffers.Factory[]) trades.get(villagerData.getLevel());
				if (factory != null) {
					MerchantOffers tradeOfferList = this.getOffers();
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

	protected void fillTradesFromPool(MerchantOffers tradeList, TradeOffers.Factory[] tradePool, int count, VillagerData villagerData) {
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
			MerchantOffer tradeOffer;
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

		List<Tuple<TradeOffers.Factory, Float>> knowledgeBookTrades = RecipeAPI.getKnowledgeBooksVillagerTrades(villagerData.getProfession(), villagerData.getLevel());

		if (knowledgeBookTrades != null) {
			Tuple<TradeOffers.Factory, Float> knowledgeBookTrade = knowledgeBookTrades.get(this.random.nextInt(knowledgeBookTrades.size()));
			Factory factory = knowledgeBookTrade.getA();
			MerchantOffer tradeOffer = factory.create(this, this.random);
			if (tradeOffer != null && this.random.nextFloat() <= knowledgeBookTrade.getB()) {
				tradeList.add(tradeOffer);
				((TradeOfferRebalanced) tradeOffer).setTemporary();
			}
		}

	}

	@Inject(method = "rewardTradeXp", at = @At("RETURN"))
	protected void rewardTradeXp(MerchantOffer offer, CallbackInfo ci) {
		if (((TradeOfferRebalanced) offer).isTemporary() && offer.getUses() == offer.getMaxUses()) {
			this.offers.remove(offer);
		}
	}


}
