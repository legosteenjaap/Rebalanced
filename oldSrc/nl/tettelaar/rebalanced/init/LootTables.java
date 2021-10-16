package nl.tettelaar.rebalanced.init;

import java.util.ArrayList;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.util.Identifier;

public class LootTables {

	private static final ArrayList<Identifier> lootTableList = new ArrayList<Identifier>();

	public static void init() {

		lootTableList.add(new Identifier("chests/simple_dungeon"));
		lootTableList.add(new Identifier("chests/abandoned_mineshaft"));
		lootTableList.add(new Identifier("chests/buried_treasure"));
		lootTableList.add(new Identifier("chests/desert_pyramid"));
		lootTableList.add(new Identifier("chests/igloo_chest"));
		lootTableList.add(new Identifier("chests/jungle_temple"));
		lootTableList.add(new Identifier("chests/pillager_outpost"));
		lootTableList.add(new Identifier("chests/shipwreck_supply"));
		lootTableList.add(new Identifier("chests/ruined_portal"));
		lootTableList.add(new Identifier("chests/underwater_ruin_small"));
		lootTableList.add(new Identifier("chests/underwater_ruin_big"));
		lootTableList.add(new Identifier("chests/spawn_bonus_chest"));
		
		
		LootTableLoadingCallback.EVENT.register((((resourceManager, lootManager, id, supplier, setter) -> {
			if (lootTableList.contains(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootTableRange.create(1))
						.withEntry(LootTableEntry.builder(new Identifier("rebalanced", "basicbooks")).build());
				supplier.pool(poolBuilder);
				System.out.println(id);
			}
		})));

	}

}
