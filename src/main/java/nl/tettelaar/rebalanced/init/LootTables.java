package nl.tettelaar.rebalanced.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.AlternativeLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext.EntityTarget;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.predicate.PlayerPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import nl.tettelaar.rebalanced.api.RecipeAPI;

public class LootTables {

	public static void init() {
		Map<Identifier,ArrayList<ItemEntry>> lootPools = new HashMap<Identifier,ArrayList<ItemEntry>>();
		
		for (Pair<List<List<Identifier>>, List<Identifier>> book : RecipeAPI.getKnowledgeBooksLootTable()) {
			
			ArrayList<ItemEntry> itemEntries = new ArrayList<>();
			
			for (List<Identifier> recipes : book.getLeft()) {
				NbtList list = new NbtList();
				for (Identifier recipe : recipes) {
					list.add(NbtString.of(recipe.toString()));
				}
				NbtCompound nbt = new NbtCompound();
				nbt.put("Recipes", list);
				LootFunction.Builder lootFunction = SetNbtLootFunction.builder(nbt);
				
				PlayerPredicate.Builder builderPlayerPredicate = new PlayerPredicate.Builder();
				for (Identifier recipe : recipes) {
					builderPlayerPredicate = builderPlayerPredicate.recipe(recipe, false);
				}
				PlayerPredicate playerPredicate = builderPlayerPredicate.build();
				EntityPredicate.Builder builderEntityPredicate = new EntityPredicate.Builder();
				EntityPredicate entityPredicate = builderEntityPredicate.player(playerPredicate).build();
				LootCondition.Builder lootCondition = AlternativeLootCondition.builder((EntityPropertiesLootCondition.builder(EntityTarget.THIS, entityPredicate)), RandomChanceLootCondition.builder(0.2f));
				
				ItemEntry.Builder<?> itemBuilder = ItemEntry.builder(Items.KNOWLEDGE_BOOK);
				itemBuilder.apply(lootFunction);
				itemBuilder.conditionally(lootCondition);
				itemEntries.add((ItemEntry) itemBuilder.build());
			}
			for (Identifier loottable : book.getRight()) {
				ArrayList<ItemEntry> itemEntry = lootPools.get(loottable);
				if (itemEntry == null) {
					lootPools.put(loottable, itemEntries);
				} else {
					List<ItemEntry> itemEntriesCopy = new ArrayList<>(itemEntries);
			        itemEntriesCopy.removeAll(itemEntry);
					itemEntry.addAll(itemEntries);
					lootPools.put(loottable, itemEntry);
				}
			}
			
		}
		
		LootTableLoadingCallback.EVENT.register((((resourceManager, lootManager, id, supplier, setter) -> {
			if (lootPools.containsKey(id)) {
				FabricLootPoolBuilder lootPoolBuilder = FabricLootPoolBuilder.builder().rolls(BinomialLootNumberProvider.create(4, 0.17f));
	
				for (ItemEntry entry : lootPools.get(id)) {
					lootPoolBuilder = lootPoolBuilder.withEntry(entry);
				}
				supplier.pool(lootPoolBuilder);
			}
		})));
	}

}
