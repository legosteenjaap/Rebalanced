package nl.tettelaar.rebalanced.mixin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.google.common.collect.Maps;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.Advancement.Task;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.util.Identifier;

@Mixin(AdvancementManager.class)
public class AdvancementManagerMixin {

	private static final ArrayList<Identifier> recipes = new ArrayList<Identifier>();
	
	/*private static Map<Identifier, Advancement.Task> map;
	
	@Inject(method="load", at = @At("HEAD"), cancellable = true)
	public void load(Map<Identifier, Advancement.Task> map, CallbackInfo ci) {
		AdvancementManagerMixin.map = map;
	}*/
	
	@ModifyVariable(method = "load", at = @At("HEAD"), ordinal = 0)
	private Map<Identifier, Advancement.Task> removeRecipes(Map<Identifier, Advancement.Task> map) {
		recipes.add(new Identifier("recipes/combat/iron_sword"));
		recipes.add(new Identifier("recipes/combat/iron_boots"));
		recipes.add(new Identifier("recipes/combat/iron_leggings"));
		recipes.add(new Identifier("recipes/combat/iron_chestplate"));
		recipes.add(new Identifier("recipes/combat/iron_helmet"));
		HashMap<Identifier, Advancement.Task> hashMap = Maps.newHashMap(map);
		for (Identifier recipe : recipes) {
			hashMap.remove(recipe);
		}
		return hashMap;
	}
	
}
