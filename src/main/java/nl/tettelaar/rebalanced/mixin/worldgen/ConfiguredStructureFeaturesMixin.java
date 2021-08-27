package nl.tettelaar.rebalanced.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.structure.DesertVillageData;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.structure.SavannaVillageData;
import net.minecraft.structure.SnowyVillageData;
import net.minecraft.structure.TaigaVillageData;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

@Mixin(ConfiguredStructureFeatures.class)
public class ConfiguredStructureFeaturesMixin {

	@SuppressWarnings("unchecked")
	@Inject(method = "register", at = @At("HEAD"), cancellable = true)
	private static <FC extends FeatureConfig, F extends StructureFeature<FC>> void register(String id, ConfiguredStructureFeature<FC, F> configuredStructureFeature, CallbackInfoReturnable<ConfiguredStructureFeature<FC, F>> cir) {
		switch (id) {
		case "village_plains":
			cir.setReturnValue((ConfiguredStructureFeature<FC, F>) BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, (String) id, StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> {
		         return PlainsVillageData.STRUCTURE_POOLS;
		      }, 7))));
			break;
		case "village_desert":
			cir.setReturnValue((ConfiguredStructureFeature<FC, F>) BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, (String) id, StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> {
		         return DesertVillageData.STRUCTURE_POOLS;
		      }, 5))));
			break;
		case "village_savanna":
			cir.setReturnValue((ConfiguredStructureFeature<FC, F>) BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, (String) id, StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> {
		         return SavannaVillageData.STRUCTURE_POOLS;
		      }, 6))));
			break;
		case "village_taiga":
			cir.setReturnValue((ConfiguredStructureFeature<FC, F>) BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, (String) id, StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> {
		         return TaigaVillageData.STRUCTURE_POOLS;
		      }, 7))));
			break;
		case "village_snowy":
			cir.setReturnValue((ConfiguredStructureFeature<FC, F>) BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, (String) id, StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> {
		         return SnowyVillageData.STRUCTURE_POOLS;
		      }, 5))));
			break;
		}
	}

}
