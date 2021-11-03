package nl.tettelaar.rebalanced.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import nl.tettelaar.rebalanced.api.RecipeAPI;

public class LootTables {

	public static void init() {
		Map<ResourceLocation,ArrayList<LootPoolSingletonContainer>> lootPools = new HashMap<ResourceLocation,ArrayList<LootPoolSingletonContainer>>();
		
		for (Tuple<List<List<ResourceLocation>>, List<ResourceLocation>> book : RecipeAPI.getKnowledgeBooksLootTable()) {
			
			ArrayList<LootPoolSingletonContainer> itemEntries = new ArrayList<>();
			
			for (List<ResourceLocation> recipes : book.getA()) {
				ListTag list = new ListTag();
				for (ResourceLocation recipe : recipes) {
					list.add(StringTag.valueOf(recipe.toString()));
				}
				CompoundTag nbt = new CompoundTag();
				nbt.put("Recipes", list);
				LootItemFunction.Builder lootFunction = SetNbtFunction.setTag(nbt);
				
				PlayerPredicate.Builder builderPlayerPredicate = new PlayerPredicate.Builder();
				for (ResourceLocation recipe : recipes) {
					builderPlayerPredicate = builderPlayerPredicate.addRecipe(recipe, false);
				}
				PlayerPredicate playerPredicate = builderPlayerPredicate.build();
				EntityPredicate.Builder builderEntityPredicate = new EntityPredicate.Builder();
				EntityPredicate entityPredicate = builderEntityPredicate.player(playerPredicate).build();
				LootItemCondition.Builder lootCondition = AlternativeLootItemCondition.alternative((LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, entityPredicate)), LootItemRandomChanceCondition.randomChance(0.2f));

				LootPoolSingletonContainer.Builder itemBuilder = LootItem.lootTableItem(Items.KNOWLEDGE_BOOK);
				itemBuilder.apply(lootFunction);
				itemBuilder.when(lootCondition);
				itemEntries.add((LootItem) itemBuilder.build());
			}
			for (ResourceLocation loottable : book.getB()) {
				ArrayList<LootPoolSingletonContainer> itemEntry = lootPools.get(loottable);
				if (itemEntry == null) {
					lootPools.put(loottable, itemEntries);
				} else {
					List<LootPoolEntryContainer> itemEntriesCopy = new ArrayList<>(itemEntries);
			        itemEntriesCopy.removeAll(itemEntry);
					itemEntry.addAll(itemEntries);
					lootPools.put(loottable, itemEntry);
				}
			}
			
		}
		
		LootTableLoadingCallback.EVENT.register((((resourceManager, lootManager, id, supplier, setter) -> {
			if (lootPools.containsKey(id)) {
				FabricLootPoolBuilder lootPoolBuilder = FabricLootPoolBuilder.builder().rolls(BinomialDistributionGenerator.binomial(4, 0.17f));
	
				for (LootPoolEntryContainer entry : lootPools.get(id)) {
					lootPoolBuilder = lootPoolBuilder.withEntry(entry);
				}
				supplier.pool(lootPoolBuilder);
			}
		})));
	}

}
